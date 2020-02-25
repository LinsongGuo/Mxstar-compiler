package SemanticChecker;

import AST.*;
import Scope.*;
import utility.*;

public class SemanticChecker implements ASTVisitor {
	Scope currentScope;
	ErrorReminder errorReminder;
	
	public SemanticChecker(ErrorReminder errorReminder) {
		this.errorReminder = errorReminder;
	}
	
	private void pushScope() {
		currentScope = new LocalScope(currentScope);
	}
	
	private void popScope() {
		currentScope = currentScope.getEnclosingScope();
	}

	public void visit(ProgramNode node){;}
	public void visit(DefNode node){;}
	public void visit(VarDefListNode node){;}
	public void visit(FunctDefNode node){;}
	public void visit(ClassDefNode node){;}
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
