package SemanticChecker;

import java.util.ArrayList;
import java.util.HashSet;
import AST.*;
import Scope.*;
import utility.*;

public class SemanticChecker implements ASTVisitor {
	private Scope currentScope;
	private GlobalScope globalScope;
	private ErrorReminder errorReminder;
	private StringType stringTemplate;
	private HashSet<String> classSet; 
	private String[] reservedWordList = {"int", "bool", "string", "null", "void", "true", "false", "if", "else", "for", "while", "break", "continue", "return", "new", "class", "this"};
	
	public SemanticChecker(ErrorReminder errorReminder) {
		this.errorReminder = errorReminder;
		currentScope = globalScope = new GlobalScope(null);
		stringTemplate = new StringType(currentScope);
		classSet = new HashSet<String>();
		((GlobalScope)currentScope).setBuiltInMember(currentScope, stringTemplate);
	}
	
	public GlobalScope getGlobalScope() {
		return globalScope;
	}
	
	public StringType getStringTemplate() {
		return stringTemplate;
	}
	
	private boolean isReservedWord(String identifier) {
		for (int i = 0; i < 17; ++i) {
			if (identifier.equals(reservedWordList[i]))
				return true;
		}
		return false;
	}
	
	@Override
	public void visit(ProgramNode node){
		node.setScope(currentScope);
		ArrayList<DefNode> defList = node.getDefList();
		//declare classes.
		for (DefNode classItem : defList) {
			if (classItem instanceof ClassDefNode) {
				String identifier = ((ClassDefNode) classItem).getIdentifier();
				if (isReservedWord(identifier)) {
					errorReminder.error(classItem.getLoc(), 
						"class name \'" + identifier + "\' is a reserved word."
					);
				}
				else {
					ClassSymbol classSymbol = currentScope.declareClass((ClassDefNode)classItem, errorReminder);					
					((ClassDefNode) classItem).setIRClass(classSymbol);
				}
			}
		}
		
		//declare members in classes.
		for (DefNode classItem : defList) {
			if (classItem instanceof ClassDefNode) {
				String identifier = ((ClassDefNode) classItem).getIdentifier();
				if (classSet.contains(identifier)) continue;
				//declare members in the class
				if (!isReservedWord(identifier)) {
					ClassSymbol classScope = currentScope.getClassScope(identifier);
					//define variables in the class
					ArrayList<VarDefListNode> varLists =  ((ClassDefNode) classItem).getVarList();
					for (VarDefListNode varListItem : varLists) {
						ArrayList<VarDefNode> varList = varListItem.getVarList();
						for (VarDefNode varItem : varList) {
							String varIdentifier = varItem.getIdentifier();
							if (isReservedWord(varIdentifier)) {
								errorReminder.error(varItem.getLoc(), 
									"variable name \'" + varIdentifier + "\' is a reserved word."
								);
							} 
							else if (classScope.duplicateClass(varIdentifier)) {
								errorReminder.error(varItem.getLoc(), 
									"duplicate name for variable \'" + varIdentifier + "\' and type \'" + varIdentifier + "\'."
								);
							}
							else {
								VarSymbol varSymbol = classScope.declareVar(varItem, errorReminder);		
								varItem.setVarSymbol(varSymbol);
							}
						}
					}
					//declare constructor in the class
					FunctSymbol constructor = classScope.declareConstructor();
					FunctDefNode constructorDef = ((ClassDefNode) classItem).getConstructorDef(); 
					if(constructorDef != null)
						constructorDef.setFunctSymbol(constructor);
					//declare functions in the class
					ArrayList<FunctDefNode> functList = ((ClassDefNode) classItem).getFunctList();
					for (FunctDefNode functItem : functList) {
						String functIdentifier = functItem.getIdentifier();
						if (isReservedWord(functIdentifier)) {
							errorReminder.error(functItem.getLoc(), 
								"variable name \'" + functIdentifier + "\' is a reserved word."
							);
						}
						else if (classScope.duplicateClass(functIdentifier)) {
							errorReminder.error(functItem.getLoc(), 
								"duplicate name for function \'" + functIdentifier + "\' and type \'" + functIdentifier + "\'."
							);
						}
						else {
							FunctSymbol functSymbol = classScope.declareFunct(functItem, errorReminder);
							functSymbol.declareParaList(functItem.getParaList(), errorReminder);
							functItem.setFunctSymbol(functSymbol);
						}
					}
				}
				classSet.add(identifier);
			}
		}
		
		//declare all functions in global scope.
		boolean hasMain = false;
		for (DefNode functItem : defList) {
			if (functItem instanceof FunctDefNode) {
				String identifier = ((FunctDefNode) functItem).getIdentifier();
				if (isReservedWord(identifier)) {
					errorReminder.error(functItem.getLoc(), 
						"class name \'" + identifier + "\' is a reserved word."
					);
				}
				else if (currentScope.duplicateClass(identifier)) {
					errorReminder.error(functItem.getLoc(), 
						"duplicate name for function \'" + identifier + "\' and class \'" + identifier + "\'."
					);
				}
				else {
					if (identifier.equals("main")) {
						boolean can = true;
						if (!((FunctDefNode) functItem).getType().toString().equals("int")) {
							errorReminder.error(functItem.getLoc(), 
								"\'main\' function must return \'int\'."
							);
							can = false;
						}
						if (!((FunctDefNode) functItem).getParaList().isEmpty()) {
							errorReminder.error(functItem.getLoc(), 
								"\'main\' function should not have parameters."
							);
							can = false;
						}
						if (can) {
							if (hasMain) {
								errorReminder.error(functItem.getLoc(), 
									"\'int main\' previously declared here."  
								);
							}
							else {
								hasMain = true;
								FunctSymbol functSymbol = currentScope.declareFunct((FunctDefNode)functItem, errorReminder);
								functSymbol.declareParaList(((FunctDefNode)functItem).getParaList(), errorReminder);
								((FunctDefNode) functItem).setFunctSymbol(functSymbol);
							}
						}	
					}
					else {
						FunctSymbol functSymbol = currentScope.declareFunct((FunctDefNode)functItem, errorReminder);
						if (functSymbol != null) {
							functSymbol.declareParaList(((FunctDefNode)functItem).getParaList(), errorReminder);
							((FunctDefNode) functItem).setFunctSymbol(functSymbol);
						}
					}
				}
			}	
		}
		if (!hasMain) {
			errorReminder.error(node.getLoc(), "\'int main()\' is not declared.");
		}
		
		for (DefNode item : defList) {
			if ((item instanceof ClassDefNode) || (item instanceof FunctDefNode) || (item instanceof VarDefListNode)) {
				item.accept(this);
			}
		}
	}
	
