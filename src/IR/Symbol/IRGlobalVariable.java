package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRPtrType;
import IR.Type.IRType;

public class IRGlobalVariable extends IRRegister {
	private IRSymbol init;
	
	public IRGlobalVariable(IRType type, String name) {
		super(type, name);
	}
	
	public void setInit(IRSymbol init) {
		this.init = init;
	}
	
	@Override
	public String toString() {
		return "@" + super.name;
	}
	
	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public String declarationString() {
		return "@" + name + " = global " + ((IRPtrType) type).getType().toString() + " " + init.toString();
	}
}
