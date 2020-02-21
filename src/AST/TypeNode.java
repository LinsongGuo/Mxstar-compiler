package AST;

import utility.Location;

public class TypeNode extends ASTNode {
	private String identifier;
	
	public TypeNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
	}
}

