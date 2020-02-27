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
	
	public ExprNode getExpr() {
		return expr;
	}
	
	public Operator getOp() {
		return op;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	/*
	public String opToString() {
		if (op == Operator.suffixINCR)
			return new String("++");
		else if (op == Operator.suffixDECR)
			return new String("--");
		else 
			return new String("");
	}
	*/
}
