package AST;

import utility.Location;

public class BrankStmtNode extends StmtNode {
	public BrankStmtNode(Location loc) {
		super(loc);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
