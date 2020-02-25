package AST;

import utility.Location;

public class ClassTypeNode extends TypeNode {
	public ClassTypeNode(Location loc, String identifier) {
		super(loc, identifier);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
