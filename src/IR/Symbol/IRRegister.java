package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRType;

public class IRRegister extends IRSymbol {
	
	private String name;
	
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
	
}
