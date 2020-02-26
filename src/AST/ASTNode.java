package AST;

import utility.Location;

abstract public class ASTNode {
	private Location loc;
	
	public ASTNode(Location loc) {
		this.loc = loc;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public abstract void accept(ASTVisitor visitor);
}
