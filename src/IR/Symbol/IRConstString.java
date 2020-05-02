package IR.Symbol;

import IR.IRVisitor;
import IR.Type.IRInt8Type;
import IR.Type.IRPtrType;

public class IRConstString extends IRConst {
	private String value;
	
	public IRConstString(String value) {
		super(new IRPtrType(new IRInt8Type()));
		this.value = value;
	}

	@Override
	public String toString() {
		String tmp = value;
		tmp = tmp.replace("\\", "\\5C");
        tmp = tmp.replace("\n","\\0A");
        tmp = tmp.replace("\0", "\\00");
        tmp = tmp.replace("\t","\\09");
        tmp = tmp.replace("\"", "\\22");
        tmp = "c\"" + tmp + "\"";
		return tmp;
	}

	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);	
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public boolean valueEquals(IRConst other) {
		return (other instanceof IRConstString) && value.equals(((IRConstString) other).getValue());
	}
}
