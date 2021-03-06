package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RegisterTable;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvOperand;
import Riscv.Operand.RvRegister;
import Riscv.Operand.RvStackSlot;

public class RvLoad extends RvInst {
	
	private RvRegister rd;
	private RvOperand src;
	private RvImm offset;
	
	public RvLoad(RvBlock currentBlock, RvRegister rd, RvRegister src, RvImm offset) {
		super(currentBlock);
		this.rd = rd;
		this.src = src;
		this.offset = offset;
	}
	
	public RvLoad(RvBlock currentBlock, RvRegister rd, RvStackSlot src) {
		super(currentBlock);
		this.rd = rd;
		this.src = src;
	}
	
	@Override
	public void init() {
		addDef(rd);
		rd.addDef(this);
		rd.increaseSpillCost(inLoop);
		if (src instanceof RvRegister) {
			addUse((RvRegister) src);
			((RvRegister) src).addUse(this);
			((RvRegister) src).increaseSpillCost(inLoop);
		}
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	public RvOperand getSrc() {
		return src;
	}
	
	public void setSrc(RvRegister src) {
		this.src = src;
	}
	
	public RvImm getOffset() {
		return offset;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		if (src instanceof RvStackSlot) 
			return "\tlw      " + rd + "," + src;
		else 
			return "\tlw      " + rd + "," + offset + "(" + src + ")";
	}

	@Override
	public void replaceUse(RvRegister old, RvRegister nw) {
		if (src instanceof RvRegister && src == old) {
			old.removeUse(this);
			use.remove(old);
			old.decreaseSpillCost(inLoop);
			src = nw;
			nw.addUse(this);
			use.add(nw);
			nw.increaseSpillCost(inLoop);
		}
	}

	@Override
	public void replaceDef(RvRegister old, RvRegister nw) {
		if (rd == old) {
			old.removeDef(this);
			def.remove(old);
			old.decreaseSpillCost(inLoop);
			rd = nw;
			nw.addDef(this);
			def.add(nw);
			nw.increaseSpillCost(inLoop);	
		}
	}

	@Override
	public void removeUseAndDef() {
		rd.removeDef(this);
		def.remove(rd);
		rd.decreaseSpillCost(inLoop);
		
		if (src instanceof RvRegister) {
			((RvRegister) src).removeUse(this);
			use.remove((RvRegister) src);
			((RvRegister) src).decreaseSpillCost(inLoop);
		}
	}
	
	public RvStackSlot getStackSlot() {
		return (src instanceof RvStackSlot) ? (RvStackSlot) src : null;
	}
	
	public boolean checkStore(RvStore store) {
		RvOperand storeDest = store.getDest();
		RvImm storeOffset = store.getOffset();
		
		int storeImm = -1, loadImm = -1;
		RvRegister storeReg = null, loadReg = null;
		if (storeDest instanceof RvStackSlot) {
			storeImm = ((RvStackSlot) storeDest).getIndex();
			storeReg = RegisterTable.sp;
		}
		else if (storeDest instanceof RvRegister) {
			storeImm = storeOffset.getValue();
			storeReg = (RvRegister) storeDest;
		}
		
		if (src instanceof RvStackSlot) {
			loadImm = ((RvStackSlot) src).getIndex();
			loadReg = RegisterTable.sp;
		}
		else if (src instanceof RvRegister) {
			loadImm = offset.getValue();
			loadReg = (RvRegister) src; 
		}
		
		if (storeReg == null || loadReg == null)
			return false;
		else return storeReg == loadReg && storeImm == loadImm;
	}
}
