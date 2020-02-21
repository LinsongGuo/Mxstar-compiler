package AST;

import utility.Location;

public class VarExprNode extends ExprNode {
	private String identifier;
	
	public VarExprNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
	}	
}
