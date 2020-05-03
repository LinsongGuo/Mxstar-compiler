package codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Stack;

import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvModule;
import Riscv.Inst.RvInst;
import Riscv.Inst.RvMove;
import Riscv.Operand.RegisterTable;
import Riscv.Operand.RvPhysicalRegister;
import Riscv.Operand.RvRegister;

public class RegisterAllocation {
	
	private static class Edge {
		public RvRegister u, v;
		
		public Edge(RvRegister u, RvRegister v) {
			this.u = u;
			this.v = v;
		}
		
		@Override
		public boolean equals(Object other) {
			return (other instanceof Edge) && u.equals(((Edge) other).u) && v.equals(((Edge) other).v);
		}
		
		@Override
		public int hashCode() {
			return u.hashCode() ^ v.hashCode();
	    }
	}
	 
	private RvModule module;
	private RegisterTable regTable;
	private int K;
	
	//every register is always in exactly one of the HashSets or lists.
	//private HashSet<RvRegister> preColored;
	private HashSet<RvRegister> initial;
	private LinkedHashSet<RvRegister> simplifyWorkList;
	private LinkedHashSet<RvRegister> freezeWorkList;
	private LinkedHashSet<RvRegister> spillWorkList;
	private HashSet<RvRegister> spilledNodes;
	private HashSet<RvRegister> coalescedNodes;
	private HashSet<RvRegister> coloredNodes;
	private Stack<RvRegister> selectStack;
	
	//every move is in exactly one of the sets.
	private HashSet<RvMove> coalescedMoves;
	private HashSet<RvMove> constrainedMoves;
	private HashSet<RvMove> frozenMoves;
	private LinkedHashSet<RvMove> workListMoves;
	private HashSet<RvMove> activeMoves;
	
	private HashSet<Edge> adjSet;
	
	public RegisterAllocation(RvModule module, RegisterTable regTable) {
		this.module = module;
		this.regTable = regTable;
		this.K = regTable.allocableNumber;
	}
	
	public void run() {
		ArrayList<RvFunction> functions = module.getFunctions();
		for (RvFunction function : functions) {
			while (true) {
				init(function);
				new LivenessAnalysis(function);
				build(function);
				makeWorkList();
				while(!simplifyWorkList.isEmpty() ||
					  !workListMoves.isEmpty()    ||
					  !freezeWorkList.isEmpty()   ||
					  !spillWorkList.isEmpty()) 
				{	
					if (!simplifyWorkList.isEmpty()) simplify();
					if (!workListMoves.isEmpty()) coalesce();	
					if (!freezeWorkList.isEmpty()) ;
					if (!spillWorkList.isEmpty());
				}
			}
		}
	}
	
	public void init(RvFunction function) {
		//preColored = new HashSet<RvRegister>();
		initial = new HashSet<RvRegister>(function.getRegList());
		simplifyWorkList = new LinkedHashSet<RvRegister>();
		freezeWorkList = new LinkedHashSet<RvRegister>();
		spillWorkList = new LinkedHashSet<RvRegister>();
		spilledNodes = new HashSet<RvRegister>();
		coalescedNodes = new HashSet<RvRegister>();
		coloredNodes = new HashSet<RvRegister>();
		selectStack = new Stack<RvRegister>();
		
		coalescedMoves = new HashSet<RvMove>(); //moves that have been coalesced.
		constrainedMoves = new HashSet<RvMove>(); //moves whose source and target interfere.
		frozenMoves = new HashSet<RvMove>(); //moves that will no longer be considered for coalescing.
		workListMoves = new LinkedHashSet<RvMove>(); //moves enabled for possible coalescing.
		activeMoves = new HashSet<RvMove>(); //moves not yet ready for coalescing.
		
		adjSet = new HashSet<Edge>();
	}
	
	private void addEdge(RvRegister u, RvRegister v) {
		if (!u.equals(v) && !adjSet.contains(new Edge(u, v))) {
			adjSet.add(new Edge(u, v));
			adjSet.add(new Edge(v, u));
			if (!isPreColored(u)) {
				u.addAdj(v);
				u.increaseDegree();
			}
			if (!isPreColored(v)) {
				v.addAdj(u);
				v.increaseDegree();
			}
		}
	}
	
