package Scope;

import AST.ExprNode;
import IR.Symbol.IRRegister;
import IR.Type.IRType;
import utility.ErrorReminder;

public class VarSymbol extends Symbol{
	private Type type;
	private Scope scope;
	
	public VarSymbol(String identifier, Type type, Scope scope) {
		super(identifier);
		this.type = type;
		this.scope = scope;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public boolean isVar() {
		return true;
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
	
	public void checkInitValue(ExprNode initValue, ErrorReminder errorReminder) {
		Type initType = initValue.getType();
		if (initType != null) {
			if (initType instanceof NullType) {
				if (!(type instanceof ClassSymbol) || (type instanceof StringType)) {
					errorReminder.error(initValue.getLoc(), 
						"\'" + type.toString() + "\' cannot be assigned to \'null\'."
					);
				}
			}
			else if (!initType.toString().equals(type.toString())) {
				errorReminder.error(initValue.getLoc(), 
					"cannot convert \'" + initType.toString() + "\' to \'" + type.toString() + "\' in initialization."
				);
			}
		}
	}
	
	//for IR
	private IRRegister address;
	private IRType IRtype;
	
	public void setAddress(IRRegister address) {
		this.address = address;
	}
	
	public IRRegister toIRAddress() {
		return address;
	}
	
	public void setIRType(IRType IRtype) {
		this.IRtype = IRtype;
	}
	
	public IRType getIRType() {
		return IRtype;
	}
	
	public void setScope(Scope scope) {
		this.scope = scope;
	}
	
	public Scope getScope() {
		return scope;
	}
}
