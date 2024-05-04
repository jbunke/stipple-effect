// Generated from C:/Users/Jordan Bunke/Documents/Java/2022/stipple-effect/antlr/ScriptParser.g4 by ANTLR 4.13.1
package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ScriptParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ScriptParser#head_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHead_rule(ScriptParser.Head_ruleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StandardFuncBody}
	 * labeled alternative in {@link ScriptParser#func_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandardFuncBody(ScriptParser.StandardFuncBodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionalFuncBody}
	 * labeled alternative in {@link ScriptParser#func_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionalFuncBody(ScriptParser.FunctionalFuncBodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VoidReturnSignature}
	 * labeled alternative in {@link ScriptParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidReturnSignature(ScriptParser.VoidReturnSignatureContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeReturnSignature}
	 * labeled alternative in {@link ScriptParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeReturnSignature(ScriptParser.TypeReturnSignatureContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_list(ScriptParser.Param_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ScriptParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(ScriptParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(ScriptParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringType(ScriptParser.StringTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetType(ScriptParser.SetTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListType(ScriptParser.ListTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorType(ScriptParser.ColorTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CharType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharType(ScriptParser.CharTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapType(ScriptParser.MapTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(ScriptParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatType(ScriptParser.FloatTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageType(ScriptParser.ImageTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SingleStatBody}
	 * labeled alternative in {@link ScriptParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleStatBody(ScriptParser.SingleStatBodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ComplexBody}
	 * labeled alternative in {@link ScriptParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComplexBody(ScriptParser.ComplexBodyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LoopStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(ScriptParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ScriptParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarDefStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStatement(ScriptParser.VarDefStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(ScriptParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ScriptParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddToCollection}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddToCollection(ScriptParser.AddToCollectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RemoveFromCollection}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRemoveFromCollection(ScriptParser.RemoveFromCollectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefineMapEntryStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefineMapEntryStatement(ScriptParser.DefineMapEntryStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DrawOntoImageStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDrawOntoImageStatement(ScriptParser.DrawOntoImageStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DotStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotStatement(ScriptParser.DotStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DrawLineStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDrawLineStatement(ScriptParser.DrawLineStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FillStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFillStatement(ScriptParser.FillStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#return_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stat(ScriptParser.Return_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(ScriptParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IteratorLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteratorLoop(ScriptParser.IteratorLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(ScriptParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoWhileLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileLoop(ScriptParser.DoWhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#iteration_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteration_def(ScriptParser.Iteration_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#while_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_def(ScriptParser.While_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#for_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_def(ScriptParser.For_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#if_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stat(ScriptParser.If_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#if_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_def(ScriptParser.If_defContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicBinExpression(ScriptParser.LogicBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpression(ScriptParser.TernaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExplicitArrayExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitArrayExpression(ScriptParser.ExplicitArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultBinExpression(ScriptParser.MultBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageBoundExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageBoundExpression(ScriptParser.ImageBoundExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(ScriptParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapLookupExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapLookupExpression(ScriptParser.MapLookupExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerBinExpression(ScriptParser.PowerBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExplicitListExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitListExpression(ScriptParser.ExplicitListExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ComparisonBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonBinExpression(ScriptParser.ComparisonBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RGBColorExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRGBColorExpression(ScriptParser.RGBColorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageOfBoundsExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageOfBoundsExpression(ScriptParser.ImageOfBoundsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignableExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignableExpression(ScriptParser.AssignableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExplicitSetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitSetExpression(ScriptParser.ExplicitSetExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewMapExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewMapExpression(ScriptParser.NewMapExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RGBAColorExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRGBAColorExpression(ScriptParser.RGBAColorExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewSetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewSetExpression(ScriptParser.NewSetExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageSectionExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageSectionExpression(ScriptParser.ImageSectionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubstringExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubstringExpression(ScriptParser.SubstringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ContainsExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContainsExpression(ScriptParser.ContainsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(ScriptParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewListExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewListExpression(ScriptParser.NewListExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapKeysetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapKeysetExpression(ScriptParser.MapKeysetExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TextureColorReplaceExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextureColorReplaceExpression(ScriptParser.TextureColorReplaceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NestedExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedExpression(ScriptParser.NestedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorAtPixelExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorAtPixelExpression(ScriptParser.ColorAtPixelExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithmeticBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticBinExpression(ScriptParser.ArithmeticBinExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GenLookupExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenLookupExpression(ScriptParser.GenLookupExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorChannelExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorChannelExpression(ScriptParser.ColorChannelExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CharAtExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharAtExpression(ScriptParser.CharAtExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayExpression(ScriptParser.NewArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImageFromPathExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImageFromPathExpression(ScriptParser.ImageFromPathExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StandardAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStandardAssignment(ScriptParser.StandardAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IncrementAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementAssignment(ScriptParser.IncrementAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DecrementAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecrementAssignment(ScriptParser.DecrementAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddAssignment(ScriptParser.AddAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubAssignment(ScriptParser.SubAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultAssignment(ScriptParser.MultAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivAssignmnet}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivAssignmnet(ScriptParser.DivAssignmnetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ModAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModAssignment(ScriptParser.ModAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndAssignment(ScriptParser.AndAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrAssignment(ScriptParser.OrAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#var_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_init(ScriptParser.Var_initContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImplicitVarDef}
	 * labeled alternative in {@link ScriptParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplicitVarDef(ScriptParser.ImplicitVarDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExplicitVarDef}
	 * labeled alternative in {@link ScriptParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitVarDef(ScriptParser.ExplicitVarDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleAssignable(ScriptParser.SimpleAssignableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListAssignable(ScriptParser.ListAssignableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAssignable(ScriptParser.ArrayAssignableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScriptParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(ScriptParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(ScriptParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CharLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharLiteral(ScriptParser.CharLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(ScriptParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(ScriptParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(ScriptParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Hexadecimal}
	 * labeled alternative in {@link ScriptParser#int_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHexadecimal(ScriptParser.HexadecimalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Decimal}
	 * labeled alternative in {@link ScriptParser#int_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(ScriptParser.DecimalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code True}
	 * labeled alternative in {@link ScriptParser#bool_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(ScriptParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code False}
	 * labeled alternative in {@link ScriptParser#bool_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(ScriptParser.FalseContext ctx);
}