package optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.AllocaInst;
import IR.Inst.BinOpInst;
import IR.Inst.BitcastToInst;
import IR.Inst.BitwiseBinOpInst;
import IR.Inst.BrInst;
import IR.Inst.CallInst;
import IR.Inst.GetElementPtrInst;
import IR.Inst.IRInst;
import IR.Inst.IcmpInst;
import IR.Inst.LoadInst;
import IR.Inst.MoveInst;
import IR.Inst.PhiInst;
import IR.Inst.RetInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRGlobalString;
import IR.Symbol.IRGlobalVariable;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class Inliner extends PASS {

	private static int MaxInstNum = 333;
	private static int MaxDepth = 1;
	
	private HashMap<IRFunction, Integer> instNums;
	private HashMap<IRFunction, ArrayList<CallInst>> callSet;
	private HashSet<IRFunction> recursiveSet;
	private HashMap<IRFunction, Integer> inStack;
	private ArrayList<IRFunction> dfsOrder;
	private HashSet<IRFunction> visited;
	
	private HashMap<IRBasicBlock, IRBasicBlock> renameBlock;
	private HashMap<IRSymbol, IRSymbol> renameReg;
	
	public Inliner(IRModule module) {
		super(module);
	}
	
	public void run() {
		instNums = new HashMap<IRFunction, Integer>();
		callSet = new HashMap<IRFunction, ArrayList<CallInst>>();
		recursiveSet = new HashSet<IRFunction>();
		inStack = new HashMap<>();
		dfsOrder = new ArrayList<>();
		visited = new HashSet<IRFunction>();
		
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			buildGraph(function);
		}
		
		IRFunction main = module.getMain();
		dfsRecursive(main);
		
		//inline non-recursive callees.
		while (true) {
			boolean changed = false;
			for (int i = dfsOrder.size() - 1; i >= 0; --i) {
				IRFunction caller = dfsOrder.get(i);
				ArrayList<CallInst> calls = callSet.get(caller);
				//System.err.println("for " + caller.getName() + " " + calls);
				for (CallInst call : calls) {
					IRFunction callee = call.getFunction();
					int callerNum = instNums.get(caller);
					int calleeNum = instNums.get(callee);
					if (!recursiveSet.contains(callee) && instNums.get(callee) < MaxInstNum && !call.inlined()) {
						inline(caller, callee, call);
						call.setInlined();
						instNums.put(caller, calleeNum + callerNum);
						changed = true;
					}
				}
			}
			if (!changed)
				break;
		}
		
		//inline recursive callees.
		for (int d = 0; d < MaxDepth; ++d) {
			for (int i = dfsOrder.size() - 1; i >= 0; --i) {
				IRFunction caller = dfsOrder.get(i);
				ArrayList<CallInst> calls = callSet.get(caller);
				for (CallInst call : calls) {
					IRFunction callee = call.getFunction();
					int callerNum = instNums.get(caller);
					int calleeNum = instNums.get(callee);
					if (instNums.get(callee) < MaxInstNum && !call.inlined() && caller != callee) {
						inline(caller, callee, call);
						call.setInlined();
						instNums.put(caller, calleeNum + callerNum);
					}
				}
			}	
		}
		
		//remove unused function.
		dfsCallee(module.getMain());
		ArrayList<IRFunction> tmps = new ArrayList<IRFunction>();
		for (IRFunction function : functions) {
			tmps.add(function);
		}
		for (IRFunction function : tmps) {
			if (!visited.contains(function)) {
				for (IRBasicBlock block = function.getEntranceBlock(); block != null; block = block.getNext()) {
					ArrayList<IRInst> instList = block.getInstList();
					for (IRInst inst : instList) {
						inst.removeItself();
					}
				}
				module.removeFunction(function);
			}
		}
	}
	
	private void dfsCallee(IRFunction caller) {
		visited.add(caller);
		ArrayList<IRFunction> calls = new ArrayList<IRFunction>();
		for (IRBasicBlock block = caller.getEntranceBlock(); block != null; block = block.getNext()) {
			for (IRInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
				if (inst instanceof CallInst) {
					IRFunction callee = ((CallInst) inst).getFunction();
					if (!visited.contains(callee)) {
						dfsCallee(callee);
					}
				}
			}
		}
	}
	
	private void buildGraph(IRFunction caller) {
		int cnt = 0;
		ArrayList<CallInst> calls = new ArrayList<CallInst>();
		for (IRBasicBlock block = caller.getEntranceBlock(); block != null; block = block.getNext()) {
			for (IRInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
				cnt++;
				if (inst instanceof CallInst) {
					IRFunction callee = ((CallInst) inst).getFunction();
					if (!module.isbuiltInFunction(callee)) {
						//System.err.println("build " + caller.getName() + " " + callee.getName());
						calls.add((CallInst) inst);
					}
				}
			}
		}
		instNums.put(caller, cnt);
		callSet.put(caller, calls);
		inStack.put(caller, 0);
	}
	
	private void dfsRecursive(IRFunction caller) {
		//enter stack.
		dfsOrder.add(caller);
		inStack.put(caller, 1);
		ArrayList<CallInst> calls = callSet.get(caller);
		for (CallInst call : calls) {
			IRFunction callee = call.getFunction();
			if (inStack.get(callee) == 0) 
				dfsRecursive(callee);
			else if (inStack.get(callee) == 1) 
				recursiveSet.add(callee);
		}
		inStack.put(caller, 2);
	}
	
	private IRSymbol rename(IRFunction caller, IRSymbol s) {
		if (s instanceof IRGlobalVariable || s instanceof IRGlobalString) 
			return s;
		else if (s instanceof IRRegister) {
			if (renameReg.containsKey(s))
				return renameReg.get(s);
			IRRegister res = new IRRegister(s.getType(), ((IRRegister) s).getName().split("\\.")[0]);
			caller.addRegister(res);
			renameReg.put(s, res);
			return res;
		}
		else 
			return s;
	}
	
	private void inline(IRFunction caller, IRFunction callee, CallInst call) {
	//	System.err.println("****inline " + caller.getName() + " " + callee.getName());
		renameBlock = new HashMap<IRBasicBlock, IRBasicBlock>();
		renameReg = new HashMap<IRSymbol, IRSymbol>();
		
		IRBasicBlock currentBlock = call.getCurrentBlock();
		IRBasicBlock spillBlock = currentBlock.spill(call);
	//	System.err.println("spill "+ currentBlock.getName() + " " + spillBlock.getName());
		ArrayList<IRBasicBlock> blocks = callee.getBlockList();
		for (IRBasicBlock block : blocks) {
			IRBasicBlock newBlock = new IRBasicBlock(block.getName().split("\\.")[0]);
			caller.addBasicBlock(newBlock);
			renameBlock.put(block, newBlock);
		}
		
	//	System.err.println(callee.getEntranceBlock()+ " " + callee.getExitBlock());
		IRBasicBlock entranceBlock = renameBlock.get(callee.getEntranceBlock());
		IRBasicBlock exitBlock = renameBlock.get(callee.getExitBlock());
	//	System.err.println("Add br " + currentBlock + " " + entranceBlock);
		currentBlock.addInst(new BrInst(currentBlock, entranceBlock));
		
		ArrayList<IRRegister> parameters = callee.getParameters();
		ArrayList<IRSymbol> arguments = call.getParameters();
		for (int i = 0; i < parameters.size(); ++i) {
			renameReg.put(parameters.get(i), arguments.get(i));
		//	System.err.println("rename " + parameters.get(i) + " " + arguments.get(i));
		}
		
		for (IRBasicBlock block : blocks) {
			IRBasicBlock newBlock = renameBlock.get(block);
			//System.err.println("------------------ " + block + " --> " + newBlock);
			for (IRInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
				if (inst instanceof AllocaInst) {
					newBlock.addInst(new AllocaInst((IRRegister) rename(caller, ((AllocaInst) inst).getRes()), 
							((AllocaInst) inst).getType()));
				}
				if (inst instanceof BinOpInst) {
					newBlock.addInst(new BinOpInst(((BinOpInst) inst).getOp(), 
							(IRRegister) rename(caller, ((BinOpInst) inst).getRes()), 
							rename(caller, ((BinOpInst) inst).getLeft()), 
							rename(caller, ((BinOpInst) inst).getRight())));
				}
				else if (inst instanceof BitcastToInst) {
					newBlock.addInst(new BitcastToInst(rename(caller, ((BitcastToInst) inst).getSrc()), 
							(IRRegister) rename(caller, ((BitcastToInst) inst).getRes())));
				}
				else if (inst instanceof BitwiseBinOpInst) {
					newBlock.addInst(new BitwiseBinOpInst(((BitwiseBinOpInst) inst).getOp(), 
							(IRRegister) rename(caller, ((BitwiseBinOpInst) inst).getRes()), 
							rename(caller, ((BitwiseBinOpInst) inst).getLeft()), 
							rename(caller, ((BitwiseBinOpInst) inst).getRight())));
				}
				else if (inst instanceof BrInst) {
					IRSymbol cond = ((BrInst) inst).getCond();
					if (cond == null) {
						newBlock.addInst(new BrInst(newBlock, renameBlock.get(((BrInst)inst).getTrue())));
					}
					else {
						newBlock.addInst(new BrInst(newBlock, rename(caller, cond), 
								renameBlock.get(((BrInst)inst).getTrue()), 
								renameBlock.get(((BrInst)inst).getFalse())));		
					}
				}
				else if (inst instanceof CallInst) {
					ArrayList<IRSymbol> oldArg = ((CallInst) inst).getParameters();
					ArrayList<IRSymbol> newArg = new ArrayList<>();
					for (IRSymbol arg : oldArg) {
						newArg.add(rename(caller, arg));
					}
					IRRegister res = ((CallInst) inst).getRes();
					if (res == null)
						newBlock.addInst(new CallInst(((CallInst) inst).getFunction(), newArg));
					else 
						newBlock.addInst(new CallInst(((CallInst) inst).getFunction(), newArg, (IRRegister) rename(caller, res)));
				}
				else if (inst instanceof GetElementPtrInst) {
					ArrayList<IRSymbol> oldIdx = ((GetElementPtrInst) inst).getIndex();
					ArrayList<IRSymbol> newIdx = new ArrayList<>();
					for (IRSymbol idx : oldIdx) {
						newIdx.add(rename(caller, idx));
					}
					newBlock.addInst(new GetElementPtrInst((IRRegister) rename(caller, ((GetElementPtrInst) inst).getRes()),
							rename(caller, ((GetElementPtrInst) inst).getPtr()), newIdx));
				}
				else if (inst instanceof IcmpInst) {
					newBlock.addInst(new IcmpInst(((IcmpInst) inst).getOp(), 
							(IRRegister) rename(caller, ((IcmpInst) inst).getRes()), 
							rename(caller, ((IcmpInst) inst).getLeft()), 
							rename(caller, ((IcmpInst) inst).getRight())));
				}
				else if (inst instanceof LoadInst) {
					newBlock.addInst(new LoadInst((IRRegister) rename(caller, ((LoadInst) inst).getRes()), 
							rename(caller, ((LoadInst) inst).getPtr())));
				}
				else if (inst instanceof MoveInst) {
					newBlock.addInst(new MoveInst((IRRegister) rename(caller, ((MoveInst) inst).getRes()), 
							rename(caller, ((MoveInst) inst).getSrc())));
				}
				else if (inst instanceof PhiInst) {
					ArrayList<IRSymbol> oldSyms = ((PhiInst) inst).getSymbols();
					ArrayList<IRBasicBlock> oldBBs = ((PhiInst) inst).getBBs();
					ArrayList<IRSymbol> newSyms = new ArrayList<>();
					ArrayList<IRBasicBlock> newBBs = new ArrayList<>();
					assert oldSyms.size() == oldBBs.size();
					for (int i = 0; i < oldBBs.size(); ++i) {
						newBBs.add(renameBlock.get(oldBBs.get(i)));
						newSyms.add(rename(caller, oldSyms.get(i)));
					}
					newBlock.addInst(new PhiInst((IRRegister) rename(caller, ((PhiInst) inst).getRes()), newSyms, newBBs));
				}
				else if (inst instanceof StoreInst) {
					StoreInst store = new StoreInst(rename(caller, ((StoreInst) inst).getValue()), 
							rename(caller, ((StoreInst) inst).getPtr()));
					newBlock.addInst(store);
			//		System.err.println(store);
			//		System.err.println("use " + store.getValue() + " " + store.getValue().getUseList());
				}
				else if (inst instanceof RetInst) {
					IRSymbol value = ((RetInst) inst).getValue();
					if (value != null) {
						value = rename(caller, value);
						IRRegister res = call.getRes();
						HashSet<IRInst> useList = res.getUseList();
						for (IRInst use : useList) {
							use.replaceUse(res, value);
						}
					}
					exitBlock.addInst(new BrInst(exitBlock, spillBlock));
				}
			}
		}
	}
	
}
