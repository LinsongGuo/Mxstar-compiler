package AST;

import utility.Location;

public class BoolLiteralNode extends LiteralExprNode {
	private boolean bool;
	
	public BoolLiteralNode(Location loc, boolean bool) {
		super(loc);
		this.bool = bool;
	}
}
