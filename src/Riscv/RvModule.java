package Riscv;

public class RvModule {
	public void accept(RvVisitor visitor) {
		visitor.visit(this);
	}
}
