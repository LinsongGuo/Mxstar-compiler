package AST;

import utility.Location;

public class ForStmtNode extends StmtNode {
	private ExprNode init, cond, step;
	private StmtNode stmt;
	
	public ForStmtNode(Location loc, ExprNode init, ExprNode cond, ExprNode step, StmtNode stmt) {
		super(loc);
		this.init = init;
		this.cond = cond;
		this.step = step;
		this.stmt = stmt;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
