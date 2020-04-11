package IR;

import java.util.ArrayList;
import java.util.HashSet;
import IR.Inst.BrInst;
import IR.Inst.IRInst;
import IR.Inst.PhiInst;
import IR.Symbol.IRRegister;
import utility.Pair;

public class IRBasicBlock {
	private String name;
	private IRInst head, tail;
	private ArrayList<IRBasicBlock> predecessors, successors;
	private IRBasicBlock prev, next;
	private IRFunction currentFunction;
	
	//for constructing SSA
	private IRBasicBlock sdom, idom, father;
	private HashSet<IRBasicBlock> bucket, strictDominators, DF;
	private ArrayList<IRBasicBlock> dominaces;
	private int dfn;
	private ArrayList<Pair<IRRegister, PhiInst>> phiMap;
	private HashSet<PhiInst> phiUse;
	
	public IRBasicBlock(String name) {
		this.name = name;
		head = tail = null;
		predecessors = new ArrayList<IRBasicBlock>();
		successors = new ArrayList<IRBasicBlock>();
		prev = next = null;
		currentFunction = null;
		sdom = idom = father = null;
		bucket = new HashSet<IRBasicBlock>();
		strictDominators = new HashSet<IRBasicBlock>();
		DF = new HashSet<IRBasicBlock>();
		phiMap = new ArrayList<Pair<IRRegister, PhiInst>>();
		dominaces = new ArrayList<IRBasicBlock>();
		phiUse = new HashSet<PhiInst>(); 
	}
	
	public void setCurrentFunction(IRFunction function) {
		currentFunction = function;
	}
	
	public IRFunction getCurrentFunction() {
		return currentFunction;
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
		if ((tail != null) && (tail instanceof BrInst)) {
			//System.err.println("can't add " + inst);
			inst.removeAllUse();
			inst.removeAllDef();
			if (inst instanceof PhiInst) ((PhiInst) inst).removeAllPhiUse();
			//inst.removeIfself();
			return;
		}
		inst.setCurrentBlock(this);
		if (head == null) {
			head = tail = inst;
		}
		else {
			tail.setNext(inst);
			inst.setPrev(tail);
			tail = inst;
		}
	}
	
	public void setHead(IRInst inst) {
		head = inst;
	}
	
	public void setTail(IRInst inst) {
		tail = inst;
	}
	
	public IRInst getHead() {
		return head;
	}
	
	public void setPrev(IRBasicBlock block) {
		prev = block;
	}
	
	public IRBasicBlock getPrev() {
		return prev;
	}
	
	public void setNext(IRBasicBlock block) {
		next = block;
	}
	
	public IRBasicBlock getNext() {
		return next;
	}
	
	public void addPredecessor(IRBasicBlock block) {
		predecessors.add(block);
	}
	
	public void removePredecessor(IRBasicBlock block) {
		predecessors.remove(block);
	}
	
	public void addSuccessor(IRBasicBlock block) {
		successors.add(block);
	}
	
	public void removeSuccessor(IRBasicBlock block) {
		successors.remove(block);
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
	
	public void addDominace(IRBasicBlock block) {
		dominaces.add(block);
	}
	
	public ArrayList<IRBasicBlock> getDominaces() {
		return dominaces;
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

	public ArrayList<IRInst> getInstList() {
		ArrayList<IRInst> res = new ArrayList<IRInst>();
		IRInst inst = head;
		while (inst != null) {
			res.add(inst);
			inst = inst.getNext();
		}
		return res;
	} 

	public void removeAllInst() {
		ArrayList<IRInst> instList = getInstList();
		for (IRInst inst : instList) {
			inst.removeIfself();
		}
	}
	
	public void removeItself() {
		if (prev != null) 
			prev.setNext(next);
		if (next != null) 
			next.setPrev(prev);
		else {
			currentFunction.setLastBlock(prev);
		}
		
		for (IRBasicBlock successor : successors) {
			successor.removePredecessor(this);
		}
	}
	
	public void addPhiUse(PhiInst inst) {
		phiUse.add(inst);
	}
	
	public void removePhiUse(PhiInst inst) {
		phiUse.remove(inst);
	}
	
	public void replacePhiUse(IRBasicBlock other) {
		for (PhiInst inst : phiUse) {
			inst.replacePhiUse(this, other);
		}
		phiUse.clear();
	}
	
	public void union(IRBasicBlock other) {
	//	System.err.println("union " + this + " " + other);
		assert tail instanceof BrInst;
		
		tail.removeIfself();
		
		ArrayList<IRInst> instList = other.getInstList();
		for (IRInst inst : instList) {
			addInst(inst);
			inst.setCurrentBlock(this);
		}
		other.replacePhiUse(this);
		
		other.removeItself();
		
		successors = other.getSuccessors();
		for (IRBasicBlock successor : successors) {
			successor.addPredecessor(this);
		}
	}
	
	public void addPhi(IRRegister address, PhiInst phi) {
		phiMap.add(new Pair<IRRegister, PhiInst>(address, phi));
	}
	
	public ArrayList<Pair<IRRegister, PhiInst>> getPhiMap() {
		return phiMap;
	}
	
	public void mergePhiMap() {
		for (int i = phiMap.size() - 1; i >= 0; --i) {
			PhiInst phi = phiMap.get(i).second;
		//	System.err.println(phi + "  " + phi.getRes() + "  " + phi.getRes().isUsed());
			if (phi.getRes().isUsed()) {
				if (head == null) {
					assert tail == null;
					head = tail = phi;
				}
				else {
					head.setPrev(phi);
					phi.setNext(head);
					head = phi;		
				}	
			}
		}
	}

}	
