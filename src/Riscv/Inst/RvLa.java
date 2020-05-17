package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvGlobalVariable;
import Riscv.Operand.RvRegister;

public class RvLa extends RvInst {
	private RvRegister dest;
	private RvGlobalVariable src;
	
	public RvLa(RvBlock currentBlock, RvRegister dest, RvGlobalVariable src) {
		super(currentBlock);
		this.dest = dest;
		this.src = src;
	}

	@Override
	public void init() {
	//	System.err.println("la init ");
		addDef(dest);
		dest.addDef(this);
		dest.increaseSpillCost(inLoop);
	}

	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "    la      " + dest + "," + src;    
	}

	@Override
	public void replaceUse(RvRegister old, RvRegister nw) {

	}

	@Override
	public void replaceDef(RvRegister old, RvRegister nw) {
		if (old == dest) {
			old.removeDef(this);
			def.remove(old);
			old.decreaseSpillCost(inLoop);
			dest = nw;
			nw.addDef(this);
			def.add(nw);
			nw.increaseSpillCost(inLoop);
		}
	}

	@Override
	public void removeUseAndDef() {
		dest.removeDef(this);
		def.remove(dest);
		dest.decreaseSpillCost(inLoop);
	}
	
}
