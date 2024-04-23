// Generated from C:/Users/Jordan Bunke/Documents/Java/2022/stipple-effect/antlr/ScrippleParser.g4 by ANTLR 4.13.1
package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ScrippleParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ScrippleParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#head_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHead_rule(ScrippleParser.Head_ruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ScrippleParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VoidReturnSignature}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidReturnSignature(ScrippleParser.VoidReturnSignatureContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeReturnSignature}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeReturnSignature(ScrippleParser.TypeReturnSignatureContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_list(ScrippleParser.Param_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(ScrippleParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(ScrippleParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringType(ScrippleParser.StringTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetType(ScrippleParser.SetTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListType(ScrippleParser.ListTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorType(ScrippleParser.ColorTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CharType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharType(ScrippleParser.CharTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapType(ScrippleParser.MapTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(ScrippleParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatType(ScrippleParser.FloatTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageType(ScrippleParser.ImageTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SingleStatBody}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleStatBody(ScrippleParser.SingleStatBodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ComplexBody}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComplexBody(ScrippleParser.ComplexBodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LoopStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(ScrippleParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ScrippleParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarDefStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStatement(ScrippleParser.VarDefStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(ScrippleParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ScrippleParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddToCollection}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddToCollection(ScrippleParser.AddToCollectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RemoveFromCollection}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRemoveFromCollection(ScrippleParser.RemoveFromCollectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefineMapEntryStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineMapEntryStatement(ScrippleParser.DefineMapEntryStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DrawOntoImageStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDrawOntoImageStatement(ScrippleParser.DrawOntoImageStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#return_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stat(ScrippleParser.Return_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(ScrippleParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IteratorLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteratorLoop(ScrippleParser.IteratorLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(ScrippleParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoWhileLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileLoop(ScrippleParser.DoWhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#iteration_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteration_def(ScrippleParser.Iteration_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#while_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_def(ScrippleParser.While_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#for_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_def(ScrippleParser.For_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#if_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stat(ScrippleParser.If_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpression(ScrippleParser.TernaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicBinExpression(ScrippleParser.LogicBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultBinExpression(ScrippleParser.MultBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageBoundExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageBoundExpression(ScrippleParser.ImageBoundExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ScrippleParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapLookupExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapLookupExpression(ScrippleParser.MapLookupExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerBinExpression(ScrippleParser.PowerBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ComparisonBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonBinExpression(ScrippleParser.ComparisonBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RGBColorExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRGBColorExpression(ScrippleParser.RGBColorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageOfBoundsExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageOfBoundsExpression(ScrippleParser.ImageOfBoundsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignableExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignableExpression(ScrippleParser.AssignableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewMapExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewMapExpression(ScrippleParser.NewMapExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RGBAColorExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRGBAColorExpression(ScrippleParser.RGBAColorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewSetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewSetExpression(ScrippleParser.NewSetExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ContainsExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContainsExpression(ScrippleParser.ContainsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(ScrippleParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewListExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewListExpression(ScrippleParser.NewListExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapKeysetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapKeysetExpression(ScrippleParser.MapKeysetExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NestedExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedExpression(ScrippleParser.NestedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorAtPixelExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorAtPixelExpression(ScrippleParser.ColorAtPixelExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticBinExpression(ScrippleParser.ArithmeticBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExplicitCollectionExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitCollectionExpression(ScrippleParser.ExplicitCollectionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorChannelExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorChannelExpression(ScrippleParser.ColorChannelExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayExpression(ScrippleParser.NewArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageFromPathExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageFromPathExpression(ScrippleParser.ImageFromPathExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StandardAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandardAssignment(ScrippleParser.StandardAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IncrementAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementAssignment(ScrippleParser.IncrementAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DecrementAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecrementAssignment(ScrippleParser.DecrementAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddAssignment(ScrippleParser.AddAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubAssignment(ScrippleParser.SubAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultAssignment(ScrippleParser.MultAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivAssignmnet}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivAssignmnet(ScrippleParser.DivAssignmnetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModAssignment(ScrippleParser.ModAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndAssignment(ScrippleParser.AndAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrAssignment(ScrippleParser.OrAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#var_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_init(ScrippleParser.Var_initContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImplicitVarDef}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplicitVarDef(ScrippleParser.ImplicitVarDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExplicitVarDef}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitVarDef(ScrippleParser.ExplicitVarDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleAssignable(ScrippleParser.SimpleAssignableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListAssignable(ScrippleParser.ListAssignableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAssignable(ScrippleParser.ArrayAssignableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(ScrippleParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(ScrippleParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CharLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharLiteral(ScrippleParser.CharLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(ScrippleParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(ScrippleParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(ScrippleParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Hexadecimal}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHexadecimal(ScrippleParser.HexadecimalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Decimal}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(ScrippleParser.DecimalContext ctx);
}