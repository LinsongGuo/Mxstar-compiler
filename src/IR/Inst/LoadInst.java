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
		if (ptr == old) {
			ptr = nw;
		//	old.removeUse(this);
			nw.addUse(this);
		}
	}
	
	public IRSymbol getRes() {
		return res;
	}
	
	public IRSymbol getPtr() {
		return ptr;
	}

	@Override
	public void removeAllUse() {
		ptr.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}
	
}
