package AST;

import Scope.VarSymbol;

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

	public String getIdentifier() {
		return identifier;
	}
	
	public ExprNode getInitValue() {
		return initValue;
	}
	
	public TypeNode getType() {
		return type;
	}
	
	public void setType(TypeNode type) {
		this.type = type;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	private VarSymbol varSymbol;
	
	public void setVarSymbol(VarSymbol varSymbol) {
		this.varSymbol = varSymbol;
	}
	
	public VarSymbol getVarSymbol() {
		return varSymbol;
	}

}
