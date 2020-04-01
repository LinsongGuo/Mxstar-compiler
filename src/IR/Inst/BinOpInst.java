package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class BinOpInst extends IRInst {
	
	public enum BinOpType {
        add(new String("add")), 
        sub(new String("sub")), 
        mul(new String("mul")), 
        sdiv(new String("sdiv")), 
        srem(new String("srem"));
        
        private String name;
        
        private BinOpType(String name) {
        	this.name = name;
        }
        
        @Override
        public String toString() {
        	return name;
        }
    }
	
	private BinOpType op;
	private IRSymbol result, left, right;
	
	public BinOpInst(BinOpType op, IRSymbol result, IRSymbol left, IRSymbol right) {
		super();
		this.op = op;
		this.result = result;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return result.toString() + " = " + op.toString() + " " + result.getType().toString() + " " + left.toString() + ", " + right.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}

}
