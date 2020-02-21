// Generated from Mxstar.g4 by ANTLR 4.8
package parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxstarParser}.
 */
public interface MxstarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxstarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxstarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxstarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(MxstarParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(MxstarParser.DefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(MxstarParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(MxstarParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MxstarParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MxstarParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#functDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctDef(MxstarParser.FunctDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#functDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctDef(MxstarParser.FunctDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MxstarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MxstarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(MxstarParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(MxstarParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(MxstarParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(MxstarParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDefStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStmt(MxstarParser.VarDefStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDefStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStmt(MxstarParser.VarDefStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MxstarParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MxstarParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MxstarParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MxstarParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MxstarParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MxstarParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(MxstarParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(MxstarParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(MxstarParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(MxstarParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code contintueStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterContintueStmt(MxstarParser.ContintueStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code contintueStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitContintueStmt(MxstarParser.ContintueStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code brankstmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBrankstmt(MxstarParser.BrankstmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code brankstmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBrankstmt(MxstarParser.BrankstmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(MxstarParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(MxstarParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(MxstarParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(MxstarParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bracketExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBracketExpr(MxstarParser.BracketExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bracketExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBracketExpr(MxstarParser.BracketExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpr(MxstarParser.LogicalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpr(MxstarParser.LogicalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(MxstarParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(MxstarParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExpr(MxstarParser.SuffixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExpr(MxstarParser.SuffixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxstarParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxstarParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFunctExpr(MxstarParser.FunctExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFunctExpr(MxstarParser.FunctExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarExpr(MxstarParser.VarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVarExpr(MxstarParser.VarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(MxstarParser.PrefixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(MxstarParser.PrefixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpr(MxstarParser.LiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpr(MxstarParser.LiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code creatorExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCreatorExpr(MxstarParser.CreatorExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code creatorExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCreatorExpr(MxstarParser.CreatorExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxstarParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxstarParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterErrorCreator(MxstarParser.ErrorCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitErrorCreator(MxstarParser.ErrorCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreator(MxstarParser.ArrayCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreator(MxstarParser.ArrayCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonarrayCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterNonarrayCreator(MxstarParser.NonarrayCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonarrayCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitNonarrayCreator(MxstarParser.NonarrayCreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#varType}.
	 * @param ctx the parse tree
	 */
	void enterVarType(MxstarParser.VarTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#varType}.
	 * @param ctx the parse tree
	 */
	void exitVarType(MxstarParser.VarTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(MxstarParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(MxstarParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxstarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MxstarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxstarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MxstarParser.LiteralContext ctx);
}