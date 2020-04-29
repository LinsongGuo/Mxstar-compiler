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

}
