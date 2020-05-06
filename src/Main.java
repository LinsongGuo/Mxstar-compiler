import org.antlr.v4.runtime.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Scope.*;
import parser.*;
import AST.*;
import utility.*;
import SemanticChecker.*;
import codegen.InstructionSelection;
import codegen.LivenessAnalysis;
import codegen.RegisterAllocation;
import codegen.RvPrinter;
import optimize.CFGSimplifier;
import optimize.DCE;
import optimize.DominatorTree;
import optimize.SCCP;
import optimize.SSAConstructor;
import optimize.SSADestructor;
import IR.*;
import Riscv.RvModule;
import Riscv.Operand.RegisterTable;

public class Main {
	public static void main(String[] args) throws IOException {
		ErrorReminder errorReminder = new ErrorReminder();
	//	InputStream IS = System.in;
		InputStream IS = new FileInputStream("code.txt");
		CharStream AIS = CharStreams.fromStream(IS);
      	
		MxstarLexer lexer = new MxstarLexer(AIS);
		lexer.removeErrorListeners();
		lexer.addErrorListener(new MxstarErrorListener(errorReminder));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MxstarParser parser = new MxstarParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(new MxstarErrorListener(errorReminder));
		
		ASTBuilder ast = new ASTBuilder(errorReminder);
		ProgramNode root = (ProgramNode) ast.visit(parser.program());
		SemanticChecker checker = new SemanticChecker(errorReminder);
		checker.visit(root);
		
		int count = errorReminder.count();
		if(args[0].equals("0")) {
			System.exit(count);			
		}
		
		GlobalScope globalScope = checker.getGlobalScope();
		StringType stringTemplate = checker.getStringTemplate();
		IRBuilder ir = new IRBuilder(globalScope, stringTemplate, errorReminder);
		ir.visit(root);
		
		IRModule irModule = ir.getModule(); 
		
		//System.err.println("optimizing------------------");
		new CFGSimplifier(irModule); 
		new DominatorTree(irModule);
		new SSAConstructor(irModule);
		new DCE(irModule);
		new SCCP(irModule);
		new CFGSimplifier(irModule); 
		
		new SSADestructor(irModule);
		
		//IRPrinter irPrinter = new IRPrinter();
		//irPrinter.visit(irModule);
		
		InstructionSelection selector = new InstructionSelection(irModule);
		RvModule rvModule = selector.run();
		
		//RvPrinter pseudoPrinter = new RvPrinter("test/pseudo.s", true);
		//pseudoPrinter.visit(rvModule);
		
		RegisterAllocation allocator = new RegisterAllocation(rvModule); 
		allocator.run();
		
		//RvPrinter rvPrinter = new RvPrinter("test/test.s", true);
		//rvPrinter.visit(rvModule);
	
		RvPrinter output = new RvPrinter("", false);
		output.visit(rvModule);
	}
}
