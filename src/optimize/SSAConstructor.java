package optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.AllocaInst;
import IR.Inst.BinOpInst;
import IR.Inst.BitwiseBinOpInst;
import IR.Inst.GetElementPtrInst;
import IR.Inst.IRInst;
import IR.Inst.IcmpInst;
import IR.Inst.LoadInst;
import IR.Inst.PhiInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRConstBool;
import IR.Symbol.IRConstInt;
import IR.Symbol.IRNull;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRInt1Type;
import IR.Type.IRInt32Type;
import IR.Type.IRPtrType;
import IR.Type.IRType;
import utility.Pair;

public class SSAConstructor extends PASS {
	private HashMap<IRSymbol, Stack<IRSymbol>> stack; 
	
	public SSAConstructor(IRModule module) {
		super(module);
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			construct(function);
		}
	}
	
	private void construct(IRFunction function) {
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		stack = new HashMap<IRSymbol, Stack<IRSymbol>>();
		removeUnusedInst(function, blockList);
		insertPhiInst(function, blockList);
		rename(function.getEntranceBlock());
		for (IRBasicBlock block : blockList) {
			block.mergePhiMap();
		}
	}
	
	private void removeUnusedInst(IRFunction function, ArrayList<IRBasicBlock> blockList) {
		for (IRBasicBlock block : blockList) {
			ArrayList<IRInst> instList = block.getInstList();
			for (IRInst inst : instList) {
				if (inst instanceof AllocaInst) {
					if (!inst.getRes().isDefed()) {
						inst.removeItself();
					}
				}/*
				else if (inst instanceof StoreInst) {
					if (!((StoreInst) inst).getPtr().isUsed()) {
						inst.removeIfself();
					}
				}
				else if (inst instanceof CallInst) {
					IRSymbol res = inst.getRes();
					if (res != null && !res.isUsed()) {
						//inst.removeIfself();
					}
				}*/
				else if (inst instanceof BinOpInst     ||
					inst instanceof BitwiseBinOpInst   ||
					inst instanceof GetElementPtrInst  ||
					inst instanceof IcmpInst           ||
					inst instanceof LoadInst           ||
					inst instanceof PhiInst            ) 
				{
					if (!inst.getRes().isUsed()) {
						inst.removeItself();
					}
				}
			}
		}
	}
	
	private void insertPhiInst(IRFunction function, ArrayList<IRBasicBlock> blockList) {
		for (IRBasicBlock block : blockList) {
			ArrayList<IRInst> instList = block.getInstList();
			for (IRInst inst : instList) {
				if (inst instanceof AllocaInst) {
					
					IRRegister address = ((AllocaInst) inst).getRes();
					IRType type = ((IRPtrType) address.getType()).getType();
					String name = address.getName().split("\\$")[0];
				
				//	System.err.println("name : " + name);
					
					HashSet<IRInst> defList = address.getDefList();
					Queue<IRBasicBlock> queue = new LinkedList<IRBasicBlock>();
					HashSet<IRBasicBlock> visitedSet = new HashSet<IRBasicBlock>(); 
					for (IRInst def : defList) {
						IRBasicBlock defBlock = def.getCurrentBlock();
						if (!visitedSet.contains(defBlock)) {
							queue.add(defBlock);
							visitedSet.add(defBlock);
						}
					}
					
					while(!queue.isEmpty()) {
						IRBasicBlock front = queue.poll();
						HashSet<IRBasicBlock> DF = front.getDF();
						for (IRBasicBlock df : DF) {
							if (!visitedSet.contains(df)) {
								//insert phi instruction
								IRRegister res = new IRRegister(type, name);
								function.addRegister(res);
								PhiInst phi = new PhiInst(res);
								df.addPhi(address, phi);
								//add dominance frontier
								queue.add(df);
								visitedSet.add(df);
								//System.err.println("insert " + df + " " + res);
							}
						}
					}
					
					stack.put(address, new Stack<IRSymbol>());
					if(type instanceof IRInt32Type) {
						push(address, new IRConstInt(0));
					}
					else if (type instanceof IRInt1Type) {
						push(address, new IRConstBool(false));
					}
					else {
						push(address, new IRNull());
					}
					inst.removeItself();
					//System.err.println(top(address));
				}
			}
		}
	}
	
	private boolean contains(IRSymbol address) {
		return stack.containsKey(address);
	}
	
	private boolean empty(IRSymbol address) {
		assert stack.containsKey(address);
		return stack.get(address).empty();
	}
	private void push(IRSymbol address, IRSymbol result) {
		//System.err.println("push " + address + " " + result);
		assert stack.containsKey(address);
		stack.get(address).push(result);
	}
	
	private IRSymbol top(IRSymbol address) {
		assert stack.containsKey(address);
		Stack<IRSymbol> sta = stack.get(address);
		assert (!sta.isEmpty());
		return sta.peek();
	}
	
	private void pop(IRSymbol address) {
	//	System.err.println("pop  " + address);
		assert stack.containsKey(address);
		stack.get(address).pop();
	}
	
	private void rename(IRBasicBlock block) {
		ArrayList<Pair<IRRegister, PhiInst>> phiMap = block.getPhiMap();
		
		for (Pair<IRRegister, PhiInst> pair : phiMap) {
			IRRegister address = pair.first;
			PhiInst phi = pair.second;
			push(address, phi.getRes());
			//System.err.println("push " + address +  " " + phi.getRes());
		}
		
		ArrayList<IRInst> instList = block.getInstList();
		for (IRInst inst : instList) {
			if (inst instanceof StoreInst) {
				IRSymbol address = ((StoreInst) inst).getPtr();
				IRSymbol value = ((StoreInst) inst).getValue();
				if (contains(address)) push(address, value);
				//System.err.println("push " + address +  " " + res);
			}
			else if (inst instanceof LoadInst) {
				IRSymbol address = ((LoadInst) inst).getPtr();
				IRRegister res = ((LoadInst) inst).getRes();
				
				if (contains(address)) {
					res.replaceUse(top(address));
					//System.err.println("replace " + res +  " with " + top(address) + " " + top(address).getUseList());
				}
			}
		}
		
		ArrayList<IRBasicBlock> successors = block.getSuccessors();
		for (IRBasicBlock successor : successors) {
			ArrayList<Pair<IRRegister, PhiInst>> succPhiMap = successor.getPhiMap();
			for (Pair<IRRegister, PhiInst> pair : succPhiMap) {
				IRRegister address = pair.first;
				PhiInst phi = pair.second;
				if (!empty(address))
					phi.addBranch(top(address), block);
			}
		}
		
		ArrayList<IRBasicBlock> dominaces = block.getDominaces();
		for (IRBasicBlock dominace : dominaces) {
			rename(dominace);
		}
		
		for (IRInst inst : instList) {
			if (inst instanceof StoreInst) {
				IRSymbol address = ((StoreInst) inst).getPtr();
				if (contains(address)) {
					pop(address);		
					inst.removeItself();		
				}
			}
			else if (inst instanceof LoadInst) {
				IRSymbol address = ((LoadInst) inst).getPtr();
				if (contains(address)) inst.removeItself();
			}
		}
		for (Pair<IRRegister, PhiInst> pair : phiMap) {
			IRRegister address = pair.first;
			pop(address);
		}
	}

}
