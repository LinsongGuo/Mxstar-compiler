package AST;

import utility.*;
import parser.MxstarParser;
import parser.MxstarBaseVisitor;
import java.util.ArrayList;

public class ASTBuilder extends MxstarBaseVisitor<ASTNode> {
	
	public ASTBuilder() {;}
	
	@Override
	public ASTNode visitProgram(MxstarParser.ProgramContext ctx) {
		//System.out.println((new Location(ctx.getStart())).toString());
		System.out.println("visitProgram: " + ctx.getText());
		ArrayList<DefNode> defList = new ArrayList<DefNode>();
		for (MxstarParser.DefContext item : ctx.def()) {
			defList.add((DefNode)visit(item));
		}
		return new ProgramNode(new Location(ctx.getStart()), defList);
	}
	
	@Override
	public ASTNode visitDef(MxstarParser.DefContext ctx) {
		///System.out.println("visitDef: " + ctx.getText());
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
		System.out.println("visitVarDefList: " + ctx.getText());
		TypeNode type = (TypeNode)visit(ctx.type());
		ArrayList<VarDefNode> varList = new ArrayList<VarDefNode>();
		for (MxstarParser.VarDefContext item : ctx.varDef()) {
			VarDefNode tmp = (VarDefNode)visit(item);
			tmp.setType(type);
			varList.add(tmp);
		}
		return new VarDefListNode(new Location(ctx.getStart()), varList);
	}
	
