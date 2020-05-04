package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;
import Riscv.Operand.RvRegister;

public class RvJ extends RvInst {
	
	private RvBlock offset;
	
	public RvJ(RvBlock currentBlock, RvBlock offset) {
		super(currentBlock);
		this.offset = offset;
	}
	
	@Override
	public void init() {
		currentBlock.addSuccessor(offset);
		offset.addPredecessor(currentBlock);
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
		return "\tj       " + offset.getName();
	}

	@Override
	public void replaceUse(RvRegister old, RvRegister nw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceDef(RvRegister old, RvRegister nw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUseAndDef() {
		// TODO Auto-generated method stub
		
	}

}
