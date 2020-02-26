package AST;

import utility.Location;

public class BracketExprNode extends ExprNode {
	public ExprNode expr;
	
	public BracketExprNode(Location loc, ExprNode expr) {
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
