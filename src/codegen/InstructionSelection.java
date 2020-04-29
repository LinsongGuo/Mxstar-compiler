package codegen;

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
import Riscv.Inst.RvCmpZ;
import Riscv.Inst.RvInst;
import Riscv.Inst.RvJ;
import Riscv.Inst.RvLi;
import Riscv.Inst.RvTypeB;
import Riscv.Inst.RvTypeI;
import Riscv.Inst.RvTypeR;
import Riscv.Operand.RegisterTable;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvOperand;
import Riscv.Operand.RvPhysicalRegister;
import Riscv.Operand.RvRegister;
import Riscv.Operand.RvVirtualRegister;


public class InstructionSelection implements IRVisitor {

	private final int IMM_MAX = 2047;
    private final int IMM_MIN = -2048;
    private RvFunction currentFunction;
    private RvBlock currentBlock;
    private RegisterTable regTable;
    
    
	public InstructionSelection(IRModule module, RegisterTable regTable) {
		visit(module);
		this.regTable = regTable;
	}
	
	private boolean canBeImm(int value) {
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
			RvVirtualRegister tmp = new RvVirtualRegister("tmp");
			currentBlock.addInst(new RvLi(currentBlock, tmp, new RvImm(value)));
			return tmp;
		}
		else if (symbol instanceof IRConstBool) {
			boolean value = ((IRConstBool) symbol).getValue();
			if (!value) return regTable.zero;
			else {
				RvVirtualRegister tmp = new RvVirtualRegister("tmp");
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.addi, tmp, regTable.zero, new RvImm(1)));
				return tmp;
			}
		}
		else if (symbol instanceof IRNull) {
			return regTable.zero;
		}
		else if (symbol instanceof IRRegister) {
			RvRegister res = ((IRRegister) symbol).getRvReg();
			if (res != null) return res;
			res = new RvVirtualRegister(((IRRegister) symbol).getName());
			((IRRegister) symbol).setRvReg(res);
			return res;
		}
		return null;
	}
	
	@Override
	public void visit(IRModule node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRFunction node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IRBasicBlock node) {
		// TODO Auto-generated method stub
		
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
		RvVirtualRegister result = new RvVirtualRegister(res.getName());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BitwiseBinOpInst node) {
		BitwiseBinOpInst.BitwiseBinOpType op = node.getOp();
		IRRegister res = node.getRes();
		RvVirtualRegister result = new RvVirtualRegister(res.getName());
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
	
	private void handleIcmpBranch(IcmpInst inst, IRRegister reg) {
		HashSet<IRInst> useList = reg.getUseList();
		IRSymbol left = inst.getLeft(), right = inst.getRight();
		RvTypeB.Op op = null;
		switch (inst.getOp()) {
	        case eq: op = RvTypeB.Op.bge; break;
	        case ne: op = RvTypeB.Op.bgt; break;
	        case slt: op = RvTypeB.Op.ble; break;
	        case sle: op = RvTypeB.Op.blt; break;
	        case sgt: op = RvTypeB.Op.bgt; break;
	        case sge: op = RvTypeB.Op.bge; break;
		}
		for (IRInst brInst : useList) {
			 ((BrInst) brInst).setRvInst(new RvTypeB(currentBlock, op, toRvRegister(left), toRvRegister(right), ((BrInst) brInst).getTrue().getRvBlock()));
		}
	}
	
	@Override
	public void visit(IcmpInst node) {
		IRRegister res = node.getRes();
		if (res.onlyBeUsedByBranch()) {
			handleIcmpBranch(node, res);
			return;
		}
		IcmpInst.IcmpOpType op = node.getOp();
		IRSymbol left = node.getLeft(), right = node.getRight();
		if (op == IcmpInst.IcmpOpType.eq || op == IcmpInst.IcmpOpType.ne) {
			RvVirtualRegister cmpzRes = new RvVirtualRegister(res.getName());
			if (right instanceof IRNull) {
				currentBlock.addInst(new RvCmpZ(currentBlock, op == IcmpInst.IcmpOpType.eq ? RvCmpZ.Op.seqz : RvCmpZ.Op.snez, cmpzRes, toRvRegister(left)));
			}
			else if (left instanceof IRNull) {
				currentBlock.addInst(new RvCmpZ(currentBlock, op == IcmpInst.IcmpOpType.eq ? RvCmpZ.Op.seqz : RvCmpZ.Op.snez, cmpzRes, toRvRegister(right)));
			}
			else {
				RvVirtualRegister xorRes = new RvVirtualRegister(res.getName());
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
			RvRegister sltRes = new RvVirtualRegister(res.getName());  
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
				RvRegister xorRes = new RvVirtualRegister(res.getName());  
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.xori, xorRes, sltRes, new RvImm(1)));		
			}
		}
		else {
			RvRegister sltRes = new RvVirtualRegister(res.getName());  
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
				RvRegister xorRes = new RvVirtualRegister(res.getName());  
				currentBlock.addInst(new RvTypeI(currentBlock, RvTypeI.Op.xori, xorRes, sltRes, new RvImm(1)));		
			}
		}
	}


	@Override
	public void visit(BrInst node) {
		IRSymbol cond = node.getCond();
		if (cond == null) {
			RvBlock block = node.getTrue().getRvBlock();
			currentBlock.addInst(new RvJ(currentBlock, block));
		}
		else {
			RvInst rvInst = node.getRvInst();
			if (rvInst != null) {
				rvInst.setCurrentBlock(currentBlock);
				currentBlock.addInst(rvInst);
			}
			else {
				currentBlock.addInst(new RvTypeB(currentBlock, RvTypeB.Op.bne, toRvRegister(cond), regTable.zero, node.getTrue().getRvBlock()));
			}
			currentBlock.addInst(new RvJ(currentBlock, node.getFalse().getRvBlock()));
		}
	}

	@Override
	public void visit(CallInst node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(GetElementPtrInst node) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void visit(LoadInst node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PhiInst node) {
		// TODO Auto-generated method stub
		
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
