package IR.Type;

import IR.IRVisitor;

public class IRInt8Type extends IRIntType {
	
	@Override
	public String toString() {
		return "i8";
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int bytes() {
		return 1;
	}

}
