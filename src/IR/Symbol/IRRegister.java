package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRType;

public class IRRegister extends IRSymbol {	
	protected String name;
	
	public IRRegister(IRType type, String name) {
		super(type);
		this.name = name;	
		status = Status.undefined;
		constant = null;
	}

	@Override
	public String toString() {
		return "%" + name;
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	//for SCCP
	public enum Status {
		undefined, constant, multiDefined;		
	}
	public Status status;
	private IRConst constant; 
		
	public void setConstant(IRConst constant) {
		this.constant = constant;
	}
	
	public IRConst getConstant() {
		return constant;
	}
	
}
