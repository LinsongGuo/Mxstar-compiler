package AST;

import utility.Location; 
import java.util.ArrayList;

public class ProgramNode extends ASTNode {
	private ArrayList<DefNode> defList;
	
	public ProgramNode(Location loc, ArrayList<DefNode> defList) {
		super(loc);
		this.defList = defList;
	}
	
	public void addDef(DefNode def) {
		defList.add(def);
	}
}
