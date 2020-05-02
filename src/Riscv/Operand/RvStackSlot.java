package Riscv.Operand;

public class RvStackSlot extends RvOperand {
	private int index;
	private boolean call;
	
	public RvStackSlot(int index) {
		this.index = index;
		call = false;
	}
	
	public RvStackSlot(int index, boolean call) {
		this.index = index;
		this.call = call;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return String.valueOf(index) + "(sp)";
	}
}
