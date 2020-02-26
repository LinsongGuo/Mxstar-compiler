package AST;

import utility.Location;

public class IfStmtNode extends StmtNode {
	private ExprNode cond;
	private StmtNode thenStmt, elseStmt;
	
	public IfStmtNode(Location loc, ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
		super(loc);
		this.cond = cond;
		this.thenStmt = thenStmt;
		this.elseStmt = elseStmt;
	}
	
	public ExprNode getCond() {
		return cond;
	}
	
	public StmtNode getThenStmt() {
		return thenStmt;
	}
	
	public StmtNode getElseStmt() {
		return elseStmt;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
