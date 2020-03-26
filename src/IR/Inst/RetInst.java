package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;
import IR.Type.IRType;
import IR.Type.IRVoidType;

public class RetInst extends IRInst {
	private IRSymbol value;
	
	public RetInst(IRSymbol value) {
		super();
		this.value = value;
	}
	
	@Override
	public String toString() {
		IRType type = value.getType();
		if (type instanceof IRVoidType) {
			return "ret void";
		}
		else {
			return "ret " + type.toString() + " " + value.toString();	
		}		
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
