package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRInt32Type;

public class IRConstInt extends IRSymbol {
	private long value;
	
	public IRConstInt(long value) {
		super(new IRInt32Type());
		this.value = value;
	}
	
	public long getValue() {
		return value;
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
