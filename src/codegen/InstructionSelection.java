package codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvModule;
import Riscv.Inst.RvCall;
import Riscv.Inst.RvCmpZ;
import Riscv.Inst.RvInst;
import Riscv.Inst.RvJ;
import Riscv.Inst.RvJr;
import Riscv.Inst.RvLi;
import Riscv.Inst.RvLoad;
import Riscv.Inst.RvLui;
import Riscv.Inst.RvMove;
import Riscv.Inst.RvStore;
import Riscv.Inst.RvTypeB;
import Riscv.Inst.RvTypeI;
import Riscv.Inst.RvTypeR;
import Riscv.Operand.RegisterTable;
import Riscv.Operand.RvAddress;
import Riscv.Operand.RvGlobalString;
import Riscv.Operand.RvGlobalVariable;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvPhysicalRegister;
import Riscv.Operand.RvRegister;
import Riscv.Operand.RvStackSlot;
import Riscv.Operand.RvVirtualRegister;

public class InstructionSelection implements IRVisitor {

	private final int IMM_MAX = 2047;
    private final int IMM_MIN = -2048;
    private RvFunction currentFunction;
    private RvBlock currentBlock;
    private RvModule module;
    private IRModule irModule;
    private RvVirtualRegister[] calleeSaved;
    
	public InstructionSelection(IRModule irModule) {
		this.irModule = irModule;
		this.calleeSaved = new RvVirtualRegister[13];
	}
	
	public RvModule run() {
		module = new RvModule();
		visit(irModule);
		return module;
	}
	
	private boolean canBeImm(int value) {
		return value >= IMM_MIN && value <= IMM_MAX;
	}
	
	private boolean canBeImm(long value) {
		return value >= IMM_MIN && value <= IMM_MAX;
	}
	
	private boolean canBeShamt(int value) {
		if (value < 0) {
			System.err.println("The shamt is negative.");
			return false;
		}
		return value < 32;
	}
	
