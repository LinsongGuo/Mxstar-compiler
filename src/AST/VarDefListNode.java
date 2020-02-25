package AST;

import utility.Location;
import java.util.ArrayList;

public class VarDefListNode extends DefNode {
	private ArrayList<VarDefNode> varList;
	
	public VarDefListNode(Location loc, ArrayList<VarDefNode> varList) {
		super(loc);
		this.varList = varList;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	public ArrayList<VarDefNode> getVarList() {
		return varList;
	}
}
