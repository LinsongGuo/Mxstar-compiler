package AST;

import utility.Location;

public class StringLiteralNode extends LiteralExprNode {
	private String string;
	
	public StringLiteralNode(Location loc, String string) {
		super(loc);
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
