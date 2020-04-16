package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRPtrType;

public class StoreInst extends IRInst {
	private IRSymbol value, ptr;
	
	public StoreInst(IRSymbol value, IRSymbol ptr) {
		super();
		this.value = value;
		this.ptr = ptr;
	}
	
	@Override
	public String toString() {
		return "store " + ((IRPtrType) ptr.getType()).getType().toString() + " " + 
				value.toString() + ", " + 
				ptr.getType().toString() + " " + 
				ptr.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		if (value == old) {
			value = nw;
		//	old.removeUse(this);
			nw.addUse(this);		
		}
	}
	
	@Override
	public IRRegister getRes() {
		return null;
	}
	
	public IRSymbol getValue() {
		return value;
	}
	
	public IRSymbol getPtr() {
		return ptr;
	}

	@Override
	public void removeAllUse() {
		value.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		ptr.removeDef(this);
	}

	@Override
	public void InitDefUse() {
		ptr.addDef(this);
		value.addUse(this);
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		if (value instanceof IRRegister)
			res.add((IRRegister) value);
		if (ptr instanceof IRRegister) 
			res.add((IRRegister) ptr);
		return res;
	}
}
