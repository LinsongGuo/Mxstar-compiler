package optimize;

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
import IR.Symbol.IRConstBool;
import IR.Symbol.IRSymbol;

public class CFGSimplifier extends PASS {

	public CFGSimplifier(IRModule module) {
		super(module);
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			removeUnusefulBlock(function);
			removeUnconditionalBranch(function);
		}
	}
	
	//remove unreachable block and union connected blocks
	public void removeUnusefulBlock(IRFunction function) {
		HashSet<IRBasicBlock> visitedSet = new HashSet<IRBasicBlock>();
		Queue<IRBasicBlock> queue = new LinkedList<IRBasicBlock>();
		IRBasicBlock entranceBlock = function.getEntranceBlock();
		visitedSet.add(entranceBlock);
		queue.add(entranceBlock);
		while(!queue.isEmpty()) {
			IRBasicBlock block = queue.poll();
			ArrayList<IRBasicBlock> successors = block.getSuccessors();
			for (IRBasicBlock successor : successors) {
				if (successor.getPredecessors().size() == 1 && successors.size() == 1)  {
					block.union(successor);
				}
				else if (!visitedSet.contains(successor)){
					visitedSet.add(successor);
					queue.offer(successor);
				}
			}
		}
		
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		for (IRBasicBlock block : blockList) {
			if (!visitedSet.contains(block)) {
				block.removeItself();
			}
		}
	}

	public void removeUnconditionalBranch(IRFunction function) {
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
	
}
