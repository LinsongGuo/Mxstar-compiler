package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvTypeB extends RvInst {
	
	public enum Op{ 
		beq("beq"), bne("bne"), ble("ble"), bge("bge"), blt("blt"), bgt("bgt");
		
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
	private RvRegister rs, rt;
	private RvBlock offset;
	
	public RvTypeB(RvBlock currentBlock, Op op, RvRegister rs, RvRegister rt, RvBlock offset) {
		super(currentBlock);
		this.op = op;
		this.rs = rs;
		this.rt = rt;
		this.offset = offset;
	}

	@Override
	public void init() {
		addUse(rs);
		addUse(rt);
		currentBlock.addSuccessor(offset);
		offset.addPredecessor(currentBlock);
		rs.increaseSpillCost(inLoop);
		rt.increaseSpillCost(inLoop);
	}

	public RvRegister getRs() {
		return rs;
	}
	
	public RvRegister getRt() {
		return rt;
	}
	
	public RvBlock getOffset() {
		return offset;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "\t" + op + "     " + rs + "," + rt + "," + offset.getName();
	}
}
