package Riscv;

import java.util.ArrayList;

import Riscv.Operand.RvGlobalString;
import Riscv.Operand.RvGlobalVariable;

public class RvModule {
	private ArrayList<RvFunction> functions;
	private ArrayList<RvGlobalVariable> globalVariables;
	private ArrayList<RvGlobalString> globalStrings;

	public RvModule() {
		functions = new ArrayList<RvFunction>();
		globalVariables = new ArrayList<RvGlobalVariable>();
		globalStrings = new ArrayList<RvGlobalString>();
	}
	
	public void addFunction(RvFunction function) {
		functions.add(function);
	}
	
	public void addGlobalVariable(RvGlobalVariable globalVariable) {
		globalVariables.add(globalVariable);
	}
	
	public void addGlobalString(RvGlobalString globalString) {
		globalStrings.add(globalString);
	}
	
	public ArrayList<RvFunction> getFunctions() {
		return functions;
	}
	
	public ArrayList<RvGlobalVariable> getGlobalVariables() {
		return globalVariables;
	}
	
	public ArrayList<RvGlobalString> getGlobalString() {
		return globalStrings;
	}
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
