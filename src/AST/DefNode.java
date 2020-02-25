package AST;

import utility.Location;

public class DefNode extends ASTNode {
	public DefNode(Location loc) {
		super(loc);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
