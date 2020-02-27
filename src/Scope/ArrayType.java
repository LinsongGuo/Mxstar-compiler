package Scope;

public class ArrayType extends Symbol implements Type {

	private int dimension;
	
	public String typeToString() {
		return identifier;
	}
	
	public ArrayType(String identifier, int dimension) {
		super(identifier);
		this.dimension = dimension;
	}
	
	public int getDimension() {
		return dimension;
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
