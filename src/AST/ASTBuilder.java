package AST;

import utility.*;
import parser.MxstarParser;
import parser.MxstarBaseVisitor;
import java.util.ArrayList;

public class ASTBuilder extends MxstarBaseVisitor<ASTNode> {
	
	public ASTBuilder() {;}
	
	@Override
	public ASTNode visitProgram(MxstarParser.ProgramContext ctx) {
		ArrayList<DefNode> defList = new ArrayList<DefNode>();
		for (MxstarParser.DefContext item : ctx.def()) {
			defList.add((DefNode)visit(item));
		}
		return new ProgramNode(new Location(ctx.getStart()), defList);
	}
	
	@Override
	public ASTNode visitDef(MxstarParser.DefContext ctx) {
		if (ctx.classDef() != null) 
			return visit(ctx.classDef());
		else if (ctx.functDef() != null) 
			return visit(ctx.functDef());
		else if (ctx.varDefStmt() != null) 
			return visit(ctx.varDefStmt());
		else 
			return null;
	}
	
	@Override
	public ASTNode visitVarDefStmt(MxstarParser.VarDefStmtContext ctx) {
		TypeNode type = (TypeNode)visit(ctx.type());
		ArrayList<VarDefNode> varList = new ArrayList<VarDefNode>();
		for (MxstarParser.VarDefContext item : ctx.varDef()) {
			VarDefNode tmp = (VarDefNode)visit(item);
			tmp.setType(type);
			varList.add(tmp);
		}
		return new VarDefStmtNode(new Location(ctx.getStart()), varList);
	}
	
	@Override
	public ASTNode visitVarDef(MxstarParser.VarDefContext ctx) {
		return new VarDefNode( new Location(ctx.getStart()), 
			null,
			ctx.Identifier().getText(),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()) 
		);
	}

	@Override
	public ASTNode visitFunctDef(MxstarParser.FunctDefContext ctx) {
		TypeNode type;
		if (ctx.VOID() == null) type = (TypeNode)visit(ctx.type());
		else type = (TypeNode)(new PrimTypeNode( new Location(ctx.type().getStart()), "void") );
		ArrayList<VarDefNode> paraList = new ArrayList<VarDefNode>();
		for (MxstarParser.ParaContext item : ctx.para()) {
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
		return new VarDefNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.type()),
			ctx.Identifier().getText(),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()) 
		);
	}
	
	@Override 
	public ASTNode visitClassDef(MxstarParser.ClassDefContext ctx) {
		ArrayList<VarDefStmtNode> varList = new ArrayList<VarDefStmtNode>();
		for (MxstarParser.VarDefStmtContext item : ctx.varDefStmt()) {
			varList.add((VarDefStmtNode)visit(item));
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
	
	@Override
	public ASTNode visitBlockStmt(MxstarParser.BlockStmtContext ctx) {
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.block().stmt()) {
			stmtList.add((StmtNode)visit(item));
		}
		return new BlockStmtNode( new Location(ctx.getStart()), stmtList);
	}
	
	@Override
	public ASTNode visitExprStmt(MxstarParser.ExprStmtContext ctx) {
		return new ExprStmtNode( new Location(ctx.getStart()), (ExprNode)visit(ctx.expr()) );
	}
	
	@Override
	public ASTNode visitVarStmt(MxstarParser.VarStmtContext ctx) {
		return visit(ctx.varDefStmt());
	}
	
	@Override
	public ASTNode visitIfStmt(MxstarParser.IfStmtContext ctx) {
		return new IfStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt(0)),
			ctx.stmt(1) == null ? null : (StmtNode)visit(ctx.stmt(1))
		);
	}
	
	@Override
	public ASTNode visitForStmt(MxstarParser.ForStmtContext ctx) {
		return new ForStmtNode( new Location(ctx.getStart()),
			ctx.init == null ? null : (ExprNode)visit(ctx.init),
			ctx.cond == null ? null : (ExprNode)visit(ctx.cond),
			ctx.step == null ? null : (ExprNode)visit(ctx.step),
			(StmtNode)visit(ctx.stmt())		
		);
	}
	
	@Override
	public ASTNode visitWhileStmt(MxstarParser.WhileStmtContext ctx) {
		return new WhileStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt())
		);
	}
	
	@Override
	public ASTNode visitReturnStmt(MxstarParser.ReturnStmtContext ctx) {
		return new ReturnStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr())
		);
	}
	
	@Override
	public ASTNode visitBreakStmt(MxstarParser.BreakStmtContext ctx) {
		return new BreakStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitContinueStmt(MxstarParser.ContinueStmtContext ctx) {
		return new ContinueStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitBrankStmt(MxstarParser.BrankStmtContext ctx) {
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
	/*
	@Override
	public ASTNode visitLiteralExpr(MxstarParser.LiteralExprContext ctx) {
		if (ctx.literal().BoolLiteral() != null) 
			return new BoolLiteralNode( new Location(ctx.getStart()), 
				ctx.literal().BoolLiteral().getText() == "true" ? true : false
			);
		else if (ctx.literal().StringLiteral() != null) 
			return new StringLiteralNode( new Location(ctx.getStart()),
				ctx.literal().StringLiteral().getText()
			);
		
	}*/
}
