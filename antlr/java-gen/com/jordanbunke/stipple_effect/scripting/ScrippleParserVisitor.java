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
	 * Visit a parse tree produced by the {@code VOID_RETURN}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVOID_RETURN(ScrippleParser.VOID_RETURNContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TYPE_RETURN}
	 * labeled alternative in {@link ScrippleParser#signature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTYPE_RETURN(ScrippleParser.TYPE_RETURNContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_list(ScrippleParser.Param_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IMAGE_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIMAGE_TYPE(ScrippleParser.IMAGE_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CHAR_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCHAR_TYPE(ScrippleParser.CHAR_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STRING_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTRING_TYPE(ScrippleParser.STRING_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MAP_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMAP_TYPE(ScrippleParser.MAP_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ARRAY_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitARRAY_TYPE(ScrippleParser.ARRAY_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COLOR_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOLOR_TYPE(ScrippleParser.COLOR_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FLOAT_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFLOAT_TYPE(ScrippleParser.FLOAT_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LIST_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLIST_TYPE(ScrippleParser.LIST_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BOOL_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBOOL_TYPE(ScrippleParser.BOOL_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INT_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINT_TYPE(ScrippleParser.INT_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SET_TYPE}
	 * labeled alternative in {@link ScrippleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSET_TYPE(ScrippleParser.SET_TYPEContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SINGLE_STAT_BODY}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSINGLE_STAT_BODY(ScrippleParser.SINGLE_STAT_BODYContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COMPLEX_BODY}
	 * labeled alternative in {@link ScrippleParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOMPLEX_BODY(ScrippleParser.COMPLEX_BODYContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LOOP_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLOOP_STAT(ScrippleParser.LOOP_STATContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IF_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIF_STAT(ScrippleParser.IF_STATContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VAR_DEF_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVAR_DEF_STAT(ScrippleParser.VAR_DEF_STATContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ASSIGNMENT_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGNMENT_STAT(ScrippleParser.ASSIGNMENT_STATContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RETURN_STAT}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRETURN_STAT(ScrippleParser.RETURN_STATContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADD_TO_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADD_TO_COLLECTION(ScrippleParser.ADD_TO_COLLECTIONContext ctx);
	/**
	 * Visit a parse tree produced by the {@code REMOVE_FROM_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitREMOVE_FROM_COLLECTION(ScrippleParser.REMOVE_FROM_COLLECTIONContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DEFINE_MAP_ENTRY}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDEFINE_MAP_ENTRY(ScrippleParser.DEFINE_MAP_ENTRYContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DRAW_ONTO_IMAGE}
	 * labeled alternative in {@link ScrippleParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDRAW_ONTO_IMAGE(ScrippleParser.DRAW_ONTO_IMAGEContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#return_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stat(ScrippleParser.Return_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COND_FIRST_LOOP}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOND_FIRST_LOOP(ScrippleParser.COND_FIRST_LOOPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COND_LAST_LOOP}
	 * labeled alternative in {@link ScrippleParser#loop_stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOND_LAST_LOOP(ScrippleParser.COND_LAST_LOOPContext ctx);
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
	 * Visit a parse tree produced by the {@code COMPARISON_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOMPARISON_BIN_EXPR(ScrippleParser.COMPARISON_BIN_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NESTED_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNESTED_EXPR(ScrippleParser.NESTED_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UNARY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUNARY_EXPR(ScrippleParser.UNARY_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MAP_KEYSET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMAP_KEYSET_EXPR(ScrippleParser.MAP_KEYSET_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CONTAINS_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCONTAINS_EXPR(ScrippleParser.CONTAINS_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RGBA_COLOR_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRGBA_COLOR_EXPR(ScrippleParser.RGBA_COLOR_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TERNARY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTERNARY_EXPR(ScrippleParser.TERNARY_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ARITHMETIC_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitARITHMETIC_BIN_EXPR(ScrippleParser.ARITHMETIC_BIN_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MAP_GET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMAP_GET_EXPR(ScrippleParser.MAP_GET_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IMAGE_OF_BOUNDS_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIMAGE_OF_BOUNDS_EXPR(ScrippleParser.IMAGE_OF_BOUNDS_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MULT_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMULT_BIN_EXPR(ScrippleParser.MULT_BIN_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LIT_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLIT_EXPR(ScrippleParser.LIT_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NEW_SET_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNEW_SET_EXPR(ScrippleParser.NEW_SET_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RGB_COLOR_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRGB_COLOR_EXPR(ScrippleParser.RGB_COLOR_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NEW_LIST_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNEW_LIST_EXPR(ScrippleParser.NEW_LIST_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NEW_ARRAY_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNEW_ARRAY_EXPR(ScrippleParser.NEW_ARRAY_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NEW_MAP_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNEW_MAP_EXPR(ScrippleParser.NEW_MAP_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ASSIGNABLE_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGNABLE_EXPR(ScrippleParser.ASSIGNABLE_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IMAGE_BOUND_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIMAGE_BOUND_EXPR(ScrippleParser.IMAGE_BOUND_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LOGIC_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLOGIC_BIN_EXPR(ScrippleParser.LOGIC_BIN_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GET_COLOR_CHANNEL}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGET_COLOR_CHANNEL(ScrippleParser.GET_COLOR_CHANNELContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IMAGE_FROM_PATH_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIMAGE_FROM_PATH_EXPR(ScrippleParser.IMAGE_FROM_PATH_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code POWER_BIN_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPOWER_BIN_EXPR(ScrippleParser.POWER_BIN_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COLOR_AT_PIXEL_EXPR}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOLOR_AT_PIXEL_EXPR(ScrippleParser.COLOR_AT_PIXEL_EXPRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EXPLICIT_COLLECTION}
	 * labeled alternative in {@link ScrippleParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEXPLICIT_COLLECTION(ScrippleParser.EXPLICIT_COLLECTIONContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STANDARD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTANDARD_ASSIGNMENT(ScrippleParser.STANDARD_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INC_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINC_ASSIGNMENT(ScrippleParser.INC_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DEC_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDEC_ASSIGNMENT(ScrippleParser.DEC_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADD_ASSIGNMENT(ScrippleParser.ADD_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SUB_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSUB_ASSIGNMENT(ScrippleParser.SUB_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MUL_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMUL_ASSIGNMENT(ScrippleParser.MUL_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DIV_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDIV_ASSIGNMENT(ScrippleParser.DIV_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MOD_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMOD_ASSIGNMENT(ScrippleParser.MOD_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AND_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAND_ASSIGNMENT(ScrippleParser.AND_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OR_ASSIGNMENT}
	 * labeled alternative in {@link ScrippleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOR_ASSIGNMENT(ScrippleParser.OR_ASSIGNMENTContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#var_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_init(ScrippleParser.Var_initContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IMPLICIT_DEF}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIMPLICIT_DEF(ScrippleParser.IMPLICIT_DEFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EXPLICIT_DEF}
	 * labeled alternative in {@link ScrippleParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEXPLICIT_DEF(ScrippleParser.EXPLICIT_DEFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ASSIGNABLE_VAR}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGNABLE_VAR(ScrippleParser.ASSIGNABLE_VARContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ASSIGNABLE_LIST_ELEM}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGNABLE_LIST_ELEM(ScrippleParser.ASSIGNABLE_LIST_ELEMContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ASSIGNABLE_ARR_ELEM}
	 * labeled alternative in {@link ScrippleParser#assignable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitASSIGNABLE_ARR_ELEM(ScrippleParser.ASSIGNABLE_ARR_ELEMContext ctx);
	/**
	 * Visit a parse tree produced by {@link ScrippleParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(ScrippleParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code STRING_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSTRING_LITERAL(ScrippleParser.STRING_LITERALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CHAR_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCHAR_LITERAL(ScrippleParser.CHAR_LITERALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code INT_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitINT_LITERAL(ScrippleParser.INT_LITERALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FLOAT_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFLOAT_LITERAL(ScrippleParser.FLOAT_LITERALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BOOL_LITERAL}
	 * labeled alternative in {@link ScrippleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBOOL_LITERAL(ScrippleParser.BOOL_LITERALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code HEXADECIMAL}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHEXADECIMAL(ScrippleParser.HEXADECIMALContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DECIMAL}
	 * labeled alternative in {@link ScrippleParser#int_lit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDECIMAL(ScrippleParser.DECIMALContext ctx);
}