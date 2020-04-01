import org.antlr.v4.runtime.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Scope.*;
import parser.*;
import AST.*;
import utility.*;
import SemanticChecker.*;
import IR.*;

public class Main {
	public static void main(String[] args) throws IOException {
		ErrorReminder errorReminder = new ErrorReminder();
		//InputStream IS = System.in;
		InputStream IS = new FileInputStream("test.txt");
		CharStream AIS = CharStreams.fromStream(IS);
      	
		//System.err.println("lexer------------------");
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
		
		//System.err.println("parser------------------");
		MxstarParser parser = new MxstarParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new MxstarErrorListener(errorReminder));
		
		//System.err.println("Building AST------------");
		ASTBuilder ast = new ASTBuilder(errorReminder);
		ProgramNode root = (ProgramNode) ast.visit(parser.program());
		
		System.err.println("Semantic checking--------");
		SemanticChecker checker = new SemanticChecker(errorReminder);
		checker.visit(root);
		
		if (errorReminder.count() > 0)
			System.exit(errorReminder.count());
		
		System.err.println("Building IR--------------");
		GlobalScope globalScope = checker.getGlobalScope();
		StringType stringTemplate = checker.getStringTemplate();
		IRBuilder ir = new IRBuilder(globalScope, stringTemplate, errorReminder);
		ir.visit(root);
		
		System.err.println("Printing IR--------------");
		IRModule module = ir.getModule(); 
		IRPrinter printer = new IRPrinter();
		printer.visit(module);
	}
}
