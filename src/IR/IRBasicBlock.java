package IR;

public class IRBasicBlock {
	private String name;
	
	public IRBasicBlock(String name) {
		this.name = name;
	}
	
	public String getName() {
		return "%" + name;
	}
}
