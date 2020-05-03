package Riscv;

import java.util.ArrayList;
import java.util.HashSet;

import Riscv.Operand.RvRegister;
import Riscv.Operand.RvVirtualRegister;

public class RvFunction {
	private String name;
	private ArrayList<RvBlock> blockList; 
	private RvBlock entranceBlock;
	private int parameters;
	private ArrayList<RvRegister> regList;
	
	public RvFunction(String name, int parameters) {
		this.name = name;
		this.parameters = parameters;
		blockList = new ArrayList<RvBlock>();
		regList = new ArrayList<RvRegister>();
	}
	
	public RvVirtualRegister newRegister(String name) {
		name = name + "_" + String.valueOf(regList.size());
		RvVirtualRegister reg = new RvVirtualRegister(name);
		regList.add(reg);
		return reg;
	}
	
	public ArrayList<RvRegister> getRegList() {
		return regList;
	}
	
	public void setEntranceBlock(RvBlock entranceBlock) {
		this.entranceBlock = entranceBlock;
	}
	
	public RvBlock getEntranceBlock() {
		return entranceBlock;
	}
	
	public String getName() {
		return name;
	}
	
	public void addBlock(RvBlock block) {
		blockList.add(block);
	}
	
	public ArrayList<RvBlock> getBlockList() {
		return blockList;
	}	
	
	public int getParameters() {
		return parameters;
	}
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
	
	public ArrayList<RvBlock> getDfsOrder() {
		ArrayList<RvBlock> order = new ArrayList<RvBlock>();
		HashSet<RvBlock> visited = new HashSet<RvBlock>();
		entranceBlock.dfs(order, visited);
		return order;
	}
}