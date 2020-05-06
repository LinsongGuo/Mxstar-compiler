package AST;

import Scope.Symbol;
import utility.Location;

public class MemberExprNode extends ExprNode {
	private ExprNode nameExpr, memberExpr;
	private String identifier;
	
	public MemberExprNode(Location loc, ExprNode nameExpr, String identifier, ExprNode memberExpr) {
		super(loc);
		this.nameExpr = nameExpr;
		this.identifier = identifier;
		this.memberExpr = memberExpr;
		this.symbol = null;
	}
	
	public ExprNode getNameExpr() {
		return nameExpr;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ExprNode getMemberExpr() {
		return memberExpr;
	}
	
	public void setMemberExpr(ExprNode memberExpr) {
		this.memberExpr = memberExpr;
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

