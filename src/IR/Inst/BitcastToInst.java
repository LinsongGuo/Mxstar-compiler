package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class BitcastToInst extends IRInst {

	private IRSymbol src;
	private IRRegister dest;
	
	public BitcastToInst(IRSymbol src, IRRegister dest) {
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
		if (src == old) {
			src = nw;
			nw.addUse(this);
		}
	}

	@Override
	public IRRegister getRes() {
		return dest;
	}
	
	public IRSymbol getSrc() {
		return src;
	}
	
	@Override
	public void removeAllUse() {
		src.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InitDefUse() {
		src.addUse(this);
		dest.addDef(this);
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		if (src instanceof IRRegister)
			res.add((IRRegister) src);
		return res;
	}
	
}
