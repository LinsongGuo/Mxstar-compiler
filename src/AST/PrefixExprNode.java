package AST;

import utility.Location;
import utility.Operator;

public class PrefixExprNode extends ExprNode {
	private Operator op;
	private ExprNode expr;
	
	public PrefixExprNode(Location loc, Operator op, ExprNode expr) {
		super(loc);
		this.op = op;
		this.expr = expr;
	}
	
	public ExprNode getExpr() {
		return expr;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
