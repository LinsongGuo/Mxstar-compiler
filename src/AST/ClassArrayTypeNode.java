package AST;

import utility.Location;

public class ClassArrayTypeNode extends ArrayTypeNode {
	public ClassArrayTypeNode(Location loc, String identifier, int dimension) {
		super(loc, identifier, dimension);
	}
}
