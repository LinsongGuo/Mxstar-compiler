package AST;

import utility.Location;

public class ASTNode {
	private Location loc;
	
	public ASTNode(Location loc) {
		this.loc = loc;
	}
	
	public Location getLoc() {
		return loc;
	}
}
