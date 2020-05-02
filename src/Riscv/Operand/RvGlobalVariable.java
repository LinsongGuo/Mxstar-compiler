package Riscv.Operand;

import Riscv.RvVisitor;

public class RvGlobalVariable extends RvRegister {
	private RvOperand value;
	
	public RvGlobalVariable(String name) {
		super(name);
	}
	
	public void setValue(RvOperand value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public RvOperand getValue() {
		return value;
	}
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
