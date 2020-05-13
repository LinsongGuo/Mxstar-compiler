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
		boolean flag = false;
		if (value == old) {
			value = nw;
			flag = true;
		}
		if (ptr == old) {
			ptr = nw;
			flag = true;
		}
		if (flag)
			nw.addUse(this);		
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
		ptr.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		ptr.removeDef(this);
	}

	@Override
	public void InitDefUse() {
	//	System.err.println("init in " + currentBlock.getName() + ": " + this);
		ptr.addDef(this);
		ptr.addUse(this); //??????????????????????????
		value.addUse(this);
	//	System.err.println("store " + value + " " + this);
	//	System.err.println(value.getUseList());
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
