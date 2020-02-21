// Generated from Mxstar.g4 by ANTLR 4.8
package parser;


import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxstarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxstarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxstarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxstarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinition(MxstarParser.DefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(MxstarParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxstarParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#functDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctDef(MxstarParser.FunctDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxstarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(MxstarParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(MxstarParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDefStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStmt(MxstarParser.VarDefStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MxstarParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(MxstarParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MxstarParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(MxstarParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(MxstarParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code contintueStmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContintueStmt(MxstarParser.ContintueStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code brankstmt}
	 * labeled alternative in {@link MxstarParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrankstmt(MxstarParser.BrankstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExpr(MxstarParser.ThisExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(MxstarParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bracketExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketExpr(MxstarParser.BracketExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpr(MxstarParser.LogicalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberExpr(MxstarParser.MemberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffixExpr(MxstarParser.SuffixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxstarParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctExpr(MxstarParser.FunctExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExpr(MxstarParser.VarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(MxstarParser.PrefixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(MxstarParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code creatorExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatorExpr(MxstarParser.CreatorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxstarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxstarParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitErrorCreator(MxstarParser.ErrorCreatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreator(MxstarParser.ArrayCreatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonarrayCreator}
	 * labeled alternative in {@link MxstarParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonarrayCreator(MxstarParser.NonarrayCreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(MxstarParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(MxstarParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxstarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxstarParser.LiteralContext ctx);
}