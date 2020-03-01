package AST;

import utility.Location;

public class NullLiteralNode extends LiteralExprNode {
	public NullLiteralNode(Location loc) {
		super(loc);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
