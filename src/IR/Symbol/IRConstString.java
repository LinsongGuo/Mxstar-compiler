package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRInt8Type;
import IR.Type.IRPtrType;

public class IRConstString extends IRSymbol {
	private String value;
	
	public IRConstString(String value) {
		super(new IRPtrType(new IRInt8Type()));
		value = value.replace("\\", "\\5C");
        value = value.replace("\n","\\0A");
        value = value.replace("\0", "\\00");
        value = value.replace("\t","\\09");
        value = value.replace("\"", "\\22");
        value = "c\"" + value + "\"";
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}
}
