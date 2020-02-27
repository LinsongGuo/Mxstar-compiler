package Scope;

public abstract class Symbol {
	protected String identifier;
	
	public Symbol(String identifier) {
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}

	public abstract boolean isVar();
	
	public abstract boolean isFunct();
}
