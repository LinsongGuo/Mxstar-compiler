package AST;

import utility.Location;
import Scope.Type;

public abstract class ExprNode extends ASTNode{
	protected Type type;
	protected boolean lvalue;
	
	public ExprNode(Location loc) {
		super(loc);
		lvalue = false;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public boolean getLvalue() {
		return lvalue;
	}
	
	public void setLvalue(boolean lvalue) {
		this.lvalue = lvalue;
	}
	
	public abstract void accept(ASTVisitor visitor);
}
