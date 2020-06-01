package optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.BitcastToInst;
import IR.Inst.CallInst;
import IR.Inst.GetElementPtrInst;
import IR.Inst.IRInst;
import IR.Inst.LoadInst;
import IR.Inst.PhiInst;
import IR.Inst.RetInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRConstInt;
import IR.Symbol.IRGlobalString;
import IR.Symbol.IRGlobalVariable;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRPtrType;

public class AliasAnalysis extends PASS {

	private class Pointer {
		public String id;
		public HashSet<Pointer> pointTo;
		public HashSet<Pointer> moves;
		public HashSet<Pointer> loads;
		public HashSet<Pointer> stores;
		
		public Pointer(String id) {
			this.id = id;
			pointTo = new HashSet<Pointer>();
			moves = new HashSet<Pointer>();
			loads = new HashSet<Pointer>();
			stores = new HashSet<Pointer>();
		}
		
		public String toString() {
			return id;
		}
	}
	
	private HashMap<IRRegister, Pointer> map;
	private Queue<Pointer> queue;
    private HashSet<Pointer> inQueue;
    
	public AliasAnalysis(IRModule module) {
		super(module);
	}
	
	public boolean mayAlias(IRRegister a, IRRegister b) {
		if (a == b) 
			return true;
		else if (a instanceof IRGlobalString || b instanceof IRGlobalString)
			return false;
		//System.err.println(a.getType().toString() + " " + b.getType().toString() + " " + a.getType().toString().equals(b.getType().toString()));
		if (!a.getType().toString().equals(b.getType().toString()))
			return false;
		else
			//return false;
			return !Collections.disjoint(map.get(a).pointTo, map.get(b).pointTo); //disjoint		 
	}
	
	public void run() {
		map = new HashMap<>();
		queue =  new LinkedList<>();
		inQueue = new HashSet<>();
		
		//find all pointers
		Collection<IRGlobalString> stringList = module.getStringList().values();
		for (IRGlobalString str : stringList) {
			Pointer pointer = new Pointer(str.getName());
			pointer.pointTo.add(new Pointer(str.getName() + ".value"));
			map.put(str, pointer);
		}
		
		Collection<IRGlobalVariable> varList = module.getGlobalVarList().values();
		for (IRGlobalVariable var : varList) {
			Pointer pointer = new Pointer(var.getName());
		//	if (var.getName().contains("g")) {
		//		System.err.println(var + " " + var.getType() + " " + ((IRPtrType) var.getType()).getType());
		//	}
			pointer.pointTo.add(new Pointer(var.getName() + ".value"));
			map.put(var, pointer);
		}
		
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			ArrayList<IRRegister> parameters = function.getParameters();
			for (IRRegister parameter : parameters) {
				if (parameter.getType() instanceof IRPtrType) {
					map.put(parameter, new Pointer(parameter.getName()));
				}
			}
			ArrayList<IRBasicBlock> blocks = function.getBlockList();
			for (IRBasicBlock block : blocks) {
				for (IRInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
					IRRegister res = inst.getRes();
					if (res != null && (res.getType() instanceof IRPtrType)) {
						map.put(res, new Pointer(res.getName()));
					}
				}
			}
		}	
		
		//construct constraints
		for (IRFunction function : functions) {
			ArrayList<IRBasicBlock> blocks = function.getBlockList();
			for (IRBasicBlock block : blocks) {
				for (IRInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
					if (inst instanceof BitcastToInst) {
						addConstraints((BitcastToInst) inst);
					}
					else if (inst instanceof CallInst) {
						addConstraints((CallInst) inst);
					}
					else if (inst instanceof GetElementPtrInst) {
						addConstraints((GetElementPtrInst) inst);
					}
					else if (inst instanceof LoadInst) {
						addConstraints((LoadInst) inst);
					}
					else if (inst instanceof PhiInst) {
						addConstraints((PhiInst) inst);
					}
					else if (inst instanceof StoreInst) {
						addConstraints((StoreInst) inst);
					}
				}
			}
		}	
		
		Collection<Pointer> pointers = map.values();
		for (Pointer pointer : pointers) {
			if (!pointer.pointTo.isEmpty()) {
		//		System.err.println("not empty " + pointer);
				queue.offer(pointer);
				inQueue.add(pointer);
			}
		}
		
