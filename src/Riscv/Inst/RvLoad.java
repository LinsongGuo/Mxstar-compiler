package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvOperand;
import Riscv.Operand.RvRegister;
import Riscv.Operand.RvStackSlot;

public class RvLoad extends RvInst {
	
	private RvRegister rd;
	private RvOperand src;
	private RvImm offset;
	
	public RvLoad(RvBlock currentBlock, RvRegister rd, RvOperand src, RvImm offset) {
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
	
	public RvRegister getRd() {
		return rd;
	}
	
	public RvOperand getSrc() {
		return src;
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
	
}
