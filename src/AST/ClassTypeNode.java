package AST;

import utility.Location;

public class ClassTypeNode extends TypeNode {
	private String identifier;
	
	public ClassTypeNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
	}
}