	@Override
	public void visit(VarDefListNode node){
		node.setScope(currentScope);		
		ArrayList<VarDefNode> varList = node.getVarList();
		for (VarDefNode item : varList) {
			//initial value
			ExprNode initValue = item.getInitValue();
			if (initValue != null) {
				initValue.accept(this);
			}
			//declare
			String identifier = item.getIdentifier();
			if (isReservedWord(identifier)) {
				errorReminder.error(item.getLoc(), 
					"variable name \'" + identifier + "\' is a reserved word."
				);
			}
			else if (currentScope.duplicateClass(identifier)) {
				errorReminder.error(item.getLoc(), 
					"duplicate name for variable \'" + identifier + "\' and class \'" + identifier + "\'."
				);
			}
			else {
				VarSymbol varSymbol = currentScope.declareVar(item, errorReminder);
				if (varSymbol != null) {
					item.setVarSymbol(varSymbol);
					if (initValue != null)
						varSymbol.checkInitValue(initValue, errorReminder);
					varSymbol.beenDefined();
				}
			}
		}
	}
	
	@Override
	public void visit(VarDefNode node) {
		node.setScope(currentScope);
		String identifier = node.getIdentifier();
		if (!isReservedWord(identifier) && !currentScope.duplicateClass(identifier)) {
			VarSymbol varSymbol = currentScope.getVarSymbol(identifier);
			node.setVarSymbol(varSymbol);
			if (varSymbol != null && !varSymbol.definedOrNot()) {
				ExprNode initValue = node.getInitValue();
				if(initValue != null) {
					initValue.accept(this);
					varSymbol.checkInitValue(initValue, errorReminder);
				}
				varSymbol.beenDefined();
			}
		}
	}
	
