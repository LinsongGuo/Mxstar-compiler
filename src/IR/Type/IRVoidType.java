package IR.Type;

import IR.IRVisitor;

public class IRVoidType extends IRType {

	@Override
	public String toString() {
		return "void";
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int bytes() {
		// TODO Auto-generated method stub
		return 0;
	}

}
