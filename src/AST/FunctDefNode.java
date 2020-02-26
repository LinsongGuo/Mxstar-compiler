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
	
	public TypeNode getType() {
		return type;
	}
	
	public String getTypeIdentifier() {
		return type.getIdentifier();
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ArrayList<VarDefNode> getParaList() {
		return paraList;
	}
	
	public BlockStmtNode getBlockStmt() {
		return stmt;
	}
	
	public boolean paraEquals(FunctDefNode other) {
		if (paraList.size() != other.paraList.size()) 
			return false;
		for (int i = 0; i < paraList.size(); ++i) {
			if (!paraList.get(i).typeEquals(other.paraList.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}
}
