package AST;

import utility.Location;

public class BreakStmtNode extends StmtNode {
	BreakStmtNode(Location loc) {
		super(loc);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
