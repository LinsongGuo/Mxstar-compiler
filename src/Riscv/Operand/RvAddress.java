package Riscv.Operand;

public class RvAddress extends RvImm {
	private RvPhysicalRegister reg; //%hi or %lo
	private RvGlobalVariable var;
	
	public RvAddress(RvPhysicalRegister reg, RvGlobalVariable var) {
		super(0);
		this.reg = reg;
		this.var = var;
	}

	@Override
	public String toString() {
		return reg + "(" + var + ")";
	}

}
