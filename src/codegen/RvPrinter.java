package codegen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import Riscv.RvBlock;
import Riscv.RvFunction;
import Riscv.RvModule;
import Riscv.RvVisitor;
import Riscv.Inst.RvCall;
import Riscv.Inst.RvCmpZ;
import Riscv.Inst.RvInst;
import Riscv.Inst.RvJ;
import Riscv.Inst.RvJr;
import Riscv.Inst.RvLi;
import Riscv.Inst.RvLoad;
import Riscv.Inst.RvLui;
import Riscv.Inst.RvMove;
import Riscv.Inst.RvStore;
import Riscv.Inst.RvTypeB;
import Riscv.Inst.RvTypeI;
import Riscv.Inst.RvTypeR;
import Riscv.Operand.RvGlobalString;
import Riscv.Operand.RvGlobalVariable;
import Riscv.Operand.RvOperand;

public class RvPrinter implements RvVisitor {
	private PrintWriter printer;
	
	public RvPrinter(String path, boolean file) throws FileNotFoundException {
		if (file)
			printer = new PrintWriter(new FileOutputStream(path));
		else 
			printer = new PrintWriter(System.out);
	}
	
	@Override
	public void visit(RvModule module) {
		ArrayList<RvGlobalString> globalStrings = module.getGlobalString();
		ArrayList<RvGlobalVariable> globalVariables = module.getGlobalVariables();
		
		if (!globalStrings.isEmpty() || !globalVariables.isEmpty()) {
			printer.println("\t.data\n");
			
			for (RvGlobalString globalString : globalStrings) {
				globalString.accept(this);
			}
			
			for (RvGlobalVariable globalVariable : globalVariables) {
				globalVariable.accept(this);
			}
			printer.println("");
		}
			
		printer.println("\t.text\n");
		ArrayList<RvFunction> functions = module.getFunctions();
		for (RvFunction function : functions) {
			function.accept(this);
		}
		
		printer.close();
	}

	@Override
	public void visit(RvGlobalString globalString) {
		printer.println("\t.globl  " + globalString);
		printer.println(globalString + ":");
		printer.println("\t.asciz  " + globalString.getStr() + "\n");
	}

	@Override
	public void visit(RvGlobalVariable globalVariable) {
		printer.println("\t.globl  " + globalVariable);
		printer.println("\t.p2align	2");
		printer.println(globalVariable + ":");
		RvOperand value = globalVariable.getValue();
		if (value != null)
			printer.println("\t.word   " + value + "\n");
		else
			printer.println("\t.word   0\n");		
	}

	@Override
	public void visit(RvFunction function) {
		printer.println("\t.globl  " + function.getName());
		printer.println("\t.p2align	2");
		printer.println("\t.type   " + function.getName() + ", @function");
		printer.println(function.getName() + ":");
		ArrayList<RvBlock> blocks = function.getBlockList();
		//System.err.println("blocks " + blocks);
		for (RvBlock block : blocks) {
			block.accept(this);
		}
		printer.println("");
	}

	@Override
	public void visit(RvBlock block) {
		if (!block.getName().contains("entranceBlock"))
			printer.println(block.getName() + ":");
		for (RvInst inst = block.getHead(); inst != null; inst = inst.getNext()) {
			inst.accept(this);
		}
		printer.println("");
	}

	@Override
	public void visit(RvInst inst) {
		
	}

	@Override
	public void visit(RvCall inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvJ inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvJr inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvLi inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvLoad inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvLui inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvMove inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvStore inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvTypeI inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvTypeR inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvTypeB inst) {
		printer.println(inst);
	}

	@Override
	public void visit(RvCmpZ inst) {
		printer.println(inst);
	}

}