	@Override
	public void visit(FunctDefNode node){
		String identifier = node.getIdentifier();
		if (!isReservedWord(identifier)) {
			if (!currentScope.duplicateClass(identifier)) {
				FunctSymbol functSymbol = currentScope.getFunctScope(identifier);
				if (functSymbol != null && !functSymbol.definedOrNot()) {
					currentScope = functSymbol;
					node.setScope(currentScope);
					ArrayList<StmtNode> stmtList = node.getStmtList();
					for (StmtNode stmt : stmtList) {
						if (stmt != null)
							stmt.accept(this);
					}
					functSymbol.beenDefined();
					currentScope = currentScope.getEnclosingScope();
				}
			}
		}
	}
	
	@Override
	public void visit(ClassDefNode node){
		String identifier = node.getIdentifier();
		if (!isReservedWord(identifier)) {
			ClassSymbol classSymbol = currentScope.getClassScope(identifier);
			if (classSymbol != null && !classSymbol.definedOrNot()) {
				currentScope = classSymbol;
				node.setScope(currentScope);
				//define variables;
				ArrayList<VarDefListNode> varLists = node.getVarList();
				for (VarDefListNode varListNode : varLists) {
					ArrayList<VarDefNode> varList = varListNode.getVarList();
					for (VarDefNode varItem : varList) {
						varItem.accept(this);
					}
				}
				//define constructor.
				FunctDefNode constructorDef = node.getConstructorDef();
				if (constructorDef != null) {
					constructorDef.accept(this);
				}
				//define functions.
				ArrayList<FunctDefNode> functList = node.getFunctList();
				for (FunctDefNode item : functList) {
					item.accept(this);
				}
				classSymbol.beenDefined();
				currentScope = currentScope.getEnclosingScope();
			}
		}
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
		node.setScope(currentScope);
		ArrayList<StmtNode> stmtList = node.getStmtList();
		for (StmtNode item : stmtList) {
			item.accept(this);
		}
		currentScope = currentScope.getEnclosingScope();
	}
	
	@Override
	public void visit(BrankStmtNode node) {
		node.setScope(currentScope);
	}
	
