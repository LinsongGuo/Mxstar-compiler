package Scope;

public class VarSymbol extends Symbol{
	protected Type type;
	
	public VarSymbol(String identifier, Type type) {
		super(identifier);
		this.type = type;
	}
}
