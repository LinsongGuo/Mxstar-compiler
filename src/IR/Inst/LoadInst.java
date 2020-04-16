package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class LoadInst extends IRInst {
	private IRSymbol ptr;
	private IRRegister res;
	
	public LoadInst(IRRegister res, IRSymbol ptr) {
		super();
		this.res = res;
		this.ptr = ptr;
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
	
	public IRRegister getRes() {
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

	@Override
	public void InitDefUse() {
		res.addDef(this);
		ptr.addUse(this);
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		if (ptr instanceof IRRegister)
			res.add((IRRegister) ptr);
		return res;
	}
	
}
