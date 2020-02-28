package AST;

import utility.Location;

public class ClassTypeNode extends TypeNode {
	public ClassTypeNode(Location loc, String identifier) {
		super(loc, identifier);
	}
	
	@Override
	public String toString() {
		return identifier;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
