package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvJr extends RvInst {
	
	private RvRegister rs;
	
	public RvJr(RvBlock currentBlock, RvRegister rs) {
		super(currentBlock);
		this.rs = rs;
	}
	
	@Override
	public void init() {
		addUse(rs);
		rs.addUse(this);
		rs.increaseSpillCost(inLoop);
	}
	
	public RvRegister getRs() {
		return rs;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\tjr      " + rs.getName();
	}

	@Override
	public void replaceUse(RvRegister old, RvRegister nw) {
		if (rs == old) {
			old.removeUse(this);
			use.remove(old);
			old.decreaseSpillCost(inLoop);
			rs = nw;
			nw.addUse(this);
			use.add(nw);
			nw.increaseSpillCost(inLoop);
		}
	}

	@Override
	public void replaceDef(RvRegister old, RvRegister nw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUseAndDef() {
		rs.removeUse(this);
		use.remove(rs);
		rs.decreaseSpillCost(inLoop);
	}

}
