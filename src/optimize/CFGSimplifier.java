package optimize;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.BrInst;
import IR.Inst.IRInst;
import IR.Inst.PhiInst;
import IR.Symbol.IRConstBool;
import IR.Symbol.IRSymbol;

public class CFGSimplifier extends PASS {

	private boolean changed;
	
	public CFGSimplifier(IRModule module) throws FileNotFoundException {
		super(module);	
	}
	
	public boolean run() {
		changed = false;
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			removeSingleBlockInPhi(function);
			removeConstantBranch(function);
			removeUnusedBlock(function);
		}
		//print();
		return changed;
	}

	public void removeSingleBlockInPhi(IRFunction function) {
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		for (IRBasicBlock block : blockList) {
			for (IRInst inst = block.getHead(); inst instanceof PhiInst; inst = inst.getNext()) {
				((PhiInst)inst).simplify();
			}
		}
	}
	
	public void removeConstantBranch(IRFunction function) {
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		for (IRBasicBlock block : blockList) {
			IRInst tail = block.getTail();
			if (tail instanceof BrInst) {
				IRSymbol cond = ((BrInst) tail).getCond();
				if ((cond != null) && (cond instanceof IRConstBool)) {
					if (((IRConstBool) cond).getValue()) {
						((BrInst) tail).changeTrue();
					}
					else {
						((BrInst) tail).changeFalse();
					}
				}
			}
		}
	}
	
	//remove unreachable block and union connected blocks
	public void removeUnusedBlock(IRFunction function) {
		for (IRBasicBlock block : function.getBlockList()) {
		//	System.err.println(block + " " + block.getSuccessors());		
		//	System.err.println(block.getInstList());
		}
		
		//System.err.println("-------------------------------");
		
		HashSet<IRBasicBlock> visitedSet = new HashSet<IRBasicBlock>();
		Queue<IRBasicBlock> queue = new LinkedList<IRBasicBlock>();
		IRBasicBlock entranceBlock = function.getEntranceBlock();
		visitedSet.add(entranceBlock);
		queue.add(entranceBlock);
		while(!queue.isEmpty()) {
			IRBasicBlock block = queue.poll();
			ArrayList<IRBasicBlock> successors = block.getSuccessors();
			//System.err.println("queue " + block);
			if (successors.size() == 1 && successors.get(0).getPredecessors().size() == 1) {
				IRBasicBlock successor = successors.get(0);
				//System.err.println("union " + block + " " + successor);
				block.union(successor);
				changed = true;
				//System.err.println(block.getSuccessors());
				queue.offer(block);
				continue;
			}
			for (IRBasicBlock successor : successors) {
				if (!visitedSet.contains(successor)){
					visitedSet.add(successor);
					queue.offer(successor);
				}
			}
		}
		
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		for (IRBasicBlock block : blockList) {
			if (!visitedSet.contains(block)) {
				changed = true;
				block.removeItself();
				block.removeAllInst();
				block.removeAllPhiUse();
			}
		}
	}
	
	private void print() throws FileNotFoundException {
		PrintWriter printer = new PrintWriter(new FileOutputStream("test/CFG.txt"));
    	Collection<IRFunction> functions = module.getFunctList().values();
    	for (IRFunction function : functions) {
    		 printer.println(function.getName());
        	 ArrayList<IRBasicBlock> blockList = function.getBlockList();
        	 for (IRBasicBlock block : blockList) {
        		 ArrayList<IRBasicBlock> successors = block.getSuccessors();
        		 printer.print(block.toString() + "  -->  ");
        		 for (IRBasicBlock successor : successors) {
        			 printer.print(successor.toString() + "  ");
        		 }
        		 printer.println("");
        		 
        		 ArrayList<IRBasicBlock> predecessors = block.getPredecessors();
        		 for (IRBasicBlock predecessor : predecessors) {
        			 printer.print(predecessor.toString() + "  ");
        		 }
        		 printer.println("-->  " + block.toString());
        	 }
        	 printer.println("");
		}
    	printer.close();
    }
	
}
