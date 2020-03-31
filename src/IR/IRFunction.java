package IR;

import java.io.PrintWriter;
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
	//private IRBasicBlock entranceBlock, returnBlock;
	//private IRRegister returnValue;
	private HashMap<String, ArrayList<IRRegister>> registerHash;
	private HashMap<String, ArrayList<IRBasicBlock>> blockHash;
	private ArrayList<IRBasicBlock> blockList;
	
	public IRFunction(IRType type, String name) {
		this.type = type;
		this.name = name;
		this.parameters = new ArrayList<IRRegister>();
		registerHash = new HashMap<String, ArrayList<IRRegister>>();
		blockHash = new HashMap<String, ArrayList<IRBasicBlock>>();
		blockList = new ArrayList<IRBasicBlock>();
		/*
		if (type instanceof IRVoidType) {
			returnValue = null;
		}
		else {
			returnValue = new IRRegister(type, name);
		}
		*/
	}
	
	public IRFunction(IRType type, String name, ArrayList<IRRegister> parameters) {
		this.type = type;
		this.name = name;
		this.parameters = parameters;
		//entranceBlock = new IRBasicBlock("entranceBlock");
		//exitBlock = new IRBasicBlock("exitBlock");
		//returnBlock = new IRBasicBlock("returnBlock");
		registerHash = new HashMap<String, ArrayList<IRRegister>>();
		blockHash = new HashMap<String, ArrayList<IRBasicBlock>>();
		blockList = new ArrayList<IRBasicBlock>();
		/*
		if (type instanceof IRVoidType) {
			returnValue = null;
		}
		else {
			returnValue = new IRRegister(type, name);
		}
		*/
	}
	
	public String declarationString () {
		StringBuilder builder = new StringBuilder("declare " + type.toString() + " @" + name + "(");
		for(int i = 0; i < parameters.size(); ++i) {
			IRRegister parameter = parameters.get(i);
			builder.append(parameter.getType().toString() + " " + parameter.toString());
			if (i + 1 < parameters.size())
				builder.append(", ");
		}
		builder.append(")");
		return builder.toString();
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

	/*
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
	}*/
	
	public void addRegister(IRRegister reg) { 
		String name = reg.getName();
		if (!registerHash.containsKey(name)) {
			registerHash.put(name, new ArrayList<IRRegister>());
		}
		ArrayList<IRRegister> tmp = registerHash.get(name);
		reg.setName(name);
		tmp.add(reg);
	}
	

	public IRRegister getRegister(String name) {
		return registerHash.get(name).get(0);
	}
	
	public void addBasicBlock(IRBasicBlock block) {
		String name = block.getName();
		if (!blockHash.containsKey(name)) {
			blockHash.put(name, new ArrayList<IRBasicBlock>());
		}
		ArrayList<IRBasicBlock> tmp = blockHash.get(name);
		block.setName(name);
		tmp.add(block);
		blockList.add(block);
	}
	
	public ArrayList<IRBasicBlock> getBlockList() {
		return blockList;
	}
	
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
}
