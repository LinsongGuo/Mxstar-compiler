package Riscv;

import Riscv.Inst.*;
import Riscv.Operand.RvGlobalString;
import Riscv.Operand.RvGlobalVariable;

public interface RvVisitor {
	public void visit(RvModule module);
	public void visit(RvFunction function);
	public void visit(RvBlock block);
	
	public void visit(RvGlobalString globalString);
	public void visit(RvGlobalVariable globalVariable);
	
	public void visit(RvInst inst);
	public void visit(RvTypeB inst);
	public void visit(RvCall inst);
	public void visit(RvJ inst);
	public void visit(RvJr inst);
	public void visit(RvLi inst);
	public void visit(RvLoad inst);
	public void visit(RvLui inst);
	public void visit(RvMove inst);
	public void visit(RvStore inst);
	public void visit(RvTypeI inst);
	public void visit(RvTypeR inst);
	public void visit(RvCmpZ inst);
}
