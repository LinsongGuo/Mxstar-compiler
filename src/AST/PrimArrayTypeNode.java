package AST;

import utility.Location;

public class PrimArrayTypeNode extends ArrayTypeNode {
	public PrimArrayTypeNode(Location loc, String identifier, int dimension) {
		super(loc, identifier, dimension);
	}
}
