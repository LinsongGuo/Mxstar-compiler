package IR;

import java.util.ArrayList;

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
	private Scope globalScope;
	private ErrorReminder errorReminder;
	private IRFunction currentFunction;
	private IRBasicBlock currentBlock; 
	private IRModule module;
	private IRBasicBlock afterLoop, nextLoop;
	private IRRegister returnAddress;
	
	public IRBuilder(Scope globalScope, ErrorReminder errorReminder) {
		this.globalScope = globalScope;
		this.errorReminder = errorReminder;
		this.currentFunction = null;
		this.currentBlock = null;
		this.module = new IRModule();
		afterLoop = nextLoop = null;
	}
	
	@Override
	public void visit(ProgramNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDefListNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctDefNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassDefNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDefNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDefStmtNode node) {
		// TODO Auto-generated method stub
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
		
		node.getInitExpr().accept(this);
		node.getCondExpr().accept(this);
		currentFunction.addBasicBlock(forCondBlock);
		currentBlock = forCondBlock;
		currentBlock.addInst(new BrInst(currentBlock, node.getCondExpr().getResult(), forBodyBlock, afterForBlock));
		
		currentFunction.addBasicBlock(forBodyBlock);
		currentBlock = forBodyBlock;
		IRBasicBlock tmpNextLoop = nextLoop, tmpAfterLoop = afterLoop;
		nextLoop = forCondBlock; 
		afterLoop = afterForBlock;
		node.getStmt().accept(this);
		nextLoop = tmpNextLoop;
		afterLoop = tmpAfterLoop;
		currentBlock.addInst(new BrInst(currentBlock, forStepBlock));
		
		currentFunction.addBasicBlock(forStepBlock);
		currentBlock = forStepBlock;
		node.getStepExpr().accept(this);
		currentBlock.addInst(new BrInst(currentBlock, forCondBlock));
		
		currentFunction.addBasicBlock(afterForBlock);
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
		
		condExpr.accept(this);
		currentFunction.addBasicBlock(ifCondBlock);
		currentBlock = ifCondBlock;
		currentBlock.addInst(new BrInst(currentBlock, condExpr.getResult(), thenBodyBlock, elseStmt == null ? afterIfBlock : elseBodyBlock));
		
		currentFunction.addBasicBlock(thenBodyBlock);
		currentBlock = thenBodyBlock;
		thenStmt.accept(this);
		currentBlock.addInst(new BrInst(currentBlock, afterIfBlock));
		
		if (elseStmt != null) {
			currentFunction.addBasicBlock(elseBodyBlock);
			currentBlock = elseBodyBlock;
			elseStmt.accept(this);
			currentBlock.addInst(new BrInst(currentBlock, afterIfBlock));
		}
		
		currentFunction.addBasicBlock(afterIfBlock);
		currentBlock = afterIfBlock;	
	}

	@Override
	public void visit(WhileStmtNode node) {
		IRBasicBlock whileCondBlock = new IRBasicBlock("whileCondBlock");
		IRBasicBlock whileBodyBlock = new IRBasicBlock("whileBodyBlock");
		IRBasicBlock afterWhileBlock = new IRBasicBlock("afterWhileBlock");
		
		ExprNode condExpr = node.getExpr();
		condExpr.accept(this);
		currentFunction.addBasicBlock(whileCondBlock);
		currentBlock = whileCondBlock;
		currentBlock.addInst(new BrInst(currentBlock, condExpr.getResult(), whileBodyBlock, afterWhileBlock));
		
		currentFunction.addBasicBlock(whileBodyBlock);
		currentBlock = whileBodyBlock;
		IRBasicBlock tmpNextLoop = nextLoop, tmpAfterLoop = afterLoop;
		nextLoop = whileCondBlock;
		afterLoop = afterWhileBlock;
		node.getStmt().accept(this);
		nextLoop = tmpNextLoop;
		afterLoop = tmpAfterLoop;
		currentBlock.addInst(new BrInst(currentBlock, whileCondBlock));
		
		currentFunction.addBasicBlock(afterWhileBlock);
		currentBlock = afterWhileBlock;
	}

	@Override
	public void visit(BrankStmtNode node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(BreakStmtNode node) {
		currentBlock.addInst(new BrInst(currentBlock, afterLoop));
	}

	@Override
	public void visit(ContinueStmtNode node) {
		currentBlock.addInst(new BrInst(currentBlock, nextLoop));
	}

	@Override
	public void visit(ExprStmtNode node) {
		node.getExpr().accept(this);	
	}

	@Override
	public void visit(ReturnStmtNode node) {
		ExprNode returnExpr = node.getExpr();
		returnExpr.accept(this);
		currentBlock.addInst(new StoreInst(returnExpr.getResult(), returnAddress));
		
		IRBasicBlock returnBlock = new IRBasicBlock("returnBlock");
		currentBlock.addInst(new BrInst(currentBlock, returnBlock));
		currentFunction.addBasicBlock(returnBlock);
		currentBlock = returnBlock;
		
		IRType returnType = currentFunction.getType();
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

	@Override
	public void visit(BinaryExprNode node) {
		// TODO Auto-generated method stub
		Operator op = node.getOp();
		ExprNode left = node.getLeft();
		ExprNode right = node.getRight();
		if (op == Operator.logicalAND) {
			IRBasicBlock logicalAnd = new IRBasicBlock("logicalAnd");
			currentFunction.addBasicBlock(logicalAnd);
			IRBasicBlock afterLogicalAnd = new IRBasicBlock("afterLogicalAnd");
			currentFunction.addBasicBlock(afterLogicalAnd);
			IRBasicBlock tmp = currentBlock;
			
			left.accept(this);
			IRSymbol leftRes = left.getResult();
			currentBlock.addInst(new BrInst(currentBlock, leftRes, logicalAnd, afterLogicalAnd));
			
			currentBlock = logicalAnd;
			right.accept(this);
			IRSymbol rightRes = left.getResult();
			currentBlock.addInst(new BrInst(currentBlock, afterLogicalAnd));
			
			currentBlock = afterLogicalAnd;
			IRRegister res = new IRRegister(new IRInt1Type(), "phi");
			PhiInst phiInst = new PhiInst(res);
			phiInst.addBranch(new IRConstBool(false), tmp);
			phiInst.addBranch(rightRes, logicalAnd);
			currentBlock.addInst(phiInst);
		}
		else if (op == Operator.logicalOR) {
			IRBasicBlock logicalOr = new IRBasicBlock("logicalOr");
			currentFunction.addBasicBlock(logicalOr);
			IRBasicBlock afterLogicalOr = new IRBasicBlock("afterLogicalOr");
			currentFunction.addBasicBlock(afterLogicalOr);
			IRBasicBlock tmp = currentBlock;
			
			left.accept(this);
			IRSymbol leftRes = left.getResult();
			currentBlock.addInst(new BrInst(currentBlock, leftRes, afterLogicalOr, logicalOr));
			
			currentBlock = logicalOr;
			right.accept(this);
			IRSymbol rightRes = left.getResult();
			currentBlock.addInst(new BrInst(currentBlock, afterLogicalOr));
			
			currentBlock = afterLogicalOr;
			IRRegister res = new IRRegister(new IRInt1Type(), "phi");
			PhiInst phiInst = new PhiInst(res);
			phiInst.addBranch(new IRConstBool(true), tmp);
			phiInst.addBranch(rightRes, logicalOr);
			currentBlock.addInst(phiInst);
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
				if ( (leftType instanceof IntType && rightType instanceof IntType)       ||   
				     (leftType instanceof BoolType && rightType instanceof BoolType)     ||    
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
				if ( (leftType instanceof IntType && rightType instanceof IntType)       ||   
				     (leftType instanceof BoolType && rightType instanceof BoolType)     ||    
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
			}	/*
			else {
				System.err.println("error in visit(BinaryExprNode)!");
			}*/
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
			if (exprRes instanceof IRConstInt) {
				node.setResult(new IRConstInt(-((IRConstInt) exprRes).getValue()));
			}
			else {
				IRRegister res = new IRRegister(new IRInt32Type(), "neg");
				currentFunction.addRegister(res);
				currentBlock.addInst(new BinOpInst(BinOpType.sub, res, new IRConstInt(0), exprRes));
				node.setResult(res);
			}
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
		}/*
		else {
			System.err.println("error in visit(PrefixExprNode)");
		}*/
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

	@Override
	public void visit(CreatorExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctExprNode node) {
		ExprNode nameExpr = node.getNameExpr();
		if (nameExpr instanceof MemberExprNode) {
			nameExpr.accept(this);
			node.setResult(nameExpr.getResult());
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
	}

	@Override
	public void visit(ArrayExprNode node) {
		ExprNode nameExpr = node.getNameExpr();
		if (nameExpr instanceof MemberExprNode) {
			nameExpr.accept(this);
			node.setResult(nameExpr.getResult());
		}
		else if (nameExpr instanceof ArrayExprNode) {
			ExprNode indexExpr = node.getIndexExpr();
			indexExpr.accept(this);
			
			IRRegister array = (IRRegister) nameExpr.getResult();
			IRPtrType arrayType = (IRPtrType) array.getType();
			
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
		else if (nameExpr instanceof VarExprNode){
			ExprNode indexExpr = node.getIndexExpr();
			indexExpr.accept(this);
			
			VarSymbol arraySymbol = (VarSymbol) ((VarExprNode) nameExpr).getSymbol();
			IRRegister arrayAddress = arraySymbol.toIRAddress();
			IRPtrType arrayType = (IRPtrType) ((IRPtrType) arrayAddress.getType()).getType();
			
			//load --> array
			IRRegister array = new IRRegister(arrayType, ((VarExprNode) nameExpr).getIdentifier());
			currentFunction.addRegister(array);
			currentBlock.addInst(new LoadInst(arrayAddress, array));
			
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
			 IRRegister memberAddress = new IRRegister(memberAddressType, base.getName() + "." + identifier + "$");
			 currentFunction.addRegister(memberAddress);
			 currentBlock.addInst(
				 new GetElementPtrInst(memberAddress, base, new IRConstInt(0), new IRConstInt(classSymbol.order(identifier)))
			 );
			 
			 //load --> member
			 IRRegister member = new IRRegister(memberAddressType.getType(), base.getName() + "." + identifier);
			 currentFunction.addRegister(member);
			 currentBlock.addInst(new LoadInst(member, memberAddress));
			 
			 node.setResult(member);
			 node.setAddress(memberAddress);
		}
		else if (memberExpr instanceof FunctExprNode) {
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
			currentFunction.addRegister(member);
			currentBlock.addInst(new CallInst(function, parametersRes, member));
		
			node.setResult(member);
		}
		else if (memberExpr instanceof ArrayExprNode) {
			ExprNode indexExpr = ((ArrayExprNode) memberExpr).getIndexExpr();
			indexExpr.accept(this);
			
			VarSymbol arraySymbol = (VarSymbol) node.getSymbol();
			IRPtrType arrayAddressType = (IRPtrType) arraySymbol.toIRAddress().getType();
			IRRegister base = (IRRegister) nameExpr.getResult();
			
			//getelementptr --> arrayAddress
			IRRegister arrayAddress = new IRRegister(arrayAddressType, identifier + "$");
			currentFunction.addRegister(arrayAddress);
			currentBlock.addInst(new GetElementPtrInst(arrayAddress, base, new IRConstInt(0), new IRConstInt(classSymbol.order(identifier))));
		
			//load --> array
			IRRegister array = new IRRegister(arrayAddressType.getType(), identifier);
			currentFunction.addRegister(array);
			currentBlock.addInst(new LoadInst(array, arrayAddress));
			
			//getelementptr --> elementAddress
			IRRegister elementAddress = new IRRegister(arrayAddressType.getType(), "element$");
			currentFunction.addRegister(elementAddress);
			currentBlock.addInst(new GetElementPtrInst(elementAddress, array, indexExpr.getResult()));
			
			//load --> element
			IRRegister element = new IRRegister(((IRPtrType) arrayAddressType.getType()).getType(), "element");
			currentFunction.addRegister(element);
			currentBlock.addInst(new LoadInst(element, elementAddress));
			
			node.setResult(element);
			node.setAddress(elementAddress);
		}
	}

	
	@Override
	public void visit(ThisExprNode node) {
		IRRegister thisAddress = currentFunction.getRegister("this$");
		//assert thisAddress.getType() instanceof IRPtrType;
		IRRegister thisRegister = new IRRegister(((IRPtrType)thisAddress.getType()).getType(), "this");
		currentFunction.addRegister(thisRegister);
		currentBlock.addInst(new LoadInst(thisRegister, thisAddress));
		node.setResult(thisRegister);
	}

	@Override
	public void visit(VarExprNode node) {
		IRRegister address = node.getScope().resolveVar(node, errorReminder).toIRAddress();
		IRRegister res = new IRRegister(((IRPtrType)address.getType()).getType(), node.getIdentifier());
		currentFunction.addRegister(res);
		currentBlock.addInst(new LoadInst(res, address));
		node.setResult(res);
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
		node.setResult(new IRConstString(node.getString()));
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
