package IR.Type;

import IR.IRVisitor;

abstract public class IRType {
	
	@Override
	abstract public String toString();
	
	abstract public void accept(IRVisitor visitor);
	
	abstract public int bytes();
}
