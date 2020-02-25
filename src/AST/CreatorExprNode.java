package AST;

import java.util.ArrayList;
import utility.Location;

public class CreatorExprNode extends ExprNode {
	private TypeNode type;
	private ArrayList<ExprNode> sizeList;
	private int dimension;
	CreatorExprNode(Location loc, TypeNode type, ArrayList<ExprNode> sizeList, int dimension) {
		super(loc);
		this.type = type;
		this.sizeList = sizeList;
		this.dimension = dimension;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
