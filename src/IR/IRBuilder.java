package IR;

import java.util.ArrayList;

import AST.*;
import IR.Symbol.*;
import IR.Type.*;
import MxCompiler.AST.BinaryExprNode;
import MxCompiler.IR.Instruction.StoreInst;
import Scope.*;
import IR.Inst.*;
import IR.Inst.BinOpInst.BinOpType;
import IR.Inst.BitwiseBinOpInst.BitwiseBinOpType;
import IR.Inst.IcmpInst.IcmpOpType;
import utility.ErrorReminder;
import utility.Operator;

public class IRBuilder implements ASTVisitor {
	private ErrorReminder errorReminder;
	private IRFunction currentFunction;
	private IRBasicBlock currentBlock; 
	private IRModule module;
	
	public IRBuilder(ErrorReminder errorReminder) {
		this.errorReminder = errorReminder;
		this.currentFunction = null;
		this.currentBlock = null;
		this.module = new IRModule();
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

	@Override
	public void visit(ArrayExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BinaryExprNode node) {
		// TODO Auto-generated method stub
		Operator op = node.getOp();
		if (op == Operator.logicalAND) {
			
		}
		else if (op == Operator.logicalOR) {
			
		}
		else {
			ExprNode left = node.getLeft();
			ExprNode right = node.getRight();
			left.accept(this);
			right.accept(this);
			IRSymbol leftRes = left.getIRSymbol();
			IRSymbol rightRes = right.getIRSymbol();
			//binary operation instructions
			if (op == Operator.ADD) {
				Type type = node.getType();
				if (type instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt32Type(), "add");
					currentFunction.addRegister(res);
					BinOpInst inst = new BinOpInst(BinOpType.add, res, leftRes, rightRes);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (type instanceof StringType) {
					IRRegister res = new IRRegister(new IRPtrType(new IRInt8Type()), "add");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringAdd");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					CallInst inst = new CallInst(funct, parameters, res);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
			}
			else if (op == Operator.SUB) {
				IRRegister res = new IRRegister(new IRInt32Type(), "sub");
				currentFunction.addRegister(res);
				BinOpInst inst = new BinOpInst(BinOpType.sub, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			else if (op == Operator.MUL) {
				IRRegister res = new IRRegister(new IRInt32Type(), "mul");
				currentFunction.addRegister(res);
				BinOpInst inst = new BinOpInst(BinOpType.mul, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			else if (op == Operator.DIV) {
				IRRegister res = new IRRegister(new IRInt32Type(), "sdiv");
				currentFunction.addRegister(res);
				BinOpInst inst = new BinOpInst(BinOpType.sdiv, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			else if (op == Operator.MOD) {
				IRRegister res = new IRRegister(new IRInt32Type(), "srem");
				currentFunction.addRegister(res);
				BinOpInst inst = new BinOpInst(BinOpType.srem, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			//bitwise binary operation instructions
			else if (op == Operator.leftSHIFT) {
				IRRegister res = new IRRegister(new IRInt32Type(), "shl");
				currentFunction.addRegister(res);
				BitwiseBinOpInst inst = new BitwiseBinOpInst(BitwiseBinOpType.shl, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			else if (op == Operator.rightSHIFT) {
				IRRegister res = new IRRegister(new IRInt32Type(), "ashr");
				currentFunction.addRegister(res);
				BitwiseBinOpInst inst = new BitwiseBinOpInst(BitwiseBinOpType.ashr, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			else if (op == Operator.bitwiseAND) {
				IRRegister res = new IRRegister(new IRInt32Type(), "and");
				currentFunction.addRegister(res);
				BitwiseBinOpInst inst = new BitwiseBinOpInst(BitwiseBinOpType.and, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			else if (op == Operator.bitwiseOR) {
				IRRegister res = new IRRegister(new IRInt32Type(), "or");
				currentFunction.addRegister(res);
				BitwiseBinOpInst inst = new BitwiseBinOpInst(BitwiseBinOpType.or, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
			}
			else if (op == Operator.bitwiseXOR) {
				IRRegister res = new IRRegister(new IRInt32Type(), "xor");
				currentFunction.addRegister(res);
				BitwiseBinOpInst inst = new BitwiseBinOpInst(BitwiseBinOpType.xor, res, leftRes, rightRes);
				currentBlock.addInst(inst);
				node.setIRSymbol(res);
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
					IcmpInst inst = new IcmpInst(IcmpOpType.eq, res, leftRes, rightRes);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof StringType && rightType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "eq");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					CallInst inst = new CallInst(funct, parameters, res);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof NullType && rightType instanceof NullType) {
					node.setIRSymbol(new IRConstBool(true));
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
					IcmpInst inst = new IcmpInst(IcmpOpType.ne, res, leftRes, rightRes);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof StringType && rightType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "eq");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringNotEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					CallInst inst = new CallInst(funct, parameters, res);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof NullType && rightType instanceof NullType) {
					node.setIRSymbol(new IRConstBool(false));
				}
			}
			else if (op == Operator.LESS) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "slt");
					currentFunction.addRegister(res);
					IcmpInst inst = new IcmpInst(IcmpOpType.slt, res, leftRes, rightRes);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "slt");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringLess");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					CallInst inst = new CallInst(funct, parameters, res);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
			}
			else if (op == Operator.lessEQU) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sle");
					currentFunction.addRegister(res);
					IcmpInst inst = new IcmpInst(IcmpOpType.sle, res, leftRes, rightRes);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sle");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringLessEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					CallInst inst = new CallInst(funct, parameters, res);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
			}
			else if (op == Operator.GREATER) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sgt");
					currentFunction.addRegister(res);
					IcmpInst inst = new IcmpInst(IcmpOpType.sgt, res, leftRes, rightRes);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sgt");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringGreater");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					CallInst inst = new CallInst(funct, parameters, res);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
			}
			else if (op == Operator.greaterEQU) {
				Type leftType = node.getLeft().getType();
				if (leftType instanceof IntType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sge");
					currentFunction.addRegister(res);
					IcmpInst inst = new IcmpInst(IcmpOpType.sge, res, leftRes, rightRes);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
				else if (leftType instanceof StringType) {
					IRRegister res = new IRRegister(new IRInt1Type(), "sge");
					currentFunction.addRegister(res);
					IRFunction funct = module.getBuiltInFunct("__stringGreaterEqual");
					ArrayList<IRSymbol> parameters = new ArrayList<IRSymbol>();
					parameters.add(leftRes);
					parameters.add(rightRes);
					CallInst inst = new CallInst(funct, parameters, res);
					currentBlock.addInst(inst);
					node.setIRSymbol(res);
				}
			}
			else if (op == Operator.ASSIGN) {
				//
			}		
			else {
				System.err.println("error in visit(BinaryExprNode)!");
			}
		}
	}

	@Override
	public void visit(BracketExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CreatorExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MemberExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PrefixExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SuffixExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ThisExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarExprNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BlockStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BrankStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BreakStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ContinueStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExprStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ForStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IfStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ReturnStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDefStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhileStmtNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BoolLiteralNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntLiteralNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StringLiteralNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NullLiteralNode node) {
		// TODO Auto-generated method stub
		
	}

}
