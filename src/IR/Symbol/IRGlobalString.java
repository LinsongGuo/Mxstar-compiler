package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRPtrType;
import IR.Type.IRType;
import Riscv.Operand.RvGlobalString;

public class IRGlobalString extends IRRegister {
	private IRConstString value;

	public IRGlobalString(IRType type, String name, IRConstString value) {
		super(type, name);
		this.value = value;
	}

	@Override
	public String toString() {
		return "@" + name;
	}
	
	public IRConstString getValue() {
		return value;
	} 

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}
	
	public String declarationString() {
		return "@" + name + " = private unnamed_addr constant " + ((IRPtrType) type).getType().toString() + " " + value.toString();
	}
	
	//for instruction selection
	private RvGlobalString rvGlobalString;
	
	public RvGlobalString toRvGlobalString() {
		return rvGlobalString;
	}
	
	public void setRvGlobalString(RvGlobalString rvGlobalString) {
		this.rvGlobalString = rvGlobalString;
	}
}
