package Scope;

public class ArrayType extends Symbol implements Type {

	private int dimension;
	
	public ArrayType(String identifier, int dimension) {
		super(identifier);
		this.dimension = dimension;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	@Override
	public String toString() {
		String res = identifier;
		for (int i = 0; i < dimension; ++i) {
			res += "[]";
		}
		return res;
	}
	
	@Override
	public String typeString() {
		return identifier;
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
}
