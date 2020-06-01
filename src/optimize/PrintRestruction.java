package optimize;

import java.util.ArrayList;
import java.util.Collection;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.CallInst;
import IR.Inst.IRInst;
import IR.Symbol.IRSymbol;

public class PrintRestruction extends PASS {

	public PrintRestruction(IRModule module) {
		super(module);
	}
	
	public void run() {
		IRFunction printlnInt = module.getBuiltInFunction("__printlnInt");
		//IRFunction println = module.getBuiltInFunction("__println");
		//IRFunction toString = module.getBuiltInFunction("__toString");
		
		Collection<IRFunction> functions = module.getFunctList().values();
		for (IRFunction function : functions) {
			for (IRBasicBlock block = function.getEntranceBlock(); block != null; block = block.getNext()) {
				ArrayList<IRInst> instList = block.getInstList();
				for (IRInst inst : instList) {
				//	System.err.println(inst);
					if (inst instanceof CallInst && ((CallInst) inst).getFunction().getName().equals("@__println")) {
						IRInst prev = inst.getPrev();
						IRSymbol instPara = ((CallInst) inst).getParameters().get(0);
						if (prev instanceof CallInst && ((CallInst) prev).getFunction().getName().equals("@__toString")) {
							IRInst next = inst.getNext();
							IRSymbol prevPara = ((CallInst) prev).getParameters().get(0);
							if (next != null && instPara == prev.getRes()) {
								CallInst newInst = new CallInst(printlnInt, prevPara);
								inst.removeItself();
								prev.removeItself();
								next.getCurrentBlock().addInstBefore(next, newInst);
							}
						}
					}
				}
			}
		}	
	}
}
