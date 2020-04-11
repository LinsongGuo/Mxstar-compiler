package IR.Inst;

import java.util.ArrayList;

import IR.IRFunction;
import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class CallInst extends IRInst {
	private IRFunction function;
	private ArrayList<IRSymbol> parameters;
	private IRSymbol result;
	
	public CallInst(IRFunction function, IRSymbol parameter) {
		super();
		this.function = function;
		this.parameters = new ArrayList<IRSymbol>();
		this.parameters.add(parameter);
		this.result = null;
		for (IRSymbol para : parameters) {
			para.addUse(this);
		}		
	}
	
	public CallInst(IRFunction function, ArrayList<IRSymbol> parameters) {
		super();
		this.function = function;
		this.parameters = parameters;
		this.result = null;
		for (IRSymbol para : parameters) {
			para.addUse(this);
		}		
	}
	
	public CallInst(IRFunction function, IRSymbol parameter, IRSymbol result) {
		super();
		this.function = function;
		this.parameters = new ArrayList<IRSymbol>();
		this.parameters.add(parameter);
		this.result = result;
		for (IRSymbol para : parameters) {
			para.addUse(this);
		}		
	}
	
	public CallInst(IRFunction function, ArrayList<IRSymbol> parameters, IRSymbol result) {
		super();
		this.function = function;
		this.parameters = parameters;
		this.result = result;
		for (IRSymbol para : parameters) {
			para.addUse(this);
		}		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (result != null) builder.append(result.toString() + " = ");
		builder.append("call " + function.getType().toString() + " " + function.getName() + "(");
		for (int i = 0; i < parameters.size(); ++i) {
			builder.append(parameters.get(i).getType().toString() + " " + parameters.get(i).toString());
			if (i + 1 < parameters.size()) 
				builder.append(", ");
		}
		builder.append(")");
		return builder.toString();
	}
	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void replaceUse(IRSymbol old, IRSymbol nw) {
		boolean flag = false;
		for (int i = 0; i < parameters.size(); ++i) {
			if (parameters.get(i) == old) {
				parameters.set(i, nw);
				flag = true;
			}
		}
		if (flag) {
			//old.removeUse(this);
			nw.addUse(this);
		}
	}
	
	public IRSymbol getRes() {
		return result;
	}

	@Override
	public void removeAllUse() {
		for (int i = 0; i < parameters.size(); ++i) {
			parameters.get(i).removeUse(this);
		}
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}
}
