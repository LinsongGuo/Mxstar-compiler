package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvTypeR extends RvInst {
	
	public enum Op{
        add("add"), sub("sub"), mul("mul"), div("div"), rem("rem"), and("and"), or("or"), xor("xor"), sll("sll"), sra("sra"), slt("slt"), sltu("sltu");
		
		private String str;
		
		private Op(String str) {
			this.str = str;
		}
		
		@Override
		public String toString() {
			return str;
		}
    }
	
	private Op op;
	private RvRegister rs1, rs2, rd;
	
	public RvTypeR(RvBlock currentBlock, Op op, RvRegister rd, RvRegister rs1, RvRegister rs2) {
		super(currentBlock);
		this.op = op;
		this.rd = rd;
		this.rs1 = rs1;
		this.rs2 = rs2;
	}
	
	@Override
	public void init() {
		addDef(rd);
		addUse(rs1);
		addUse(rs2);
		rd.addDef(this);
		rs1.addUse(this);
		rs2.addUse(this);
		rd.increaseSpillCost(inLoop);
		rs1.increaseSpillCost(inLoop);
		rs2.increaseSpillCost(inLoop);
	}

	public RvRegister getRd() {
		return rd;
	}
	
	public RvRegister getRs1() {
		return rs1;
	}
	
	public RvRegister getRs2() {
		return rs2;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		if (op == Op.or)
			return "\tor      " + rd + "," + rs1 + "," + rs2;
		else if (op == Op.sltu)
			return "\tsltu    " + rd + ","  + rs1 + "," + rs2;
		else 
			return "\t" +  op + "     " + rd + "," + rs1 + "," + rs2;
	}

	@Override
	public void replaceUse(RvRegister old, RvRegister nw) {
		boolean flag = false;
		if (rs1 == old) {
			old.decreaseSpillCost(inLoop);
			rs1 = nw;
			nw.increaseSpillCost(inLoop);
			flag = true;
		}
		if (rs2 == old) {
			old.decreaseSpillCost(inLoop);
			rs2 = nw;
			nw.increaseSpillCost(inLoop);
			flag = true;
		}
		if (flag) {
			old.removeUse(this);
			use.remove(old);
			nw.addUse(this);
			use.add(nw);
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
		rs1.removeUse(this);
		use.remove(rs1);
		rs1.decreaseSpillCost(inLoop);
		
		rs2.removeUse(this);
		use.remove(rs2);
		rs2.decreaseSpillCost(inLoop);
		
		rd.removeDef(this);
		def.remove(rd);
		rd.decreaseSpillCost(inLoop);
	}
	
}
