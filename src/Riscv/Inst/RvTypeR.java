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
	
}
