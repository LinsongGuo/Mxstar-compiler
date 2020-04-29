package Riscv.Operand;

public class RvStackLocation extends RvOperand {
	private int index;
	
	public RvStackLocation(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
