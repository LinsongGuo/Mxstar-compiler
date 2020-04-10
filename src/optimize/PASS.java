package optimize;

import IR.IRModule;

public abstract class PASS {
    protected IRModule module;
  
    public PASS(IRModule module) {
        this.module = module;
    }
}
