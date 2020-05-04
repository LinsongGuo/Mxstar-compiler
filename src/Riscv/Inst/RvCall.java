package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvVisitor;
import Riscv.Operand.RegisterTable;
import Riscv.Operand.RvRegister;

public class RvCall extends RvInst {

	private String function;
	private int parameters;
	
	public RvCall(RvBlock currentBlock, String function, int parameters) {
		super(currentBlock);
		this.function = function;
		this.parameters = parameters;
	}
	
	@Override
	public void init() {
		for (int i = 0; i < parameters && i < 8; ++i) {
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
		return "\tcall    " + function;
	}

	@Override
	public void replaceUse(RvRegister old, RvRegister nw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceDef(RvRegister old, RvRegister nw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUseAndDef() {
		// TODO Auto-generated method stub
		
	}

	
}
