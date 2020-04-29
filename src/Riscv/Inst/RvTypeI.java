package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvRegister;

public class RvTypeI extends RvInst {
	
	public enum Op {
		addi, andi, ori, xori, slli, srai, slti, sltiu
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
	
}
