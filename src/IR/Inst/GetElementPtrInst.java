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
	}
	
	public GetElementPtrInst(IRSymbol result, IRSymbol ptr, IRSymbol idx) {
		super();
		this.result = result;
		this.ptr = ptr;
		index = new ArrayList<IRSymbol>();
		index.add(idx);
	}
	
	public GetElementPtrInst(IRSymbol result, IRSymbol ptr, IRSymbol idx, IRSymbol idx2) {
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
			StringBuilder builder = new StringBuilder("getelementptr " + type.toString() + ", " + ptrType.toString() + " " + ptr.toString() + ", ");
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
}
