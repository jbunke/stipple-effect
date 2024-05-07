// Generated from C:/Users/Jordan Bunke/Documents/Java/2022/stipple-effect/antlr/ScriptParser.g4 by ANTLR 4.13.1
package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ScriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, LINE_COMMENT=2, MULTILINE_COMMENT=3, LPAREN=4, RPAREN=5, LBRACKET=6, 
		RBRACKET=7, LCURLY=8, RCURLY=9, SEMICOLON=10, COLON=11, COMMA=12, PERIOD=13, 
		PIPE=14, QUESTION=15, ASSIGN=16, INCREMENT=17, DECREMENT=18, ADD_ASSIGN=19, 
		SUB_ASSIGN=20, MUL_ASSIGN=21, DIV_ASSIGN=22, MOD_ASSIGN=23, AND_ASSIGN=24, 
		OR_ASSIGN=25, ARROW=26, DEF=27, EXTENSION=28, EQUAL=29, NOT_EQUAL=30, 
		GT=31, LT=32, GEQ=33, LEQ=34, RAISE=35, PLUS=36, MINUS=37, TIMES=38, DIVIDE=39, 
		MOD=40, AND=41, OR=42, NOT=43, SIZE=44, IN=45, FINAL=46, BOOL=47, FLOAT=48, 
		INT=49, CHAR=50, COLOR=51, IMAGE=52, STRING=53, RETURN=54, DO=55, WHILE=56, 
		FOR=57, IF=58, ELSE=59, TRUE=60, FALSE=61, NEW=62, FROM=63, RGBA=64, RGB=65, 
		BLANK=66, TEX_COL_REPL=67, GEN_LOOKUP=68, ABS=69, MIN=70, MAX=71, CLAMP=72, 
		RAND=73, PROB=74, FLIP_COIN=75, RED=76, GREEN=77, BLUE=78, ALPHA=79, WIDTH=80, 
		HEIGHT=81, HAS=82, LOOKUP=83, KEYS=84, PIXEL=85, ADD=86, REMOVE=87, DEFINE=88, 
		DRAW=89, AT=90, SUB=91, DOT=92, LINE=93, FILL=94, SECTION=95, CALL=96, 
		FLOAT_LIT=97, DEC_LIT=98, HEX_LIT=99, COL_LIT=100, CHAR_QUOTE=101, STR_QUOTE=102, 
		STRING_LIT=103, CHAR_LIT=104, ESC_CHAR=105, IDENTIFIER=106, PATH=107;
	public static final int
		RULE_head_rule = 0, RULE_helper = 1, RULE_func_body = 2, RULE_signature = 3, 
		RULE_param_list = 4, RULE_declaration = 5, RULE_type = 6, RULE_func_type = 7, 
		RULE_param_types = 8, RULE_body = 9, RULE_stat = 10, RULE_return_stat = 11, 
		RULE_loop_stat = 12, RULE_iteration_def = 13, RULE_while_def = 14, RULE_for_def = 15, 
		RULE_if_stat = 16, RULE_if_def = 17, RULE_expr = 18, RULE_k_v_pairs = 19, 
		RULE_k_v_pair = 20, RULE_args = 21, RULE_assignment = 22, RULE_var_init = 23, 
		RULE_var_def = 24, RULE_assignable = 25, RULE_ident = 26, RULE_literal = 27, 
		RULE_int_lit = 28, RULE_bool_lit = 29;
	private static String[] makeRuleNames() {
		return new String[] {
			"head_rule", "helper", "func_body", "signature", "param_list", "declaration", 
			"type", "func_type", "param_types", "body", "stat", "return_stat", "loop_stat", 
			"iteration_def", "while_def", "for_def", "if_stat", "if_def", "expr", 
			"k_v_pairs", "k_v_pair", "args", "assignment", "var_init", "var_def", 
			"assignable", "ident", "literal", "int_lit", "bool_lit"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'('", "')'", "'['", "']'", "'{'", "'}'", "';'", 
			"':'", "','", "'.'", "'|'", "'?'", "'='", "'++'", "'--'", "'+='", "'-='", 
			"'*='", "'/='", "'%='", "'&='", "'|='", "'->'", "'::'", "'$'", "'=='", 
			"'!='", "'>'", "'<'", "'>='", "'<='", "'^'", "'+'", "'-'", "'*'", "'/'", 
			"'%'", "'&&'", "'||'", "'!'", "'#|'", "'in'", null, "'bool'", "'float'", 
			"'int'", "'char'", "'color'", "'image'", "'string'", "'return'", "'do'", 
			"'while'", "'for'", "'if'", "'else'", "'true'", "'false'", "'new'", "'from'", 
			"'rgba'", "'rgb'", "'blank'", "'tex_col_repl'", "'gen_lookup'", "'abs'", 
			"'min'", "'max'", "'clamp'", "'rand'", "'prob'", "'flip_coin'", null, 
			null, null, null, null, null, "'.has'", "'.lookup'", "'.keys'", "'.pixel'", 
			"'.add'", "'.remove'", "'.define'", "'.draw'", "'.at'", "'.sub'", "'.dot'", 
			"'.line'", "'.fill'", "'.section'", "'.call'", null, null, null, null, 
			"'''", "'\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "LINE_COMMENT", "MULTILINE_COMMENT", "LPAREN", "RPAREN", 
			"LBRACKET", "RBRACKET", "LCURLY", "RCURLY", "SEMICOLON", "COLON", "COMMA", 
			"PERIOD", "PIPE", "QUESTION", "ASSIGN", "INCREMENT", "DECREMENT", "ADD_ASSIGN", 
			"SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", "AND_ASSIGN", 
			"OR_ASSIGN", "ARROW", "DEF", "EXTENSION", "EQUAL", "NOT_EQUAL", "GT", 
			"LT", "GEQ", "LEQ", "RAISE", "PLUS", "MINUS", "TIMES", "DIVIDE", "MOD", 
			"AND", "OR", "NOT", "SIZE", "IN", "FINAL", "BOOL", "FLOAT", "INT", "CHAR", 
			"COLOR", "IMAGE", "STRING", "RETURN", "DO", "WHILE", "FOR", "IF", "ELSE", 
			"TRUE", "FALSE", "NEW", "FROM", "RGBA", "RGB", "BLANK", "TEX_COL_REPL", 
			"GEN_LOOKUP", "ABS", "MIN", "MAX", "CLAMP", "RAND", "PROB", "FLIP_COIN", 
			"RED", "GREEN", "BLUE", "ALPHA", "WIDTH", "HEIGHT", "HAS", "LOOKUP", 
			"KEYS", "PIXEL", "ADD", "REMOVE", "DEFINE", "DRAW", "AT", "SUB", "DOT", 
			"LINE", "FILL", "SECTION", "CALL", "FLOAT_LIT", "DEC_LIT", "HEX_LIT", 
			"COL_LIT", "CHAR_QUOTE", "STR_QUOTE", "STRING_LIT", "CHAR_LIT", "ESC_CHAR", 
			"IDENTIFIER", "PATH"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ScriptParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Head_ruleContext extends ParserRuleContext {
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public Func_bodyContext func_body() {
			return getRuleContext(Func_bodyContext.class,0);
		}
		public List<HelperContext> helper() {
			return getRuleContexts(HelperContext.class);
		}
		public HelperContext helper(int i) {
			return getRuleContext(HelperContext.class,i);
		}
		public Head_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_head_rule; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitHead_rule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Head_ruleContext head_rule() throws RecognitionException {
		Head_ruleContext _localctx = new Head_ruleContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_head_rule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			signature();
			setState(61);
			func_body();
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(62);
				helper();
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HelperContext extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public Func_bodyContext func_body() {
			return getRuleContext(Func_bodyContext.class,0);
		}
		public HelperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_helper; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitHelper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HelperContext helper() throws RecognitionException {
		HelperContext _localctx = new HelperContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_helper);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			ident();
			setState(69);
			signature();
			setState(70);
			func_body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_bodyContext extends ParserRuleContext {
		public Func_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_body; }
	 
		public Func_bodyContext() { }
		public void copyFrom(Func_bodyContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionalFuncBodyContext extends Func_bodyContext {
		public TerminalNode ARROW() { return getToken(ScriptParser.ARROW, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FunctionalFuncBodyContext(Func_bodyContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFunctionalFuncBody(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StandardFuncBodyContext extends Func_bodyContext {
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public StandardFuncBodyContext(Func_bodyContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitStandardFuncBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_bodyContext func_body() throws RecognitionException {
		Func_bodyContext _localctx = new Func_bodyContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_func_body);
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case LBRACKET:
			case LCURLY:
			case DEF:
			case EXTENSION:
			case LT:
			case MINUS:
			case NOT:
			case SIZE:
			case FINAL:
			case BOOL:
			case FLOAT:
			case INT:
			case CHAR:
			case COLOR:
			case IMAGE:
			case STRING:
			case RETURN:
			case DO:
			case WHILE:
			case FOR:
			case IF:
			case TRUE:
			case FALSE:
			case NEW:
			case FROM:
			case RGBA:
			case RGB:
			case BLANK:
			case TEX_COL_REPL:
			case GEN_LOOKUP:
			case ABS:
			case MIN:
			case MAX:
			case CLAMP:
			case RAND:
			case PROB:
			case FLIP_COIN:
			case FLOAT_LIT:
			case DEC_LIT:
			case HEX_LIT:
			case COL_LIT:
			case STRING_LIT:
			case CHAR_LIT:
			case IDENTIFIER:
				_localctx = new StandardFuncBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				body();
				}
				break;
			case ARROW:
				_localctx = new FunctionalFuncBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(73);
				match(ARROW);
				setState(74);
				expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SignatureContext extends ParserRuleContext {
		public SignatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signature; }
	 
		public SignatureContext() { }
		public void copyFrom(SignatureContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VoidReturnSignatureContext extends SignatureContext {
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public VoidReturnSignatureContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitVoidReturnSignature(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeReturnSignatureContext extends SignatureContext {
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode ARROW() { return getToken(ScriptParser.ARROW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public TypeReturnSignatureContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitTypeReturnSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignatureContext signature() throws RecognitionException {
		SignatureContext _localctx = new SignatureContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_signature);
		int _la;
		try {
			setState(90);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new VoidReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				match(LPAREN);
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17944029765304592L) != 0) || _la==IDENTIFIER) {
					{
					setState(78);
					param_list();
					}
				}

				setState(81);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new TypeReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(82);
				match(LPAREN);
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17944029765304592L) != 0) || _la==IDENTIFIER) {
					{
					setState(83);
					param_list();
					}
				}

				setState(86);
				match(ARROW);
				setState(87);
				type(0);
				setState(88);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Param_listContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public Param_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitParam_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Param_listContext param_list() throws RecognitionException {
		Param_listContext _localctx = new Param_listContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_param_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			declaration();
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(93);
				match(COMMA);
				setState(94);
				declaration();
				}
				}
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode FINAL() { return getToken(ScriptParser.FINAL, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINAL) {
				{
				setState(100);
				match(FINAL);
				}
			}

			setState(103);
			type(0);
			setState(104);
			ident();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolTypeContext extends TypeContext {
		public TerminalNode BOOL() { return getToken(ScriptParser.BOOL, 0); }
		public BoolTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringTypeContext extends TypeContext {
		public TerminalNode STRING() { return getToken(ScriptParser.STRING, 0); }
		public StringTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitStringType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionTypeContext extends TypeContext {
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public Func_typeContext func_type() {
			return getRuleContext(Func_typeContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public FunctionTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFunctionType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MapTypeContext extends TypeContext {
		public TypeContext key;
		public TypeContext val;
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public TerminalNode COLON() { return getToken(ScriptParser.COLON, 0); }
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public MapTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMapType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntTypeContext extends TypeContext {
		public TerminalNode INT() { return getToken(ScriptParser.INT, 0); }
		public IntTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatTypeContext extends TypeContext {
		public TerminalNode FLOAT() { return getToken(ScriptParser.FLOAT, 0); }
		public FloatTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFloatType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageTypeContext extends TypeContext {
		public TerminalNode IMAGE() { return getToken(ScriptParser.IMAGE, 0); }
		public ImageTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitImageType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(ScriptParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(ScriptParser.RBRACKET, 0); }
		public ArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
		public SetTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitSetType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExtensionTypeContext extends TypeContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExtensionTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExtensionType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LT() { return getToken(ScriptParser.LT, 0); }
		public TerminalNode GT() { return getToken(ScriptParser.GT, 0); }
		public ListTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitListType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorTypeContext extends TypeContext {
		public TerminalNode COLOR() { return getToken(ScriptParser.COLOR, 0); }
		public ColorTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitColorType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CharTypeContext extends TypeContext {
		public TerminalNode CHAR() { return getToken(ScriptParser.CHAR, 0); }
		public CharTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitCharType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
				{
				_localctx = new BoolTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(107);
				match(BOOL);
				}
				break;
			case INT:
				{
				_localctx = new IntTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(108);
				match(INT);
				}
				break;
			case FLOAT:
				{
				_localctx = new FloatTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(109);
				match(FLOAT);
				}
				break;
			case CHAR:
				{
				_localctx = new CharTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(110);
				match(CHAR);
				}
				break;
			case STRING:
				{
				_localctx = new StringTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(111);
				match(STRING);
				}
				break;
			case IMAGE:
				{
				_localctx = new ImageTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(112);
				match(IMAGE);
				}
				break;
			case COLOR:
				{
				_localctx = new ColorTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(113);
				match(COLOR);
				}
				break;
			case LCURLY:
				{
				_localctx = new MapTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(114);
				match(LCURLY);
				setState(115);
				((MapTypeContext)_localctx).key = type(0);
				setState(116);
				match(COLON);
				setState(117);
				((MapTypeContext)_localctx).val = type(0);
				setState(118);
				match(RCURLY);
				}
				break;
			case LPAREN:
				{
				_localctx = new FunctionTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(120);
				match(LPAREN);
				setState(121);
				func_type();
				setState(122);
				match(RPAREN);
				}
				break;
			case IDENTIFIER:
				{
				_localctx = new ExtensionTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(124);
				ident();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(138);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(136);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(127);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(128);
						match(LBRACKET);
						setState(129);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new ListTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(130);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(131);
						match(LT);
						setState(132);
						match(GT);
						}
						break;
					case 3:
						{
						_localctx = new SetTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(133);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(134);
						match(LCURLY);
						setState(135);
						match(RCURLY);
						}
						break;
					}
					} 
				}
				setState(140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_typeContext extends ParserRuleContext {
		public TypeContext ret;
		public Param_typesContext param_types() {
			return getRuleContext(Param_typesContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(ScriptParser.ARROW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Func_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFunc_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_typeContext func_type() throws RecognitionException {
		Func_typeContext _localctx = new Func_typeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_func_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			param_types();
			setState(142);
			match(ARROW);
			setState(143);
			((Func_typeContext)_localctx).ret = type(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Param_typesContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public Param_typesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_types; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitParam_types(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Param_typesContext param_types() throws RecognitionException {
		Param_typesContext _localctx = new Param_typesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_param_types);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17873661021126928L) != 0) || _la==IDENTIFIER) {
				{
				setState(145);
				type(0);
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(146);
					match(COMMA);
					setState(147);
					type(0);
					}
					}
					setState(152);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BodyContext extends ParserRuleContext {
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
	 
		public BodyContext() { }
		public void copyFrom(BodyContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SingleStatBodyContext extends BodyContext {
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public SingleStatBodyContext(BodyContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitSingleStatBody(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComplexBodyContext extends BodyContext {
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ComplexBodyContext(BodyContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitComplexBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_body);
		int _la;
		try {
			setState(164);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new SingleStatBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(155);
				stat();
				}
				break;
			case 2:
				_localctx = new ComplexBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(156);
				match(LCURLY);
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -576504590631960240L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 6176162975743L) != 0)) {
					{
					{
					setState(157);
					stat();
					}
					}
					setState(162);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(163);
				match(RCURLY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatContext extends ParserRuleContext {
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RemoveFromCollectionContext extends StatContext {
		public ExprContext col;
		public ExprContext arg;
		public TerminalNode REMOVE() { return getToken(ScriptParser.REMOVE, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RemoveFromCollectionContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitRemoveFromCollection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExtScopedCallStatementContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EXTENSION() { return getToken(ScriptParser.EXTENSION, 0); }
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public ExtScopedCallStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExtScopedCallStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LoopStatementContext extends StatContext {
		public Loop_statContext loop_stat() {
			return getRuleContext(Loop_statContext.class,0);
		}
		public LoopStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DrawLineStatementContext extends StatContext {
		public ExprContext canvas;
		public ExprContext col;
		public ExprContext breadth;
		public ExprContext x1;
		public ExprContext y1;
		public ExprContext x2;
		public ExprContext y2;
		public TerminalNode LINE() { return getToken(ScriptParser.LINE, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DrawLineStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDrawLineStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DotStatementContext extends StatContext {
		public ExprContext canvas;
		public ExprContext col;
		public ExprContext x;
		public ExprContext y;
		public TerminalNode DOT() { return getToken(ScriptParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DotStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDotStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends StatContext {
		public If_statContext if_stat() {
			return getRuleContext(If_statContext.class,0);
		}
		public IfStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefineMapEntryStatementContext extends StatContext {
		public ExprContext map;
		public ExprContext key;
		public ExprContext val;
		public TerminalNode DEFINE() { return getToken(ScriptParser.DEFINE, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DefineMapEntryStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDefineMapEntryStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentStatementContext extends StatContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public AssignmentStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DrawOntoImageStatementContext extends StatContext {
		public ExprContext canvas;
		public ExprContext img;
		public ExprContext x;
		public ExprContext y;
		public TerminalNode DRAW() { return getToken(ScriptParser.DRAW, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DrawOntoImageStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDrawOntoImageStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExtFuncCallStatementContext extends StatContext {
		public TerminalNode EXTENSION() { return getToken(ScriptParser.EXTENSION, 0); }
		public TerminalNode PATH() { return getToken(ScriptParser.PATH, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public ExtFuncCallStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExtFuncCallStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends StatContext {
		public Return_statContext return_stat() {
			return getRuleContext(Return_statContext.class,0);
		}
		public ReturnStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddToCollectionContext extends StatContext {
		public ExprContext col;
		public ExprContext elem;
		public ExprContext index;
		public TerminalNode ADD() { return getToken(ScriptParser.ADD, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public AddToCollectionContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitAddToCollection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FillStatementContext extends StatContext {
		public ExprContext canvas;
		public ExprContext col;
		public ExprContext x;
		public ExprContext y;
		public ExprContext w;
		public ExprContext h;
		public TerminalNode FILL() { return getToken(ScriptParser.FILL, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public FillStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFillStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarDefStatementContext extends StatContext {
		public Var_defContext var_def() {
			return getRuleContext(Var_defContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public VarDefStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitVarDefStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_stat);
		int _la;
		try {
			setState(267);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new LoopStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(166);
				loop_stat();
				}
				break;
			case 2:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
				if_stat();
				}
				break;
			case 3:
				_localctx = new VarDefStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(168);
				var_def();
				setState(169);
				match(SEMICOLON);
				}
				break;
			case 4:
				_localctx = new AssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(171);
				assignment();
				setState(172);
				match(SEMICOLON);
				}
				break;
			case 5:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(174);
				return_stat();
				}
				break;
			case 6:
				_localctx = new AddToCollectionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(175);
				((AddToCollectionContext)_localctx).col = expr(0);
				setState(176);
				match(ADD);
				setState(177);
				match(LPAREN);
				setState(178);
				((AddToCollectionContext)_localctx).elem = expr(0);
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(179);
					match(COMMA);
					setState(180);
					((AddToCollectionContext)_localctx).index = expr(0);
					}
				}

				setState(183);
				match(RPAREN);
				setState(184);
				match(SEMICOLON);
				}
				break;
			case 7:
				_localctx = new RemoveFromCollectionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(186);
				((RemoveFromCollectionContext)_localctx).col = expr(0);
				setState(187);
				match(REMOVE);
				setState(188);
				match(LPAREN);
				setState(189);
				((RemoveFromCollectionContext)_localctx).arg = expr(0);
				setState(190);
				match(RPAREN);
				setState(191);
				match(SEMICOLON);
				}
				break;
			case 8:
				_localctx = new DefineMapEntryStatementContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(193);
				((DefineMapEntryStatementContext)_localctx).map = expr(0);
				setState(194);
				match(DEFINE);
				setState(195);
				match(LPAREN);
				setState(196);
				((DefineMapEntryStatementContext)_localctx).key = expr(0);
				setState(197);
				match(COMMA);
				setState(198);
				((DefineMapEntryStatementContext)_localctx).val = expr(0);
				setState(199);
				match(RPAREN);
				setState(200);
				match(SEMICOLON);
				}
				break;
			case 9:
				_localctx = new DrawOntoImageStatementContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(202);
				((DrawOntoImageStatementContext)_localctx).canvas = expr(0);
				setState(203);
				match(DRAW);
				setState(204);
				match(LPAREN);
				setState(205);
				((DrawOntoImageStatementContext)_localctx).img = expr(0);
				setState(206);
				match(COMMA);
				setState(207);
				((DrawOntoImageStatementContext)_localctx).x = expr(0);
				setState(208);
				match(COMMA);
				setState(209);
				((DrawOntoImageStatementContext)_localctx).y = expr(0);
				setState(210);
				match(RPAREN);
				setState(211);
				match(SEMICOLON);
				}
				break;
			case 10:
				_localctx = new DotStatementContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(213);
				((DotStatementContext)_localctx).canvas = expr(0);
				setState(214);
				match(DOT);
				setState(215);
				match(LPAREN);
				setState(216);
				((DotStatementContext)_localctx).col = expr(0);
				setState(217);
				match(COMMA);
				setState(218);
				((DotStatementContext)_localctx).x = expr(0);
				setState(219);
				match(COMMA);
				setState(220);
				((DotStatementContext)_localctx).y = expr(0);
				setState(221);
				match(RPAREN);
				setState(222);
				match(SEMICOLON);
				}
				break;
			case 11:
				_localctx = new DrawLineStatementContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(224);
				((DrawLineStatementContext)_localctx).canvas = expr(0);
				setState(225);
				match(LINE);
				setState(226);
				match(LPAREN);
				setState(227);
				((DrawLineStatementContext)_localctx).col = expr(0);
				setState(228);
				match(COMMA);
				setState(229);
				((DrawLineStatementContext)_localctx).breadth = expr(0);
				setState(230);
				match(COMMA);
				setState(231);
				((DrawLineStatementContext)_localctx).x1 = expr(0);
				setState(232);
				match(COMMA);
				setState(233);
				((DrawLineStatementContext)_localctx).y1 = expr(0);
				setState(234);
				match(COMMA);
				setState(235);
				((DrawLineStatementContext)_localctx).x2 = expr(0);
				setState(236);
				match(COMMA);
				setState(237);
				((DrawLineStatementContext)_localctx).y2 = expr(0);
				setState(238);
				match(RPAREN);
				setState(239);
				match(SEMICOLON);
				}
				break;
			case 12:
				_localctx = new FillStatementContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(241);
				((FillStatementContext)_localctx).canvas = expr(0);
				setState(242);
				match(FILL);
				setState(243);
				match(LPAREN);
				setState(244);
				((FillStatementContext)_localctx).col = expr(0);
				setState(245);
				match(COMMA);
				setState(246);
				((FillStatementContext)_localctx).x = expr(0);
				setState(247);
				match(COMMA);
				setState(248);
				((FillStatementContext)_localctx).y = expr(0);
				setState(249);
				match(COMMA);
				setState(250);
				((FillStatementContext)_localctx).w = expr(0);
				setState(251);
				match(COMMA);
				setState(252);
				((FillStatementContext)_localctx).h = expr(0);
				setState(253);
				match(RPAREN);
				setState(254);
				match(SEMICOLON);
				}
				break;
			case 13:
				_localctx = new ExtScopedCallStatementContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(256);
				expr(0);
				setState(257);
				match(EXTENSION);
				setState(258);
				ident();
				setState(259);
				args();
				setState(260);
				match(SEMICOLON);
				}
				break;
			case 14:
				_localctx = new ExtFuncCallStatementContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(262);
				match(EXTENSION);
				setState(263);
				match(PATH);
				setState(264);
				args();
				setState(265);
				match(SEMICOLON);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Return_statContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(ScriptParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Return_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_stat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitReturn_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Return_statContext return_stat() throws RecognitionException {
		Return_statContext _localctx = new Return_statContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_return_stat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			match(RETURN);
			setState(271);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -1152894974191206064L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 6176162975743L) != 0)) {
				{
				setState(270);
				expr(0);
				}
			}

			setState(273);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Loop_statContext extends ParserRuleContext {
		public Loop_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop_stat; }
	 
		public Loop_statContext() { }
		public void copyFrom(Loop_statContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForLoopContext extends Loop_statContext {
		public For_defContext for_def() {
			return getRuleContext(For_defContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public ForLoopContext(Loop_statContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitForLoop(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IteratorLoopContext extends Loop_statContext {
		public Iteration_defContext iteration_def() {
			return getRuleContext(Iteration_defContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public IteratorLoopContext(Loop_statContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIteratorLoop(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoWhileLoopContext extends Loop_statContext {
		public TerminalNode DO() { return getToken(ScriptParser.DO, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public While_defContext while_def() {
			return getRuleContext(While_defContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScriptParser.SEMICOLON, 0); }
		public DoWhileLoopContext(Loop_statContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDoWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileLoopContext extends Loop_statContext {
		public While_defContext while_def() {
			return getRuleContext(While_defContext.class,0);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public WhileLoopContext(Loop_statContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Loop_statContext loop_stat() throws RecognitionException {
		Loop_statContext _localctx = new Loop_statContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_loop_stat);
		try {
			setState(289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new WhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(275);
				while_def();
				setState(276);
				body();
				}
				break;
			case 2:
				_localctx = new IteratorLoopContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
				iteration_def();
				setState(279);
				body();
				}
				break;
			case 3:
				_localctx = new ForLoopContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(281);
				for_def();
				setState(282);
				body();
				}
				break;
			case 4:
				_localctx = new DoWhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(284);
				match(DO);
				setState(285);
				body();
				setState(286);
				while_def();
				setState(287);
				match(SEMICOLON);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Iteration_defContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(ScriptParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public TerminalNode IN() { return getToken(ScriptParser.IN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public Iteration_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteration_def; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIteration_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Iteration_defContext iteration_def() throws RecognitionException {
		Iteration_defContext _localctx = new Iteration_defContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_iteration_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			match(FOR);
			setState(292);
			match(LPAREN);
			setState(293);
			declaration();
			setState(294);
			match(IN);
			setState(295);
			expr(0);
			setState(296);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class While_defContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(ScriptParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public While_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_def; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitWhile_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_defContext while_def() throws RecognitionException {
		While_defContext _localctx = new While_defContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_while_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			match(WHILE);
			setState(299);
			match(LPAREN);
			setState(300);
			expr(0);
			setState(301);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class For_defContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(ScriptParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public Var_initContext var_init() {
			return getRuleContext(Var_initContext.class,0);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(ScriptParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(ScriptParser.SEMICOLON, i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public For_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_def; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFor_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final For_defContext for_def() throws RecognitionException {
		For_defContext _localctx = new For_defContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_for_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			match(FOR);
			setState(304);
			match(LPAREN);
			setState(305);
			var_init();
			setState(306);
			match(SEMICOLON);
			setState(307);
			expr(0);
			setState(308);
			match(SEMICOLON);
			setState(309);
			assignment();
			setState(310);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class If_statContext extends ParserRuleContext {
		public BodyContext elseBody;
		public List<If_defContext> if_def() {
			return getRuleContexts(If_defContext.class);
		}
		public If_defContext if_def(int i) {
			return getRuleContext(If_defContext.class,i);
		}
		public List<TerminalNode> ELSE() { return getTokens(ScriptParser.ELSE); }
		public TerminalNode ELSE(int i) {
			return getToken(ScriptParser.ELSE, i);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public If_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stat; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIf_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_statContext if_stat() throws RecognitionException {
		If_statContext _localctx = new If_statContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_if_stat);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			if_def();
			setState(317);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(313);
					match(ELSE);
					setState(314);
					if_def();
					}
					} 
				}
				setState(319);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			setState(322);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(320);
				match(ELSE);
				setState(321);
				((If_statContext)_localctx).elseBody = body();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class If_defContext extends ParserRuleContext {
		public ExprContext cond;
		public TerminalNode IF() { return getToken(ScriptParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public If_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_def; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIf_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_defContext if_def() throws RecognitionException {
		If_defContext _localctx = new If_defContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_if_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(IF);
			setState(325);
			match(LPAREN);
			setState(326);
			((If_defContext)_localctx).cond = expr(0);
			setState(327);
			match(RPAREN);
			setState(328);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MinCollectionExpressionContext extends ExprContext {
		public TerminalNode MIN() { return getToken(ScriptParser.MIN, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public MinCollectionExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMinCollectionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitArrayExpressionContext extends ExprContext {
		public TerminalNode LBRACKET() { return getToken(ScriptParser.LBRACKET, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RBRACKET() { return getToken(ScriptParser.RBRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public ExplicitArrayExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExplicitArrayExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultBinExpressionContext extends ExprContext {
		public ExprContext a;
		public Token op;
		public ExprContext b;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode TIMES() { return getToken(ScriptParser.TIMES, 0); }
		public TerminalNode DIVIDE() { return getToken(ScriptParser.DIVIDE, 0); }
		public TerminalNode MOD() { return getToken(ScriptParser.MOD, 0); }
		public MultBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMultBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageBoundExpressionContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode WIDTH() { return getToken(ScriptParser.WIDTH, 0); }
		public TerminalNode HEIGHT() { return getToken(ScriptParser.HEIGHT, 0); }
		public ImageBoundExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitImageBoundExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RandomTwoArgExpressionContext extends ExprContext {
		public ExprContext min;
		public ExprContext max;
		public TerminalNode RAND() { return getToken(ScriptParser.RAND, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RandomTwoArgExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitRandomTwoArgExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PowerBinExpressionContext extends ExprContext {
		public ExprContext a;
		public ExprContext b;
		public TerminalNode RAISE() { return getToken(ScriptParser.RAISE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public PowerBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitPowerBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionCallExpressionContext extends ExprContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public FunctionCallExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFunctionCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FlipCoinArgExpressionContext extends ExprContext {
		public ExprContext t;
		public ExprContext f;
		public TerminalNode FLIP_COIN() { return getToken(ScriptParser.FLIP_COIN, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public FlipCoinArgExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFlipCoinArgExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonBinExpressionContext extends ExprContext {
		public ExprContext a;
		public Token op;
		public ExprContext b;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(ScriptParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ScriptParser.NOT_EQUAL, 0); }
		public TerminalNode GT() { return getToken(ScriptParser.GT, 0); }
		public TerminalNode LT() { return getToken(ScriptParser.LT, 0); }
		public TerminalNode GEQ() { return getToken(ScriptParser.GEQ, 0); }
		public TerminalNode LEQ() { return getToken(ScriptParser.LEQ, 0); }
		public ComparisonBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitComparisonBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RGBColorExpressionContext extends ExprContext {
		public ExprContext r;
		public ExprContext g;
		public ExprContext b;
		public TerminalNode RGB() { return getToken(ScriptParser.RGB, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RGBColorExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitRGBColorExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignableExpressionContext extends ExprContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public AssignableExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitAssignableExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitSetExpressionContext extends ExprContext {
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public ExplicitSetExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExplicitSetExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExtScopedCallExpressionContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EXTENSION() { return getToken(ScriptParser.EXTENSION, 0); }
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public ExtScopedCallExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExtScopedCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExtFuncCallExpressionContext extends ExprContext {
		public TerminalNode EXTENSION() { return getToken(ScriptParser.EXTENSION, 0); }
		public TerminalNode PATH() { return getToken(ScriptParser.PATH, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public ExtFuncCallExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExtFuncCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubstringExpressionContext extends ExprContext {
		public ExprContext string;
		public ExprContext beg;
		public ExprContext end;
		public TerminalNode SUB() { return getToken(ScriptParser.SUB, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public SubstringExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitSubstringExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ContainsExpressionContext extends ExprContext {
		public ExprContext col;
		public ExprContext elem;
		public TerminalNode HAS() { return getToken(ScriptParser.HAS, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ContainsExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitContainsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralExpressionContext extends ExprContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MapKeysetExpressionContext extends ExprContext {
		public ExprContext map;
		public TerminalNode KEYS() { return getToken(ScriptParser.KEYS, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MapKeysetExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMapKeysetExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClampExpressionContext extends ExprContext {
		public ExprContext min;
		public ExprContext val;
		public ExprContext max;
		public TerminalNode CLAMP() { return getToken(ScriptParser.CLAMP, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ClampExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitClampExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NestedExpressionContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public NestedExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitNestedExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class HOFuncExpressionContext extends ExprContext {
		public TerminalNode DEF() { return getToken(ScriptParser.DEF, 0); }
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public HOFuncExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitHOFuncExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AbsoluteExpressionContext extends ExprContext {
		public TerminalNode ABS() { return getToken(ScriptParser.ABS, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public AbsoluteExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitAbsoluteExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GenLookupExpressionContext extends ExprContext {
		public ExprContext source;
		public ExprContext vert;
		public TerminalNode GEN_LOOKUP() { return getToken(ScriptParser.GEN_LOOKUP, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GenLookupExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitGenLookupExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorChannelExpressionContext extends ExprContext {
		public ExprContext c;
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RED() { return getToken(ScriptParser.RED, 0); }
		public TerminalNode GREEN() { return getToken(ScriptParser.GREEN, 0); }
		public TerminalNode BLUE() { return getToken(ScriptParser.BLUE, 0); }
		public TerminalNode ALPHA() { return getToken(ScriptParser.ALPHA, 0); }
		public ColorChannelExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitColorChannelExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CastExpressionContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CastExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitCastExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MinTwoArgExpressionContext extends ExprContext {
		public ExprContext a;
		public ExprContext b;
		public TerminalNode MIN() { return getToken(ScriptParser.MIN, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public MinTwoArgExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMinTwoArgExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageFromPathExpressionContext extends ExprContext {
		public TerminalNode FROM() { return getToken(ScriptParser.FROM, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public ImageFromPathExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitImageFromPathExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicBinExpressionContext extends ExprContext {
		public ExprContext a;
		public Token op;
		public ExprContext b;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(ScriptParser.OR, 0); }
		public TerminalNode AND() { return getToken(ScriptParser.AND, 0); }
		public LogicBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitLogicBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TernaryExpressionContext extends ExprContext {
		public ExprContext cond;
		public ExprContext if_;
		public ExprContext else_;
		public TerminalNode QUESTION() { return getToken(ScriptParser.QUESTION, 0); }
		public TerminalNode COLON() { return getToken(ScriptParser.COLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TernaryExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitTernaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FlipCoinBoolExpressionContext extends ExprContext {
		public TerminalNode FLIP_COIN() { return getToken(ScriptParser.FLIP_COIN, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public FlipCoinBoolExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFlipCoinBoolExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExpressionContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(ScriptParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(ScriptParser.NOT, 0); }
		public TerminalNode SIZE() { return getToken(ScriptParser.SIZE, 0); }
		public UnaryExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MapLookupExpressionContext extends ExprContext {
		public ExprContext map;
		public ExprContext elem;
		public TerminalNode LOOKUP() { return getToken(ScriptParser.LOOKUP, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public MapLookupExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMapLookupExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MaxCollectionExpressionContext extends ExprContext {
		public TerminalNode MAX() { return getToken(ScriptParser.MAX, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public MaxCollectionExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMaxCollectionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitListExpressionContext extends ExprContext {
		public TerminalNode LT() { return getToken(ScriptParser.LT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode GT() { return getToken(ScriptParser.GT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public ExplicitListExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExplicitListExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RandomExpressionContext extends ExprContext {
		public TerminalNode RAND() { return getToken(ScriptParser.RAND, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public RandomExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitRandomExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageOfBoundsExpressionContext extends ExprContext {
		public ExprContext width;
		public ExprContext height;
		public TerminalNode BLANK() { return getToken(ScriptParser.BLANK, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ImageOfBoundsExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitImageOfBoundsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MaxTwoArgExpressionContext extends ExprContext {
		public ExprContext a;
		public ExprContext b;
		public TerminalNode MAX() { return getToken(ScriptParser.MAX, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public MaxTwoArgExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMaxTwoArgExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewMapExpressionContext extends ExprContext {
		public TypeContext kt;
		public TypeContext vt;
		public TerminalNode NEW() { return getToken(ScriptParser.NEW, 0); }
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public TerminalNode COLON() { return getToken(ScriptParser.COLON, 0); }
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public NewMapExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitNewMapExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RGBAColorExpressionContext extends ExprContext {
		public ExprContext r;
		public ExprContext g;
		public ExprContext b;
		public ExprContext a;
		public TerminalNode RGBA() { return getToken(ScriptParser.RGBA, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RGBAColorExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitRGBAColorExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewSetExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScriptParser.NEW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
		public NewSetExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitNewSetExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageSectionExpressionContext extends ExprContext {
		public ExprContext img;
		public ExprContext x;
		public ExprContext y;
		public ExprContext w;
		public ExprContext h;
		public TerminalNode SECTION() { return getToken(ScriptParser.SECTION, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ImageSectionExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitImageSectionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewListExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScriptParser.NEW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LT() { return getToken(ScriptParser.LT, 0); }
		public TerminalNode GT() { return getToken(ScriptParser.GT, 0); }
		public NewListExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitNewListExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ProbabilityExpressionContext extends ExprContext {
		public TerminalNode PROB() { return getToken(ScriptParser.PROB, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public ProbabilityExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitProbabilityExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TextureColorReplaceExpressionContext extends ExprContext {
		public ExprContext texture;
		public ExprContext lookup;
		public ExprContext replace;
		public TerminalNode TEX_COL_REPL() { return getToken(ScriptParser.TEX_COL_REPL, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TextureColorReplaceExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitTextureColorReplaceExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitMapExpressionContext extends ExprContext {
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public K_v_pairsContext k_v_pairs() {
			return getRuleContext(K_v_pairsContext.class,0);
		}
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
		public ExplicitMapExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExplicitMapExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class HOFuncCallExpressionContext extends ExprContext {
		public ExprContext func;
		public TerminalNode CALL() { return getToken(ScriptParser.CALL, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public HOFuncCallExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitHOFuncCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorAtPixelExpressionContext extends ExprContext {
		public ExprContext img;
		public ExprContext x;
		public ExprContext y;
		public TerminalNode PIXEL() { return getToken(ScriptParser.PIXEL, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScriptParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ColorAtPixelExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitColorAtPixelExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticBinExpressionContext extends ExprContext {
		public ExprContext a;
		public Token op;
		public ExprContext b;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(ScriptParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(ScriptParser.MINUS, 0); }
		public ArithmeticBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitArithmeticBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CharAtExpressionContext extends ExprContext {
		public ExprContext string;
		public ExprContext index;
		public TerminalNode AT() { return getToken(ScriptParser.AT, 0); }
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CharAtExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitCharAtExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScriptParser.NEW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(ScriptParser.LBRACKET, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(ScriptParser.RBRACKET, 0); }
		public NewArrayExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitNewArrayExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(523);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				_localctx = new NestedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(331);
				match(LPAREN);
				setState(332);
				expr(0);
				setState(333);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new AbsoluteExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(335);
				match(ABS);
				setState(336);
				match(LPAREN);
				setState(337);
				expr(0);
				setState(338);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new MinCollectionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(340);
				match(MIN);
				setState(341);
				match(LPAREN);
				setState(342);
				expr(0);
				setState(343);
				match(RPAREN);
				}
				break;
			case 4:
				{
				_localctx = new MinTwoArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(345);
				match(MIN);
				setState(346);
				match(LPAREN);
				setState(347);
				((MinTwoArgExpressionContext)_localctx).a = expr(0);
				setState(348);
				match(COMMA);
				setState(349);
				((MinTwoArgExpressionContext)_localctx).b = expr(0);
				setState(350);
				match(RPAREN);
				}
				break;
			case 5:
				{
				_localctx = new MaxCollectionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(352);
				match(MAX);
				setState(353);
				match(LPAREN);
				setState(354);
				expr(0);
				setState(355);
				match(RPAREN);
				}
				break;
			case 6:
				{
				_localctx = new MaxTwoArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(357);
				match(MAX);
				setState(358);
				match(LPAREN);
				setState(359);
				((MaxTwoArgExpressionContext)_localctx).a = expr(0);
				setState(360);
				match(COMMA);
				setState(361);
				((MaxTwoArgExpressionContext)_localctx).b = expr(0);
				setState(362);
				match(RPAREN);
				}
				break;
			case 7:
				{
				_localctx = new ClampExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(364);
				match(CLAMP);
				setState(365);
				match(LPAREN);
				setState(366);
				((ClampExpressionContext)_localctx).min = expr(0);
				setState(367);
				match(COMMA);
				setState(368);
				((ClampExpressionContext)_localctx).val = expr(0);
				setState(369);
				match(COMMA);
				setState(370);
				((ClampExpressionContext)_localctx).max = expr(0);
				setState(371);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new RandomExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(373);
				match(RAND);
				setState(374);
				match(LPAREN);
				setState(375);
				match(RPAREN);
				}
				break;
			case 9:
				{
				_localctx = new RandomTwoArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(376);
				match(RAND);
				setState(377);
				match(LPAREN);
				setState(378);
				((RandomTwoArgExpressionContext)_localctx).min = expr(0);
				setState(379);
				match(COMMA);
				setState(380);
				((RandomTwoArgExpressionContext)_localctx).max = expr(0);
				setState(381);
				match(RPAREN);
				}
				break;
			case 10:
				{
				_localctx = new ProbabilityExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(383);
				match(PROB);
				setState(384);
				match(LPAREN);
				setState(385);
				expr(0);
				setState(386);
				match(RPAREN);
				}
				break;
			case 11:
				{
				_localctx = new FlipCoinBoolExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(388);
				match(FLIP_COIN);
				setState(389);
				match(LPAREN);
				setState(390);
				match(RPAREN);
				}
				break;
			case 12:
				{
				_localctx = new FlipCoinArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(391);
				match(FLIP_COIN);
				setState(392);
				match(LPAREN);
				setState(393);
				((FlipCoinArgExpressionContext)_localctx).t = expr(0);
				setState(394);
				match(COMMA);
				setState(395);
				((FlipCoinArgExpressionContext)_localctx).f = expr(0);
				setState(396);
				match(RPAREN);
				}
				break;
			case 13:
				{
				_localctx = new ImageFromPathExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(398);
				match(FROM);
				setState(399);
				match(LPAREN);
				setState(400);
				expr(0);
				setState(401);
				match(RPAREN);
				}
				break;
			case 14:
				{
				_localctx = new ImageOfBoundsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(403);
				match(BLANK);
				setState(404);
				match(LPAREN);
				setState(405);
				((ImageOfBoundsExpressionContext)_localctx).width = expr(0);
				setState(406);
				match(COMMA);
				setState(407);
				((ImageOfBoundsExpressionContext)_localctx).height = expr(0);
				setState(408);
				match(RPAREN);
				}
				break;
			case 15:
				{
				_localctx = new TextureColorReplaceExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(410);
				match(TEX_COL_REPL);
				setState(411);
				match(LPAREN);
				setState(412);
				((TextureColorReplaceExpressionContext)_localctx).texture = expr(0);
				setState(413);
				match(COMMA);
				setState(414);
				((TextureColorReplaceExpressionContext)_localctx).lookup = expr(0);
				setState(415);
				match(COMMA);
				setState(416);
				((TextureColorReplaceExpressionContext)_localctx).replace = expr(0);
				setState(417);
				match(RPAREN);
				}
				break;
			case 16:
				{
				_localctx = new GenLookupExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(419);
				match(GEN_LOOKUP);
				setState(420);
				match(LPAREN);
				setState(421);
				((GenLookupExpressionContext)_localctx).source = expr(0);
				setState(422);
				match(COMMA);
				setState(423);
				((GenLookupExpressionContext)_localctx).vert = expr(0);
				setState(424);
				match(RPAREN);
				}
				break;
			case 17:
				{
				_localctx = new RGBColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(426);
				match(RGB);
				setState(427);
				match(LPAREN);
				setState(428);
				((RGBColorExpressionContext)_localctx).r = expr(0);
				setState(429);
				match(COMMA);
				setState(430);
				((RGBColorExpressionContext)_localctx).g = expr(0);
				setState(431);
				match(COMMA);
				setState(432);
				((RGBColorExpressionContext)_localctx).b = expr(0);
				setState(433);
				match(RPAREN);
				}
				break;
			case 18:
				{
				_localctx = new RGBAColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(435);
				match(RGBA);
				setState(436);
				match(LPAREN);
				setState(437);
				((RGBAColorExpressionContext)_localctx).r = expr(0);
				setState(438);
				match(COMMA);
				setState(439);
				((RGBAColorExpressionContext)_localctx).g = expr(0);
				setState(440);
				match(COMMA);
				setState(441);
				((RGBAColorExpressionContext)_localctx).b = expr(0);
				setState(442);
				match(COMMA);
				setState(443);
				((RGBAColorExpressionContext)_localctx).a = expr(0);
				setState(444);
				match(RPAREN);
				}
				break;
			case 19:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(446);
				((UnaryExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 26525718020096L) != 0)) ) {
					((UnaryExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(447);
				expr(21);
				}
				break;
			case 20:
				{
				_localctx = new CastExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(448);
				match(LPAREN);
				setState(449);
				type(0);
				setState(450);
				match(RPAREN);
				setState(451);
				expr(20);
				}
				break;
			case 21:
				{
				_localctx = new ExplicitMapExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(453);
				match(LCURLY);
				setState(454);
				k_v_pairs();
				setState(455);
				match(RCURLY);
				}
				break;
			case 22:
				{
				_localctx = new ExplicitArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(457);
				match(LBRACKET);
				setState(458);
				expr(0);
				setState(463);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(459);
					match(COMMA);
					setState(460);
					expr(0);
					}
					}
					setState(465);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(466);
				match(RBRACKET);
				}
				break;
			case 23:
				{
				_localctx = new ExplicitListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(468);
				match(LT);
				setState(469);
				expr(0);
				setState(474);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(470);
					match(COMMA);
					setState(471);
					expr(0);
					}
					}
					setState(476);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(477);
				match(GT);
				}
				break;
			case 24:
				{
				_localctx = new ExplicitSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(479);
				match(LCURLY);
				setState(480);
				expr(0);
				setState(485);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(481);
					match(COMMA);
					setState(482);
					expr(0);
					}
					}
					setState(487);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(488);
				match(RCURLY);
				}
				break;
			case 25:
				{
				_localctx = new NewArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(490);
				match(NEW);
				setState(491);
				type(0);
				setState(492);
				match(LBRACKET);
				setState(493);
				expr(0);
				setState(494);
				match(RBRACKET);
				}
				break;
			case 26:
				{
				_localctx = new NewListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(496);
				match(NEW);
				setState(497);
				type(0);
				setState(498);
				match(LT);
				setState(499);
				match(GT);
				}
				break;
			case 27:
				{
				_localctx = new NewSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(501);
				match(NEW);
				setState(502);
				type(0);
				setState(503);
				match(LCURLY);
				setState(504);
				match(RCURLY);
				}
				break;
			case 28:
				{
				_localctx = new NewMapExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(506);
				match(NEW);
				setState(507);
				match(LCURLY);
				setState(508);
				((NewMapExpressionContext)_localctx).kt = type(0);
				setState(509);
				match(COLON);
				setState(510);
				((NewMapExpressionContext)_localctx).vt = type(0);
				setState(511);
				match(RCURLY);
				}
				break;
			case 29:
				{
				_localctx = new FunctionCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(513);
				ident();
				setState(514);
				args();
				}
				break;
			case 30:
				{
				_localctx = new HOFuncExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(516);
				match(DEF);
				setState(517);
				ident();
				}
				break;
			case 31:
				{
				_localctx = new ExtFuncCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(518);
				match(EXTENSION);
				setState(519);
				match(PATH);
				setState(520);
				args();
				}
				break;
			case 32:
				{
				_localctx = new AssignableExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(521);
				assignable();
				}
				break;
			case 33:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(522);
				literal();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(610);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(608);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ArithmeticBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(525);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(526);
						((ArithmeticBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((ArithmeticBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(527);
						((ArithmeticBinExpressionContext)_localctx).b = expr(20);
						}
						break;
					case 2:
						{
						_localctx = new MultBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((MultBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(528);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(529);
						((MultBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1924145348608L) != 0)) ) {
							((MultBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(530);
						((MultBinExpressionContext)_localctx).b = expr(19);
						}
						break;
					case 3:
						{
						_localctx = new PowerBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((PowerBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(531);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(532);
						match(RAISE);
						setState(533);
						((PowerBinExpressionContext)_localctx).b = expr(18);
						}
						break;
					case 4:
						{
						_localctx = new ComparisonBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ComparisonBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(534);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(535);
						((ComparisonBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 33822867456L) != 0)) ) {
							((ComparisonBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(536);
						((ComparisonBinExpressionContext)_localctx).b = expr(17);
						}
						break;
					case 5:
						{
						_localctx = new LogicBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((LogicBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(537);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(538);
						((LogicBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((LogicBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(539);
						((LogicBinExpressionContext)_localctx).b = expr(16);
						}
						break;
					case 6:
						{
						_localctx = new TernaryExpressionContext(new ExprContext(_parentctx, _parentState));
						((TernaryExpressionContext)_localctx).cond = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(540);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(541);
						match(QUESTION);
						setState(542);
						((TernaryExpressionContext)_localctx).if_ = expr(0);
						setState(543);
						match(COLON);
						setState(544);
						((TernaryExpressionContext)_localctx).else_ = expr(15);
						}
						break;
					case 7:
						{
						_localctx = new ContainsExpressionContext(new ExprContext(_parentctx, _parentState));
						((ContainsExpressionContext)_localctx).col = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(546);
						if (!(precpred(_ctx, 49))) throw new FailedPredicateException(this, "precpred(_ctx, 49)");
						setState(547);
						match(HAS);
						setState(548);
						match(LPAREN);
						setState(549);
						((ContainsExpressionContext)_localctx).elem = expr(0);
						setState(550);
						match(RPAREN);
						}
						break;
					case 8:
						{
						_localctx = new MapLookupExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapLookupExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(552);
						if (!(precpred(_ctx, 48))) throw new FailedPredicateException(this, "precpred(_ctx, 48)");
						setState(553);
						match(LOOKUP);
						setState(554);
						match(LPAREN);
						setState(555);
						((MapLookupExpressionContext)_localctx).elem = expr(0);
						setState(556);
						match(RPAREN);
						}
						break;
					case 9:
						{
						_localctx = new MapKeysetExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapKeysetExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(558);
						if (!(precpred(_ctx, 47))) throw new FailedPredicateException(this, "precpred(_ctx, 47)");
						setState(559);
						match(KEYS);
						setState(560);
						match(LPAREN);
						setState(561);
						match(RPAREN);
						}
						break;
					case 10:
						{
						_localctx = new ColorChannelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorChannelExpressionContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(562);
						if (!(precpred(_ctx, 46))) throw new FailedPredicateException(this, "precpred(_ctx, 46)");
						setState(563);
						((ColorChannelExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 76)) & ~0x3f) == 0 && ((1L << (_la - 76)) & 15L) != 0)) ) {
							((ColorChannelExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 11:
						{
						_localctx = new ImageSectionExpressionContext(new ExprContext(_parentctx, _parentState));
						((ImageSectionExpressionContext)_localctx).img = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(564);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(565);
						match(SECTION);
						setState(566);
						match(LPAREN);
						setState(567);
						((ImageSectionExpressionContext)_localctx).x = expr(0);
						setState(568);
						match(COMMA);
						setState(569);
						((ImageSectionExpressionContext)_localctx).y = expr(0);
						setState(570);
						match(COMMA);
						setState(571);
						((ImageSectionExpressionContext)_localctx).w = expr(0);
						setState(572);
						match(COMMA);
						setState(573);
						((ImageSectionExpressionContext)_localctx).h = expr(0);
						setState(574);
						match(RPAREN);
						}
						break;
					case 12:
						{
						_localctx = new ColorAtPixelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorAtPixelExpressionContext)_localctx).img = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(576);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(577);
						match(PIXEL);
						setState(578);
						match(LPAREN);
						setState(579);
						((ColorAtPixelExpressionContext)_localctx).x = expr(0);
						setState(580);
						match(COMMA);
						setState(581);
						((ColorAtPixelExpressionContext)_localctx).y = expr(0);
						setState(582);
						match(RPAREN);
						}
						break;
					case 13:
						{
						_localctx = new ImageBoundExpressionContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(584);
						if (!(precpred(_ctx, 28))) throw new FailedPredicateException(this, "precpred(_ctx, 28)");
						setState(585);
						((ImageBoundExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==WIDTH || _la==HEIGHT) ) {
							((ImageBoundExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 14:
						{
						_localctx = new CharAtExpressionContext(new ExprContext(_parentctx, _parentState));
						((CharAtExpressionContext)_localctx).string = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(586);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(587);
						match(AT);
						setState(588);
						match(LPAREN);
						setState(589);
						((CharAtExpressionContext)_localctx).index = expr(0);
						setState(590);
						match(RPAREN);
						}
						break;
					case 15:
						{
						_localctx = new SubstringExpressionContext(new ExprContext(_parentctx, _parentState));
						((SubstringExpressionContext)_localctx).string = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(592);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(593);
						match(SUB);
						setState(594);
						match(LPAREN);
						setState(595);
						((SubstringExpressionContext)_localctx).beg = expr(0);
						setState(596);
						match(COMMA);
						setState(597);
						((SubstringExpressionContext)_localctx).end = expr(0);
						setState(598);
						match(RPAREN);
						}
						break;
					case 16:
						{
						_localctx = new HOFuncCallExpressionContext(new ExprContext(_parentctx, _parentState));
						((HOFuncCallExpressionContext)_localctx).func = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(600);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(601);
						match(CALL);
						setState(602);
						args();
						}
						break;
					case 17:
						{
						_localctx = new ExtScopedCallExpressionContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(603);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(604);
						match(EXTENSION);
						setState(605);
						ident();
						setState(606);
						args();
						}
						break;
					}
					} 
				}
				setState(612);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class K_v_pairsContext extends ParserRuleContext {
		public List<K_v_pairContext> k_v_pair() {
			return getRuleContexts(K_v_pairContext.class);
		}
		public K_v_pairContext k_v_pair(int i) {
			return getRuleContext(K_v_pairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public K_v_pairsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_k_v_pairs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitK_v_pairs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final K_v_pairsContext k_v_pairs() throws RecognitionException {
		K_v_pairsContext _localctx = new K_v_pairsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_k_v_pairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(613);
			k_v_pair();
			setState(618);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(614);
				match(COMMA);
				setState(615);
				k_v_pair();
				}
				}
				setState(620);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class K_v_pairContext extends ParserRuleContext {
		public ExprContext key;
		public ExprContext val;
		public TerminalNode COLON() { return getToken(ScriptParser.COLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public K_v_pairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_k_v_pair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitK_v_pair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final K_v_pairContext k_v_pair() throws RecognitionException {
		K_v_pairContext _localctx = new K_v_pairContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_k_v_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(621);
			((K_v_pairContext)_localctx).key = expr(0);
			setState(622);
			match(COLON);
			setState(623);
			((K_v_pairContext)_localctx).val = expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(ScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScriptParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(ScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScriptParser.COMMA, i);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_args);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(625);
			match(LPAREN);
			setState(634);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -1152894974191206064L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 6176162975743L) != 0)) {
				{
				setState(626);
				expr(0);
				setState(631);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(627);
					match(COMMA);
					setState(628);
					expr(0);
					}
					}
					setState(633);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(636);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	 
		public AssignmentContext() { }
		public void copyFrom(AssignmentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StandardAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ScriptParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StandardAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitStandardAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode ADD_ASSIGN() { return getToken(ScriptParser.ADD_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AddAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitAddAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DivAssignmnetContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode DIV_ASSIGN() { return getToken(ScriptParser.DIV_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DivAssignmnetContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDivAssignmnet(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IncrementAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode INCREMENT() { return getToken(ScriptParser.INCREMENT, 0); }
		public IncrementAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIncrementAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode OR_ASSIGN() { return getToken(ScriptParser.OR_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public OrAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitOrAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode SUB_ASSIGN() { return getToken(ScriptParser.SUB_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SubAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitSubAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode AND_ASSIGN() { return getToken(ScriptParser.AND_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AndAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitAndAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DecrementAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode DECREMENT() { return getToken(ScriptParser.DECREMENT, 0); }
		public DecrementAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDecrementAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode MUL_ASSIGN() { return getToken(ScriptParser.MUL_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MultAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitMultAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ModAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode MOD_ASSIGN() { return getToken(ScriptParser.MOD_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ModAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitModAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_assignment);
		try {
			setState(676);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				_localctx = new StandardAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(638);
				assignable();
				setState(639);
				match(ASSIGN);
				setState(640);
				expr(0);
				}
				break;
			case 2:
				_localctx = new IncrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(642);
				assignable();
				setState(643);
				match(INCREMENT);
				}
				break;
			case 3:
				_localctx = new DecrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(645);
				assignable();
				setState(646);
				match(DECREMENT);
				}
				break;
			case 4:
				_localctx = new AddAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(648);
				assignable();
				setState(649);
				match(ADD_ASSIGN);
				setState(650);
				expr(0);
				}
				break;
			case 5:
				_localctx = new SubAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(652);
				assignable();
				setState(653);
				match(SUB_ASSIGN);
				setState(654);
				expr(0);
				}
				break;
			case 6:
				_localctx = new MultAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(656);
				assignable();
				setState(657);
				match(MUL_ASSIGN);
				setState(658);
				expr(0);
				}
				break;
			case 7:
				_localctx = new DivAssignmnetContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(660);
				assignable();
				setState(661);
				match(DIV_ASSIGN);
				setState(662);
				expr(0);
				}
				break;
			case 8:
				_localctx = new ModAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(664);
				assignable();
				setState(665);
				match(MOD_ASSIGN);
				setState(666);
				expr(0);
				}
				break;
			case 9:
				_localctx = new AndAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(668);
				assignable();
				setState(669);
				match(AND_ASSIGN);
				setState(670);
				expr(0);
				}
				break;
			case 10:
				_localctx = new OrAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(672);
				assignable();
				setState(673);
				match(OR_ASSIGN);
				setState(674);
				expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_initContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(ScriptParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Var_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitVar_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_initContext var_init() throws RecognitionException {
		Var_initContext _localctx = new Var_initContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_var_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(678);
			declaration();
			setState(679);
			match(ASSIGN);
			setState(680);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_defContext extends ParserRuleContext {
		public Var_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_def; }
	 
		public Var_defContext() { }
		public void copyFrom(Var_defContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitVarDefContext extends Var_defContext {
		public Var_initContext var_init() {
			return getRuleContext(Var_initContext.class,0);
		}
		public ExplicitVarDefContext(Var_defContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitExplicitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImplicitVarDefContext extends Var_defContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public ImplicitVarDefContext(Var_defContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitImplicitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_defContext var_def() throws RecognitionException {
		Var_defContext _localctx = new Var_defContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_var_def);
		try {
			setState(684);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				_localctx = new ImplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(682);
				declaration();
				}
				break;
			case 2:
				_localctx = new ExplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(683);
				var_init();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignableContext extends ParserRuleContext {
		public AssignableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignable; }
	 
		public AssignableContext() { }
		public void copyFrom(AssignableContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayAssignableContext extends AssignableContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(ScriptParser.LBRACKET, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(ScriptParser.RBRACKET, 0); }
		public ArrayAssignableContext(AssignableContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitArrayAssignable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SimpleAssignableContext extends AssignableContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public SimpleAssignableContext(AssignableContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitSimpleAssignable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListAssignableContext extends AssignableContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode LT() { return getToken(ScriptParser.LT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode GT() { return getToken(ScriptParser.GT, 0); }
		public ListAssignableContext(AssignableContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitListAssignable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignableContext assignable() throws RecognitionException {
		AssignableContext _localctx = new AssignableContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_assignable);
		try {
			setState(697);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				_localctx = new SimpleAssignableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(686);
				ident();
				}
				break;
			case 2:
				_localctx = new ListAssignableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(687);
				ident();
				setState(688);
				match(LT);
				setState(689);
				expr(0);
				setState(690);
				match(GT);
				}
				break;
			case 3:
				_localctx = new ArrayAssignableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(692);
				ident();
				setState(693);
				match(LBRACKET);
				setState(694);
				expr(0);
				setState(695);
				match(RBRACKET);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(ScriptParser.IDENTIFIER, 0); }
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(699);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorLiteralContext extends LiteralContext {
		public TerminalNode COL_LIT() { return getToken(ScriptParser.COL_LIT, 0); }
		public ColorLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitColorLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends LiteralContext {
		public TerminalNode STRING_LIT() { return getToken(ScriptParser.STRING_LIT, 0); }
		public StringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CharLiteralContext extends LiteralContext {
		public TerminalNode CHAR_LIT() { return getToken(ScriptParser.CHAR_LIT, 0); }
		public CharLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitCharLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolLiteralContext extends LiteralContext {
		public Bool_litContext bool_lit() {
			return getRuleContext(Bool_litContext.class,0);
		}
		public BoolLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitBoolLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatLiteralContext extends LiteralContext {
		public TerminalNode FLOAT_LIT() { return getToken(ScriptParser.FLOAT_LIT, 0); }
		public FloatLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFloatLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntLiteralContext extends LiteralContext {
		public Int_litContext int_lit() {
			return getRuleContext(Int_litContext.class,0);
		}
		public IntLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_literal);
		try {
			setState(707);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LIT:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(701);
				match(STRING_LIT);
				}
				break;
			case CHAR_LIT:
				_localctx = new CharLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(702);
				match(CHAR_LIT);
				}
				break;
			case COL_LIT:
				_localctx = new ColorLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(703);
				match(COL_LIT);
				}
				break;
			case DEC_LIT:
			case HEX_LIT:
				_localctx = new IntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(704);
				int_lit();
				}
				break;
			case FLOAT_LIT:
				_localctx = new FloatLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(705);
				match(FLOAT_LIT);
				}
				break;
			case TRUE:
			case FALSE:
				_localctx = new BoolLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(706);
				bool_lit();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Int_litContext extends ParserRuleContext {
		public Int_litContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_int_lit; }
	 
		public Int_litContext() { }
		public void copyFrom(Int_litContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class HexadecimalContext extends Int_litContext {
		public TerminalNode HEX_LIT() { return getToken(ScriptParser.HEX_LIT, 0); }
		public HexadecimalContext(Int_litContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitHexadecimal(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DecimalContext extends Int_litContext {
		public TerminalNode DEC_LIT() { return getToken(ScriptParser.DEC_LIT, 0); }
		public DecimalContext(Int_litContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitDecimal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Int_litContext int_lit() throws RecognitionException {
		Int_litContext _localctx = new Int_litContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_int_lit);
		try {
			setState(711);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HEX_LIT:
				_localctx = new HexadecimalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(709);
				match(HEX_LIT);
				}
				break;
			case DEC_LIT:
				_localctx = new DecimalContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(710);
				match(DEC_LIT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Bool_litContext extends ParserRuleContext {
		public Bool_litContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_lit; }
	 
		public Bool_litContext() { }
		public void copyFrom(Bool_litContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueContext extends Bool_litContext {
		public TerminalNode TRUE() { return getToken(ScriptParser.TRUE, 0); }
		public TrueContext(Bool_litContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseContext extends Bool_litContext {
		public TerminalNode FALSE() { return getToken(ScriptParser.FALSE, 0); }
		public FalseContext(Bool_litContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bool_litContext bool_lit() throws RecognitionException {
		Bool_litContext _localctx = new Bool_litContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_bool_lit);
		try {
			setState(715);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(713);
				match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(714);
				match(FALSE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 18:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 19);
		case 4:
			return precpred(_ctx, 18);
		case 5:
			return precpred(_ctx, 17);
		case 6:
			return precpred(_ctx, 16);
		case 7:
			return precpred(_ctx, 15);
		case 8:
			return precpred(_ctx, 14);
		case 9:
			return precpred(_ctx, 49);
		case 10:
			return precpred(_ctx, 48);
		case 11:
			return precpred(_ctx, 47);
		case 12:
			return precpred(_ctx, 46);
		case 13:
			return precpred(_ctx, 30);
		case 14:
			return precpred(_ctx, 29);
		case 15:
			return precpred(_ctx, 28);
		case 16:
			return precpred(_ctx, 25);
		case 17:
			return precpred(_ctx, 24);
		case 18:
			return precpred(_ctx, 23);
		case 19:
			return precpred(_ctx, 22);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001k\u02ce\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0005\u0000@\b\u0000\n\u0000\f\u0000C\t\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002L\b\u0002\u0001\u0003\u0001\u0003\u0003\u0003P\b\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u0003U\b\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0003\u0003[\b\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0005\u0004`\b\u0004\n\u0004\f\u0004c\t\u0004\u0001"+
		"\u0005\u0003\u0005f\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0003\u0006~\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005"+
		"\u0006\u0089\b\u0006\n\u0006\f\u0006\u008c\t\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0005\b\u0095\b\b\n\b"+
		"\f\b\u0098\t\b\u0003\b\u009a\b\b\u0001\t\u0001\t\u0001\t\u0005\t\u009f"+
		"\b\t\n\t\f\t\u00a2\t\t\u0001\t\u0003\t\u00a5\b\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\n\u00b6\b\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003"+
		"\n\u010c\b\n\u0001\u000b\u0001\u000b\u0003\u000b\u0110\b\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u0122\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u013c\b\u0010\n\u0010"+
		"\f\u0010\u013f\t\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u0143\b\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012"+
		"\u01ce\b\u0012\n\u0012\f\u0012\u01d1\t\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u01d9\b\u0012\n"+
		"\u0012\f\u0012\u01dc\t\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u01e4\b\u0012\n\u0012\f\u0012"+
		"\u01e7\t\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0003\u0012\u020c\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0005\u0012\u0261\b\u0012\n\u0012\f\u0012\u0264\t\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u0269\b\u0013\n\u0013\f\u0013"+
		"\u026c\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u0276\b\u0015\n\u0015"+
		"\f\u0015\u0279\t\u0015\u0003\u0015\u027b\b\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0003\u0016\u02a5\b\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0003\u0018\u02ad\b\u0018"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019"+
		"\u02ba\b\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u02c4\b\u001b\u0001\u001c"+
		"\u0001\u001c\u0003\u001c\u02c8\b\u001c\u0001\u001d\u0001\u001d\u0003\u001d"+
		"\u02cc\b\u001d\u0001\u001d\u0000\u0002\f$\u001e\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,."+
		"02468:\u0000\u0007\u0002\u0000%%+,\u0001\u0000$%\u0001\u0000&(\u0001\u0000"+
		"\u001d\"\u0001\u0000)*\u0001\u0000LO\u0001\u0000PQ\u0324\u0000<\u0001"+
		"\u0000\u0000\u0000\u0002D\u0001\u0000\u0000\u0000\u0004K\u0001\u0000\u0000"+
		"\u0000\u0006Z\u0001\u0000\u0000\u0000\b\\\u0001\u0000\u0000\u0000\ne\u0001"+
		"\u0000\u0000\u0000\f}\u0001\u0000\u0000\u0000\u000e\u008d\u0001\u0000"+
		"\u0000\u0000\u0010\u0099\u0001\u0000\u0000\u0000\u0012\u00a4\u0001\u0000"+
		"\u0000\u0000\u0014\u010b\u0001\u0000\u0000\u0000\u0016\u010d\u0001\u0000"+
		"\u0000\u0000\u0018\u0121\u0001\u0000\u0000\u0000\u001a\u0123\u0001\u0000"+
		"\u0000\u0000\u001c\u012a\u0001\u0000\u0000\u0000\u001e\u012f\u0001\u0000"+
		"\u0000\u0000 \u0138\u0001\u0000\u0000\u0000\"\u0144\u0001\u0000\u0000"+
		"\u0000$\u020b\u0001\u0000\u0000\u0000&\u0265\u0001\u0000\u0000\u0000("+
		"\u026d\u0001\u0000\u0000\u0000*\u0271\u0001\u0000\u0000\u0000,\u02a4\u0001"+
		"\u0000\u0000\u0000.\u02a6\u0001\u0000\u0000\u00000\u02ac\u0001\u0000\u0000"+
		"\u00002\u02b9\u0001\u0000\u0000\u00004\u02bb\u0001\u0000\u0000\u00006"+
		"\u02c3\u0001\u0000\u0000\u00008\u02c7\u0001\u0000\u0000\u0000:\u02cb\u0001"+
		"\u0000\u0000\u0000<=\u0003\u0006\u0003\u0000=A\u0003\u0004\u0002\u0000"+
		">@\u0003\u0002\u0001\u0000?>\u0001\u0000\u0000\u0000@C\u0001\u0000\u0000"+
		"\u0000A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000B\u0001\u0001"+
		"\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000DE\u00034\u001a\u0000EF\u0003"+
		"\u0006\u0003\u0000FG\u0003\u0004\u0002\u0000G\u0003\u0001\u0000\u0000"+
		"\u0000HL\u0003\u0012\t\u0000IJ\u0005\u001a\u0000\u0000JL\u0003$\u0012"+
		"\u0000KH\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000L\u0005\u0001"+
		"\u0000\u0000\u0000MO\u0005\u0004\u0000\u0000NP\u0003\b\u0004\u0000ON\u0001"+
		"\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000"+
		"Q[\u0005\u0005\u0000\u0000RT\u0005\u0004\u0000\u0000SU\u0003\b\u0004\u0000"+
		"TS\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000"+
		"\u0000VW\u0005\u001a\u0000\u0000WX\u0003\f\u0006\u0000XY\u0005\u0005\u0000"+
		"\u0000Y[\u0001\u0000\u0000\u0000ZM\u0001\u0000\u0000\u0000ZR\u0001\u0000"+
		"\u0000\u0000[\u0007\u0001\u0000\u0000\u0000\\a\u0003\n\u0005\u0000]^\u0005"+
		"\f\u0000\u0000^`\u0003\n\u0005\u0000_]\u0001\u0000\u0000\u0000`c\u0001"+
		"\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000"+
		"b\t\u0001\u0000\u0000\u0000ca\u0001\u0000\u0000\u0000df\u0005.\u0000\u0000"+
		"ed\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000"+
		"\u0000gh\u0003\f\u0006\u0000hi\u00034\u001a\u0000i\u000b\u0001\u0000\u0000"+
		"\u0000jk\u0006\u0006\uffff\uffff\u0000k~\u0005/\u0000\u0000l~\u00051\u0000"+
		"\u0000m~\u00050\u0000\u0000n~\u00052\u0000\u0000o~\u00055\u0000\u0000"+
		"p~\u00054\u0000\u0000q~\u00053\u0000\u0000rs\u0005\b\u0000\u0000st\u0003"+
		"\f\u0006\u0000tu\u0005\u000b\u0000\u0000uv\u0003\f\u0006\u0000vw\u0005"+
		"\t\u0000\u0000w~\u0001\u0000\u0000\u0000xy\u0005\u0004\u0000\u0000yz\u0003"+
		"\u000e\u0007\u0000z{\u0005\u0005\u0000\u0000{~\u0001\u0000\u0000\u0000"+
		"|~\u00034\u001a\u0000}j\u0001\u0000\u0000\u0000}l\u0001\u0000\u0000\u0000"+
		"}m\u0001\u0000\u0000\u0000}n\u0001\u0000\u0000\u0000}o\u0001\u0000\u0000"+
		"\u0000}p\u0001\u0000\u0000\u0000}q\u0001\u0000\u0000\u0000}r\u0001\u0000"+
		"\u0000\u0000}x\u0001\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000~\u008a"+
		"\u0001\u0000\u0000\u0000\u007f\u0080\n\u0006\u0000\u0000\u0080\u0081\u0005"+
		"\u0006\u0000\u0000\u0081\u0089\u0005\u0007\u0000\u0000\u0082\u0083\n\u0005"+
		"\u0000\u0000\u0083\u0084\u0005 \u0000\u0000\u0084\u0089\u0005\u001f\u0000"+
		"\u0000\u0085\u0086\n\u0004\u0000\u0000\u0086\u0087\u0005\b\u0000\u0000"+
		"\u0087\u0089\u0005\t\u0000\u0000\u0088\u007f\u0001\u0000\u0000\u0000\u0088"+
		"\u0082\u0001\u0000\u0000\u0000\u0088\u0085\u0001\u0000\u0000\u0000\u0089"+
		"\u008c\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008a"+
		"\u008b\u0001\u0000\u0000\u0000\u008b\r\u0001\u0000\u0000\u0000\u008c\u008a"+
		"\u0001\u0000\u0000\u0000\u008d\u008e\u0003\u0010\b\u0000\u008e\u008f\u0005"+
		"\u001a\u0000\u0000\u008f\u0090\u0003\f\u0006\u0000\u0090\u000f\u0001\u0000"+
		"\u0000\u0000\u0091\u0096\u0003\f\u0006\u0000\u0092\u0093\u0005\f\u0000"+
		"\u0000\u0093\u0095\u0003\f\u0006\u0000\u0094\u0092\u0001\u0000\u0000\u0000"+
		"\u0095\u0098\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000\u0000\u0000"+
		"\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u009a\u0001\u0000\u0000\u0000"+
		"\u0098\u0096\u0001\u0000\u0000\u0000\u0099\u0091\u0001\u0000\u0000\u0000"+
		"\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u0011\u0001\u0000\u0000\u0000"+
		"\u009b\u00a5\u0003\u0014\n\u0000\u009c\u00a0\u0005\b\u0000\u0000\u009d"+
		"\u009f\u0003\u0014\n\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009f\u00a2"+
		"\u0001\u0000\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1"+
		"\u0001\u0000\u0000\u0000\u00a1\u00a3\u0001\u0000\u0000\u0000\u00a2\u00a0"+
		"\u0001\u0000\u0000\u0000\u00a3\u00a5\u0005\t\u0000\u0000\u00a4\u009b\u0001"+
		"\u0000\u0000\u0000\u00a4\u009c\u0001\u0000\u0000\u0000\u00a5\u0013\u0001"+
		"\u0000\u0000\u0000\u00a6\u010c\u0003\u0018\f\u0000\u00a7\u010c\u0003 "+
		"\u0010\u0000\u00a8\u00a9\u00030\u0018\u0000\u00a9\u00aa\u0005\n\u0000"+
		"\u0000\u00aa\u010c\u0001\u0000\u0000\u0000\u00ab\u00ac\u0003,\u0016\u0000"+
		"\u00ac\u00ad\u0005\n\u0000\u0000\u00ad\u010c\u0001\u0000\u0000\u0000\u00ae"+
		"\u010c\u0003\u0016\u000b\u0000\u00af\u00b0\u0003$\u0012\u0000\u00b0\u00b1"+
		"\u0005V\u0000\u0000\u00b1\u00b2\u0005\u0004\u0000\u0000\u00b2\u00b5\u0003"+
		"$\u0012\u0000\u00b3\u00b4\u0005\f\u0000\u0000\u00b4\u00b6\u0003$\u0012"+
		"\u0000\u00b5\u00b3\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005\u0005\u0000"+
		"\u0000\u00b8\u00b9\u0005\n\u0000\u0000\u00b9\u010c\u0001\u0000\u0000\u0000"+
		"\u00ba\u00bb\u0003$\u0012\u0000\u00bb\u00bc\u0005W\u0000\u0000\u00bc\u00bd"+
		"\u0005\u0004\u0000\u0000\u00bd\u00be\u0003$\u0012\u0000\u00be\u00bf\u0005"+
		"\u0005\u0000\u0000\u00bf\u00c0\u0005\n\u0000\u0000\u00c0\u010c\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u0003$\u0012\u0000\u00c2\u00c3\u0005X\u0000\u0000"+
		"\u00c3\u00c4\u0005\u0004\u0000\u0000\u00c4\u00c5\u0003$\u0012\u0000\u00c5"+
		"\u00c6\u0005\f\u0000\u0000\u00c6\u00c7\u0003$\u0012\u0000\u00c7\u00c8"+
		"\u0005\u0005\u0000\u0000\u00c8\u00c9\u0005\n\u0000\u0000\u00c9\u010c\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cb\u0003$\u0012\u0000\u00cb\u00cc\u0005Y\u0000"+
		"\u0000\u00cc\u00cd\u0005\u0004\u0000\u0000\u00cd\u00ce\u0003$\u0012\u0000"+
		"\u00ce\u00cf\u0005\f\u0000\u0000\u00cf\u00d0\u0003$\u0012\u0000\u00d0"+
		"\u00d1\u0005\f\u0000\u0000\u00d1\u00d2\u0003$\u0012\u0000\u00d2\u00d3"+
		"\u0005\u0005\u0000\u0000\u00d3\u00d4\u0005\n\u0000\u0000\u00d4\u010c\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d6\u0003$\u0012\u0000\u00d6\u00d7\u0005\\"+
		"\u0000\u0000\u00d7\u00d8\u0005\u0004\u0000\u0000\u00d8\u00d9\u0003$\u0012"+
		"\u0000\u00d9\u00da\u0005\f\u0000\u0000\u00da\u00db\u0003$\u0012\u0000"+
		"\u00db\u00dc\u0005\f\u0000\u0000\u00dc\u00dd\u0003$\u0012\u0000\u00dd"+
		"\u00de\u0005\u0005\u0000\u0000\u00de\u00df\u0005\n\u0000\u0000\u00df\u010c"+
		"\u0001\u0000\u0000\u0000\u00e0\u00e1\u0003$\u0012\u0000\u00e1\u00e2\u0005"+
		"]\u0000\u0000\u00e2\u00e3\u0005\u0004\u0000\u0000\u00e3\u00e4\u0003$\u0012"+
		"\u0000\u00e4\u00e5\u0005\f\u0000\u0000\u00e5\u00e6\u0003$\u0012\u0000"+
		"\u00e6\u00e7\u0005\f\u0000\u0000\u00e7\u00e8\u0003$\u0012\u0000\u00e8"+
		"\u00e9\u0005\f\u0000\u0000\u00e9\u00ea\u0003$\u0012\u0000\u00ea\u00eb"+
		"\u0005\f\u0000\u0000\u00eb\u00ec\u0003$\u0012\u0000\u00ec\u00ed\u0005"+
		"\f\u0000\u0000\u00ed\u00ee\u0003$\u0012\u0000\u00ee\u00ef\u0005\u0005"+
		"\u0000\u0000\u00ef\u00f0\u0005\n\u0000\u0000\u00f0\u010c\u0001\u0000\u0000"+
		"\u0000\u00f1\u00f2\u0003$\u0012\u0000\u00f2\u00f3\u0005^\u0000\u0000\u00f3"+
		"\u00f4\u0005\u0004\u0000\u0000\u00f4\u00f5\u0003$\u0012\u0000\u00f5\u00f6"+
		"\u0005\f\u0000\u0000\u00f6\u00f7\u0003$\u0012\u0000\u00f7\u00f8\u0005"+
		"\f\u0000\u0000\u00f8\u00f9\u0003$\u0012\u0000\u00f9\u00fa\u0005\f\u0000"+
		"\u0000\u00fa\u00fb\u0003$\u0012\u0000\u00fb\u00fc\u0005\f\u0000\u0000"+
		"\u00fc\u00fd\u0003$\u0012\u0000\u00fd\u00fe\u0005\u0005\u0000\u0000\u00fe"+
		"\u00ff\u0005\n\u0000\u0000\u00ff\u010c\u0001\u0000\u0000\u0000\u0100\u0101"+
		"\u0003$\u0012\u0000\u0101\u0102\u0005\u001c\u0000\u0000\u0102\u0103\u0003"+
		"4\u001a\u0000\u0103\u0104\u0003*\u0015\u0000\u0104\u0105\u0005\n\u0000"+
		"\u0000\u0105\u010c\u0001\u0000\u0000\u0000\u0106\u0107\u0005\u001c\u0000"+
		"\u0000\u0107\u0108\u0005k\u0000\u0000\u0108\u0109\u0003*\u0015\u0000\u0109"+
		"\u010a\u0005\n\u0000\u0000\u010a\u010c\u0001\u0000\u0000\u0000\u010b\u00a6"+
		"\u0001\u0000\u0000\u0000\u010b\u00a7\u0001\u0000\u0000\u0000\u010b\u00a8"+
		"\u0001\u0000\u0000\u0000\u010b\u00ab\u0001\u0000\u0000\u0000\u010b\u00ae"+
		"\u0001\u0000\u0000\u0000\u010b\u00af\u0001\u0000\u0000\u0000\u010b\u00ba"+
		"\u0001\u0000\u0000\u0000\u010b\u00c1\u0001\u0000\u0000\u0000\u010b\u00ca"+
		"\u0001\u0000\u0000\u0000\u010b\u00d5\u0001\u0000\u0000\u0000\u010b\u00e0"+
		"\u0001\u0000\u0000\u0000\u010b\u00f1\u0001\u0000\u0000\u0000\u010b\u0100"+
		"\u0001\u0000\u0000\u0000\u010b\u0106\u0001\u0000\u0000\u0000\u010c\u0015"+
		"\u0001\u0000\u0000\u0000\u010d\u010f\u00056\u0000\u0000\u010e\u0110\u0003"+
		"$\u0012\u0000\u010f\u010e\u0001\u0000\u0000\u0000\u010f\u0110\u0001\u0000"+
		"\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u0112\u0005\n\u0000"+
		"\u0000\u0112\u0017\u0001\u0000\u0000\u0000\u0113\u0114\u0003\u001c\u000e"+
		"\u0000\u0114\u0115\u0003\u0012\t\u0000\u0115\u0122\u0001\u0000\u0000\u0000"+
		"\u0116\u0117\u0003\u001a\r\u0000\u0117\u0118\u0003\u0012\t\u0000\u0118"+
		"\u0122\u0001\u0000\u0000\u0000\u0119\u011a\u0003\u001e\u000f\u0000\u011a"+
		"\u011b\u0003\u0012\t\u0000\u011b\u0122\u0001\u0000\u0000\u0000\u011c\u011d"+
		"\u00057\u0000\u0000\u011d\u011e\u0003\u0012\t\u0000\u011e\u011f\u0003"+
		"\u001c\u000e\u0000\u011f\u0120\u0005\n\u0000\u0000\u0120\u0122\u0001\u0000"+
		"\u0000\u0000\u0121\u0113\u0001\u0000\u0000\u0000\u0121\u0116\u0001\u0000"+
		"\u0000\u0000\u0121\u0119\u0001\u0000\u0000\u0000\u0121\u011c\u0001\u0000"+
		"\u0000\u0000\u0122\u0019\u0001\u0000\u0000\u0000\u0123\u0124\u00059\u0000"+
		"\u0000\u0124\u0125\u0005\u0004\u0000\u0000\u0125\u0126\u0003\n\u0005\u0000"+
		"\u0126\u0127\u0005-\u0000\u0000\u0127\u0128\u0003$\u0012\u0000\u0128\u0129"+
		"\u0005\u0005\u0000\u0000\u0129\u001b\u0001\u0000\u0000\u0000\u012a\u012b"+
		"\u00058\u0000\u0000\u012b\u012c\u0005\u0004\u0000\u0000\u012c\u012d\u0003"+
		"$\u0012\u0000\u012d\u012e\u0005\u0005\u0000\u0000\u012e\u001d\u0001\u0000"+
		"\u0000\u0000\u012f\u0130\u00059\u0000\u0000\u0130\u0131\u0005\u0004\u0000"+
		"\u0000\u0131\u0132\u0003.\u0017\u0000\u0132\u0133\u0005\n\u0000\u0000"+
		"\u0133\u0134\u0003$\u0012\u0000\u0134\u0135\u0005\n\u0000\u0000\u0135"+
		"\u0136\u0003,\u0016\u0000\u0136\u0137\u0005\u0005\u0000\u0000\u0137\u001f"+
		"\u0001\u0000\u0000\u0000\u0138\u013d\u0003\"\u0011\u0000\u0139\u013a\u0005"+
		";\u0000\u0000\u013a\u013c\u0003\"\u0011\u0000\u013b\u0139\u0001\u0000"+
		"\u0000\u0000\u013c\u013f\u0001\u0000\u0000\u0000\u013d\u013b\u0001\u0000"+
		"\u0000\u0000\u013d\u013e\u0001\u0000\u0000\u0000\u013e\u0142\u0001\u0000"+
		"\u0000\u0000\u013f\u013d\u0001\u0000\u0000\u0000\u0140\u0141\u0005;\u0000"+
		"\u0000\u0141\u0143\u0003\u0012\t\u0000\u0142\u0140\u0001\u0000\u0000\u0000"+
		"\u0142\u0143\u0001\u0000\u0000\u0000\u0143!\u0001\u0000\u0000\u0000\u0144"+
		"\u0145\u0005:\u0000\u0000\u0145\u0146\u0005\u0004\u0000\u0000\u0146\u0147"+
		"\u0003$\u0012\u0000\u0147\u0148\u0005\u0005\u0000\u0000\u0148\u0149\u0003"+
		"\u0012\t\u0000\u0149#\u0001\u0000\u0000\u0000\u014a\u014b\u0006\u0012"+
		"\uffff\uffff\u0000\u014b\u014c\u0005\u0004\u0000\u0000\u014c\u014d\u0003"+
		"$\u0012\u0000\u014d\u014e\u0005\u0005\u0000\u0000\u014e\u020c\u0001\u0000"+
		"\u0000\u0000\u014f\u0150\u0005E\u0000\u0000\u0150\u0151\u0005\u0004\u0000"+
		"\u0000\u0151\u0152\u0003$\u0012\u0000\u0152\u0153\u0005\u0005\u0000\u0000"+
		"\u0153\u020c\u0001\u0000\u0000\u0000\u0154\u0155\u0005F\u0000\u0000\u0155"+
		"\u0156\u0005\u0004\u0000\u0000\u0156\u0157\u0003$\u0012\u0000\u0157\u0158"+
		"\u0005\u0005\u0000\u0000\u0158\u020c\u0001\u0000\u0000\u0000\u0159\u015a"+
		"\u0005F\u0000\u0000\u015a\u015b\u0005\u0004\u0000\u0000\u015b\u015c\u0003"+
		"$\u0012\u0000\u015c\u015d\u0005\f\u0000\u0000\u015d\u015e\u0003$\u0012"+
		"\u0000\u015e\u015f\u0005\u0005\u0000\u0000\u015f\u020c\u0001\u0000\u0000"+
		"\u0000\u0160\u0161\u0005G\u0000\u0000\u0161\u0162\u0005\u0004\u0000\u0000"+
		"\u0162\u0163\u0003$\u0012\u0000\u0163\u0164\u0005\u0005\u0000\u0000\u0164"+
		"\u020c\u0001\u0000\u0000\u0000\u0165\u0166\u0005G\u0000\u0000\u0166\u0167"+
		"\u0005\u0004\u0000\u0000\u0167\u0168\u0003$\u0012\u0000\u0168\u0169\u0005"+
		"\f\u0000\u0000\u0169\u016a\u0003$\u0012\u0000\u016a\u016b\u0005\u0005"+
		"\u0000\u0000\u016b\u020c\u0001\u0000\u0000\u0000\u016c\u016d\u0005H\u0000"+
		"\u0000\u016d\u016e\u0005\u0004\u0000\u0000\u016e\u016f\u0003$\u0012\u0000"+
		"\u016f\u0170\u0005\f\u0000\u0000\u0170\u0171\u0003$\u0012\u0000\u0171"+
		"\u0172\u0005\f\u0000\u0000\u0172\u0173\u0003$\u0012\u0000\u0173\u0174"+
		"\u0005\u0005\u0000\u0000\u0174\u020c\u0001\u0000\u0000\u0000\u0175\u0176"+
		"\u0005I\u0000\u0000\u0176\u0177\u0005\u0004\u0000\u0000\u0177\u020c\u0005"+
		"\u0005\u0000\u0000\u0178\u0179\u0005I\u0000\u0000\u0179\u017a\u0005\u0004"+
		"\u0000\u0000\u017a\u017b\u0003$\u0012\u0000\u017b\u017c\u0005\f\u0000"+
		"\u0000\u017c\u017d\u0003$\u0012\u0000\u017d\u017e\u0005\u0005\u0000\u0000"+
		"\u017e\u020c\u0001\u0000\u0000\u0000\u017f\u0180\u0005J\u0000\u0000\u0180"+
		"\u0181\u0005\u0004\u0000\u0000\u0181\u0182\u0003$\u0012\u0000\u0182\u0183"+
		"\u0005\u0005\u0000\u0000\u0183\u020c\u0001\u0000\u0000\u0000\u0184\u0185"+
		"\u0005K\u0000\u0000\u0185\u0186\u0005\u0004\u0000\u0000\u0186\u020c\u0005"+
		"\u0005\u0000\u0000\u0187\u0188\u0005K\u0000\u0000\u0188\u0189\u0005\u0004"+
		"\u0000\u0000\u0189\u018a\u0003$\u0012\u0000\u018a\u018b\u0005\f\u0000"+
		"\u0000\u018b\u018c\u0003$\u0012\u0000\u018c\u018d\u0005\u0005\u0000\u0000"+
		"\u018d\u020c\u0001\u0000\u0000\u0000\u018e\u018f\u0005?\u0000\u0000\u018f"+
		"\u0190\u0005\u0004\u0000\u0000\u0190\u0191\u0003$\u0012\u0000\u0191\u0192"+
		"\u0005\u0005\u0000\u0000\u0192\u020c\u0001\u0000\u0000\u0000\u0193\u0194"+
		"\u0005B\u0000\u0000\u0194\u0195\u0005\u0004\u0000\u0000\u0195\u0196\u0003"+
		"$\u0012\u0000\u0196\u0197\u0005\f\u0000\u0000\u0197\u0198\u0003$\u0012"+
		"\u0000\u0198\u0199\u0005\u0005\u0000\u0000\u0199\u020c\u0001\u0000\u0000"+
		"\u0000\u019a\u019b\u0005C\u0000\u0000\u019b\u019c\u0005\u0004\u0000\u0000"+
		"\u019c\u019d\u0003$\u0012\u0000\u019d\u019e\u0005\f\u0000\u0000\u019e"+
		"\u019f\u0003$\u0012\u0000\u019f\u01a0\u0005\f\u0000\u0000\u01a0\u01a1"+
		"\u0003$\u0012\u0000\u01a1\u01a2\u0005\u0005\u0000\u0000\u01a2\u020c\u0001"+
		"\u0000\u0000\u0000\u01a3\u01a4\u0005D\u0000\u0000\u01a4\u01a5\u0005\u0004"+
		"\u0000\u0000\u01a5\u01a6\u0003$\u0012\u0000\u01a6\u01a7\u0005\f\u0000"+
		"\u0000\u01a7\u01a8\u0003$\u0012\u0000\u01a8\u01a9\u0005\u0005\u0000\u0000"+
		"\u01a9\u020c\u0001\u0000\u0000\u0000\u01aa\u01ab\u0005A\u0000\u0000\u01ab"+
		"\u01ac\u0005\u0004\u0000\u0000\u01ac\u01ad\u0003$\u0012\u0000\u01ad\u01ae"+
		"\u0005\f\u0000\u0000\u01ae\u01af\u0003$\u0012\u0000\u01af\u01b0\u0005"+
		"\f\u0000\u0000\u01b0\u01b1\u0003$\u0012\u0000\u01b1\u01b2\u0005\u0005"+
		"\u0000\u0000\u01b2\u020c\u0001\u0000\u0000\u0000\u01b3\u01b4\u0005@\u0000"+
		"\u0000\u01b4\u01b5\u0005\u0004\u0000\u0000\u01b5\u01b6\u0003$\u0012\u0000"+
		"\u01b6\u01b7\u0005\f\u0000\u0000\u01b7\u01b8\u0003$\u0012\u0000\u01b8"+
		"\u01b9\u0005\f\u0000\u0000\u01b9\u01ba\u0003$\u0012\u0000\u01ba\u01bb"+
		"\u0005\f\u0000\u0000\u01bb\u01bc\u0003$\u0012\u0000\u01bc\u01bd\u0005"+
		"\u0005\u0000\u0000\u01bd\u020c\u0001\u0000\u0000\u0000\u01be\u01bf\u0007"+
		"\u0000\u0000\u0000\u01bf\u020c\u0003$\u0012\u0015\u01c0\u01c1\u0005\u0004"+
		"\u0000\u0000\u01c1\u01c2\u0003\f\u0006\u0000\u01c2\u01c3\u0005\u0005\u0000"+
		"\u0000\u01c3\u01c4\u0003$\u0012\u0014\u01c4\u020c\u0001\u0000\u0000\u0000"+
		"\u01c5\u01c6\u0005\b\u0000\u0000\u01c6\u01c7\u0003&\u0013\u0000\u01c7"+
		"\u01c8\u0005\t\u0000\u0000\u01c8\u020c\u0001\u0000\u0000\u0000\u01c9\u01ca"+
		"\u0005\u0006\u0000\u0000\u01ca\u01cf\u0003$\u0012\u0000\u01cb\u01cc\u0005"+
		"\f\u0000\u0000\u01cc\u01ce\u0003$\u0012\u0000\u01cd\u01cb\u0001\u0000"+
		"\u0000\u0000\u01ce\u01d1\u0001\u0000\u0000\u0000\u01cf\u01cd\u0001\u0000"+
		"\u0000\u0000\u01cf\u01d0\u0001\u0000\u0000\u0000\u01d0\u01d2\u0001\u0000"+
		"\u0000\u0000\u01d1\u01cf\u0001\u0000\u0000\u0000\u01d2\u01d3\u0005\u0007"+
		"\u0000\u0000\u01d3\u020c\u0001\u0000\u0000\u0000\u01d4\u01d5\u0005 \u0000"+
		"\u0000\u01d5\u01da\u0003$\u0012\u0000\u01d6\u01d7\u0005\f\u0000\u0000"+
		"\u01d7\u01d9\u0003$\u0012\u0000\u01d8\u01d6\u0001\u0000\u0000\u0000\u01d9"+
		"\u01dc\u0001\u0000\u0000\u0000\u01da\u01d8\u0001\u0000\u0000\u0000\u01da"+
		"\u01db\u0001\u0000\u0000\u0000\u01db\u01dd\u0001\u0000\u0000\u0000\u01dc"+
		"\u01da\u0001\u0000\u0000\u0000\u01dd\u01de\u0005\u001f\u0000\u0000\u01de"+
		"\u020c\u0001\u0000\u0000\u0000\u01df\u01e0\u0005\b\u0000\u0000\u01e0\u01e5"+
		"\u0003$\u0012\u0000\u01e1\u01e2\u0005\f\u0000\u0000\u01e2\u01e4\u0003"+
		"$\u0012\u0000\u01e3\u01e1\u0001\u0000\u0000\u0000\u01e4\u01e7\u0001\u0000"+
		"\u0000\u0000\u01e5\u01e3\u0001\u0000\u0000\u0000\u01e5\u01e6\u0001\u0000"+
		"\u0000\u0000\u01e6\u01e8\u0001\u0000\u0000\u0000\u01e7\u01e5\u0001\u0000"+
		"\u0000\u0000\u01e8\u01e9\u0005\t\u0000\u0000\u01e9\u020c\u0001\u0000\u0000"+
		"\u0000\u01ea\u01eb\u0005>\u0000\u0000\u01eb\u01ec\u0003\f\u0006\u0000"+
		"\u01ec\u01ed\u0005\u0006\u0000\u0000\u01ed\u01ee\u0003$\u0012\u0000\u01ee"+
		"\u01ef\u0005\u0007\u0000\u0000\u01ef\u020c\u0001\u0000\u0000\u0000\u01f0"+
		"\u01f1\u0005>\u0000\u0000\u01f1\u01f2\u0003\f\u0006\u0000\u01f2\u01f3"+
		"\u0005 \u0000\u0000\u01f3\u01f4\u0005\u001f\u0000\u0000\u01f4\u020c\u0001"+
		"\u0000\u0000\u0000\u01f5\u01f6\u0005>\u0000\u0000\u01f6\u01f7\u0003\f"+
		"\u0006\u0000\u01f7\u01f8\u0005\b\u0000\u0000\u01f8\u01f9\u0005\t\u0000"+
		"\u0000\u01f9\u020c\u0001\u0000\u0000\u0000\u01fa\u01fb\u0005>\u0000\u0000"+
		"\u01fb\u01fc\u0005\b\u0000\u0000\u01fc\u01fd\u0003\f\u0006\u0000\u01fd"+
		"\u01fe\u0005\u000b\u0000\u0000\u01fe\u01ff\u0003\f\u0006\u0000\u01ff\u0200"+
		"\u0005\t\u0000\u0000\u0200\u020c\u0001\u0000\u0000\u0000\u0201\u0202\u0003"+
		"4\u001a\u0000\u0202\u0203\u0003*\u0015\u0000\u0203\u020c\u0001\u0000\u0000"+
		"\u0000\u0204\u0205\u0005\u001b\u0000\u0000\u0205\u020c\u00034\u001a\u0000"+
		"\u0206\u0207\u0005\u001c\u0000\u0000\u0207\u0208\u0005k\u0000\u0000\u0208"+
		"\u020c\u0003*\u0015\u0000\u0209\u020c\u00032\u0019\u0000\u020a\u020c\u0003"+
		"6\u001b\u0000\u020b\u014a\u0001\u0000\u0000\u0000\u020b\u014f\u0001\u0000"+
		"\u0000\u0000\u020b\u0154\u0001\u0000\u0000\u0000\u020b\u0159\u0001\u0000"+
		"\u0000\u0000\u020b\u0160\u0001\u0000\u0000\u0000\u020b\u0165\u0001\u0000"+
		"\u0000\u0000\u020b\u016c\u0001\u0000\u0000\u0000\u020b\u0175\u0001\u0000"+
		"\u0000\u0000\u020b\u0178\u0001\u0000\u0000\u0000\u020b\u017f\u0001\u0000"+
		"\u0000\u0000\u020b\u0184\u0001\u0000\u0000\u0000\u020b\u0187\u0001\u0000"+
		"\u0000\u0000\u020b\u018e\u0001\u0000\u0000\u0000\u020b\u0193\u0001\u0000"+
		"\u0000\u0000\u020b\u019a\u0001\u0000\u0000\u0000\u020b\u01a3\u0001\u0000"+
		"\u0000\u0000\u020b\u01aa\u0001\u0000\u0000\u0000\u020b\u01b3\u0001\u0000"+
		"\u0000\u0000\u020b\u01be\u0001\u0000\u0000\u0000\u020b\u01c0\u0001\u0000"+
		"\u0000\u0000\u020b\u01c5\u0001\u0000\u0000\u0000\u020b\u01c9\u0001\u0000"+
		"\u0000\u0000\u020b\u01d4\u0001\u0000\u0000\u0000\u020b\u01df\u0001\u0000"+
		"\u0000\u0000\u020b\u01ea\u0001\u0000\u0000\u0000\u020b\u01f0\u0001\u0000"+
		"\u0000\u0000\u020b\u01f5\u0001\u0000\u0000\u0000\u020b\u01fa\u0001\u0000"+
		"\u0000\u0000\u020b\u0201\u0001\u0000\u0000\u0000\u020b\u0204\u0001\u0000"+
		"\u0000\u0000\u020b\u0206\u0001\u0000\u0000\u0000\u020b\u0209\u0001\u0000"+
		"\u0000\u0000\u020b\u020a\u0001\u0000\u0000\u0000\u020c\u0262\u0001\u0000"+
		"\u0000\u0000\u020d\u020e\n\u0013\u0000\u0000\u020e\u020f\u0007\u0001\u0000"+
		"\u0000\u020f\u0261\u0003$\u0012\u0014\u0210\u0211\n\u0012\u0000\u0000"+
		"\u0211\u0212\u0007\u0002\u0000\u0000\u0212\u0261\u0003$\u0012\u0013\u0213"+
		"\u0214\n\u0011\u0000\u0000\u0214\u0215\u0005#\u0000\u0000\u0215\u0261"+
		"\u0003$\u0012\u0012\u0216\u0217\n\u0010\u0000\u0000\u0217\u0218\u0007"+
		"\u0003\u0000\u0000\u0218\u0261\u0003$\u0012\u0011\u0219\u021a\n\u000f"+
		"\u0000\u0000\u021a\u021b\u0007\u0004\u0000\u0000\u021b\u0261\u0003$\u0012"+
		"\u0010\u021c\u021d\n\u000e\u0000\u0000\u021d\u021e\u0005\u000f\u0000\u0000"+
		"\u021e\u021f\u0003$\u0012\u0000\u021f\u0220\u0005\u000b\u0000\u0000\u0220"+
		"\u0221\u0003$\u0012\u000f\u0221\u0261\u0001\u0000\u0000\u0000\u0222\u0223"+
		"\n1\u0000\u0000\u0223\u0224\u0005R\u0000\u0000\u0224\u0225\u0005\u0004"+
		"\u0000\u0000\u0225\u0226\u0003$\u0012\u0000\u0226\u0227\u0005\u0005\u0000"+
		"\u0000\u0227\u0261\u0001\u0000\u0000\u0000\u0228\u0229\n0\u0000\u0000"+
		"\u0229\u022a\u0005S\u0000\u0000\u022a\u022b\u0005\u0004\u0000\u0000\u022b"+
		"\u022c\u0003$\u0012\u0000\u022c\u022d\u0005\u0005\u0000\u0000\u022d\u0261"+
		"\u0001\u0000\u0000\u0000\u022e\u022f\n/\u0000\u0000\u022f\u0230\u0005"+
		"T\u0000\u0000\u0230\u0231\u0005\u0004\u0000\u0000\u0231\u0261\u0005\u0005"+
		"\u0000\u0000\u0232\u0233\n.\u0000\u0000\u0233\u0261\u0007\u0005\u0000"+
		"\u0000\u0234\u0235\n\u001e\u0000\u0000\u0235\u0236\u0005_\u0000\u0000"+
		"\u0236\u0237\u0005\u0004\u0000\u0000\u0237\u0238\u0003$\u0012\u0000\u0238"+
		"\u0239\u0005\f\u0000\u0000\u0239\u023a\u0003$\u0012\u0000\u023a\u023b"+
		"\u0005\f\u0000\u0000\u023b\u023c\u0003$\u0012\u0000\u023c\u023d\u0005"+
		"\f\u0000\u0000\u023d\u023e\u0003$\u0012\u0000\u023e\u023f\u0005\u0005"+
		"\u0000\u0000\u023f\u0261\u0001\u0000\u0000\u0000\u0240\u0241\n\u001d\u0000"+
		"\u0000\u0241\u0242\u0005U\u0000\u0000\u0242\u0243\u0005\u0004\u0000\u0000"+
		"\u0243\u0244\u0003$\u0012\u0000\u0244\u0245\u0005\f\u0000\u0000\u0245"+
		"\u0246\u0003$\u0012\u0000\u0246\u0247\u0005\u0005\u0000\u0000\u0247\u0261"+
		"\u0001\u0000\u0000\u0000\u0248\u0249\n\u001c\u0000\u0000\u0249\u0261\u0007"+
		"\u0006\u0000\u0000\u024a\u024b\n\u0019\u0000\u0000\u024b\u024c\u0005Z"+
		"\u0000\u0000\u024c\u024d\u0005\u0004\u0000\u0000\u024d\u024e\u0003$\u0012"+
		"\u0000\u024e\u024f\u0005\u0005\u0000\u0000\u024f\u0261\u0001\u0000\u0000"+
		"\u0000\u0250\u0251\n\u0018\u0000\u0000\u0251\u0252\u0005[\u0000\u0000"+
		"\u0252\u0253\u0005\u0004\u0000\u0000\u0253\u0254\u0003$\u0012\u0000\u0254"+
		"\u0255\u0005\f\u0000\u0000\u0255\u0256\u0003$\u0012\u0000\u0256\u0257"+
		"\u0005\u0005\u0000\u0000\u0257\u0261\u0001\u0000\u0000\u0000\u0258\u0259"+
		"\n\u0017\u0000\u0000\u0259\u025a\u0005`\u0000\u0000\u025a\u0261\u0003"+
		"*\u0015\u0000\u025b\u025c\n\u0016\u0000\u0000\u025c\u025d\u0005\u001c"+
		"\u0000\u0000\u025d\u025e\u00034\u001a\u0000\u025e\u025f\u0003*\u0015\u0000"+
		"\u025f\u0261\u0001\u0000\u0000\u0000\u0260\u020d\u0001\u0000\u0000\u0000"+
		"\u0260\u0210\u0001\u0000\u0000\u0000\u0260\u0213\u0001\u0000\u0000\u0000"+
		"\u0260\u0216\u0001\u0000\u0000\u0000\u0260\u0219\u0001\u0000\u0000\u0000"+
		"\u0260\u021c\u0001\u0000\u0000\u0000\u0260\u0222\u0001\u0000\u0000\u0000"+
		"\u0260\u0228\u0001\u0000\u0000\u0000\u0260\u022e\u0001\u0000\u0000\u0000"+
		"\u0260\u0232\u0001\u0000\u0000\u0000\u0260\u0234\u0001\u0000\u0000\u0000"+
		"\u0260\u0240\u0001\u0000\u0000\u0000\u0260\u0248\u0001\u0000\u0000\u0000"+
		"\u0260\u024a\u0001\u0000\u0000\u0000\u0260\u0250\u0001\u0000\u0000\u0000"+
		"\u0260\u0258\u0001\u0000\u0000\u0000\u0260\u025b\u0001\u0000\u0000\u0000"+
		"\u0261\u0264\u0001\u0000\u0000\u0000\u0262\u0260\u0001\u0000\u0000\u0000"+
		"\u0262\u0263\u0001\u0000\u0000\u0000\u0263%\u0001\u0000\u0000\u0000\u0264"+
		"\u0262\u0001\u0000\u0000\u0000\u0265\u026a\u0003(\u0014\u0000\u0266\u0267"+
		"\u0005\f\u0000\u0000\u0267\u0269\u0003(\u0014\u0000\u0268\u0266\u0001"+
		"\u0000\u0000\u0000\u0269\u026c\u0001\u0000\u0000\u0000\u026a\u0268\u0001"+
		"\u0000\u0000\u0000\u026a\u026b\u0001\u0000\u0000\u0000\u026b\'\u0001\u0000"+
		"\u0000\u0000\u026c\u026a\u0001\u0000\u0000\u0000\u026d\u026e\u0003$\u0012"+
		"\u0000\u026e\u026f\u0005\u000b\u0000\u0000\u026f\u0270\u0003$\u0012\u0000"+
		"\u0270)\u0001\u0000\u0000\u0000\u0271\u027a\u0005\u0004\u0000\u0000\u0272"+
		"\u0277\u0003$\u0012\u0000\u0273\u0274\u0005\f\u0000\u0000\u0274\u0276"+
		"\u0003$\u0012\u0000\u0275\u0273\u0001\u0000\u0000\u0000\u0276\u0279\u0001"+
		"\u0000\u0000\u0000\u0277\u0275\u0001\u0000\u0000\u0000\u0277\u0278\u0001"+
		"\u0000\u0000\u0000\u0278\u027b\u0001\u0000\u0000\u0000\u0279\u0277\u0001"+
		"\u0000\u0000\u0000\u027a\u0272\u0001\u0000\u0000\u0000\u027a\u027b\u0001"+
		"\u0000\u0000\u0000\u027b\u027c\u0001\u0000\u0000\u0000\u027c\u027d\u0005"+
		"\u0005\u0000\u0000\u027d+\u0001\u0000\u0000\u0000\u027e\u027f\u00032\u0019"+
		"\u0000\u027f\u0280\u0005\u0010\u0000\u0000\u0280\u0281\u0003$\u0012\u0000"+
		"\u0281\u02a5\u0001\u0000\u0000\u0000\u0282\u0283\u00032\u0019\u0000\u0283"+
		"\u0284\u0005\u0011\u0000\u0000\u0284\u02a5\u0001\u0000\u0000\u0000\u0285"+
		"\u0286\u00032\u0019\u0000\u0286\u0287\u0005\u0012\u0000\u0000\u0287\u02a5"+
		"\u0001\u0000\u0000\u0000\u0288\u0289\u00032\u0019\u0000\u0289\u028a\u0005"+
		"\u0013\u0000\u0000\u028a\u028b\u0003$\u0012\u0000\u028b\u02a5\u0001\u0000"+
		"\u0000\u0000\u028c\u028d\u00032\u0019\u0000\u028d\u028e\u0005\u0014\u0000"+
		"\u0000\u028e\u028f\u0003$\u0012\u0000\u028f\u02a5\u0001\u0000\u0000\u0000"+
		"\u0290\u0291\u00032\u0019\u0000\u0291\u0292\u0005\u0015\u0000\u0000\u0292"+
		"\u0293\u0003$\u0012\u0000\u0293\u02a5\u0001\u0000\u0000\u0000\u0294\u0295"+
		"\u00032\u0019\u0000\u0295\u0296\u0005\u0016\u0000\u0000\u0296\u0297\u0003"+
		"$\u0012\u0000\u0297\u02a5\u0001\u0000\u0000\u0000\u0298\u0299\u00032\u0019"+
		"\u0000\u0299\u029a\u0005\u0017\u0000\u0000\u029a\u029b\u0003$\u0012\u0000"+
		"\u029b\u02a5\u0001\u0000\u0000\u0000\u029c\u029d\u00032\u0019\u0000\u029d"+
		"\u029e\u0005\u0018\u0000\u0000\u029e\u029f\u0003$\u0012\u0000\u029f\u02a5"+
		"\u0001\u0000\u0000\u0000\u02a0\u02a1\u00032\u0019\u0000\u02a1\u02a2\u0005"+
		"\u0019\u0000\u0000\u02a2\u02a3\u0003$\u0012\u0000\u02a3\u02a5\u0001\u0000"+
		"\u0000\u0000\u02a4\u027e\u0001\u0000\u0000\u0000\u02a4\u0282\u0001\u0000"+
		"\u0000\u0000\u02a4\u0285\u0001\u0000\u0000\u0000\u02a4\u0288\u0001\u0000"+
		"\u0000\u0000\u02a4\u028c\u0001\u0000\u0000\u0000\u02a4\u0290\u0001\u0000"+
		"\u0000\u0000\u02a4\u0294\u0001\u0000\u0000\u0000\u02a4\u0298\u0001\u0000"+
		"\u0000\u0000\u02a4\u029c\u0001\u0000\u0000\u0000\u02a4\u02a0\u0001\u0000"+
		"\u0000\u0000\u02a5-\u0001\u0000\u0000\u0000\u02a6\u02a7\u0003\n\u0005"+
		"\u0000\u02a7\u02a8\u0005\u0010\u0000\u0000\u02a8\u02a9\u0003$\u0012\u0000"+
		"\u02a9/\u0001\u0000\u0000\u0000\u02aa\u02ad\u0003\n\u0005\u0000\u02ab"+
		"\u02ad\u0003.\u0017\u0000\u02ac\u02aa\u0001\u0000\u0000\u0000\u02ac\u02ab"+
		"\u0001\u0000\u0000\u0000\u02ad1\u0001\u0000\u0000\u0000\u02ae\u02ba\u0003"+
		"4\u001a\u0000\u02af\u02b0\u00034\u001a\u0000\u02b0\u02b1\u0005 \u0000"+
		"\u0000\u02b1\u02b2\u0003$\u0012\u0000\u02b2\u02b3\u0005\u001f\u0000\u0000"+
		"\u02b3\u02ba\u0001\u0000\u0000\u0000\u02b4\u02b5\u00034\u001a\u0000\u02b5"+
		"\u02b6\u0005\u0006\u0000\u0000\u02b6\u02b7\u0003$\u0012\u0000\u02b7\u02b8"+
		"\u0005\u0007\u0000\u0000\u02b8\u02ba\u0001\u0000\u0000\u0000\u02b9\u02ae"+
		"\u0001\u0000\u0000\u0000\u02b9\u02af\u0001\u0000\u0000\u0000\u02b9\u02b4"+
		"\u0001\u0000\u0000\u0000\u02ba3\u0001\u0000\u0000\u0000\u02bb\u02bc\u0005"+
		"j\u0000\u0000\u02bc5\u0001\u0000\u0000\u0000\u02bd\u02c4\u0005g\u0000"+
		"\u0000\u02be\u02c4\u0005h\u0000\u0000\u02bf\u02c4\u0005d\u0000\u0000\u02c0"+
		"\u02c4\u00038\u001c\u0000\u02c1\u02c4\u0005a\u0000\u0000\u02c2\u02c4\u0003"+
		":\u001d\u0000\u02c3\u02bd\u0001\u0000\u0000\u0000\u02c3\u02be\u0001\u0000"+
		"\u0000\u0000\u02c3\u02bf\u0001\u0000\u0000\u0000\u02c3\u02c0\u0001\u0000"+
		"\u0000\u0000\u02c3\u02c1\u0001\u0000\u0000\u0000\u02c3\u02c2\u0001\u0000"+
		"\u0000\u0000\u02c47\u0001\u0000\u0000\u0000\u02c5\u02c8\u0005c\u0000\u0000"+
		"\u02c6\u02c8\u0005b\u0000\u0000\u02c7\u02c5\u0001\u0000\u0000\u0000\u02c7"+
		"\u02c6\u0001\u0000\u0000\u0000\u02c89\u0001\u0000\u0000\u0000\u02c9\u02cc"+
		"\u0005<\u0000\u0000\u02ca\u02cc\u0005=\u0000\u0000\u02cb\u02c9\u0001\u0000"+
		"\u0000\u0000\u02cb\u02ca\u0001\u0000\u0000\u0000\u02cc;\u0001\u0000\u0000"+
		"\u0000#AKOTZae}\u0088\u008a\u0096\u0099\u00a0\u00a4\u00b5\u010b\u010f"+
		"\u0121\u013d\u0142\u01cf\u01da\u01e5\u020b\u0260\u0262\u026a\u0277\u027a"+
		"\u02a4\u02ac\u02b9\u02c3\u02c7\u02cb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}