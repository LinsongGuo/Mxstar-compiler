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
import codegen.RegisterAllocation;
import codegen.RvPrinter;
import optimize.CFGSimplifier;
import optimize.DCE;
import optimize.DominatorTree;
import optimize.GlobalVarElimination;
import optimize.Inliner;
import optimize.SCCP;
import optimize.SSAConstructor;
import optimize.SSADestructor;
import IR.*;
import Riscv.RvModule;

public class Main {
	public static void main(String[] args) throws IOException {
		ErrorReminder errorReminder = new ErrorReminder();
		InputStream IS = System.in;
	//	InputStream IS = new FileInputStream("code.txt");
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
		
		//build IR
		GlobalScope globalScope = checker.getGlobalScope();
		StringType stringTemplate = checker.getStringTemplate();
		IRBuilder ir = new IRBuilder(globalScope, stringTemplate, errorReminder);
		ir.visit(root);
		IRModule irModule = ir.getModule(); 
		
		//optimize
		Inliner inliner = new Inliner(irModule);
		GlobalVarElimination gve = new GlobalVarElimination(irModule);
		CFGSimplifier cfg = new CFGSimplifier(irModule); 
		DominatorTree dom = new DominatorTree(irModule);
		SSAConstructor ssaConstructor = new SSAConstructor(irModule);
		DCE dce = new DCE(irModule);
		SCCP sccp = new SCCP(irModule);	
		
		inliner.run();
		gve.run();
		cfg.run();
		dom.run();
		ssaConstructor.run();
		
	//	IRPrinter irPrinter = new IRPrinter("test/test.ll");
	//	irPrinter.visit(irModule);
		
		boolean changed = true;
		while(changed) {
			///System.err.println("----------------while");
			changed = false;
			changed |= inliner.run();
			dom.run();
			changed |= dce.run();
			changed |= sccp.run();
			changed |= cfg.run();
		}

	//	IRPrinter irPrinter2 = new IRPrinter("test/test2.ll");
	//	irPrinter2.visit(irModule);
	
		//codegen
		SSADestructor ssaDestructor = new SSADestructor(irModule);
		ssaDestructor.run();	
		InstructionSelection selector = new InstructionSelection(irModule);
		RvModule rvModule = selector.run();
	//	RvPrinter pseudoPrinter = new RvPrinter("test/pseudo.s", true);
	//	pseudoPrinter.visit(rvModule);
		
		RegisterAllocation allocator = new RegisterAllocation(rvModule); 
		allocator.run();
		
	//	RvPrinter rvPrinter = new RvPrinter("test/test.s", true);
	//	rvPrinter.visit(rvModule);
	
		RvPrinter output = new RvPrinter("test.s", true);
		output.visit(rvModule);
	}
}
