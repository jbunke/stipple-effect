// Generated from C:/Users/Jordan Bunke/Documents/Java/2022/stipple-effect/antlr/ScrippleParser.g4 by ANTLR 4.13.1
package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ScriptParser}.
 */
public interface ScriptParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ScriptParser#head_rule}.
	 * @param ctx the parse tree
	 */
	void enterHead_rule(ScriptParser.Head_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#head_rule}.
	 * @param ctx the parse tree
	 */
	void exitHead_rule(ScriptParser.Head_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ScriptParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ScriptParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VoidReturnSignature}
	 * labeled alternative in {@link ScriptParser#signature}.
	 * @param ctx the parse tree
	 */
	void enterVoidReturnSignature(ScriptParser.VoidReturnSignatureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VoidReturnSignature}
	 * labeled alternative in {@link ScriptParser#signature}.
	 * @param ctx the parse tree
	 */
	void exitVoidReturnSignature(ScriptParser.VoidReturnSignatureContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeReturnSignature}
	 * labeled alternative in {@link ScriptParser#signature}.
	 * @param ctx the parse tree
	 */
	void enterTypeReturnSignature(ScriptParser.TypeReturnSignatureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeReturnSignature}
	 * labeled alternative in {@link ScriptParser#signature}.
	 * @param ctx the parse tree
	 */
	void exitTypeReturnSignature(ScriptParser.TypeReturnSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(ScriptParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(ScriptParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(ScriptParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(ScriptParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(ScriptParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(ScriptParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterStringType(ScriptParser.StringTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitStringType(ScriptParser.StringTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterSetType(ScriptParser.SetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitSetType(ScriptParser.SetTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterListType(ScriptParser.ListTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitListType(ScriptParser.ListTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterColorType(ScriptParser.ColorTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitColorType(ScriptParser.ColorTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CharType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterCharType(ScriptParser.CharTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CharType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitCharType(ScriptParser.CharTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterMapType(ScriptParser.MapTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitMapType(ScriptParser.MapTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterIntType(ScriptParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitIntType(ScriptParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterFloatType(ScriptParser.FloatTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitFloatType(ScriptParser.FloatTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void enterImageType(ScriptParser.ImageTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageType}
	 * labeled alternative in {@link ScriptParser#type()}.
	 * @param ctx the parse tree
	 */
	void exitImageType(ScriptParser.ImageTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SingleStatBody}
	 * labeled alternative in {@link ScriptParser#body}.
	 * @param ctx the parse tree
	 */
	void enterSingleStatBody(ScriptParser.SingleStatBodyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SingleStatBody}
	 * labeled alternative in {@link ScriptParser#body}.
	 * @param ctx the parse tree
	 */
	void exitSingleStatBody(ScriptParser.SingleStatBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ComplexBody}
	 * labeled alternative in {@link ScriptParser#body}.
	 * @param ctx the parse tree
	 */
	void enterComplexBody(ScriptParser.ComplexBodyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ComplexBody}
	 * labeled alternative in {@link ScriptParser#body}.
	 * @param ctx the parse tree
	 */
	void exitComplexBody(ScriptParser.ComplexBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LoopStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(ScriptParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LoopStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(ScriptParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ScriptParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ScriptParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarDefStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStatement(ScriptParser.VarDefStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarDefStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStatement(ScriptParser.VarDefStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(ScriptParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(ScriptParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ScriptParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ScriptParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddToCollection}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAddToCollection(ScriptParser.AddToCollectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddToCollection}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAddToCollection(ScriptParser.AddToCollectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RemoveFromCollection}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterRemoveFromCollection(ScriptParser.RemoveFromCollectionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RemoveFromCollection}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitRemoveFromCollection(ScriptParser.RemoveFromCollectionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefineMapEntryStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDefineMapEntryStatement(ScriptParser.DefineMapEntryStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefineMapEntryStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDefineMapEntryStatement(ScriptParser.DefineMapEntryStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DrawOntoImageStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDrawOntoImageStatement(ScriptParser.DrawOntoImageStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DrawOntoImageStatement}
	 * labeled alternative in {@link ScriptParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDrawOntoImageStatement(ScriptParser.DrawOntoImageStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#return_stat}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stat(ScriptParser.Return_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#return_stat}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stat(ScriptParser.Return_statContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(ScriptParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(ScriptParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IteratorLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterIteratorLoop(ScriptParser.IteratorLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IteratorLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitIteratorLoop(ScriptParser.IteratorLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterForLoop(ScriptParser.ForLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitForLoop(ScriptParser.ForLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoWhileLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileLoop(ScriptParser.DoWhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoWhileLoop}
	 * labeled alternative in {@link ScriptParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileLoop(ScriptParser.DoWhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#iteration_def}.
	 * @param ctx the parse tree
	 */
	void enterIteration_def(ScriptParser.Iteration_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#iteration_def}.
	 * @param ctx the parse tree
	 */
	void exitIteration_def(ScriptParser.Iteration_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#while_def}.
	 * @param ctx the parse tree
	 */
	void enterWhile_def(ScriptParser.While_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#while_def}.
	 * @param ctx the parse tree
	 */
	void exitWhile_def(ScriptParser.While_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#for_def}.
	 * @param ctx the parse tree
	 */
	void enterFor_def(ScriptParser.For_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#for_def}.
	 * @param ctx the parse tree
	 */
	void exitFor_def(ScriptParser.For_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(ScriptParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(ScriptParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#if_def}.
	 * @param ctx the parse tree
	 */
	void enterIf_def(ScriptParser.If_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#if_def}.
	 * @param ctx the parse tree
	 */
	void exitIf_def(ScriptParser.If_defContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterLogicBinExpression(ScriptParser.LogicBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitLogicBinExpression(ScriptParser.LogicBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpression(ScriptParser.TernaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TernaryExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpression(ScriptParser.TernaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitArrayExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterExplicitArrayExpression(ScriptParser.ExplicitArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitArrayExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitExplicitArrayExpression(ScriptParser.ExplicitArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterMultBinExpression(ScriptParser.MultBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitMultBinExpression(ScriptParser.MultBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageBoundExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterImageBoundExpression(ScriptParser.ImageBoundExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageBoundExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitImageBoundExpression(ScriptParser.ImageBoundExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(ScriptParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(ScriptParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapLookupExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterMapLookupExpression(ScriptParser.MapLookupExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapLookupExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitMapLookupExpression(ScriptParser.MapLookupExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PowerBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterPowerBinExpression(ScriptParser.PowerBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PowerBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitPowerBinExpression(ScriptParser.PowerBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitListExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterExplicitListExpression(ScriptParser.ExplicitListExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitListExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitExplicitListExpression(ScriptParser.ExplicitListExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ComparisonBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterComparisonBinExpression(ScriptParser.ComparisonBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ComparisonBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitComparisonBinExpression(ScriptParser.ComparisonBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RGBColorExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterRGBColorExpression(ScriptParser.RGBColorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RGBColorExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitRGBColorExpression(ScriptParser.RGBColorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageOfBoundsExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterImageOfBoundsExpression(ScriptParser.ImageOfBoundsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageOfBoundsExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitImageOfBoundsExpression(ScriptParser.ImageOfBoundsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignableExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterAssignableExpression(ScriptParser.AssignableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignableExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitAssignableExpression(ScriptParser.AssignableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitSetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSetExpression(ScriptParser.ExplicitSetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitSetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSetExpression(ScriptParser.ExplicitSetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewMapExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewMapExpression(ScriptParser.NewMapExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewMapExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewMapExpression(ScriptParser.NewMapExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RGBAColorExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterRGBAColorExpression(ScriptParser.RGBAColorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RGBAColorExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitRGBAColorExpression(ScriptParser.RGBAColorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewSetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewSetExpression(ScriptParser.NewSetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewSetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewSetExpression(ScriptParser.NewSetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ContainsExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterContainsExpression(ScriptParser.ContainsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ContainsExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitContainsExpression(ScriptParser.ContainsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(ScriptParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(ScriptParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewListExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewListExpression(ScriptParser.NewListExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewListExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewListExpression(ScriptParser.NewListExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapKeysetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterMapKeysetExpression(ScriptParser.MapKeysetExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapKeysetExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitMapKeysetExpression(ScriptParser.MapKeysetExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TextureColorReplaceExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterTextureColorReplaceExpression(ScriptParser.TextureColorReplaceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TextureColorReplaceExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitTextureColorReplaceExpression(ScriptParser.TextureColorReplaceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NestedExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNestedExpression(ScriptParser.NestedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NestedExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNestedExpression(ScriptParser.NestedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorAtPixelExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterColorAtPixelExpression(ScriptParser.ColorAtPixelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorAtPixelExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitColorAtPixelExpression(ScriptParser.ColorAtPixelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArithmeticBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticBinExpression(ScriptParser.ArithmeticBinExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArithmeticBinExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticBinExpression(ScriptParser.ArithmeticBinExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorChannelExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterColorChannelExpression(ScriptParser.ColorChannelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorChannelExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitColorChannelExpression(ScriptParser.ColorChannelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewArrayExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayExpression(ScriptParser.NewArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewArrayExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayExpression(ScriptParser.NewArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImageFromPathExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void enterImageFromPathExpression(ScriptParser.ImageFromPathExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImageFromPathExpression}
	 * labeled alternative in {@link ScriptParser#expr()}.
	 * @param ctx the parse tree
	 */
	void exitImageFromPathExpression(ScriptParser.ImageFromPathExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StandardAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterStandardAssignment(ScriptParser.StandardAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StandardAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitStandardAssignment(ScriptParser.StandardAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IncrementAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterIncrementAssignment(ScriptParser.IncrementAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IncrementAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitIncrementAssignment(ScriptParser.IncrementAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DecrementAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDecrementAssignment(ScriptParser.DecrementAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DecrementAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDecrementAssignment(ScriptParser.DecrementAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAddAssignment(ScriptParser.AddAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAddAssignment(ScriptParser.AddAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterSubAssignment(ScriptParser.SubAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitSubAssignment(ScriptParser.SubAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterMultAssignment(ScriptParser.MultAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitMultAssignment(ScriptParser.MultAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivAssignmnet}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDivAssignmnet(ScriptParser.DivAssignmnetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivAssignmnet}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDivAssignmnet(ScriptParser.DivAssignmnetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ModAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterModAssignment(ScriptParser.ModAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ModAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitModAssignment(ScriptParser.ModAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAndAssignment(ScriptParser.AndAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAndAssignment(ScriptParser.AndAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterOrAssignment(ScriptParser.OrAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrAssignment}
	 * labeled alternative in {@link ScriptParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitOrAssignment(ScriptParser.OrAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#var_init}.
	 * @param ctx the parse tree
	 */
	void enterVar_init(ScriptParser.Var_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#var_init}.
	 * @param ctx the parse tree
	 */
	void exitVar_init(ScriptParser.Var_initContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImplicitVarDef}
	 * labeled alternative in {@link ScriptParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterImplicitVarDef(ScriptParser.ImplicitVarDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImplicitVarDef}
	 * labeled alternative in {@link ScriptParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitImplicitVarDef(ScriptParser.ImplicitVarDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitVarDef}
	 * labeled alternative in {@link ScriptParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterExplicitVarDef(ScriptParser.ExplicitVarDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitVarDef}
	 * labeled alternative in {@link ScriptParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitExplicitVarDef(ScriptParser.ExplicitVarDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SimpleAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterSimpleAssignable(ScriptParser.SimpleAssignableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SimpleAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitSimpleAssignable(ScriptParser.SimpleAssignableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterListAssignable(ScriptParser.ListAssignableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitListAssignable(ScriptParser.ListAssignableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterArrayAssignable(ScriptParser.ArrayAssignableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAssignable}
	 * labeled alternative in {@link ScriptParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitArrayAssignable(ScriptParser.ArrayAssignableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ScriptParser#ident}.
	 * @param ctx the parse tree
	 */
	void enterIdent(ScriptParser.IdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ScriptParser#ident}.
	 * @param ctx the parse tree
	 */
	void exitIdent(ScriptParser.IdentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(ScriptParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(ScriptParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CharLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterCharLiteral(ScriptParser.CharLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CharLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitCharLiteral(ScriptParser.CharLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(ScriptParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(ScriptParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(ScriptParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(ScriptParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(ScriptParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolLiteral}
	 * labeled alternative in {@link ScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(ScriptParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Hexadecimal}
	 * labeled alternative in {@link ScriptParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void enterHexadecimal(ScriptParser.HexadecimalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Hexadecimal}
	 * labeled alternative in {@link ScriptParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void exitHexadecimal(ScriptParser.HexadecimalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Decimal}
	 * labeled alternative in {@link ScriptParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void enterDecimal(ScriptParser.DecimalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Decimal}
	 * labeled alternative in {@link ScriptParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void exitDecimal(ScriptParser.DecimalContext ctx);
}