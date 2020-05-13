package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRPtrType;
import IR.Type.IRType;
import Riscv.Operand.RvGlobalString;
import Riscv.Operand.RvGlobalVariable;

public class IRGlobalVariable extends IRRegister {
	private IRSymbol init;
	
	public IRGlobalVariable(IRType type, String name) {
		super(type, name);
	}
	
	public void setInit(IRSymbol init) {
		this.init = init;
	}
	
	public IRSymbol getInit() {
		return init;
	}
	
	@Override
	public String toString() {
		return "@" + super.name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public String declarationString() {
		return "@" + name + " = global " + ((IRPtrType) type).getType().toString() + " " +
				(init instanceof IRGlobalString ? "null" : init.toString());
	}
	
	//for instruction selection
	private RvGlobalVariable rvGlobalVariable;
	
	public RvGlobalVariable toRvGlobalVariable() {
		return rvGlobalVariable;
	}
	
	public void setRvGlobalVariable(RvGlobalVariable rvGlobalVariable) {
		this.rvGlobalVariable = rvGlobalVariable;
	}
}
