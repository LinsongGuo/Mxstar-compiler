package optimize;

import java.util.ArrayList;
import java.util.Collection;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.CallInst;
import IR.Inst.IRInst;
import IR.Inst.LoadInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;

public class PeepholeOptimization extends PASS {
	
	private boolean changed;
	private AliasAnalysis aa;
	
	public PeepholeOptimization(IRModule module, AliasAnalysis aa) {
		super(module);
		this.aa = aa;
	}

	public boolean run() {
		changed = false;
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			run(function);
		}	
		return changed;
	}
	
	private IRInst getPrev(IRInst inst) {
		if (inst.getPrev() == null) {
			IRBasicBlock currentBlock = inst.getCurrentBlock();
			IRBasicBlock idom = currentBlock.getIdom();
			if (/*currentBlock.getPredecessors().contains(idom) && */currentBlock.getPredecessors().size() == 1) {
				return currentBlock.getPredecessors().get(0).getTail();
			}
			else 
				return null;
		}	
		else 
			return inst.getPrev();
	}
		
	private void run(IRFunction function) {
		for (IRBasicBlock block = function.getEntranceBlock(); block != null; block = block.getNext()) {
			ArrayList<IRInst> instList = block.getInstList();
			for (IRInst inst : instList) {
				if (inst instanceof LoadInst) {
				//	System.err.println("peep " + inst);
					IRRegister ptr = (IRRegister) ((LoadInst) inst).getPtr();
					IRRegister loadRes = inst.getRes();
					IRInst prev = getPrev(inst);
					for (int i = 0; i < 10 && prev != null; ++i) {
						///System.err.println("prev " + prev);
						if (prev instanceof StoreInst) {
							IRRegister storePtr = (IRRegister) ((StoreInst) prev).getPtr();
							if (storePtr == ptr) {
								IRSymbol storeVal = ((StoreInst) prev).getValue();
								for (IRInst use : loadRes.getUseList()) {
									use.replaceUse(loadRes, storeVal);
								}
								changed = true;
								inst.removeItself();
								break;
							}
							else if (aa.mayAlias(storePtr, ptr)) {
								break;
							}
						}
						else if (prev instanceof CallInst)
							break;
						
						prev = getPrev(prev);
					}
				}
			}
		}
	}
}
