package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRType;

abstract public class IRSymbol {
	protected IRType type;
	
	public IRSymbol(IRType type) {
		this.type = type;
	}
	
	@Override
	abstract public String toString();
	
	abstract public void accept(IRVisitor visitor);

	public IRType getType() {
		return type;
	}
}
