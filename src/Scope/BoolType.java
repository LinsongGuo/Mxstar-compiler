package Scope;

public class BoolType extends Symbol implements Type {
	
	public BoolType() {
		super("bool");
	}
	
	@Override
	public String toString() {
		return new String("bool");
	}
	
	@Override
	public String typeString() {
		return new String("bool");
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
		return true;
	}
}