	@Override
	public ASTNode visitVarDef(MxstarParser.VarDefContext ctx) {
		//System.out.println("visitVarDef: " + ctx.getText());
		return new VarDefNode( new Location(ctx.getStart()), 
			null,
			ctx.Identifier().getText(),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()) 
		);
	}
	
	@Override
	public ASTNode visitFunctDef(MxstarParser.FunctDefContext ctx) {
		System.out.println("visitFunctDef: " + ctx.getText());
		TypeNode type;
		
		if (ctx.VOID() == null) type = (TypeNode)visit(ctx.type());
		else {
			System.out.println("void");
			type = (TypeNode)(new PrimTypeNode( new Location(ctx.getStart()), "void") );
			System.out.println("end");
		}
		ArrayList<VarDefNode> paraList = new ArrayList<VarDefNode>();
		for (MxstarParser.ParaContext item : ctx.paraList().para()) {
			paraList.add((VarDefNode)visit(item));
		}
		return new FunctDefNode( new Location(ctx.getStart()),
			type,
			ctx.Identifier().getText(),
			paraList,
			(BlockStmtNode)visit(ctx.block())
		);
	}
	
	@Override 
	public ASTNode visitPara(MxstarParser.ParaContext ctx) {
		System.out.println("visitPara: " + ctx.getText());
		return new VarDefNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.type()),
			ctx.Identifier().getText(),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()) 
		);
	}
	
	@Override 
	public ASTNode visitClassDef(MxstarParser.ClassDefContext ctx) {
		System.out.println("visitClassDef: " + ctx.getText());
		ArrayList<VarDefListNode> varList = new ArrayList<VarDefListNode>();
		for (MxstarParser.VarDefListContext item : ctx.varDefList()) {
			varList.add((VarDefListNode)visit(item));
		}
		ArrayList<FunctDefNode> functList = new ArrayList<FunctDefNode>();
		for (MxstarParser.FunctDefContext item : ctx.functDef()) {
			functList.add((FunctDefNode)visit(item));
		}
		return new ClassDefNode( new Location(ctx.getStart()),
			ctx.Identifier().getText(),
			varList, functList
		);
	}
	
	/*
	@Override
	public ASTNode visitStmt(MxstarParser.StmtContext ctx) {
		System.out.println("visitStmt: " + ctx.getText());
		return visit(ctx.);
	}*/
	
	@Override
	public ASTNode visitBlock(MxstarParser.BlockContext ctx) {
		System.out.println("visitBlock: " + ctx.getText());
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.stmt()) {
			//System.out.println("item: " + item.getText());
			stmtList.add((StmtNode)visit(item));
		}
		return new BlockStmtNode( new Location(ctx.getStart()), stmtList);
	}
	
	@Override
	public ASTNode visitBlockStmt(MxstarParser.BlockStmtContext ctx) {
		System.out.println("visitBlockStmt: " + ctx.getText());
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.block().stmt()) {
			stmtList.add((StmtNode)visit(item));
		}
		return new BlockStmtNode( new Location(ctx.getStart()), stmtList);
	}
	
	@Override
	public ASTNode visitVarDefStmt(MxstarParser.VarDefStmtContext ctx) {
		System.out.println("visitVarDefStmt: " + ctx.getText());
		TypeNode type = (TypeNode)visit(ctx.varDefList().type());
		ArrayList<VarDefNode> varList = new ArrayList<VarDefNode>();
		for (MxstarParser.VarDefContext item : ctx.varDefList().varDef()) {
			VarDefNode tmp = (VarDefNode)visit(item);
			tmp.setType(type);
			varList.add(tmp);
		}
		return new VarDefStmtNode(new Location(ctx.getStart()), varList);
	}
	
	@Override
	public ASTNode visitIfStmt(MxstarParser.IfStmtContext ctx) {
		System.out.println("visitIfStmt: " + ctx.getText());
		return new IfStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt(0)),
			ctx.stmt(1) == null ? null : (StmtNode)visit(ctx.stmt(1))
		);
	}
	
	@Override
	public ASTNode visitForStmt(MxstarParser.ForStmtContext ctx) {
		System.out.println("visitForStmt: " + ctx.getText());
		return new ForStmtNode( new Location(ctx.getStart()),
			ctx.init == null ? null : (ExprNode)visit(ctx.init),
			ctx.cond == null ? null : (ExprNode)visit(ctx.cond),
			ctx.step == null ? null : (ExprNode)visit(ctx.step),
			(StmtNode)visit(ctx.stmt())		
		);
	}
	
	@Override
	public ASTNode visitWhileStmt(MxstarParser.WhileStmtContext ctx) {
		System.out.println("visitWhileStmt: " + ctx.getText());
		return new WhileStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt())
		);
	}
	
	@Override
	public ASTNode visitReturnStmt(MxstarParser.ReturnStmtContext ctx) {
		System.out.println("visitReturnStmt: " + ctx.getText());
		return new ReturnStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr())
		);
	}
	
	@Override
	public ASTNode visitBreakStmt(MxstarParser.BreakStmtContext ctx) {
		System.out.println("visitBreakStmt: " + ctx.getText());
		return new BreakStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitContinueStmt(MxstarParser.ContinueStmtContext ctx) {
		System.out.println("visitContinueStmt: " + ctx.getText());
		return new ContinueStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitExprStmt(MxstarParser.ExprStmtContext ctx) {
		System.out.println("visitExprStmt: " + ctx.getText());
		return new ExprStmtNode( new Location(ctx.getStart()), (ExprNode)visit(ctx.expr()) );
	}
	
	@Override
	public ASTNode visitBrankStmt(MxstarParser.BrankStmtContext ctx) {
		System.out.println("visitBrankStmt: " + ctx.getText());
		return new BrankStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitVarExpr(MxstarParser.VarExprContext ctx) {
		return new VarExprNode( new Location(ctx.getStart()), ctx.Identifier().getText() );
	}
	
	@Override
	public ASTNode visitThisExpr(MxstarParser.ThisExprContext ctx) {
		return new ThisExprNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitLiteralExpr(MxstarParser.LiteralExprContext ctx) {
		//System.out.println("visitLiteralExpr: " + ctx.getText());
		if (ctx.literal().BoolLiteral() != null) 
			return new BoolLiteralNode( new Location(ctx.getStart()), 
				ctx.literal().BoolLiteral().getText() == "true" ? true : false
			);
		else if (ctx.literal().StringLiteral() != null) 
			return new StringLiteralNode( new Location(ctx.getStart()),
				ctx.literal().StringLiteral().getText()
			);
		else if (ctx.literal().IntLiteral() != null) 
			return new IntLiteralNode( new Location(ctx.getStart()),
				Integer.parseInt(ctx.literal().IntLiteral().getText())   
			);
		else return null;
	}
	
	@Override
	public ASTNode visitMemberExpr(MxstarParser.MemberExprContext ctx) {
		return new MemberExprNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			ctx.Identifier().getText()
		);
	}
	
	@Override
	public ASTNode visitCreatorExpr(MxstarParser.CreatorExprContext ctx) {
		return visit(ctx.creator());
	}
	
	@Override 
	public ASTNode visitInvalidCreator(MxstarParser.InvalidCreatorContext ctx) {
		return null;
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
		return new CreatorExprNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.varType()),
			new ArrayList<ExprNode>(),
			0
		);
	}
	
	@Override
	public ASTNode visitArrayExpr(MxstarParser.ArrayExprContext ctx) {
		return new ArrayExprNode( new Location(ctx.getStart()), 
			(ExprNode)visit(ctx.expr(0)),
			(ExprNode)visit(ctx.expr(1))
		);	
	}
	
	@Override
	public ASTNode visitFunctExpr(MxstarParser.FunctExprContext ctx) {
		//System.out.println("visitFunctExpr: " + ctx.getText());
		ArrayList<ExprNode> paraList = new ArrayList<ExprNode>();
		for (MxstarParser.ExprContext item : ctx.exprList().expr()) {
			paraList.add((ExprNode)visit(item));
		}
		return new FunctExprNode( new Location(ctx.getStart()), 
			(ExprNode)visit(ctx.expr()),	
			paraList 
		);
	}
	
	@Override
	public ASTNode visitBracketExpr(MxstarParser.BracketExprContext ctx) {
		return new BracketExprNode( new Location(ctx.getStart()), (ExprNode)visit(ctx.expr()) );
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
		//System.out.println("visitBinaryExpr: " + ctx.getText());
		//System.out.println(ctx.op);
		//System.out.println(ctx.expr(0).getText());
		//System.out.println(ctx.expr(1).getText());
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
		System.out.println("visitType: " + ctx.getText());
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
		/*if (ctx.getText().equals("bool")) 
			return new PrimTypeNode( new Location(ctx.getStart()), "bool" );
		else if (ctx.getText().equals("int")) 
			return new PrimTypeNode( new Location(ctx.getStart()), "int" );
		else if (ctx.getText().equals("string"))
			return new PrimTypeNode( new Location(ctx.getStart()), "string" );
		else if (ctx.getText().equals("void"))
			return new PrimTypeNode( new Location(ctx.getStart()), "void" );
		else
			return null;
		*/
		return new PrimTypeNode( new Location(ctx.getStart()), ctx.getText() );
	}
	
	@Override
	public ASTNode visitArrayType(MxstarParser.ArrayTypeContext ctx) {
		int dimension = 0;
		for (var item : ctx.children) {
			if (item.getText().equals("[")) 
				dimension++;
		}
		return new ArrayTypeNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.varType()),
			dimension
		);
	}
	
}
