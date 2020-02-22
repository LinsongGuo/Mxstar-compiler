package AST;

import utility.Location;

public class VarDefNode extends ASTNode{
	private TypeNode type;
	private String identifier;
	private ExprNode initValue;
	
	public VarDefNode(Location loc, TypeNode type, String identifier, ExprNode initValue) {
		super(loc);
		this.type = type;
		this.identifier = identifier;
		this.initValue = initValue;
	}
	
	public void setType(TypeNode type) {
		this.type = type;
	}
}
