package AST;

import utility.Location;

public abstract class DefNode extends ASTNode {
	public DefNode(Location loc) {
		super(loc);
	}
	
	public abstract void accept(ASTVisitor visitor);
}
