package optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.IRVisitor;
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
import IR.Symbol.IRConst;
import IR.Symbol.IRConstBool;
import IR.Symbol.IRConstInt;
import IR.Symbol.IRConstString;
import IR.Symbol.IRGlobalString;
import IR.Symbol.IRGlobalVariable;
import IR.Symbol.IRNull;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRArrayType;
import IR.Type.IRClassType;
import IR.Type.IRInt1Type;
import IR.Type.IRInt32Type;
import IR.Type.IRInt8Type;
import IR.Type.IRIntType;
import IR.Type.IRPtrType;
import IR.Type.IRType;
import IR.Type.IRVoidType;

public class SCCP extends PASS implements IRVisitor {
	//for SCCP
	public enum Status {
		undefined, constant, multiDefined;		
	}
	
	private boolean changed;
	private Queue<IRBasicBlock> queueBB;
	private Queue<IRRegister> queueREG;
	private HashMap<IRRegister, Status> statusMap;
	private HashMap<IRRegister, IRConst> constantMap;
	private HashSet<IRBasicBlock> executableSet;
	private ArrayList<IRInst> removeList;
	
	public SCCP(IRModule module) {
		super(module);
	}
	
	public boolean run() {
		changed = false;
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			function.accept(this);
		}	
		return changed;
	}

	private IRConst getConstant(IRRegister reg) {
		return constantMap.get(reg);
	}
	
	private Status getStatus(IRRegister reg) {
		return statusMap.containsKey(reg) ? statusMap.get(reg) : Status.undefined;
	}
	
	private void markConstant(IRRegister reg, IRConst constant) {
		if (getStatus(reg) != Status.constant) {
			constantMap.put(reg, constant);
			statusMap.put(reg, Status.constant);
			queueREG.offer(reg);		
		}
	}
	
	private void markMultiDefined(IRRegister reg) {
		if (getStatus(reg) != Status.multiDefined) {
			constantMap.put(reg, null);
			statusMap.put(reg, Status.multiDefined);
			queueREG.offer(reg);		
		}
	}
	
	private void markExecutable(IRBasicBlock block) {
		//System.err.println("executable "+ block);
		if (!executableSet.contains(block)) {
			executableSet.add(block);
			queueBB.offer(block);
		}
		else {
			for (IRInst inst = block.getHead(); inst instanceof PhiInst; inst = inst.getNext()) {
			//	System.err.println("ddddd  " + inst);
				inst.accept(this);
			}
		}
	}
	
	private IRConst toConstant(IRSymbol symbol) {
		return symbol instanceof IRConst ?  ((IRConst) symbol) : 
			(symbol instanceof IRRegister ? getConstant((IRRegister) symbol) : null); 
	}
	
	private boolean isMultiDefined(IRSymbol symbol) {
	//	if (symbol instanceof IRRegister)
	//		System.err.println(symbol + " ismultidefined " +  getStatus((IRRegister) symbol));
	//	else System.err.println(symbol + " not register");
		return (symbol instanceof IRRegister) && getStatus((IRRegister) symbol) == Status.multiDefined;
	}
	
	@Override
	public void visit(IRModule node) {
		
	}

	@Override
	public void visit(IRFunction node) {
		queueBB = new LinkedList<IRBasicBlock>();
		queueREG = new LinkedList<IRRegister>();
		statusMap = new HashMap<IRRegister, Status>();
		constantMap = new HashMap<IRRegister, IRConst>();
		executableSet = new HashSet<IRBasicBlock>();
		removeList = new ArrayList<IRInst>();
		
		ArrayList<IRRegister> parameters = node.getParameters();
		for (IRRegister parameter : parameters) {
			statusMap.put(parameter, Status.multiDefined);
		}
		
		IRBasicBlock entranceBlock = node.getEntranceBlock();
		queueBB.offer(entranceBlock);
		executableSet.add(entranceBlock);
		
		while(!queueBB.isEmpty() || !queueREG.isEmpty()) {
			if (!queueBB.isEmpty()) {
				queueBB.poll().accept(this);
			}
			if (!queueREG.isEmpty()) {
				IRRegister reg = queueREG.poll();
				//System.err.println("queueReg " + reg);
				HashSet<IRInst> useList = reg.getUseList();
				for (IRInst inst : useList) {
					inst.accept(this);
				}
			}
		}
		
		ArrayList<IRBasicBlock> bbs = node.getBlockList();
		for (IRBasicBlock block : bbs) {
			if (!executableSet.contains(block)) {
				//System.err.println("sccp " + block.getName());
				block.removeItself();
				block.removeAllInst();
				block.removeAllPhiUse();
				changed = true;
			}
			else {
				ArrayList<IRInst> instList = block.getInstList();
				for (IRInst inst : instList) {
					IRRegister res = inst.getRes();
					if (res != null && getStatus(res) == Status.constant) {
					//	System.err.println("sccp " + res + " " + inst);
						res.replaceUse(getConstant(res));
						removeList.add(inst);
						changed = true;
					}
				}
			}
		}
		
		for (IRInst inst : removeList) {
			inst.removeItself();
		}
	}

	@Override
	public void visit(IRBasicBlock node) {
		for (IRInst inst = node.getHead(); inst != null; inst = inst.getNext()) {
			inst.accept(this);
		}
	}

	@Override
	public void visit(IRInst node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(AllocaInst node) {
		markMultiDefined(node.getRes());
	}

	@Override
	public void visit(BitcastToInst node) {
		markMultiDefined(node.getRes());
	}
	
	@Override
	public void visit(CallInst node) {
		if (node.getRes() != null)
			markMultiDefined(node.getRes());
	}

	@Override
	public void visit(GetElementPtrInst node) {
		markMultiDefined(node.getRes());
	}
	
	@Override
	public void visit(LoadInst node) {
		markMultiDefined(node.getRes());
	}
	
	@Override
	public void visit(BinOpInst node) {
		IRRegister res = node.getRes();
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (getStatus(res) == Status.undefined) {
			IRConst leftConst = toConstant(left);
			IRConst rightConst = toConstant(right);
			if (leftConst != null && rightConst != null) {
				//System.err.println(node.op +  "  " + ((IRConstInt) leftConst).getValue() + " " +  ((IRConstInt) rightConst).getValue());
				if (node.op == BinOpInst.BinOpType.add) {
					markConstant(res, new IRConstInt((int) ((IRConstInt) leftConst).getValue() + ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.sub) {
					markConstant(res, new IRConstInt((int) ((IRConstInt) leftConst).getValue() - ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.mul) {
					markConstant(res, new IRConstInt((int) ((IRConstInt) leftConst).getValue() * ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.sdiv) {
					markConstant(res, new IRConstInt((int) ((IRConstInt) leftConst).getValue() / ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.srem) {
				//System.err.println("node " + node);
				//System.err.println("rem " + ((IRConstInt) leftConst).getValue() + " " +  ((IRConstInt) rightConst).getValue() + " " 
				//+((IRConstInt) leftConst).getValue() % ((IRConstInt) rightConst).getValue());
					if (((IRConstInt) rightConst).getValue() != 0)
						markConstant(res, new IRConstInt((int) ((IRConstInt) leftConst).getValue() % ((IRConstInt) rightConst).getValue()));
				}
			}
			else if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
		else if (getStatus(res) == Status.constant) {
			 if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
	}

	@Override
	public void visit(BitwiseBinOpInst node) {
		IRRegister res = node.getRes();
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (getStatus(res) == Status.undefined) {
			IRConst leftConst = toConstant(left);
			IRConst rightConst = toConstant(right);
			if (leftConst != null && rightConst != null) {
				//System.err.println(node);
				//System.err.println(node.op +  "  " + ((IRConstInt) leftConst).getValue() + " " +  ((IRConstInt) rightConst).getValue());
				if (node.op == BitwiseBinOpInst.BitwiseBinOpType.and) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() & ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BitwiseBinOpInst.BitwiseBinOpType.or) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() | ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BitwiseBinOpInst.BitwiseBinOpType.shl) {
					markConstant(res, new IRConstInt((int) ((IRConstInt) leftConst).getValue() << ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BitwiseBinOpInst.BitwiseBinOpType.ashr) {
					markConstant(res, new IRConstInt((int) ((IRConstInt) leftConst).getValue() >> ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BitwiseBinOpInst.BitwiseBinOpType.xor) {
					if (leftConst.getType() instanceof IRInt32Type)
						markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() ^ ((IRConstInt) rightConst).getValue()));
					else if (leftConst.getType() instanceof IRInt1Type)
						markConstant(res, new IRConstBool(((IRConstBool) leftConst).getValue() ^ ((IRConstBool) rightConst).getValue()));
				}
			}
			else if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
		else if (getStatus(res) == Status.constant) {
			 if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
	}

	@Override
	public void visit(IcmpInst node) {
		//System.err.println("icmp " + node);
		IRRegister res = node.getRes();
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (getStatus(res) == Status.undefined) {
			IRConst leftConst = toConstant(left);
			IRConst rightConst = toConstant(right);
			if (leftConst != null && rightConst != null) {
				if (node.op == IcmpInst.IcmpOpType.eq) {
					//if (res.getName().contains("eq.7")) System.err.println("caooooooooo");
					if (leftConst instanceof IRConstInt) {
						
						markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() == ((IRConstInt) rightConst).getValue()));
					//	System.err.println("eq " + res + " " + new IRConstBool(((IRConstInt) leftConst).getValue() == ((IRConstInt) rightConst).getValue()));
					}
					else if (leftConst instanceof IRConstBool)
						markConstant(res, new IRConstBool(((IRConstBool) leftConst).getValue() == ((IRConstBool) rightConst).getValue()));
				}
				else if (node.op == IcmpInst.IcmpOpType.ne) {
					if (leftConst instanceof IRConstInt)
						markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() != ((IRConstInt) rightConst).getValue()));
					else if (leftConst instanceof IRConstBool)
						markConstant(res, new IRConstBool(((IRConstBool) leftConst).getValue() != ((IRConstBool) rightConst).getValue()));
				}
				else if (node.op == IcmpInst.IcmpOpType.sgt) {
					markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() > ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == IcmpInst.IcmpOpType.sge) {
					markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() >= ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == IcmpInst.IcmpOpType.slt) {
					markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() < ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == IcmpInst.IcmpOpType.sle) {
					markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() <= ((IRConstInt) rightConst).getValue()));
				}
			}
			else if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
		else if (getStatus(res) == Status.constant) {
			 if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
	}


	@Override
	public void visit(BrInst node) {
		//System.err.println("brInst " + node);
		IRSymbol cond = node.getCond();
		if (cond == null) {
		//	System.err.println("brinst " + node.getTrue());
			markExecutable(node.getTrue());
		}
		else {
			IRConstBool constant = (IRConstBool) toConstant(cond);
			//System.err.println("constant " + constant);
			if (constant != null) {
				if (constant.getValue()) {
					markExecutable(node.getTrue());
				}
				else {
					markExecutable(node.getFalse());
				}
			}
			else {
				markExecutable(node.getTrue());
				markExecutable(node.getFalse());
			}
		}
	}

	@Override
	public void visit(PhiInst node) {
	//	System.err.println("visit " + node);
		IRConst constant = null;
		IRRegister res = node.getRes();
		ArrayList<IRSymbol> symbols = node.getSymbols();
		ArrayList<IRBasicBlock> bbs = node.getBBs();
		for (int i = 0; i < symbols.size(); ++i) {
			IRSymbol symbol = symbols.get(i);
			IRBasicBlock bb = bbs.get(i);
			if (executableSet.contains(bb)) {
			//	System.err.println("for " + i + " " + symbol + " " + bb + " " + isMultiDefined(symbol));	
				if (isMultiDefined(symbol)) {
					markMultiDefined(res);
					return;
				}
				IRConst tmp = toConstant(symbol);
				if (tmp != null) {
					//System.err.println("constant " + constant + " " + tmp);
					if (constant == null) constant = tmp;
					else if (!constant.valueEquals(tmp)) {
						markMultiDefined(res);
						return;
					}	
					
				}
			}
		}
		if (constant != null) {
		//	System.err.println("constant " + constant);
			markConstant(res, constant);
		}
		//doubt : constant and undefined.
	}
	
	@Override
	public void visit(RetInst node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StoreInst node) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(MoveInst node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRSymbol node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRRegister node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRGlobalVariable node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRGlobalString node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRConstInt node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRConstBool node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRConstString node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRNull node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRType node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRIntType node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRInt1Type node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRInt8Type node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRInt32Type node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRVoidType node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRClassType node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRPtrType node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRArrayType node) {
		// TODO Auto-generated method stub
		
	}



}
