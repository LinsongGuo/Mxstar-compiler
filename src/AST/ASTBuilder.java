package AST;

import parser.MxstarParser;
import parser.MxstarBaseVisitor;

import java.util.ArrayList;

import utility.Location;
import utility.Operator;
import utility.ErrorReminder;

public class ASTBuilder extends MxstarBaseVisitor<ASTNode> {
	
	private ErrorReminder errorReminder;
	
	public ASTBuilder(ErrorReminder errorReminder) {
		this.errorReminder = errorReminder;
	}
	
	@Override
	public ASTNode visitProgram(MxstarParser.ProgramContext ctx) {
		//System.err.println("visitProgram: " + ctx.getText());
		ArrayList<DefNode> defList = new ArrayList<DefNode>();
		for (MxstarParser.DefContext item : ctx.def()) {
			defList.add((DefNode)visit(item)); 
		}
		return new ProgramNode(new Location(ctx.getStart()), defList);
	}
	
	@Override
	public ASTNode visitDef(MxstarParser.DefContext ctx) {
		///System.err.println("visitDef: " + ctx.getText());
		if (ctx.classDef() != null) 
			return visit(ctx.classDef());
		else if (ctx.functDef() != null) 
			return visit(ctx.functDef());
		else if (ctx.varDefList() != null) 
			return visit(ctx.varDefList());
		else 
			return null;
	}
	
	@Override
	public ASTNode visitVarDefList(MxstarParser.VarDefListContext ctx) {
		//System.err.println("visitVarDefList: " + ctx.getText());
		TypeNode type = (TypeNode)visit(ctx.type());
		ArrayList<VarDefNode> varList = new ArrayList<VarDefNode>();
		for (MxstarParser.VarDefContext item : ctx.varDef()) {
			VarDefNode tmp = (VarDefNode)visit(item);
			if (tmp != null) {
				tmp.setType(type);
				varList.add(tmp);
			}
		}
		return new VarDefListNode(new Location(ctx.getStart()), type, varList);
	}
	
