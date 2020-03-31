package IR.Inst;

import java.util.ArrayList;

import IR.IRFunction;
import IR.IRVisitor;
import IR.Symbol.IRSymbol;

public class CallInst extends IRInst {
	private IRFunction function;
	private ArrayList<IRSymbol> parameters;
	private IRSymbol result;
	

	public CallInst(IRFunction function, ArrayList<IRSymbol> parameters) {
		super();
		this.function = function;
		this.parameters = parameters;
		this.result = null;
	}
	
	public CallInst(IRFunction function, ArrayList<IRSymbol> parameters, IRSymbol result) {
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
}
