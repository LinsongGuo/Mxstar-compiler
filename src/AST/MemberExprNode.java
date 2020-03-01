package AST;

import utility.Location;

public class MemberExprNode extends ExprNode {
	private ExprNode nameExpr, memberExpr;
	
	public MemberExprNode(Location loc, ExprNode nameExpr, ExprNode memberExpr) {
		super(loc);
		this.nameExpr = nameExpr;
		this.memberExpr = memberExpr;
	}
	
	public ExprNode getExpr() {
		return nameExpr;
	}
	
	public ExprNode getMemberExpr() {
		return memberExpr;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}

