package IR.Type;

import IR.IRVisitor;

public class IRPtrType extends IRType {
	IRType type;
	
	public IRPtrType(IRType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type.toString() + "*";
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public IRType getType() {
		return type;
	}

	@Override
	public int bytes() {
		return 8;
	}
}
