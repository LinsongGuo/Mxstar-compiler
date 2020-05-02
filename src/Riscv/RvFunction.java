package Riscv;

import java.util.ArrayList;

public class RvFunction {
	private String name;
	private ArrayList<RvBlock> blockList; 
	
	public RvFunction(String name) {
		this.name = name;
		this.blockList = new ArrayList<RvBlock>();
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
	
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}