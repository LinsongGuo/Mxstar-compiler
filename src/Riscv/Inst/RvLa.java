package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvGlobalVar;
import Riscv.Operand.RvRegister;

public class RvLa extends RvInst {
	private RvRegister rd;
	private RvGlobalVar symbol;
	
	public RvLa(RvBlock currentBlock, RvRegister rd, RvGlobalVar symbol) {
		super(currentBlock);
		this.rd = rd;
		this.symbol = symbol;
	}
	
	public RvRegister getRd() {
		return rd;
	}
	
	public RvGlobalVar getSymbol() {
		return symbol;
	}
	
	@Override
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
	
}
