package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvVisitor;

public class RvCall extends RvInst {

	private String function;
	
	public RvCall(RvBlock currentBlock, String function) {
		super(currentBlock);
		this.function = function;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\tcall    " + function;
	}
}
