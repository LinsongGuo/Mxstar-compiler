package AST;

import utility.Location;

public class TypeNode extends ASTNode {
	protected String identifier;
	
	public TypeNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public boolean identifierEquals(TypeNode other) {
		return identifier.equals(other.identifier);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}

