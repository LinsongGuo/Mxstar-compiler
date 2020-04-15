package IR.Inst;

import IR.IRBasicBlock;
import IR.IRVisitor;
import IR.Symbol.IRConstBool;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRInt1Type;

public class BrInst extends IRInst {
	private IRSymbol cond;
	private IRBasicBlock ifTrue, ifFalse;
	
	public BrInst(IRBasicBlock currentBlock, IRBasicBlock ifTrue) {
		this.ifTrue = ifTrue;
		currentBlock.addSuccessor(ifTrue);
		ifTrue.addPredecessor(currentBlock);
	}
	
	public BrInst(IRBasicBlock currentBlock, IRSymbol cond, IRBasicBlock ifTrue, IRBasicBlock ifFalse) {
		super();
		this.cond = cond;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
		currentBlock.addSuccessor(ifTrue);
		ifTrue.addPredecessor(currentBlock);
		currentBlock.addSuccessor(ifFalse);
		ifFalse.addPredecessor(currentBlock);
		cond.addUse(this);
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
	
	public IRSymbol getCond() {
		return cond;
	}
	
	public void changeTrue() {
		cond.removeUse(this);
		cond = null;
		currentBlock.removeSuccessor(ifFalse);
		ifFalse.removePredecessor(currentBlock);
		ifFalse = null;
	}
	
	public void changeFalse() {
		cond.removeUse(this);
		cond = null;
		currentBlock.removeSuccessor(ifTrue);
		ifTrue.removePredecessor(currentBlock);
		ifTrue = ifFalse;
		ifFalse = null;
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		if (cond == old) {
			cond = nw;
			old.removeUse(this);
			nw.addUse(this);
			if (nw instanceof IRConstBool) {
				cond = null;
				if (((IRConstBool) nw).getValue()) {
					ifFalse = null;
				}
				else {
					ifTrue = ifFalse;
					ifFalse = null;
				}
			}
		}
	}

	@Override
	public IRRegister getRes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllUse() {
		if (cond != null) cond.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InitDefUse() {
		if (cond != null) cond.addUse(this);
	}
	
	public IRBasicBlock getTrue() {
		return ifTrue;
	}
	
	public IRBasicBlock getFalse() {
		return ifFalse;
	}
	
	public void removeBlock(IRBasicBlock block) {
		if (ifTrue == block) {
			if (cond != null) {
				cond.removeUse(this);
				cond = null;
			}
			ifTrue = ifFalse;
		}
		else {
			if (cond != null) {
				cond.removeUse(this);
				cond = null;
			}
			ifFalse = null;
		}
	}
}
