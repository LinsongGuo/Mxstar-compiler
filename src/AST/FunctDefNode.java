package AST;

import utility.Location;
import java.util.ArrayList;

public class FunctDefNode extends DefNode {
	private TypeNode type;
	private String identifier;
	private ArrayList<VarDefNode> paraList;
	private ArrayList<StmtNode> stmtList;
	
	public FunctDefNode(Location loc, TypeNode type, String identifier, ArrayList<VarDefNode> paraList, ArrayList<StmtNode> stmtList) {
		super(loc);
		this.type = type;
		this.identifier = identifier;
		this.paraList = paraList;
		this.stmtList = stmtList;
	}
	
	public TypeNode getType() {
		return type;
	}
	
	public String getTypeIdentifier() {
		return type.toString();
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ArrayList<VarDefNode> getParaList() {
		return paraList;
	}
	
	public ArrayList<StmtNode> getStmtList() {
		return stmtList;
	}

	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
