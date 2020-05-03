package Riscv.Operand;

import Riscv.RvVisitor;

public class RvGlobalVariable extends RvOperand {
	private RvOperand value;
	private String name;
	
	public RvGlobalVariable(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return name;
	}
}
