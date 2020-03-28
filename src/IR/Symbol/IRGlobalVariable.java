package IR.Symbol;
import IR.Type.IRType;

public class IRGlobalVariable extends IRRegister {
	private IRSymbol init;
	
	public IRGlobalVariable(IRType type, String name, IRSymbol init) {
		super(type, name);
		this.init = init;
	}
	
	@Override
	public String toString() {
		return "@" + super.name;
	}
	
	public IRSymbol getInit() {
		return init;
	}
}
