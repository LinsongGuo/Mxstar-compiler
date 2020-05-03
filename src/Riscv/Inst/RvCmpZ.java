package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvCmpZ extends RvInst {
	
	public enum Op {
		seqz("seqz"), snez("snez"), sltz("sltz"), sgtz("sgtz");
		
		private String str;
		
		private Op(String str) {
			this.str = str;
		}
		
		@Override
		public String toString() {
			return str;
		}
	};
	
	private Op op;
	private RvRegister rs, rd;
	
	public RvCmpZ(RvBlock currentBlock, Op op, RvRegister rd, RvRegister rs) {
		super(currentBlock);
		this.op = op;
		this.rd = rd;
		this.rs = rs;
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
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\t" + op.toString() + "    " + rd + "," + rs;
	}

}