	@Override
	public void visit(BreakStmtNode node) {
		node.setScope(currentScope);
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"break-statement not within loop."	
			);
		}
	}
	
	@Override
	public void visit(ContinueStmtNode node) { 
		node.setScope(currentScope);
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"continue-statement not within loop."		
			);
		}
	}
	
	@Override
	public void visit(ExprStmtNode node) {
		node.setScope(currentScope);
		node.getExpr().accept(this);
	}
	
	@Override
	public void visit(IfStmtNode node) { 
		//System.err.println("enter if");
		currentScope = new LocalScope(currentScope, ScopeType.IfScope);
		node.setScope(currentScope);
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
		node.setScope(currentScope);
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
		node.setScope(currentScope);
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
		node.setScope(currentScope);
		FunctSymbol functSymbol = currentScope.InFunctSymbol();
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
			if (exprType != null && retType != null) { 
				if (functSymbol.isConstructor()) {
					errorReminder.error(node.getLoc(), 
						"returning a value from a constructor."
					);
				}
				else if (retType instanceof VoidType) {
					errorReminder.error(node.getLoc(), 
						"return-statement with a value, in function returning \'void\'."
					);
				}
				else if (exprType instanceof NullType) {
					if (!(retType instanceof ClassSymbol)) {
						errorReminder.error(node.getLoc(), 
							"return-statement with null, in function returning \'" + retType.toString() + "\'."	
						);
					}
				}
				else if(!retType.toString().equals(exprType.toString())){
					errorReminder.error(expr.getLoc(),
						"cannot convert \'" + exprType.toString() + "\' to \'" + retType.toString() + "\' in initialization."
					);
				}
			}
		}
		else {
			Type retType = functSymbol.getType();
			if (retType != null) {
				if (!(retType instanceof VoidType) && !functSymbol.isConstructor()) {
					errorReminder.error(node.getLoc(),
						"return-statement with no value, in function returning \'" + retType.toString() + "\'."
					);
				}	
			}
		}
		
	}
	
	@Override
	public void visit(VarDefStmtNode node) {
		node.setScope(currentScope);
		node.getVarDefList().accept(this);
	}
	
	//expression--------------------------------------------------
	@Override
	public void visit(ThisExprNode node) {
		node.setScope(currentScope);
		ClassSymbol classSymbol = currentScope.InClassSymbol();
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
		node.setScope(currentScope);
		VarSymbol var = currentScope.resolveVar(node, errorReminder);
		node.setSymbol(var);
		if (var != null) {
			node.setType(var.getType());
			node.setLvalue(true);
		}
	}	
	
	@Override
	public void visit(ArrayExprNode node) {
		node.setScope(currentScope);
		//check index.
		ExprNode indexExpr = node.getIndexExpr();
		if (indexExpr != null) {
			indexExpr.accept(this);
			Type indexType = indexExpr.getType();
			if (!(indexType instanceof IntType)) {
				errorReminder.error(indexExpr.getLoc(), "cannot convert \'" + indexType.toString() + "\' to \'int\'.");
			}
		}
		else {
			errorReminder.error(node.getLoc(), "empty index of array.");
		}
		
		ExprNode nameExpr = node.getNameExpr();
		if (nameExpr instanceof MemberExprNode) {
			node.setIdentifier(((MemberExprNode) nameExpr).getIdentifier());
			((MemberExprNode) nameExpr).setMemberExpr(node);
			nameExpr.accept(this);
			node.setType(nameExpr.getType());
			node.setLvalue(true);
		}
		else if (nameExpr instanceof VarExprNode){
			nameExpr.setScope(currentScope);
			node.setIdentifier(((VarExprNode) nameExpr).getIdentifier());
			VarSymbol varSymbol = currentScope.resolveArray(node, errorReminder);
			((VarExprNode)nameExpr).setSymbol(currentScope.resolveVar((VarExprNode) nameExpr, errorReminder));
			if (varSymbol != null) {
				node.setType(varSymbol.getType());
				node.setLvalue(true);
			}
		}
		else {
			nameExpr.accept(this);
			Type type = nameExpr.getType();
			if (type != null) {
				if (type instanceof ArrayType) {
					int dimension = ((ArrayType) type).getDimension();
					if (dimension == 1) {
						type = currentScope.resolveType(type.typeString());
					}
					else {
						type = new ArrayType(globalScope, type.typeString(), dimension - 1);
					}
					node.setType(type);
					node.setLvalue(true);
				}
				else {
					errorReminder.error(indexExpr.getLoc(), "invalid dimension.");
				}
			}
		} 
	}
	
	@Override
	public void visit(FunctExprNode node) {
		node.setScope(currentScope);
		//check parameters
		ArrayList<ExprNode> paraList = node.getParaList();
		for (ExprNode item : paraList) {
			item.accept(this);
		}
		
		ExprNode nameExpr = node.getNameExpr();
		if (nameExpr instanceof MemberExprNode) {
			node.setIdentifier(((MemberExprNode) nameExpr).getIdentifier());
			((MemberExprNode) nameExpr).setMemberExpr(node);
			nameExpr.accept(this);
			node.setType(nameExpr.getType());
			node.setLvalue(false);
		}
		else if (nameExpr instanceof VarExprNode) {
			node.setIdentifier(((VarExprNode) nameExpr).getIdentifier());
			FunctSymbol functSymbol = currentScope.resolveFunct(node, errorReminder);
			((VarExprNode)nameExpr).setSymbol(functSymbol);
			if (functSymbol != null) {
				node.setType(functSymbol.getType());
				node.setLvalue(false);
			}
		}
		else {
			errorReminder.error(node.getLoc(), "error in FunctExprNode!");
		}
	}
	
	@Override
	public void visit(MemberExprNode node) { 
		node.setScope(currentScope);
		//System.err.println("visit memberexpr.");
		ExprNode nameExpr = node.getNameExpr(), memberExpr = node.getMemberExpr();
		nameExpr.accept(this);
		Type type = nameExpr.getType();
		if (type != null) {
			if(!(type instanceof ClassSymbol)) {
				errorReminder.error(memberExpr.getLoc(), "invalid member call in a non-class type \'" + type.toString() + "\'.");
			}
			else {
				if (memberExpr instanceof VarExprNode) {
					VarSymbol varSymbol = ((ClassSymbol)type).findVar((VarExprNode)memberExpr, errorReminder);
					node.setSymbol(varSymbol);
					if (varSymbol != null) {
						node.setType(varSymbol.getType());
						node.setLvalue(true);
					}
				}
				else if (memberExpr instanceof ArrayExprNode) {
					VarSymbol arraySymbol = ((ClassSymbol)type).findArray((ArrayExprNode)memberExpr, errorReminder);
					node.setSymbol(((ClassSymbol) type).findArray(node.getIdentifier()));
					if (arraySymbol != null) {
						node.setType(arraySymbol.getType());
						node.setLvalue(true);
					}
				}
				else if (memberExpr instanceof FunctExprNode) {
					FunctSymbol functSymbol = ((ClassSymbol)type).findFunct((FunctExprNode)memberExpr, errorReminder);
					node.setSymbol(functSymbol);
					if (functSymbol != null) {
						node.setType(functSymbol.getType());
						node.setLvalue(false);
					}
				}
			}
		}
	}
	
	@Override
	public void visit(BracketExprNode node) {
		node.setScope(currentScope);
		ExprNode expr = node.getExpr();
		expr.accept(this);
		node.setType(expr.getType());
		node.setLvalue(expr.getLvalue());
	}
	
	@Override
	public void visit(CreatorExprNode node) {
		node.setScope(currentScope);
		ArrayList<ExprNode> indexList = node.getIndexList();
		for (ExprNode item : indexList) {
			item.accept(this);
			Type tmp = item.getType();
			if (tmp != null && !(tmp instanceof IntType)) {
				errorReminder.error(item.getLoc(), 
					"cannot convert \'" + tmp.toString() + "\' to \'int\' in initialization."	
				);
			}	
		}
		String tmp = node.getTypeNode().typeString();
		Type type = currentScope.resolveType(tmp);
		if (type != null) {
			int dimension = node.getDimension();
			if (dimension > 0) {
				if (tmp.equals("void")) {
					errorReminder.error(node.getTypeNode().getLoc(), "new expression cannot apply to void.");
				}
				node.setType(new ArrayType(globalScope, tmp, dimension));
			}
			else {
				if (tmp.equals("void")) {
					errorReminder.error(node.getTypeNode().getLoc(), "new expression cannot apply to void.");
				}
				else if (tmp.equals("bool")) {
					errorReminder.error(node.getTypeNode().getLoc(), "new expression cannot apply to bool.");
				}
				else if (tmp.equals("int")) {
					errorReminder.error(node.getTypeNode().getLoc(), "new expression cannot apply to int.");
				} 
				node.setType(type);
			}	
		}
		else {
			errorReminder.error(node.getLoc(), 
				"class \'" + tmp + "\' was not declared in this scope."
			);
		}
	}
	
	@Override
	public void visit(SuffixExprNode node) {
		node.setScope(currentScope);
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
		node.setScope(currentScope);
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
		node.setScope(currentScope);
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
						"no match for operator" + op.toString() + " between \'" + leftType.toString() + "\' and \'" + rightType.toString() + "\'."
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
		node.setScope(currentScope);
		node.setType(new BoolType());
		node.setLvalue(false);
	}
	
	@Override
	public void visit(IntLiteralNode node) { 
		node.setScope(currentScope);
		node.setType(new IntType());
		node.setLvalue(false);
	}
	
	@Override
	public void visit(StringLiteralNode node) {
		node.setScope(currentScope);
		node.setType(stringTemplate);
		node.setLvalue(false);
	}

	@Override
	public void visit(NullLiteralNode node) {
		node.setScope(currentScope);
		node.setType(new NullType());
		node.setLvalue(false);
	}
}
