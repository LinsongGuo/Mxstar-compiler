package parser;

import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.BaseErrorListener;
import utility.ErrorReminder;
import utility.Location;

public class MxstarErrorListener extends BaseErrorListener {
	private ErrorReminder errorReminder;
	
	public MxstarErrorListener(ErrorReminder errorReminder) {
		this.errorReminder = errorReminder;
	}
		
	@Override
	public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
		errorReminder.error( new Location(line, charPositionInLine), msg);	
	}
}
