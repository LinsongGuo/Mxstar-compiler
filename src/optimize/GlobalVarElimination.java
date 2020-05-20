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
			//	System.err.println("for " + var + " " + current.getName());
				if (function == null)
					function = current;
				else if (function != current) {
					flag = false;
					break;
				}	
			}
			
			if (flag) {
				if (function == null) {
					//System.err.println("remove1 " + var);
					removeList.add(var);
				}
				else if (function.getName().equals("@main")){
					//System.err.println("remove2 " + var + " " + function.getName());
					removeList.add(var);
					IRRegister res = new IRRegister(var.getType(), var.getName() + "$");
					IRBasicBlock entranceBlock = function.getEntranceBlock();
					function.addRegister(res);
					IRSymbol init = var.getInit();
					if (init != null && !(init instanceof IRNull)) {
						assert init instanceof IRConstInt;
						StoreInst store = new StoreInst(init, res);
						entranceBlock.addInstInHead(store);
					}
					AllocaInst alloca = new AllocaInst(res, ((IRPtrType) res.getType()).getType());
					entranceBlock.addInstInHead(alloca);
					
					for (IRInst use : uses) {
						use.replaceUse(var, res);
					}	
				}
			}
		}

		for (IRGlobalVariable var : removeList) {
			module.removeGlobalVar(var);
		}
	}
}
