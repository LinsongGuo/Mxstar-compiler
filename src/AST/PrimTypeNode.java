package AST;

import utility.Location;

public class PrimTypeNode extends TypeNode {
	public PrimTypeNode(Location loc, String identifier) {
		super(loc, identifier);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
