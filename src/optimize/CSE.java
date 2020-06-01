package optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.AllocaInst;
import IR.Inst.BinOpInst;
import IR.Inst.BitcastToInst;
import IR.Inst.BitwiseBinOpInst;
import IR.Inst.CallInst;
import IR.Inst.GetElementPtrInst;
import IR.Inst.IRInst;
import IR.Inst.IcmpInst;
import IR.Inst.LoadInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRConstBool;
import IR.Symbol.IRConstInt;
import IR.Symbol.IRConstString;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import utility.Pair;

public class CSE extends PASS {

	private enum Op {
        add(new String("add"),   true), 
        sub(new String("sub"),   false), 
        mul(new String("mul"),   true), 
        sdiv(new String("sdiv"), false), 
        srem(new String("srem"), false),
        shl(new String("shl"),   false), 
        ashr(new String("ashr"), false), 
        and(new String("and"),   true), 
        or(new String("or"),     true), 
        xor(new String("xor"),   true),
        eq(new String("eq"),     true), 
        ne(new String("ne"),     true), 
        sgt(new String("sgt"),   true), 
        sge(new String("sge"),   true), 
        slt(new String("slt"),   true),
		sle(new String("sle"),   true),
		ld(new String("ld"),     false), //load
		gep(new String("gep"),   false); //GetElementPtr
		
        private String name;
        private boolean commutable;
        
        private Op(String name, boolean commutable) {
        	this.name = name;
        	this.commutable = commutable;
        }
        
        @Override
        public String toString() {
        	return name;
        }
    }
	
	private class Expr {
		public Op op;
		public ArrayList<IRSymbol> syms;
	
		public Expr(Op op, IRSymbol s) {
			this.op = op;
			syms = new ArrayList<IRSymbol>();
			syms.add(s);
		}
		
		public Expr(Op op, IRSymbol s1, IRSymbol s2) {
			this.op = op;
			syms = new ArrayList<IRSymbol>();
			syms.add(s1);
			syms.add(s2);
		}
		
