package Riscv;

import java.util.LinkedList;

import IR.IRFunction;

public class RvFunction {
	private String name;
	private IRFunction irFunction;
	private LinkedList<RvBlock> blockList; 
	
	public RvFunction(String name, IRFunction irFunction) {
		this.name = name;
		this.irFunction = irFunction;
		this.blockList = new LinkedList<RvBlock>();
	}
	
	public String getName() {
		return name;
	}
	
	public IRFunction getIRFunction() {
		return irFunction;
	}
	
	public void addBlock(RvBlock block) {
		blockList.add(block);
	}
	
	public LinkedList<RvBlock> getBlockList() {
		return blockList;
	}	
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}