package AST;

import utility.Location;

public class StmtNode extends ASTNode {
	public StmtNode(Location loc) {
		super(loc);
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
