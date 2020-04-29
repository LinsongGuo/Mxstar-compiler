package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvOperand;
import Riscv.Operand.RvRegister;

public class RvLoad extends RvInst {
	
	private RvRegister rd;
	private RvOperand src;
	
	public RvLoad(RvBlock currentBlock, RvRegister rd, RvRegister src) {
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
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
	
}
