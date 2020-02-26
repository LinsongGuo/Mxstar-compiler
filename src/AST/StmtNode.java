package AST;

import utility.Location;

public abstract class StmtNode extends ASTNode {
	public StmtNode(Location loc) {
		super(loc);
	}
	
	public abstract void accept(ASTVisitor visitor);
}
