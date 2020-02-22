package AST;

import utility.Location;
import java.util.ArrayList;

public class ClassDefNode extends DefNode {
	private String identifier;
	private ArrayList<VarDefListNode> varList;
	private ArrayList<FunctDefNode> functList;
	
	public ClassDefNode(Location loc, String identifier, ArrayList<VarDefListNode> varList, ArrayList<FunctDefNode> functList) {
		super(loc);
		this.identifier = identifier;
		this.varList = varList;
		this.functList = functList;
	}
}
