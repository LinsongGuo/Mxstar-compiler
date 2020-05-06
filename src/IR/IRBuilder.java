package IR;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import AST.*;
import IR.Symbol.*;
import IR.Type.*;
import Scope.*;
import IR.Inst.*;
import IR.Inst.BinOpInst.BinOpType;
import IR.Inst.BitwiseBinOpInst.BitwiseBinOpType;
import IR.Inst.IcmpInst.IcmpOpType;
import utility.ErrorReminder;
import utility.Operator;

public class IRBuilder implements ASTVisitor {
	private GlobalScope globalScope;
	private IRFunction currentFunction, mallocFunction;
	private IRBasicBlock currentBlock, currentReturnBlock; 
	private IRModule module;
	private IRBasicBlock afterLoop, nextLoop;
	private IRRegister returnAddress;
	private ClassSymbol currentClass;
	
	public IRBuilder(GlobalScope globalScope, StringType stringTemplate, ErrorReminder errorReminder) {
		this.globalScope = globalScope;
		this.currentFunction = null;
		this.currentReturnBlock = null;
		this.currentBlock = null;
		this.module = new IRModule(globalScope, stringTemplate);
		this.mallocFunction = module.getBuiltInFunct("malloc");
		this.afterLoop = this.nextLoop = null;
	}
	
	public IRModule getModule() {
		return module;
	}
	
	private IRType toIRType(TypeNode typeNode) {
		String name = typeNode.typeString();
		if (typeNode instanceof PrimTypeNode) {
			if (name.equals("int"))
				return new IRInt32Type();
			else if (name.equals("bool"))
				return new IRInt1Type();
			else if (name.equals("string"))
				return new IRPtrType(new IRInt8Type());
			else
				return new IRVoidType(); 
		}
		else if (typeNode instanceof ArrayTypeNode) {
			IRType res;
			if (name.equals("int"))
				res = new IRInt32Type();
			else if (name.equals("bool"))
				res = new IRInt1Type();
			else if (name.equals("string"))
				res = new IRPtrType(new IRInt8Type());
			else if (name.equals("void"))
				res = new IRVoidType();
			else 
				res = new IRPtrType(((ClassSymbol) globalScope.findClassSymbol(name)).toIRClass());
			for (int i = 0; i < ((ArrayTypeNode) typeNode).getDimension(); ++i) {
				res = new IRPtrType(res);
			}
			return res;
		}
		else  {
			//typeNode instanceof ClassTypeNode
			ClassSymbol classSymbol = (ClassSymbol) globalScope.resolveType(name);
			return new IRPtrType(classSymbol.toIRClass());
			//return classSymbol.toIRClass();
		}
	}
	
