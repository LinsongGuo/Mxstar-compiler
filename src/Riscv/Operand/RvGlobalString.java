package Riscv.Operand;

import Riscv.RvVisitor;

public class RvGlobalString extends RvGlobalVariable {
	
	private String str;
	
	public RvGlobalString(String name, String str) {
		super(name);
		str = str.replace("\\", "\\\\");
	    str = str.replace("\n","\\n");
	    str = str.replace("\0", "");
	    str = str.replace("\t","\\t");
	    str = str.replace("\"", "\\\"");
	    str = "\"" + str + "\"";
	    this.str = str;
	}

	public String getStr() {
		return str;
	}
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
