package IR.Symbol;

import java.util.HashSet;

import IR.IRVisitor;
import IR.Inst.IRInst;
import IR.Type.IRType;

abstract public class IRSymbol {
	protected IRType type;
	protected HashSet<IRInst> useList, defList;
	
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
	
	public void removeUse(IRInst inst) {
		useList.remove(inst);
	}
	
	public void addDef(IRInst inst) {
		defList.add(inst);
	}
	
	public void removeDef(IRInst inst) {
		defList.remove(inst);
	}
	
	public HashSet<IRInst> getUseList() {
		return useList;
	}
	
	public HashSet<IRInst> getDefList() {
		return defList;
	}
	
	public boolean isUsed() {
		return !useList.isEmpty();
	}
	
	public boolean isDefed() {
		return !defList.isEmpty();
	}
	
	public void replaceUse(IRSymbol other) {
		if (this == other) return;
		for (IRInst inst : useList) {
			inst.replaceUse(this, other);
		}
		useList.clear();
	}
}
