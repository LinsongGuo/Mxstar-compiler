package Riscv.Inst;

import java.util.HashSet;
import java.util.Set;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public abstract class RvInst {
	protected RvInst prev, next;
	protected RvBlock currentBlock;
	
	protected HashSet<RvRegister> def, use;
	protected boolean inLoop;
	
	private boolean checkInLoop() {
		return currentBlock.getName().contains("forCond")  || 
			   currentBlock.getName().contains("forBody")  || 
			   currentBlock.getName().contains("forStep")  ||
			   currentBlock.getName().contains("whileCond") || 
			   currentBlock.getName().contains("whileBody");
	}
	
	public RvInst(RvBlock currentBlock) {
		this.currentBlock = currentBlock;
		prev = next = null;
		def = new HashSet<RvRegister>();
		use = new HashSet<RvRegister>();
		inLoop = checkInLoop();
	}
	
	abstract public void init();
	
	public void setPrev(RvInst inst) {
		prev = inst;
	}
	
	public void setNext(RvInst inst) {
		next = inst;
	}
	
	public RvInst getPrev() {
		return prev;
	}
	
	public RvInst getNext() {
		return next;
	}
	
	public void setCurrentBlock(RvBlock currentBlock) {
		this.currentBlock = currentBlock;
		inLoop = checkInLoop();
	}
	
	public RvBlock getCurrentBlock() {
		return currentBlock;
	}
	
	abstract public void accept(RvVisitor visitor);
	
	@Override
	abstract public String toString();
	
	public HashSet<RvRegister> getDef() {
		return def;
	}
	
	public HashSet<RvRegister> getUse() {
		return use;
	}
	
	public void addDef(RvRegister reg) {
		def.add(reg);
	}
	
	public void addUse(RvRegister reg) {
		use.add(reg);
	}
	
	public abstract void replaceUse(RvRegister old, RvRegister nw);
	
	public abstract void replaceDef(RvRegister old, RvRegister nw);
	
	public abstract void removeUseAndDef();
}
