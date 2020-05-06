package utility;

public class ReservedWord {
	private String[] list = {"int", "bool", "string", "null", "void", "true", "false", "if", "else", "for", "while", "break", "continue", "return", "new", "class", "this"};
	
	private boolean IsReservedWord(String identifier) {
		for (int i = 0; i < 17; ++i) {
			if (identifier.equals(list[i]));
				return true;
		}
		return false;
	}
}
