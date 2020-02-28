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
	
	public TypeNode getType() {
		return type;
	}
	
	public String getTypeIdentifier() {
		return type.toString();
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public boolean typeEquals(VarDefNode other) {
		return type.identifierEquals(other.type);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
