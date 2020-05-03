package Riscv.Inst;

import Riscv.RvBlock;
import Riscv.RvVisitor;

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

}
