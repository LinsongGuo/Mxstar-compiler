package AST;

import utility.Location;

public class ContinueStmtNode extends StmtNode {
	public ContinueStmtNode(Location loc) {
		super(loc);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
