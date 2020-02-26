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
	
	private void pushScope() {
		currentScope = new LocalScope(currentScope);
	}
	
	private void popScope() {
		currentScope = currentScope.getEnclosingScope();
	}

	public void visit(ProgramNode node){
		ArrayList<DefNode> defList = node.getDefList();
		for(DefNode item : defList) {
			item.accept(this);
		}
	}
	
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
	}
	
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
	public void visit(ExprNode node){;}
	public void visit(ArrayExprNode node){;}
	public void visit(BinaryExprNode node){;}
	public void visit(BracketExprNode node){;}
	public void visit(CreatorExprNode node){;}
	public void visit(FunctExprNode node){;}
	public void visit(LiteralExprNode node){;}
	public void visit(MemberExprNode node){;}
	public void visit(PrefixExprNode node){;}
	public void visit(SuffixExprNode node){;}
	public void visit(ThisExprNode node){;}
	public void visit(VarExprNode node){;}
	public void visit(StmtNode node){;}
	public void visit(BlockStmtNode node){;}
	public void visit(BrankStmtNode node){;}
	public void visit(BreakStmtNode node){;}
	public void visit(ContinueStmtNode node){;}
	public void visit(ExprStmtNode node){;}
	public void visit(ForStmtNode node){;}
	public void visit(IfStmtNode node){;}
	public void visit(ReturnStmtNode node){;}
	public void visit(VarDefStmtNode node){;}
	public void visit(WhileStmtNode node){;}
	public void visit(BoolLiteralNode node){;}
	public void visit(IntLiteralNode node){;}
	public void visit(StringLiteralNode node){;}
	
}
