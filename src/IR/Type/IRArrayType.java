package IR.Type;

import java.util.ArrayList;

import IR.IRVisitor;
 
public class IRArrayType extends IRType {
    private IRType type;
    private int size;
	
    public IRArrayType(IRType type, int size) {
    	this.type = type;
    	this.size = size;
    }
    
    @Override
	public String toString() {
    	return "[" + size + " x " + type.toString() + "]";
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
