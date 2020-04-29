package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvVisitor;

public class RvCall extends RvInst {

	RvFunction function;
	
	public RvCall(RvBlock currentBlock, RvFunction function) {
		super(currentBlock);
		this.function = function;
	}

	public RvFunction getFunction() {
		return function;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
