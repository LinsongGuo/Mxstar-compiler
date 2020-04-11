package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;
import IR.Type.IRPtrType;

public class StoreInst extends IRInst {
	private IRSymbol res, ptr;
	
	public StoreInst(IRSymbol res, IRSymbol ptr) {
		super();
		this.res = res;
		this.ptr = ptr;
		ptr.addDef(this);
		res.addUse(this);
	}
	
	@Override
	public String toString() {
		return "store " + ((IRPtrType) ptr.getType()).getType().toString() + " " + 
				res.toString() + ", " + 
				ptr.getType().toString() + " " + 
				ptr.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		if (res == old) {
			res = nw;
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
		res.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		ptr.removeDef(this);
	}
}
