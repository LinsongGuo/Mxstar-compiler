package Riscv;

import Riscv.Inst.RvInst;

public class RvBlock {
	private String name;
	private RvFunction currentFunction;
	private RvInst head, tail;
	
	public RvBlock(String name, RvFunction currentFunction) {
		this.name = name;
		this.currentFunction = currentFunction;
		head = tail = null;
	}
	
	public void addInst(RvInst inst) {
		if (head == null) {
			head = tail = inst;
		}
		else {
			tail.setNext(inst);
			inst.setPrev(tail);
			tail = inst;
		}
	}
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
