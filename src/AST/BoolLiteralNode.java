package AST;

import utility.Location;

public class BoolLiteralNode extends LiteralExprNode {
	private boolean value;
	
	public BoolLiteralNode(Location loc, boolean value) {
		super(loc);
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
