package IR.Inst;

import IR.IRVisitor;
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
		value.addUse(this);
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
	public IRSymbol getRes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllUse() {
		if (value != null) value.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}
}
