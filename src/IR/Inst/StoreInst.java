package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class StoreInst extends IRInst {
	private IRSymbol reg, ptr;
	
	public StoreInst(IRSymbol reg, IRSymbol ptr) {
		super();
		this.reg = reg;
		this.ptr = ptr;
	}

	@Override
	public String toString() {
		return "store " + reg.getType().toString() + " " + 
				reg.toString() + ", " + 
				ptr.getType().toString() + " " + 
				ptr.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
}
