package Riscv.Operand;

public class RvRegister extends RvOperand {
	protected String name;
	
	public RvRegister(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
