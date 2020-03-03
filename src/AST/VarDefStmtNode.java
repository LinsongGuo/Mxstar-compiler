package AST;

import utility.Location;

public class VarDefStmtNode extends StmtNode {
	private VarDefListNode varDefList;
	public VarDefStmtNode(Location loc, VarDefListNode varDefList) {
		super(loc);
		this.varDefList = varDefList;
	}
	
	public VarDefListNode getVarDefList() {
		return varDefList;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
