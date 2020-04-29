package Riscv.Operand;

public class RvImm extends RvOperand {
	private int value;
	
	public RvImm(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
