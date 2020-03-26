package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Type.IRType;

public class AllocaInst extends IRInst {
	private IRRegister reg;
	private IRType type;

	public AllocaInst(IRRegister reg, IRType type) {
		super();
		this.reg = reg;
		this.type = type;
	}

	@Override
	public String toString() {
		return reg.toString() + " = alloca " + type.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
