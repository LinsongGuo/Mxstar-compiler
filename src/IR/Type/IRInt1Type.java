package IR.Type;

import IR.IRVisitor;

public class IRInt1Type extends IRIntType {

	@Override
	public String toString() {
		return "i1";
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
}
