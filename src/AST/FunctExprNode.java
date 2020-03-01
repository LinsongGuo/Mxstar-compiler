package AST;

import utility.Location;
import java.util.ArrayList;

public class FunctExprNode extends ExprNode {
	private ExprNode nameExpr;
	private ArrayList<ExprNode> paraList;
	
	public FunctExprNode(Location loc, ExprNode nameExpr, ArrayList<ExprNode> paraList) {
		super(loc);
		this.nameExpr = nameExpr;
		this.paraList = paraList;
	}
	
	public ExprNode getNameExpr() {
		return nameExpr;
	}
	
	public ArrayList<ExprNode> getParaList() {
		return paraList;
	}
	
	public String getIdentifier() {
		return ((VarExprNode)nameExpr).getIdentifier();
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
