package AST;

import utility.Location;

public class MemberExprNode extends ExprNode {
	private ExprNode expr;
	private String identifier;
	private FunctExprNode functExpr;
	private ArrayExprNode arrayExpr;
	
	public MemberExprNode(Location loc, ExprNode expr, String identifier) {
		super(loc);
		this.expr = expr;
		this.identifier = identifier;
		this.functExpr = null;
		this.arrayExpr = null;
	}
	
	public MemberExprNode(Location loc, ExprNode expr, FunctExprNode functExpr) {
		super(loc);
		this.expr = expr;
		this.functExpr = functExpr;
		this.arrayExpr = null;
	}
	
	public MemberExprNode(Location loc, ExprNode expr, ArrayExprNode arrayExpr) {
		super(loc);
		this.expr = expr;
		this.functExpr = null;
		this.arrayExpr = arrayExpr;
	}
	
	public ExprNode getExpr() {
		return expr;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public FunctExprNode getFunctExpr() {
		return functExpr;
	}
	
	public ArrayExprNode getArrayExpr() {
		return arrayExpr;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
