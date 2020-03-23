package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRInt32Type;

public class IRIntConst extends IRConst {
	private long value;
	
	public IRIntConst(long value) {
		super(new IRInt32Type());
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
