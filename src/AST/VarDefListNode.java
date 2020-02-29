package AST;

import utility.Location;
import java.util.ArrayList;

public class VarDefListNode extends DefNode {
	private TypeNode type;
	private ArrayList<VarDefNode> varList;
	
	public VarDefListNode(Location loc, TypeNode type, ArrayList<VarDefNode> varList) {
		super(loc);
		this.varList = varList;
		this.type = type;
	}
	
	public TypeNode getType() {
		return type;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
	
	public ArrayList<VarDefNode> getVarList() {
		return varList;
	}
}
