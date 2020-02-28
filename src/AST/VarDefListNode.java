package AST;

import utility.Location;
import java.util.ArrayList;

public class VarDefListNode extends DefNode {
	private ArrayList<VarDefNode> varList;
	
	public VarDefListNode(Location loc, ArrayList<VarDefNode> varList) {
		super(loc);
		this.varList = varList;
	}
	
	public TypeNode getType() {
		return varList.isEmpty() ? null : varList.get(0).getType();
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	public ArrayList<VarDefNode> getVarList() {
		return varList;
	}
}
