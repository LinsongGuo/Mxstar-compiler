package optimize;

import java.util.ArrayList;
import java.util.Collection;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.BrInst;
import IR.Inst.IRInst;
import IR.Inst.MoveInst;
import IR.Inst.PhiInst;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class SSADestructor extends PASS {

	public SSADestructor(IRModule module) {
		super(module);
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			CriticalEdgeSplitting(function);
			SequentializeParallelCopies(function);
		}
	}
	
	public void CriticalEdgeSplitting(IRFunction function) {
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		for (IRBasicBlock block : blockList) {
			ArrayList<PhiInst> phiInsts = block.getAllPhiInst();
			for (PhiInst phi : phiInsts) {
				//System.err.println("phi " + block + " " + phi);
				ArrayList<IRBasicBlock> predecessors = phi.getBBs();
				ArrayList<IRSymbol> symbols = phi.getSymbols();
				IRRegister res = phi.getRes();	
				for (int i = 0; i < predecessors.size(); ++i) {
					IRBasicBlock predecessor = predecessors.get(i);
					IRSymbol symbol = symbols.get(i);
					if (predecessor.getSuccessors().size() == 1) {
						//not critical
						predecessor.addMoveInst(new MoveInst(res, symbol));
					}
					else {
						System.err.println("critical " + predecessor + " " + block);
						//convert critical edge to noncritical edge 
						//by splitting critical edge
						IRBasicBlock newBlock = new IRBasicBlock("criticalEdge");
						function.addBasicBlock(newBlock);
						newBlock.addMoveInst(new MoveInst(res, symbol));
						newBlock.addInst(new BrInst(newBlock, block));
						
						predecessor.removeSuccessor(block);
						predecessor.addSuccessor(newBlock);
						block.removePredecessor(predecessor);
						block.addPredecessor(newBlock);
						//System.err.println("head " + block.getHead());
						for (IRInst inst = block.getHead(); inst instanceof PhiInst; inst = inst.getNext()) {
							((PhiInst) inst).replacePhiUse(predecessor, newBlock);	
							//System.err.println("--phi " + inst);
						}
						
						assert block.getTail() instanceof BrInst;
						((BrInst) predecessor.getTail()).replaceBlock(block, newBlock);
					}
				}
				phi.removeItself();
			}
		}
		/*
		blockList = function.getBlockList();
		for (IRBasicBlock block : blockList) {
			System.err.println(block + "---------------");
			ArrayList<MoveInst> moveList = block.getMoveList();
			for (MoveInst move : moveList) {
				System.err.println(move);
			}
		}
		*/
	}
	
	public void SequentializeParallelCopies(IRFunction function) {
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		for (IRBasicBlock block : blockList) {
			//System.err.println("for " + block);
			while (block.hasMove()) {
				MoveInst move = block.MoveInstOutsideCircle();
				if (move != null) {
					block.mergeMoveInst(move);
				}
				else {
					//parallel copy is only made-up of cycles; Break one of them.
					move = block.getFirstMove();
					IRSymbol src = move.getSrc();
	            	IRRegister srcTmp = new IRRegister(src.getType(), "srcTmp");
	            	function.addRegister(srcTmp);
	            	block.mergeMoveInst(new MoveInst(srcTmp, src));
	            	move.modifySrc(srcTmp);
				}
			}
			//System.err.println(block.getInstList());
		}
	}
}
