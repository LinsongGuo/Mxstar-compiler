package IR.Inst;

import java.util.ArrayList;

import IR.IRBasicBlock;
import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

abstract public class IRInst {
	
	protected IRInst prev, next;
	protected IRBasicBlock currentBlock;
	
	public IRInst() {
		prev = next = null;
		currentBlock = null;
	}
	
	@Override
	abstract public String toString();
	
	abstract public void accept(IRVisitor visitor);
	
	public void setPrev(IRInst prev) {
		this.prev = prev;
	}
	
	public void setNext(IRInst next) {
		this.next = next;
	}
	
	public IRInst getPrev() {
		return prev;
	}
	
	public IRInst getNext() {
		return next;
	}
	
	public void setCurrentBlock(IRBasicBlock block) {
		currentBlock = block;
	} 
	
	public IRBasicBlock getCurrentBlock() {
		return currentBlock;
	}
	
	public void removeItself() {
		removeAllUse();
		removeAllDef();
		if (prev != null) prev.setNext(next);
		else {
			//System.err.println("current " + currentBlock);
			currentBlock.setHead(next);
		}
		if (next != null) next.setPrev(prev);
		else {
			currentBlock.setTail(prev);
		}
	} 
	
	abstract public void InitDefUse();
	
	abstract public void replaceUse(IRSymbol old, IRSymbol nw);
	
	abstract public void removeAllUse();
	
	abstract public void removeAllDef();
	
	abstract public IRRegister getRes();
	
	abstract public ArrayList<IRRegister> getUsedRegister();
}
