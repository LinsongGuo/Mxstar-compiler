package SemanticChecker;

import java.util.ArrayList;
import AST.*;
import Scope.*;
import parser.MxstarParser.ConstructorDefContext;
import utility.*;

public class SemanticChecker implements ASTVisitor {
	Scope currentScope;
	ErrorReminder errorReminder;
	
	public SemanticChecker(ErrorReminder errorReminder) {
		this.errorReminder = errorReminder;
		currentScope = new GlobalScope(null);
		((GlobalScope)currentScope).setBuiltInType();
		((GlobalScope)currentScope).setBuiltInFunction(currentScope);
	}
	
	@Override
	public void visit(ProgramNode node){
		ArrayList<DefNode> defList = node.getDefList();
		for(DefNode item : defList) {
			item.accept(this);
		}
	}
	
	@Override
	public void visit(VarDefListNode node){
		currentScope.defineVarList((VarDefListNode)node, errorReminder);
	}
	
	@Override
	public void visit(FunctDefNode node){
		currentScope = currentScope.defineFunct(node, errorReminder);
		currentScope.defineParaList(node.getParaList(), errorReminder);
		((FunctDefNode) node).getBlockStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
	}
	
	@Override
	public void visit(ClassDefNode node){
		currentScope.defineClass(node, errorReminder);
		//define variables in class.
		ArrayList<VarDefListNode> varList = node.getVarList();
		for (VarDefListNode item : varList) {
			currentScope.defineVarList(item, errorReminder);
		}
		//define constructor in class.
		FunctDefNode constructorDef = node.getConstructorDef();
		currentScope = currentScope.defineFunct(constructorDef, errorReminder);
		if (constructorDef != null) 	
			constructorDef.getBlockStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
		
		//define functions in class.
		ArrayList<FunctDefNode> functList = node.getFunctList();
		for (FunctDefNode item : functList) {
			currentScope = currentScope.defineFunct(item, errorReminder);
			currentScope.defineParaList(item.getParaList(), errorReminder);
			item.getBlockStmt().accept(this);
			currentScope = currentScope.getEnclosingScope();
		}
	}
	

	@Override
	public void visit(VarDefNode node) {
		
	}
	
	@Override
	public void visit(TypeNode node) {
		
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
		ArrayList<StmtNode> stmtList = node.getStmtList();
		for (StmtNode item : stmtList) {
			item.accept(this);
		}
	}
	
	@Override
	public void visit(BrankStmtNode node) {
		;
	}
	
