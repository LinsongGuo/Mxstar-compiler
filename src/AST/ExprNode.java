package AST;

import utility.Location;

public class ExprNode extends ASTNode{
	public ExprNode(Location loc) {
		super(loc);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
