package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class LoadInst extends IRInst {
	private IRSymbol res, ptr;
	
	public LoadInst(IRSymbol res, IRSymbol ptr) {
		super();
		this.res = res;
		this.ptr = ptr;
		ptr.addUse(this);
	}

	@Override
	public String toString() {
		return res.toString() + " = load " + res.getType().toString() + ", " + ptr.getType().toString() + " " + ptr.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		// TODO Auto-generated method stub
		
	}
	
	public IRSymbol getRes() {
		return res;
	}
	
	public IRSymbol getPtr() {
		return ptr;
	}
	
}
