package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRType;
import IR.Type.IRVoidType;
import IR.Type.IRPtrType;

public class IRNull extends IRSymbol {

	public IRNull() {
		super(new IRPtrType(new IRVoidType()));
	}

	@Override
	public String toString() {
		return "null";
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

}
