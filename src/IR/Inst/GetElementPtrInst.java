package IR.Inst;

import java.util.ArrayList;

import IR.IRVisitor;
import IR.Symbol.IRSymbol;
import IR.Type.IRPtrType;
import IR.Type.IRType;

public class GetElementPtrInst extends IRInst {
	private IRSymbol ptr, result;
	private ArrayList<IRSymbol> index;
	
	public GetElementPtrInst(IRSymbol result, IRSymbol ptr, ArrayList<IRSymbol> index) {
		super();
		this.result = result;
		this.ptr = ptr;
		this.index = index;
		ptr.addUse(this);
		for (IRSymbol s : index) {
			s.addUse(this);
		}	
	}
	
	public GetElementPtrInst(IRSymbol result, IRSymbol ptr, IRSymbol idx) {
		super();
		this.result = result;
		this.ptr = ptr;
		index = new ArrayList<IRSymbol>();
		index.add(idx);
		ptr.addUse(this);
		for (IRSymbol s : index) {
			s.addUse(this);
		}	
	}
	
	public GetElementPtrInst(IRSymbol result, IRSymbol ptr, IRSymbol idx, IRSymbol idx2) {
		super();
		this.result = result;
		this.ptr = ptr;
		index = new ArrayList<IRSymbol>();
		index.add(idx);
		index.add(idx2);
		ptr.addUse(this);
		for (IRSymbol s : index) {
			s.addUse(this);
		}	
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
	
	public IRSymbol getRes() {
		return result;
	}

	@Override
	public void removeAllUse() {
		ptr.removeUse(this);
		for (int i = 0; i < index.size(); ++i) {
			index.get(i).removeUse(this);
		}	
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}
}
