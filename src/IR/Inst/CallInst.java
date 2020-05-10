package IR.Inst;

import java.util.ArrayList;

import IR.IRFunction;
import IR.IRVisitor;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class CallInst extends IRInst {
	private IRFunction function;
	private ArrayList<IRSymbol> parameters;
	private IRRegister result;
	private boolean inlined;
	
	public CallInst(IRFunction function, IRSymbol parameter) {
		super();
		this.function = function;
		this.parameters = new ArrayList<IRSymbol>();
		this.parameters.add(parameter);
		this.result = null;
		this.inlined = false;
	}
	
	public CallInst(IRFunction function, ArrayList<IRSymbol> parameters) {
		super();
		this.function = function;
		this.parameters = parameters;
		this.result = null;	
		this.inlined = false;
	}
	
	public CallInst(IRFunction function, IRSymbol parameter, IRRegister result) {
		super();
		this.function = function;
		this.parameters = new ArrayList<IRSymbol>();
		this.parameters.add(parameter);
		this.result = result;	
		this.inlined = false;
	}
	
	public CallInst(IRFunction function, ArrayList<IRSymbol> parameters, IRRegister result) {
		super();
		this.function = function;
		this.parameters = parameters;
		this.result = result;
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
	
	public IRRegister getRes() {
		return result;
	}

	@Override
	public void removeAllUse() {
		for (IRSymbol para : parameters) {
			para.removeUse(this);
		}		
	}

	@Override
	public void removeAllDef() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InitDefUse() {
		if (result != null) result.addDef(this);
		for (IRSymbol para : parameters) {
			para.addUse(this);
		}		
	}

	@Override
	public ArrayList<IRRegister> getUsedRegister() {
		ArrayList<IRRegister> res =	new ArrayList<IRRegister>();
		for (IRSymbol parameter : parameters) {
			if (parameter instanceof IRRegister)
				res.add((IRRegister) parameter);
		}
		return res;
	}
	
	public ArrayList<IRSymbol> getParameters() {
		return parameters;
	}
	
	public IRFunction getFunction() {
		return function;
	}
	
	public void setInlined() {
		inlined = true;
	}
	
	public boolean inlined() {
		return inlined;
	}
}
