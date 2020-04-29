package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvTypeB extends RvInst {
	
	public enum Op{ 
		beq, bne, ble, bge, blt, bgt
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

}
