package AST;

import utility.Location;
import utility.Operator;

public class BinaryExprNode extends ExprNode {
	private Operator op;
	private ExprNode left, right;
	
	public BinaryExprNode(Location loc, Operator op, ExprNode left, ExprNode right) {
		super(loc);
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	public Operator getOp() {
		return op;
	}
	
	public ExprNode getLeft() {
		return left;
	}
	
	public ExprNode getRight() {
		return right;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
