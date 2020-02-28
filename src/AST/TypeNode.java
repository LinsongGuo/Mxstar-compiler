package AST;

import utility.Location;

public abstract class TypeNode extends ASTNode {
	protected String identifier;
	
	public TypeNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
	}
	
	public abstract String toString();
	
	public String typeString() {
		return identifier;
	}
	
	public abstract void accept(ASTVisitor visitor);
}

