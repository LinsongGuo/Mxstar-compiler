package AST;

public interface ASTVisitor {
	public abstract void visit(ProgramNode node);

	//public abstract void visit(DefNode node);
	public abstract void visit(VarDefListNode node);
	public abstract void visit(FunctDefNode node);
	public abstract void visit(ClassDefNode node);
	public abstract void visit(VarDefNode node);

//	public abstract void visit(TypeNode node);
	public abstract void visit(PrimTypeNode node);
	public abstract void visit(ClassTypeNode node);
	public abstract void visit(ArrayTypeNode node);

	//public abstract void visit(ExprNode node);
	public abstract void visit(ArrayExprNode node);
	public abstract void visit(BinaryExprNode node);
	public abstract void visit(BracketExprNode node);
	public abstract void visit(CreatorExprNode node);
	public abstract void visit(FunctExprNode node);
	//public abstract void visit(LiteralExprNode node);
	public abstract void visit(MemberExprNode node);
	public abstract void visit(PrefixExprNode node);
	public abstract void visit(SuffixExprNode node);
	public abstract void visit(ThisExprNode node);
	public abstract void visit(VarExprNode node);
 
	//public abstract void visit(StmtNode node);
	public abstract void visit(BlockStmtNode node);
	public abstract void visit(BrankStmtNode node);
	public abstract void visit(BreakStmtNode node);
	public abstract void visit(ContinueStmtNode node);
	public abstract void visit(ExprStmtNode node);
	public abstract void visit(ForStmtNode node);
	public abstract void visit(IfStmtNode node);
	public abstract void visit(ReturnStmtNode node);
	public abstract void visit(VarDefStmtNode node);
	public abstract void visit(WhileStmtNode node);

	public abstract void visit(BoolLiteralNode node);
	public abstract void visit(IntLiteralNode node);
	public abstract void visit(StringLiteralNode node);
	public abstract void visit(NullLiteralNode node);
}
