package AST;

import utility.Location;

public class VarDefNode extends ASTNode{
	private TypeNode type;
	private String identifier;
	private ExprNode expr;
	
	public VarDefNode(Location loc, TypeNode type, String identifier, ExprNode expr) {
		super(loc);
		this.type = type;
		this.identifier = identifier;
		this.expr = expr;
	}
	
	public void setType(TypeNode type) {
		this.type = type;
	}
}
