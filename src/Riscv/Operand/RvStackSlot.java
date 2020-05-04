package Riscv.Operand;

public class RvStackSlot extends RvOperand {
	private int index;
	
	public RvStackSlot() {
		this.index = 0;
	}
	
	public RvStackSlot(int index) {
		this.index = index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return String.valueOf(index) + "(sp)";
	}
}
