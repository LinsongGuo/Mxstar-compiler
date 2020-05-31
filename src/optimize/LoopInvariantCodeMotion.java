package optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.BinOpInst;
import IR.Inst.BitcastToInst;
import IR.Inst.BitwiseBinOpInst;
import IR.Inst.BrInst;
import IR.Inst.GetElementPtrInst;
import IR.Inst.IRInst;
import IR.Inst.IcmpInst;
import IR.Inst.LoadInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRConst;
import IR.Symbol.IRGlobalString;
import IR.Symbol.IRGlobalVariable;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class LoopInvariantCodeMotion extends PASS {
	
	private boolean changed;
	private AliasAnalysis aa;
	private HashSet<IRBasicBlock> loopHeaders;
	private HashMap<IRBasicBlock, HashSet<IRBasicBlock>> loopBackers;
	private HashMap<IRBasicBlock, HashSet<IRBasicBlock>> loopBlocks;
	private HashMap<IRBasicBlock, HashSet<StoreInst>> loopStores; 	
	private HashMap<IRBasicBlock, IRBasicBlock> loopBelong; 	
	private HashSet<IRRegister> invariances;
	
	public LoopInvariantCodeMotion(IRModule module, AliasAnalysis aa) {
		super(module);
		this.aa = aa;
	}
	
	public boolean run() {
		changed = false;
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			run(function);
		}	
		return changed;
	}
	
	private void run(IRFunction function) {
		loopHeaders = new HashSet<>();
		loopBackers = new HashMap<>();
		loopBlocks = new HashMap<>();
		loopStores = new HashMap<>();
		loopBelong = new HashMap<>();
		invariances = new HashSet<>();
		loopAnalysis(function);
		motion(function);
	}

	private void loopAnalysis(IRFunction function) {
		ArrayList<IRBasicBlock> blockList = function.getRDfsSeq();
		for (IRBasicBlock backer : blockList) {
			for (IRBasicBlock header : backer.getSuccessors()) {
				if (header.getDominaces().contains(backer)) { //back-edge
				//	System.err.println("back-edge : " + backer + " --> " + header);
					loopHeaders.add(header);
					if (!loopBackers.containsKey(header)) {
						loopBackers.put(header, new HashSet<>());
						loopBackers.get(header).add(backer);
					}
					else {
						System.err.println("warning : multiple backers!");
					}
				}
			}
		}
		
		for (IRBasicBlock header : loopHeaders) {
			HashSet<IRBasicBlock> blocks = new HashSet<>();
			loopBlocks.put(header, blocks);
			blocks.add(header);
			loopBelong.put(header, header);
			for (IRBasicBlock backer : loopBackers.get(header)) {
				Stack<IRBasicBlock> stack = new Stack<>();
				blocks.add(backer);
				stack.push(backer);
				loopBelong.put(backer, header);
				while (!stack.isEmpty()) {
					IRBasicBlock top = stack.pop();
				//	System.err.println("top " + top);
					for (IRBasicBlock predecessor : top.getPredecessors()) {
						if (!blocks.contains(predecessor)) {
							blocks.add(predecessor);
							stack.push(predecessor);
							loopBelong.put(predecessor, header);
						}
					}
				}
			}
			
			HashSet<StoreInst> stores = new HashSet<>();
			loopStores.put(header, stores);
			for (IRBasicBlock block : blocks) {
				for (IRInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
					if (inst instanceof StoreInst) {
						stores.add((StoreInst) inst);
					}
				}
			}
			
			/*
			for (IRBasicBlock block : blocks) {
				System.err.println("block : " + block);
			}
			*/
		}
	}
	
	private void motion(IRFunction function) {
		ArrayList<IRRegister> parameters = function.getParameters();
		for (IRRegister parameter : parameters) {
			invariances.add(parameter);
		}
		
		for (IRBasicBlock header : loopHeaders) {
			HashSet<IRBasicBlock> blocks = loopBlocks.get(header);
			ArrayList<IRInst> invariantInsts = new ArrayList<>();
			boolean loopChanged = true;
			while(loopChanged) {
				loopChanged = false;
				for (IRBasicBlock block : blocks) {
					for (IRInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
						if ((inst instanceof BinOpInst && checkInvariant((BinOpInst) inst, header))                 ||
							(inst instanceof BitcastToInst && checkInvariant((BitcastToInst) inst, header))         ||
							(inst instanceof BitwiseBinOpInst && checkInvariant((BitwiseBinOpInst) inst, header))   ||
							(inst instanceof GetElementPtrInst && checkInvariant((GetElementPtrInst) inst, header)) ||
							(inst instanceof IcmpInst && checkInvariant((IcmpInst)inst, header))                    ||
							(inst instanceof LoadInst && checkInvariant((LoadInst)inst, header))                    
						) {
							invariantInsts.add(inst);
							loopChanged = true;
							changed = true;
						}
					}
				}
			}
			
			IRBasicBlock preHeader = null;
			for (IRBasicBlock predecessor : header.getPredecessors()) {
				if (!loopBelong.containsKey(predecessor)) {
					preHeader = predecessor;
				}
			}
			if (preHeader == null) {
				System.err.println("warning : no preheader!");
			}
			else {
				for(IRInst inst : invariantInsts) {
				//	System.err.println("LICM  " + inst);
					inst.getCurrentBlock().removeInst(inst);
					IRInst tail = preHeader.getTail();
					assert tail instanceof BrInst;
					preHeader.addInstBeforeWithoutInit(tail, inst);
				}
			}
			
		}
	}

	private boolean isVariant(IRSymbol s, IRBasicBlock header) {
		if (s instanceof IRGlobalString || s instanceof IRGlobalVariable || s instanceof IRConst)
			return true;
		if (s instanceof IRRegister) {
			if (invariances.contains((IRRegister) s))
				return true;
			//System.err.println("defList " + s + " " + ((IRRegister) s).getDefList());
			IRInst def = (((IRRegister) s).getDefList()).iterator().next();
			return !loopBlocks.get(header).contains(def.getCurrentBlock());
		}
		return false;
	}
	
	private boolean checkInvariant(BinOpInst inst, IRBasicBlock header) {
		if (isVariant(inst.getRes(), header))
			return false;
		if (isVariant(inst.getLeft(), header) && isVariant(inst.getRight(), header)) {
			invariances.add(inst.getRes());
			return true;
		}
		return false;
	}
	
	private boolean checkInvariant(BitcastToInst inst, IRBasicBlock header) {
		if (isVariant(inst.getRes(), header))
			return false;
		if (isVariant(inst.getSrc(), header)) {
			invariances.add(inst.getRes());
			return true;
		}
		return false;
	}
	
	
	private boolean checkInvariant(BitwiseBinOpInst inst, IRBasicBlock header) {
		if (isVariant(inst.getRes(), header))
			return false;
		if (isVariant(inst.getLeft(), header) && isVariant(inst.getRight(), header)) {
			invariances.add(inst.getRes());
			return true;
		}
		return false;
	}
	
	private boolean checkInvariant(GetElementPtrInst inst, IRBasicBlock header) {
		if (isVariant(inst.getRes(), header))
			return false;
		if (!isVariant(inst.getPtr(), header))
			return false;
		for (IRSymbol index : inst.getIndex()) {
			if (!isVariant(index, header)) {
				return false;
			}
		}
		invariances.add(inst.getRes());
		return true;
	}
	
	private boolean checkInvariant(IcmpInst inst, IRBasicBlock header) {
		if (isVariant(inst.getRes(), header))
			return false;
		if (isVariant(inst.getLeft(), header) && isVariant(inst.getRight(), header)) {
			invariances.add(inst.getRes());
			return true;
		}
		return false;
	}
	
	private boolean checkInvariant(LoadInst inst, IRBasicBlock header) {
		IRRegister res = inst.getRes();
		IRRegister ptr = (IRRegister) inst.getPtr();
		if (isVariant(res, header))
			return false;
		if (!isVariant(ptr, header)) 
			return false;
		HashSet<StoreInst> stores = loopStores.get(header);
		for (StoreInst store : stores) {
			if (aa.mayAlias(ptr, (IRRegister) store.getPtr()))
				return false;
		}
		invariances.add(res);
		return true;
	}
}
