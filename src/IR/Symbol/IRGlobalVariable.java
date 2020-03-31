package IR.Symbol;
import java.io.PrintWriter;

import IR.IRVisitor;
import IR.Type.IRType;

public class IRGlobalVariable extends IRRegister {
	
	public IRGlobalVariable(IRType type, String name) {
		super(type, name);
	}
	
	@Override
	public String toString() {
		return "@" + super.name;
	}
	
	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public String declarationString() {
		return "@" + name + " = global " + type.toString();
	}
}
