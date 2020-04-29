package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvCmpZ extends RvInst {
	
	public enum Op {
		seqz, snez, sltz, sgtz
	};
	
	private Op op;
	private RvRegister rs, rd;
	
	public RvCmpZ(RvBlock currentBlock, Op op, RvRegister rd, RvRegister rs) {
		super(currentBlock);
		this.op = op;
		this.rd = rd;
		this.rs = rs;
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	public RvRegister getRs() {
		return rs;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
