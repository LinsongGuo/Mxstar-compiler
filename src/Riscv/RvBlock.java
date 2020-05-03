package Riscv;

import java.util.ArrayList;
import java.util.HashSet;

import Riscv.Inst.RvInst;
import Riscv.Operand.RvRegister;

public class RvBlock {
	private String name;
	private RvFunction currentFunction;
	private RvInst head, tail;
	private HashSet<RvRegister> def, useNotDef, liveIn, liveOut;
	private ArrayList<RvBlock> successors, predecessors;
	
	public RvBlock(String name, RvFunction currentFunction) {
		this.name = "." + name;
		this.currentFunction = currentFunction;
		head = tail = null;
		def = new HashSet<RvRegister>();
		useNotDef = new HashSet<RvRegister>();
		liveIn = new HashSet<RvRegister>();
		liveOut = new HashSet<RvRegister>();
		successors = new ArrayList<RvBlock>();
		predecessors = new ArrayList<RvBlock>();
	}
	
	public void addInst(RvInst inst) {
		//System.err.println("addinst " + inst);
		if (head == null) {
			head = tail = inst;
		}
		else {
			tail.setNext(inst);
			inst.setPrev(tail);
			tail = inst;
		}
		//System.err.println("tail " + tail);
	}
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	public String getName() {
		return name;
	}
	
	public RvInst getHead() {
		return head;
	}
	
	public RvInst getTail() {
		return tail;
	}
	
	public void addDef(HashSet<RvRegister> regs) {
		def.addAll(regs);
	}
	
	public void addUseNotDef(HashSet<RvRegister> regs) {
		useNotDef.addAll(regs);
	}
	
	public void addDef(RvRegister reg) {
		def.add(reg);
	}
	
	public void addUseNotDef(RvRegister reg) {
		useNotDef.add(reg);
	}
	
	public HashSet<RvRegister> getDef() {
		return def;
	}
	
	public HashSet<RvRegister> getUseNotDef() {
		return useNotDef;
	}
	
	public HashSet<RvRegister> getLiveIn() {
		return liveIn;
	}
	
	public HashSet<RvRegister> getLiveOut() {
		return liveOut;
	}
	
	public void setLiveIn(HashSet<RvRegister> liveIn) {
		this.liveIn = liveIn;
	}
	
	public void setLiveOut(HashSet<RvRegister> liveOut) {
		this.liveOut = liveOut;
	}
	
	public void clearDefAndUse() {
		def.clear();
		useNotDef.clear();
	}
	
	public void clearLive() {
		liveIn.clear();
		liveOut.clear();
	}
	
	public void addPredecessor(RvBlock block) {
		predecessors.add(block);
	}
	
	public void addSuccessor(RvBlock block) {
		successors.add(block);
	}
	
	public ArrayList<RvBlock> getPredecessors() {
		return predecessors;
	}
	
	public ArrayList<RvBlock> getSuccessors() {
		return successors;
	}

	public void dfs(ArrayList<RvBlock> order, HashSet<RvBlock> visited) {
		order.add(this);
		visited.add(this);
		for (RvBlock successor : successors) {
			if (!visited.contains(successor)) {
				successor.dfs(order, visited);
			}
		}
	}
}

