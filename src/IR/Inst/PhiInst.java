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
		symbol.addUse(this);
		bb.addPhiUse(this);
	}
	
	public IRRegister getRes() {
		return res;
	}
	
	public ArrayList<IRSymbol> getSymbols() {
		return symbols;
	}
	
	public ArrayList<IRBasicBlock> getBBs() {
		return bbs;
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
			nw.addPhiUse(this);
		}
	}
	
	public void removePhiUse(IRBasicBlock bb) {
		int index = bbs.indexOf(bb);
		if (index != -1) {
			bbs.remove(index);
			symbols.remove(index);
		}
	}

	@Override
	public void InitDefUse() {
		res.addDef(this);
		//System.err.println("------------phi " + res);
		for (IRSymbol s : symbols) {
			s.addUse(this);
		}
		for (IRBasicBlock block : bbs) {
			block.addPhiUse(this);
		}
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res = new ArrayList<IRRegister>();
		for (IRSymbol s : symbols) {
			if (s instanceof IRRegister) {
				res.add((IRRegister) s);
			}
		}
		return res;
	}
	
	public void simplify() {
		if (bbs.size() == 1) {
			assert symbols.size() == 1;
			IRSymbol symbol = symbols.get(0);
			res.replaceUse(symbol);
			IRBasicBlock bb = bbs.get(0);
			bb.removePhiUse(this);
			removeItself();
		}
	}
}
