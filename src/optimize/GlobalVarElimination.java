package optimize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import IR.IRBasicBlock;
import IR.IRFunction;
import IR.IRModule;
import IR.Inst.AllocaInst;
import IR.Inst.IRInst;
import IR.Inst.StoreInst;
import IR.Symbol.IRConstInt;
import IR.Symbol.IRGlobalVariable;
import IR.Symbol.IRNull;
import IR.Symbol.IRRegister;
import IR.Symbol.IRSymbol;
import IR.Type.IRPtrType;

public class GlobalVarElimination extends PASS {

	public GlobalVarElimination(IRModule module) {
		super(module);
	}
	
	public void run() {
		ArrayList<IRGlobalVariable> removeList = new ArrayList<>();
		Collection<IRGlobalVariable> vars = module.getGlobalVars().values();
		for (IRGlobalVariable var : vars) {
			HashSet<IRInst> uses = var.getUseList();
			IRFunction function = null;
			boolean flag = true;
			for (IRInst use : uses) {
				IRFunction current = use.getCurrentBlock().getCurrentFunction();
			//	System.err.println(var + " " + use + " " + current.getName());
				if (function == null)
					function = current;
				else if (function != current) {
					flag = false;
					break;
				}	
			}
			
			if (flag) {
				removeList.add(var);
				//System.err.println(var.getName());
				IRRegister res = new IRRegister(var.getType(), var.getName() + "$");
				IRBasicBlock entranceBlock = function.getEntranceBlock();
				function.addRegister(res);
				IRSymbol init = var.getInit();
				if (init != null && !(init instanceof IRNull)) {
					//System.err.println("chushi " + init);
					assert init instanceof IRConstInt;
					StoreInst store = new StoreInst(init, res);
					entranceBlock.addInstInHead(store);
				}
				AllocaInst alloca = new AllocaInst(res, ((IRPtrType) res.getType()).getType());
				entranceBlock.addInstInHead(alloca);
				
				for (IRInst use : uses) {
					//System.err.println("*** " + use);
					use.replaceUse(var, res);
				}
			}
		}

		for (IRGlobalVariable var : removeList) {
			module.removeGlobalVar(var);
		}
	}
}
