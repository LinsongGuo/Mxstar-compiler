package AST;

import utility.Location;

public class IntLiteralNode extends LiteralExprNode {
	private int integer;
	
	public IntLiteralNode(Location loc, int integer) {
		super(loc);
		this.integer = integer;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
