package Riscv.Operand;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import Riscv.Inst.RvMove;

public class RvRegister extends RvOperand {
	protected String name;
	
	public RvRegister(String name) {
		this.name = name;
		moveList = new ArrayList<RvMove>();
		adjList = new LinkedHashSet<RvRegister>();
		degree = 0;
		alias = this;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
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
	
	public void setColor(RvPhysicalRegister color) {
		this.color = color;
	}
	
	public RvPhysicalRegister getColor() {
		return color;
	}
}
