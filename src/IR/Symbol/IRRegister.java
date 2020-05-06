package IR.Symbol;

import IR.IRVisitor;
import IR.Inst.BrInst;
import IR.Inst.IRInst;
import IR.Inst.LoadInst;
import IR.Inst.StoreInst;
import IR.Type.IRType;
import Riscv.Operand.RvRegister;

public class IRRegister extends IRSymbol {	
	protected String name;
	
	public IRRegister(IRType type, String name) {
		super(type);
		this.name = name;	
		status = Status.undefined;
		constant = null;
	}

	@Override
	public String toString() {
		return "%" + name;
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	//for SCCP
	public enum Status {
		undefined, constant, multiDefined;		
	}
	public Status status;
	private IRConst constant; 
		
	public void setConstant(IRConst constant) {
		this.constant = constant;
	}
	
	public IRConst getConstant() {
		return constant;
	}
	
	//for instruction selection
	private RvRegister rvReg;
	
	public void setRvReg(RvRegister rvReg) {
		this.rvReg = rvReg;
	}
	
	public RvRegister getRvReg() {
		return rvReg;
	}
	
	public boolean onlyBeUsedByBranch() {
		for (IRInst inst : useList) {
			if (!(inst instanceof BrInst)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean onlyBeUsedByLoadStore() {
		for (IRInst inst : useList) {
			if (inst instanceof LoadInst) {
				if (inst.getRes().equals(this) || !((LoadInst) inst).getPtr().equals(this))
					return false;
			}
			else if (inst instanceof StoreInst) {
				if (((StoreInst) inst).getValue().equals(this) || !((StoreInst) inst).getPtr().equals(this))
					return false;
			}
			else
				return false;
		}
		return true;
	}

}
