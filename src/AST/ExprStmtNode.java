package AST;

import utility.Location;

public class ExprStmtNode extends StmtNode {
	private ExprNode expr;
	
	public ExprStmtNode(Location loc, ExprNode expr) {
		super(loc);
		this.expr = expr;
	}
	
	public ExprNode getExpr() {
		return expr;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
