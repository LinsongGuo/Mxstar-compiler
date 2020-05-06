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
		rd.addDef(this);
		rs.addUse(this);
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
		if (rd == old) {
			old.removeDef(this);
			def.remove(old);
			old.decreaseSpillCost(inLoop);
			rd = nw;
			nw.addDef(this);
			def.add(nw);
			nw.increaseSpillCost(inLoop);	
		}
	}

	@Override
	public void removeUseAndDef() {
		rs.removeUse(this);
		use.remove(rs);
		rs.decreaseSpillCost(inLoop);
		
		rd.removeDef(this);
		def.remove(rd);
		rd.decreaseSpillCost(inLoop);
	}

}
