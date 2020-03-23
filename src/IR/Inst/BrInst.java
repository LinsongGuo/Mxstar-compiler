package IR.Inst;

import IR.IRBasicBlock;
import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class BrInst extends IRInst {
	private IRSymbol cond;
	private IRBasicBlock ifTrue, ifFalse;
	
	public BrInst(IRSymbol cond, IRBasicBlock ifTrue, IRBasicBlock ifFalse) {
		this.cond = cond;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}
	
	@Override
	public String toString() {
		if (cond == null) {
			return "br label " + ifTrue.getName();
		}
		else {
			return "br i1 " + cond.toString() + ", label " + ifTrue.getName() + ", label " + ifFalse.getName(); 
		}
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
