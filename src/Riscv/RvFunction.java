package Riscv;

import java.util.ArrayList;
import java.util.HashSet;

import Riscv.Inst.RvInst;
import Riscv.Inst.RvTypeI;
import Riscv.Operand.RegisterTable;
import Riscv.Operand.RvImm;
import Riscv.Operand.RvRegister;
import Riscv.Operand.RvStackSlot;
import Riscv.Operand.RvVirtualRegister;

public class RvFunction {
	private String name;
	private ArrayList<RvBlock> blockList; 
	private RvBlock entranceBlock, exitBlock;
	private int parameters;
	private ArrayList<RvRegister> regList;
	
	public RvFunction(String name, int parameters) {
		this.name = name;
		this.parameters = parameters;
		blockList = new ArrayList<RvBlock>();
		regList = new ArrayList<RvRegister>();
		spills = new ArrayList<RvStackSlot>();
		calls = new ArrayList< ArrayList<RvStackSlot> >();
		entranceBlock = exitBlock = null;
	}
	
	public RvVirtualRegister newRegister(String name) {
		//System.err.println("enter " + regList);
		name = name + "_" + String.valueOf(regList.size());
		RvVirtualRegister reg = new RvVirtualRegister(name);
		regList.add(reg);
		//System.err.println("new reg " + reg + " " + regList);
		return reg;
	}
	
	public ArrayList<RvRegister> getRegList() {
		return regList;
	}
	
	public void setEntranceBlock(RvBlock entranceBlock) {
		this.entranceBlock = entranceBlock;
	}
	
	public void setExitBlock(RvBlock exitBlock) {
		this.exitBlock = exitBlock;
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
	
	//stack slot allocation.
	private ArrayList<RvStackSlot> spills;
	private ArrayList< ArrayList<RvStackSlot> > calls;
	
	public void addSpillStackSlot(RvStackSlot slot) {
		spills.add(slot);
	}
	
	public void addCallStackSlot(ArrayList<RvStackSlot> slots) {
		calls.add(slots);
	}
	
	public int stackSlotAllocation() {
		int maxCall = 0;
		for (ArrayList<RvStackSlot> slots : calls) {
			if (maxCall < slots.size())
				maxCall = slots.size();
		}
		int size = maxCall + spills.size();
		size = (size + 3) / 4 * 4;
		/*
		for (int i = 0; i < spills.size(); ++i) {
			spills.get(i).setIndex((size - (i + 1)) << 2);
		}
		
		for (ArrayList<RvStackSlot> slots : calls) {
			for (int i = 0; i < slots.size(); ++i) {
				slots.get(i).setIndex(i << 2);
			}
		}
		*/
		if (size > 0) {
			RvInst inst = new RvTypeI(entranceBlock, RvTypeI.Op.addi, RegisterTable.sp, RegisterTable.sp, new RvImm(-size << 2));
			entranceBlock.insertPrev(entranceBlock.getHead(), inst);
			inst = new RvTypeI(entranceBlock, RvTypeI.Op.addi, RegisterTable.sp, RegisterTable.sp, new RvImm(size <<2));
			exitBlock.insertPrev(exitBlock.getTail(), inst);
		}
		
		return size;
	}
	
}