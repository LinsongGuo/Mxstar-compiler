package AST;

import utility.Location;

public class WhileStmtNode extends StmtNode {
	private ExprNode expr;
	private StmtNode stmt;
	
	public WhileStmtNode(Location loc, ExprNode expr, StmtNode stmt) {
		super(loc);
		this.expr = expr;
		this.stmt = stmt;
	}
}
