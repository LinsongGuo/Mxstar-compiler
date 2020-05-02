package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvRegister;

public class RvLui extends RvInst {

	private RvRegister rd;
	private RvImm imm;
	
	public RvLui(RvBlock currentBlock, RvRegister rd, RvImm imm) {
		super(currentBlock);
		this.rd = rd;
		this.imm = imm;
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\tlui     " + rd + "," + imm;
	}
}
