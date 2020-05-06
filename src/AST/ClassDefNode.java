package AST;

import utility.Location;
import java.util.ArrayList;

public class ClassDefNode extends DefNode {
	private String identifier;
	private ArrayList<VarDefListNode> varList;
	private ArrayList<FunctDefNode> functList;
	private FunctDefNode constructorDef;
	
	public ClassDefNode(Location loc, String identifier, ArrayList<VarDefListNode> varList, ArrayList<FunctDefNode> functList, FunctDefNode constructorDef) {
		super(loc);
		this.identifier = identifier;
		this.varList = varList;
		this.functList = functList;
		this.constructorDef = constructorDef;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ArrayList<VarDefListNode> getVarList() {
		return varList;
	}
	
	public ArrayList<FunctDefNode> getFunctList() {
		return functList;
	}
	
	public FunctDefNode getConstructorDef() {
		return constructorDef;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
