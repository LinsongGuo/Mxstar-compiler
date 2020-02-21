package AST;

import utility.Location;

public class ArrayTypeNode extends TypeNode {
	private int dimension;
	
	public ArrayTypeNode(Location loc, String identifier, int dimension) {
		super(loc, identifier);
		this.dimension = dimension;
	}
}
