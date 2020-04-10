package IR.Symbol;

import java.util.HashSet;

import IR.IRVisitor;
import IR.Inst.IRInst;
import IR.Type.IRType;

abstract public class IRSymbol {
	protected IRType type;
	private HashSet<IRInst> useList, defList;
	
	public IRSymbol(IRType type) {
		this.type = type;
		useList = new HashSet<IRInst>();
		defList = new HashSet<IRInst>();
	}
	
	@Override
	abstract public String toString();
	
	abstract public void accept(IRVisitor visitor);

	public IRType getType() {
		return type;
	}

	public void addUse(IRInst inst) {
		useList.add(inst);
	}
	
	public void addDef(IRInst inst) {
		defList.add(inst);
	}
	
	public HashSet<IRInst> getUseList() {
		return useList;
	}
	
	public HashSet<IRInst> getDefList() {
		return defList;
	}
	
	public void replaceUse(IRSymbol other) {
		for (IRInst inst : useList) {
			inst.replaceUse(this, other);
		}
	}
}
