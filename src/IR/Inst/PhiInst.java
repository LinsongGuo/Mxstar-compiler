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
		for (IRSymbol s : symbols) {
			s.addUse(this);
		}
		for (IRBasicBlock block : bbs) {
			block.addPhiUse(this);
		}
	}

	public PhiInst(IRRegister res, ArrayList<IRSymbol> symbols, ArrayList<IRBasicBlock> bbs) {
		this.res = res;
		this.symbols = symbols;
		this.bbs = bbs;
		for (IRSymbol s : symbols) {
			s.addUse(this);
		}
		for (IRBasicBlock block : bbs) {
			block.addPhiUse(this);
		}
	}
	
	public void addBranch(IRSymbol symbol, IRBasicBlock bb) {
		symbols.add(symbol);
		bbs.add(bb);
		symbol.addUse(this);
		bb.addPhiUse(this);
	}
	
	public IRRegister getRes() {
		return res;
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

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		boolean flag = false;
		for (int i = 0; i < symbols.size(); ++i) {
			if (symbols.get(i) == old) {
				symbols.set(i, nw);
				flag = true;
			}
		}
		if (flag) {
		//	old.removeUse(this);
			nw.addUse(this);
		}
	}
	
	@Override
	public void removeAllUse() {
		for (IRSymbol s : symbols) {
			s.removeUse(this);
		}
		for (IRBasicBlock block : bbs) {
			block.removePhiUse(this);
		}
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}
	
	public void replacePhiUse(IRBasicBlock old, IRBasicBlock nw) {
		boolean flag = false;
		for (int i = 0; i < bbs.size(); ++i) {
			if (bbs.get(i) == old) {
				bbs.set(i, nw);
				flag = true;
			}
		}
		if (flag) {
		//	old.removePhiUse(this);
			nw.addPhiUse(this);
		}
	}
	
	public void removeAllPhiUse() {
		for (IRBasicBlock block : bbs) {
			block.removePhiUse(this);
		}
	}
}
