package Riscv.Operand;

import Riscv.RvVisitor;

public class RvAddress extends RvImm {
	private RvPhysicalRegister reg; //%hi or %lo
	private RvRegister symbol;
	
	public RvAddress(RvPhysicalRegister reg, RvRegister symbol) {
		super(0);
		this.reg = reg;
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return reg + "(" + symbol + ")";
	}

}
