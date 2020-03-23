package IR.Type;

import IR.IRVisitor;

public class IRInt32Type extends IRIntType {

	@Override
	public String toString() {
		return "int32";
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

}
