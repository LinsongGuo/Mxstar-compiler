package AST;

import utility.Location;

public class ArrayTypeNode extends TypeNode {
	private int dimension;
	
	public ArrayTypeNode(Location loc, String identifier, int dimension) {
		super(loc, identifier);
		this.dimension = dimension;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
