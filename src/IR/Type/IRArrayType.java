package IR.Type;

import IR.IRVisitor;
 
public class IRArrayType extends IRType {
    private IRType type;
    private int size;
	
    @Override
	public String toString() {
    	return "[" + size + " x " + type.toString() + "]";
    }
	
    @Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
}
