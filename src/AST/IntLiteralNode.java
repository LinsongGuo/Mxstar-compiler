package AST;

import utility.Location;

public class IntLiteralNode extends LiteralExprNode {
	private long value;
	
	public IntLiteralNode(Location loc, long value) {
		super(loc);
		this.value = value;
	}
	
	public long getValue() {
		return value;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
