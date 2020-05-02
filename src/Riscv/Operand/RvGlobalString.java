package Riscv.Operand;

import Riscv.RvVisitor;

public class RvGlobalString extends RvRegister {
	
	public String value;
	
	public RvGlobalString(String name, String value) {
		super(name);
		value = value.replace("\\", "\\\\");
	    value = value.replace("\n","\\n");
	    value = value.replace("\0", "");
	    value = value.replace("\t","\\t");
	    value = value.replace("\"", "\\\"");
	    value = "\"" + value + "\"";
	    this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