	private void build(RvFunction function) {
		ArrayList<RvBlock> blocks = function.getBlockList();
		for (RvBlock block : blocks) {
			HashSet<RvRegister> live = new HashSet<>(block.getLiveOut());
			for (RvInst inst = block.getTail(); inst != null; inst = inst.getPrev()) {
				HashSet<RvRegister> use = inst.getUse();
				HashSet<RvRegister> def = inst.getDef();
				if (inst instanceof RvMove) {
					live.removeAll(use);
					HashSet<RvRegister> tmp = new HashSet<>(use);
					tmp.addAll(def);
					for (RvRegister reg : tmp) {
						reg.addMove((RvMove) inst);
					}
					workListMoves.add((RvMove) inst);
				}
				
				//live.addAll(def); ???
				for (RvRegister u : def) {
					for (RvRegister v : live) {
						addEdge(u, v);
					}
				}
				live.removeAll(def);
				live.addAll(use);
			}
		}
	}
	
	private boolean isPreColored(RvRegister reg) {
		return (reg instanceof RvPhysicalRegister);
	}
	
	private HashSet<RvMove> nodeMoves(RvRegister reg) { //moves that related to reg and may be coalesced in the future.
		HashSet<RvMove> res = new HashSet<>(activeMoves);
		res.addAll(workListMoves);
		res.retainAll(reg.getMoveList());
		return res;
	}
	
	private boolean moveRelated(RvRegister reg) {// Is reg in moves that may be coalesced in the future?
		return !nodeMoves(reg).isEmpty();
	}
	
	private void makeWorkList() {
		for (RvRegister reg : initial) {
			if (reg.getDegree() >= K) 
				spillWorkList.add(reg);
			else if (moveRelated(reg))
				freezeWorkList.add(reg);
			else
				simplifyWorkList.add(reg);
		}
		initial.clear();
	}
	
	private HashSet<RvRegister> adjacent(RvRegister reg) { //nodes adjacent with reg but not simplified or coalesced.
		HashSet<RvRegister> tmp = new HashSet<RvRegister>(coalescedNodes);
		tmp.addAll(selectStack);
		HashSet<RvRegister> res = new HashSet<RvRegister>(reg.getAdjList());
		res.removeAll(tmp);
		return res;
	}
	
	private void simplify() {
		RvRegister reg = simplifyWorkList.iterator().next();
		simplifyWorkList.remove(reg);
		selectStack.push(reg);
		HashSet<RvRegister> adjs = adjacent(reg);
		for (RvRegister adj : adjs) {
			decreaseDegree(adj);
		}
	}

	private void decreaseDegree(RvRegister reg) {
		reg.decreaseDegree();
		if (reg.getDegree() == K - 1) {
			HashSet<RvRegister> tmp = adjacent(reg);
			tmp.add(reg);
			enableMoves(tmp);
			spillWorkList.remove(reg);
			if (moveRelated(reg))
				freezeWorkList.add(reg);
			else
				simplifyWorkList.add(reg);
		}
	}
	
	private void enableMoves(HashSet<RvRegister> regs) {
		for (RvRegister reg : regs) {
			HashSet<RvMove> moves = nodeMoves(reg);
			for (RvMove move : moves) {
				if (activeMoves.contains(move)) {
					 activeMoves.remove(move);
					 workListMoves.add(move);
				}
			}
		}
	}
	
	private void addWorkList(RvRegister reg) { 
		// a move may be non-move-related after coalescing and should be added to the simplifyWorkList.
		if (!isPreColored(reg) && !moveRelated(reg) && reg.getDegree() < K) {
			freezeWorkList.remove(reg);
			simplifyWorkList.add(reg);
		}
	}
	
	private boolean OK(RvRegister t, RvRegister u) {
		return t.getDegree() < K || isPreColored(t) || adjSet.contains(new Edge(t, u));
	}
	
	private boolean George(RvRegister u, RvRegister v) {
		//George: u and v can be coalesced if, for every neighbor t of v, either t already interferes with u or t is of insignificant degree.
		HashSet<RvRegister> adjs = adjacent(v);
		for (RvRegister t : adjs) {
			if (!OK(t, u))
				return false; 
		}
		return true;
	}
	
	private boolean Briggs(RvRegister u, RvRegister v) {
		//Briggs :u and v can be coalesced if the resulting node uv will have fewer than K neighbors of significant degree
		HashSet<RvRegister> regs = adjacent(u);
		regs.addAll(adjacent(v));
		int k = 0;
		for (RvRegister reg : regs) {
			if (reg.getDegree() >= K)
				++k;
		}
		return k < K;
	}
	