	@Override
	public void visit(BreakStmtNode node) { 
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"The break statement must be in one loop."	
			);
		}
	}
	
	@Override
	public void visit(ContinueStmtNode node) { 
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"The continue statement must be in one loop."	
			);
		}
	}
	
	@Override
	public void visit(ExprStmtNode node) {;
		node.accept(this);
	}
	
	@Override
	public void visit(ForStmtNode node) {
		currentScope = new LocalScope(currentScope, ScopeType.LoopScope);
		ExprNode initExpr = node.getInitExpr();
		if (initExpr != null) 
			initExpr.accept(this);
		ExprNode condExpr = node.getCondExpr();
		if (condExpr != null)
			condExpr.accept(this);
		ExprNode stepExpr = node.getStepExpr();
		if (stepExpr != null)
			stepExpr.accept(this);
		node.getStmt().accept(this);	
	}
	
	@Override
	public void visit(IfStmtNode node) { 
		currentScope = new LocalScope(currentScope, ScopeType.IfScope);
		node.getCond().accept(this);
		node.getThenStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
		
		if(node.getElseStmt() != null) {
			currentScope = new LocalScope(currentScope, ScopeType.ElseScope);
			node.getElseStmt().accept(this);
			currentScope = currentScope.getEnclosingScope();
		}
	}
	
	@Override
	public void visit(ReturnStmtNode node) {
		if (!currentScope.inFunctScope()) {
			errorReminder.error(node.getLoc(),
				"The return statement must be in a function."
			);
		}
		if (node.getExpr() != null) {
			node.getExpr().accept(this);
		}
	}
	
	@Override
	public void visit(VarDefStmtNode node) {
		node.getVarDefList().accept(this);
	}
	
	@Override
	public void visit(WhileStmtNode node) {
		currentScope = new LocalScope(currentScope, ScopeType.LoopScope);
		node.getExpr().accept(this);
		node.getStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
	}
	
	
	//expression--------------------------------------------------
	@Override
	public void visit(ThisExprNode node) {
		ClassSymbol classSymbol = currentScope.getClassSymbol();
		if (classSymbol == null) {
			errorReminder.error(node.getLoc(), 
				"invalid use of \"this\" in non-member function."
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
		for (ExprNode item : indexExpr) {
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
	}
	
	@Override
	public void visit(CreatorExprNode node) {
		ArrayList<ExprNode> indexList = node.getIndexList();
		for (ExprNode item : indexList) {
			item.accept(this);
			if (!(item.getType() instanceof IntType)) {
				errorReminder.error(item.getLoc(), "the index of the array should be an integer.");
			}	
		}
		String tmp = node.getTypeNode().toString();
		Type type = currentScope.resolveType(tmp);
		if (type != null) {
			node.setType(type);
		}
		else {
			errorReminder.error(node.getLoc(), 
				"class \"" + tmp + "\" is not declared in this scope."
			);
		}
		
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
			if(!(type instanceof ClassSymbol)) {
				errorReminder.error(node.getLoc(), "\"" + type.toString() + "\" is a non-class type.");
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
					VarSymbol arraySymbol = ((ClassSymbol)type).findArray(arrayExpr, errorReminder);
					if (arraySymbol != null) {
						node.setType(arraySymbol.getType());
						node.setLvalue(true);
					}
				}
				else if (node.getFunctExpr() != null) {
					FunctExprNode functExpr = node.getFunctExpr();
					FunctSymbol functSymbol = ((ClassSymbol)type).findFunct(functExpr, errorReminder);
					if (functExpr != null) {
						node.setType(functExpr.getType());
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
		node.setType(type);
		//check lvalue
		if (!expr.getLvalue()) {
			errorReminder.error(node.getLoc(), 
				"lvalue required as operator" + op.toString() + "."
			);
		}
		//check type
		if (!(type instanceof IntType)) {
			errorReminder.error(node.getLoc(), 
				"no match for operator" + op.toString() + "as the suffix of the expression."
			);
		}
	}
	
	@Override
	public void visit(PrefixExprNode node) {
		ExprNode expr = node.getExpr();
		Operator op = node.getOp();
		expr.accept(this);
		Type type = expr.getType();
		if (type == null) return;
		node.setType(type);
		//check type
		if (op == Operator.logicalNOT) {
			if (!(type instanceof BoolType)) {
				errorReminder.error(node.getLoc(), 
					"no match for operator! as the prefix of the expression."
				);
			}	
		}
		else if (op == Operator.prefixDECR || op == Operator.prefixINCR){
			if (!expr.getLvalue()) {
				errorReminder.error(node.getLoc(), 
					"lvalue required as operator" + op.toString() + "."
				);
			}
			if (!(type instanceof IntType)) {
				errorReminder.error(node.getLoc(), 
					"no match for operator" + op.toString() + "as the prefix of the expression."
				);
			}
		}
		else if (op == Operator.POS || op == Operator.NEG || op == Operator.bitwiseNOT){
			if (!(type instanceof IntType)) {
				errorReminder.error(node.getLoc(), 
					"no match for operator" + op.toString() + "as the prefix of the expression."
				);
			}
		}
	}
	
	@Override
	public void visit(BinaryExprNode node) {
		ExprNode left = node.getLeft(), right = node.getRight();
		left.accept(this);
		right.accept(this);
		node.setLvalue(false);
		Operator op = node.getOp();
		Type leftType = left.getType(), rightType = right.getType();
		if (leftType == null || rightType == null) return;
		if (op == Operator.ASSIGN) {
			node.setType(leftType);
			if (!left.getLvalue()) {
				errorReminder.error( node.getLoc(),
					"lvalue required as left operand of assignment."
				);
			}
			if (!leftType.toString().equals(rightType.toString())) {
				if (!((leftType instanceof ClassSymbol || leftType instanceof ArrayType) && rightType instanceof NullType)) {
					errorReminder.error( node.getLoc(),
						"no match for assignment between the two expressions."
					);
				}
			}
		}
		else if (op == Operator.EQU || op == Operator.notEQU) {
			node.setType(new BoolType());
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
						"no match for operator== between the two expressions."
					);
			     }
		}
		else if ( op == Operator.LESS    || 
			      op == Operator.lessEQU || 
			      op == Operator.GREATER || 
			      op == Operator.greaterEQU 
			   	) { 
			        node.setType(new BoolType());
					if ( !((leftType instanceof IntType && rightType instanceof IntType)  ||
						   (leftType instanceof StringType && rightType instanceof StringType)) 
					   ) {
							errorReminder.error( node.getLoc(),
							   "no match for operator" + op.toString() + " between the two expressions."
						    );
				         }
		         }
		else if (op == Operator.logicalAND || op == Operator.logicalOR) {
			node.setType(new BoolType());
			if ( !(leftType instanceof BoolType && rightType instanceof BoolType) ) {
				errorReminder.error( node.getLoc(),
					"no match for operator" + op.toString() + " between the two expressions."
			    );
			}
		}
		else if ( op == Operator.ADD        ||
				  op == Operator.MUL        ||
				  op == Operator.DIV        ||
				  op == Operator.MOD        ||
				  op == Operator.bitwiseAND ||
				  op == Operator.bitwiseOR  ||
				  op == Operator.bitwiseXOR ||
				  op == Operator.leftSHIFT  ||
				  op == Operator.rightSHIFT
				) {
					 node.setType(new IntType());
					 if ( !(leftType instanceof IntType && rightType instanceof IntType) ) {
							errorReminder.error( node.getLoc(),
								"no match for operator" + op.toString() + " between the two expressions."
						    );
						}
		          }
		else if (op == Operator.ADD) {
			if (leftType instanceof IntType && rightType instanceof IntType) {
				node.setType(new IntType());
			}
			else if (leftType instanceof StringType && rightType instanceof StringType) {
				node.setType(new StringType());
			}
			else {
				errorReminder.error( node.getLoc(),
					"no match for operator+ between the two expressions."
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
		node.setType(new StringType());
		node.setLvalue(false);
	}
	
}
