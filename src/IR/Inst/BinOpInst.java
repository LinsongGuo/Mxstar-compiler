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
		left.addUse(this);
		right.addUse(this);
	}
	
	@Override
	public String toString() {
		return result.toString() + " = " + 
				op.toString() + " " + 
				result.getType().toString() + " " + 
				left.toString() + ", " + 
				right.toString();
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
		if (flag) {
			nw.addUse(this);
			//old.removeUse(this);		
		}
	}
	
	@Override
	public IRSymbol getRes() {
		return result;
	}

	@Override
	public void removeAllUse() {
		left.removeUse(this);
		right.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	} 
}
