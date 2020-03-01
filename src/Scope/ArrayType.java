package Scope;

public class ArrayType extends ClassSymbol implements Type {
	private int dimension;
	
	public ArrayType(Scope parent, String identifier, int dimension) {
		super(parent, identifier);
		this.dimension = dimension;
		
		functList.put("size", new FunctSymbol(parent, "size", new IntType()));
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
