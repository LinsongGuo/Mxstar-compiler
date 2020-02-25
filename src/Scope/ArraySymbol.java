package Scope;

public class ArraySymbol extends VarSymbol{
	private int dimension;
	
	public ArraySymbol(String identifier, Type type, int dimension) {
		super(identifier, type);
		this.dimension = dimension;
	}
}
