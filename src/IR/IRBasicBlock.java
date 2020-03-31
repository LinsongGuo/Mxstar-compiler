package IR;

import java.util.ArrayList;

import IR.Inst.IRInst;

public class IRBasicBlock {
	private String name;
	private IRInst head, tail;
	private ArrayList<IRBasicBlock> prevList, nextList;
	
	public IRBasicBlock(String name) {
		this.name = name;
		head = tail = null;
		prevList = new ArrayList<IRBasicBlock>();
		nextList = new ArrayList<IRBasicBlock>();
	}
	
	public String getName() {
		return "%" + name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addInst(IRInst inst) {
		if (head == null) {
			head = tail = inst;
		}
		else {
			tail.setNext(inst);
			inst.setPrev(tail);
			tail = inst;
		}
	}
	
	public IRInst getHead() {
		return head;
	}
	
	public void addPrev(IRBasicBlock block) {
		prevList.add(block);
	}
	
	public void addNext(IRBasicBlock block) {
		nextList.add(block);
	}
	
	
	public ArrayList<IRBasicBlock> getPrevList() {
		return prevList;
	}
	
	public ArrayList<IRBasicBlock> getNextList() {
		return nextList;
	}
	
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
