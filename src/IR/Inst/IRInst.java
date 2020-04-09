package IR.Inst;

import IR.IRBasicBlock;
import IR.IRVisitor;

abstract public class IRInst {
	
	protected IRInst prev, next;
	protected IRBasicBlock block;
	
	public IRInst() {
		prev = next = null;
		block = null;
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
	
	public void setBlock(IRBasicBlock block) {
		this.block = block;
	} 
	
	public IRBasicBlock getBlock() {
		return block;
	}
}
