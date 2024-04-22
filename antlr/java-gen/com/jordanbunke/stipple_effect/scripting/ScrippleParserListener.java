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
	 * Enter a parse tree produced by the {@code VOID_RETURN}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void enterVOID_RETURN(ScrippleParser.VOID_RETURNContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VOID_RETURN}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void exitVOID_RETURN(ScrippleParser.VOID_RETURNContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TYPE_RETURN}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void enterTYPE_RETURN(ScrippleParser.TYPE_RETURNContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TYPE_RETURN}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 */
	void exitTYPE_RETURN(ScrippleParser.TYPE_RETURNContext ctx);
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
	 * Enter a parse tree produced by the {@code IMAGE_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIMAGE_TYPE(ScrippleParser.IMAGE_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IMAGE_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIMAGE_TYPE(ScrippleParser.IMAGE_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CHAR_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterCHAR_TYPE(ScrippleParser.CHAR_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CHAR_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitCHAR_TYPE(ScrippleParser.CHAR_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code STRING_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterSTRING_TYPE(ScrippleParser.STRING_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code STRING_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitSTRING_TYPE(ScrippleParser.STRING_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MAP_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterMAP_TYPE(ScrippleParser.MAP_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MAP_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitMAP_TYPE(ScrippleParser.MAP_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ARRAY_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterARRAY_TYPE(ScrippleParser.ARRAY_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ARRAY_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitARRAY_TYPE(ScrippleParser.ARRAY_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code COLOR_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterCOLOR_TYPE(ScrippleParser.COLOR_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code COLOR_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitCOLOR_TYPE(ScrippleParser.COLOR_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FLOAT_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterFLOAT_TYPE(ScrippleParser.FLOAT_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FLOAT_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitFLOAT_TYPE(ScrippleParser.FLOAT_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LIST_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterLIST_TYPE(ScrippleParser.LIST_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LIST_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitLIST_TYPE(ScrippleParser.LIST_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BOOL_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBOOL_TYPE(ScrippleParser.BOOL_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BOOL_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBOOL_TYPE(ScrippleParser.BOOL_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INT_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterINT_TYPE(ScrippleParser.INT_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INT_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitINT_TYPE(ScrippleParser.INT_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SET_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterSET_TYPE(ScrippleParser.SET_TYPEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SET_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitSET_TYPE(ScrippleParser.SET_TYPEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SINGLE_STAT_BODY}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void enterSINGLE_STAT_BODY(ScrippleParser.SINGLE_STAT_BODYContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SINGLE_STAT_BODY}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void exitSINGLE_STAT_BODY(ScrippleParser.SINGLE_STAT_BODYContext ctx);
	/**
	 * Enter a parse tree produced by the {@code COMPLEX_BODY}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void enterCOMPLEX_BODY(ScrippleParser.COMPLEX_BODYContext ctx);
	/**
	 * Exit a parse tree produced by the {@code COMPLEX_BODY}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 */
	void exitCOMPLEX_BODY(ScrippleParser.COMPLEX_BODYContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LOOP_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterLOOP_STAT(ScrippleParser.LOOP_STATContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LOOP_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitLOOP_STAT(ScrippleParser.LOOP_STATContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IF_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIF_STAT(ScrippleParser.IF_STATContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IF_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIF_STAT(ScrippleParser.IF_STATContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VAR_DEF_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterVAR_DEF_STAT(ScrippleParser.VAR_DEF_STATContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VAR_DEF_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitVAR_DEF_STAT(ScrippleParser.VAR_DEF_STATContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGNMENT_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNMENT_STAT(ScrippleParser.ASSIGNMENT_STATContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNMENT_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNMENT_STAT(ScrippleParser.ASSIGNMENT_STATContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RETURN_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterRETURN_STAT(ScrippleParser.RETURN_STATContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RETURN_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitRETURN_STAT(ScrippleParser.RETURN_STATContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ADD_TO_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterADD_TO_COLLECTION(ScrippleParser.ADD_TO_COLLECTIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ADD_TO_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitADD_TO_COLLECTION(ScrippleParser.ADD_TO_COLLECTIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code REMOVE_FROM_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterREMOVE_FROM_COLLECTION(ScrippleParser.REMOVE_FROM_COLLECTIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code REMOVE_FROM_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitREMOVE_FROM_COLLECTION(ScrippleParser.REMOVE_FROM_COLLECTIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DEFINE_MAP_ENTRY}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDEFINE_MAP_ENTRY(ScrippleParser.DEFINE_MAP_ENTRYContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DEFINE_MAP_ENTRY}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDEFINE_MAP_ENTRY(ScrippleParser.DEFINE_MAP_ENTRYContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DRAW_ONTO_IMAGE}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDRAW_ONTO_IMAGE(ScrippleParser.DRAW_ONTO_IMAGEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DRAW_ONTO_IMAGE}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDRAW_ONTO_IMAGE(ScrippleParser.DRAW_ONTO_IMAGEContext ctx);
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
	 * Enter a parse tree produced by the {@code COND_FIRST_LOOP}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterCOND_FIRST_LOOP(ScrippleParser.COND_FIRST_LOOPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code COND_FIRST_LOOP}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitCOND_FIRST_LOOP(ScrippleParser.COND_FIRST_LOOPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code COND_LAST_LOOP}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void enterCOND_LAST_LOOP(ScrippleParser.COND_LAST_LOOPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code COND_LAST_LOOP}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 */
	void exitCOND_LAST_LOOP(ScrippleParser.COND_LAST_LOOPContext ctx);
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
	 * Enter a parse tree produced by the {@code COMPARISON_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCOMPARISON_BIN_EXPR(ScrippleParser.COMPARISON_BIN_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code COMPARISON_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCOMPARISON_BIN_EXPR(ScrippleParser.COMPARISON_BIN_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NESTED_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNESTED_EXPR(ScrippleParser.NESTED_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NESTED_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNESTED_EXPR(ScrippleParser.NESTED_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UNARY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUNARY_EXPR(ScrippleParser.UNARY_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UNARY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUNARY_EXPR(ScrippleParser.UNARY_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MAP_KEYSET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMAP_KEYSET_EXPR(ScrippleParser.MAP_KEYSET_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MAP_KEYSET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMAP_KEYSET_EXPR(ScrippleParser.MAP_KEYSET_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CONTAINS_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCONTAINS_EXPR(ScrippleParser.CONTAINS_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CONTAINS_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCONTAINS_EXPR(ScrippleParser.CONTAINS_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RGBA_COLOR_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRGBA_COLOR_EXPR(ScrippleParser.RGBA_COLOR_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RGBA_COLOR_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRGBA_COLOR_EXPR(ScrippleParser.RGBA_COLOR_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TERNARY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTERNARY_EXPR(ScrippleParser.TERNARY_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TERNARY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTERNARY_EXPR(ScrippleParser.TERNARY_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ARITHMETIC_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterARITHMETIC_BIN_EXPR(ScrippleParser.ARITHMETIC_BIN_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ARITHMETIC_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitARITHMETIC_BIN_EXPR(ScrippleParser.ARITHMETIC_BIN_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MAP_GET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMAP_GET_EXPR(ScrippleParser.MAP_GET_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MAP_GET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMAP_GET_EXPR(ScrippleParser.MAP_GET_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IMAGE_OF_BOUNDS_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIMAGE_OF_BOUNDS_EXPR(ScrippleParser.IMAGE_OF_BOUNDS_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IMAGE_OF_BOUNDS_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIMAGE_OF_BOUNDS_EXPR(ScrippleParser.IMAGE_OF_BOUNDS_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MULT_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMULT_BIN_EXPR(ScrippleParser.MULT_BIN_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MULT_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMULT_BIN_EXPR(ScrippleParser.MULT_BIN_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LIT_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLIT_EXPR(ScrippleParser.LIT_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LIT_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLIT_EXPR(ScrippleParser.LIT_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NEW_SET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNEW_SET_EXPR(ScrippleParser.NEW_SET_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NEW_SET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNEW_SET_EXPR(ScrippleParser.NEW_SET_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RGB_COLOR_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRGB_COLOR_EXPR(ScrippleParser.RGB_COLOR_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RGB_COLOR_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRGB_COLOR_EXPR(ScrippleParser.RGB_COLOR_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NEW_LIST_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNEW_LIST_EXPR(ScrippleParser.NEW_LIST_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NEW_LIST_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNEW_LIST_EXPR(ScrippleParser.NEW_LIST_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NEW_ARRAY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNEW_ARRAY_EXPR(ScrippleParser.NEW_ARRAY_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NEW_ARRAY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNEW_ARRAY_EXPR(ScrippleParser.NEW_ARRAY_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NEW_MAP_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNEW_MAP_EXPR(ScrippleParser.NEW_MAP_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NEW_MAP_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNEW_MAP_EXPR(ScrippleParser.NEW_MAP_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGNABLE_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNABLE_EXPR(ScrippleParser.ASSIGNABLE_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNABLE_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNABLE_EXPR(ScrippleParser.ASSIGNABLE_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IMAGE_BOUND_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIMAGE_BOUND_EXPR(ScrippleParser.IMAGE_BOUND_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IMAGE_BOUND_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIMAGE_BOUND_EXPR(ScrippleParser.IMAGE_BOUND_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LOGIC_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLOGIC_BIN_EXPR(ScrippleParser.LOGIC_BIN_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LOGIC_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLOGIC_BIN_EXPR(ScrippleParser.LOGIC_BIN_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GET_COLOR_CHANNEL}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGET_COLOR_CHANNEL(ScrippleParser.GET_COLOR_CHANNELContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GET_COLOR_CHANNEL}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGET_COLOR_CHANNEL(ScrippleParser.GET_COLOR_CHANNELContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IMAGE_FROM_PATH_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIMAGE_FROM_PATH_EXPR(ScrippleParser.IMAGE_FROM_PATH_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IMAGE_FROM_PATH_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIMAGE_FROM_PATH_EXPR(ScrippleParser.IMAGE_FROM_PATH_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code POWER_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPOWER_BIN_EXPR(ScrippleParser.POWER_BIN_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code POWER_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPOWER_BIN_EXPR(ScrippleParser.POWER_BIN_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code COLOR_AT_PIXEL_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCOLOR_AT_PIXEL_EXPR(ScrippleParser.COLOR_AT_PIXEL_EXPRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code COLOR_AT_PIXEL_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCOLOR_AT_PIXEL_EXPR(ScrippleParser.COLOR_AT_PIXEL_EXPRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EXPLICIT_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEXPLICIT_COLLECTION(ScrippleParser.EXPLICIT_COLLECTIONContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EXPLICIT_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEXPLICIT_COLLECTION(ScrippleParser.EXPLICIT_COLLECTIONContext ctx);
	/**
	 * Enter a parse tree produced by the {@code STANDARD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterSTANDARD_ASSIGNMENT(ScrippleParser.STANDARD_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code STANDARD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitSTANDARD_ASSIGNMENT(ScrippleParser.STANDARD_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INC_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterINC_ASSIGNMENT(ScrippleParser.INC_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INC_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitINC_ASSIGNMENT(ScrippleParser.INC_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DEC_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDEC_ASSIGNMENT(ScrippleParser.DEC_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DEC_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDEC_ASSIGNMENT(ScrippleParser.DEC_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ADD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterADD_ASSIGNMENT(ScrippleParser.ADD_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ADD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitADD_ASSIGNMENT(ScrippleParser.ADD_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SUB_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterSUB_ASSIGNMENT(ScrippleParser.SUB_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SUB_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitSUB_ASSIGNMENT(ScrippleParser.SUB_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MUL_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterMUL_ASSIGNMENT(ScrippleParser.MUL_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MUL_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitMUL_ASSIGNMENT(ScrippleParser.MUL_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DIV_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDIV_ASSIGNMENT(ScrippleParser.DIV_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DIV_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDIV_ASSIGNMENT(ScrippleParser.DIV_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MOD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterMOD_ASSIGNMENT(ScrippleParser.MOD_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MOD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitMOD_ASSIGNMENT(ScrippleParser.MOD_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AND_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAND_ASSIGNMENT(ScrippleParser.AND_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AND_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAND_ASSIGNMENT(ScrippleParser.AND_ASSIGNMENTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OR_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterOR_ASSIGNMENT(ScrippleParser.OR_ASSIGNMENTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OR_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitOR_ASSIGNMENT(ScrippleParser.OR_ASSIGNMENTContext ctx);
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
	 * Enter a parse tree produced by the {@code IMPLICIT_DEF}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterIMPLICIT_DEF(ScrippleParser.IMPLICIT_DEFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IMPLICIT_DEF}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitIMPLICIT_DEF(ScrippleParser.IMPLICIT_DEFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EXPLICIT_DEF}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterEXPLICIT_DEF(ScrippleParser.EXPLICIT_DEFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EXPLICIT_DEF}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitEXPLICIT_DEF(ScrippleParser.EXPLICIT_DEFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGNABLE_VAR}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNABLE_VAR(ScrippleParser.ASSIGNABLE_VARContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNABLE_VAR}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNABLE_VAR(ScrippleParser.ASSIGNABLE_VARContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGNABLE_LIST_ELEM}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNABLE_LIST_ELEM(ScrippleParser.ASSIGNABLE_LIST_ELEMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNABLE_LIST_ELEM}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNABLE_LIST_ELEM(ScrippleParser.ASSIGNABLE_LIST_ELEMContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGNABLE_ARR_ELEM}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNABLE_ARR_ELEM(ScrippleParser.ASSIGNABLE_ARR_ELEMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNABLE_ARR_ELEM}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNABLE_ARR_ELEM(ScrippleParser.ASSIGNABLE_ARR_ELEMContext ctx);
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
	 * Enter a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterSTRING_LITERAL(ScrippleParser.STRING_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitSTRING_LITERAL(ScrippleParser.STRING_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CHAR_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterCHAR_LITERAL(ScrippleParser.CHAR_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CHAR_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitCHAR_LITERAL(ScrippleParser.CHAR_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INT_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterINT_LITERAL(ScrippleParser.INT_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INT_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitINT_LITERAL(ScrippleParser.INT_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FLOAT_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFLOAT_LITERAL(ScrippleParser.FLOAT_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FLOAT_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFLOAT_LITERAL(ScrippleParser.FLOAT_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BOOL_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterBOOL_LITERAL(ScrippleParser.BOOL_LITERALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BOOL_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitBOOL_LITERAL(ScrippleParser.BOOL_LITERALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code HEXADECIMAL}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void enterHEXADECIMAL(ScrippleParser.HEXADECIMALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code HEXADECIMAL}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void exitHEXADECIMAL(ScrippleParser.HEXADECIMALContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DECIMAL}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void enterDECIMAL(ScrippleParser.DECIMALContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DECIMAL}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 */
	void exitDECIMAL(ScrippleParser.DECIMALContext ctx);
}