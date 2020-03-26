package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRInt1Type;

public class IRConstBool extends IRSymbol {
	private boolean value;
	
	public IRConstBool(boolean value) {
		super(new IRInt1Type());
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
