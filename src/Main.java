import org.antlr.v4.runtime.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Scope.*;
import parser.*;
import AST.*;
import utility.*;
import SemanticChecker.*;
import optimize.CFGSimplifier;
import optimize.DominatorTree;
import optimize.SSAConstructor;
import IR.*;

public class Main {
	public static void main(String[] args) throws IOException {
		ErrorReminder errorReminder = new ErrorReminder();
		//InputStream IS = System.in;
		InputStream IS = new FileInputStream("test/test.txt");
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
		
		//System.err.println("Semantic checking--------");
		SemanticChecker checker = new SemanticChecker(errorReminder);
		checker.visit(root);
		
		int count = errorReminder.count();
		//System.out.println(count + " error(s) in total.");
		if (count > 0) {
			System.exit(count);
		}
		
		//System.err.println("Building IR--------------");
		GlobalScope globalScope = checker.getGlobalScope();
		StringType stringTemplate = checker.getStringTemplate();
		IRBuilder ir = new IRBuilder(globalScope, stringTemplate, errorReminder);
		ir.visit(root);
		
		IRModule module = ir.getModule(); 
		
		//System.err.println("optimizing------------------");
		new CFGSimplifier(module); 
		new DominatorTree(module);
		new SSAConstructor(module);
		
		//System.err.println("Printing IR--------------");
		IRPrinter printer = new IRPrinter();
		printer.visit(module);
	}
}
