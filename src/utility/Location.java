package utility;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Location {
	private int line, col;
	
	public Location(int line, int col) {
		this.line = line;
		this.col = col;
	}
	
	public Location(Token token) {
		this.line = token.getLine();
		this.col = token.getCharPositionInLine();
	}
	
	public Location(ParserRuleContext ctx) {
		this(ctx.start);
	}
	
	public int getLine() {
		return line;
	}
	
	public int getCol() {
		return col;
	}
	
	public String toString() {
        return "(" + line + "," + col + ")";
    }
}
