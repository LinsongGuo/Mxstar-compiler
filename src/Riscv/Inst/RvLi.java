package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvRegister;

public class RvLi extends RvInst {
	private RvRegister rd;
	private RvImm imm;
	
	public RvLi(RvBlock currentBlock, RvRegister rd, RvImm imm) {
		super(currentBlock);
		this.rd = rd;
		this.imm = imm;
	}
	
	@Override
	public void init() {
		addDef(rd);
		rd.increaseSpillCost(inLoop);
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	public RvImm getImm() {
		return imm;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\tli      " + rd + "," + imm;
	}
}
