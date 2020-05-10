package IR;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import IR.Symbol.IRRegister;
import IR.Type.IRType;
import Riscv.RvFunction;

public class IRFunction {
	private IRType type;
	private String name;
	private ArrayList<IRRegister> parameters;
	private HashMap<String, ArrayList<IRRegister>> registerHash;
	private HashMap<String, ArrayList<IRBasicBlock>> blockHash;
	private IRBasicBlock entranceBlock, lastBlock, exitBlock;
	
	//for Constructing SSA
	private ArrayList<IRBasicBlock> dfsSeq;
	private HashSet<IRBasicBlock> visited;
	private ArrayList<IRBasicBlock> RdfsSeq;
	private HashSet<IRBasicBlock> Rvisited;
	
	public IRFunction(IRType type, String name) {
		this.type = type;
		this.name = name;
		this.parameters = new ArrayList<IRRegister>();
		this.entranceBlock = this.lastBlock = this.exitBlock = null;
		this.registerHash = new HashMap<String, ArrayList<IRRegister>>();
		this.blockHash = new HashMap<String, ArrayList<IRBasicBlock>>();
	}
	
	public IRFunction(IRType type, String name, ArrayList<IRRegister> parameters) {
		this.type = type;
		this.name = name;
		this.parameters = parameters;
		this.entranceBlock = this.lastBlock = this.exitBlock = null;
		this.registerHash = new HashMap<String, ArrayList<IRRegister>>();
		this.blockHash = new HashMap<String, ArrayList<IRBasicBlock>>();
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
	
	public String definitionString () {
		StringBuilder builder = new StringBuilder("define " + type.toString() + " @" + name + "(");
		for(int i = 0; i < parameters.size(); ++i) {
			IRRegister parameter = parameters.get(i);
			builder.append(parameter.getType().toString() + " " + parameter.toString());
			if (i + 1 < parameters.size())
				builder.append(", ");
		}
		builder.append(")");
		return builder.toString();
	}
	
	public ArrayList<IRRegister> getParameters() {
		return parameters;
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

	public void addRegister(IRRegister reg) { 
		String name = reg.getName();
		if (!registerHash.containsKey(name)) {
			registerHash.put(name, new ArrayList<IRRegister>());
		}
		ArrayList<IRRegister> tmp = registerHash.get(name);
		reg.setName(name + "." + String.valueOf(tmp.size()));
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
		block.setName(name + "." + String.valueOf(tmp.size()));
		tmp.add(block);
		block.setCurrentFunction(this);
		if (entranceBlock == null) {
			entranceBlock = block;
			lastBlock = block;
		}
		else {
			block.setPrev(lastBlock);
			lastBlock.setNext(block);
			lastBlock = block;
		}
	}
	
	public ArrayList<IRBasicBlock> getBlockList() {
		ArrayList<IRBasicBlock> blockList = new ArrayList<IRBasicBlock>();
		IRBasicBlock block = entranceBlock;
		while (block != null) {
			blockList.add(block);
			block = block.getNext();
		}
		return blockList;
	}

	public IRBasicBlock getEntranceBlock() {
		return entranceBlock;
	} 
	
	public void setLastBlock(IRBasicBlock block) {
		lastBlock = block;
	}
	
	public IRBasicBlock getExitBlock() {
		return exitBlock;
	}
	
	public void setExitBlock(IRBasicBlock block) {
		exitBlock = block;
	}
	
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public ArrayList<IRBasicBlock> getDfsSeq() {
		return dfsSeq;
	}
	
	public ArrayList<IRBasicBlock> getRDfsSeq() {
		return RdfsSeq;
	}
	
	public ArrayList<IRBasicBlock> dfsBasicBlock() {
		dfsSeq = new ArrayList<IRBasicBlock>();
		visited = new HashSet<IRBasicBlock>();
		entranceBlock.dfs(visited, dfsSeq);
		return dfsSeq;
	}

	public ArrayList<IRBasicBlock> RdfsBasicBlock() {
		RdfsSeq = new ArrayList<IRBasicBlock>();
		Rvisited = new HashSet<IRBasicBlock>();
	//	System.err.println("exit " + exitBlock);
		exitBlock.Rdfs(Rvisited, RdfsSeq);
		return RdfsSeq;
	}
	
	//for instruction selection
	private RvFunction function;
	
	public void setRvFunction(RvFunction function) {
		this.function = function;
	}
	
	public RvFunction toRvFunction() {
		return function;
	}
}
