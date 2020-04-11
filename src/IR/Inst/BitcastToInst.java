package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class BitcastToInst extends IRInst {

	private IRSymbol src, dest;
	
	public BitcastToInst(IRSymbol src, IRSymbol dest) {
		super();
		this.src = src;
		this.dest = dest;
		src.addUse(this);
	}
	
	@Override
	public String toString() {
		return dest.toString() + " = bitcast " + src.getType().toString() + " " + src.toString() + " to " + dest.getType().toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		if (src == old) {
			src = nw;
			//old.removeUse(this);
			nw.addUse(this);
		}
	}

	@Override
	public IRSymbol getRes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllUse() {
		src.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}
	
}
