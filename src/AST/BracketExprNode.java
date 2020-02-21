package AST;

import utility.Location;

public class BracketExprNode extends ExprNode {
	public ExprNode subexpr;
	
	public BracketExprNode(Location loc, ExprNode subexpr) {
		super(loc);
		this.subexpr = subexpr;
	}
}