	private void coalesce() {
		RvMove move = workListMoves.iterator().next();
		RvRegister u = getAlias(move.getRd()), v = getAlias(move.getRs());
		if (isPreColored(v)) {
			//swap u and v
			RvRegister tmp = v;
			v = u;
			u = tmp;
		}
		workListMoves.remove(move);
		if (u.equals(v)) {
			coalescedMoves.add(move);
			addWorkList(u);
		}
		else if (isPreColored(v) || adjSet.contains(new Edge(u, v))) {
			//both u and v are preColored 
			//or 
			//u and v are interfere.
			constrainedMoves.add(move);
			addWorkList(u);
			addWorkList(v);
		}
		else if ((isPreColored(u) && George(u, v)) // if u preColored, use George to check. 
			  || (!isPreColored(u) && Briggs(u, v))) // if u not preColored, use Briggs to check.
		{
			coalescedMoves.add(move);
			combine(u, v);
			addWorkList(u);
		}
		else 
			activeMoves.add(move);
	}
	
	private RvRegister getAlias(RvRegister reg) {
		return coalescedNodes.contains(reg) ? getAlias(reg.getAlias()) : reg;
	}
	
	private void combine(RvRegister u, RvRegister v) {
		if (freezeWorkList.contains(v)) // low-degree and move-related.
			freezeWorkList.remove(v);
		else                            // high-degree and move-related.
			spillWorkList.remove(v);
		coalescedNodes.add(v);
		v.setAlias(u);
		u.getMoveList().addAll(v.getMoveList());
		enableMoves(v);
		HashSet<RvRegister> adjs = adjacent(v);
		for (RvRegister t : adjs) {
			addEdge(t, u);
			t.decreaseDegree();
		}
		if (u.getDegree() >= K && freezeWorkList.contains(u)) {
			freezeWorkList.remove(u);
			spillWorkList.add(u);
		}
	}
	
	private void enableMoves(RvRegister reg) {
		HashSet<RvMove> moves = nodeMoves(reg);
		for (RvMove move : moves) {
			if (activeMoves.contains(move)) {
				 activeMoves.remove(move);					 
				 workListMoves.add(move);
			}
		}
	}
	
	private void freezeMoves(RvRegister u) {
		HashSet<RvMove> moves = nodeMoves(u);
		for (RvMove move : moves) {
			RvRegister x = move.getRd(), y = move.getRs();
			RvRegister xAlias = getAlias(x), yAlias = getAlias(y), uAlias = getAlias(u); 
			RvRegister v = yAlias.equals(uAlias) ? xAlias : yAlias;
			activeMoves.remove(move);
			frozenMoves.add(move);
			if (freezeWorkList.contains(v) || nodeMoves(v).isEmpty()) {
				freezeWorkList.remove(v);
				simplifyWorkList.add(v);
			}
		}
	}
	
	private void freeze() {
		RvRegister u = freezeWorkList.iterator().next();
		freezeWorkList.remove(u);
		simplifyWorkList.add(u);
		freezeMoves(u);
	}
	
	private void selectSpill() {
		RvRegister res = null;
		for (RvRegister reg : spillWorkList) {
			if (res == null || reg.getSpillCost() < res.getSpillCost()) 
				res = reg;
		}
		
		spillWorkList.remove(res);
		simplifyWorkList.add(res);
		freezeMoves(res);	
	}
	
	private void assignColors() {
		while (!selectStack.isEmpty()) {
			RvRegister u = selectStack.pop();
			HashSet<RvPhysicalRegister> colors = new HashSet<RvPhysicalRegister>(RegisterTable.allocableSet);
			LinkedHashSet<RvRegister> adjs = u.getAdjList();
			for (RvRegister v : adjs) {
				if (isPreColored(v) || coloredNodes.contains(v)) {
					colors.remove(getAlias(v).getColor());
				}
			}
			
			if(colors.isEmpty())
				spilledNodes.add(u);
			else {
				u.setColor(colors.iterator().next());
				coloredNodes.add(u);
			}
		}
		
		for (RvRegister u : coalescedNodes) {
			u.setColor(getAlias(u).getColor());
		}
	}
}
