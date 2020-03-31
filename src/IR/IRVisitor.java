package IR;

import IR.Type.*;
import IR.Inst.*;
import IR.Symbol.*;

public interface IRVisitor {
	abstract public void visit(IRModule node);
	abstract public void visit(IRFunction node);
	abstract public void visit(IRBasicBlock node);
	
	//Type
	abstract public void visit(IRType node);	
	abstract public void visit(IRIntType node);
	abstract public void visit(IRInt1Type node);
	abstract public void visit(IRInt8Type node);
	abstract public void visit(IRInt32Type node);
	abstract public void visit(IRVoidType node);
	abstract public void visit(IRClassType node);
	abstract public void visit(IRPtrType node);
	abstract public void visit(IRArrayType node);

	//Inst
	abstract public void visit(IRInst node);
	abstract public void visit(AllocaInst node);
	abstract public void visit(BinOpInst node);
	abstract public void visit(BitcastToInst node);
	abstract public void visit(BitwiseBinOpInst node);
	abstract public void visit(BrInst node);
	abstract public void visit(CallInst node);
	abstract public void visit(GetElementPtrInst node);
	abstract public void visit(IcmpInst node);
	abstract public void visit(LoadInst node);
	abstract public void visit(PhiInst node);
	abstract public void visit(RetInst node);
	abstract public void visit(StoreInst node);
	
	//Symbol
	abstract public void visit(IRSymbol node);
	abstract public void visit(IRRegister node);
	abstract public void visit(IRGlobalVariable node);
	abstract public void visit(IRGlobalString node);
	abstract public void visit(IRConstInt node);
	abstract public void visit(IRConstBool node);
	abstract public void visit(IRConstString node);
	abstract public void visit(IRNull node);
}
