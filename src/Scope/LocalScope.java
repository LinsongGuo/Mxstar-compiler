package Scope;


public class LocalScope extends BaseScope {
	
	public LocalScope(Scope parent) {
		super(parent);
	}

	@Override
	public Type resolveType(String identifier) {
		Scope parent = getEnclosingScope();
		if (parent != null) 
			return parent.resolveType(identifier);
		return null;
	}
	
}
