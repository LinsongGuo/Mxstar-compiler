package IR.Inst;

import java.util.ArrayList;

import IR.IRBasicBlock;
import IR.IRVisitor;
import IR.Symbol.IRConstBool;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import Riscv.Inst.RvInst;

public class BrInst extends IRInst {
	private IRSymbol cond;
	private IRBasicBlock ifTrue, ifFalse;
	
	public BrInst(IRBasicBlock currentBlock, IRBasicBlock ifTrue) {
		this.ifTrue = ifTrue;
	}
	
	public BrInst(IRBasicBlock currentBlock, IRSymbol cond, IRBasicBlock ifTrue, IRBasicBlock ifFalse) {
		super();
		this.cond = cond;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
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

	private void removeCond() {
		if (cond != null) {
			cond.removeUse(this);
			cond = null;
		}
	}
	
	private void removeTrue() {
		if (ifTrue != null) {
			ifTrue.removeIncomingBlockInPhi(currentBlock);
			currentBlock.removeSuccessor(ifTrue);
			ifTrue.removePredecessor(currentBlock);
			ifTrue = null;
		}
	}
	
	private void removeFalse() {
		if (ifFalse != null) {
			ifFalse.removeIncomingBlockInPhi(currentBlock);
			currentBlock.removeSuccessor(ifFalse);
			ifFalse.removePredecessor(currentBlock);
			ifFalse = null;
		}
	}
	
	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public IRSymbol getCond() {
		return cond;
	}
	
	public void change(IRBasicBlock block) {
		assert (block == ifTrue) || (block == ifFalse);
		if (block == ifTrue) {
			changeTrue();
		}
		else if (block == ifFalse) {
			changeFalse();
		}
	}
	
	public void changeTrue() {
		removeCond();
		removeFalse();
	}
	
	public void changeFalse() {
		removeCond();
		removeTrue();
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
					removeFalse();
				}
				else {
					removeTrue();
					ifTrue = ifFalse;
					ifFalse = null;
				}
			}
		}
	}
	
	public void replaceBlock(IRBasicBlock old, IRBasicBlock nw) {
		if (ifTrue == old) {
			ifTrue = nw;
		}
		if (ifFalse == old) {
			ifFalse = nw;
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
		if (ifTrue != null) {
			currentBlock.addSuccessor(ifTrue);
			ifTrue.addPredecessor(currentBlock);
		//	System.err.println("edge : " + currentBlock + " --> " + ifTrue);
		}
		if (ifFalse != null) {
			currentBlock.addSuccessor(ifFalse);
			ifFalse.addPredecessor(currentBlock);	
		//	System.err.println("edge : " + currentBlock + " --> " + ifFalse);
		}
	}
	
	public IRBasicBlock getTrue() {
		return ifTrue;
	}
	
	public IRBasicBlock getFalse() {
		return ifFalse;
	}
	
	public void removeBlock(IRBasicBlock block) {
		if (ifTrue == block) {
			removeCond();
		//	removeTrue();
			ifTrue = ifFalse;
			ifFalse = null;
		}
		if (ifFalse == block) {
			removeCond();
		//	removeFalse();
		}
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		if (cond != null && (cond instanceof IRRegister)) 
			res.add((IRRegister) cond);
		return res;
	}
	
}
