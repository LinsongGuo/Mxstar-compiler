package IR.Type;

import java.util.ArrayList;

import IR.IRVisitor;

public class IRClassType extends IRType {
	private String name;
	private ArrayList<IRType> memberList;

	public IRClassType(String name) {
		this.name = name;
		this.memberList = new ArrayList<IRType>();
	}
	
	public IRClassType(String name, ArrayList<IRType> memberList) {
		this.name = name;
		this.memberList = memberList;
	}
	
	@Override
	public String toString() {
		return "%class." + name;
	}
	
	@Override
	public void accept(IRVisitor visitor) {
		visitor.visit(this);
	}
	
	public String declarationString() {
		StringBuilder builder = new StringBuilder(toString());
		builder.append(" = type { ");
		for(int i = 0; i < memberList.size(); ++i) {
			builder.append(memberList.get(i).toString());
			if (i + 1 < memberList.size())
				builder.append(", ");
		}
		builder.append(" }");
		return builder.toString();
	}
	
	public void addMemberType(IRType type) {
		memberList.add(type);
	}
}
