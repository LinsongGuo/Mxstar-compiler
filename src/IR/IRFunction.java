package IR;

import java.util.ArrayList;

import IR.Symbol.IRRegister;
import IR.Type.IRType;

public class IRFunction {
	private IRType type;
	private String name;
	private ArrayList<IRRegister> parameters;
	
	public IRFunction(IRType type, String name, ArrayList<IRRegister> parameters) {
		this.type = type;
		this.name = name;
		this.parameters = parameters;
	}
	
	public IRType getType() {
		return type;
	}
	
	public String getName() {
		return "@" + name;
	}
	
}
