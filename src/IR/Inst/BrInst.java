package IR.Inst;

import IR.IRBasicBlock;
import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class BrInst extends IRInst {
	private IRSymbol cond;
	private IRBasicBlock ifTrue, ifFalse;
	
	public BrInst(IRBasicBlock currentBlock, IRBasicBlock ifTrue) {
		this.ifTrue = ifTrue;
		currentBlock.addNext(ifTrue);
		ifTrue.addPrev(currentBlock);
	}
	
	public BrInst(IRBasicBlock currentBlock, IRSymbol cond, IRBasicBlock ifTrue, IRBasicBlock ifFalse) {
		super();
		this.cond = cond;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
		currentBlock.addNext(ifTrue);
		ifTrue.addPrev(currentBlock);
		currentBlock.addNext(ifFalse);
		ifFalse.addPrev(currentBlock);
	}
	
	@Override
	public String toString() {
		if (cond == null) {
			return "br label " + ifTrue.toString();
		}
		else {
			return "br i1 " + cond.toString() + ", label " + ifTrue.toString() + ", label " + ifFalse.toString(); 
		}
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
