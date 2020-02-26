package AST;

import utility.Location;

public class IdentifierExprNode extends ExprNode {
	private String identifier;
	
	public IdentifierExprNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
	}	
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
