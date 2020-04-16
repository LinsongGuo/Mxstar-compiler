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
import IR.Inst.CallInst;
import IR.Inst.GetElementPtrInst;
import IR.Inst.IRInst;
import IR.Inst.PhiInst;
import IR.Inst.RetInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRRegister;

public class DCE extends PASS {

	Queue<IRInst> workList;
	HashSet<IRInst> marked;
	HashSet<IRBasicBlock> visited;

	public DCE(IRModule module) {
		super(module);
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			run(function);
		}
	}
	
	private void push(IRInst inst) {
		if (!marked.contains(inst)) {
			marked.add(inst);
			workList.offer(inst);
		}
	}
	
	private void run(IRFunction function) {
		workList = new LinkedList<IRInst>();
		marked = new HashSet<IRInst>();	
		visited = new HashSet<IRBasicBlock>();
		
		ArrayList<IRBasicBlock> RdfsSeq = function.getRDfsSeq();
		for (IRBasicBlock block : RdfsSeq) {
			for (IRInst inst = block.getTail(); inst != null; inst = inst.getPrev()) {
				if ((inst instanceof StoreInst) || 
					(inst instanceof CallInst)  || 
					(inst instanceof RetInst)   ||
					(inst instanceof GetElementPtrInst)) {
					push(inst);
				}
			}
		}
		
		while(!workList.isEmpty()) {
			IRInst inst = workList.poll();
			ArrayList<IRRegister> regs = inst.getUsedRegister();
		//	System.err.println(inst);
			for (IRRegister reg : regs) {
				HashSet<IRInst> defList = reg.getDefList();
			//	System.err.println("reg : " + reg);
				for (IRInst def : defList) {
				//	System.err.println("def : " + def);
					push(def);
				}
			}
			
			if (inst instanceof PhiInst) {
				ArrayList<IRBasicBlock> blocks = ((PhiInst) inst).getBBs();
				for (IRBasicBlock block : blocks) {
					assert block.getTail() instanceof BrInst;
					push(block.getTail());
				}
			}
			
			IRBasicBlock currentBlock = inst.getCurrentBlock();
			if (!visited.contains(currentBlock)) {
				visited.add(currentBlock);
				HashSet<IRBasicBlock> RDF = inst.getCurrentBlock().getRDF(); 
				for (IRBasicBlock block : RDF) {
					assert block.getTail() instanceof BrInst;
					push(block.getTail());
				}	
			}
		}
		
		for (IRBasicBlock block : RdfsSeq) {
			ArrayList<IRInst> instList = block.getInstList();
			for (IRInst inst : instList) {
				if (!marked.contains(inst)) {
					if (inst instanceof BrInst) {
					//	System.err.println("idom " + block + " " + block.getRIdom());
						((BrInst) inst).change(block.getRIdom());			
					}
					else {
						inst.removeItself();
					}
				}
			}
		}
	}
}
