package AST;

import java.util.ArrayList;

import utility.Location;

public class CreatorExprNode extends ExprNode {
	private TypeNode type;
	private ArrayList<ExprNode> indexList;
	private int dimension;
	
	CreatorExprNode(Location loc, TypeNode type, ArrayList<ExprNode> indexList, int dimension) {
		super(loc);
		this.type = type;
		this.indexList = indexList;
		this.dimension = dimension;
	}
	
	public ArrayList<ExprNode> getIndexList() {
		return indexList;
	}
	
	public TypeNode getTypeNode() {
		return type;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
