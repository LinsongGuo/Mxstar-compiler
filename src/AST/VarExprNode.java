package AST;

import Scope.Symbol;
import utility.Location;

public class VarExprNode extends ExprNode {
	private String identifier;
	
	public VarExprNode(Location loc, String identifier) {
		super(loc);
		this.identifier = identifier;
		this.symbol = null;
	}	
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	private Symbol symbol;
	
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
}
