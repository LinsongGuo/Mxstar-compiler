package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class MoveInst extends IRInst {
	private IRRegister dest;
	private IRSymbol src;
	
	public MoveInst(IRRegister dest, IRSymbol src) {
		this.dest = dest;
		this.src = src;
	}
	
	@Override
	public String toString() {
		return "move " + dest.toString() + ", " + src.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void InitDefUse() {
		dest.addDef(this);
		src.addUse(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		if (src == old) {
			src = nw;
			nw.addUse(this);
		}
	}

	@Override
	public void removeAllUse() {
		src.removeUse(this);
	}

	@Override
	public void removeAllDef() {
		dest.removeDef(this);
	}

	@Override
	public IRRegister getRes() {
		return dest;
	}

	public IRSymbol getSrc() {
		return src;
	}
	
	public void modifySrc(IRSymbol srcTmp) {
		this.src = srcTmp;
	}
	
	//for SCCP
	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		return null;
	}

}
