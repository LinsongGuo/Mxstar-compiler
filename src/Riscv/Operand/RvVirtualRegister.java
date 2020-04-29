package Riscv.Operand;

public class RvVirtualRegister extends RvRegister {
	private static int count = 0;
	
	public RvVirtualRegister(String name) {
		super(name + '_' + count);
		++count;
	}
	
}
