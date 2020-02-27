package Scope;

public class StringType extends Symbol implements Type {
	
	public StringType() {
		super("string");
	}
	
	@Override
	public String toString() {
		return new String("string");
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
