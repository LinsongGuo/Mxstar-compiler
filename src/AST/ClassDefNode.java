package AST;

import utility.Location;
import java.util.ArrayList;

public class ClassDefNode extends DefNode {
	private String identifier;
	private ArrayList<VarDefListNode> varList;
	private ArrayList<FunctDefNode> functList, constructorList;
	
	public ClassDefNode(Location loc, String identifier, ArrayList<VarDefListNode> varList, ArrayList<FunctDefNode> functList, ArrayList<FunctDefNode> constructorList) {
		super(loc);
		this.identifier = identifier;
		this.varList = varList;
		this.functList = functList;
		this.constructorList = constructorList;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}