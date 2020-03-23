package IR.Inst;

import IR.IRVisitor;

abstract public class IRInst {

	@Override
	abstract public String toString();
	
	abstract public void accept(IRVisitor visitor);
	
}
