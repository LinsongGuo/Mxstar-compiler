package AST;

import utility.Location;

public abstract class LiteralExprNode extends ExprNode {
	
	LiteralExprNode(Location loc) {
		super(loc);
	}
	
	public abstract void accept(ASTVisitor visitor);
}
