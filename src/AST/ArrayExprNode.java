package AST;

import utility.Location;
import java.util.ArrayList;

public class ArrayExprNode extends ExprNode {
	private ExprNode nameExpr, indexExpr;
	
	public ArrayExprNode(Location loc, ExprNode nameExpr, ExprNode indexExpr) {
		super(loc);
		this.nameExpr = nameExpr;
		this.indexExpr = indexExpr;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
