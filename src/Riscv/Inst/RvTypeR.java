package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvTypeR extends RvInst {
	
	public enum Op{
        add, sub, mul, div, rem, and, or, xor, sll, sra, slt, sltu
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

	
}
