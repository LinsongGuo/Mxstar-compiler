package codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Stack;

import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvModule;
import Riscv.Inst.RvInst;
import Riscv.Inst.RvLoad;
import Riscv.Inst.RvMove;
import Riscv.Inst.RvStore;
import Riscv.Operand.RegisterTable;
import Riscv.Operand.RvPhysicalRegister;
import Riscv.Operand.RvRegister;
import Riscv.Operand.RvStackSlot;

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
	private int K;
	private static int INF = 0x3f3f3f3f;
	
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
	
	public RegisterAllocation(RvModule module) {
		this.module = module;
		this.K = RegisterTable.allocableNumber;
		//System.err.println("K" + K);
	}
	
	public void run() {
		ArrayList<RvFunction> functions = module.getFunctions();
		for (RvFunction function : functions) {
			while (true) {
			//	System.err.println("true");
			//	cnt++;
			//for (int i = 1; i <= 1; ++i) {
				//System.err.println("for----------------- " + function.getName());
				init(function);
				new LivenessAnalysis(function);
				build(function);
				makeWorkList();
				while(!simplifyWorkList.isEmpty() ||
					  !workListMoves.isEmpty()    ||
					  !freezeWorkList.isEmpty()   ||
					  !spillWorkList.isEmpty()) 
				{	
					//System.err.println("simplifyWorkList " + simplifyWorkList);
					if (!simplifyWorkList.isEmpty()) simplify();
					//System.err.println("simplifyWorkList after simplify " + simplifyWorkList);
					else if (!workListMoves.isEmpty()) coalesce();	
					//System.err.println("simplifyWorkList after coalesce " + simplifyWorkList);
					else if (!freezeWorkList.isEmpty()) freeze();
					//System.err.println("simplifyWorkList after freeze " + simplifyWorkList);
					else if (!spillWorkList.isEmpty()) selectSpill();
					//System.err.println("simplifyWorkList after select " + simplifyWorkList);
					/*
					System.err.println("simplifyWorkList " + simplifyWorkList);
					System.err.println("workListMoves " + workListMoves);
					System.err.println("freezeWorkList " + freezeWorkList);
					System.err.println("spillWorkList " + spillWorkList);
					*/
				}
				assignColors();
				if (spilledNodes.isEmpty())
					break;
				else 
					rewriteProgram(function);
			}
			//System.err.println(cnt);
			removeRedundantMove(function);
			stackSlotAllocation(function);
		}
	}
	
	public void init(RvFunction function) {
		//preColored = new HashSet<RvRegister>();
		initial = new HashSet<RvRegister>();// all virtual registers
		simplifyWorkList = new LinkedHashSet<RvRegister>(); // low-degree, non-move-related.
		freezeWorkList = new LinkedHashSet<RvRegister>(); // low-degree, move-related.
		spillWorkList = new LinkedHashSet<RvRegister>(); // hign-degree.
		spilledNodes = new HashSet<RvRegister>(); // marked for spilling.
		coalescedNodes = new HashSet<RvRegister>(); // if u<--v coalesced, v is added. 
		coloredNodes = new HashSet<RvRegister>(); // has been colored.
		selectStack = new Stack<RvRegister>(); // nodes removed from graph and will be colored.
		
		coalescedMoves = new HashSet<RvMove>(); //moves that have been coalesced.
		constrainedMoves = new HashSet<RvMove>(); //moves whose source and target interfere.
		frozenMoves = new HashSet<RvMove>(); //moves that will no longer be considered for coalescing.
		workListMoves = new LinkedHashSet<RvMove>(); //moves enabled for possible coalescing.
		activeMoves = new HashSet<RvMove>(); //moves not yet ready for coalescing.
		
		adjSet = new HashSet<Edge>();
		
		ArrayList<RvRegister> regs = function.getRegList();
		for (RvRegister reg : regs) {
			reg.clearColor();
			if (!reg.isSpilled()) {
				initial.add(reg);
		///		System.err.println("init " + reg);
			}
		}
		
		for (int i = 0; i < 32; ++i) {
			RegisterTable.registers[i].setDegree(INF);
		}
	}
	
	private void addEdge(RvRegister u, RvRegister v) {
		if (!u.equals(v) && !adjSet.contains(new Edge(u, v))) {
		//	if (u.getName().contains("calleeSaved_6") || v.getName().contains("calleeSaved_6")) {
		//		System.err.println("add " + u + " " + v);
		//	}
			adjSet.add(new Edge(u, v));
			adjSet.add(new Edge(v, u));
			if (!isPreColored(u)) {
				u.addAdj(v);
				u.increaseDegree();
				//System.err.println("edge " + u + " " + v);
				
			}
			if (!isPreColored(v)) {
				v.addAdj(u);
				v.increaseDegree();
				//System.err.println("edge " + v + " " + u);
				
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
				
				live.add(RegisterTable.zero);
			/*
				 although zero isn't a allocable register, but may be coalesced with zero, such as the following case.  
				 addi    tmp_25,zero,1
				 mv      returnValue.1_24,tmp_25
			*/
				//	System.err.println("def " + inst + " " + def);
				//	System.err.println(live);
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
	
	private void makeWorkList() {
		for (RvRegister reg : initial) {
		//	System.err.println("initial " + reg + " " + reg.getDegree() + " " + reg.getAdjList());
			if (reg.getDegree() >= K) 	
				spillWorkList.add(reg);
			else if (moveRelated(reg))
				freezeWorkList.add(reg);
			else
				simplifyWorkList.add(reg);
		}
		initial.clear();
	}
	
	private boolean isPreColored(RvRegister reg) {
		return (reg instanceof RvPhysicalRegister);
	}
	
	private HashSet<RvMove> nodeMoves(RvRegister reg) { 
		//moves that related to reg and may be coalesced in the future.
		HashSet<RvMove> res = new HashSet<>(activeMoves);
		res.addAll(workListMoves);
		res.retainAll(reg.getMoveList());
		return res;
	}
	
	private boolean moveRelated(RvRegister reg) {
		// Is reg in moves that may be coalesced in the future?
		return !nodeMoves(reg).isEmpty();
	}
	
	private HashSet<RvRegister> adjacent(RvRegister reg) { 
		//nodes adjacent with reg but not simplified or coalesced.
		HashSet<RvRegister> res = new HashSet<RvRegister>(reg.getAdjList());
		res.removeAll(selectStack);
		res.removeAll(coalescedNodes);
		return res;
	}
	
	private void simplify() {
		RvRegister reg = simplifyWorkList.iterator().next();
		simplifyWorkList.remove(reg);
		selectStack.push(reg);
	//	System.err.println("selectStack push " + reg);
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
		//System.err.println("OK " + u + " " + t + " " + t.getDegree());
		return t.getDegree() < K || isPreColored(t) || adjSet.contains(new Edge(t, u));
	}
	
	private boolean George(RvRegister u, RvRegister v) {
		//George: u and v can be coalesced if, for every neighbor t of v, either t already interferes with u or t is of insignificant degree.
		HashSet<RvRegister> adjs = adjacent(v);
	//	if(u.getName().contains("calleeSaved_0") || v.getName().contains("calleeSaved_0"))
	//		System.err.println("george " + u + " " + v + " " + adjs.size() + " " + adjs);	
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
		//if(u.getName().contains("calleeSaved_0") || v.getName().contains("calleeSaved_0"))
	//		System.err.println("coalesce " + u + " " + v);
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
		/*if (u.getName().contains("returnValue") 
				|| v.getName().contains("returnValue")
				|| u.getName().contains("tmp_25")
				|| v.getName().contains("tmp_25")
			)
			System.err.println("combine " + u + " " + v);
		*/
		if (freezeWorkList.contains(v)) // low-degree and move-related.
			freezeWorkList.remove(v);
		else                            // high-degree and move-related.
			spillWorkList.remove(v);
		coalescedNodes.add(v);
		v.setAlias(u);
		u.getMoveList().addAll(v.getMoveList());
		
		HashSet<RvRegister> nodes = new HashSet<RvRegister>();
		nodes.add(v);
		enableMoves(nodes);
		
		HashSet<RvRegister> adjs = adjacent(v);
		for (RvRegister t : adjs) {
			addEdge(t, u);
			decreaseDegree(t);
		}
		if (u.getDegree() >= K && freezeWorkList.contains(u)) {
			freezeWorkList.remove(u);
			spillWorkList.add(u);
		}
	}
	/*
	private void enableMoves(RvRegister reg) {
		HashSet<RvMove> moves = nodeMoves(reg);
		for (RvMove move : moves) {
			if (activeMoves.contains(move)) {
				 activeMoves.remove(move);					 
				 workListMoves.add(move);
			}
		}
	}
	*/
	private void freezeMoves(RvRegister u) {
		HashSet<RvMove> moves = nodeMoves(u);
		for (RvMove move : moves) {
			RvRegister x = move.getRd(), y = move.getRs();
			RvRegister xAlias = getAlias(x), yAlias = getAlias(y), uAlias = getAlias(u); 
			RvRegister v = yAlias.equals(uAlias) ? xAlias : yAlias;
			activeMoves.remove(move);
			frozenMoves.add(move);
			if (freezeWorkList.contains(v) && nodeMoves(v).isEmpty()) {
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
		//	System.err.println("select " + reg + " " + reg.getName().contains("calleeSaved") + " "+ getAlias(reg));
			if (res == null 
			//|| RegisterTable.calleeSavedSet.contains(getAlias(reg)) //let s0, s1.... spill  
			//|| (!reg.getName().contains("calleeSaved") && res.getName().contains("calleeSaved"))
			|| reg.getSpillCost() < res.getSpillCost()) 
			{
				res = reg;		
			}
		}
		//System.err.println("selected " + res);
		spillWorkList.remove(res);
	//	System.err.println("spillworklist " + res + " " + spillWorkList);
		simplifyWorkList.add(res);
		freezeMoves(res);	
	}
	
	private void assignColors() {
		while (!selectStack.isEmpty()) {
			RvRegister u = selectStack.pop();
	//		System.err.println("selectStack " + u);
			HashSet<RvPhysicalRegister> colors = new HashSet<RvPhysicalRegister>(RegisterTable.allocableSet);
			LinkedHashSet<RvRegister> adjs = u.getAdjList();
			for (RvRegister v : adjs) {
				RvRegister vAlias = getAlias(v);
				if (isPreColored(vAlias) || coloredNodes.contains(vAlias)) {
					//System.err.println("color " +  vAlias + " " +  vAlias.getColor());
					colors.remove(vAlias.getColor());
				}
			}
			
			if(colors.isEmpty()) {
	//			System.err.println("color empty " + u);
				spilledNodes.add(u);
			}
			else {
			//	System.err.println("assign " + u + " " + colors.iterator().next());
				u.setColor(colors.iterator().next());
				coloredNodes.add(u);
			}
		}
		
		for (RvRegister u : coalescedNodes) {
			u.setColor(getAlias(u).getColor());
		}
	}
	
	private void rewriteProgram(RvFunction function) {
		for (RvRegister reg : spilledNodes) {
			//System.err.println("spill " + reg);
			RvStackSlot slot = new RvStackSlot();
			function.addSpillStackSlot(slot);
			
			HashSet<RvInst> def = new HashSet<>(reg.getDef());
			for (RvInst inst : def) {
				RvRegister spill = function.newRegister("spill");
				inst.replaceDef(reg,  spill);
				RvBlock block = inst.getCurrentBlock();
				block.insertNext(inst, new RvStore(block, spill, slot));
			}
			
			HashSet<RvInst> use = new HashSet<>(reg.getUse());
			for (RvInst inst : use) {
				RvRegister spill = function.newRegister("spill");
				inst.replaceUse(reg, spill);
				RvBlock block = inst.getCurrentBlock();
				block.insertPrev(inst, new RvLoad(block, spill, slot));
			}
			
			reg.setSpilled();
			//System.err.println("set spilled " + reg + " " + reg.isSpilled());
		}
	}
	
	private void removeRedundantMove(RvFunction function) {
		ArrayList<RvBlock> blocks = function.getBlockList();
		for (RvBlock block : blocks) {
			ArrayList<RvMove> moves = block.getAllMoves();
			for (RvMove move : moves) {
				if (move.getRd().getColor() == move.getRs().getColor()) {
					block.removeInst(move);
					move.removeUseAndDef();
				}
			}
		}
	}
	
	private void stackSlotAllocation(RvFunction function) {
		int size = function.stackSlotAllocation();
		ArrayList<RvBlock> blocks = function.getBlockList();
		int i = 1;
		for (RvBlock block : blocks) {
			for (RvInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
				//System.err.println("inst " + inst);
				if (inst instanceof RvLoad) {
					RvStackSlot slot = ((RvLoad) inst).getStackSlot();
					if (slot != null) {
						if (slot.call() == 1) {
							slot.setIndex((size - i) << 2);
							++i;
						}
						else if (slot.call() == 2) {
							slot.addIndex(size);
						}
					}
				}
				if (inst instanceof RvStore) {
					RvStackSlot slot = ((RvStore) inst).getStackSlot();
					if (slot != null) {
						if (slot.call() == 1) {
							slot.setIndex((size - i) << 2);
							++i;
						}
						else if (slot.call() == 2) {
							slot.addIndex(size);
						}
					}
				}
			}
		}
	}
}
