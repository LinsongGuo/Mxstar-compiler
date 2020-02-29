package SemanticChecker;

import java.util.ArrayList;
import AST.*;
import Scope.*;
import parser.MxstarParser.ConstructorDefContext;
import utility.*;

public class SemanticChecker implements ASTVisitor {
	private Scope currentScope;
	private ErrorReminder errorReminder;
	private StringType stringTemplate;
	
	public SemanticChecker(ErrorReminder errorReminder) {
		this.errorReminder = errorReminder;
		currentScope = new GlobalScope(null);
		stringTemplate = new StringType(currentScope);
		((GlobalScope)currentScope).setBuiltInMember(currentScope, stringTemplate);
	}
	
	@Override
	public void visit(ProgramNode node){
		ArrayList<DefNode> defList = node.getDefList();
		boolean hasMain = false;
		for (DefNode item : defList) {
			if (item instanceof FunctDefNode) {
				if (((FunctDefNode) item).getIdentifier().equals("main")) {
					boolean can = true;
					if (!((FunctDefNode) item).getType().toString().equals("int")) {
						errorReminder.error(item.getLoc(), 
							"\'main\' function must return \'int\'."
						);
						can = false;
					}
					if (!((FunctDefNode) item).getParaList().isEmpty()) {
						errorReminder.error(item.getLoc(), 
							"\'main\' function should not have parameters."
						);
						can = false;
					}
					if (can) {
						if (hasMain) {
							errorReminder.error(item.getLoc(), 
								"\'int main\' previously here."  
							);
						}
						else {
							hasMain = true;
							item.accept(this);
						}
					}	
				}
				else 
					item.accept(this);
			}	
			else if ((item instanceof VarDefListNode) || (item instanceof ClassDefNode))
				item.accept(this);
		}
		if (!hasMain) {
			errorReminder.error(node.getLoc(), "\'int main()\' is not declared.");
		}
	}
	
	@Override
	public void visit(VarDefListNode node){
		ArrayList<VarDefNode> varList = node.getVarList();
		for (VarDefNode item : varList) {
			ExprNode initValue = item.getInitValue();
			if (initValue != null)
				initValue.accept(this);
			//System.err.println("INIT" + initValue.getType().toString());
		}
		currentScope.defineVarList((VarDefListNode)node, errorReminder);
	}
	
	@Override
	public void visit(FunctDefNode node){
		currentScope = currentScope.defineFunct(node, errorReminder);
		
		currentScope.defineParaList(node.getParaList(), errorReminder);
		ArrayList<StmtNode> stmtList = node.getStmtList();
		for (StmtNode stmt : stmtList) {
			stmt.accept(this);
		}
		
		currentScope = currentScope.getEnclosingScope();
	}
	
	@Override
	public void visit(ClassDefNode node){
		currentScope = currentScope.defineClass(node, errorReminder);
		
		//define variables in class.
		ArrayList<VarDefListNode> varList = node.getVarList();
		for (VarDefListNode item : varList) {
			ArrayList<VarDefNode> initList = item.getVarList();
			for (VarDefNode initVar : initList) {
				ExprNode initValue = initVar.getInitValue();
				if (initValue != null)
					initValue.accept(this);
			}
			currentScope.defineVarList(item, errorReminder);
		}
		
		//define constructor in class.
		FunctDefNode constructorDef = node.getConstructorDef();
		currentScope = ((ClassSymbol)currentScope).defineConstructor();
		if (constructorDef != null) {
			ArrayList<StmtNode> stmtList = constructorDef.getStmtList();
			for (StmtNode stmt : stmtList) {
				stmt.accept(this);
			}
		}
		currentScope = currentScope.getEnclosingScope();
		
		//define functions in class.
		ArrayList<FunctDefNode> functList = node.getFunctList();
		for (FunctDefNode item : functList) {
			currentScope = currentScope.defineFunct(item, errorReminder);
			currentScope.defineParaList(item.getParaList(), errorReminder);
			ArrayList<StmtNode> stmtList1 = item.getStmtList();
			for (StmtNode stmt : stmtList1) {
				stmt.accept(this);
			}
			currentScope = currentScope.getEnclosingScope();
		}
		
		currentScope = currentScope.getEnclosingScope();
	}
	

