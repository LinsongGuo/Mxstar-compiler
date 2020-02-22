package AST;

import utility.Location;

public class ArrayTypeNode extends TypeNode {
	private TypeNode type;
	private int dimension;
	
	public ArrayTypeNode(Location loc, TypeNode type, int dimension) {
		super(loc);
		this.type = type;
		this.dimension = dimension;
	}
}
