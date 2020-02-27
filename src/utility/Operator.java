package utility;

public enum Operator {
	suffixINCR(new String("++")),
	suffixDECR(new String("--")),
	POS(new String("+")),
	NEG(new String("-")),
	prefixINCR(new String("++")),
	prefixDECR(new String("--")),
	logicalNOT(new String("!")),
	bitwiseNOT(new String("~")),
	MUL(new String("*")),
	DIV(new String("/")),
	MOD(new String("%")),
	ADD(new String("+")),
	SUB(new String("-")),
	leftSHIFT(new String("<<")),
	rightSHIFT(new String(">>")),
	LESS(new String("<")),
	GREATER(new String(">")),
	lessEQU(new String("<=")),
	greaterEQU(new String(">=")),
	EQU(new String("==")),
	notEQU(new String("!=")),
	bitwiseAND(new String("&")),
	bitwiseXOR(new String("^")),
	bitwiseOR(new String("|")),
	logicalAND(new String("&&")),
	logicalOR(new String("||")),
	ASSIGN(new String("=")),
	INVALID(new String(""));
	
	private String op;
	
	private Operator(String op) {
		this.op = op;
	}
	
	@Override
	public String toString() {
		return op;
	}
	
}
