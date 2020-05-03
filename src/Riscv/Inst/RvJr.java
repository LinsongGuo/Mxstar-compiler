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

}
