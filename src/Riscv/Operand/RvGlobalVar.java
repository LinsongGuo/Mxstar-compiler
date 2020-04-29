package Riscv.Operand;

public class RvGlobalVar extends RvOperand {
	private String name;
	
	public RvGlobalVar(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
