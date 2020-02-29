package AST;

import Scope.Type;
import utility.Location;

public class IntLiteralNode extends LiteralExprNode {
	private long integer;
	
	public IntLiteralNode(Location loc, long integer) {
		super(loc);
		this.integer = integer;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
