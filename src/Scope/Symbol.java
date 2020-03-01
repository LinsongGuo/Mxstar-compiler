package Scope;

public abstract class Symbol {
	protected String identifier;
	private boolean defined;
	
	public Symbol(String identifier) {
		this.identifier = identifier;
	}
	
	public boolean definedOrNot() {
		return defined;
	}
	
	public void beenDefined() {
		defined = true;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}

	public abstract boolean isVar();
	
	public abstract boolean isFunct();
}
