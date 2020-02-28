package AST;

import utility.Location;

public class MemberExprNode extends ExprNode {
	private ExprNode expr;
	private VarExprNode varExpr;
	private FunctExprNode functExpr;
	private ArrayExprNode arrayExpr;
	
	public MemberExprNode(Location loc, ExprNode expr, VarExprNode varExpr) {
		super(loc);
		this.expr = expr;
		this.varExpr = varExpr;
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
	
	public VarExprNode getVarExpr() {
		return varExpr;
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
