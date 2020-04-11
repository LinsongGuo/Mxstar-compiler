package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRType;

public class AllocaInst extends IRInst {
	private IRRegister res;
	private IRType type;

	public AllocaInst(IRRegister res, IRType type) {
		super();
		this.res = res;
		this.type = type;
	}

	@Override
	public String toString() {
		return res.toString() + " = alloca " + type.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void removeAllUse() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IRRegister getRes() {
		return res;
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}
}
