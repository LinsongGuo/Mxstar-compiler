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
	/*
	private void pushScope() {
		currentScope = new LocalScope(currentScope);
	}
	
	private void popScope() {
		currentScope = currentScope.getEnclosingScope();
	}
	*/
	
	public void visit(ProgramNode node){
		ArrayList<DefNode> defList = node.getDefList();
		for(DefNode item : defList) {
			item.accept(this);
		}
	}
	
	/*
	public void visit(DefNode node){
		if (node instanceof VarDefListNode) {
			((VarDefListNode)node).accept(this);
		} 
		else if (node instanceof FunctDefNode) {
			((FunctDefNode)node).accept(this);
		}
		else if (node instanceof ClassDefNode) {
			((ClassDefNode)node).accept(this);
		}
	}*/
	
	public void visit(VarDefListNode node){
		currentScope.defineVarList((VarDefListNode)node, errorReminder);
	}
	
	public void visit(FunctDefNode node){
		currentScope = currentScope.defineFunct(node, errorReminder);
		currentScope.defineParaList(node.getParaList(), errorReminder);
		((FunctDefNode) node).getBlockStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
	}
	
	public void visit(ClassDefNode node){
		currentScope.defineClass(node, errorReminder);
		//define variables in class.
		ArrayList<VarDefListNode> varList = node.getVarList();
		for (VarDefListNode item : varList) {
			currentScope.defineVarList(item, errorReminder);
		}
		//define constructor in class.
		FunctDefNode constructorDef = node.getConstructorDef();
		if (constructorDef != null) {
			currentScope = currentScope.defineFunct(constructorDef, errorReminder);
			constructorDef.getBlockStmt().accept(this);
			currentScope = currentScope.getEnclosingScope();
		}
		//define functions in class.
		ArrayList<FunctDefNode> functList = node.getFunctList();
		for (FunctDefNode item : functList) {
			currentScope = currentScope.defineFunct(item, errorReminder);
			currentScope.defineParaList(item.getParaList(), errorReminder);
			item.getBlockStmt().accept(this);
			currentScope = currentScope.getEnclosingScope();
		}
	}
	

	public void visit(VarDefNode node){;}
	
	public void visit(TypeNode node){;}
	public void visit(PrimTypeNode node){;}
	public void visit(ClassTypeNode node){;}
	public void visit(ArrayTypeNode node){;}

	//public void visit(StmtNode node) {;}
	
	public void visit(BlockStmtNode node) {
		ArrayList<StmtNode> stmtList = node.getStmtList();
		for (StmtNode item : stmtList) {
			item.accept(this);
		}
	}
	
	public void visit(BrankStmtNode node) {
		;
	}
	
	public void visit(BreakStmtNode node) { 
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"The break statement must be in one loop."	
			);
		}
	}
	
	public void visit(ContinueStmtNode node) { 
		if (!currentScope.inLoopScope()) {
			errorReminder.error(node.getLoc(), 
				"The continue statement must be in one loop."	
			);
		}
	}
	
	public void visit(ExprStmtNode node) {;
		node.accept(this);
	}
	
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
	
	public void visit(VarDefStmtNode node) {
		node.getVarDefList().accept(this);
	}
	
	public void visit(WhileStmtNode node) {
		currentScope = new LocalScope(currentScope, ScopeType.LoopScope);
		node.getExpr().accept(this);
		node.getStmt().accept(this);
		currentScope = currentScope.getEnclosingScope();
	}
	
	public void visit(BoolLiteralNode node) {;}
	public void visit(IntLiteralNode node) {;}
	public void visit(StringLiteralNode node) {;}
	
	//public void visit(ExprNode node){;}
	public void visit(ArrayExprNode node) {
		Symbol var = currentScope.resovleVar(node.getLoc(), node.getIdentifier(), node.getDimension(), errorReminder);
		ArrayList<ExprNode> indexExpr = node.getIndexExpr();
		for (ExprNode item : indexExpr) {
			item.accept(this);
		}
	}
	
	public void visit(BinaryExprNode node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
	}
	
	public void visit(BracketExprNode node) {
		node.getExpr().accept(this);
	}
	
	public void visit(CreatorExprNode node) {;}
	
	public void visit(FunctExprNode node) {
		
	}
	
	public void visit(LiteralExprNode node) {
		;
	}
	
	public void visit(MemberExprNode node) { 
		;
	}
	
	public void visit(PrefixExprNode node) {
		node.getExpr().accept(this);
	}
	
	public void visit(SuffixExprNode node) {
		node.getExpr().accept(this);
	}
	
	public void visit(ThisExprNode node) {
		if (!currentScope.inClassScope()) {
			errorReminder.error(node.getLoc(), 
				"The \"This\" expression must be in the scope of one class."
			);
		}
	}
	
	public void visit(IdentifierExprNode node) {
		Symbol var = currentScope.resovleVar(node.getLoc(), node.getIdentifier(), 0, errorReminder);
	}	
}
