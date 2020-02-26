package AST;

import utility.Location;
import java.util.ArrayList;

public class ArrayExprNode extends ExprNode {
	private ExprNode nameExpr;
	private ArrayList<ExprNode> indexExpr;
	
	public ArrayExprNode(Location loc, ExprNode nameExpr, ArrayList<ExprNode> indexExpr) {
		super(loc);
		this.nameExpr = nameExpr;
		this.indexExpr = indexExpr;
	}
	
	public String getIdentifier() {
		return ((IdentifierExprNode)nameExpr).getIdentifier();
	}
	
	public ArrayList<ExprNode> getIndexExpr() {
		return indexExpr;
	}
	
	public int getDimension() {
		return indexExpr.size();
	}
	
	public ExprNode getNameExpr() {
		return nameExpr;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
