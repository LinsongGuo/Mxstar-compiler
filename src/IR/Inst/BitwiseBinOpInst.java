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
		left.addUse(this);
		right.addUse(this);
	}
	
	@Override
	public String toString() {
		return result.toString() + " = " + op.toString() + " " + result.getType().toString() + " " + left.toString() + ", " + right.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		boolean flag = false;
		if (left == old) {
			left = nw;
			flag = true;
		}
		if (right == old) {
			right = nw;		
			flag = true;
		}
		if (flag) nw.addUse(this);
	}
	
	public IRSymbol getRes() {
		return result;
	}

	@Override
	public void removeAllUse() {
		
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}

}