	@Override
	public void visit(ProgramNode node) {
		ArrayList<DefNode> defList = node.getDefList();
		//declare class
		for (DefNode defNode : defList) {
			if (defNode instanceof ClassDefNode) {
				ClassSymbol classSymbol = ((ClassDefNode) defNode).getClassSymbol();
				String identifier = "class." + ((ClassDefNode) defNode).getIdentifier();
				IRClassType IRClass = new IRClassType(identifier);
				classSymbol.setIRClass(IRClass);
				module.addClass(identifier, IRClass);
			}
		}
		
		//define class
		for (DefNode defNode: defList) {
			if (defNode instanceof ClassDefNode) {
				//define variable in class
				ClassSymbol classSymbol = ((ClassDefNode) defNode).getClassSymbol();
				IRClassType IRClass = classSymbol.toIRClass();
				ArrayList<VarDefListNode> varList = ((ClassDefNode) defNode).getVarList();
				for (VarDefListNode item : varList) {
					ArrayList<VarDefNode> varList2 = item.getVarList();
					for(VarDefNode item2: varList2) {
						VarSymbol varSymbol = item2.getVarSymbol();
						IRType type = toIRType(item2.getType());
						IRClass.addMemberType(type);
						varSymbol.setIRType(type);
						IRRegister address = new IRRegister(new IRPtrType(type), item2.getIdentifier());
						varSymbol.setAddress(address);
					}
				}
				//declare constructor in class
				FunctDefNode constructor = ((ClassDefNode) defNode).getConstructorDef();
				if (constructor != null) {
					IRType returnType = new IRVoidType();
					String identifier = ((ClassDefNode) defNode).getIdentifier() + "." + constructor.getIdentifier();
					IRFunction function = new IRFunction(returnType, identifier);
					FunctSymbol functSymbol = constructor.getFunctSymbol();
					functSymbol.setIRFunction(function);
					module.addFunct(identifier, function);	
				}
				//declare function in class
				ArrayList<FunctDefNode> functList = ((ClassDefNode) defNode).getFunctList();
				for (FunctDefNode functDef : functList) {
					IRType returnType = functDef.getType() == null ? new IRVoidType() : toIRType(functDef.getType());
					String identifier = ((ClassDefNode) defNode).getIdentifier() + "." + functDef.getIdentifier();
					IRFunction function = new IRFunction(returnType, identifier);
					FunctSymbol functSymbol = functDef.getFunctSymbol();
					functSymbol.setIRFunction(function);
					module.addFunct(identifier, function);
				}
			}
		}
		
		//declare function in global scope
		for (DefNode defNode : defList) {
			if (defNode instanceof FunctDefNode) {
				IRType returnType = toIRType(((FunctDefNode) defNode).getType());
				String identifier = ((FunctDefNode) defNode).getIdentifier();
				IRFunction function = new IRFunction(returnType, identifier);
				FunctSymbol functSymbol = ((FunctDefNode) defNode).getFunctSymbol();
				functSymbol.setIRFunction(function);
				module.addFunct(identifier, function);
			}
		}
		
		//define variable in global scope
		IRFunction initFunction = new IRFunction(new IRVoidType(), "__init__");
		module.addFunct("__init__", initFunction);
		currentFunction = initFunction;
		IRBasicBlock entranceBlock = new IRBasicBlock("entranceBlock");
		currentFunction.addBasicBlock(entranceBlock);
		currentBlock = entranceBlock;
		
		for (DefNode defNode : defList) {
			if (defNode instanceof VarDefListNode) {
				ArrayList<VarDefNode> varList = ((VarDefListNode) defNode).getVarList();
				for (VarDefNode var : varList) {
					VarSymbol varSymbol = var.getVarSymbol();
					IRType type = toIRType(var.getType());
					IRGlobalVariable globalVariable = new IRGlobalVariable(new IRPtrType(type), var.getIdentifier());
					varSymbol.setAddress(globalVariable);
					varSymbol.setIRType(type);
					module.addGlobalVariable(globalVariable);
					ExprNode init = var.getInitValue();
					//System.err.println(init);
					if (init != null) {
						init.accept(this);
						IRSymbol initRes = init.getResult();
						if (type instanceof IRInt32Type)  {
							if (initRes instanceof IRConstInt)
								globalVariable.setInit(initRes);	
							else {
								globalVariable.setInit(new IRConstInt(0));
								currentBlock.addInst(new StoreInst(initRes, globalVariable));
							}
						}
						else {
							//System.err.println(currentBlock.getTail());
							IRInst tail = currentBlock.getTail();
							if (tail instanceof GetElementPtrInst && tail.getRes().getName().indexOf("__stringLiteral") != -1) {
								globalVariable.setInit(((GetElementPtrInst) tail).getPtr());
								tail.setIgnored();
							}
							else 
								globalVariable.setInit(new IRNull());
							IRInst inst = new StoreInst(initRes, globalVariable);
							inst.setIgnored();
							currentBlock.addInst(inst);		
						}	
					}
					else {
						if(type instanceof IRInt32Type) 
							globalVariable.setInit(new IRConstInt(0));
						else 
							globalVariable.setInit(new IRNull());
					}
				}
			}
		}
		IRBasicBlock returnBlock = new IRBasicBlock("returnBlock");
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, returnBlock));
		currentFunction.addBasicBlock(returnBlock);
		currentFunction.setExitBlock(returnBlock);
		currentBlock = returnBlock;
		currentBlock.addInst(new RetInst());
		
		//define function in global and class scope 
		for (DefNode defNode : defList) {
			if (defNode instanceof ClassDefNode) {
				defNode.accept(this);
			}
			else if (defNode instanceof FunctDefNode) {
				defNode.accept(this);
			}
		}
	}

	@Override
	public void visit(ClassDefNode node) {
		ClassSymbol classSymbol = node.getClassSymbol();
		currentClass = classSymbol;
		ArrayList<FunctDefNode> functList = node.getFunctList();
		for (FunctDefNode item : functList) {
			item.accept(this);
		}
		FunctDefNode constructorDef = node.getConstructorDef();
		if (constructorDef != null)
			constructorDef.accept(this);
	}

	@Override
	public void visit(FunctDefNode node) {
		//System.err.println("IRBuilder::visit(FunctDefNode)");
		Scope parentScope = node.getScope().getEnclosingScope();
		if  (parentScope == globalScope) {
			//function in global scope.
			FunctSymbol functSymbol = node.getFunctSymbol();
			IRFunction function = functSymbol.toIRFunction();
			IRType returnType = function.getType();
			currentFunction = function;
			//entranceBlock
			IRBasicBlock entranceBlock = new IRBasicBlock("entranceBlock");
			IRBasicBlock returnBlock = new IRBasicBlock("returnBlock");
			currentReturnBlock = returnBlock;
			currentFunction.addBasicBlock(entranceBlock);
			currentBlock = entranceBlock;
			if (node.getIdentifier().equals("main")) {
				module.setMain(function);
				currentBlock.addInst(new CallInst(module.getFunct("__init__"), new ArrayList<IRSymbol>()));
			}
			//parameters
			ArrayList<VarDefNode> parameters = node.getParaList();
			LinkedHashMap<String, VarSymbol> parameterSymbols = functSymbol.getParaList();
			int i = 0;
			for (Map.Entry<String, VarSymbol> entry : parameterSymbols.entrySet()) {
				VarDefNode parameter = parameters.get(i++);
				VarSymbol parameterSymbol = entry.getValue();
				IRType paraType = toIRType(parameter.getType());
				IRRegister address = new IRRegister(new IRPtrType(paraType), parameter.getIdentifier() + "$");
				currentFunction.addRegister(address);
				currentBlock.addInst(new AllocaInst(address, paraType));
				parameterSymbol.setAddress(address);
				parameterSymbol.setIRType(paraType);
				
				IRRegister reg = new IRRegister(paraType, parameter.getIdentifier());
				currentFunction.addRegister(reg);
				currentFunction.addParameter(reg);
				currentBlock.addInst(new StoreInst(reg, address));
			}
			
			//returnAddress
			if (!(returnType instanceof IRVoidType)) {
				returnAddress = new IRRegister(new IRPtrType(returnType), "returnValue$");
				currentFunction.addRegister(returnAddress);
				currentBlock.addInst(new AllocaInst(returnAddress, returnType));	
			}
			
			ArrayList<StmtNode> stmtList = node.getStmtList();
			for (StmtNode stmt : stmtList) {
				stmt.accept(this);
			}
			
			//returnBlock
			if(!(currentBlock.getTail() instanceof BrInst)) {
				if (returnType instanceof IRVoidType) {
					
				}
				else if (returnType instanceof IRInt32Type) {
					currentBlock.addInst(new StoreInst(new IRConstInt(0), returnAddress));
				}
				else if (returnType instanceof IRInt1Type) {
					currentBlock.addInst(new StoreInst(new IRConstBool(false), returnAddress));
				}
				else {
					currentBlock.addInst(new StoreInst(new IRNull(), returnAddress));	
				}
				currentBlock.addInst(new BrInst(currentBlock, returnBlock));
			}
			currentFunction.addBasicBlock(returnBlock);
			currentFunction.setExitBlock(returnBlock);
			currentBlock = returnBlock;
			if (returnType instanceof IRVoidType) {
				currentBlock.addInst(new RetInst());
			}
			else {
				IRRegister returnValue = new IRRegister(returnType, "returnValue");
				currentFunction.addRegister(returnValue);
				currentBlock.addInst(new LoadInst(returnValue, returnAddress));
				currentBlock.addInst(new RetInst(returnValue));
			}
		}
		else if (parentScope instanceof ClassSymbol){
			//function in class scope.
			FunctSymbol functSymbol = node.getFunctSymbol();
			IRFunction function = functSymbol.toIRFunction();
			IRType returnType = function.getType();
			currentFunction = function;
			//entranceBlock
			IRBasicBlock entranceBlock = new IRBasicBlock("entranceBlock");
			IRBasicBlock returnBlock = new IRBasicBlock("returnBlock");
			currentReturnBlock = returnBlock;
			currentFunction.addBasicBlock(entranceBlock);
			currentBlock = entranceBlock;
			//returnValue
			if (!(returnType instanceof IRVoidType)) {
				returnAddress = new IRRegister(new IRPtrType(returnType), "returnValue$");
				currentFunction.addRegister(returnAddress);
				currentBlock.addInst(new AllocaInst(returnAddress, returnType));	
			}
			//this
			IRType thisType = new IRPtrType(((ClassSymbol) parentScope).toIRClass());
			IRRegister thisAddress = new IRRegister(new IRPtrType(thisType), "this$");
			currentFunction.addRegister(thisAddress);
			currentBlock.addInst(new AllocaInst(thisAddress, thisType)); 
			
			IRRegister thisRegister = new IRRegister(thisType, "this");
			currentFunction.addRegister(thisRegister);
			currentFunction.addParameter(thisRegister);
			currentBlock.addInst(new StoreInst(thisRegister, thisAddress));
			//parameters	
			ArrayList<VarDefNode> parameters = node.getParaList();
			LinkedHashMap<String, VarSymbol> parameterSymbols = functSymbol.getParaList();
			int i = 0;
			for (Map.Entry<String, VarSymbol> entry : parameterSymbols.entrySet()) {
				VarDefNode parameter = parameters.get(i++);
				VarSymbol parameterSymbol = entry.getValue();
				IRType paraType = toIRType(parameter.getType());
				IRRegister address = new IRRegister(new IRPtrType(paraType), parameter.getIdentifier() + "$");
				currentFunction.addRegister(address);
				currentBlock.addInst(new AllocaInst(address, paraType));
				parameterSymbol.setAddress(address);
				parameterSymbol.setIRType(paraType);
				
				IRRegister reg = new IRRegister(paraType, parameter.getIdentifier());
				currentFunction.addRegister(reg);
				currentFunction.addParameter(reg);
				currentBlock.addInst(new StoreInst(reg, address));
			}
			
			ArrayList<StmtNode> stmtList = node.getStmtList();
			for (StmtNode stmt : stmtList) {
				stmt.accept(this);
			}
			
			//returnBlock
			if(!(currentBlock.getTail() instanceof BrInst)) {
				if (returnType instanceof IRVoidType) {
					
				}
				else if (returnType instanceof IRInt32Type) {
					currentBlock.addInst(new StoreInst(new IRConstInt(0), returnAddress));
				}
				else if (returnType instanceof IRInt1Type) {
					currentBlock.addInst(new StoreInst(new IRConstBool(false), returnAddress));
				}
				else {
					currentBlock.addInst(new StoreInst(new IRNull(), returnAddress));	
				}
				currentBlock.addInst(new BrInst(currentBlock, returnBlock)); 
			}
			currentFunction.addBasicBlock(returnBlock);
			currentFunction.setExitBlock(returnBlock);
			currentBlock = returnBlock;
			if (returnType instanceof IRVoidType) {
				currentBlock.addInst(new RetInst());
			}
			else {
				IRRegister returnValue = new IRRegister(returnType, "returnValue");
				currentFunction.addRegister(returnValue);
				currentBlock.addInst(new LoadInst(returnValue, returnAddress));
				currentBlock.addInst(new RetInst(returnValue));
			}
		}
	}
	
	@Override
	public void visit(VarDefListNode node) {
		ArrayList<VarDefNode> varList = node.getVarList();
		for (VarDefNode item : varList) {		
			item.accept(this);
		}	
	}
	
	@Override
	public void visit(VarDefNode node) {
		VarSymbol varSymbol = node.getVarSymbol();
		IRType type = toIRType(node.getType());
		varSymbol.setIRType(type);
		IRRegister address = new IRRegister(new IRPtrType(type), node.getIdentifier() + "$");
		varSymbol.setAddress(address);
		currentFunction.addRegister(address);
		currentBlock.addInst(new AllocaInst(address, type));
		
		ExprNode init = node.getInitValue();
		if (init != null) {
			init.accept(this);
			currentBlock.addInst(new StoreInst(init.getResult(), address));
		}
	}

	@Override
	public void visit(VarDefStmtNode node) {
		node.getVarDefList().accept(this);
	}

	@Override
	public void visit(BlockStmtNode node) {
		ArrayList<StmtNode> stmtList = node.getStmtList();
		for (StmtNode stmt : stmtList) {
			stmt.accept(this);
		}
	}
	
	@Override
	public void visit(ForStmtNode node) {
		IRBasicBlock forCondBlock = new IRBasicBlock("forCondBlock");
		IRBasicBlock forBodyBlock = new IRBasicBlock("forBodyBlock");
		IRBasicBlock forStepBlock = new IRBasicBlock("forStepBlock");
		IRBasicBlock afterForBlock = new IRBasicBlock("afterForBlock");
		currentFunction.addBasicBlock(forCondBlock);
		currentFunction.addBasicBlock(forBodyBlock);
		currentFunction.addBasicBlock(forStepBlock);
		currentFunction.addBasicBlock(afterForBlock);
		
		if (node.getInitExpr() != null)
			node.getInitExpr().accept(this);

		if (!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, forCondBlock));
	
		currentBlock = forCondBlock;
		if (node.getCondExpr() != null) {
			node.getCondExpr().accept(this);
			currentBlock.addInst(new BrInst(currentBlock, node.getCondExpr().getResult(), forBodyBlock, afterForBlock));
		}
		else {
			currentBlock.addInst(new BrInst(currentBlock, forBodyBlock));
		}
		
		currentBlock = forBodyBlock;
		IRBasicBlock tmpNextLoop = nextLoop, tmpAfterLoop = afterLoop;
		nextLoop = forStepBlock; 
		afterLoop = afterForBlock;
		node.getStmt().accept(this);
		nextLoop = tmpNextLoop;
		afterLoop = tmpAfterLoop;
		if (!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, forStepBlock));
		
		currentBlock = forStepBlock;
		if (node.getStepExpr() != null)
			node.getStepExpr().accept(this);
		currentBlock.addInst(new BrInst(currentBlock, forCondBlock));
		
		currentBlock = afterForBlock;
	}

	@Override
	public void visit(IfStmtNode node) {
		ExprNode condExpr = node.getCond();
		StmtNode thenStmt = node.getThenStmt(), elseStmt = node.getElseStmt();
		
		IRBasicBlock ifCondBlock = new IRBasicBlock("ifCondBlock");
		IRBasicBlock thenBodyBlock = new IRBasicBlock("thenBodyBlock");
		IRBasicBlock elseBodyBlock = new IRBasicBlock("elseBodyBlock");
		IRBasicBlock afterIfBlock = new IRBasicBlock("afterIfBlock");
		currentFunction.addBasicBlock(ifCondBlock);
		currentFunction.addBasicBlock(thenBodyBlock);
		if (elseStmt != null)
			currentFunction.addBasicBlock(elseBodyBlock);
		currentFunction.addBasicBlock(afterIfBlock);
		
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, ifCondBlock));
		
		currentBlock = ifCondBlock;
		condExpr.accept(this);
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, condExpr.getResult(), thenBodyBlock, elseStmt == null ? afterIfBlock : elseBodyBlock));
		
		currentBlock = thenBodyBlock;
		thenStmt.accept(this);
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, afterIfBlock));
		
		if (elseStmt != null) {
			currentBlock = elseBodyBlock;
			elseStmt.accept(this);
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, afterIfBlock));
		}
		
		currentBlock = afterIfBlock;	
	}

	@Override
	public void visit(WhileStmtNode node) {
		IRBasicBlock whileCondBlock = new IRBasicBlock("whileCondBlock");
		IRBasicBlock whileBodyBlock = new IRBasicBlock("whileBodyBlock");
		IRBasicBlock afterWhileBlock = new IRBasicBlock("afterWhileBlock");
		currentFunction.addBasicBlock(whileCondBlock);
		currentFunction.addBasicBlock(whileBodyBlock);
		currentFunction.addBasicBlock(afterWhileBlock);
		
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, whileCondBlock));
		
		ExprNode condExpr = node.getExpr();
		currentBlock = whileCondBlock;
		condExpr.accept(this);
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, condExpr.getResult(), whileBodyBlock, afterWhileBlock));
		
		currentBlock = whileBodyBlock;
		IRBasicBlock tmpNextLoop = nextLoop, tmpAfterLoop = afterLoop;
		nextLoop = whileCondBlock;
		afterLoop = afterWhileBlock;
		node.getStmt().accept(this);
		nextLoop = tmpNextLoop;
		afterLoop = tmpAfterLoop;
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, whileCondBlock));
		
		currentBlock = afterWhileBlock;
	}

	@Override
	public void visit(BrankStmtNode node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(BreakStmtNode node) {
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, afterLoop));
	}

	@Override
	public void visit(ContinueStmtNode node) {
		if(!(currentBlock.getTail() instanceof BrInst))
			currentBlock.addInst(new BrInst(currentBlock, nextLoop));
	}

	@Override
	public void visit(ExprStmtNode node) {
		node.getExpr().accept(this);	
	}

	@Override
	public void visit(ReturnStmtNode node) {
		ExprNode returnExpr = node.getExpr();
		if(returnExpr != null) {
			returnExpr.accept(this);
			currentBlock.addInst(new StoreInst(returnExpr.getResult(), returnAddress));
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, currentReturnBlock));
		}
		else {
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, currentReturnBlock));
		}
	}

	@Override
	public void visit(BinaryExprNode node) {
		Operator op = node.getOp();
		ExprNode left = node.getLeft();
		ExprNode right = node.getRight();
		if (op == Operator.logicalAND) {
			left.accept(this);
			IRSymbol leftRes = left.getResult();
			IRBasicBlock logicalAnd = new IRBasicBlock("logicalAnd");
			currentFunction.addBasicBlock(logicalAnd);
			IRBasicBlock afterLogicalAnd = new IRBasicBlock("afterLogicalAnd");
			currentFunction.addBasicBlock(afterLogicalAnd);
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, leftRes, logicalAnd, afterLogicalAnd));
			
			IRBasicBlock tmp = currentBlock;
			currentBlock = logicalAnd;
			right.accept(this);
			IRSymbol rightRes = right.getResult();
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, afterLogicalAnd));
			
			currentBlock = afterLogicalAnd;
			IRRegister res = new IRRegister(new IRInt1Type(), "phi");
			currentFunction.addRegister(res);
			PhiInst phiInst = new PhiInst(res);
			phiInst.addBranch(new IRConstBool(false), tmp);
			phiInst.addBranch(rightRes, logicalAnd);
			currentBlock.addInst(phiInst);
			
			node.setResult(res);
		}
		else if (op == Operator.logicalOR) {
			left.accept(this);
			IRSymbol leftRes = left.getResult();
			IRBasicBlock logicalOr = new IRBasicBlock("logicalOr");
			currentFunction.addBasicBlock(logicalOr);
			IRBasicBlock afterLogicalOr = new IRBasicBlock("afterLogicalOr");
			currentFunction.addBasicBlock(afterLogicalOr);
			currentBlock.addInst(new BrInst(currentBlock, leftRes, afterLogicalOr, logicalOr));
			
			IRBasicBlock tmp = currentBlock;
			currentBlock = logicalOr;
			right.accept(this);
			IRSymbol rightRes = right.getResult();
			currentBlock.addInst(new BrInst(currentBlock, afterLogicalOr));
			
			currentBlock = afterLogicalOr;
			IRRegister res = new IRRegister(new IRInt1Type(), "phi");
			currentFunction.addRegister(res);
			PhiInst phiInst = new PhiInst(res);
			phiInst.addBranch(new IRConstBool(true), tmp);
			phiInst.addBranch(rightRes, logicalOr);
			currentBlock.addInst(phiInst);
			
			node.setResult(res);
		}
		else {
			left.accept(this);
			right.accept(this);
			IRSymbol leftRes = left.getResult();
			IRSymbol rightRes = right.getResult();
			//binary operation instructions
			if (op == Operator.ADD) {
				Type type = node.getType();
				if (type instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt32Type(), "add");
					currentFunction.addRegister(res);
					currentBlock.addInst(new BinOpInst(BinOpType.add, res, leftRes, rightRes));
					node.setResult(res);
				}
				else if (type instanceof StringType) {
					IRRegister res = new IRRegister(new IRPtrType(new IRInt8Type()), "add");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringAdd");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					currentBlock.addInst(new CallInst(funct, parameters, res));
					node.setResult(res);
				}
			}
			else if (op == Operator.SUB) {
				IRRegister res = new IRRegister(new IRInt32Type(), "sub");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BinOpInst(BinOpType.sub, res, leftRes, rightRes));
				node.setResult(res);
			}
			else if (op == Operator.MUL) {
				IRRegister res = new IRRegister(new IRInt32Type(), "mul");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BinOpInst(BinOpType.mul, res, leftRes, rightRes));
				node.setResult(res);
			}
			else if (op == Operator.DIV) {
				IRRegister res = new IRRegister(new IRInt32Type(), "sdiv");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BinOpInst(BinOpType.sdiv, res, leftRes, rightRes));
				node.setResult(res);
			}
			else if (op == Operator.MOD) {
				IRRegister res = new IRRegister(new IRInt32Type(), "srem");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BinOpInst(BinOpType.srem, res, leftRes, rightRes));
				node.setResult(res);
			}
			//bitwise binary operation instructions
			else if (op == Operator.leftSHIFT) {
				IRRegister res = new IRRegister(new IRInt32Type(), "shl");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BitwiseBinOpInst(BitwiseBinOpType.shl, res, leftRes, rightRes));
				node.setResult(res);
			}
			else if (op == Operator.rightSHIFT) {
				IRRegister res = new IRRegister(new IRInt32Type(), "ashr");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BitwiseBinOpInst(BitwiseBinOpType.ashr, res, leftRes, rightRes));
				node.setResult(res);
			}
			else if (op == Operator.bitwiseAND) {
				IRRegister res = new IRRegister(new IRInt32Type(), "and");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BitwiseBinOpInst(BitwiseBinOpType.and, res, leftRes, rightRes));
				node.setResult(res);
			}
			else if (op == Operator.bitwiseOR) {
				IRRegister res = new IRRegister(new IRInt32Type(), "or");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BitwiseBinOpInst(BitwiseBinOpType.or, res, leftRes, rightRes));
				node.setResult(res);
			}
			else if (op == Operator.bitwiseXOR) {
				IRRegister res = new IRRegister(new IRInt32Type(), "xor");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BitwiseBinOpInst(BitwiseBinOpType.xor, res, leftRes, rightRes));
				node.setResult(res);
			}
			//icmp instructions
			else if (op == Operator.EQU) {
				Type leftType = node.getLeft().getType();
				Type rightType = node.getRight().getType();
				if (leftType instanceof IntType && rightType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "eq");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.eq, res, leftRes, rightRes));
					node.setResult(res);		
				}
				else if ((leftType instanceof BoolType && rightType instanceof BoolType) ||    
				     (leftType instanceof ClassSymbol && rightType instanceof NullType)  ||    
				     (leftType instanceof NullType && rightType instanceof ClassSymbol)  ||     
				     (leftType instanceof ArrayType && rightType instanceof NullType)    ||        
			         (leftType instanceof NullType && rightType instanceof ArrayType)    
				) {
					IRRegister res = new IRRegister(new IRInt1Type(), "eq");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.eq, res, leftRes, rightRes));
					node.setResult(res);
				}
				else if (leftType instanceof StringType && rightType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "eq");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					currentBlock.addInst(new CallInst(funct, parameters, res));
					node.setResult(res);
				}
				else if (leftType instanceof NullType && rightType instanceof NullType) {
					node.setResult(new IRConstBool(true));
				}
			}
			else if (op == Operator.notEQU) {
				Type leftType = node.getLeft().getType();
				Type rightType = node.getRight().getType();
				if (leftType instanceof IntType && rightType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "ne");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.ne, res, leftRes, rightRes));
					node.setResult(res);		
				}
				else if ((leftType instanceof BoolType && rightType instanceof BoolType) ||    
				     (leftType instanceof ClassSymbol && rightType instanceof NullType)  ||    
				     (leftType instanceof NullType && rightType instanceof ClassSymbol)  ||     
				     (leftType instanceof ArrayType && rightType instanceof NullType)    ||        
			         (leftType instanceof NullType && rightType instanceof ArrayType)    
				) {
					IRRegister res = new IRRegister(new IRInt1Type(), "ne");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.ne, res, leftRes, rightRes));
					node.setResult(res);
				}
				else if (leftType instanceof StringType && rightType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "eq");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringNotEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					currentBlock.addInst(new CallInst(funct, parameters, res));
					node.setResult(res);
				}
				else if (leftType instanceof NullType && rightType instanceof NullType) {
					node.setResult(new IRConstBool(false));
				}
			}
			else if (op == Operator.LESS) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "slt");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.slt, res, leftRes, rightRes));
					node.setResult(res);		
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "slt");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringLess");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					currentBlock.addInst(new CallInst(funct, parameters, res));
					node.setResult(res);
				}
			}
			else if (op == Operator.lessEQU) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sle");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.sle, res, leftRes, rightRes));
					node.setResult(res);	
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sle");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringLessEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					currentBlock.addInst(new CallInst(funct, parameters, res));
					node.setResult(res);
				}
			}
			else if (op == Operator.GREATER) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sgt");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.sgt, res, leftRes, rightRes));
					node.setResult(res);		
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sgt");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringGreater");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					currentBlock.addInst(new CallInst(funct, parameters, res));
					node.setResult(res);
				}
			}
			else if (op == Operator.greaterEQU) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sge");
					currentFunction.addRegister(res);
					currentBlock.addInst(new IcmpInst(IcmpOpType.sge, res, leftRes, rightRes));
					node.setResult(res);
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sge");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringGreaterEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					currentBlock.addInst(new CallInst(funct, parameters, res));
					node.setResult(res);
				}
			}
			else if (op == Operator.ASSIGN) {
				currentBlock.addInst(new StoreInst(rightRes, node.getLeft().getAddress()));
				node.setResult(rightRes);
			}
		}
	}

	@Override
	public void visit(PrefixExprNode node) {
		ExprNode expr = node.getExpr();
		Operator op = node.getOp();
		expr.accept(this);
		IRSymbol exprRes = expr.getResult();
		if (op == Operator.POS) {
			node.setResult(exprRes);
		}
		else if (op == Operator.NEG) {
			IRRegister res = new IRRegister(new IRInt32Type(), "neg");
			currentFunction.addRegister(res);
			currentBlock.addInst(new BinOpInst(BinOpType.sub, res, new IRConstInt(0), exprRes));
			node.setResult(res);
		}
		else if (op == Operator.prefixINCR) {
			IRRegister res = new IRRegister(new IRInt32Type(), "prefixIncr");
			currentFunction.addRegister(res);
			currentBlock.addInst(new BinOpInst(BinOpType.add, res, exprRes, new IRConstInt(1)));
			currentBlock.addInst(new StoreInst(res, expr.getAddress()));
			node.setResult(res);
			node.setAddress(expr.getAddress());
		}
		else if (op == Operator.prefixDECR) {
			IRRegister res = new IRRegister(new IRInt32Type(), "prefixDecr");
			currentFunction.addRegister(res);
			currentBlock.addInst(new BinOpInst(BinOpType.sub, res, exprRes, new IRConstInt(1)));
			currentBlock.addInst(new StoreInst(res, expr.getAddress()));
			node.setResult(res);
			node.setAddress(expr.getAddress());
		}
		else if (op == Operator.logicalNOT) {
			IRRegister res = new IRRegister(new IRInt1Type(), "logicalNot");
			currentFunction.addRegister(res);
			currentBlock.addInst(new BitwiseBinOpInst(BitwiseBinOpType.xor, res, exprRes, new IRConstBool(true)));
			node.setResult(res);
		}
		else if (op == Operator.bitwiseNOT) {
			IRRegister res = new IRRegister(new IRInt32Type(), "bitwiseNot");
			currentFunction.addRegister(res);
			currentBlock.addInst(new BitwiseBinOpInst(BitwiseBinOpType.xor, res, exprRes, new IRConstInt(-1)));
			node.setResult(res);
		}
	}

	@Override
	public void visit(SuffixExprNode node) {
		ExprNode expr = node.getExpr();
		Operator op = node.getOp();
		expr.accept(this);
		IRSymbol exprRes = expr.getResult();
		if (op == Operator.suffixINCR) {
			IRRegister res = new IRRegister(new IRInt32Type(), "suffixIncr");
			currentFunction.addRegister(res);
			currentBlock.addInst(new BinOpInst(BinOpType.add, res, exprRes, new IRConstInt(1)));
			currentBlock.addInst(new StoreInst(res, expr.getAddress()));
			node.setResult(exprRes);
			node.setAddress(expr.getAddress());
		}
		else if (op == Operator.suffixDECR) {
			IRRegister res = new IRRegister(new IRInt32Type(), "suffixDecr");
			currentFunction.addRegister(res);
			currentBlock.addInst(new BinOpInst(BinOpType.sub, res, exprRes, new IRConstInt(1)));
			currentBlock.addInst(new StoreInst(res, expr.getAddress()));
			node.setResult(exprRes);
			node.setAddress(expr.getAddress());
		}
	}
	
	@Override
	public void visit(BracketExprNode node) {
		ExprNode expr = node.getExpr();
		expr.accept(this);
		node.setResult(expr.getResult());
		node.setAddress(expr.getAddress());
	}
	
	private IRRegister createIntOrString(IRType baseType, int dimension, ArrayList<ExprNode> indexes) {
		IRType type = baseType;
		for (int i = 0; i < dimension; ++i) 
			type = new IRPtrType(type);
		ExprNode index = indexes.get(0);
		
		IRRegister mul = new IRRegister(new IRInt32Type(), "mul");
		currentFunction.addRegister((IRRegister)mul);
		currentBlock.addInst(new BinOpInst(BinOpInst.BinOpType.mul, mul, new IRConstInt(((IRPtrType) type).getType().bytes()), index.getResult()));
		
		IRRegister add = new IRRegister(new IRInt32Type(), "add");
		currentFunction.addRegister(add);
		currentBlock.addInst(new BinOpInst(BinOpInst.BinOpType.add, add, mul, new IRConstInt(4)));
		
		IRRegister malloc8 = new IRRegister(new IRPtrType(new IRInt8Type()), "malloc8");
		currentFunction.addRegister(malloc8);
		currentBlock.addInst(new CallInst(mallocFunction, add, malloc8));
		
		IRRegister malloc32 = new IRRegister(new IRPtrType(new IRInt32Type()), "malloc32");
		currentFunction.addRegister(malloc32);
		currentBlock.addInst(new BitcastToInst(malloc8, malloc32));
		
		currentBlock.addInst(new StoreInst(index.getResult(), malloc32));
		
		IRRegister arrayHead32 = new IRRegister(new IRPtrType(new IRInt32Type()), "arrayHead32");
		currentFunction.addRegister(arrayHead32);
		currentBlock.addInst(new GetElementPtrInst(arrayHead32, malloc32, new IRConstInt(1)));
		
		IRRegister arrayHead;
		if (((IRPtrType)type).getType() instanceof IRInt32Type) 
			arrayHead = arrayHead32;
		else {
			arrayHead = new IRRegister(type, "arrayHead");
			currentFunction.addRegister(arrayHead);
			currentBlock.addInst(new BitcastToInst(arrayHead32, arrayHead));
		}
		
		if (indexes.size() > 1) {
			ArrayList<ExprNode> newIndexes = new ArrayList<ExprNode>();
			for (int i = 1; i < indexes.size(); ++i) 
				newIndexes.add(indexes.get(i));
			
			IRBasicBlock creatorCondBlock = new IRBasicBlock("creatorCondBlock");
			IRBasicBlock creatorBodyBlock = new IRBasicBlock("creatorBodyBlock");
			IRBasicBlock creatorStepBlock = new IRBasicBlock("creatorStepBlock");
			IRBasicBlock afterCreatorBlock = new IRBasicBlock("afterCreatorBlock");
			currentFunction.addBasicBlock(creatorCondBlock);
			currentFunction.addBasicBlock(creatorBodyBlock);
			currentFunction.addBasicBlock(creatorStepBlock);
			currentFunction.addBasicBlock(afterCreatorBlock);
			
			IRRegister arrayTail = new IRRegister(type, "arrayTail");
			currentFunction.addRegister(arrayTail);
			IRRegister arrayNext = new IRRegister(type, "arrayNext");
			currentFunction.addRegister(arrayNext);
			IRRegister arrayNow = new IRRegister(type, "arrayNow");
			currentFunction.addRegister(arrayNow);
			
			currentBlock.addInst(new GetElementPtrInst(arrayTail, arrayHead, index.getResult()));
			
			IRBasicBlock tmp = currentBlock;
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, creatorCondBlock));
			currentBlock = creatorCondBlock;
			
			PhiInst inst = new PhiInst(arrayNow);
			inst.addBranch(arrayHead, tmp);
			inst.addBranch(arrayNext, creatorStepBlock);
			currentBlock.addInst(inst);
			
			IRRegister ne = new IRRegister(new IRInt1Type(), "ne");
			currentFunction.addRegister(ne);
			currentBlock.addInst(new IcmpInst(IcmpInst.IcmpOpType.ne, ne, arrayNow, arrayTail));
			
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, ne, creatorBodyBlock, afterCreatorBlock));
			currentBlock = creatorBodyBlock;
	
			IRRegister subCreator = createIntOrString(baseType, dimension - 1, newIndexes);
			
			currentBlock.addInst(new StoreInst(subCreator, arrayNow));
			
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, creatorStepBlock));
			currentBlock = creatorStepBlock; 
			
			currentBlock.addInst(new GetElementPtrInst(arrayNext, arrayNow, new IRConstInt(1)));
			
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, creatorCondBlock));

			currentBlock = afterCreatorBlock;
		}
		return arrayHead;
	}
	
	
	private IRRegister createClassType(IRType baseType, int dimension, ArrayList<ExprNode> indexes, IRFunction constructor) {
		if (dimension == 0) {
			IRRegister malloc8 = new IRRegister(new IRPtrType(new IRInt8Type()), "malloc8");
			currentFunction.addRegister(malloc8);
			currentBlock.addInst(new CallInst(mallocFunction, new IRConstInt(baseType.bytes()), malloc8));
			
			IRRegister malloc = new IRRegister(new IRPtrType(baseType), "malloc");
			currentFunction.addRegister(malloc);
			currentBlock.addInst(new BitcastToInst(malloc8, malloc));
			
			if (constructor != null) {
				currentBlock.addInst(new CallInst(constructor, malloc));
			}
			return malloc;
		}
		
		IRType type = new IRPtrType(baseType);
		for (int i = 0; i < dimension; ++i) {
			type = new IRPtrType(type);
		}
		ExprNode index = indexes.get(0);
		
		IRRegister mul = new IRRegister(new IRInt32Type(), "mul");
		currentFunction.addRegister((IRRegister)mul);
		currentBlock.addInst(new BinOpInst(BinOpInst.BinOpType.mul, mul, new IRConstInt(8), index.getResult()));
		
		IRRegister add = new IRRegister(new IRInt32Type(), "add");
		currentFunction.addRegister(add);
		currentBlock.addInst(new BinOpInst(BinOpInst.BinOpType.add, add, mul, new IRConstInt(4)));
		
		IRRegister malloc8 = new IRRegister(new IRPtrType(new IRInt8Type()), "malloc8");
		currentFunction.addRegister(malloc8);
		currentBlock.addInst(new CallInst(mallocFunction, add, malloc8));
		
		IRRegister malloc32 = new IRRegister(new IRPtrType(new IRInt32Type()), "malloc32");
		currentFunction.addRegister(malloc32);
		currentBlock.addInst(new BitcastToInst(malloc8, malloc32));
		
		currentBlock.addInst(new StoreInst(index.getResult(), malloc32));
		
		IRRegister arrayHead32 = new IRRegister(new IRPtrType(new IRInt32Type()), "arrayHead32");
		currentFunction.addRegister(arrayHead32);
		currentBlock.addInst(new GetElementPtrInst(arrayHead32, malloc32, new IRConstInt(1)));
		
		IRRegister arrayHead = new IRRegister(type, "arrayHead");
		currentFunction.addRegister(arrayHead);
		currentBlock.addInst(new BitcastToInst(arrayHead32, arrayHead));
		
		if (/*(indexes.size() == 1 && dimension == 1) || */indexes.size() > 1) {
			ArrayList<ExprNode> newIndexes = new ArrayList<ExprNode>();
			for (int i = 1; i < indexes.size(); ++i) 
				newIndexes.add(indexes.get(i));
			
			IRBasicBlock creatorCondBlock = new IRBasicBlock("creatorCondBlock");
			IRBasicBlock creatorBodyBlock = new IRBasicBlock("creatorBodyBlock");
			IRBasicBlock creatorStepBlock = new IRBasicBlock("creatorStepBlock");
			IRBasicBlock afterCreatorBlock = new IRBasicBlock("afterCreatorBlock");
			currentFunction.addBasicBlock(creatorCondBlock);
			currentFunction.addBasicBlock(creatorBodyBlock);
			currentFunction.addBasicBlock(creatorStepBlock);
			currentFunction.addBasicBlock(afterCreatorBlock);
			
			IRRegister arrayTail = new IRRegister(type, "arrayTail");
			currentFunction.addRegister(arrayTail);
			IRRegister arrayNext = new IRRegister(type, "arrayNext");
			currentFunction.addRegister(arrayNext);
			IRRegister arrayNow = new IRRegister(type, "arrayNow");
			currentFunction.addRegister(arrayNow);
			
			currentBlock.addInst(new GetElementPtrInst(arrayTail, arrayHead, index.getResult()));
			
			IRBasicBlock tmp = currentBlock;
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, creatorCondBlock));
			currentBlock = creatorCondBlock;
			
			PhiInst inst = new PhiInst(arrayNow);
			inst.addBranch(arrayHead, tmp);
			inst.addBranch(arrayNext, creatorStepBlock);
			currentBlock.addInst(inst);
			
			IRRegister ne = new IRRegister(new IRInt1Type(), "ne");
			currentFunction.addRegister(ne);
			currentBlock.addInst(new IcmpInst(IcmpInst.IcmpOpType.ne, ne, arrayNow, arrayTail));
			
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, ne, creatorBodyBlock, afterCreatorBlock));
			currentBlock = creatorBodyBlock;
			
			IRRegister subCreator = createClassType(baseType, dimension - 1, newIndexes, constructor);
					
			currentBlock.addInst(new StoreInst(subCreator, arrayNow));
			
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, creatorStepBlock));
			currentBlock = creatorStepBlock; 
			
			currentBlock.addInst(new GetElementPtrInst(arrayNext, arrayNow, new IRConstInt(1)));
			
			if(!(currentBlock.getTail() instanceof BrInst))
				currentBlock.addInst(new BrInst(currentBlock, creatorCondBlock));
			
			currentBlock = afterCreatorBlock;
		}
		return arrayHead;
	}
	
	@Override
	public void visit(CreatorExprNode node) {
		int dimension = node.getDimension();
		ArrayList<ExprNode> indexes = node.getIndexList();
		for (ExprNode index : indexes) {
			index.accept(this);
		}
		
		TypeNode typeNode = node.getTypeNode();
		String typeName = typeNode.typeString();
		
		if (typeName.equals("int")) {
			IRRegister res = createIntOrString(new IRInt32Type(), dimension, indexes);
			node.setResult(res);
		}
		else if (typeName.equals("bool")) {
			IRRegister res = createIntOrString(new IRInt1Type(), dimension, indexes);
			node.setResult(res);
		}
		else if (typeName.equals("string")) {
			IRRegister res = createIntOrString(new IRPtrType(new IRInt8Type()), dimension, indexes);
			node.setResult(res);
		}
		else {
			ClassSymbol classSymbol = globalScope.getClassScope(node.getType().typeString());
			IRFunction constructor = classSymbol.getConstructor().toIRFunction();
			IRRegister res = createClassType(classSymbol.toIRClass(), dimension, indexes, constructor);
			node.setResult(res);
		}
	}

	@Override
	public void visit(FunctExprNode node) {
		ExprNode nameExpr = node.getNameExpr();
		if (nameExpr instanceof MemberExprNode) {
			nameExpr.accept(this);
			node.setResult(nameExpr.getResult());
			node.setAddress(nameExpr.getAddress());
		}
		else if (nameExpr instanceof VarExprNode){
			FunctSymbol functSymbol = (FunctSymbol) ((VarExprNode)nameExpr).getSymbol();
			IRFunction function = functSymbol.toIRFunction();
			IRType returnType = function.getType(); 
			IRRegister res = returnType instanceof IRVoidType ? null : new IRRegister(returnType, "call");
			if (res != null)
				currentFunction.addRegister(res);
			if (functSymbol.getEnclosingScope() == globalScope) {
				//function in global scope	
				ArrayList<ExprNode> parameters = node.getParaList();
				ArrayList<IRSymbol> parametersRes = new ArrayList<IRSymbol>();
				for (ExprNode expr : parameters) {
					expr.accept(this);
					parametersRes.add(expr.getResult());
				}
				currentBlock.addInst(new CallInst(functSymbol.toIRFunction(), parametersRes, res));
				node.setResult(res);
			}
			else {
				//function in class scope
				IRRegister thisAddress = currentFunction.getRegister("this$");
				assert thisAddress.getType() instanceof IRPtrType;
				IRRegister thisRegister = new IRRegister(((IRPtrType) thisAddress.getType()).getType(), "this");
				currentFunction.addRegister(thisRegister);
				currentBlock.addInst(new LoadInst(thisRegister, thisAddress));
				
				ArrayList<ExprNode> parameters = node.getParaList();
				ArrayList<IRSymbol> parametersRes = new ArrayList<IRSymbol>();
				parametersRes.add(thisRegister);
				for (ExprNode expr : parameters) {
					expr.accept(this);
					parametersRes.add(expr.getResult());
				}
				currentBlock.addInst(new CallInst(functSymbol.toIRFunction(), parametersRes, res));
				node.setResult(res);
			}
		}
		else if (nameExpr instanceof CreatorExprNode) {
			nameExpr.accept(this);
			node.setAddress(nameExpr.getAddress());
			node.setResult(nameExpr.getResult());
		}
	}

	@Override
	public void visit(ArrayExprNode node) {
		ExprNode nameExpr = node.getNameExpr();
		if (nameExpr instanceof MemberExprNode) {
			nameExpr.accept(this);
			node.setAddress(nameExpr.getAddress());
			node.setResult(nameExpr.getResult());
		}
		else if (nameExpr instanceof VarExprNode){
			nameExpr.accept(this);
			ExprNode indexExpr = node.getIndexExpr();
			indexExpr.accept(this);
			//getelementptr --> elementAddress
			IRRegister array = (IRRegister) nameExpr.getResult();
			IRPtrType arrayType = (IRPtrType) array.getType();
			IRRegister elementAddress = new IRRegister(arrayType, "element$");
			currentFunction.addRegister(elementAddress);
			currentBlock.addInst(new GetElementPtrInst(elementAddress, array, indexExpr.getResult()));
			
			//load --> element
			IRRegister element = new IRRegister(arrayType.getType(), "element");
			currentFunction.addRegister(element);
			currentBlock.addInst(new LoadInst(element, elementAddress));
			
			node.setResult(element);
			node.setAddress(elementAddress);
		} 
		else {
			//nameExpr instanceof ArrayExprNode or FunctExprNode
			nameExpr.accept(this);
			IRRegister array = (IRRegister) nameExpr.getResult();
			IRPtrType arrayType = (IRPtrType) array.getType();
			
			ExprNode indexExpr = node.getIndexExpr();
			indexExpr.accept(this);
		
			//getelementptr --> elementAddress
			IRRegister elementAddress = new IRRegister(arrayType, "element$");
			currentFunction.addRegister(elementAddress);
			currentBlock.addInst(new GetElementPtrInst(elementAddress, array, indexExpr.getResult()));
			
			//load --> element
			IRRegister element = new IRRegister(arrayType.getType(), "element");
			currentFunction.addRegister(element);
			currentBlock.addInst(new LoadInst(element, elementAddress));
			
			node.setResult(element);
			node.setAddress(elementAddress);
		}
		
	}
	
	
	@Override
	public void visit(MemberExprNode node) {
		ExprNode nameExpr = node.getNameExpr();
		nameExpr.accept(this);
		ClassSymbol classSymbol = (ClassSymbol) nameExpr.getType();
		ExprNode memberExpr = node.getMemberExpr();
		String identifier = node.getIdentifier();
		if (memberExpr instanceof VarExprNode) {
			 VarSymbol varSymbol = (VarSymbol) node.getSymbol();
			 IRPtrType memberAddressType = (IRPtrType) varSymbol.toIRAddress().getType();
			 IRRegister base = (IRRegister) nameExpr.getResult();
			 
			 //getelementptr --> memberAddress
			 IRRegister memberAddress = new IRRegister(memberAddressType, identifier + "$");
			 currentFunction.addRegister(memberAddress);
			 currentBlock.addInst(
				 new GetElementPtrInst(memberAddress, base, new IRConstInt(0), new IRConstInt(classSymbol.order(identifier)))
			 );
			 
			 //load --> member
			 IRRegister member = new IRRegister(memberAddressType.getType(), identifier);
			 currentFunction.addRegister(member);
			 currentBlock.addInst(new LoadInst(member, memberAddress));
			 
			 node.setResult(member);
			 node.setAddress(memberAddress);
		}
		else if (memberExpr instanceof FunctExprNode) {
			//ambiguity : class[].size()
			if (identifier.equals("size") && nameExpr.getType() instanceof ArrayType) {
				IRRegister arrayHead = (IRRegister) nameExpr.getResult();
				IRPtrType arrayType = (IRPtrType) arrayHead.getType();
				IRRegister arrayHead32;
				if (arrayType.getType() instanceof IRInt32Type)
					arrayHead32 = arrayHead;
				else {
					arrayHead32 = new IRRegister(new IRPtrType(new IRInt32Type()), "arrayHead32");
					currentFunction.addRegister(arrayHead32);
					currentBlock.addInst(new BitcastToInst(arrayHead, arrayHead32));
				}
				IRRegister sizePtr = new IRRegister(new IRPtrType(new IRInt32Type()), "sizePtr");
				currentFunction.addRegister(sizePtr);
				currentBlock.addInst(new GetElementPtrInst(sizePtr, arrayHead32, new IRConstInt(-1)));
				
				IRRegister size = new IRRegister(new IRInt32Type(), "size");
				currentFunction.addRegister(size);
				currentBlock.addInst(new LoadInst(size, sizePtr));
				
				node.setResult(size);
			}
			else {
				FunctSymbol functSymbol = (FunctSymbol) node.getSymbol();
				IRFunction function = functSymbol.toIRFunction();
				IRType returnType = function.getType();
				IRRegister base = (IRRegister) nameExpr.getResult();
				
				ArrayList<ExprNode> parameters = ((FunctExprNode) memberExpr).getParaList();
				ArrayList<IRSymbol> parametersRes = new ArrayList<IRSymbol>();
				parametersRes.add(base);
				for(ExprNode parameter : parameters) {
					parameter.accept(this);
					parametersRes.add(parameter.getResult());
				}
				
				//call --> member
				IRRegister member = returnType instanceof IRVoidType ? null : new IRRegister(returnType, "call");
				if (member != null)
					currentFunction.addRegister(member);
				currentBlock.addInst(new CallInst(function, parametersRes, member));
			
				node.setResult(member);
			}
		}
		else if (memberExpr instanceof ArrayExprNode) {
			ExprNode indexExpr = ((ArrayExprNode) memberExpr).getIndexExpr();
			indexExpr.accept(this);
			
			VarSymbol arraySymbol = (VarSymbol) node.getSymbol();
			IRPtrType arrayType = (IRPtrType) arraySymbol.getIRType();
			IRRegister base = (IRRegister) nameExpr.getResult();
			
			//getelementptr --> arrayAddress
			IRRegister arrayAddress = new IRRegister(new IRPtrType(arrayType), identifier + "$");
			currentFunction.addRegister(arrayAddress);
			currentBlock.addInst(new GetElementPtrInst(arrayAddress, base, new IRConstInt(0), new IRConstInt(classSymbol.order(identifier))));
		
			//load --> array
			IRRegister array = new IRRegister(arrayType, identifier);
			currentFunction.addRegister(array);
			currentBlock.addInst(new LoadInst(array, arrayAddress));
			
			//getelementptr --> elementAddress
			IRRegister elementAddress = new IRRegister(arrayType, "element$");
			currentFunction.addRegister(elementAddress);
			currentBlock.addInst(new GetElementPtrInst(elementAddress, array, indexExpr.getResult()));
			
			//load --> element
			IRRegister element = new IRRegister(arrayType.getType(), "element");
			currentFunction.addRegister(element);
			currentBlock.addInst(new LoadInst(element, elementAddress));
			
			node.setResult(element);
			node.setAddress(elementAddress);
		}
	}
	
	@Override
	public void visit(ThisExprNode node) {
		IRRegister thisAddress = currentFunction.getRegister("this$");
		IRRegister thisRegister = new IRRegister(((IRPtrType)thisAddress.getType()).getType(), "this");
		currentFunction.addRegister(thisRegister);
		currentBlock.addInst(new LoadInst(thisRegister, thisAddress));
		node.setResult(thisRegister);
	}

	@Override
	public void visit(VarExprNode node) {
		VarSymbol varSymbol = (VarSymbol) node.getSymbol();
		if (node.getScope().InClassSymbol() == currentClass && varSymbol.getScope() == currentClass) {
			//call member in own class
			//load --> this
			IRRegister thisAddress = currentFunction.getRegister("this$");
			IRRegister thisRegister = new IRRegister(((IRPtrType) thisAddress.getType()).getType(), "this");
			currentFunction.addRegister(thisRegister);
			currentBlock.addInst(new LoadInst(thisRegister, thisAddress));
			//getelementptr --> memberAddress
			String name = varSymbol.getIdentifier();
			IRType memberType = varSymbol.getIRType();
			IRRegister memberAddress = new IRRegister(new IRPtrType(memberType), name + "$");
			currentFunction.addRegister(memberAddress);
			currentBlock.addInst(new GetElementPtrInst(memberAddress, thisRegister, new IRConstInt(0), new IRConstInt(currentClass.order(name))));
			//load --> memberRegister
			IRRegister memberRegister = new IRRegister(memberType, name);
			currentFunction.addRegister(memberRegister);
			currentBlock.addInst(new LoadInst(memberRegister, memberAddress));
			node.setAddress(memberAddress);
			node.setResult(memberRegister);
		}
		else {
			IRRegister address = varSymbol.toIRAddress();
			IRRegister res = new IRRegister(((IRPtrType)address.getType()).getType(), node.getIdentifier());
			currentFunction.addRegister(res);
			currentBlock.addInst(new LoadInst(res, address));
			node.setResult(res);
			node.setAddress(address);
		}
	} 

	@Override
	public void visit(BoolLiteralNode node) {
		node.setResult(new IRConstBool(node.getValue()));
	}

	@Override
	public void visit(IntLiteralNode node) {
		node.setResult(new IRConstInt(node.getValue()));
	}

	@Override
	public void visit(StringLiteralNode node) {
	    IRGlobalString globalString = module.addGlobalString(node.getString());
	    globalString.getType();
	    IRRegister res = new IRRegister(new IRPtrType(new IRInt8Type()), "__stringLiteral");
	    currentFunction.addRegister(res);
	    currentBlock.addInst(new GetElementPtrInst(res, globalString, new IRConstInt(0), new IRConstInt(0)));
	    
		node.setResult(res);
	}

	@Override
	public void visit(NullLiteralNode node) {
		node.setResult(new IRNull());
	}
	

	@Override
	public void visit(PrimTypeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassTypeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayTypeNode node) {
		// TODO Auto-generated method stub
		
	}
}
