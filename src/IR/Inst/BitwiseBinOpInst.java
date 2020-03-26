package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class BitwiseBinOpInst extends IRInst {

	public enum BitwiseBinOpType {
        shl(new String("shl")), 
        ashr(new String("ashr")), 
        and(new String("and")), 
        or(new String("or")), 
        xor(new String("xor"));
		
        private String name;
        
        private BitwiseBinOpType(String name) {
        	this.name = name;
        }
        
        @Override
        public String toString() {
        	return name;
        }
    }
	
	private BitwiseBinOpType op;
	private IRSymbol result, left, right;
	
	public BitwiseBinOpInst(BitwiseBinOpType op, IRSymbol result, IRSymbol left, IRSymbol right) {
		super();
		this.op = op;
		this.result = result;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return result.toString() + " = " + op.toString() + " " + result.getType().toString() + " " + left.toString() + "," + right.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}

}
