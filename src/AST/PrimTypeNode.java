package AST;

import utility.Location;

public class PrimTypeNode extends TypeNode {
	private String identifier;
	
	public PrimTypeNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
	}
}
