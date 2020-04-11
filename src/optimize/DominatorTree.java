package optimize;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;


import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import utility.Pair;

public class DominatorTree extends PASS {
	//Union Find Set
	private HashMap<IRBasicBlock, Pair<IRBasicBlock, IRBasicBlock>> unionFindSet;
	 
	public DominatorTree(IRModule module) throws FileNotFoundException {
		super(module);
		
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			LengauerTarjan(function);
			CalcDF(function);
		}
		
		print();
	}
	
	private Pair<IRBasicBlock, IRBasicBlock> find(IRBasicBlock block) {
		Pair<IRBasicBlock, IRBasicBlock> father = unionFindSet.get(block);
		if (block == father.first)
			return father;
		Pair<IRBasicBlock, IRBasicBlock> grandfather = find(father.first);
		father.first = grandfather.first;
		if (grandfather.second.getSdom().getDfn() < father.second.getSdom().getDfn()) {
			father.second = grandfather.second;
		}
		return father;
	}
	
	private void link(IRBasicBlock block, IRBasicBlock father) {
		unionFindSet.get(block).first = father;
	}
	
	//The Lengauer Tarjan algorithm to construct the dominator's tree.
	private void LengauerTarjan (IRFunction function) {
		unionFindSet = new HashMap<IRBasicBlock, Pair<IRBasicBlock, IRBasicBlock>>();
		ArrayList<IRBasicBlock> dfsSeq = function.dfsBasicBlock();
		for (IRBasicBlock block : dfsSeq) {
			unionFindSet.put(block, new Pair<IRBasicBlock, IRBasicBlock>(block, block));
			block.setSdom(block);
		}
		
		for (int i = dfsSeq.size() - 1; i > 0; --i) {
			IRBasicBlock block = dfsSeq.get(i);
			ArrayList<IRBasicBlock> predecessors = block.getPredecessors();
			for (IRBasicBlock predecessor : predecessors) {
				Pair<IRBasicBlock, IRBasicBlock> ancestor = find(predecessor);
				if (ancestor.second.getSdom().getDfn() < block.getSdom().getDfn()) {
					block.setSdom(ancestor.second.getSdom());
				}
			}
			block.getSdom().addSdom(block);
			
			IRBasicBlock father = block.getFather();
			link(block, father);
		
			HashSet<IRBasicBlock> fatherBucket = father.getBucket();
			for (IRBasicBlock bucket : fatherBucket) {
				Pair<IRBasicBlock, IRBasicBlock> ancestor = find(bucket);
				if (ancestor.second.getSdom().getDfn() < bucket.getSdom().getDfn()) {
					bucket.setIdom(ancestor.second);
				}
				else {
					bucket.setIdom(father);
				}
			}
			fatherBucket.clear();
		}
		
		for (int i = 1; i < dfsSeq.size(); ++i) {
			IRBasicBlock block = dfsSeq.get(i);
			IRBasicBlock idom = block.getIdom();
			if (idom != block.getSdom()) {
				idom = block.getIdom().getIdom();
				block.setIdom(idom);	
			}
			idom.addDominace(block);
			for (; idom != null; idom = idom.getIdom()) {
				block.addStrictDominator(idom);
			}
		}
	}
	
	//Calculate Dominance Frontier
    private void CalcDF(IRFunction function) {
        ArrayList<IRBasicBlock> blockList = function.getBlockList();
        for (IRBasicBlock block : blockList) {
        	HashSet<IRBasicBlock> strictDominators = block.getStrictDominators();
        	ArrayList<IRBasicBlock> predecessors = block.getPredecessors();
        	for (IRBasicBlock predecessor : predecessors) {
                IRBasicBlock upBlock = predecessor;
                while (upBlock != null && !strictDominators.contains(upBlock)) {
                    upBlock.addDF(block);
                    upBlock = upBlock.getIdom();
                }
            }
        }	
    }
    
    private void print() throws FileNotFoundException {
    	PrintWriter printer = new PrintWriter(new FileOutputStream("test/dominates.txt"));
    	Collection<IRFunction> functions = module.getFunctList().values();
    	for (IRFunction function : functions) {
    		 printer.println(function.getName());
        	 ArrayList<IRBasicBlock> blockList = function.getBlockList();
        	 for (IRBasicBlock block : blockList) {
        		 ArrayList<IRBasicBlock> dominaces = block.getDominaces();
        		 printer.print(block.toString() + " --> ");
        		 for (IRBasicBlock dominace : dominaces) {
        			 printer.print(dominace.toString() + "  ");
        		 }
        		 printer.println("");
        	 }
        	 printer.println("");
		}
    	printer.close();
    }
}
