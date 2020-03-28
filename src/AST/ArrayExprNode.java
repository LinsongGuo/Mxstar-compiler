package AST;

import Scope.Symbol;
import utility.Location;

public class ArrayExprNode extends ExprNode {
	private ExprNode nameExpr, indexExpr;
	private String identifier;

	public ArrayExprNode(Location loc, ExprNode nameExpr, ExprNode indexExpr) {
		super(loc);
		this.nameExpr = nameExpr;
		this.indexExpr = indexExpr;
		//this.symbol = null;
	}
	
	public ArrayExprNode(Location loc, String identifier, ExprNode indexExpr) {
		super(loc);
		this.identifier = identifier;
		this.indexExpr = indexExpr;
		this.nameExpr = null;
	}
	
	public ExprNode getNameExpr() {
		return nameExpr;
	}
	
	public ExprNode getIndexExpr() {
		return indexExpr;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	/*
	private Symbol symbol;
	
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
	*/
}
