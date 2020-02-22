//import org.antlr.runtime.ANTLRInputStream;
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
		InputStream IS = new FileInputStream("code.Mx");
		CharStream AIS = CharStreams.fromStream(IS);
       // ANTLRInputStream AIS = new ANTLRInputStream(IS);
		MxstarLexer lexer = new MxstarLexer(AIS);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		System.out.println("Get tokens.");
		/*
		tokens.fill();
		for(Token token : tokens.getTokens()) {
			System.out.println(token.getType() + " " + token.getText());
		}*/
		
		MxstarParser parser = new MxstarParser(tokens);
		ASTBuilder ast = new ASTBuilder();
		ProgramNode root = (ProgramNode) ast.visit(parser.program());
		System.out.println("Build AST.");
	}
}
