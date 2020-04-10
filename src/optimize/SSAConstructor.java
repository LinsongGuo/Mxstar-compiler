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
import IR.Inst.IRInst;
import IR.Inst.LoadInst;
import IR.Inst.PhiInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRPtrType;
import IR.Type.IRType;
import utility.Pair;

public class SSAConstructor extends PASS {
	private HashMap<IRRegister, Stack<IRSymbol>> stack; 
	
	public SSAConstructor(IRModule module) {
		super(module);
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			construct(function);
		}
	}
	
	private void construct(IRFunction function) {
		ArrayList<IRBasicBlock> blockList = function.getBlockList();
		insertPhiInst(function, blockList);
		
		stack = new HashMap<IRRegister, Stack<IRSymbol>>();
		rename(function.getEntranceBlock());
	}
	
	private void insertPhiInst(IRFunction function, ArrayList<IRBasicBlock> blockList) {
		for (IRBasicBlock block : blockList) {
			ArrayList<IRInst> instList = block.getInstList();
			for (IRInst inst : instList) {
				if (inst instanceof AllocaInst) {
					IRRegister address = ((AllocaInst) inst).getRes(); 
					IRType type = ((IRPtrType) address.getType()).getType();
					String name = address.getName().split("\\$")[0];
					
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
								block.addPhi(address, phi);
								//add dominance frontier
								queue.add(df);
								visitedSet.add(df);
							}
						}
					}
				}
			}
		}
	}
	
	
	private void rename(IRBasicBlock block) {
		ArrayList<Pair<IRRegister, PhiInst>> phiMap = block.getPhiMap();
		for (Pair<IRRegister, PhiInst> pair : phiMap) {
			IRRegister address = pair.first;
			PhiInst phi = pair.second;
			stack.get(address).push(phi.getRes());
		}
		
		ArrayList<IRInst> instList = block.getInstList();
		for (IRInst inst : instList) {
			if (inst instanceof StoreInst) {
				IRRegister address = (IRRegister) ((StoreInst) inst).getPtr();
				IRRegister res = (IRRegister) ((StoreInst) inst).getRes();
				stack.get(address).push(res);		
			}
			else if (inst instanceof LoadInst) {
				IRRegister address = (IRRegister) ((LoadInst) inst).getPtr();
				IRRegister res = (IRRegister) ((LoadInst) inst).getRes();
				res.replaceUse(stack.get(address).peek());
			}
		}
		
		ArrayList<IRBasicBlock> successors = block.getSuccessors();
		for (IRBasicBlock successor : successors) {
			ArrayList<Pair<IRRegister, PhiInst>> succPhiMap = successor.getPhiMap();
			for (Pair<IRRegister, PhiInst> pair : succPhiMap) {
				IRRegister address = pair.first;
				PhiInst phi = pair.second;
				
				assert stack.containsKey(address) && (!stack.get(address).isEmpty());
				
				phi.addBranch(stack.get(address).peek(), block);
			}
		}
		
		ArrayList<IRBasicBlock> dominaces = block.getDominaces();
		for (IRBasicBlock dominace : dominaces) {
			rename(dominace);
		}
		
		for (IRInst inst : instList) {
			if (inst instanceof StoreInst) {
				IRRegister address = (IRRegister) ((StoreInst) inst).getPtr();
				stack.get(address).pop();		
			}
		}
		for (Pair<IRRegister, PhiInst> pair : phiMap) {
			IRRegister address = pair.first;
			stack.get(address).pop();
		}
	}

}
