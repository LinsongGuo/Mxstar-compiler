package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvOperand;
import Riscv.Operand.RvRegister;

public class RvStore extends RvInst {

	private RvRegister rd;
	private RvOperand dest;
	
	public RvStore(RvBlock currentBlock, RvRegister rd, RvOperand dest) {
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
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
