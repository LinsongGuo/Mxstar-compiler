package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRType;

public class IRRegister extends IRSymbol {
	
	protected String name;
	
	public IRRegister(IRType type, String name) {
		super(type);
		this.name = name;	
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
}
