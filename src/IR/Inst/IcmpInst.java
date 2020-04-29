package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class IcmpInst extends IRInst {
	public enum IcmpOpType {
        eq(new String("eq")), 
        ne(new String("ne")), 
        sgt(new String("sgt")), 
        sge(new String("sge")), 
        slt(new String("slt")),
		sle(new String("sle"));
        
        private String name;
        
        private IcmpOpType(String name) {
        	this.name = name;
        }
        
        @Override
        public String toString() {
        	return name;
        }
    }

	public IcmpOpType op;
	private IRSymbol left, right;
	private IRRegister result;
	
	public IcmpInst(IcmpOpType op, IRRegister result, IRSymbol left, IRSymbol right) {
		super();
		this.op = op;
		this.result = result;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return result.toString() + " = icmp " + op.toString() + " " + left.getType().toString() + " " + left.toString() + ", " + right.toString();
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
		//	old.removeUse(this);
			nw.addUse(this);
		}
	}
	
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
	
	public IcmpOpType getOp() {
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
