import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import parser.*;
import AST.*;
import utility.*;

public class Main {
	public static void main(String[] args) throws IOException {
		ErrorReminder errorReminder = new ErrorReminder();
		InputStream IS = new FileInputStream("code.Mx");
		CharStream AIS = CharStreams.fromStream(IS);
      	//ANTLRInputStream AIS = new ANTLRInputStream(IS);
		
		System.err.println("lexer------------------");
		MxstarLexer lexer = new MxstarLexer(AIS);
		lexer.removeErrorListeners();
		lexer.addErrorListener(new MxstarErrorListener(errorReminder));
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		/*
		System.out.println("Get tokens.");
		tokens.fill();
		for(Token token : tokens.getTokens()) {
			System.out.println(token.getType() + " " + token.getText());
		}*/
		
		System.err.println("parser------------------");
		MxstarParser parser = new MxstarParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new MxstarErrorListener(errorReminder));
		
		System.err.println("Building AST------------");
		ASTBuilder ast = new ASTBuilder(errorReminder);
		ProgramNode root = (ProgramNode) ast.visit(parser.program());
		
		System.err.println("Finished.");
	}
}
