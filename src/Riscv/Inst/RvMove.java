package Riscv.Inst;

import java.util.Set;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvMove extends RvInst {
	
	private RvRegister rs, rd;
	
	public RvMove(RvBlock currentBlock, RvRegister rd, RvRegister rs) {
		super(currentBlock);
		this.rs = rs;
		this.rd = rd;
	}

	@Override
	public void init() {
		addDef(rd);
		addUse(rs);
		rd.increaseSpillCost(inLoop);
		rs.increaseSpillCost(inLoop);
	}
	
	public RvRegister getRs() {
		return rs;
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\tmv      " + rd + "," + rs;
	}

}
