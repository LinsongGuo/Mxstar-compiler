package AST;

import utility.Location;
import java.util.ArrayList;

public class ClassDefNode extends DefNode {
	private String identifier;
	private ArrayList<VarDefStmtNode> varList;
	private ArrayList<FunctDefNode> functList;
	
	public ClassDefNode(Location loc, String identifier, ArrayList<VarDefStmtNode> varList, ArrayList<FunctDefNode> functList) {
		super(loc);
		this.identifier = identifier;
		this.varList = varList;
		this.functList = functList;
	}
}
