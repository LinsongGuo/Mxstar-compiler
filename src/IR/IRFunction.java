package IR;

import java.util.ArrayList;
import java.util.HashMap;

import IR.Symbol.IRRegister;
import IR.Type.IRType;
import IR.Type.IRVoidType;

public class IRFunction {
	private IRType type;
	private String name;
	//private SymbolHash symbolHash;
	private ArrayList<IRRegister> parameters;
	private IRBasicBlock entranceBlock, exitBlock, returnBlock;
	private IRRegister returnValue;
	private HashMap<String, ArrayList<IRRegister>> registerHash;
	private HashMap<String, ArrayList<IRBasicBlock>> blockHash;
	
	public IRFunction(IRType type, String name, ArrayList<IRRegister> parameters) {
		this.type = type;
		this.name = name;
		this.parameters = parameters;
		registerHash = new HashMap<String, ArrayList<IRRegister>>();
		blockHash = new HashMap<String, ArrayList<IRBasicBlock>>();
		entranceBlock = new IRBasicBlock("entranceBlock");
		exitBlock = new IRBasicBlock("exitBlock");
		returnBlock = new IRBasicBlock("returnBlock");
		if (type instanceof IRVoidType) {
			returnValue = null;
		}
		else {
			returnValue = new IRRegister(type, name);
		}
	}
	
	public void addParameter(IRRegister reg) {
		parameters.add(reg);
	}
	
	public IRType getType() {
		return type;
	}
	
	public String getName() {
		return "@" + name;
	}
	
	public IRBasicBlock getEntranceBlock() {
		return entranceBlock;
	} 
	
	public IRBasicBlock getExitBlock() {
		return exitBlock;
	}
	
	public IRBasicBlock getReturnBlock() {
		return returnBlock;
	}
	
	public IRRegister getReturnValue() {
		return returnValue;
	}
	
	public void addRegister(IRRegister reg) { 
		String name = reg.getName();
		if (!registerHash.containsKey(name)) {
			registerHash.put(name, new ArrayList<IRRegister>());
		}
		ArrayList<IRRegister> tmp = registerHash.get(name);
		reg.setName(name);
		tmp.add(reg);
	 }
	
	public void addBasicBlock(IRBasicBlock block) {
		String name = block.getName();
		if (!blockHash.containsKey(name)) {
			blockHash.put(name, new ArrayList<IRBasicBlock>());
		}
		ArrayList<IRBasicBlock> tmp = blockHash.get(name);
		block.setName(name);
		tmp.add(block);
	}
}
