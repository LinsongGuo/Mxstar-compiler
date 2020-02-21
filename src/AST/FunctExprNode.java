package AST;

import utility.Location;
import java.util.ArrayList;

public class FunctExprNode extends ExprNode {
	private ExprNode funct;
	private ArrayList<ExprNode> paraList;
	
	public FunctExprNode(Location loc, ExprNode funct, ArrayList<ExprNode> paraList) {
		super(loc);
		this.funct = funct;
		this.paraList = paraList;
	}
}
