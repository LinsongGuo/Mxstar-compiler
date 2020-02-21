package AST;

import utility.Location;
import java.util.ArrayList;

public class FunctDefNode extends DefNode {
	private TypeNode type;
	private String identifier;
	private ArrayList<VarDefNode> paraList;
	private BlockStmtNode stmt;
	
	public FunctDefNode(Location loc, TypeNode type, String identifier, ArrayList<VarDefNode> paraList, BlockStmtNode stmt) {
		super(loc);
		this.type = type;
		this.identifier = identifier;
		this.paraList = paraList;
		this.stmt = stmt;
	}
}
