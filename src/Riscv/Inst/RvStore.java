package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvOperand;
import Riscv.Operand.RvRegister;
import Riscv.Operand.RvStackSlot;

public class RvStore extends RvInst {

	private RvRegister rd;
	private RvOperand dest;
	private RvImm offset;
	
	public RvStore(RvBlock currentBlock, RvRegister rd, RvOperand dest, RvImm offset) {
		super(currentBlock);
		this.rd = rd;
		this.dest = dest;
		this.offset = offset;
	}
	
	public RvStore(RvBlock currentBlock, RvRegister rd, RvStackSlot dest) {
		super(currentBlock);
		this.rd = rd;
		this.dest = dest;
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	public RvOperand getDest() {
		return dest;
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
		if (dest instanceof RvStackSlot) 
			return "\tsw      " + rd + "," + dest;
		else
			return "\tsw      " + rd + "," + offset + "(" + dest + ")";
	}
}
