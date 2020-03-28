package IR.Inst;

import java.util.ArrayList;

import IR.IRBasicBlock;
import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class PhiInst extends IRInst {
	private IRRegister res;
	private ArrayList<IRSymbol> symbols;
	private ArrayList<IRBasicBlock> bbs;
	

	public PhiInst(IRRegister res) {
		this.res = res;
		this.symbols = new ArrayList<IRSymbol>();
		this.bbs = new ArrayList<IRBasicBlock>();
	}

	public PhiInst(IRRegister res, ArrayList<IRSymbol> symbols, ArrayList<IRBasicBlock> bbs) {
		this.res = res;
		this.symbols = symbols;
		this.bbs = bbs;
	}
	
	public void addBranch(IRSymbol symbol, IRBasicBlock bb) {
		symbols.add(symbol);
		bbs.add(bb);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(res.toString() + " = phi " + res.getType().toString() + " ");
		for (int i = 0; i < symbols.size(); ++i) { 
			builder.append(" [ " + symbols.get(i).toString() + ", " + bbs.get(i).toString() + " ]");
			if (i + 1 < symbols.size()) 
				builder.append(", ");
		}
		return builder.toString();
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}

}
