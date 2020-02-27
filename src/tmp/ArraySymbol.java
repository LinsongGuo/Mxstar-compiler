package Scope;

import AST.ArrayExprNode;

public class ArraySymbol extends VarSymbol{
	private int dimension;
	
	public ArraySymbol(String identifier, Type type, int dimension) {
		super(identifier, type);
		this.dimension = dimension;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public boolean matchDimension(ArrayExprNode node) {
		return dimension == node.getDimension();
	}
}
