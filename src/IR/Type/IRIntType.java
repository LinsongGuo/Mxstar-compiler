package IR.Type;

import IR.IRVisitor;

abstract public class IRIntType extends IRType {

	@Override
	abstract public String toString();

	@Override
	abstract public void accept(IRVisitor visitor);
	
	@Override
	abstract public int bytes();
}
