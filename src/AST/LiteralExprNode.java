package AST;

import utility.Location;

public class LiteralExprNode extends ExprNode {
	LiteralExprNode(Location loc) {
		super(loc);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
