package IR;

import java.util.ArrayList;
import java.util.HashSet;

import IR.Inst.IRInst;

public class IRBasicBlock {
	private String name;
	private IRInst head, tail;
	private ArrayList<IRBasicBlock> predecessors, successors;
	private IRBasicBlock succ;
	
	//for constructing SSA
	private IRBasicBlock sdom, idom, father;
	private HashSet<IRBasicBlock> bucket, strictDominators, DF;
	private int dfn;
	
	public IRBasicBlock(String name) {
		this.name = name;
		head = tail = null;
		predecessors = new ArrayList<IRBasicBlock>();
		successors = new ArrayList<IRBasicBlock>();
		sdom = idom = father = null;
		bucket = new HashSet<IRBasicBlock>();
		strictDominators = new HashSet<IRBasicBlock>();
		DF = new HashSet<IRBasicBlock>();
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return "%" + name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addInst(IRInst inst) {
		inst.setBlock(this);
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
	
	public void setSucc(IRBasicBlock block) {
		succ = block;
	}
	
	public IRBasicBlock getSucc() {
		return succ;
	}
	
	public void addPredecessor(IRBasicBlock block) {
		predecessors.add(block);
	}
	
	public void addSuccessor(IRBasicBlock block) {
		successors.add(block);
	}

	public ArrayList<IRBasicBlock> getPredecessors() {
		return predecessors;
	}
	
	public ArrayList<IRBasicBlock> getSuccessors() {
		return successors;
	}
	
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public IRInst getTail() {
		return tail;
	}
	
	public IRBasicBlock getSdom() {
		return sdom;
	}
	
	public void setSdom(IRBasicBlock sdom) {
		this.sdom = sdom;
	}
	
	public IRBasicBlock getIdom() {
		return idom;
	}
	
	public void setIdom(IRBasicBlock idom) {
		this.idom = idom;
	}
	
	public void setFather(IRBasicBlock father) {
		this.father = father;
	}
	
	public IRBasicBlock getFather() {
		return father;
	}
	
	public int getDfn() {
		return dfn;
	}
	
	public void setDfn(int dfn) {
		this.dfn = dfn;
	} 
	
	public void dfs(HashSet<IRBasicBlock> visited, ArrayList<IRBasicBlock> dfsSeq) {
		dfn = dfsSeq.size();
		dfsSeq.add(this);
		visited.add(this);
		for (IRBasicBlock successor : successors) {
			if (!visited.contains(successor)) {
				successor.setFather(this);
				successor.dfs(visited, dfsSeq);
			}
		}
	}
	
	public void addSdom(IRBasicBlock block) {
		bucket.add(block);
	}
	
	public HashSet<IRBasicBlock> getBucket() {
		return bucket;
	}
	
	public void addStrictDominator(IRBasicBlock block) {
		strictDominators.add(block);
	}
	
	public HashSet<IRBasicBlock> getStrictDominators() {
		return strictDominators;
	}
	
	public void addDF(IRBasicBlock block) {
		DF.add(block);
	}
	
	public HashSet<IRBasicBlock> getDF() {
		return DF;
	}
	
	
}
