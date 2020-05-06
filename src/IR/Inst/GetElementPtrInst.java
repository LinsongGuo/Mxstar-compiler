package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRPtrType;
import IR.Type.IRType;

public class GetElementPtrInst extends IRInst {
	private IRSymbol ptr;
	private IRRegister result;
	private ArrayList<IRSymbol> index;
	
	public GetElementPtrInst(IRRegister result, IRSymbol ptr, ArrayList<IRSymbol> index) {
		super();
		this.result = result;
		this.ptr = ptr;
		this.index = index;
	}
	
	public GetElementPtrInst(IRRegister result, IRSymbol ptr, IRSymbol idx) {
		super();
		this.result = result;
		this.ptr = ptr;
		index = new ArrayList<IRSymbol>();
		index.add(idx);
	}
	
	public GetElementPtrInst(IRRegister result, IRSymbol ptr, IRSymbol idx, IRSymbol idx2) {
		super();
		this.result = result;
		this.ptr = ptr;
		index = new ArrayList<IRSymbol>();
		index.add(idx);
		index.add(idx2);
	}
	
	@Override
	public String toString() {
		IRType ptrType = ptr.getType(), type;
		if (ptrType instanceof IRPtrType) {
			type = ((IRPtrType) ptrType).getType();
			StringBuilder builder = new StringBuilder(result.toString() + " = ");
			builder.append("getelementptr " + type.toString() + ", " + ptrType.toString() + " " + ptr.toString() + ", ");
			for (int i = 0; i < index.size(); ++i) {
				builder.append(index.get(i).getType().toString() + " " + index.get(i).toString());
				if (i + 1 < index.size()) 
					builder.append(", ");
			}
			return builder.toString();
		}
		else {
			System.err.println("getelementptr error!");
			return null;
		}
	}
	
	public int bytes() {
		 return ((IRPtrType) ptr.getType()).getType().bytes();
	}
	
	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		boolean flag = false;
		if (ptr == old) {
			ptr = nw;
			flag = true;
		}
		for (int i = 0; i < index.size(); ++i) {
			if (index.get(i) == old) {
				index.set(i, nw);
				flag = true;
			}
		}	
		if (flag) {
		//	old.removeUse(this);
			nw.addUse(this);
		}
	}
	
	public IRRegister getRes() {
		return result;
	}

	@Override
	public void removeAllUse() {
		ptr.removeUse(this);
		for (IRSymbol s : index) {
			s.removeUse(this);
		}		
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InitDefUse() {
		result.addDef(this);
		ptr.addUse(this);
		for (IRSymbol s : index) {
			s.addUse(this);
		}	
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		if (ptr instanceof IRRegister)
			res.add((IRRegister) ptr);
		for (IRSymbol symbol : index) {
			if (symbol instanceof IRRegister)
				res.add((IRRegister) symbol);
		}
		return res;
	}
	
	public IRSymbol getPtr() {
		return ptr;
	}
	
	public IRSymbol getIndex0() {
		return index.get(0);
	}
	
	public IRSymbol getIndex1() {
		if (index.size() == 1) 
			return null;
		return index.get(1);
	}
	
	public ArrayList<IRSymbol> getIndex() {
		return index;
	}
}
