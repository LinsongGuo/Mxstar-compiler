package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;

public abstract class RvInst {
	protected RvInst prev, next;
	protected RvBlock currentBlock;
	
	public RvInst(RvBlock currentBlock) {
		this.currentBlock = currentBlock;
		prev = next = null;
	}
	
	public void setPrev(RvInst inst) {
		prev = inst;
	}
	
	public void setNext(RvInst inst) {
		next = inst;
	}
	
	public RvInst getPrev() {
		return prev;
	}
	
	public RvInst getNext() {
		return next;
	}
	
	public void setCurrentBlock(RvBlock currentBlock) {
		this.currentBlock = currentBlock;
	}
	
	public RvBlock getCurrentBlock() {
		return currentBlock;
	}
	
	abstract public void accept(RvVisitor visitor);
}
