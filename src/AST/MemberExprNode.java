package AST;

import utility.Location;

public class MemberExprNode extends ExprNode {
	private ExprNode expr;
	private String identifier;
	
	public MemberExprNode(Location loc, ExprNode expr, String identifier) {
		super(loc);
		this.expr = expr;
		this.identifier = identifier;
	}
}
