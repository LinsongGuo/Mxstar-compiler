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
	
	/*
	public String opToString() {
		if (op == Operator.MUL)
			return new String("*");
		else if (op == Operator.DIV)
			return new String("/");
		else if (op == Operator.MOD) 
			return new String("%");
		else if (op == Operator.ADD)
			return new String("+");
		else if (op == Operator.SUB)
			return new String("-");
		else if (op == Operator.leftSHIFT)
			return new String("<<");
		else if (op == Operator.rightSHIFT)
			return new String(">>");
		else if (op == Operator.LESS)
			return new String("<");
		else if (op == Operator.GREATER)
			return new String(">");
		else if (op == Operator.lessEQU)
			return new String("<=");
		else if (op == Operator.greaterEQU)
			return new String(">=");
		else if (op == Operator.EQU)
			return new String("==");
		else if (op == Operator.notEQU)
			return new String("!=");
		else if (op == Operator.bitwiseAND)
			return new String("&");
		else if (op == Operator.bitwiseXOR)
			return new String("^");
		else if (op == Operator.bitwiseOR)
			return new String("|");
		else if (op == Operator.logicalAND)
			return new String("&&");
		else if (op == Operator.logicalOR)
			return new String("||");
		else if (op == Operator.ASSIGN)
			return new String("=");
		else 
			return new String("");
	}
	*/
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
