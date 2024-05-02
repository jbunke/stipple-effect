// Generated from C:/Users/Jordan Bunke/Documents/Java/2022/stipple-effect/antlr/ScrippleParser.g4 by ANTLR 4.13.1
package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ScrippleParser}.
 */
public interface ScrippleParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#head_rule}.
	 * @param ctx the parse tree
	 */
	void enterHead_rule(ScrippleParser.Head_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#head_rule}.
	 * @param ctx the parse tree
	 */
	void exitHead_rule(ScrippleParser.Head_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ScrippleParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ScrippleParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VoidReturnSignature}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void enterVoidReturnSignature(ScrippleParser.VoidReturnSignatureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VoidReturnSignature}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void exitVoidReturnSignature(ScrippleParser.VoidReturnSignatureContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeReturnSignature}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void enterTypeReturnSignature(ScrippleParser.TypeReturnSignatureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeReturnSignature}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void exitTypeReturnSignature(ScrippleParser.TypeReturnSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(ScrippleParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(ScrippleParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(ScrippleParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(ScrippleParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(ScrippleParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(ScrippleParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterStringType(ScrippleParser.StringTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitStringType(ScrippleParser.StringTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterSetType(ScrippleParser.SetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitSetType(ScrippleParser.SetTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterListType(ScrippleParser.ListTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitListType(ScrippleParser.ListTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterColorType(ScrippleParser.ColorTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitColorType(ScrippleParser.ColorTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CharType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterCharType(ScrippleParser.CharTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CharType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitCharType(ScrippleParser.CharTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterMapType(ScrippleParser.MapTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitMapType(ScrippleParser.MapTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterIntType(ScrippleParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitIntType(ScrippleParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterFloatType(ScrippleParser.FloatTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitFloatType(ScrippleParser.FloatTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterImageType(ScrippleParser.ImageTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageType}
	 * labeled alternative in {@link ScrippleParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitImageType(ScrippleParser.ImageTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SingleStatBody}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void enterSingleStatBody(ScrippleParser.SingleStatBodyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleStatBody}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void exitSingleStatBody(ScrippleParser.SingleStatBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ComplexBody}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void enterComplexBody(ScrippleParser.ComplexBodyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ComplexBody}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void exitComplexBody(ScrippleParser.ComplexBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LoopStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(ScrippleParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LoopStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(ScrippleParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ScrippleParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ScrippleParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarDefStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStatement(ScrippleParser.VarDefStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarDefStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStatement(ScrippleParser.VarDefStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(ScrippleParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(ScrippleParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ScrippleParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ScrippleParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddToCollection}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAddToCollection(ScrippleParser.AddToCollectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddToCollection}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAddToCollection(ScrippleParser.AddToCollectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RemoveFromCollection}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterRemoveFromCollection(ScrippleParser.RemoveFromCollectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RemoveFromCollection}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitRemoveFromCollection(ScrippleParser.RemoveFromCollectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefineMapEntryStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDefineMapEntryStatement(ScrippleParser.DefineMapEntryStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefineMapEntryStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDefineMapEntryStatement(ScrippleParser.DefineMapEntryStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DrawOntoImageStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDrawOntoImageStatement(ScrippleParser.DrawOntoImageStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DrawOntoImageStatement}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDrawOntoImageStatement(ScrippleParser.DrawOntoImageStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#return_stat}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stat(ScrippleParser.Return_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#return_stat}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stat(ScrippleParser.Return_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(ScrippleParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(ScrippleParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IteratorLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterIteratorLoop(ScrippleParser.IteratorLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IteratorLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitIteratorLoop(ScrippleParser.IteratorLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterForLoop(ScrippleParser.ForLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitForLoop(ScrippleParser.ForLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoWhileLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileLoop(ScrippleParser.DoWhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoWhileLoop}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileLoop(ScrippleParser.DoWhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#iteration_def}.
	 * @param ctx the parse tree
	 */
	void enterIteration_def(ScrippleParser.Iteration_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#iteration_def}.
	 * @param ctx the parse tree
	 */
	void exitIteration_def(ScrippleParser.Iteration_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#while_def}.
	 * @param ctx the parse tree
	 */
	void enterWhile_def(ScrippleParser.While_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#while_def}.
	 * @param ctx the parse tree
	 */
	void exitWhile_def(ScrippleParser.While_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#for_def}.
	 * @param ctx the parse tree
	 */
	void enterFor_def(ScrippleParser.For_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#for_def}.
	 * @param ctx the parse tree
	 */
	void exitFor_def(ScrippleParser.For_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(ScrippleParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(ScrippleParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#if_def}.
	 * @param ctx the parse tree
	 */
	void enterIf_def(ScrippleParser.If_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#if_def}.
	 * @param ctx the parse tree
	 */
	void exitIf_def(ScrippleParser.If_defContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterLogicBinExpression(ScrippleParser.LogicBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitLogicBinExpression(ScrippleParser.LogicBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpression(ScrippleParser.TernaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpression(ScrippleParser.TernaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitArrayExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterExplicitArrayExpression(ScrippleParser.ExplicitArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitArrayExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitExplicitArrayExpression(ScrippleParser.ExplicitArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterMultBinExpression(ScrippleParser.MultBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitMultBinExpression(ScrippleParser.MultBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageBoundExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterImageBoundExpression(ScrippleParser.ImageBoundExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageBoundExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitImageBoundExpression(ScrippleParser.ImageBoundExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(ScrippleParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(ScrippleParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapLookupExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterMapLookupExpression(ScrippleParser.MapLookupExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapLookupExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitMapLookupExpression(ScrippleParser.MapLookupExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PowerBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterPowerBinExpression(ScrippleParser.PowerBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PowerBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitPowerBinExpression(ScrippleParser.PowerBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitListExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterExplicitListExpression(ScrippleParser.ExplicitListExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitListExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitExplicitListExpression(ScrippleParser.ExplicitListExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ComparisonBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterComparisonBinExpression(ScrippleParser.ComparisonBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ComparisonBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitComparisonBinExpression(ScrippleParser.ComparisonBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RGBColorExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterRGBColorExpression(ScrippleParser.RGBColorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RGBColorExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitRGBColorExpression(ScrippleParser.RGBColorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageOfBoundsExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterImageOfBoundsExpression(ScrippleParser.ImageOfBoundsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageOfBoundsExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitImageOfBoundsExpression(ScrippleParser.ImageOfBoundsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignableExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterAssignableExpression(ScrippleParser.AssignableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignableExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitAssignableExpression(ScrippleParser.AssignableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitSetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSetExpression(ScrippleParser.ExplicitSetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitSetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSetExpression(ScrippleParser.ExplicitSetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewMapExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewMapExpression(ScrippleParser.NewMapExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewMapExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewMapExpression(ScrippleParser.NewMapExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RGBAColorExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterRGBAColorExpression(ScrippleParser.RGBAColorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RGBAColorExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitRGBAColorExpression(ScrippleParser.RGBAColorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewSetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewSetExpression(ScrippleParser.NewSetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewSetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewSetExpression(ScrippleParser.NewSetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ContainsExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterContainsExpression(ScrippleParser.ContainsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ContainsExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitContainsExpression(ScrippleParser.ContainsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(ScrippleParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(ScrippleParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewListExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewListExpression(ScrippleParser.NewListExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewListExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewListExpression(ScrippleParser.NewListExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapKeysetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterMapKeysetExpression(ScrippleParser.MapKeysetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapKeysetExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitMapKeysetExpression(ScrippleParser.MapKeysetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TextureColorReplaceExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterTextureColorReplaceExpression(ScrippleParser.TextureColorReplaceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TextureColorReplaceExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitTextureColorReplaceExpression(ScrippleParser.TextureColorReplaceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NestedExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNestedExpression(ScrippleParser.NestedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NestedExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNestedExpression(ScrippleParser.NestedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorAtPixelExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterColorAtPixelExpression(ScrippleParser.ColorAtPixelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorAtPixelExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitColorAtPixelExpression(ScrippleParser.ColorAtPixelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArithmeticBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticBinExpression(ScrippleParser.ArithmeticBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArithmeticBinExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticBinExpression(ScrippleParser.ArithmeticBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorChannelExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterColorChannelExpression(ScrippleParser.ColorChannelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorChannelExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitColorChannelExpression(ScrippleParser.ColorChannelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewArrayExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayExpression(ScrippleParser.NewArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewArrayExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayExpression(ScrippleParser.NewArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageFromPathExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterImageFromPathExpression(ScrippleParser.ImageFromPathExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageFromPathExpression}
	 * labeled alternative in {@link ScrippleParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitImageFromPathExpression(ScrippleParser.ImageFromPathExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StandardAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterStandardAssignment(ScrippleParser.StandardAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StandardAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitStandardAssignment(ScrippleParser.StandardAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IncrementAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterIncrementAssignment(ScrippleParser.IncrementAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IncrementAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitIncrementAssignment(ScrippleParser.IncrementAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DecrementAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDecrementAssignment(ScrippleParser.DecrementAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DecrementAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDecrementAssignment(ScrippleParser.DecrementAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAddAssignment(ScrippleParser.AddAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAddAssignment(ScrippleParser.AddAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterSubAssignment(ScrippleParser.SubAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitSubAssignment(ScrippleParser.SubAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterMultAssignment(ScrippleParser.MultAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitMultAssignment(ScrippleParser.MultAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivAssignmnet}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDivAssignmnet(ScrippleParser.DivAssignmnetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivAssignmnet}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDivAssignmnet(ScrippleParser.DivAssignmnetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ModAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterModAssignment(ScrippleParser.ModAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ModAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitModAssignment(ScrippleParser.ModAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAndAssignment(ScrippleParser.AndAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAndAssignment(ScrippleParser.AndAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterOrAssignment(ScrippleParser.OrAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrAssignment}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitOrAssignment(ScrippleParser.OrAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#var_init}.
	 * @param ctx the parse tree
	 */
	void enterVar_init(ScrippleParser.Var_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#var_init}.
	 * @param ctx the parse tree
	 */
	void exitVar_init(ScrippleParser.Var_initContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImplicitVarDef}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterImplicitVarDef(ScrippleParser.ImplicitVarDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImplicitVarDef}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitImplicitVarDef(ScrippleParser.ImplicitVarDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitVarDef}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterExplicitVarDef(ScrippleParser.ExplicitVarDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitVarDef}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitExplicitVarDef(ScrippleParser.ExplicitVarDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SimpleAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterSimpleAssignable(ScrippleParser.SimpleAssignableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SimpleAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitSimpleAssignable(ScrippleParser.SimpleAssignableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterListAssignable(ScrippleParser.ListAssignableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitListAssignable(ScrippleParser.ListAssignableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterArrayAssignable(ScrippleParser.ArrayAssignableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAssignable}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitArrayAssignable(ScrippleParser.ArrayAssignableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScrippleParser#ident}.
	 * @param ctx the parse tree
	 */
	void enterIdent(ScrippleParser.IdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScrippleParser#ident}.
	 * @param ctx the parse tree
	 */
	void exitIdent(ScrippleParser.IdentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(ScrippleParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(ScrippleParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CharLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterCharLiteral(ScrippleParser.CharLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CharLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitCharLiteral(ScrippleParser.CharLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(ScrippleParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(ScrippleParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(ScrippleParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(ScrippleParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(ScrippleParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(ScrippleParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Hexadecimal}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void enterHexadecimal(ScrippleParser.HexadecimalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Hexadecimal}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void exitHexadecimal(ScrippleParser.HexadecimalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Decimal}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void enterDecimal(ScrippleParser.DecimalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Decimal}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void exitDecimal(ScrippleParser.DecimalContext ctx);
}