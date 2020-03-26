package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class LoadInst extends IRInst {
	private IRSymbol reg, ptr;
	
	public LoadInst(IRSymbol reg, IRSymbol ptr) {
		super();
		this.reg = reg;
		this.ptr = ptr;
	}

	@Override
	public String toString() {
		return reg.toString() + " = load " + reg.getType().toString() + ", " + ptr.getType().toString() + " " + ptr.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	
}
