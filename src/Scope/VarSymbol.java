package Scope;

public class VarSymbol extends Symbol{
	protected Type type;
	
	public VarSymbol(String identifier, Type type) {
		super(identifier);
		this.type = type;
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
}
