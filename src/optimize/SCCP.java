package optimize;

import java.util.ArrayList;
import java.util.Collection;
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
	private Queue<IRBasicBlock> queueBB;
	private Queue<IRRegister> queueREG;
	
	public SCCP(IRModule module) {
		super(module);
		visit(module);
	}

	private void markConstant(IRRegister reg, IRConst constant) {
		if (reg.status != IRRegister.Status.constant) {
			reg.setConstant(constant);
			reg.status = IRRegister.Status.constant;
			queueREG.offer(reg);		
		}
	}
	
	private void markMultiDefined(IRRegister reg) {
		if (reg.status != IRRegister.Status.multiDefined) {
			//System.err.println("mult " + reg);
			reg.setConstant(null);
			reg.status = IRRegister.Status.multiDefined;
			queueREG.offer(reg);		
		}
	}
	
	private void markExecutable(IRBasicBlock block) {
		if (!block.getExecutable()) {
			block.setExecutable();
			queueBB.offer(block);
		}
		else {
			for (IRInst inst = block.getHead(); inst instanceof PhiInst; inst = inst.getNext()) {
				inst.accept(this);
			}
		}
	}
	
	private IRConst toConstant(IRSymbol symbol) {
		return symbol instanceof IRConst ?  ((IRConst) symbol) : 
			(symbol instanceof IRRegister ? ((IRRegister) symbol).getConstant() : null); 
	}
	
	private boolean isMultiDefined(IRSymbol symbol) {
		return (symbol instanceof IRRegister) && ((IRRegister) symbol).status == IRRegister.Status.multiDefined;
	}
	
	@Override
	public void visit(IRModule node) {
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			function.accept(this);
		}
	}

	@Override
	public void visit(IRFunction node) {
		ArrayList<IRRegister> parameters = node.getParameters();
		for (IRRegister parameter : parameters) {
			parameter.status = IRRegister.Status.multiDefined;
		}
		
		queueBB = new LinkedList<IRBasicBlock>();
		queueREG = new LinkedList<IRRegister>();
		IRBasicBlock entranceBlock = node.getEntranceBlock();
		queueBB.offer(entranceBlock);
		entranceBlock.setExecutable();
		
		while(!queueBB.isEmpty() || !queueREG.isEmpty()) {
			if (!queueBB.isEmpty()) {
				queueBB.poll().accept(this);
			}
			
			if (!queueREG.isEmpty()) {
				IRRegister reg = queueREG.poll();
				HashSet<IRInst> useList = reg.getUseList();
				for (IRInst inst : useList) {
					inst.accept(this);
				}
			}
		}
		
		ArrayList<IRBasicBlock> bbs = node.getBlockList();
		for (IRBasicBlock block : bbs) {
			if (!block.getExecutable()) {
				block.removeItself();
				block.removeAllInst();
				block.removeAllPhiUse();
			}
			else {
				ArrayList<IRInst> instList = block.getInstList();
				for (IRInst inst : instList) {
					IRRegister res = inst.getRes();
					if (res != null && res.status == IRRegister.Status.constant) {
						res.replaceUse(res.getConstant());
						inst.removeItself();
					}
				}
			}
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
		if (res.status == IRRegister.Status.undefined) {
			IRConst leftConst = toConstant(left);
			IRConst rightConst = toConstant(right);
			if (leftConst != null && rightConst != null) {
				if (node.op == BinOpInst.BinOpType.add) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() + ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.sub) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() - ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.mul) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() * ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.sdiv) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() / ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BinOpInst.BinOpType.srem) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() % ((IRConstInt) rightConst).getValue()));
				}
			}
			else if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
		else if (res.status == IRRegister.Status.constant) {
			 if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
	}

	@Override
	public void visit(BitwiseBinOpInst node) {
		IRRegister res = node.getRes();
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (res.status == IRRegister.Status.undefined) {
			IRConst leftConst = toConstant(left);
			IRConst rightConst = toConstant(right);
			if (leftConst != null && rightConst != null) {
				if (node.op == BitwiseBinOpInst.BitwiseBinOpType.and) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() & ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BitwiseBinOpInst.BitwiseBinOpType.or) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() | ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BitwiseBinOpInst.BitwiseBinOpType.shl) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() << ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == BitwiseBinOpInst.BitwiseBinOpType.ashr) {
					markConstant(res, new IRConstInt(((IRConstInt) leftConst).getValue() >> ((IRConstInt) rightConst).getValue()));
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
		else if (res.status == IRRegister.Status.constant) {
			 if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
	}

	@Override
	public void visit(IcmpInst node) {
		IRRegister res = node.getRes();
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (res.status == IRRegister.Status.undefined) {
			IRConst leftConst = toConstant(left);
			IRConst rightConst = toConstant(right);
			if (leftConst != null && rightConst != null) {
				if (node.op == IcmpInst.IcmpOpType.eq) {
					markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() == ((IRConstInt) rightConst).getValue()));
				}
				else if (node.op == IcmpInst.IcmpOpType.ne) {
					markConstant(res, new IRConstBool(((IRConstInt) leftConst).getValue() != ((IRConstInt) rightConst).getValue()));
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
		else if (res.status == IRRegister.Status.constant) {
			 if (isMultiDefined(left) || isMultiDefined(right)){
				markMultiDefined(res);
			}
		}
	}


	@Override
	public void visit(BrInst node) {
		//System.err.println("BrInst " + node);
		IRSymbol cond = node.getCond();
		if (cond == null) {
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
		//System.err.println("visit " + node);
		IRConst constant = null;
		IRRegister res = node.getRes();
		ArrayList<IRSymbol> symbols = node.getSymbols();
		ArrayList<IRBasicBlock> bbs = node.getBBs();
		for (int i = 0; i < symbols.size(); ++i) {
			IRSymbol symbol = symbols.get(i);
			IRBasicBlock bb = bbs.get(i);
			if (bb.getExecutable()) {
			//	System.err.println("for " + i + " " + symbol + " " + bb + " " + isMultiDefined(symbol));
				
				if (isMultiDefined(symbol)) {
					markMultiDefined(res);
					return;
				}
				IRConst tmp = toConstant(symbol);
				if (tmp != null) {
					if (constant == null) constant = tmp;
					else if (!constant.equals(tmp)) {
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
