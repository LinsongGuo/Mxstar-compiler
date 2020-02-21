package AST;

import utility.Location;
import java.util.ArrayList;

public class VarDefStmtNode extends StmtNode {
	private ArrayList<VarDefNode> varList;
	public VarDefStmtNode(Location loc, ArrayList<VarDefNode> varList) {
		super(loc);
		this.varList = varList;
	}
}