	@Override
	public void visit(VarDefNode node) {
		
	}
	
	@Override
	public void visit(PrimTypeNode node) {
		
	}
	
	@Override
	public void visit(ClassTypeNode node) {
		
	}
	
	@Override
	public void visit(ArrayTypeNode node) {
		
	}

	//statement--------------------------------------------------
	@Override
	public void visit(BlockStmtNode node) {
		currentScope = new LocalScope(currentScope, ScopeType.BlockScope);
		ArrayList<StmtNode> stmtList = node.getStmtList();
		for (StmtNode item : stmtList) {
			item.accept(this);
		}
		currentScope = currentScope.getEnclosingScope();
	}
	
	@Override
	public void visit(BrankStmtNode node) {
		;
	}
	
	@Override
	public void visit(BreakStmtNode node) { 
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"break-statement not within loop."	
			);
		}
	}
	
	@Override
	public void visit(ContinueStmtNode node) { 
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"continue-statement not within loop."		
			);
		}
	}
	
	@Override
	public void visit(ExprStmtNode node) {
		node.getExpr().accept(this);
	}
	
	@Override
	public void visit(IfStmtNode node) { 
		//System.err.println("enter if");
		currentScope = new LocalScope(currentScope, ScopeType.IfScope);
		//check the type of cond-expr
		ExprNode cond = node.getCond();
		if (cond == null) {
			errorReminder.error(node.getLoc(), "empty condition of if-statement.");
		}
		else {
			cond.accept(this);
			Type type = cond.getType();
			if (type != null && !(type instanceof BoolType)) {
				errorReminder.error(cond.getLoc(),
					"cannot convert \'" + type.toString() + "\' to \'bool\' in initialization."
				);
			}
		}
		//then-stmt
		node.getThenStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
		//else-stmt
		if(node.getElseStmt() != null) {
			currentScope = new LocalScope(currentScope, ScopeType.ElseScope);
			node.getElseStmt().accept(this);
			currentScope = currentScope.getEnclosingScope();
		}
	}
	
	@Override
	public void visit(ForStmtNode node) {
		currentScope = new LocalScope(currentScope, ScopeType.LoopScope);
		//init-expr
		ExprNode initExpr = node.getInitExpr();
		if (initExpr != null) 
			initExpr.accept(this);
		//check the type of cond-expr
		ExprNode condExpr = node.getCondExpr();
		if (condExpr != null) {
			condExpr.accept(this);
			Type type = condExpr.getType();
			if (type != null && !(type instanceof BoolType)) {
				errorReminder.error(condExpr.getLoc(),
					"cannot convert \'" + type.toString() + "\' to \'bool\' in initialization."
				);
			}
		}
		//step-expr
		ExprNode stepExpr = node.getStepExpr();
		if (stepExpr != null)
			stepExpr.accept(this);
		//stmt
		node.getStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
	}
	
	@Override
	public void visit(WhileStmtNode node) {
		currentScope = new LocalScope(currentScope, ScopeType.LoopScope);
		//check the type of cond-expr
		ExprNode condExpr = node.getExpr();
		if (condExpr == null) {
			errorReminder.error(node.getLoc(), "empty condition of while-statement.");
		}
		else {
			condExpr.accept(this);
			Type type = condExpr.getType();
			if (type != null && !(type instanceof BoolType)) {
				errorReminder.error(condExpr.getLoc(),
					"cannot convert \'" + type.toString() + "\' to \'bool\' in initialization."
				);
			}				
		}
		//stmt
		node.getStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
	}
		
	@Override
	public void visit(ReturnStmtNode node) {
		FunctSymbol functSymbol = currentScope.getFunctSymbol();
		ExprNode expr = node.getExpr();
		if (functSymbol == null) {
			errorReminder.error(node.getLoc(),
				"return-statement must be in a function."
			);
		}
		else if (expr != null) {
			expr.accept(this);
			Type retType = functSymbol.getType();
			Type exprType = expr.getType();
			if (exprType == null) return;
			//System.err.println("return " + retType.toString());
			if (retType == null) {
				errorReminder.error(node.getLoc(), 
					"should have return value."
				);
			}
			else if (retType instanceof VoidType) {
				errorReminder.error(node.getLoc(), 
					"return-statement with a value, in function returning \'void\'."
				);
			}
			else if(!retType.toString().equals(exprType.toString())){
				errorReminder.error(expr.getLoc(),
					"cannot convert \'" + exprType.toString() + "\' to \'" + retType.toString() + "\' in initialization."
				);
			}
		}
		else {
			Type retType = functSymbol.getType();
			if (!(retType instanceof VoidType)) {
				errorReminder.error(node.getLoc(),
					"return-statement with no value, in function returning \'" + retType.toString() + "\'."
				);
			}
		}
		
	}
	
	@Override
	public void visit(VarDefStmtNode node) {
		node.getVarDefList().accept(this);
	}
	
	//expression--------------------------------------------------
	@Override
	public void visit(ThisExprNode node) {
		ClassSymbol classSymbol = currentScope.getClassSymbol();
		if (classSymbol == null) {
			errorReminder.error(node.getLoc(), 
				"invalid use of \'this\' in non-member function."
			);
		}
		else 
			node.setType(classSymbol);
	}
	
	@Override
	public void visit(VarExprNode node) {
		VarSymbol var = currentScope.resovleVar(node, errorReminder);
		if (var != null) {
			node.setType(var.getType());
			node.setLvalue(true);
		}
	}	
	
	@Override
	public void visit(ArrayExprNode node) {
		ArrayList<ExprNode> indexExpr = node.getIndexExpr();
		//System.err.println(indexExpr.size());
		for (ExprNode item : indexExpr) {
			if (item != null)
				item.accept(this);
		}
		VarSymbol var = currentScope.resovleArray(node, errorReminder);
		if (var != null) {
			node.setType(var.getType());
			node.setLvalue(true);
		}
	}
	
	@Override
	public void visit(BracketExprNode node) {
		ExprNode expr = node.getExpr();
		expr.accept(this);
		node.setType(expr.getType());
		node.setLvalue(expr.getLvalue());
		//System.err.println("bracket: " + node.getType().toString());
	}
	
	@Override
	public void visit(CreatorExprNode node) {
		ArrayList<ExprNode> indexList = node.getIndexList();
		for (ExprNode item : indexList) {
			item.accept(this);
			Type tmp = item.getType();
			if (!(tmp instanceof IntType)) {
				errorReminder.error(item.getLoc(), 
					"cannot convert \'" + tmp.toString() + "\' to \'int\' in initialization."	
				);
			}	
		}
		String tmp = node.getTypeNode().typeString();
		Type type = currentScope.resolveType(tmp);
		if (type != null) {
			node.setType(new ArrayType(tmp, node.getDimension()));
		}
		else {
			errorReminder.error(node.getLoc(), 
				"class \'" + tmp + "\' was not declared in this scope."
			);
		}
		//System.err.println("creator: " + node.getType().toString());
	}
	
	@Override
	public void visit(FunctExprNode node) {
		ArrayList<ExprNode> paraList = node.getParaList();
		for (ExprNode item : paraList) {
			item.accept(this);
		}
		FunctSymbol functSymbol = currentScope.resolveFunct(node, errorReminder);
		if (functSymbol != null)
			node.setType(functSymbol.getType());
	}
	
	@Override
	public void visit(MemberExprNode node) { 
		ExprNode expr = node.getExpr();
		expr.accept(this);
		Type type = expr.getType();
		if (type != null) {
			//System.err.println("MemberExpr " + type.toString());
			if(!(type instanceof ClassSymbol)) {
				errorReminder.error(node.getLoc(), "invalid member call in a non-class type \'" + type.toString() + "\'.");
			}
			else {
				if (node.getVarExpr() != null) {
					VarExprNode varExpr = node.getVarExpr();
					VarSymbol varSymbol = ((ClassSymbol)type).findVar(varExpr, errorReminder);
					if (varSymbol != null) {
						node.setType(varSymbol.getType());
						node.setLvalue(true);
					}
				}
				else if (node.getArrayExpr() != null) {
					ArrayExprNode arrayExpr = node.getArrayExpr();
					ArrayList<ExprNode> indexExpr = arrayExpr.getIndexExpr();
					for (ExprNode item : indexExpr) {
						item.accept(this);
					}
					VarSymbol arraySymbol = ((ClassSymbol)type).findArray(arrayExpr, errorReminder);
					if (arraySymbol != null) {
						node.setType(arraySymbol.getType());
						node.setLvalue(true);
					}
				}
				else if (node.getFunctExpr() != null) {
					FunctExprNode functExpr = node.getFunctExpr();
					ArrayList<ExprNode> paraList = functExpr.getParaList();
					for (ExprNode item : paraList) {
						item.accept(this);
					}
					FunctSymbol functSymbol = ((ClassSymbol)type).findFunct(functExpr, errorReminder);
					if (functSymbol != null) {
						node.setType(functSymbol.getType());
					}
				}
			}
		}
		
	}
	
	@Override
	public void visit(SuffixExprNode node) {
		ExprNode expr = node.getExpr();
		Operator op = node.getOp();
		expr.accept(this);
		Type type = expr.getType();
		if (type == null) return;
		//check lvalue
		if (!expr.getLvalue()) {
			errorReminder.error(node.getLoc(), 
				"lvalue required as operator" + op.toString() + "."
			);
		}
		//check type
		else if (!(type instanceof IntType)) {
			errorReminder.error(node.getLoc(), 
				"no match for operator" + op.toString() + "as the suffix of \'" + type.toString() + "\'."
			);
		}
		else 
			node.setType(type);
	}
	
	@Override
	public void visit(PrefixExprNode node) {
		ExprNode expr = node.getExpr();
		Operator op = node.getOp();
		expr.accept(this);
		Type type = expr.getType();
		if (type == null) return;
		//check type
		if (op == Operator.logicalNOT) {
			if (!(type instanceof BoolType)) {
				errorReminder.error(node.getLoc(), 
					"no match for operator! as the prefix of \'" + type.toString() + "\'."
				);
			}	
			else
				node.setType(type);
		}
		else if (op == Operator.prefixDECR || op == Operator.prefixINCR){
			if (!expr.getLvalue()) {
				errorReminder.error(node.getLoc(), 
					"lvalue required as operator" + op.toString() + "."
				);
			}
			else if (!(type instanceof IntType)) {
				errorReminder.error(node.getLoc(), 
					"no match for operator" + op.toString() + "as the prefix of \'" + type.toString() + "\'."
				);
			}
			else {
				node.setType(type);
				node.setLvalue(true);
			}	
		}
		else if (op == Operator.POS || op == Operator.NEG || op == Operator.bitwiseNOT){
			if (!(type instanceof IntType)) {
				errorReminder.error(node.getLoc(), 
					"no match for operator" + op.toString() + "as the prefix of \'" + type.toString() + "\'."
				);
			}
			else
				node.setType(type);
		}
	}
	
	@Override
	public void visit(BinaryExprNode node) {
		//System.err.println("check bianrynode");
		ExprNode left = node.getLeft(), right = node.getRight();
		left.accept(this);
		right.accept(this);
		node.setLvalue(false);
		Operator op = node.getOp();
		Type leftType = left.getType(), rightType = right.getType();
		if (leftType == null || rightType == null) {
			return;
		}
		if (op == Operator.ASSIGN) {
			if (!left.getLvalue()) {
				errorReminder.error( node.getLoc(),
					"lvalue required as left operand of assignment."
				);
			}
			else if (!leftType.toString().equals(rightType.toString())) {
				if (!((leftType instanceof ClassSymbol || leftType instanceof ArrayType) && rightType instanceof NullType)) {
					errorReminder.error( right.getLoc(),
						"cannot convert \'" + rightType.toString() + "\' to \'" + leftType.toString() + "\' in initialization."	
					);
				}
			}
			else {
				/*node.setType(leftType);
				int d1 = (leftType instanceof ArrayType) ? ((ArrayType)leftType).getDimension() : 0;
				int d2 = (rightType instanceof ArrayType) ? ((ArrayType)rightType).getDimension() : 0;
				if (d1 != d2) {
					errorReminder.error(right.getLoc(), 
						"cannot convert \'" + rightType.toString() + "\' to \'" + leftType.toString() + "\' in initialization."
					);
				}
				else */
				node.setType(leftType);
			}
		}
		else if (op == Operator.EQU || op == Operator.notEQU) {
			if ( !((leftType instanceof IntType && rightType instanceof IntType)       ||   
				   (leftType instanceof BoolType && rightType instanceof BoolType)     ||    
				   (leftType instanceof StringType && rightType instanceof StringType) ||     
				   (leftType instanceof ClassSymbol && rightType instanceof NullType)  ||    
				   (leftType instanceof NullType && rightType instanceof ClassSymbol)  ||     
				   (leftType instanceof ArrayType && rightType instanceof NullType)    ||        
				   (leftType instanceof NullType && rightType instanceof ArrayType)    ||     
				   (leftType instanceof NullType && rightType instanceof NullType)) 
			   ) {
					errorReminder.error( node.getLoc(),
						"no match for operator== between \'" + leftType.toString() + "\' and \'" + rightType.toString() + "\'."
					);
			     }
			else 
				node.setType(new BoolType());
		}
		else if ( op == Operator.LESS    || 
			      op == Operator.lessEQU || 
			      op == Operator.GREATER || 
			      op == Operator.greaterEQU 
			   	) { 
					if ( !((leftType instanceof IntType && rightType instanceof IntType)  ||
						   (leftType instanceof StringType && rightType instanceof StringType)) 
					   ) {
							errorReminder.error( node.getLoc(),
							   "no match for operator" + op.toString() + " between \'" + leftType.toString() + "\' and \'" + rightType.toString() + "\'."
						    );
				         }
			        else
			        	node.setType(new BoolType());
		         }
		else if (op == Operator.logicalAND || op == Operator.logicalOR) {
			if ( !(leftType instanceof BoolType && rightType instanceof BoolType) ) {
				errorReminder.error( node.getLoc(),
					"no match for operator" + op.toString() + " between \'" + leftType.toString() + "\' and \'" + rightType.toString() + "\'."
			    );
			}
			else 
				node.setType(new BoolType());
		}
		else if ( op == Operator.SUB        ||
				  op == Operator.MUL        ||
				  op == Operator.DIV        ||
				  op == Operator.MOD        ||
				  op == Operator.bitwiseAND ||
				  op == Operator.bitwiseOR  ||
				  op == Operator.bitwiseXOR ||
				  op == Operator.leftSHIFT  ||
				  op == Operator.rightSHIFT
				) {
					 if ( !(leftType instanceof IntType && rightType instanceof IntType) ) {
							errorReminder.error( node.getLoc(),
								"no match for operator" + op.toString() + " between \'" + leftType.toString() + "\' and \'" + rightType.toString() + "\'."
							);
						}
					 else
						 node.setType(new IntType());
		          }
		else if (op == Operator.ADD) {
			if (leftType instanceof IntType && rightType instanceof IntType) {
				node.setType(new IntType());
			}
			else if (leftType instanceof StringType && rightType instanceof StringType) {
				node.setType(stringTemplate);
			}
			else {
				errorReminder.error( node.getLoc(),
					"no match for operator" + op.toString() + " between \'" + leftType.toString() + "\' and \'" + rightType.toString() + "\'."    
				);
			}
		}
	}
	
	@Override
	public void visit(BoolLiteralNode node) {
		node.setType(new BoolType());
		node.setLvalue(false);
	}
	
	@Override
	public void visit(IntLiteralNode node) { 
		node.setType(new IntType());
		node.setLvalue(false);
	}
	
	@Override
	public void visit(StringLiteralNode node) {
		node.setType(stringTemplate);
		node.setLvalue(false);
	}
	
}
