package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
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
	
	public BinOpType op;
	private IRRegister result;
	private IRSymbol left, right;
	
	public BinOpInst(BinOpType op, IRRegister result, IRSymbol left, IRSymbol right) {
		super();
		this.op = op;
		this.result = result;
		this.left = left;
		this.right = right;
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
		}
	}
	
	@Override
	public IRRegister getRes() {
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

	@Override
	public void InitDefUse() {
		left.addUse(this);
		right.addUse(this);
		result.addDef(this);
	} 
	
	public BinOpType getOp() {
		return op;
	}
	
	public IRSymbol getLeft() {
		return left;
	}
	
	public IRSymbol getRight() {
		return right;
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		if (left instanceof IRRegister) 
			res.add((IRRegister) left);
		if (right instanceof IRRegister)
			res.add((IRRegister) right);
		return res;
	}
}
