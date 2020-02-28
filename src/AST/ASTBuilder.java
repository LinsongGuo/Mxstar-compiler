package AST;

import parser.MxstarParser;
import parser.MxstarBaseVisitor;

import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;

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
		//System.err.println((new Location(ctx.getStart())).toString());
		System.err.println("visitProgram: " + ctx.getText());
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
		System.err.println("visitVarDefList: " + ctx.getText());
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
		System.err.println("visitVarDef: " + ctx.getText());
		return new VarDefNode( new Location(ctx.getStart()), 
			null,
			ctx.Identifier().getText(),
			ctx.expr() == null ? null : (ExprNode)visit(ctx.expr()) 
		);
	}
	
	@Override
	public ASTNode visitFunctDef(MxstarParser.FunctDefContext ctx) {
		System.err.println("visitFunctDef: " + ctx.getText());
		ArrayList<VarDefNode> paraList = new ArrayList<VarDefNode>();
		for (MxstarParser.ParaContext item : ctx.paraList().para()) {
			paraList.add((VarDefNode)visit(item));
		}
		return new FunctDefNode( new Location(ctx.getStart()),
			(TypeNode)visit(ctx.type()),
			ctx.Identifier().getText(),
			paraList,
			(BlockStmtNode)visit(ctx.block())
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
		System.err.println("visitClassDef: " + ctx.getText());
		ArrayList<VarDefListNode> varList = new ArrayList<VarDefListNode>();
		for (MxstarParser.VarDefListContext item : ctx.varDefList()) {
			varList.add((VarDefListNode)visit(item));
		}
		String identifier = ctx.Identifier().getText();
		ArrayList<FunctDefNode> functList = new ArrayList<FunctDefNode>();
		for (MxstarParser.FunctDefContext item : ctx.functDef()) {
			if (item.Identifier().getText().equals(identifier)) {
				errorReminder.error( new Location(item.getStart()), 
					"The constructor \"" + identifier + "()\"has return type."
				);
				continue;
			}
			functList.add((FunctDefNode)visit(item));
		}
		FunctDefNode constructorDef = null;
		int count = 0;
		for (MxstarParser.ConstructorDefContext item : ctx.constructorDef()) {
			if (item.Identifier().getText() != identifier) {
				errorReminder.error(new Location(item.getStart()), 
					"The function \"" + item.Identifier().getText() + "()\" has no return type."
				);
			}
			else {
				count++;
				if (count > 1) {
					errorReminder.error(new Location(item.getStart()), 
						"The class \"" + identifier + "\" has several constructors."
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
		System.err.println("visitConstructorDef: " + ctx.getText());
		return new FunctDefNode( new Location(ctx.getStart()),
			null,
			ctx.Identifier().getText(),
			new ArrayList<VarDefNode>(),
			(BlockStmtNode)visit(ctx.block())
		);
	}
	
	@Override
	public ASTNode visitBlock(MxstarParser.BlockContext ctx) {
		System.err.println("visitBlock: " + ctx.getText());
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.stmt()) {
			//System.err.println("item: " + item.getText());
			stmtList.add((StmtNode)visit(item));
		}
		return new BlockStmtNode( new Location(ctx.getStart()), stmtList);
	}
	
	@Override
	public ASTNode visitBlockStmt(MxstarParser.BlockStmtContext ctx) {
		System.err.println("visitBlockStmt: " + ctx.getText());
		ArrayList<StmtNode> stmtList = new ArrayList<StmtNode>();
		for (MxstarParser.StmtContext item : ctx.block().stmt()) {
			stmtList.add((StmtNode)visit(item));
		}
		return new BlockStmtNode( new Location(ctx.getStart()), stmtList);
	}
	
	@Override
	public ASTNode visitVarDefStmt(MxstarParser.VarDefStmtContext ctx) {
		System.err.println("visitVarDefStmt: " + ctx.getText());
		TypeNode type = (TypeNode)visit(ctx.varDefList().type());
		ArrayList<VarDefNode> varList = new ArrayList<VarDefNode>();
		for (MxstarParser.VarDefContext item : ctx.varDefList().varDef()) {
			VarDefNode tmp = (VarDefNode)visit(item);
			tmp.setType(type);
			varList.add(tmp);
		}
		return new VarDefStmtNode(new Location(ctx.getStart()), 
			new VarDefListNode(new Location(ctx.getStart()), varList) 
		);
	}
	
	@Override
	public ASTNode visitIfStmt(MxstarParser.IfStmtContext ctx) {
		System.err.println("visitIfStmt: " + ctx.getText());
		return new IfStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt(0)),
			ctx.stmt(1) == null ? null : (StmtNode)visit(ctx.stmt(1))
		);
	}
	
	@Override
	public ASTNode visitForStmt(MxstarParser.ForStmtContext ctx) {
		System.err.println("visitForStmt: " + ctx.getText());
		System.err.println("init: " + ctx.init.getText());
		return new ForStmtNode( new Location(ctx.getStart()),
			ctx.init == null ? null : (ExprNode)visit(ctx.init),
			ctx.cond == null ? null : (ExprNode)visit(ctx.cond),
			ctx.step == null ? null : (ExprNode)visit(ctx.step),
			(StmtNode)visit(ctx.stmt())		
		);
	}
	
	@Override
	public ASTNode visitWhileStmt(MxstarParser.WhileStmtContext ctx) {
		System.err.println("visitWhileStmt: " + ctx.getText());
		return new WhileStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr()),
			(StmtNode)visit(ctx.stmt())
		);
	}
	
	@Override
	public ASTNode visitReturnStmt(MxstarParser.ReturnStmtContext ctx) {
		System.err.println("visitReturnStmt: " + ctx.getText());
		return new ReturnStmtNode( new Location(ctx.getStart()),
			(ExprNode)visit(ctx.expr())
		);
	}
	
	@Override
	public ASTNode visitBreakStmt(MxstarParser.BreakStmtContext ctx) {
		System.err.println("visitBreakStmt: " + ctx.getText());
		return new BreakStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitContinueStmt(MxstarParser.ContinueStmtContext ctx) {
		System.err.println("visitContinueStmt: " + ctx.getText());
		return new ContinueStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitExprStmt(MxstarParser.ExprStmtContext ctx) {
		System.err.println("visitExprStmt: " + ctx.getText());
		return new ExprStmtNode( new Location(ctx.getStart()), (ExprNode)visit(ctx.expr()) );
	}
	
	@Override
	public ASTNode visitBrankStmt(MxstarParser.BrankStmtContext ctx) {
		System.err.println("visitBrankStmt: " + ctx.getText());
		return new BrankStmtNode( new Location(ctx.getStart()) );
	}
	
	@Override
	public ASTNode visitVarExpr(MxstarParser.VarExprContext ctx) {
		//System.err.println("visitVarExpr: " + ctx.getText());
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
		ExprNode expr = (ExprNode) visit(ctx.expr());
		if (ctx.identifierMember() != null) {
			return new MemberExprNode( new Location(ctx.getStart()),
				expr,
				(VarExprNode) visit(ctx.identifierMember())
			);
		}
		else if (ctx.functCall() != null) {
			return new MemberExprNode( new Location(ctx.getStart()),
				expr,
				(FunctExprNode) visit(ctx.functCall())
			);
		}
		else if (ctx.arrayCall() != null){
			return new MemberExprNode( new Location(ctx.getStart()),
				expr,
				(ArrayExprNode) visit(ctx.arrayCall())
			);
		}
		else 		
			return null;
	}
	
	@Override
	public ASTNode visitIdentifierMember(MxstarParser.IdentifierMemberContext ctx) {
		return new VarExprNode(new Location(ctx.getStart()), ctx.getText());
	}
	
	@Override
	public ASTNode visitCreatorExpr(MxstarParser.CreatorExprContext ctx) {
		return visit(ctx.creator());
	}
	
	@Override 
	public ASTNode visitInvalidCreator(MxstarParser.InvalidCreatorContext ctx) {
		errorReminder.error( new Location(ctx.getStart()), "Invalid creator.");
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
		return visit(ctx.arrayCall());
		/*
		ExprNode nameExpr = (ExprNode)visit(ctx.expr(0));
		if (nameExpr instanceof ArrayExprNode) { 
			ArrayList<ExprNode> indexExpr = ((ArrayExprNode)nameExpr).getIndexExpr();
			indexExpr.add((ExprNode)visit(ctx.expr(1)));
			return new ArrayExprNode( new Location(ctx.getStart()), 
				((ArrayExprNode) nameExpr).getNameExpr(),
				indexExpr	
			);	
		}
		else {
			ArrayList<ExprNode> indexExpr = new ArrayList<ExprNode>();
			indexExpr.add((ExprNode)visit(ctx.expr(1)));
			return new ArrayExprNode( new Location(ctx.getStart()), 
				nameExpr,
				indexExpr	
			);	
		}*/
	}
	
	@Override
	public ASTNode visitArrayCall(MxstarParser.ArrayCallContext ctx) {
		ArrayList<ExprNode> indexExpr = new ArrayList<ExprNode>();
		for (MxstarParser.ExprContext item : ctx.expr()) {
			indexExpr.add((ExprNode)visit(item));
		}
		return new ArrayExprNode( new Location(ctx.getStart()),
			ctx.Identifier().getText(),
			indexExpr
		);
	}
	
	@Override
	public ASTNode visitFunctExpr(MxstarParser.FunctExprContext ctx) {
		//System.err.println("visitFunctExpr: " + ctx.getText());
		return visit(ctx.functCall());
	}
	
	@Override
	public ASTNode visitFunctCall(MxstarParser.FunctCallContext ctx) {
		ArrayList<ExprNode> paraList = new ArrayList<ExprNode>();
		for (MxstarParser.ExprContext item : ctx.expr()) {
			paraList.add((ExprNode)visit(item));
		}
		return new FunctExprNode( new Location(ctx.getStart()),
			ctx.Identifier().getText(), 
			paraList
		);
	}
	
	@Override
	public ASTNode visitBracketExpr(MxstarParser.BracketExprContext ctx) {
		System.err.println("visitBracketExpr: " + ctx.getText());
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
		System.err.println("visitBinaryExpr: " + ctx.getText());
		//System.err.println(ctx.op);
		//System.err.println(ctx.expr(0).getText());
		//System.err.println(ctx.expr(1).getText());
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
		TypeNode tmp = (TypeNode)visit(ctx.varType());
		return new ArrayTypeNode( new Location(ctx.getStart()),
			tmp.toString(),
			dimension
		);
	}
	
}
