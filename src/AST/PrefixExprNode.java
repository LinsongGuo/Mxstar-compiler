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
	
	public Operator getOp() {
		return op;
	}
	
	/*
	public String opToString() {
		if (op == Operator.POS)
			return new String("+");
		else if (op == Operator.NEG)
			return new String("-");
		else if (op == Operator.prefixINCR)
			return new String("++");
		else if (op == Operator.prefixDECR)
			return new String("--");
		else if (op == Operator.bitwiseNOT)
			return new String("~");
		else if (op == Operator.logicalNOT)
			return new String("!");
		else 
			return new String("");
	}
	*/
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
