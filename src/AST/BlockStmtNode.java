package AST;

import utility.Location;
import java.util.ArrayList;

public class BlockStmtNode extends StmtNode {
	private ArrayList<StmtNode> stmtList;
	
	public BlockStmtNode(Location loc, ArrayList<StmtNode> stmtList) {
		super(loc);
		this.stmtList = stmtList;
	}
}
