package Riscv.Operand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import Riscv.Inst.RvInst;
import Riscv.Inst.RvMove;

public class RvRegister extends RvOperand {
	protected String name;
	protected HashSet<RvInst> use, def;
	protected boolean spilled;
	
	public RvRegister(String name) {
		this.name = name;
		moveList = new ArrayList<RvMove>();
		adjList = new LinkedHashSet<RvRegister>();
		degree = 0;
		alias = this;
		use = new HashSet<RvInst>();
		def = new HashSet<RvInst>();
		spilled = false;
		color = null;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		//return name;
		return color == null ? name : color.getName();
	}
	
	public void addUse(RvInst inst) {
		use.add(inst);
	}
	
	public void addDef(RvInst inst) {
		def.add(inst);
	}
	
	public void removeUse(RvInst inst) {
		use.remove(inst);
	}
	
	public void removeDef(RvInst inst) {
		def.remove(inst);
	}
	
	public HashSet<RvInst> getDef() {
		return def;
	}
	
	public HashSet<RvInst> getUse() {
		return use;
	}
	
	public void setSpilled() {
		spilled = true;
	}
	
	public boolean isSpilled() {
		return spilled;
	}
	
	//for register allocation.
	private ArrayList<RvMove> moveList;
	private LinkedHashSet<RvRegister> adjList;
	private int degree;
	private RvRegister alias;
	private double spillCost;
	protected RvPhysicalRegister color;
	
	public void addMove(RvMove inst) {
		moveList.add(inst);
	}
	
	public ArrayList<RvMove> getMoveList() {
		return moveList;
	}
	
	public void addAdj(RvRegister other) {
		adjList.add(other);
	}
	
	public LinkedHashSet<RvRegister> getAdjList() {
		return adjList;
	}
	
	public void increaseDegree() {
		++degree;
	}
	
	public void decreaseDegree() {
		--degree;
	}
	
	public int getDegree() {
		return degree;
	}
	
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public void setAlias(RvRegister alias) {
		this.alias = alias;
	}
	
	public RvRegister getAlias() {
		return alias;
	}
	
	public double getSpillCost() {
		return spillCost / degree;
	}
	
	public void increaseSpillCost(boolean inLoop) {
		spillCost += (inLoop ? 10 : 1);
	}
	
	public void decreaseSpillCost(boolean inLoop) {
		spillCost -= (inLoop ? 10 : 1);
	}
	
	public void setColor(RvPhysicalRegister color) {
		this.color = color;
	}
	
	public RvPhysicalRegister getColor() {
		return color;
	}
	
	public void clearColor() {
		moveList = new ArrayList<RvMove>();
		adjList = new LinkedHashSet<RvRegister>();
		degree = 0;
		alias = this;
		color = null;
	}
}