	@Override
	public ASTNode visitVarDef(MxstarParser.VarDefContext ctx) {
		//System.err.println("visitVarDef: " + ctx.getText());
		if (ctx.Identifier() == null) {
			errorReminder.error(new Location(ctx.getStart()), "invalid name of variable.");
			return null;
		}
		//System.err.println("visitVarDef " + ctx.getText() + " " + ctx.Identifier().getText());
		return new VarDefNode( new Location(ctx.getStart()), 
				null,
			ctx.Identifier().getText(),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()) 
		);
	}
	
	@Override
	public ASTNode visitFunctDef(MxstarParser.FunctDefContext ctx) {
		if (ctx.Identifier() == null) {
			errorReminder.error(new Location(ctx.getStart()), "invalid name of function.");
			return null;
		}
		//System.err.println("visitFunctDef: " + ctx.getText());
		ArrayList<VarDefNode> paraList = new ArrayList<VarDefNode>();
		for (MxstarParser.ParaContext item : ctx.paraList().para()) {
			paraList.add((VarDefNode)visit(item));
		}
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.stmt()) {
			stmtList.add((StmtNode)visit(item));
		}
		
		return new FunctDefNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.type()),
			ctx.Identifier().getText(),
			paraList,
			stmtList
		);
	}
	
	@Override 
	public ASTNode visitPara(MxstarParser.ParaContext ctx) {
		//System.err.println("visitPara: " + ctx.getText());
		return new VarDefNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.type()),
			ctx.Identifier().getText(),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()) 
		);
	}
	
	@Override 
	public ASTNode visitClassDef(MxstarParser.ClassDefContext ctx) {
		if (ctx.Identifier() == null) {
			errorReminder.error(new Location(ctx.getStart()), "invalid name of class.");
			return null;
		}
		//System.err.println("visitClassDef: " + ctx.getText());
		//variable in class.
		ArrayList<VarDefListNode> varList = new ArrayList<VarDefListNode>();
		for (MxstarParser.VarDefListContext item : ctx.varDefList()) {
			varList.add((VarDefListNode)visit(item));
		}
		//functions in class.
		String identifier = ctx.Identifier().getText();
		ArrayList<FunctDefNode> functList = new ArrayList<FunctDefNode>();
		for (MxstarParser.FunctDefContext item : ctx.functDef()) {
			if (item.Identifier().getText().equals(identifier)) {
				errorReminder.error( new Location(item.getStart()), 
					"return type specification for constructor invalid."
				);
				continue;
			}
			functList.add((FunctDefNode)visit(item));
		}
		//constructor of class.
		FunctDefNode constructorDef = null;
		int count = 0;
		for (MxstarParser.ConstructorDefContext item : ctx.constructorDef()) {
			if (!item.Identifier().getText().equals(identifier)) {
				errorReminder.error(new Location(item.getStart()), 
					"mismatched constructor name."
				);
			}
			else {
				count++;
				if (count > 1) {
					errorReminder.error(new Location(item.getStart()), 
						"the constructor cannot be overloaded."
					);
				}
				else {
					constructorDef = (FunctDefNode) visit(item);
				}
			}
		}
		return new ClassDefNode( new Location(ctx.getStart()),
			ctx.Identifier().getText(),
			varList, 
			functList, 
			constructorDef
		);
	}
	
	@Override
	public ASTNode visitConstructorDef(MxstarParser.ConstructorDefContext ctx) {
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.stmt()) {
			stmtList.add((StmtNode)visit(item));
		}
		return new FunctDefNode( new Location(ctx.getStart()),
			null,
			ctx.Identifier().getText(),
			new ArrayList<VarDefNode>(),
			stmtList
		);
	}
	
	@Override
	public ASTNode visitBlock(MxstarParser.BlockContext ctx) {
		//System.err.println("visitBlock: " + ctx.getText());
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.stmt()) {
			stmtList.add((StmtNode)visit(item));
		}
		return new BlockStmtNode( new Location(ctx.getStart()), stmtList);
	}
	
	@Override
	public ASTNode visitBlockStmt(MxstarParser.BlockStmtContext ctx) {
		//System.err.println("visitBlockStmt: " + ctx.getText());
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.block().stmt()) {
			stmtList.add((StmtNode)visit(item));
		}
		return new BlockStmtNode( new Location(ctx.getStart()), stmtList);
	}
	
	@Override
	public ASTNode visitVarDefStmt(MxstarParser.VarDefStmtContext ctx) {
		//System.err.println("visitVarDefStmt: " + ctx.getText());
		TypeNode type = (TypeNode)visit(ctx.varDefList().type());
		ArrayList<VarDefNode> varList = new ArrayList<VarDefNode>();
		if (ctx.varDefList() != null) {
			for (MxstarParser.VarDefContext item : ctx.varDefList().varDef()) {
				VarDefNode tmp = (VarDefNode)visit(item);
				if (tmp != null) {
					tmp.setType(type);
					varList.add(tmp);
				}
			}
		}
		return new VarDefStmtNode(new Location(ctx.getStart()),
			new VarDefListNode(new Location(ctx.getStart()), type, varList) 
		);
	}
		
	@Override
	public ASTNode visitIfStmt(MxstarParser.IfStmtContext ctx) {
		//System.err.println("visitIfStmt: " + ctx.getText());
		return new IfStmtNode( new Location(ctx.getStart()),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt(0)),
			ctx.stmt(1) == null ? null : (StmtNode)visit(ctx.stmt(1))
		);
	}
	
	@Override
	public ASTNode visitForStmt(MxstarParser.ForStmtContext ctx) {
		//System.err.println("visitForStmt: " + ctx.getText());
		return new ForStmtNode( new Location(ctx.getStart()),
			ctx.init == null ? null : (ExprNode)visit(ctx.init),
			ctx.cond == null ? null : (ExprNode)visit(ctx.cond),
			ctx.step == null ? null : (ExprNode)visit(ctx.step),
			(StmtNode)visit(ctx.stmt())		
		);
	}
	
	@Override
	public ASTNode visitWhileStmt(MxstarParser.WhileStmtContext ctx) {
		//System.err.println("visitWhileStmt: " + ctx.getText());
		return new WhileStmtNode( new Location(ctx.getStart()),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt())
		);
	}
	
	@Override
	public ASTNode visitReturnStmt(MxstarParser.ReturnStmtContext ctx) {
		//System.err.println("visitReturnStmt: " + ctx.getText());
		if (ctx.expr() == null) 
			return new ReturnStmtNode( new Location(ctx.getStart()), null );
		else 
			return new ReturnStmtNode( new Location(ctx.getStart()),
				(ExprNode)visit(ctx.expr())
			);
	}
	
	@Override
	public ASTNode visitBreakStmt(MxstarParser.BreakStmtContext ctx) {
		//System.err.println("visitBreakStmt: " + ctx.getText());
		return new BreakStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitContinueStmt(MxstarParser.ContinueStmtContext ctx) {
		///System.err.println("visitContinueStmt: " + ctx.getText());
		return new ContinueStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitExprStmt(MxstarParser.ExprStmtContext ctx) {
		//System.err.println("visitExprStmt: " + ctx.getText());
		return new ExprStmtNode( new Location(ctx.getStart()), (ExprNode)visit(ctx.expr()) );
	}
	
	@Override
	public ASTNode visitBrankStmt(MxstarParser.BrankStmtContext ctx) {
		//System.err.println("visitBrankStmt: " + ctx.getText());
		return new BrankStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitVarExpr(MxstarParser.VarExprContext ctx) {
		//System.err.println("visitVarExpr: " + ctx.getText() + (new Location(ctx.getStart())));
		return new VarExprNode( new Location(ctx.getStart()), ctx.Identifier().getText() );
	}
	
	@Override
	public ASTNode visitThisExpr(MxstarParser.ThisExprContext ctx) {
		return new ThisExprNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitLiteralExpr(MxstarParser.LiteralExprContext ctx) {
		//System.err.println("visitLiteralExpr: " + ctx.getText());
		if (ctx.literal().BoolLiteral() != null) 
			return new BoolLiteralNode( new Location(ctx.getStart()), 
				ctx.literal().BoolLiteral().getText() == "true" ? true : false
			);
		else if (ctx.literal().StringLiteral() != null) {
			String value = ctx.literal().StringLiteral().getText();
			return new StringLiteralNode( new Location(ctx.getStart()),
				value.substring(1, value.length() - 1)
			);
		}	
		else if (ctx.literal().IntLiteral() != null) 
			return new IntLiteralNode( new Location(ctx.getStart()),
				Long.parseLong(ctx.literal().IntLiteral().getText())   
			);
		else return new NullLiteralNode(new Location(ctx.getStart()));
	}
	
	@Override
	public ASTNode visitMemberExpr(MxstarParser.MemberExprContext ctx) {
		//System.err.println("visitMemberExpr: " + ctx.getText());
		return new MemberExprNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			ctx.Identifier().getText(),
			new VarExprNode(new Location(ctx.getStart()), ctx.Identifier().getText())
		);
	}
	
	@Override
	public ASTNode visitCreatorExpr(MxstarParser.CreatorExprContext ctx) {
		//System.err.println("visitCreator " + ctx.getText());
		return visit(ctx.creator());
	}
	
	@Override 
	public ASTNode visitInvalidCreator(MxstarParser.InvalidCreatorContext ctx) {
		System.err.println("visitInvalidCreator " + ctx.getText());
		errorReminder.error( new Location(ctx.getStart()), "invalid creator.");
		int dimension = 0;
		for (var item: ctx.children) {
			if (item.getText().equals("[")) 
				dimension++;
		}
		ArrayList<ExprNode> sizeList = new ArrayList<ExprNode>();
		for (MxstarParser.ExprContext item : ctx.expr()) {
			sizeList.add((ExprNode)visit(item));
		}
		return new CreatorExprNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.varType()),
			sizeList, 
			dimension
		);
	}
	
	@Override 
	public ASTNode visitArrayCreator(MxstarParser.ArrayCreatorContext ctx) {
		int dimension = 0;
		for (var item: ctx.children) {
			if (item.getText().equals("[")) 
				dimension++;
		}
		ArrayList<ExprNode> sizeList = new ArrayList<ExprNode>();
		for (MxstarParser.ExprContext item : ctx.expr()) {
			sizeList.add((ExprNode)visit(item));
		}
		return new CreatorExprNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.varType()),
			sizeList, 
			dimension
		);
	}
	
	@Override
	public ASTNode visitClassCreator(MxstarParser.ClassCreatorContext ctx) {
		return new CreatorExprNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.varType()),
			new ArrayList<ExprNode>(),
			0
		);
	}
	
	@Override
	public ASTNode visitNaiveCreator(MxstarParser.NaiveCreatorContext ctx) {
		//System.err.println("visitNaiveCreator " + ctx.getText() + " " + ctx.varType().getText().toString());
		return new CreatorExprNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.varType()),
			new ArrayList<ExprNode>(),
			0
		);
	}
	
	@Override
	public ASTNode visitArrayExpr(MxstarParser.ArrayExprContext ctx) {
		//System.err.println("visitArrayExpr: " + ctx.getText());
		return new ArrayExprNode( new Location(ctx.getStart()), 
			(ExprNode)visit(ctx.expr(0)), 
			(ExprNode)visit(ctx.expr(1))
		);
	}
	
	@Override
	public ASTNode visitFunctExpr(MxstarParser.FunctExprContext ctx) {
		//System.err.println("visitFunctExpr: " + ctx.getText());
		ArrayList<ExprNode> paraList = new ArrayList<ExprNode>();
		if (ctx.exprList() != null) {
			for (MxstarParser.ExprContext item : ctx.exprList().expr()) {
				paraList.add((ExprNode)visit(item));
			}
		}
		return new FunctExprNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()), 
			paraList
		);
	}
	
	@Override
	public ASTNode visitBracketExpr(MxstarParser.BracketExprContext ctx) {
		//System.err.println("visitBracketExpr: " + ctx.getText());
		return new BracketExprNode(new Location(ctx.getStart()), (ExprNode)visit(ctx.expr()) );
	}
	
	@Override
	public ASTNode visitSuffixExpr(MxstarParser.SuffixExprContext ctx) {
		Operator op;
		String tmp = ctx.op.getText();
		switch (tmp) {
		case "++":
			op = Operator.suffixINCR;
			break;
		case "--":
			op = Operator.suffixDECR;
			break;
		default:
			op = Operator.INVALID;
		}
		return new SuffixExprNode( new Location(ctx.getStart()),
			op, 
			(ExprNode)visit(ctx.expr())
		);
	}
	
	@Override
	public ASTNode visitPrefixExpr(MxstarParser.PrefixExprContext ctx) {
		Operator op;
		String tmp = ctx.op.getText();
		switch (tmp) {
		case "+":
			op = Operator.POS;
			break;
		case "-":
			op = Operator.NEG;
			break;
		case "++":
			op = Operator.prefixINCR;
			break;
		case "--":
			op = Operator.prefixDECR;
			break;
		case "~":
			op = Operator.bitwiseNOT;
			break;
		case "!":
			op = Operator.logicalNOT;
			break;
		default:
			op = Operator.INVALID;
		}
		return new PrefixExprNode( new Location(ctx.getStart()),
			op,
			(ExprNode)visit(ctx.expr())
		);
	}
	
	@Override
	public ASTNode visitBinaryExpr(MxstarParser.BinaryExprContext ctx) {
		//System.err.println("visitBinaryExpr: " + ctx.getText());
		Operator op;
		String tmp = ctx.op.getText();
		switch(tmp) {
		case "*":
			op = Operator.MUL;
			break;
		case "/":
			op = Operator.DIV;
			break;
		case "%":
			op = Operator.MOD;
			break;
		case "+":
			op = Operator.ADD;
			break;
		case "-":
			op = Operator.SUB;
			break;
		case "<<":
			op = Operator.leftSHIFT;
			break;
		case ">>":
			op = Operator.rightSHIFT;
			break;
		case "<":
			op = Operator.LESS;
			break;
		case ">":
			op = Operator.GREATER;
			break;
		case "<=":
			op = Operator.lessEQU;
			break;
		case ">=":
			op = Operator.greaterEQU;
			break;
		case "==":
			op = Operator.EQU;
			break;
		case "!=":
			op = Operator.notEQU;
			break;
		case "&":
			op = Operator.bitwiseAND;
			break;
		case "^":
			op = Operator.bitwiseXOR;
			break;
		case "|":
			op = Operator.bitwiseOR;
			break;
		case "&&":
			op = Operator.logicalAND;
			break;
		case "||":
			op = Operator.logicalOR;
			break;
		case "=":
			op = Operator.ASSIGN;
			break;
		default:
			op = Operator.INVALID;
		}
		return new BinaryExprNode( new Location(ctx.getStart()),
			op,
			(ExprNode)visit(ctx.expr(0)),
			(ExprNode)visit(ctx.expr(1))
		);
	}
	
	@Override
	public ASTNode visitType(MxstarParser.TypeContext ctx) {
		//System.err.println("visitType: " + ctx.getText());
		if (ctx.varType() != null)
			return visit(ctx.varType());
		else if (ctx.arrayType() != null)
			return visit(ctx.arrayType());
		else 
			return null;
	}
	
	@Override
	public ASTNode visitVarType(MxstarParser.VarTypeContext ctx) {
		if (ctx.primType() != null)
			return visit(ctx.primType());
		else if (ctx.Identifier() != null)
			return new ClassTypeNode( new Location(ctx.getStart()), ctx.Identifier().getText() );
		else 
			return null;
	}
	
	@Override
	public ASTNode visitPrimType(MxstarParser.PrimTypeContext ctx) {
		return new PrimTypeNode( new Location(ctx.getStart()), ctx.getText() );
	}
	
	@Override
	public ASTNode visitArrayType(MxstarParser.ArrayTypeContext ctx) {
		int dimension = 0;
		for (var item : ctx.children) {
			if (item.getText().equals("[")) 
				dimension++;
		}
		TypeNode tmp = (TypeNode)visit(ctx.varType());
		return new ArrayTypeNode( new Location(ctx.getStart()),
			tmp.toString(),
			dimension
		);
	}
	
}
