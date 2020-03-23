package IR.Type;

import IR.IRVisitor;

public class IRInt8Type extends IRIntType {
	
	@Override
	public String toString() {
		return "int8";
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

}