		public Expr(Op op, IRSymbol s1, IRSymbol s2, IRSymbol s3) {
			this.op = op;
			syms = new ArrayList<IRSymbol>();
			syms.add(s1);
			syms.add(s2);
			syms.add(s3);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Expr))
				return false;
			else if (op != ((Expr) obj).op)
				return false; 
			else if (syms.size() != ((Expr) obj).syms.size())
				return false;
			else {
				for (int i = 0; i < syms.size(); ++i) {
					IRSymbol a = syms.get(i), b = ((Expr) obj).syms.get(i);
					if (a instanceof IRRegister) {
						if (!(b instanceof IRRegister) || !a.equals(b))
							return false;
					}
					else if (a instanceof IRConstInt) {
						if (!(b instanceof IRConstInt) || 
								((IRConstInt)a).getValue() != ((IRConstInt)b).getValue())
							return false;
					}
					else if (a instanceof IRConstBool) {
						if (!(b instanceof IRConstBool) || 
								((IRConstBool)a).getValue() != ((IRConstBool)b).getValue())
							return false;
					}
					else if (a instanceof IRConstString) {
						if (!(b instanceof IRConstString) || 
								!((IRConstString)a).getValue().equals(((IRConstString)b).getValue()))
							return false;
					}
				}
			}	
			return true;
		}
		
		@Override
		public int hashCode() {
			int hash = 0;
			hash = syms.get(0).hashCode();
			//for (IRSymbol s : syms)
			//	hash += s.hashCode();
			return hash;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(op + " ");
			for (IRSymbol s : syms) {
				builder.append(s + " ");
			}
			return builder.toString();
		}
 	}
	
	private Expr commutation(Expr e) {
		assert e.syms.size() == 2;
		if (e.op == Op.slt)
			return new Expr(Op.sgt, e.syms.get(1), e.syms.get(0));
		else if (e.op == Op.sgt)
			return new Expr(Op.slt, e.syms.get(1), e.syms.get(0));
		else if (e.op == Op.sle)
			return new Expr(Op.sge, e.syms.get(1), e.syms.get(0));
		else if (e.op == Op.sge)
			return new Expr(Op.sle, e.syms.get(1), e.syms.get(0));
		else
			return new Expr(e.op, e.syms.get(1), e.syms.get(0));
	}
	
	
	private AliasAnalysis aa;
	private boolean changed;
	private HashMap<IRInst, Expr> exprMap;
	private HashMap<Expr, Stack<Pair<IRInst, IRRegister>> > stackMap;
	private ArrayList<IRInst> removeList;
	private ArrayList<IRRegister> replaceList;
	
	
	public CSE(IRModule module, AliasAnalysis aa) {
		super(module);
		this.aa = aa;
	}
	
	public boolean run() {
		changed = false;
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			elimination(function);
		}
		return changed;
	}
	
	public void elimination(IRFunction function) {
		exprMap = new HashMap<>();
		stackMap = new HashMap<>();
		removeList = new ArrayList<>();
		replaceList = new ArrayList<>();
		elimination(function.getEntranceBlock());
		
		if (removeList.size() > 0)
			changed = true;
		
		for (int i = 0; i < removeList.size(); ++i) {
			IRInst inst = removeList.get(i);
			IRRegister res = inst.getRes();
			IRRegister replace = replaceList.get(i);
			HashSet<IRInst> uses = res.getUseList();
			for (IRInst use : uses) {
				use.replaceUse(res, replace);
			}
			inst.removeItself();
		}
	}
	
	public void elimination(IRBasicBlock block) {
		ArrayList<IRInst> instList = block.getInstList();
		for (IRInst inst : instList) {
			Expr e = null;
			if (inst instanceof BinOpInst)
				e = toExpr((BinOpInst) inst);
			else if (inst instanceof BitwiseBinOpInst)
				e = toExpr((BitwiseBinOpInst) inst);
			else if (inst instanceof GetElementPtrInst) 
				e = toExpr((GetElementPtrInst) inst);
			else if (inst instanceof IcmpInst) 
				e = toExpr((IcmpInst) inst);
			else if (inst instanceof LoadInst)
				e = toExpr((LoadInst) inst);
			else 
				continue;
			
			Stack<Pair<IRInst, IRRegister>> stack = null; 
			if (stackMap.containsKey(e)) {
				stack = stackMap.get(e);
			}
			else if (e.op.commutable){
				Expr com = commutation(e);
				if (stackMap.containsKey(com)) {
					stack = stackMap.get(com);
					e = com;
				}
			}
			exprMap.put(inst, e);
				
			IRRegister res = inst.getRes();
			if (stack == null) {
				stack = new Stack<Pair<IRInst, IRRegister>>();
				stack.add(new Pair<IRInst, IRRegister>(inst, res));
				stackMap.put(e, stack);
			}
			else if (stack.isEmpty()) {
				stack.add(new Pair<IRInst, IRRegister>(inst, res));
			}
			else {
				Pair<IRInst, IRRegister> top = stack.peek();
				IRInst last = top.first;
				IRRegister replace = top.second;
				if (inst instanceof LoadInst) {
				//	System.err.println(inst);
					if (cheakAlias(inst, (IRRegister) ((LoadInst) inst).getPtr(), (LoadInst) last, new HashSet<IRInst>())) {
					//	System.err.println("check");
						removeList.add(inst);
						replaceList.add(replace);
						stack.push(new Pair<IRInst, IRRegister>(inst, replace));
					}
					else {
						stack.push(new Pair<IRInst, IRRegister>(inst, res));
					}
				}
				else {
						removeList.add(inst);
						replaceList.add(replace);
						stack.push(new Pair<IRInst, IRRegister>(inst, replace));
				}
			}
		}
		
		ArrayList<IRBasicBlock> doms = block.getDominaces();
		for (IRBasicBlock dom : doms) {
			elimination(dom);
		}

		for (IRInst inst : instList) {
			if (exprMap.containsKey(inst)) {
				stackMap.get(exprMap.get(inst)).pop();
			}
		}
	}
	
	private boolean cheakAlias(IRInst inst, IRRegister ptr, LoadInst goal, HashSet<IRInst>visited) {
		//System.err.println("checkAlias " + inst + " " + ptr + " " + goal);
		if (visited.contains(inst))
			return true;
		if (inst == goal)
			return true;
		if (inst instanceof StoreInst && aa.mayAlias(ptr, (IRRegister) ((StoreInst) inst).getPtr())) {
			return false;
		}
		if (inst instanceof CallInst && !module.isbuiltInFunction(((CallInst) inst).getFunction())) {
			return false;
		}
		visited.add(inst);
		if (inst.getPrev() != null) {
			return cheakAlias(inst.getPrev(), ptr, goal, visited);
		}
		else {
			ArrayList<IRBasicBlock> predecessors = inst.getCurrentBlock().getPredecessors();
			for (IRBasicBlock predecessor : predecessors) {
				if (!cheakAlias(predecessor.getTail(), ptr, goal, visited)) {
					return false;
				}
			}
			return true;
		}
	}
	
	private Expr toExpr(BinOpInst inst) {
		if (inst.op == BinOpInst.BinOpType.add)
			return new Expr(Op.add, inst.getLeft(), inst.getRight());
		else if (inst.op == BinOpInst.BinOpType.sub)
			return new Expr(Op.sub, inst.getLeft(), inst.getRight());
		else if (inst.op == BinOpInst.BinOpType.mul)
			return new Expr(Op.mul, inst.getLeft(), inst.getRight());
		else if (inst.op == BinOpInst.BinOpType.sdiv)
			return new Expr(Op.sdiv, inst.getLeft(), inst.getRight());
		else if (inst.op == BinOpInst.BinOpType.srem)
			return new Expr(Op.srem, inst.getLeft(), inst.getRight());	
		return null;
	}
	
	private Expr toExpr(BitwiseBinOpInst inst) {
		if (inst.op == BitwiseBinOpInst.BitwiseBinOpType.shl)
			return new Expr(Op.shl, inst.getLeft(), inst.getRight());
		else if (inst.op == BitwiseBinOpInst.BitwiseBinOpType.ashr)
			return new Expr(Op.ashr, inst.getLeft(), inst.getRight());
		else if (inst.op == BitwiseBinOpInst.BitwiseBinOpType.and)
			return new Expr(Op.and, inst.getLeft(), inst.getRight());
		else if (inst.op == BitwiseBinOpInst.BitwiseBinOpType.or)
			return new Expr(Op.or, inst.getLeft(), inst.getRight());
		else if (inst.op == BitwiseBinOpInst.BitwiseBinOpType.xor)
			return new Expr(Op.xor, inst.getLeft(), inst.getRight());
		else
			return null;
	}

	private Expr toExpr(GetElementPtrInst inst) {
		if (inst.getIndex1() == null)
			return new Expr(Op.gep, inst.getPtr(), inst.getIndex0());
		else
			return new Expr(Op.gep, inst.getPtr(), inst.getIndex0(), inst.getIndex1());
	}
	
	private Expr toExpr(IcmpInst inst) {
		if (inst.op == IcmpInst.IcmpOpType.eq)
			return new Expr(Op.eq, inst.getLeft(), inst.getRight());
		else if (inst.op == IcmpInst.IcmpOpType.ne)
			return new Expr(Op.ne, inst.getLeft(), inst.getRight());
		else if (inst.op == IcmpInst.IcmpOpType.sgt)
			return new Expr(Op.sgt, inst.getLeft(), inst.getRight());
		else if (inst.op == IcmpInst.IcmpOpType.sge)
			return new Expr(Op.sge, inst.getLeft(), inst.getRight());
		else if (inst.op == IcmpInst.IcmpOpType.slt)
			return new Expr(Op.slt, inst.getLeft(), inst.getRight());
		else if (inst.op == IcmpInst.IcmpOpType.sle)
			return new Expr(Op.sle, inst.getLeft(), inst.getRight());
		else
			return null;
	}
	
	private Expr toExpr(LoadInst inst) {
		return new Expr(Op.ld, inst.getPtr());
	}

}
