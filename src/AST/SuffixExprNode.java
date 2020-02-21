package AST;

import utility.Location;
import utility.Operator;

public class SuffixExprNode extends ExprNode {
	private Operator op;
	private ExprNode expr;
	
	public SuffixExprNode(Location loc, Operator op, ExprNode expr) {
		super(loc);
		this.op = op;
		this.expr = expr;
	}
}
