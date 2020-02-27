package AST;

import utility.Location;
import java.util.ArrayList;

public class ArrayExprNode extends ExprNode {
	private String identifier;
	private ArrayList<ExprNode> indexExpr;
	
	public ArrayExprNode(Location loc, String identifier, ArrayList<ExprNode> indexExpr) {
		super(loc);
		this.identifier = identifier;
		this.indexExpr = indexExpr;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ArrayList<ExprNode> getIndexExpr() {
		return indexExpr;
	}
	
	public int getDimension() {
		return indexExpr.size();
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
