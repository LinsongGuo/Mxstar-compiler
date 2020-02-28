package Scope;

public class IntType extends Symbol implements Type {
	
	public IntType() {
		super("int");
	}
	
	@Override
	public String toString() {
		return new String("int");
	}
	
	@Override
	public String typeString() {
		return new String("int");
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