	private RvRegister toRvRegister(IRSymbol symbol) {
		if (symbol instanceof IRConstInt) {
			int value = (int) ((IRConstInt) symbol).getValue();
			RvVirtualRegister tmp = currentFunction.newRegister("tmp");
			currentBlock.addInst(new RvLi(currentBlock, tmp, new RvImm(value)));
			return tmp;
		}
		else if (symbol instanceof IRConstBool) {
			boolean value = ((IRConstBool) symbol).getValue();
			if (!value) return RegisterTable.zero;
			else {
				RvVirtualRegister tmp = currentFunction.newRegister("tmp");
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, tmp, RegisterTable.zero, new RvImm(1)));
				return tmp;
			}
		}
		else if (symbol instanceof IRNull) {
			return RegisterTable.zero;
		}
		else if (symbol instanceof IRRegister) {
			RvRegister res = ((IRRegister) symbol).getRvReg();
			if (res != null) return res;
			res = currentFunction.newRegister(((IRRegister) symbol).getName());
			((IRRegister) symbol).setRvReg(res);
			//System.err.println("----save " + symbol);
			return res;
		}
		return null;
	}
	
	private RvRegister toRvRegister(int value) {
		RvVirtualRegister tmp = currentFunction.newRegister("tmp");
		currentBlock.addInst(new RvLi(currentBlock, tmp, new RvImm(value)));
		return tmp;
	}
	
	@Override
	public void visit(IRModule node) {
		Collection<IRFunction> functions = node.getFunctList().values();
		for (IRFunction function : functions) {
			RvFunction rvFunction = new RvFunction(function.getName().substring(1), function.getParameters().size());
			function.setRvFunction(rvFunction);
			//System.err.println("function: " + function + " " + rvFunction.getName());
			module.addFunction(rvFunction);
		}
		
		Collection<IRGlobalString> stringList = node.getStringList().values();
		for (IRGlobalString str : stringList) {
			RvGlobalString res = new RvGlobalString(str.getName(), str.getValue().getValue());
			str.setRvGlobalString(res);
			module.addGlobalString(res);
		}
			
		Collection<IRGlobalVariable> globalVarList = node.getGlobalVarList().values();
		for (IRGlobalVariable var : globalVarList) {
			RvGlobalVariable res = new RvGlobalVariable(var.getName());
			var.setRvGlobalVariable(res);
			module.addGlobalVariable(res);
			IRSymbol init = var.getInit();
			if (init != null) {
				//System.err.println("init " + init + " " + (init instanceof IRConstInt));
				if (init instanceof IRGlobalString) {
					RvGlobalString str = ((IRGlobalString) init).toRvGlobalString();
					res.setValue(str);	
				}
				if (init instanceof IRConstInt) {
					RvImm imm = new RvImm((int) ((IRConstInt) init).getValue());
					res.setValue(imm);
				}
			}
		}
		
		for (IRFunction function : functions) {
			function.accept(this);
		}
	}

	@Override
	public void visit(IRFunction node) {
		currentFunction = node.toRvFunction();
		//System.err.println(currentFunction.getName());
		
		ArrayList<IRBasicBlock> blocks = node.getBlockList();
		for (IRBasicBlock block : blocks) {
			RvBlock rvBlock = new RvBlock(block.getName(), node.getName().substring(1), currentFunction);
			block.setRvBlock(rvBlock);
			currentFunction.addBlock(rvBlock);
		}
		//callee saved registers
		IRBasicBlock entranceBlock = node.getEntranceBlock();
		currentBlock = entranceBlock.toRvBlock();
		currentFunction.setEntranceBlock(currentBlock);
		
		//ra
		calleeSaved[12] = currentFunction.newRegister("ra");
		currentBlock.addInst(new RvMove(currentBlock, calleeSaved[12], RegisterTable.ra));	
		for (int i = 0; i < 12; ++i) {
			RvPhysicalRegister reg = RegisterTable.calleeSavedRegisters[i];
			calleeSaved[i] = currentFunction.newRegister("calleeSaved");
			currentBlock.addInst(new RvMove(currentBlock, calleeSaved[i], reg));
		}
		
	//	System.err.println("head " + currentBlock.getHead());
		
		//function arguments
		ArrayList<IRRegister> parameters = node.getParameters();
		for (int i = 0; i < 8 && i < parameters.size(); ++i) {
			currentBlock.addInst(new RvMove(currentBlock, toRvRegister(parameters.get(i)), RegisterTable.argumentRegisters[i]));
		}
		for (int i = 8; i < parameters.size(); ++i) {
			currentBlock.addInst(new RvLoad(currentBlock, toRvRegister(parameters.get(i)), new RvStackSlot((i - 8) << 2, 2)));
		}
		
		for (IRBasicBlock block : blocks) {
			block.accept(this);
		}
	}

	@Override
	public void visit(IRBasicBlock node) {
		currentBlock = node.toRvBlock();
		//System.err.println(currentBlock.getName());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BinOpInst node) {
		BinOpInst.BinOpType op = node.getOp();
		IRRegister res = node.getRes();
		//System.err.println("binop " + res);
		RvRegister result = toRvRegister(res);
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (op == BinOpInst.BinOpType.add) {
			if (right instanceof IRConstInt) {
				int rightValue = (int) ((IRConstInt)right).getValue();
				if (canBeImm(rightValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, result, toRvRegister(left), new RvImm(rightValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.add, result, toRvRegister(left), toRvRegister(right)));		
				}
			}
			else if (left instanceof IRConstInt) {
				int leftValue = (int) ((IRConstInt)left).getValue();
				if (canBeImm(leftValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, result, toRvRegister(right), new RvImm(leftValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.add, result, toRvRegister(left), toRvRegister(right)));		
				}
			}
			else {
				currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.add, result, toRvRegister(left), toRvRegister(right)));		
			}
		}
		else if (op == BinOpInst.BinOpType.sub) {
			if (right instanceof IRConstInt) {
				int rightValue = -(int) ((IRConstInt)right).getValue();
				if (canBeImm(rightValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, result, toRvRegister(left), new RvImm(rightValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.sub, result, toRvRegister(left), toRvRegister(right)));		
				}
			}
			else {
				currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.sub, result, toRvRegister(left), toRvRegister(right)));		
			}
		}
		else if (op == BinOpInst.BinOpType.mul) {
			currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.mul, result, toRvRegister(left), toRvRegister(right)));		
		}
		else if (op == BinOpInst.BinOpType.sdiv) {
			currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.div, result, toRvRegister(left), toRvRegister(right)));		
		}
		else if (op == BinOpInst.BinOpType.srem) {
			currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.rem, result, toRvRegister(left), toRvRegister(right)));		
		}
	}

	@Override
	public void visit(BitcastToInst node) {
		IRRegister res = node.getRes();
		IRSymbol src = node.getSrc();
		currentBlock.addInst(new RvMove(currentBlock, toRvRegister(res), toRvRegister(src)));
	}

	@Override
	public void visit(BitwiseBinOpInst node) {
		BitwiseBinOpInst.BitwiseBinOpType op = node.getOp();
		IRRegister res = node.getRes();
		RvRegister result = toRvRegister(res);
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (op == BitwiseBinOpInst.BitwiseBinOpType.shl || op == BitwiseBinOpInst.BitwiseBinOpType.ashr) { 
			RvTypeI.Op typeI = op == BitwiseBinOpInst.BitwiseBinOpType.shl ? RvTypeI.Op.slli :  RvTypeI.Op.srai;
			RvTypeR.Op typeR = op == BitwiseBinOpInst.BitwiseBinOpType.shl ? RvTypeR.Op.sll :  RvTypeR.Op.sra;
			if (right instanceof IRConstInt) {
				int rightValue = (int) ((IRConstInt)right).getValue();
				if (canBeShamt(rightValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, typeI, result, toRvRegister(left), new RvImm(rightValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, typeR, result, toRvRegister(left), toRvRegister(right)));		
				}
			}
			else {
				currentBlock.addInst(new RvTypeR(currentBlock, typeR, result, toRvRegister(left), toRvRegister(right)));		
			}
		}
		else {
			RvTypeI.Op typeI;
			RvTypeR.Op typeR;
			if (op == BitwiseBinOpInst.BitwiseBinOpType.and) {
				typeI = RvTypeI.Op.andi; typeR =  RvTypeR.Op.and;
			}
			else if (op == BitwiseBinOpInst.BitwiseBinOpType.or) {
				typeI = RvTypeI.Op.ori; typeR =  RvTypeR.Op.or;
			}
			else {
				typeI = RvTypeI.Op.xori; typeR =  RvTypeR.Op.xor;
			}
			
			if (right instanceof IRConstInt) {
				int rightValue = (int) ((IRConstInt)right).getValue();
				if (canBeImm(rightValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, typeI, result, toRvRegister(left), new RvImm(rightValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, typeR, result, toRvRegister(left), toRvRegister(right)));		
				}
			}
			else if (left instanceof IRConstInt) {
				int leftValue = (int) ((IRConstInt)left).getValue();
				if (canBeImm(leftValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, typeI, result, toRvRegister(right), new RvImm(leftValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, typeR, result, toRvRegister(left), toRvRegister(right)));		
				}
			}
			else {
				currentBlock.addInst(new RvTypeR(currentBlock, typeR, result, toRvRegister(left), toRvRegister(right)));		
			}
		}
	}
	
	private void handleIcmpWithBranch(IcmpInst inst, IRRegister reg) {
		HashSet<IRInst> useList = reg.getUseList();
		IRSymbol left = inst.getLeft(), right = inst.getRight();
		RvTypeB.Op op = null;
		switch (inst.getOp()) {
	        case eq: op = RvTypeB.Op.beq; break;
	        case ne: op = RvTypeB.Op.bne; break;
	        case slt: op = RvTypeB.Op.blt; break;
	        case sle: op = RvTypeB.Op.ble; break;
	        case sgt: op = RvTypeB.Op.bgt; break;
	        case sge: op = RvTypeB.Op.bge; break;
		}
		for (IRInst brInst : useList) {
			 ((BrInst) brInst).setRvInst(new RvTypeB(currentBlock, op, toRvRegister(left), toRvRegister(right), ((BrInst) brInst).getTrue().toRvBlock()));
		}
	}
	
	@Override
	public void visit(IcmpInst node) {
		IRRegister res = node.getRes();
		if (res.onlyBeUsedByBranch()) {
			handleIcmpWithBranch(node, res);
			return;
		}
		IcmpInst.IcmpOpType op = node.getOp();
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (op == IcmpInst.IcmpOpType.eq || op == IcmpInst.IcmpOpType.ne) {
			RvRegister cmpzRes = toRvRegister(res);
			if (right instanceof IRNull) {
				currentBlock.addInst(new RvCmpZ(currentBlock, op == IcmpInst.IcmpOpType.eq ? RvCmpZ.Op.seqz : RvCmpZ.Op.snez, cmpzRes, toRvRegister(left)));
			}
			else if (left instanceof IRNull) {
				currentBlock.addInst(new RvCmpZ(currentBlock, op == IcmpInst.IcmpOpType.eq ? RvCmpZ.Op.seqz : RvCmpZ.Op.snez, cmpzRes, toRvRegister(right)));
			}
			else {
				RvVirtualRegister xorRes = (RvVirtualRegister) toRvRegister(res);
				if (right instanceof IRConstInt) {
					int rightValue = (int) ((IRConstInt) right).getValue();
					if (canBeImm(rightValue)) {
						currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.xori, xorRes, toRvRegister(left), new RvImm(rightValue)));
					}
					else {
						currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.xor, xorRes, toRvRegister(left), toRvRegister(right)));		
					}
				}
				else if (left instanceof IRConstInt) {
					int leftValue = (int) ((IRConstInt)left).getValue();
					if (canBeImm(leftValue)) {
						currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.xori, xorRes, toRvRegister(right), new RvImm(leftValue)));
					}
					else {
						currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.xor, xorRes, toRvRegister(left), toRvRegister(right)));		
					}
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.xor, xorRes, toRvRegister(left), toRvRegister(right)));		
				}
				currentBlock.addInst(new RvCmpZ(currentBlock, op == IcmpInst.IcmpOpType.eq ? RvCmpZ.Op.seqz : RvCmpZ.Op.snez, cmpzRes, xorRes));
			} 
		}
		else if (op == IcmpInst.IcmpOpType.slt || op == IcmpInst.IcmpOpType.sge) {
			RvRegister sltRes = toRvRegister(res);  
			if (right instanceof IRConstInt) {
				int rightValue = (int) ((IRConstInt) right).getValue();
				if (canBeImm(rightValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.slti, sltRes, toRvRegister(left), new RvImm(rightValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.slt, sltRes, toRvRegister(left), toRvRegister(right)));		
				}
			}
			else {
				currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.slt, sltRes, toRvRegister(left), toRvRegister(right)));		
			}
			if (op == IcmpInst.IcmpOpType.sge) {
				RvRegister xorRes = toRvRegister(res);  
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.xori, xorRes, sltRes, new RvImm(1)));		
			}
		}
		else {
			RvRegister sltRes = toRvRegister(res);  
			if (left instanceof IRConstInt) {
				int leftValue = (int) ((IRConstInt) left).getValue();
				if (canBeImm(leftValue)) {
					currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.slti, sltRes, toRvRegister(right), new RvImm(leftValue)));
				}
				else {
					currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.slt, sltRes, toRvRegister(right), toRvRegister(left)));		
				}
			}
			else {
				currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.slt, sltRes, toRvRegister(right), toRvRegister(left)));		
			}
			if (op == IcmpInst.IcmpOpType.sle) {
				RvRegister xorRes = toRvRegister(res);  
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.xori, xorRes, sltRes, new RvImm(1)));		
			}
		}
	}


	@Override
	public void visit(BrInst node) {
		IRSymbol cond = node.getCond();
		if (cond == null) {
			RvBlock block = node.getTrue().toRvBlock();
			currentBlock.addInst(new RvJ(currentBlock, block));
		}
		else {
			RvInst rvInst = node.getRvInst();
			if (rvInst != null) {
				rvInst.setCurrentBlock(currentBlock);
				currentBlock.addInst(rvInst);
			}
			else {
				currentBlock.addInst(new RvTypeB(currentBlock, RvTypeB.Op.bne, toRvRegister(cond), RegisterTable.zero, node.getTrue().toRvBlock()));
			}
			currentBlock.addInst(new RvJ(currentBlock, node.getFalse().toRvBlock()));
		}
	}

	public void handleGEPWithLoadStore(IRRegister res, RvRegister reg, RvImm imm) {
		HashSet<IRInst> instList = res.getUseList();
		//System.err.println("handleGEPWithLoadStore " + reg + " " + imm);
		//System.err.println(instList);
		for (IRInst inst : instList) {
			//System.err.println("store " + inst);
			if (inst instanceof LoadInst) 
				inst.setRvInst(new RvLoad(currentBlock, toRvRegister(inst.getRes()), reg, imm));
			else 
				inst.setRvInst(new RvStore(currentBlock, toRvRegister(((StoreInst) inst).getValue()), reg, imm));	
		}
	}
	
	private int log2(int value) {
		int res = 0;
		while (value > 1) {
			value >>= 1;
			res++;
		}
		return value == 0 ? res : -1;
	}
	
	@Override
	public void visit(GetElementPtrInst node) {
		IRRegister res = node.getRes();
		IRSymbol ptr = node.getPtr();
		if (res.getName().indexOf("__stringLiteral") != -1) {
			if (!node.isIgnored()) {
				RvVirtualRegister tmp = currentFunction.newRegister("tmp");
				RvGlobalString var = ((IRGlobalString) ptr).toRvGlobalString();
				currentBlock.addInst(new RvLui(currentBlock, tmp, new RvAddress(RegisterTable.hi, var)));
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, toRvRegister(res), tmp, new RvAddress(RegisterTable.lo, var)));
			}
			return;
		}
		IRSymbol index0 = node.getIndex0(), index1 = node.getIndex1();
		boolean only = res.onlyBeUsedByLoadStore();
		//System.err.println(res + " only " + only);
		
		int offset1 = 0;
		if (index1 != null) {
			assert index1 instanceof IRConstInt;
			offset1 = (int) (((IRConstInt)index1).getValue() << 2);
		}
		
		int bytes = node.bytes();
		if (index0 instanceof IRConstInt && canBeImm((int) ((IRConstInt) index0).getValue() * bytes)) {
			int offset = (int) ((IRConstInt) index0).getValue() * bytes;
			if (canBeImm(offset + offset1)) {
				offset += offset1;
				if (only) 
					handleGEPWithLoadStore(res, toRvRegister(ptr), new RvImm(offset));
				else {	
					if (offset == 0) 						
						currentBlock.addInst(new RvMove(currentBlock, toRvRegister(res), toRvRegister(ptr)));
					else 
						currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, toRvRegister(res), toRvRegister(ptr), new RvImm(offset)));		
				}	
			}
			else {
				RvVirtualRegister tmp = currentFunction.newRegister("tmp");
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, tmp, toRvRegister(offset1), new RvImm(offset)));
				currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.add, toRvRegister(res), toRvRegister(ptr), tmp));
			}
		}
		else {
			RvVirtualRegister tmp = currentFunction.newRegister("tmp");
		//	int log = log2(bytes);
		//	if (log != -1)
			currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.slli, tmp, toRvRegister(index0), new RvImm(2)));
		//	else
			//	currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.mul, tmp, toRvRegister(index0), toRvRegister(bytes)));
		
			RvVirtualRegister addTmp;			
			if (offset1 != 0) {
				addTmp = currentFunction.newRegister("tmp");
				if (canBeImm(offset1)) 
					currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, addTmp, tmp, new RvImm(offset1)));
				else
					currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.add, addTmp, tmp, toRvRegister(offset1)));	
			}
			else
				addTmp = tmp;
			currentBlock.addInst(new RvTypeR(currentBlock, RvTypeR.Op.add, toRvRegister(res), toRvRegister(ptr), addTmp));
		}
	}

	
	@Override
	public void visit(LoadInst node) {
		RvInst inst = node.getRvInst();
		if (inst != null) {
			inst.setCurrentBlock(currentBlock);
			currentBlock.addInst(inst);
		}
		else {
			IRSymbol ptr = node.getPtr();
			if (ptr instanceof IRGlobalVariable) {
				RvVirtualRegister tmp = currentFunction.newRegister("tmp");
				RvGlobalVariable var = ((IRGlobalVariable) ptr).toRvGlobalVariable();
				currentBlock.addInst(new RvLui(currentBlock, tmp, new RvAddress(RegisterTable.hi, var)));
				currentBlock.addInst(new RvLoad(currentBlock, toRvRegister(node.getRes()), tmp, new RvAddress(RegisterTable.lo, var)));
			}
			else if (ptr instanceof IRGlobalString) {
				RvVirtualRegister tmp = currentFunction.newRegister("tmp");
				RvGlobalString var = ((IRGlobalString) ptr).toRvGlobalString();
				currentBlock.addInst(new RvLui(currentBlock, tmp, new RvAddress(RegisterTable.hi, var)));
				currentBlock.addInst(new RvLoad(currentBlock, toRvRegister(node.getRes()), tmp, new RvAddress(RegisterTable.lo, var)));
			} 
			else 
				currentBlock.addInst(new RvLoad(currentBlock, toRvRegister(node.getRes()), toRvRegister(node.getPtr()), new RvImm(0)));
		}
	}
	
	@Override
	public void visit(StoreInst node) {
		if (node.getValue() instanceof IRRegister && ((IRRegister) node.getValue()).getName().indexOf("__stringLiteral") != -1) {
			if (node.isIgnored())
				return;
		}
		RvInst inst = node.getRvInst();
		if (inst != null) {
			inst.setCurrentBlock(currentBlock);
			currentBlock.addInst(inst);
		}
		else {
			IRSymbol ptr = node.getPtr();
			if (ptr instanceof IRGlobalVariable) {
				RvVirtualRegister tmp = currentFunction.newRegister("tmp");
				RvGlobalVariable var = ((IRGlobalVariable) ptr).toRvGlobalVariable();
				currentBlock.addInst(new RvLui(currentBlock, tmp, new RvAddress(RegisterTable.hi, var)));
				currentBlock.addInst(new RvStore(currentBlock, toRvRegister(node.getValue()), tmp, new RvAddress(RegisterTable.lo, var)));
			}
			else if (ptr instanceof IRGlobalString) {
				RvVirtualRegister tmp = currentFunction.newRegister("tmp");
				RvGlobalString var = ((IRGlobalString) ptr).toRvGlobalString();
				currentBlock.addInst(new RvLui(currentBlock, tmp, new RvAddress(RegisterTable.hi, var)));
				currentBlock.addInst(new RvStore(currentBlock, toRvRegister(node.getRes()), tmp, new RvAddress(RegisterTable.lo, var)));
			} 
			else 
				currentBlock.addInst(new RvStore(currentBlock, toRvRegister(node.getValue()), toRvRegister(node.getPtr()), new RvImm(0)));
		}
	}
	
	@Override
	public void visit(MoveInst node) {
		currentBlock.addInst(new RvMove(currentBlock, toRvRegister(node.getRes()), toRvRegister(node.getSrc())));
	}
	
	@Override
	public void visit(CallInst node) {
		//System.err.println(node + " " + currentBlock.getName());
		ArrayList<IRSymbol> parameters = node.getParameters();
		for (int i = 0; i < 8 && i < parameters.size(); ++i) {
			RvPhysicalRegister reg = RegisterTable.argumentRegisters[i];
			currentBlock.addInst(new RvMove(currentBlock, reg, toRvRegister(parameters.get(i))));
		}
		
		ArrayList<RvStackSlot> slots = new ArrayList<RvStackSlot>();
		for (int i = 8; i < parameters.size(); ++i) {
			RvStackSlot slot = new RvStackSlot((i - 8) << 2, 0);
			currentBlock.addInst(new RvStore(currentBlock, toRvRegister(parameters.get(i)), slot));
			slots.add(slot);
		}
		currentFunction.addCallStackSlot(slots);
		
		//System.err.println(node.getFunction() + " " + node.getFunction().toRvFunction());
		currentBlock.addInst(new RvCall(currentBlock, node.getFunction().getName().substring(1), parameters.size()));
		IRRegister res = node.getRes();
		if (res != null) {
			currentBlock.addInst(new RvMove(currentBlock, toRvRegister(res), RegisterTable.a0));
		}
	}

	@Override
	public void visit(RetInst node) {
		IRSymbol value = node.getValue();
		//System.err.println("ret " + value);
		if (value != null) {
			currentBlock.addInst(new RvMove(currentBlock, RegisterTable.a0, toRvRegister(value)));
		}
		
		//callee saved registers
		
		for (int i = 11; i >= 0; --i) {
			RvPhysicalRegister reg = RegisterTable.calleeSavedRegisters[i];
			currentBlock.addInst(new RvMove(currentBlock, reg, calleeSaved[i]));
		}
		/*
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[4], calleeSaved[4]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[1], calleeSaved[1]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[11], calleeSaved[11]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[5], calleeSaved[5]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[0], calleeSaved[0]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[10], calleeSaved[10]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[2], calleeSaved[2]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[9], calleeSaved[9]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[7], calleeSaved[7]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[3], calleeSaved[3]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[6], calleeSaved[6]));
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.calleeSavedRegisters[8], calleeSaved[8]));
		*/
		currentBlock.addInst(new RvMove(currentBlock, RegisterTable.ra, calleeSaved[12]));
		
		currentBlock.addInst(new RvJr(currentBlock, RegisterTable.ra));
		currentFunction.setExitBlock(currentBlock);
	}

	@Override
	public void visit(PhiInst node) {
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
