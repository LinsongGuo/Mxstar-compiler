package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvVisitor;
import Riscv.Operand.RegisterTable;

public class RvCall extends RvInst {

	private RvFunction function;
	
	public RvCall(RvBlock currentBlock, RvFunction function) {
		super(currentBlock);
		this.function = function;
	}
	
	@Override
	public void init() {
		for (int i = 0; i < function.getParameters() && i < 8; ++i) {
			addUse(RegisterTable.argumentRegisters[i]);
		}
		for (int i = 0; i < 16; ++i) {
			addDef(RegisterTable.callerSavedRegisters[i]);
		}
	}

	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\tcall    " + function.getName();
	}

	
}
