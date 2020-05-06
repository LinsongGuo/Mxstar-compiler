package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class RetInst extends IRInst {
	private IRSymbol value;
	
	public RetInst() {
		super();
		value = null;
	}
	
	public RetInst(IRSymbol value) {
		super();
		this.value = value;
	}
	
	@Override
	public String toString() {
		if (value == null) {
			return "ret void";
		}
		else {
			return "ret " + value.getType().toString() + " " + value.toString();	
		}		
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		if (value == old) {
			value = nw;
	//		old.removeUse(this);
			nw.addUse(this);
		}
	}

	@Override
	public IRRegister getRes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IRSymbol getValue() {
		return value;
	}

	@Override
	public void removeAllUse() {
		if (value != null) value.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InitDefUse() {
		if (value != null) value.addUse(this);
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		if (value != null && (value instanceof IRRegister))
			res.add((IRRegister) value);
		return res;
	}
	
}
