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
		rs.addUse(this);
		rt.addUse(this);
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

	@Override
	public void replaceUse(RvRegister old, RvRegister nw) {
		boolean flag = false;
		if (rs == old) {
			old.decreaseSpillCost(inLoop);
			rs = nw;
			nw.increaseSpillCost(inLoop);
			flag = true;
		}
		if (rt == old) {
			old.decreaseSpillCost(inLoop);
			rt = nw;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUseAndDef() {
		rs.removeUse(this);
		use.remove(rs);
		rs.decreaseSpillCost(inLoop);
		
		rt.removeUse(this);
		use.remove(rt);
		rt.decreaseSpillCost(inLoop);
	}
}
