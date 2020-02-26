package AST;

import utility.Location;

public abstract class ExprNode extends ASTNode{
	public ExprNode(Location loc) {
		super(loc);
	}
	
	public abstract void accept(ASTVisitor visitor);
}
