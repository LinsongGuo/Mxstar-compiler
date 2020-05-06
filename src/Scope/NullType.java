package Scope;

public class NullType extends Symbol implements Type {
	
	public NullType() {
		super("null");
	}
	
	@Override
	public String toString() {
		return new String("null");
	}
	
	@Override
	public String typeString() {
		return new String("null");
	}
	
	@Override
	public boolean isVar() {
		return false;
	}
	
	@Override
	public boolean isFunct() {
		return false;
	}
	
	@Override
	public boolean isBuiltInType() {
		return false;
	}

	/*
	@Override
	public IRType toIRType() {
		// TODO Auto-generated method stub
		return null;
	}*/
}
