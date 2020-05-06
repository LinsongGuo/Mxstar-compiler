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
		rd.addDef(this);
		rs.addUse(this);
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
