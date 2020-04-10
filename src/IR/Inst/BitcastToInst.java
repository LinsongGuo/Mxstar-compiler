package IR.Inst;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class BitcastToInst extends IRInst {

	private IRSymbol src, dest;
	
	public BitcastToInst(IRSymbol src, IRSymbol dest) {
		super();
		this.src = src;
		this.dest = dest;
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
		// TODO Auto-generated method stub
		
	}
	
}
