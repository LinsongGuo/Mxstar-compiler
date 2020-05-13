package IR;

import java.util.ArrayList;
import java.util.HashSet;
import IR.Inst.BrInst;
import IR.Inst.CallInst;
import IR.Inst.IRInst;
import IR.Inst.MoveInst;
import IR.Inst.PhiInst;
import IR.Symbol.IRRegister;
import Riscv.RvBlock;
import utility.Pair;

public class IRBasicBlock {
	private String name;
	private IRInst head, tail;
	private ArrayList<IRBasicBlock> predecessors, successors;
	private IRBasicBlock prev, next;
	private IRFunction currentFunction;
	
	//for constructing SSA
	private int dfn;
	private IRBasicBlock sdom, idom, father;
	private HashSet<IRBasicBlock> bucket, strictDominators, DF;
	private ArrayList<IRBasicBlock> dominaces;
	private int Rdfn;
	private IRBasicBlock Rsdom, Ridom, Rfather;
	private HashSet<IRBasicBlock> Rbucket, RstrictDominators, RDF;
	private ArrayList<IRBasicBlock> Rdominaces;
	private ArrayList<Pair<IRRegister, PhiInst>> phiMap;
	private HashSet<PhiInst> phiUse;
	
	public IRBasicBlock(String name) {
		this.name = name;
		head = tail = null;
		predecessors = new ArrayList<IRBasicBlock>();
		successors = new ArrayList<IRBasicBlock>();
		prev = next = null;
		currentFunction = null;
		dfn = 0;
		Rdfn = 0;
		sdom = idom = father = null;
		Rsdom = Ridom = Rfather = null;
		bucket = new HashSet<IRBasicBlock>();
		Rbucket = new HashSet<IRBasicBlock>();
		strictDominators = new HashSet<IRBasicBlock>();
		RstrictDominators = new HashSet<IRBasicBlock>();
		DF = new HashSet<IRBasicBlock>();
		RDF = new HashSet<IRBasicBlock>();
		dominaces = new ArrayList<IRBasicBlock>();
		Rdominaces = new ArrayList<IRBasicBlock>();
		phiMap = new ArrayList<Pair<IRRegister, PhiInst>>();
		phiUse = new HashSet<PhiInst>(); 
		executable = false;
		moveList = new ArrayList<MoveInst>();
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
			return;
		}
		inst.setCurrentBlock(this);
		inst.InitDefUse();
		if (head == null) {
			head = tail = inst;
		}
		else {
			tail.setNext(inst);
			inst.setPrev(tail);
			tail = inst;
		}
	}
	
	public void addInstWithoutInit(IRInst inst) {
		if ((tail != null) && (tail instanceof BrInst)) {
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
	 
	public void addInstInHead(IRInst inst) {
		inst.setCurrentBlock(this);
		inst.InitDefUse();
		if (head == null) {
			assert tail == null;
			head = inst;
		}
		else {
			head.setPrev(inst);
			inst.setNext(head);
			head = inst;
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
		if (!predecessors.contains(block))
			predecessors.add(block);
	}
	
	public void removePredecessor(IRBasicBlock block) {
		predecessors.remove(block);
	}
	
	public void addSuccessor(IRBasicBlock block) {
		if (!successors.contains(block))
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
	
	public IRBasicBlock getRSdom() {
		return Rsdom;
	}
	
	public void setSdom(IRBasicBlock sdom) {
		this.sdom = sdom;
	}
	
	public void setRSdom(IRBasicBlock Rsdom) {
		this.Rsdom = Rsdom;
	}
	
	public IRBasicBlock getIdom() {
		return idom;
	}
	
	public IRBasicBlock getRIdom() {
		return Ridom;
	}
	
	public void setIdom(IRBasicBlock idom) {
		this.idom = idom;
	}
	
	public void setRIdom(IRBasicBlock Ridom) {
		this.Ridom = Ridom;
	}
	
	public void setFather(IRBasicBlock father) {
		this.father = father;
	}
	
	public void setRFather(IRBasicBlock Rfather) {
		this.Rfather = Rfather;
	}
	
	public IRBasicBlock getFather() {
		return father;
	}
	
	public IRBasicBlock getRFather() {
		return Rfather;
	}
	
	public int getDfn() {
		return dfn;
	}
	
	public int getRDfn() {
		return Rdfn;
	}
	
	public void setDfn(int dfn) {
		this.dfn = dfn;
	} 

	public void setRDfn(int Rdfn) {
		this.Rdfn = Rdfn;
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
	
	public void Rdfs(HashSet<IRBasicBlock> Rvisited, ArrayList<IRBasicBlock> RdfsSeq) {
		//System.err.println("rdfs " + this);
		Rdfn = RdfsSeq.size();
		RdfsSeq.add(this);
		Rvisited.add(this);
		for (IRBasicBlock predecessor : predecessors) {
			if (!Rvisited.contains(predecessor)) {
				predecessor.setRFather(this);
				predecessor.Rdfs(Rvisited, RdfsSeq);
			}
		}
	}
	
	public void addSdom(IRBasicBlock block) {
		bucket.add(block);
	}
	
	public void addRSdom(IRBasicBlock block) {
		Rbucket.add(block);
	}
	
	public HashSet<IRBasicBlock> getBucket() {
		return bucket;
	}
	
	public HashSet<IRBasicBlock> getRBucket() {
		return Rbucket;
	}
	
	public void addDominace(IRBasicBlock block) {
		dominaces.add(block);
	}
	
	public void addRDominace(IRBasicBlock block) {
		Rdominaces.add(block);
	}
	
	public ArrayList<IRBasicBlock> getDominaces() {
		return dominaces;
	}
	
	public ArrayList<IRBasicBlock> getRDominaces() {
		return Rdominaces;
	}
	
	public void addStrictDominator(IRBasicBlock block) {
		strictDominators.add(block);
	}
	
	public void addRStrictDominator(IRBasicBlock block) {
		RstrictDominators.add(block);
	}
	
	public HashSet<IRBasicBlock> getStrictDominators() {
		return strictDominators;
	}
	
	public HashSet<IRBasicBlock> getRStrictDominators() {
		return RstrictDominators;
	}
	
	public void addDF(IRBasicBlock block) {
		DF.add(block);
	}
	
	public void addRDF(IRBasicBlock block) {
		RDF.add(block);
	}
	
	public HashSet<IRBasicBlock> getDF() {
		return DF;
	}
	
	public HashSet<IRBasicBlock> getRDF() {
		return RDF;
	}

	public ArrayList<IRInst> getInstList() {
		ArrayList<IRInst> res = new ArrayList<IRInst>();
		for (Pair<IRRegister, PhiInst> pair : phiMap) {
			res.add(pair.second);
		}
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
			inst.removeItself();
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
		
		for (IRBasicBlock predecessor : predecessors) {
			IRInst tail = predecessor.getTail();
			if (tail != null) {
				assert tail instanceof BrInst;
				((BrInst) tail).removeBlock(this);
			}
			predecessor.removeSuccessor(this);
			//System.err.println("tail " + predecessor + " --> " + this + " " + predecessor.getTail());
		}
		
		for (IRBasicBlock successor : successors) {
			successor.removePredecessor(this);
		}
	}
	
	public boolean hasPhiUse(PhiInst inst) {
		return phiUse.contains(inst);
	}
	
	public void addPhiUse(PhiInst inst) {
		phiUse.add(inst);
	}
	
	public void removePhiUse(PhiInst inst) {
		phiUse.remove(inst);
	}
	
	public void removeAllPhiUse() {
		for (PhiInst inst : phiUse) {
			inst.removePhiUse(this);
		}
	}
	
	public void replaceAllPhiUse(IRBasicBlock other) {
		for (PhiInst inst : phiUse) {
			inst.replacePhiUse(this, other);
		}
		phiUse.clear();
	}
	
	public void union(IRBasicBlock other) {
		//System.err.println("union " + this + " " + other);
		assert tail instanceof BrInst;
	
		tail.removeItself();
		
		ArrayList<IRInst> instList = other.getInstList();
		for (IRInst inst : instList) {
			addInst(inst);
			inst.setCurrentBlock(this);
		}
		other.replaceAllPhiUse(this);
		
		if (other == currentFunction.getExitBlock())
			currentFunction.setExitBlock(this);
		
		IRBasicBlock otherNext = other.getNext(), otherPrev = other.getPrev();
		//System.err.println("other " + otherPrev + " " + otherNext);
		otherPrev.setNext(otherNext);
		if (otherNext != null) otherNext.setPrev(otherPrev);
		else currentFunction.setLastBlock(otherPrev);
		
		successors = other.getSuccessors();
		for (IRBasicBlock successor : successors) {
			successor.removePredecessor(other);
			successor.addPredecessor(this);
		}
	}
	
	public void addPhi(IRRegister address, PhiInst phi) {
		phiMap.add(new Pair<IRRegister, PhiInst>(address, phi));
	}
	
	public ArrayList<Pair<IRRegister, PhiInst>> getPhiMap() {
		return phiMap;
	}
	
	public ArrayList<PhiInst> getAllPhiInst() {
		 ArrayList<PhiInst> phiInsts = new ArrayList<PhiInst>();
		 for (IRInst inst = head; inst instanceof PhiInst; inst = inst.getNext()) {
			 phiInsts.add((PhiInst) inst);
		 }
		 return phiInsts;
	}
	
	public void mergePhiMap() {
		for (int i = phiMap.size() - 1; i >= 0; --i) {
			PhiInst phi = phiMap.get(i).second;
			phi.setCurrentBlock(this);
			phi.InitDefUse();
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
		phiMap.clear();
	}
	
	public void removeIncomingBlockInPhi(IRBasicBlock block) {
		ArrayList<IRInst> res = new ArrayList<IRInst>();
		for (Pair<IRRegister, PhiInst> pair : phiMap) {
			PhiInst inst = pair.second;
			if (block.hasPhiUse(inst)) {
				inst.removePhiUse(block);
				block.removePhiUse(inst);			
			}
		}
		for (IRInst inst = head; inst instanceof PhiInst; inst = inst.getNext()) {
			if (block.hasPhiUse((PhiInst) inst)) {
				((PhiInst) inst).removePhiUse(block);
				block.removePhiUse((PhiInst) inst);			
			}
		}
	}
	
	//for SCCP
	private boolean executable;
	
	public void setExecutable() {
		executable = true;
	}
	
	public boolean getExecutable() {
		return executable;
	}
	
	//for SSA destruction
	private ArrayList<MoveInst> moveList;
	
	public boolean hasMove() {
		return !moveList.isEmpty();
	}
	
	public ArrayList<MoveInst> getMoveList() {
		return moveList;
	}
	
	public MoveInst getFirstMove() {
		return moveList.get(0);
	}
	
	public void addMoveInst(MoveInst inst) {
		moveList.add(inst);
	}
	
	public MoveInst MoveInstOutsideCircle() {
		for (MoveInst move : moveList) {
			boolean flag = true;
			for (MoveInst other : moveList) {
				if (move.getRes().equals(other.getSrc())) {
					//System.err.println(move.getRes());
					flag = false;
					break;
				}
			}
			if (flag) {
				//System.err.println("return " + move);
				return move;
			}
		}
		return null;
	}
	
	public void mergeMoveInst(MoveInst inst) {
		if (moveList.contains(inst))
			moveList.remove(inst);
		inst.setCurrentBlock(this);
		inst.InitDefUse();
		IRInst tailPrev = tail.getPrev();
		if (tailPrev == null) {
			assert head == tail;
			head = inst;
			head.setNext(tail);
			tail.setPrev(inst);
		}
		else {
			//System.err.println("insert " + inst + " " + tailPrev);
			tailPrev.setNext(inst);
			inst.setPrev(tailPrev);
			tail.setPrev(inst);
			inst.setNext(tail);
		}
	}
	
	public IRBasicBlock spill(CallInst call) {
		IRBasicBlock spillBlock = new IRBasicBlock("spillBlock");
		currentFunction.addBasicBlock(spillBlock);
		
		if (currentFunction.getExitBlock() == this)
			currentFunction.setExitBlock(spillBlock);
 		
		/*
		if (head == call) 
			head = tail = null;
		else {
			tail = prev;
			prev.setNext(null);
		}*/
		
		IRInst next = null;
		for (IRInst inst = call.getNext(); inst != null; inst = next) {
			next = inst.getNext();
			inst.setNext(null);
			inst.setPrev(null);
			spillBlock.addInstWithoutInit(inst);
		}
		
		call.setNext(null);
		call.removeItself();
		
	///	System.err.println(head + " " + tail);
		
		for (IRBasicBlock successor : successors) {
			spillBlock.addSuccessor(successor);
			successor.removePredecessor(this);
			successor.addPredecessor(spillBlock);
		}
		successors.clear();
		
		for (PhiInst phi : phiUse) {
			phi.replacePhiUse(this, spillBlock);
		}
		phiUse.clear();
		
		
		//System.err.println("after " + head);
		//System.err.println("after " + tail);
		
		return spillBlock;
	}
	
	//for instruction selection
	private RvBlock rvBlock;
	
	public void setRvBlock(RvBlock rvBlock) {
		this.rvBlock = rvBlock;
	}
	
	public RvBlock toRvBlock() {
		return rvBlock;
	}

}	