		while (!queue.isEmpty()) {
			Pointer pointer = queue.poll();
			inQueue.remove(pointer);
			
			for (Pointer load : pointer.loads) {
				for (Pointer pointTo : pointer.pointTo) {
					if (!pointTo.moves.contains(load)) {
						pointTo.moves.add(load);
						if (!inQueue.contains(pointTo)) {
							inQueue.add(pointTo);
							queue.offer(pointTo);
						}
					}
				}
			}
			for (Pointer store : pointer.stores) {
				for (Pointer pointTo : pointer.pointTo) {
					if (!store.moves.contains(pointTo)) {
						store.moves.add(pointTo);
						if (!inQueue.contains(store)) {
							inQueue.add(store);
							queue.offer(store);
						}
					}
				}
			}
			for (Pointer move: pointer.moves) {
				if (move.pointTo.addAll(pointer.pointTo)) {
					if (!inQueue.contains(move)) {
						inQueue.add(move);
						queue.offer(move);
					}
				}
			}
		}
		
		/*
		for (Pointer pointer : pointers) {
			System.err.println("pointTo " + pointer + " " + pointer.pointTo);
		}
		for (Pointer a : pointers) {
			for (Pointer b : pointers) {
				System.err.println(a + " " + b + " " + a.pointTo + " " + b.pointTo + " " + !Collections.disjoint(a.pointTo, b.pointTo));
			}
		}
		
		Collection<IRRegister> regs = map.keySet();
		for (IRRegister a : regs) {
			for (IRRegister b : regs) {
				if (mayAlias(a, b))
					System.err.println("mayAlias : " + a + " " + b);
			}
		}*/
		
	}
	
	private void addConstraints(BitcastToInst inst) {
		IRRegister src = (IRRegister) inst.getSrc();
		IRRegister res = inst.getRes();
		if ((src.getType() instanceof IRPtrType) && (res.getType() instanceof IRPtrType)) {
			map.get(src).moves.add(map.get(res));
			//System.err.println(src + " move " + res);
		}
	}
	
	private void addConstraints(CallInst inst) {
		IRRegister res = inst.getRes();
		IRFunction function = inst.getFunction();
		if (module.isbuiltInFunction(function)) {
			if (res != null && res.getType() instanceof IRPtrType) {
				Pointer ret = new Pointer(function.getName() + ".returnValue");
				map.get(res).pointTo.add(ret);
			}
		}
		else {
			if (res != null && res.getType() instanceof IRPtrType) {
				RetInst ret = (RetInst) function.getExitBlock().getTail();
				IRSymbol retValue = ret.getValue();
				if (retValue instanceof IRRegister && retValue.getType() instanceof IRPtrType) {
					map.get(retValue).moves.add(map.get(res));	
				}
			}
			ArrayList<IRRegister> formal_args = function.getParameters();
			ArrayList<IRSymbol> actual_args = inst.getParameters();
			assert formal_args.size() == actual_args.size();
			for (int i = 0; i < formal_args.size(); ++i) {
				IRRegister formal_arg = formal_args.get(i);
				IRSymbol actual_arg =  actual_args.get(i);
				if (actual_arg instanceof IRRegister && formal_arg.getType() instanceof IRPtrType && actual_arg.getType() instanceof IRPtrType) {
					map.get((IRRegister) actual_arg).moves.add(map.get(formal_arg));
				}
			}
	
		}
	}

	private void addConstraints(GetElementPtrInst inst) {
		IRRegister res = inst.getRes();
		if (res.getType() instanceof IRPtrType) {
			if (inst.getPtr() instanceof IRRegister) {
				IRRegister ptr = (IRRegister) inst.getPtr();
				map.get(ptr).moves.add(map.get(res));
		//		System.err.println("move " + map.get(ptr) + " " + map.get(res));
			}
		}
	}
	
	private void addConstraints(LoadInst inst) {
		IRRegister res = inst.getRes();
		if (res.getType() instanceof IRPtrType) {
			IRRegister ptr = (IRRegister) inst.getPtr();
			map.get(ptr).loads.add(map.get(res));
			//System.err.println(ptr + " load " + res);
		}
	}
	
	private void addConstraints(StoreInst inst) {
		IRSymbol val = inst.getValue();
		if (val instanceof IRRegister && val.getType() instanceof IRPtrType) {
			IRRegister ptr = (IRRegister) inst.getPtr();
			map.get(ptr).stores.add(map.get(val));
			//System.err.println(ptr + " store " + val);
		}
	}

	private void addConstraints(PhiInst inst) {
		IRRegister res = inst.getRes();
		if (res.getType() instanceof IRPtrType) {
			ArrayList<IRSymbol> symbols = inst.getSymbols();
			for (IRSymbol s : symbols) {
				if (s instanceof IRRegister && s.getType() instanceof IRPtrType) {
					//System.err.println((IRRegister) s + " " + map.containsKey((IRRegister) s));
					map.get((IRRegister) s).moves.add(map.get(res));	
				}
			}
		}
	}
}
