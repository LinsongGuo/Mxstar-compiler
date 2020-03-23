package IR.Inst;

import IR.IRVisitor;

public class PhiInst extends IRInst {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}

}
