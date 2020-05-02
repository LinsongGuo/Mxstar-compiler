package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRType;

abstract public class IRConst extends IRSymbol {

	public IRConst(IRType type) {
		super(type);
	}

	@Override
	abstract public String toString();

	@Override
	abstract public void accept(IRVisitor visitor);

	abstract public boolean valueEquals(IRConst other);

}
