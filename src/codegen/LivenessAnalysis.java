package codegen;

import java.util.ArrayList;
import java.util.HashSet;


import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.Inst.RvInst;
import Riscv.Operand.RvRegister;

public class LivenessAnalysis {
	public LivenessAnalysis(RvFunction function) {
		ArrayList<RvBlock> blocks = function.getDfsOrder();
		for (RvBlock block : blocks) {
			block.clearDefAndUse();
			block.clearLive();
			for (RvInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
				HashSet<RvRegister> def = inst.getDef();
				HashSet<RvRegister> use = new HashSet<>(inst.getUse());
			//	System.err.println(inst + " " + def + " " + use);
				use.removeAll(block.getDef());
				block.addUseNotDef(use);
				block.addDef(def);
			}
		}
		
		/*
		for (RvBlock block : blocks) {
			System.err.println("use not def " + block.getName());
			for (RvRegister reg : block.getUseNotDef()) {
				System.err.println(reg + " ");
			}
			
		}*/
		
		boolean flag = true;
		while (flag) {
			flag = false;
			for (int i = blocks.size() - 1; i >= 0; --i) {
				RvBlock block = blocks.get(i);
				HashSet<RvRegister> liveIn = block.getLiveIn();
				HashSet<RvRegister> liveOut = block.getLiveOut();
				HashSet<RvRegister> newLiveIn = new HashSet<RvRegister>();
				HashSet<RvRegister> newLiveOut = new HashSet<RvRegister>();
				newLiveIn.addAll(liveOut);
				newLiveIn.removeAll(block.getDef());
				newLiveIn.addAll(block.getUseNotDef());
				for (RvBlock successor : block.getSuccessors()) {
					newLiveOut.addAll(successor.getLiveIn());
				}
				if (!liveIn.equals(newLiveIn)) {
					block.setLiveIn(newLiveIn);
					flag = true;
				}
				if (!liveOut.equals(newLiveOut)) {
					block.setLiveOut(newLiveOut);
					flag = true;
				}
			}
		}
		
		/*
		for (RvBlock block : blocks) {
			System.err.println("liveIn " + block.getName());
			for (RvRegister reg : block.getLiveIn()) {
				System.err.println(reg + " ");
			}
		}
		*/
	}
	
}
