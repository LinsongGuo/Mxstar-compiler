package Riscv.Operand;

public class RvStackSlot extends RvOperand {
	private int index;
	private int call;
	
	public RvStackSlot() {
		this.index = 0;
		this.call = 1;
	}
	
	public RvStackSlot(int index, int call) {
		this.index = index;
		this.call = call;
	}
	
	public void setIndex(int index) {
		this.index = index;
		this.call = 0;
	}
	
	public void addIndex(int index) {
		this.index += index;
		this.call = 0;
	}
	
	public int getIndex() {
		return index;
	}

	public int call() {
		return call;
	}
	
	@Override
	public String toString() {
		return String.valueOf(index) + "(sp)";
	}
}
