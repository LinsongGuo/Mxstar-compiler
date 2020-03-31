package IR;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import IR.Inst.*;
import IR.Symbol.*;
import IR.Type.*;
import Scope.VarSymbol;

public class IRPrinter implements IRVisitor {
	private PrintWriter printer;
	private String tab = new String("    ");
	
	public IRPrinter() throws FileNotFoundException {
		//printer = new PrintWriter(System.out);
		printer = new PrintWriter(new FileOutputStream("test.ll"));
	}
	
	@Override
	public void visit(IRModule node) {
		LinkedHashMap<String, IRClassType> classList = node.getClassList();
		for (Entry<String, IRClassType> entry : classList.entrySet()) {
			entry.getValue().accept(this);
		}
		
		LinkedHashMap<String, IRGlobalString> stringList = node.getStringList();
		for (Entry<String, IRGlobalString> entry : stringList.entrySet()) {
			entry.getValue().accept(this);
		}
		
		LinkedHashMap<String, IRGlobalVariable> globalVarList = node.getGlobalVarList();
		for (Entry<String, IRGlobalVariable> entry : globalVarList.entrySet()) {
			entry.getValue().accept(this);
		}
		
		LinkedHashMap<String, IRFunction> builtInFunctList = node.getBuiltInFunctList();
		for (Entry<String, IRFunction> entry : builtInFunctList.entrySet()) {
			printer.println(entry.getValue().declarationString());
		}
		
		LinkedHashMap<String, IRFunction> functList = node.getFunctList();
		for (Entry<String, IRFunction> entry : builtInFunctList.entrySet()) {
			entry.getValue().accept(this);
		}
	}
	
	@Override
	public void visit(IRClassType node) {
		printer.println(node.declarationString());
	}
	
	@Override
	public void visit(IRGlobalVariable node) {
		printer.println(node.declarationString());
	}

	@Override
	public void visit(IRGlobalString node) {
		printer.println(node.declarationString());
	}
	
	@Override
	public void visit(IRFunction node) {
		printer.println(node.declarationString() + " {");
		ArrayList<IRBasicBlock> blockList = node.getBlockList();
		for (IRBasicBlock block : blockList) {
			block.accept(this);
		}
		printer.println("}");
	}
	
	@Override
	public void visit(IRBasicBlock node) {
		printer.println(node.getName() + ":");
		for(IRInst inst = node.getHead(); inst != null; inst = inst.getNext()) {
			inst.accept(this);
		}
		printer.println("");
	}

	@Override
	public void visit(IRInst node) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void visit(AllocaInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(BinOpInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(BitcastToInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(BitwiseBinOpInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(BrInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(CallInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(GetElementPtrInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(IcmpInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(LoadInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(PhiInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(RetInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(StoreInst node) {
		printer.println(tab + node.toString());
	}

	@Override
	public void visit(IRType node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRIntType node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRInt1Type node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRInt8Type node) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void visit(IRInt32Type node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRVoidType node) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void visit(IRPtrType node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRArrayType node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRSymbol node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRRegister node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRConstInt node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRConstBool node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRConstString node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(IRNull node) {
		// TODO Auto-generated method stub
	}
}
