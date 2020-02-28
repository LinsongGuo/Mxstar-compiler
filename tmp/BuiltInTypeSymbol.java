package Scope;

public class BuiltInTypeSymbol extends Symbol implements Type {
	
	public BuiltInTypeSymbol(String identifier) {
		super(identifier);
	}
	
	@Override
	public boolean isVar() {
		return false;
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
}
