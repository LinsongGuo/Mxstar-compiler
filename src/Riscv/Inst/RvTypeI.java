package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvRegister;

public class RvTypeI extends RvInst {
	
	public enum Op {
		addi("addi"), andi("andi"), ori("ori"), xori("xori"), slli("slli"), srai("srai"), slti("slti"), sltiu("sltiu");
		
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
	private RvRegister rd, rs;
	private RvImm imm;
	    
	public RvTypeI(RvBlock currentBlock, Op op, RvRegister rd, RvRegister rs, RvImm imm) {
		super(currentBlock);
		this.op = op;
		this.rd = rd;
		this.rs = rs;
		this.imm = imm;
	}

	@Override
	public void init() {
		addDef(rd);
		addUse(rs);
		rd.increaseSpillCost(inLoop);
		rs.increaseSpillCost(inLoop);
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	public RvRegister getRs() {
		return rs;
	}
	
	public RvImm getImm() {
		return imm;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		if (op == Op.sltiu) 
			return "\tsltiu   " + rd + "," + rs + "," + imm;
		else 
			return  "\t" + op + "    " + rd + "," + rs + "," + imm; 
	}
	
}
