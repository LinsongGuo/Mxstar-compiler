package AST;

import utility.Location;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import Scope.Type;

public abstract class ExprNode extends ASTNode{
	protected Type type;
	protected boolean lvalue;

	public ExprNode(Location loc) {
		super(loc);
		lvalue = false;
		result = null;
		address = null;
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
	
	//for IR
	protected IRSymbol result;
	protected IRRegister address;
	
	public void setResult(IRSymbol result) {
		this.result = result;
	}
	
	public IRSymbol getResult() {
		return result;
	}
	
	public void setAddress(IRRegister address) {
		this.address = address;
	}
	
	public IRRegister getAddress() {
		return address;
	}
}
