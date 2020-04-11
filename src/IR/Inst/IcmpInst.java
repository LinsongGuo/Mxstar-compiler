package IR.Inst;

import IR.IRVisitor;
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

	private IcmpOpType op;
	private IRSymbol result, left, right;
	
	public IcmpInst(IcmpOpType op, IRSymbol result, IRSymbol left, IRSymbol right) {
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
