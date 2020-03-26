package IR.Inst;

import IR.IRVisitor;

abstract public class IRInst {
	
	protected IRInst prev, next;
	
	public IRInst() {
		prev = next = null;
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
}
