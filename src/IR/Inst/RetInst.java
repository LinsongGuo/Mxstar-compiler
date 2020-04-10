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
		value.addDef(this);
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
		if (value == old)
			value = nw;
	}
}
