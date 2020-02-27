package AST;

import utility.Location;
import java.util.ArrayList;

public class FunctExprNode extends ExprNode {
	private String identifier;
	private ArrayList<ExprNode> paraList;
	
	public FunctExprNode(Location loc, String identifier, ArrayList<ExprNode> paraList) {
		super(loc);
		this.identifier = identifier;
		this.paraList = paraList;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ArrayList<ExprNode> getParaList() {
		return paraList;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
