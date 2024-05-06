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
		OR_ASSIGN=25, ARROW=26, DEF=27, EQUAL=28, NOT_EQUAL=29, GT=30, LT=31, 
		GEQ=32, LEQ=33, RAISE=34, PLUS=35, MINUS=36, TIMES=37, DIVIDE=38, MOD=39, 
		AND=40, OR=41, NOT=42, SIZE=43, IN=44, FINAL=45, BOOL=46, FLOAT=47, INT=48, 
		CHAR=49, COLOR=50, IMAGE=51, STRING=52, RETURN=53, DO=54, WHILE=55, FOR=56, 
		IF=57, ELSE=58, TRUE=59, FALSE=60, NEW=61, FROM=62, RGBA=63, RGB=64, BLANK=65, 
		TEX_COL_REPL=66, GEN_LOOKUP=67, ABS=68, MIN=69, MAX=70, CLAMP=71, RAND=72, 
		PROB=73, FLIP_COIN=74, RED=75, GREEN=76, BLUE=77, ALPHA=78, WIDTH=79, 
		HEIGHT=80, HAS=81, LOOKUP=82, KEYS=83, PIXEL=84, ADD=85, REMOVE=86, DEFINE=87, 
		DRAW=88, AT=89, SUB=90, DOT=91, LINE=92, FILL=93, SECTION=94, FLOAT_LIT=95, 
		DEC_LIT=96, HEX_LIT=97, COL_LIT=98, CHAR_QUOTE=99, STR_QUOTE=100, STRING_LIT=101, 
		CHAR_LIT=102, ESC_CHAR=103, IDENTIFIER=104;
	public static final int
		RULE_head_rule = 0, RULE_helper = 1, RULE_func_body = 2, RULE_signature = 3, 
		RULE_param_list = 4, RULE_declaration = 5, RULE_type = 6, RULE_body = 7, 
		RULE_stat = 8, RULE_return_stat = 9, RULE_loop_stat = 10, RULE_iteration_def = 11, 
		RULE_while_def = 12, RULE_for_def = 13, RULE_if_stat = 14, RULE_if_def = 15, 
		RULE_expr = 16, RULE_k_v_pairs = 17, RULE_k_v_pair = 18, RULE_func_call = 19, 
		RULE_assignment = 20, RULE_var_init = 21, RULE_var_def = 22, RULE_assignable = 23, 
		RULE_ident = 24, RULE_literal = 25, RULE_int_lit = 26, RULE_bool_lit = 27;
	private static String[] makeRuleNames() {
		return new String[] {
			"head_rule", "helper", "func_body", "signature", "param_list", "declaration", 
			"type", "body", "stat", "return_stat", "loop_stat", "iteration_def", 
			"while_def", "for_def", "if_stat", "if_def", "expr", "k_v_pairs", "k_v_pair", 
			"func_call", "assignment", "var_init", "var_def", "assignable", "ident", 
			"literal", "int_lit", "bool_lit"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'('", "')'", "'['", "']'", "'{'", "'}'", "';'", 
			"':'", "','", "'.'", "'|'", "'?'", "'='", "'++'", "'--'", "'+='", "'-='", 
			"'*='", "'/='", "'%='", "'&='", "'|='", "'->'", "'::'", "'=='", "'!='", 
			"'>'", "'<'", "'>='", "'<='", "'^'", "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'&&'", "'||'", "'!'", "'#|'", "'in'", null, "'bool'", "'float'", "'int'", 
			"'char'", "'color'", "'image'", "'string'", "'return'", "'do'", "'while'", 
			"'for'", "'if'", "'else'", "'true'", "'false'", "'new'", "'from'", "'rgba'", 
			"'rgb'", "'blank'", "'tex_col_repl'", "'gen_lookup'", "'abs'", "'min'", 
			"'max'", "'clamp'", "'rand'", "'prob'", "'flip_coin'", null, null, null, 
			null, null, null, "'.has'", "'.lookup'", "'.keys'", "'.pixel'", "'.add'", 
			"'.remove'", "'.define'", "'.draw'", "'.at'", "'.sub'", "'.dot'", "'.line'", 
			"'.fill'", "'.section'", null, null, null, null, "'''", "'\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "LINE_COMMENT", "MULTILINE_COMMENT", "LPAREN", "RPAREN", 
			"LBRACKET", "RBRACKET", "LCURLY", "RCURLY", "SEMICOLON", "COLON", "COMMA", 
			"PERIOD", "PIPE", "QUESTION", "ASSIGN", "INCREMENT", "DECREMENT", "ADD_ASSIGN", 
			"SUB_ASSIGN", "MUL_ASSIGN", "DIV_ASSIGN", "MOD_ASSIGN", "AND_ASSIGN", 
			"OR_ASSIGN", "ARROW", "DEF", "EQUAL", "NOT_EQUAL", "GT", "LT", "GEQ", 
			"LEQ", "RAISE", "PLUS", "MINUS", "TIMES", "DIVIDE", "MOD", "AND", "OR", 
			"NOT", "SIZE", "IN", "FINAL", "BOOL", "FLOAT", "INT", "CHAR", "COLOR", 
			"IMAGE", "STRING", "RETURN", "DO", "WHILE", "FOR", "IF", "ELSE", "TRUE", 
			"FALSE", "NEW", "FROM", "RGBA", "RGB", "BLANK", "TEX_COL_REPL", "GEN_LOOKUP", 
			"ABS", "MIN", "MAX", "CLAMP", "RAND", "PROB", "FLIP_COIN", "RED", "GREEN", 
			"BLUE", "ALPHA", "WIDTH", "HEIGHT", "HAS", "LOOKUP", "KEYS", "PIXEL", 
			"ADD", "REMOVE", "DEFINE", "DRAW", "AT", "SUB", "DOT", "LINE", "FILL", 
			"SECTION", "FLOAT_LIT", "DEC_LIT", "HEX_LIT", "COL_LIT", "CHAR_QUOTE", 
			"STR_QUOTE", "STRING_LIT", "CHAR_LIT", "ESC_CHAR", "IDENTIFIER"
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
			setState(56);
			signature();
			setState(57);
			func_body();
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(58);
				helper();
				}
				}
				setState(63);
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
			setState(64);
			ident();
			setState(65);
			signature();
			setState(66);
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
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case LBRACKET:
			case LCURLY:
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
				setState(68);
				body();
				}
				break;
			case ARROW:
				_localctx = new FunctionalFuncBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				match(ARROW);
				setState(70);
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
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new VoidReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				match(LPAREN);
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8972014882652416L) != 0)) {
					{
					setState(74);
					param_list();
					}
				}

				setState(77);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new TypeReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				match(LPAREN);
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8972014882652416L) != 0)) {
					{
					setState(79);
					param_list();
					}
				}

				setState(82);
				match(ARROW);
				setState(83);
				type(0);
				setState(84);
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
			setState(88);
			declaration();
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(89);
				match(COMMA);
				setState(90);
				declaration();
				}
				}
				setState(95);
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
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINAL) {
				{
				setState(96);
				match(FINAL);
				}
			}

			setState(99);
			type(0);
			setState(100);
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
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
				{
				_localctx = new BoolTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(103);
				match(BOOL);
				}
				break;
			case INT:
				{
				_localctx = new IntTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(104);
				match(INT);
				}
				break;
			case FLOAT:
				{
				_localctx = new FloatTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(105);
				match(FLOAT);
				}
				break;
			case CHAR:
				{
				_localctx = new CharTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(106);
				match(CHAR);
				}
				break;
			case STRING:
				{
				_localctx = new StringTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(107);
				match(STRING);
				}
				break;
			case IMAGE:
				{
				_localctx = new ImageTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(108);
				match(IMAGE);
				}
				break;
			case COLOR:
				{
				_localctx = new ColorTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(109);
				match(COLOR);
				}
				break;
			case LCURLY:
				{
				_localctx = new MapTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(110);
				match(LCURLY);
				setState(111);
				((MapTypeContext)_localctx).key = type(0);
				setState(112);
				match(COLON);
				setState(113);
				((MapTypeContext)_localctx).val = type(0);
				setState(114);
				match(RCURLY);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(129);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(127);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(118);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(119);
						match(LBRACKET);
						setState(120);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new ListTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(121);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(122);
						match(LT);
						setState(123);
						match(GT);
						}
						break;
					case 3:
						{
						_localctx = new SetTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(124);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(125);
						match(LCURLY);
						setState(126);
						match(RCURLY);
						}
						break;
					}
					} 
				}
				setState(131);
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
		enterRule(_localctx, 14, RULE_body);
		int _la;
		try {
			setState(141);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new SingleStatBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				stat();
				}
				break;
			case 2:
				_localctx = new ComplexBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				match(LCURLY);
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -288252295517306544L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 1544040744959L) != 0)) {
					{
					{
					setState(134);
					stat();
					}
					}
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(140);
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

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_stat);
		int _la;
		try {
			setState(233);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new LoopStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				loop_stat();
				}
				break;
			case 2:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				if_stat();
				}
				break;
			case 3:
				_localctx = new VarDefStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
				var_def();
				setState(146);
				match(SEMICOLON);
				}
				break;
			case 4:
				_localctx = new AssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(148);
				assignment();
				setState(149);
				match(SEMICOLON);
				}
				break;
			case 5:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(151);
				return_stat();
				}
				break;
			case 6:
				_localctx = new AddToCollectionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(152);
				((AddToCollectionContext)_localctx).col = expr(0);
				setState(153);
				match(ADD);
				setState(154);
				match(LPAREN);
				setState(155);
				((AddToCollectionContext)_localctx).elem = expr(0);
				setState(158);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(156);
					match(COMMA);
					setState(157);
					((AddToCollectionContext)_localctx).index = expr(0);
					}
				}

				setState(160);
				match(RPAREN);
				setState(161);
				match(SEMICOLON);
				}
				break;
			case 7:
				_localctx = new RemoveFromCollectionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(163);
				((RemoveFromCollectionContext)_localctx).col = expr(0);
				setState(164);
				match(REMOVE);
				setState(165);
				match(LPAREN);
				setState(166);
				((RemoveFromCollectionContext)_localctx).arg = expr(0);
				setState(167);
				match(RPAREN);
				setState(168);
				match(SEMICOLON);
				}
				break;
			case 8:
				_localctx = new DefineMapEntryStatementContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(170);
				((DefineMapEntryStatementContext)_localctx).map = expr(0);
				setState(171);
				match(DEFINE);
				setState(172);
				match(LPAREN);
				setState(173);
				((DefineMapEntryStatementContext)_localctx).key = expr(0);
				setState(174);
				match(COMMA);
				setState(175);
				((DefineMapEntryStatementContext)_localctx).val = expr(0);
				setState(176);
				match(RPAREN);
				setState(177);
				match(SEMICOLON);
				}
				break;
			case 9:
				_localctx = new DrawOntoImageStatementContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(179);
				((DrawOntoImageStatementContext)_localctx).canvas = expr(0);
				setState(180);
				match(DRAW);
				setState(181);
				match(LPAREN);
				setState(182);
				((DrawOntoImageStatementContext)_localctx).img = expr(0);
				setState(183);
				match(COMMA);
				setState(184);
				((DrawOntoImageStatementContext)_localctx).x = expr(0);
				setState(185);
				match(COMMA);
				setState(186);
				((DrawOntoImageStatementContext)_localctx).y = expr(0);
				setState(187);
				match(RPAREN);
				setState(188);
				match(SEMICOLON);
				}
				break;
			case 10:
				_localctx = new DotStatementContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(190);
				((DotStatementContext)_localctx).canvas = expr(0);
				setState(191);
				match(DOT);
				setState(192);
				match(LPAREN);
				setState(193);
				((DotStatementContext)_localctx).col = expr(0);
				setState(194);
				match(COMMA);
				setState(195);
				((DotStatementContext)_localctx).x = expr(0);
				setState(196);
				match(COMMA);
				setState(197);
				((DotStatementContext)_localctx).y = expr(0);
				setState(198);
				match(RPAREN);
				setState(199);
				match(SEMICOLON);
				}
				break;
			case 11:
				_localctx = new DrawLineStatementContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(201);
				((DrawLineStatementContext)_localctx).canvas = expr(0);
				setState(202);
				match(LINE);
				setState(203);
				match(LPAREN);
				setState(204);
				((DrawLineStatementContext)_localctx).col = expr(0);
				setState(205);
				match(COMMA);
				setState(206);
				((DrawLineStatementContext)_localctx).breadth = expr(0);
				setState(207);
				match(COMMA);
				setState(208);
				((DrawLineStatementContext)_localctx).x1 = expr(0);
				setState(209);
				match(COMMA);
				setState(210);
				((DrawLineStatementContext)_localctx).y1 = expr(0);
				setState(211);
				match(COMMA);
				setState(212);
				((DrawLineStatementContext)_localctx).x2 = expr(0);
				setState(213);
				match(COMMA);
				setState(214);
				((DrawLineStatementContext)_localctx).y2 = expr(0);
				setState(215);
				match(RPAREN);
				setState(216);
				match(SEMICOLON);
				}
				break;
			case 12:
				_localctx = new FillStatementContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(218);
				((FillStatementContext)_localctx).canvas = expr(0);
				setState(219);
				match(FILL);
				setState(220);
				match(LPAREN);
				setState(221);
				((FillStatementContext)_localctx).col = expr(0);
				setState(222);
				match(COMMA);
				setState(223);
				((FillStatementContext)_localctx).x = expr(0);
				setState(224);
				match(COMMA);
				setState(225);
				((FillStatementContext)_localctx).y = expr(0);
				setState(226);
				match(COMMA);
				setState(227);
				((FillStatementContext)_localctx).w = expr(0);
				setState(228);
				match(COMMA);
				setState(229);
				((FillStatementContext)_localctx).h = expr(0);
				setState(230);
				match(RPAREN);
				setState(231);
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
		enterRule(_localctx, 18, RULE_return_stat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(RETURN);
			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -576447487296929456L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 1544040744959L) != 0)) {
				{
				setState(236);
				expr(0);
				}
			}

			setState(239);
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
		enterRule(_localctx, 20, RULE_loop_stat);
		try {
			setState(255);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new WhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(241);
				while_def();
				setState(242);
				body();
				}
				break;
			case 2:
				_localctx = new IteratorLoopContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(244);
				iteration_def();
				setState(245);
				body();
				}
				break;
			case 3:
				_localctx = new ForLoopContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(247);
				for_def();
				setState(248);
				body();
				}
				break;
			case 4:
				_localctx = new DoWhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(250);
				match(DO);
				setState(251);
				body();
				setState(252);
				while_def();
				setState(253);
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
		enterRule(_localctx, 22, RULE_iteration_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			match(FOR);
			setState(258);
			match(LPAREN);
			setState(259);
			declaration();
			setState(260);
			match(IN);
			setState(261);
			expr(0);
			setState(262);
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
		enterRule(_localctx, 24, RULE_while_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(WHILE);
			setState(265);
			match(LPAREN);
			setState(266);
			expr(0);
			setState(267);
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
		enterRule(_localctx, 26, RULE_for_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			match(FOR);
			setState(270);
			match(LPAREN);
			setState(271);
			var_init();
			setState(272);
			match(SEMICOLON);
			setState(273);
			expr(0);
			setState(274);
			match(SEMICOLON);
			setState(275);
			assignment();
			setState(276);
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
		enterRule(_localctx, 28, RULE_if_stat);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			if_def();
			setState(283);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(279);
					match(ELSE);
					setState(280);
					if_def();
					}
					} 
				}
				setState(285);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			setState(288);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(286);
				match(ELSE);
				setState(287);
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
		enterRule(_localctx, 30, RULE_if_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(IF);
			setState(291);
			match(LPAREN);
			setState(292);
			((If_defContext)_localctx).cond = expr(0);
			setState(293);
			match(RPAREN);
			setState(294);
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
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
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

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(482);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				_localctx = new NestedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(297);
				match(LPAREN);
				setState(298);
				expr(0);
				setState(299);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new CastExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(301);
				match(LPAREN);
				setState(302);
				type(0);
				setState(303);
				match(RPAREN);
				setState(304);
				expr(45);
				}
				break;
			case 3:
				{
				_localctx = new AbsoluteExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(306);
				match(ABS);
				setState(307);
				match(LPAREN);
				setState(308);
				expr(0);
				setState(309);
				match(RPAREN);
				}
				break;
			case 4:
				{
				_localctx = new MinCollectionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(311);
				match(MIN);
				setState(312);
				match(LPAREN);
				setState(313);
				expr(0);
				setState(314);
				match(RPAREN);
				}
				break;
			case 5:
				{
				_localctx = new MinTwoArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(316);
				match(MIN);
				setState(317);
				match(LPAREN);
				setState(318);
				((MinTwoArgExpressionContext)_localctx).a = expr(0);
				setState(319);
				match(COMMA);
				setState(320);
				((MinTwoArgExpressionContext)_localctx).b = expr(0);
				setState(321);
				match(RPAREN);
				}
				break;
			case 6:
				{
				_localctx = new MaxCollectionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(323);
				match(MAX);
				setState(324);
				match(LPAREN);
				setState(325);
				expr(0);
				setState(326);
				match(RPAREN);
				}
				break;
			case 7:
				{
				_localctx = new MaxTwoArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(328);
				match(MAX);
				setState(329);
				match(LPAREN);
				setState(330);
				((MaxTwoArgExpressionContext)_localctx).a = expr(0);
				setState(331);
				match(COMMA);
				setState(332);
				((MaxTwoArgExpressionContext)_localctx).b = expr(0);
				setState(333);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new ClampExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(335);
				match(CLAMP);
				setState(336);
				match(LPAREN);
				setState(337);
				((ClampExpressionContext)_localctx).min = expr(0);
				setState(338);
				match(COMMA);
				setState(339);
				((ClampExpressionContext)_localctx).val = expr(0);
				setState(340);
				match(COMMA);
				setState(341);
				((ClampExpressionContext)_localctx).max = expr(0);
				setState(342);
				match(RPAREN);
				}
				break;
			case 9:
				{
				_localctx = new RandomExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(344);
				match(RAND);
				setState(345);
				match(LPAREN);
				setState(346);
				match(RPAREN);
				}
				break;
			case 10:
				{
				_localctx = new RandomTwoArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(347);
				match(RAND);
				setState(348);
				match(LPAREN);
				setState(349);
				((RandomTwoArgExpressionContext)_localctx).min = expr(0);
				setState(350);
				match(COMMA);
				setState(351);
				((RandomTwoArgExpressionContext)_localctx).max = expr(0);
				setState(352);
				match(RPAREN);
				}
				break;
			case 11:
				{
				_localctx = new ProbabilityExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(354);
				match(PROB);
				setState(355);
				match(LPAREN);
				setState(356);
				expr(0);
				setState(357);
				match(RPAREN);
				}
				break;
			case 12:
				{
				_localctx = new FlipCoinBoolExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(359);
				match(FLIP_COIN);
				setState(360);
				match(LPAREN);
				setState(361);
				match(RPAREN);
				}
				break;
			case 13:
				{
				_localctx = new FlipCoinArgExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(362);
				match(FLIP_COIN);
				setState(363);
				match(LPAREN);
				setState(364);
				((FlipCoinArgExpressionContext)_localctx).t = expr(0);
				setState(365);
				match(COMMA);
				setState(366);
				((FlipCoinArgExpressionContext)_localctx).f = expr(0);
				setState(367);
				match(RPAREN);
				}
				break;
			case 14:
				{
				_localctx = new ImageFromPathExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(369);
				match(FROM);
				setState(370);
				match(LPAREN);
				setState(371);
				expr(0);
				setState(372);
				match(RPAREN);
				}
				break;
			case 15:
				{
				_localctx = new ImageOfBoundsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(374);
				match(BLANK);
				setState(375);
				match(LPAREN);
				setState(376);
				((ImageOfBoundsExpressionContext)_localctx).width = expr(0);
				setState(377);
				match(COMMA);
				setState(378);
				((ImageOfBoundsExpressionContext)_localctx).height = expr(0);
				setState(379);
				match(RPAREN);
				}
				break;
			case 16:
				{
				_localctx = new TextureColorReplaceExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(381);
				match(TEX_COL_REPL);
				setState(382);
				match(LPAREN);
				setState(383);
				((TextureColorReplaceExpressionContext)_localctx).texture = expr(0);
				setState(384);
				match(COMMA);
				setState(385);
				((TextureColorReplaceExpressionContext)_localctx).lookup = expr(0);
				setState(386);
				match(COMMA);
				setState(387);
				((TextureColorReplaceExpressionContext)_localctx).replace = expr(0);
				setState(388);
				match(RPAREN);
				}
				break;
			case 17:
				{
				_localctx = new GenLookupExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(390);
				match(GEN_LOOKUP);
				setState(391);
				match(LPAREN);
				setState(392);
				((GenLookupExpressionContext)_localctx).source = expr(0);
				setState(393);
				match(COMMA);
				setState(394);
				((GenLookupExpressionContext)_localctx).vert = expr(0);
				setState(395);
				match(RPAREN);
				}
				break;
			case 18:
				{
				_localctx = new RGBColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(397);
				match(RGB);
				setState(398);
				match(LPAREN);
				setState(399);
				((RGBColorExpressionContext)_localctx).r = expr(0);
				setState(400);
				match(COMMA);
				setState(401);
				((RGBColorExpressionContext)_localctx).g = expr(0);
				setState(402);
				match(COMMA);
				setState(403);
				((RGBColorExpressionContext)_localctx).b = expr(0);
				setState(404);
				match(RPAREN);
				}
				break;
			case 19:
				{
				_localctx = new RGBAColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(406);
				match(RGBA);
				setState(407);
				match(LPAREN);
				setState(408);
				((RGBAColorExpressionContext)_localctx).r = expr(0);
				setState(409);
				match(COMMA);
				setState(410);
				((RGBAColorExpressionContext)_localctx).g = expr(0);
				setState(411);
				match(COMMA);
				setState(412);
				((RGBAColorExpressionContext)_localctx).b = expr(0);
				setState(413);
				match(COMMA);
				setState(414);
				((RGBAColorExpressionContext)_localctx).a = expr(0);
				setState(415);
				match(RPAREN);
				}
				break;
			case 20:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(417);
				((UnaryExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 13262859010048L) != 0)) ) {
					((UnaryExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(418);
				expr(18);
				}
				break;
			case 21:
				{
				_localctx = new ExplicitMapExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(419);
				match(LCURLY);
				setState(420);
				k_v_pairs();
				setState(421);
				match(RCURLY);
				}
				break;
			case 22:
				{
				_localctx = new ExplicitArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(423);
				match(LBRACKET);
				setState(424);
				expr(0);
				setState(429);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(425);
					match(COMMA);
					setState(426);
					expr(0);
					}
					}
					setState(431);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(432);
				match(RBRACKET);
				}
				break;
			case 23:
				{
				_localctx = new ExplicitListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(434);
				match(LT);
				setState(435);
				expr(0);
				setState(440);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(436);
					match(COMMA);
					setState(437);
					expr(0);
					}
					}
					setState(442);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(443);
				match(GT);
				}
				break;
			case 24:
				{
				_localctx = new ExplicitSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(445);
				match(LCURLY);
				setState(446);
				expr(0);
				setState(451);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(447);
					match(COMMA);
					setState(448);
					expr(0);
					}
					}
					setState(453);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(454);
				match(RCURLY);
				}
				break;
			case 25:
				{
				_localctx = new NewArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(456);
				match(NEW);
				setState(457);
				type(0);
				setState(458);
				match(LBRACKET);
				setState(459);
				expr(0);
				setState(460);
				match(RBRACKET);
				}
				break;
			case 26:
				{
				_localctx = new NewListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(462);
				match(NEW);
				setState(463);
				type(0);
				setState(464);
				match(LT);
				setState(465);
				match(GT);
				}
				break;
			case 27:
				{
				_localctx = new NewSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(467);
				match(NEW);
				setState(468);
				type(0);
				setState(469);
				match(LCURLY);
				setState(470);
				match(RCURLY);
				}
				break;
			case 28:
				{
				_localctx = new NewMapExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(472);
				match(NEW);
				setState(473);
				match(LCURLY);
				setState(474);
				((NewMapExpressionContext)_localctx).kt = type(0);
				setState(475);
				match(COLON);
				setState(476);
				((NewMapExpressionContext)_localctx).vt = type(0);
				setState(477);
				match(RCURLY);
				}
				break;
			case 29:
				{
				_localctx = new FunctionCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(479);
				func_call();
				}
				break;
			case 30:
				{
				_localctx = new AssignableExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(480);
				assignable();
				}
				break;
			case 31:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(481);
				literal();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(561);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(559);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ArithmeticBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(484);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(485);
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
						setState(486);
						((ArithmeticBinExpressionContext)_localctx).b = expr(18);
						}
						break;
					case 2:
						{
						_localctx = new MultBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((MultBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(487);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(488);
						((MultBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0)) ) {
							((MultBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(489);
						((MultBinExpressionContext)_localctx).b = expr(17);
						}
						break;
					case 3:
						{
						_localctx = new PowerBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((PowerBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(490);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(491);
						match(RAISE);
						setState(492);
						((PowerBinExpressionContext)_localctx).b = expr(16);
						}
						break;
					case 4:
						{
						_localctx = new ComparisonBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ComparisonBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(493);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(494);
						((ComparisonBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16911433728L) != 0)) ) {
							((ComparisonBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(495);
						((ComparisonBinExpressionContext)_localctx).b = expr(15);
						}
						break;
					case 5:
						{
						_localctx = new LogicBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((LogicBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(496);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(497);
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
						setState(498);
						((LogicBinExpressionContext)_localctx).b = expr(14);
						}
						break;
					case 6:
						{
						_localctx = new TernaryExpressionContext(new ExprContext(_parentctx, _parentState));
						((TernaryExpressionContext)_localctx).cond = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(499);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(500);
						match(QUESTION);
						setState(501);
						((TernaryExpressionContext)_localctx).if_ = expr(0);
						setState(502);
						match(COLON);
						setState(503);
						((TernaryExpressionContext)_localctx).else_ = expr(13);
						}
						break;
					case 7:
						{
						_localctx = new ContainsExpressionContext(new ExprContext(_parentctx, _parentState));
						((ContainsExpressionContext)_localctx).col = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(505);
						if (!(precpred(_ctx, 44))) throw new FailedPredicateException(this, "precpred(_ctx, 44)");
						setState(506);
						match(HAS);
						setState(507);
						match(LPAREN);
						setState(508);
						((ContainsExpressionContext)_localctx).elem = expr(0);
						setState(509);
						match(RPAREN);
						}
						break;
					case 8:
						{
						_localctx = new MapLookupExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapLookupExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(511);
						if (!(precpred(_ctx, 43))) throw new FailedPredicateException(this, "precpred(_ctx, 43)");
						setState(512);
						match(LOOKUP);
						setState(513);
						match(LPAREN);
						setState(514);
						((MapLookupExpressionContext)_localctx).elem = expr(0);
						setState(515);
						match(RPAREN);
						}
						break;
					case 9:
						{
						_localctx = new MapKeysetExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapKeysetExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(517);
						if (!(precpred(_ctx, 42))) throw new FailedPredicateException(this, "precpred(_ctx, 42)");
						setState(518);
						match(KEYS);
						setState(519);
						match(LPAREN);
						setState(520);
						match(RPAREN);
						}
						break;
					case 10:
						{
						_localctx = new ColorChannelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorChannelExpressionContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(521);
						if (!(precpred(_ctx, 41))) throw new FailedPredicateException(this, "precpred(_ctx, 41)");
						setState(522);
						((ColorChannelExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 75)) & ~0x3f) == 0 && ((1L << (_la - 75)) & 15L) != 0)) ) {
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
						setState(523);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(524);
						match(SECTION);
						setState(525);
						match(LPAREN);
						setState(526);
						((ImageSectionExpressionContext)_localctx).x = expr(0);
						setState(527);
						match(COMMA);
						setState(528);
						((ImageSectionExpressionContext)_localctx).y = expr(0);
						setState(529);
						match(COMMA);
						setState(530);
						((ImageSectionExpressionContext)_localctx).w = expr(0);
						setState(531);
						match(COMMA);
						setState(532);
						((ImageSectionExpressionContext)_localctx).h = expr(0);
						setState(533);
						match(RPAREN);
						}
						break;
					case 12:
						{
						_localctx = new ColorAtPixelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorAtPixelExpressionContext)_localctx).img = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(535);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(536);
						match(PIXEL);
						setState(537);
						match(LPAREN);
						setState(538);
						((ColorAtPixelExpressionContext)_localctx).x = expr(0);
						setState(539);
						match(COMMA);
						setState(540);
						((ColorAtPixelExpressionContext)_localctx).y = expr(0);
						setState(541);
						match(RPAREN);
						}
						break;
					case 13:
						{
						_localctx = new ImageBoundExpressionContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(543);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(544);
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
						setState(545);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(546);
						match(AT);
						setState(547);
						match(LPAREN);
						setState(548);
						((CharAtExpressionContext)_localctx).index = expr(0);
						setState(549);
						match(RPAREN);
						}
						break;
					case 15:
						{
						_localctx = new SubstringExpressionContext(new ExprContext(_parentctx, _parentState));
						((SubstringExpressionContext)_localctx).string = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(551);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(552);
						match(SUB);
						setState(553);
						match(LPAREN);
						setState(554);
						((SubstringExpressionContext)_localctx).beg = expr(0);
						setState(555);
						match(COMMA);
						setState(556);
						((SubstringExpressionContext)_localctx).end = expr(0);
						setState(557);
						match(RPAREN);
						}
						break;
					}
					} 
				}
				setState(563);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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
		enterRule(_localctx, 34, RULE_k_v_pairs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(564);
			k_v_pair();
			setState(569);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(565);
				match(COMMA);
				setState(566);
				k_v_pair();
				}
				}
				setState(571);
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
		enterRule(_localctx, 36, RULE_k_v_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			((K_v_pairContext)_localctx).key = expr(0);
			setState(573);
			match(COLON);
			setState(574);
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
	public static class Func_callContext extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
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
		public Func_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_call; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScriptParserVisitor ) return ((ScriptParserVisitor<? extends T>)visitor).visitFunc_call(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_callContext func_call() throws RecognitionException {
		Func_callContext _localctx = new Func_callContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_func_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(576);
			ident();
			setState(577);
			match(LPAREN);
			setState(586);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -576447487296929456L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 1544040744959L) != 0)) {
				{
				setState(578);
				expr(0);
				setState(583);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(579);
					match(COMMA);
					setState(580);
					expr(0);
					}
					}
					setState(585);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(588);
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
		enterRule(_localctx, 40, RULE_assignment);
		try {
			setState(628);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				_localctx = new StandardAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(590);
				assignable();
				setState(591);
				match(ASSIGN);
				setState(592);
				expr(0);
				}
				break;
			case 2:
				_localctx = new IncrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(594);
				assignable();
				setState(595);
				match(INCREMENT);
				}
				break;
			case 3:
				_localctx = new DecrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(597);
				assignable();
				setState(598);
				match(DECREMENT);
				}
				break;
			case 4:
				_localctx = new AddAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(600);
				assignable();
				setState(601);
				match(ADD_ASSIGN);
				setState(602);
				expr(0);
				}
				break;
			case 5:
				_localctx = new SubAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(604);
				assignable();
				setState(605);
				match(SUB_ASSIGN);
				setState(606);
				expr(0);
				}
				break;
			case 6:
				_localctx = new MultAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(608);
				assignable();
				setState(609);
				match(MUL_ASSIGN);
				setState(610);
				expr(0);
				}
				break;
			case 7:
				_localctx = new DivAssignmnetContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(612);
				assignable();
				setState(613);
				match(DIV_ASSIGN);
				setState(614);
				expr(0);
				}
				break;
			case 8:
				_localctx = new ModAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(616);
				assignable();
				setState(617);
				match(MOD_ASSIGN);
				setState(618);
				expr(0);
				}
				break;
			case 9:
				_localctx = new AndAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(620);
				assignable();
				setState(621);
				match(AND_ASSIGN);
				setState(622);
				expr(0);
				}
				break;
			case 10:
				_localctx = new OrAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(624);
				assignable();
				setState(625);
				match(OR_ASSIGN);
				setState(626);
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
		enterRule(_localctx, 42, RULE_var_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(630);
			declaration();
			setState(631);
			match(ASSIGN);
			setState(632);
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
		enterRule(_localctx, 44, RULE_var_def);
		try {
			setState(636);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				_localctx = new ImplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(634);
				declaration();
				}
				break;
			case 2:
				_localctx = new ExplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(635);
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
		enterRule(_localctx, 46, RULE_assignable);
		try {
			setState(649);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				_localctx = new SimpleAssignableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(638);
				ident();
				}
				break;
			case 2:
				_localctx = new ListAssignableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(639);
				ident();
				setState(640);
				match(LT);
				setState(641);
				expr(0);
				setState(642);
				match(GT);
				}
				break;
			case 3:
				_localctx = new ArrayAssignableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(644);
				ident();
				setState(645);
				match(LBRACKET);
				setState(646);
				expr(0);
				setState(647);
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
		enterRule(_localctx, 48, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(651);
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
		enterRule(_localctx, 50, RULE_literal);
		try {
			setState(659);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LIT:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(653);
				match(STRING_LIT);
				}
				break;
			case CHAR_LIT:
				_localctx = new CharLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(654);
				match(CHAR_LIT);
				}
				break;
			case COL_LIT:
				_localctx = new ColorLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(655);
				match(COL_LIT);
				}
				break;
			case DEC_LIT:
			case HEX_LIT:
				_localctx = new IntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(656);
				int_lit();
				}
				break;
			case FLOAT_LIT:
				_localctx = new FloatLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(657);
				match(FLOAT_LIT);
				}
				break;
			case TRUE:
			case FALSE:
				_localctx = new BoolLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(658);
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
		enterRule(_localctx, 52, RULE_int_lit);
		try {
			setState(663);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HEX_LIT:
				_localctx = new HexadecimalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(661);
				match(HEX_LIT);
				}
				break;
			case DEC_LIT:
				_localctx = new DecimalContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(662);
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
		enterRule(_localctx, 54, RULE_bool_lit);
		try {
			setState(667);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(665);
				match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(666);
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
		case 16:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 17);
		case 4:
			return precpred(_ctx, 16);
		case 5:
			return precpred(_ctx, 15);
		case 6:
			return precpred(_ctx, 14);
		case 7:
			return precpred(_ctx, 13);
		case 8:
			return precpred(_ctx, 12);
		case 9:
			return precpred(_ctx, 44);
		case 10:
			return precpred(_ctx, 43);
		case 11:
			return precpred(_ctx, 42);
		case 12:
			return precpred(_ctx, 41);
		case 13:
			return precpred(_ctx, 25);
		case 14:
			return precpred(_ctx, 24);
		case 15:
			return precpred(_ctx, 23);
		case 16:
			return precpred(_ctx, 20);
		case 17:
			return precpred(_ctx, 19);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001h\u029e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000<\b\u0000\n\u0000\f\u0000"+
		"?\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u0002H\b\u0002\u0001\u0003\u0001\u0003"+
		"\u0003\u0003L\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"Q\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"W\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\\\b\u0004\n"+
		"\u0004\f\u0004_\t\u0004\u0001\u0005\u0003\u0005b\b\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006u\b\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u0080\b\u0006\n\u0006\f\u0006"+
		"\u0083\t\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0088\b"+
		"\u0007\n\u0007\f\u0007\u008b\t\u0007\u0001\u0007\u0003\u0007\u008e\b\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u009f\b\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0003\b\u00ea\b\b\u0001\t\u0001\t\u0003\t\u00ee\b\t\u0001\t\u0001\t"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u0100\b\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0005\u000e\u011a\b\u000e\n\u000e\f\u000e\u011d\t\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000e\u0121\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0005\u0010\u01ac\b\u0010\n\u0010\f\u0010\u01af"+
		"\t\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0005\u0010\u01b7\b\u0010\n\u0010\f\u0010\u01ba\t\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010"+
		"\u01c2\b\u0010\n\u0010\f\u0010\u01c5\t\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0003\u0010\u01e3\b\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0005\u0010\u0230\b\u0010\n\u0010\f\u0010\u0233\t\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0005\u0011\u0238\b\u0011\n\u0011\f\u0011\u023b"+
		"\t\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u0246\b\u0013\n"+
		"\u0013\f\u0013\u0249\t\u0013\u0003\u0013\u024b\b\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u0275\b\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0003\u0016\u027d"+
		"\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0003"+
		"\u0017\u028a\b\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019\u0294\b\u0019\u0001"+
		"\u001a\u0001\u001a\u0003\u001a\u0298\b\u001a\u0001\u001b\u0001\u001b\u0003"+
		"\u001b\u029c\b\u001b\u0001\u001b\u0000\u0002\f \u001c\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.0246\u0000\u0007\u0002\u0000$$*+\u0001\u0000#$\u0001\u0000%\'\u0001"+
		"\u0000\u001c!\u0001\u0000()\u0001\u0000KN\u0001\u0000OP\u02ec\u00008\u0001"+
		"\u0000\u0000\u0000\u0002@\u0001\u0000\u0000\u0000\u0004G\u0001\u0000\u0000"+
		"\u0000\u0006V\u0001\u0000\u0000\u0000\bX\u0001\u0000\u0000\u0000\na\u0001"+
		"\u0000\u0000\u0000\ft\u0001\u0000\u0000\u0000\u000e\u008d\u0001\u0000"+
		"\u0000\u0000\u0010\u00e9\u0001\u0000\u0000\u0000\u0012\u00eb\u0001\u0000"+
		"\u0000\u0000\u0014\u00ff\u0001\u0000\u0000\u0000\u0016\u0101\u0001\u0000"+
		"\u0000\u0000\u0018\u0108\u0001\u0000\u0000\u0000\u001a\u010d\u0001\u0000"+
		"\u0000\u0000\u001c\u0116\u0001\u0000\u0000\u0000\u001e\u0122\u0001\u0000"+
		"\u0000\u0000 \u01e2\u0001\u0000\u0000\u0000\"\u0234\u0001\u0000\u0000"+
		"\u0000$\u023c\u0001\u0000\u0000\u0000&\u0240\u0001\u0000\u0000\u0000("+
		"\u0274\u0001\u0000\u0000\u0000*\u0276\u0001\u0000\u0000\u0000,\u027c\u0001"+
		"\u0000\u0000\u0000.\u0289\u0001\u0000\u0000\u00000\u028b\u0001\u0000\u0000"+
		"\u00002\u0293\u0001\u0000\u0000\u00004\u0297\u0001\u0000\u0000\u00006"+
		"\u029b\u0001\u0000\u0000\u000089\u0003\u0006\u0003\u00009=\u0003\u0004"+
		"\u0002\u0000:<\u0003\u0002\u0001\u0000;:\u0001\u0000\u0000\u0000<?\u0001"+
		"\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000"+
		">\u0001\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000@A\u00030\u0018"+
		"\u0000AB\u0003\u0006\u0003\u0000BC\u0003\u0004\u0002\u0000C\u0003\u0001"+
		"\u0000\u0000\u0000DH\u0003\u000e\u0007\u0000EF\u0005\u001a\u0000\u0000"+
		"FH\u0003 \u0010\u0000GD\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000"+
		"H\u0005\u0001\u0000\u0000\u0000IK\u0005\u0004\u0000\u0000JL\u0003\b\u0004"+
		"\u0000KJ\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LM\u0001\u0000"+
		"\u0000\u0000MW\u0005\u0005\u0000\u0000NP\u0005\u0004\u0000\u0000OQ\u0003"+
		"\b\u0004\u0000PO\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000QR\u0001"+
		"\u0000\u0000\u0000RS\u0005\u001a\u0000\u0000ST\u0003\f\u0006\u0000TU\u0005"+
		"\u0005\u0000\u0000UW\u0001\u0000\u0000\u0000VI\u0001\u0000\u0000\u0000"+
		"VN\u0001\u0000\u0000\u0000W\u0007\u0001\u0000\u0000\u0000X]\u0003\n\u0005"+
		"\u0000YZ\u0005\f\u0000\u0000Z\\\u0003\n\u0005\u0000[Y\u0001\u0000\u0000"+
		"\u0000\\_\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000]^\u0001\u0000"+
		"\u0000\u0000^\t\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000`b\u0005"+
		"-\u0000\u0000a`\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000bc\u0001"+
		"\u0000\u0000\u0000cd\u0003\f\u0006\u0000de\u00030\u0018\u0000e\u000b\u0001"+
		"\u0000\u0000\u0000fg\u0006\u0006\uffff\uffff\u0000gu\u0005.\u0000\u0000"+
		"hu\u00050\u0000\u0000iu\u0005/\u0000\u0000ju\u00051\u0000\u0000ku\u0005"+
		"4\u0000\u0000lu\u00053\u0000\u0000mu\u00052\u0000\u0000no\u0005\b\u0000"+
		"\u0000op\u0003\f\u0006\u0000pq\u0005\u000b\u0000\u0000qr\u0003\f\u0006"+
		"\u0000rs\u0005\t\u0000\u0000su\u0001\u0000\u0000\u0000tf\u0001\u0000\u0000"+
		"\u0000th\u0001\u0000\u0000\u0000ti\u0001\u0000\u0000\u0000tj\u0001\u0000"+
		"\u0000\u0000tk\u0001\u0000\u0000\u0000tl\u0001\u0000\u0000\u0000tm\u0001"+
		"\u0000\u0000\u0000tn\u0001\u0000\u0000\u0000u\u0081\u0001\u0000\u0000"+
		"\u0000vw\n\u0004\u0000\u0000wx\u0005\u0006\u0000\u0000x\u0080\u0005\u0007"+
		"\u0000\u0000yz\n\u0003\u0000\u0000z{\u0005\u001f\u0000\u0000{\u0080\u0005"+
		"\u001e\u0000\u0000|}\n\u0002\u0000\u0000}~\u0005\b\u0000\u0000~\u0080"+
		"\u0005\t\u0000\u0000\u007fv\u0001\u0000\u0000\u0000\u007fy\u0001\u0000"+
		"\u0000\u0000\u007f|\u0001\u0000\u0000\u0000\u0080\u0083\u0001\u0000\u0000"+
		"\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0081\u0082\u0001\u0000\u0000"+
		"\u0000\u0082\r\u0001\u0000\u0000\u0000\u0083\u0081\u0001\u0000\u0000\u0000"+
		"\u0084\u008e\u0003\u0010\b\u0000\u0085\u0089\u0005\b\u0000\u0000\u0086"+
		"\u0088\u0003\u0010\b\u0000\u0087\u0086\u0001\u0000\u0000\u0000\u0088\u008b"+
		"\u0001\u0000\u0000\u0000\u0089\u0087\u0001\u0000\u0000\u0000\u0089\u008a"+
		"\u0001\u0000\u0000\u0000\u008a\u008c\u0001\u0000\u0000\u0000\u008b\u0089"+
		"\u0001\u0000\u0000\u0000\u008c\u008e\u0005\t\u0000\u0000\u008d\u0084\u0001"+
		"\u0000\u0000\u0000\u008d\u0085\u0001\u0000\u0000\u0000\u008e\u000f\u0001"+
		"\u0000\u0000\u0000\u008f\u00ea\u0003\u0014\n\u0000\u0090\u00ea\u0003\u001c"+
		"\u000e\u0000\u0091\u0092\u0003,\u0016\u0000\u0092\u0093\u0005\n\u0000"+
		"\u0000\u0093\u00ea\u0001\u0000\u0000\u0000\u0094\u0095\u0003(\u0014\u0000"+
		"\u0095\u0096\u0005\n\u0000\u0000\u0096\u00ea\u0001\u0000\u0000\u0000\u0097"+
		"\u00ea\u0003\u0012\t\u0000\u0098\u0099\u0003 \u0010\u0000\u0099\u009a"+
		"\u0005U\u0000\u0000\u009a\u009b\u0005\u0004\u0000\u0000\u009b\u009e\u0003"+
		" \u0010\u0000\u009c\u009d\u0005\f\u0000\u0000\u009d\u009f\u0003 \u0010"+
		"\u0000\u009e\u009c\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000\u0000"+
		"\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005\u0005\u0000"+
		"\u0000\u00a1\u00a2\u0005\n\u0000\u0000\u00a2\u00ea\u0001\u0000\u0000\u0000"+
		"\u00a3\u00a4\u0003 \u0010\u0000\u00a4\u00a5\u0005V\u0000\u0000\u00a5\u00a6"+
		"\u0005\u0004\u0000\u0000\u00a6\u00a7\u0003 \u0010\u0000\u00a7\u00a8\u0005"+
		"\u0005\u0000\u0000\u00a8\u00a9\u0005\n\u0000\u0000\u00a9\u00ea\u0001\u0000"+
		"\u0000\u0000\u00aa\u00ab\u0003 \u0010\u0000\u00ab\u00ac\u0005W\u0000\u0000"+
		"\u00ac\u00ad\u0005\u0004\u0000\u0000\u00ad\u00ae\u0003 \u0010\u0000\u00ae"+
		"\u00af\u0005\f\u0000\u0000\u00af\u00b0\u0003 \u0010\u0000\u00b0\u00b1"+
		"\u0005\u0005\u0000\u0000\u00b1\u00b2\u0005\n\u0000\u0000\u00b2\u00ea\u0001"+
		"\u0000\u0000\u0000\u00b3\u00b4\u0003 \u0010\u0000\u00b4\u00b5\u0005X\u0000"+
		"\u0000\u00b5\u00b6\u0005\u0004\u0000\u0000\u00b6\u00b7\u0003 \u0010\u0000"+
		"\u00b7\u00b8\u0005\f\u0000\u0000\u00b8\u00b9\u0003 \u0010\u0000\u00b9"+
		"\u00ba\u0005\f\u0000\u0000\u00ba\u00bb\u0003 \u0010\u0000\u00bb\u00bc"+
		"\u0005\u0005\u0000\u0000\u00bc\u00bd\u0005\n\u0000\u0000\u00bd\u00ea\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0003 \u0010\u0000\u00bf\u00c0\u0005[\u0000"+
		"\u0000\u00c0\u00c1\u0005\u0004\u0000\u0000\u00c1\u00c2\u0003 \u0010\u0000"+
		"\u00c2\u00c3\u0005\f\u0000\u0000\u00c3\u00c4\u0003 \u0010\u0000\u00c4"+
		"\u00c5\u0005\f\u0000\u0000\u00c5\u00c6\u0003 \u0010\u0000\u00c6\u00c7"+
		"\u0005\u0005\u0000\u0000\u00c7\u00c8\u0005\n\u0000\u0000\u00c8\u00ea\u0001"+
		"\u0000\u0000\u0000\u00c9\u00ca\u0003 \u0010\u0000\u00ca\u00cb\u0005\\"+
		"\u0000\u0000\u00cb\u00cc\u0005\u0004\u0000\u0000\u00cc\u00cd\u0003 \u0010"+
		"\u0000\u00cd\u00ce\u0005\f\u0000\u0000\u00ce\u00cf\u0003 \u0010\u0000"+
		"\u00cf\u00d0\u0005\f\u0000\u0000\u00d0\u00d1\u0003 \u0010\u0000\u00d1"+
		"\u00d2\u0005\f\u0000\u0000\u00d2\u00d3\u0003 \u0010\u0000\u00d3\u00d4"+
		"\u0005\f\u0000\u0000\u00d4\u00d5\u0003 \u0010\u0000\u00d5\u00d6\u0005"+
		"\f\u0000\u0000\u00d6\u00d7\u0003 \u0010\u0000\u00d7\u00d8\u0005\u0005"+
		"\u0000\u0000\u00d8\u00d9\u0005\n\u0000\u0000\u00d9\u00ea\u0001\u0000\u0000"+
		"\u0000\u00da\u00db\u0003 \u0010\u0000\u00db\u00dc\u0005]\u0000\u0000\u00dc"+
		"\u00dd\u0005\u0004\u0000\u0000\u00dd\u00de\u0003 \u0010\u0000\u00de\u00df"+
		"\u0005\f\u0000\u0000\u00df\u00e0\u0003 \u0010\u0000\u00e0\u00e1\u0005"+
		"\f\u0000\u0000\u00e1\u00e2\u0003 \u0010\u0000\u00e2\u00e3\u0005\f\u0000"+
		"\u0000\u00e3\u00e4\u0003 \u0010\u0000\u00e4\u00e5\u0005\f\u0000\u0000"+
		"\u00e5\u00e6\u0003 \u0010\u0000\u00e6\u00e7\u0005\u0005\u0000\u0000\u00e7"+
		"\u00e8\u0005\n\u0000\u0000\u00e8\u00ea\u0001\u0000\u0000\u0000\u00e9\u008f"+
		"\u0001\u0000\u0000\u0000\u00e9\u0090\u0001\u0000\u0000\u0000\u00e9\u0091"+
		"\u0001\u0000\u0000\u0000\u00e9\u0094\u0001\u0000\u0000\u0000\u00e9\u0097"+
		"\u0001\u0000\u0000\u0000\u00e9\u0098\u0001\u0000\u0000\u0000\u00e9\u00a3"+
		"\u0001\u0000\u0000\u0000\u00e9\u00aa\u0001\u0000\u0000\u0000\u00e9\u00b3"+
		"\u0001\u0000\u0000\u0000\u00e9\u00be\u0001\u0000\u0000\u0000\u00e9\u00c9"+
		"\u0001\u0000\u0000\u0000\u00e9\u00da\u0001\u0000\u0000\u0000\u00ea\u0011"+
		"\u0001\u0000\u0000\u0000\u00eb\u00ed\u00055\u0000\u0000\u00ec\u00ee\u0003"+
		" \u0010\u0000\u00ed\u00ec\u0001\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000"+
		"\u0000\u0000\u00ee\u00ef\u0001\u0000\u0000\u0000\u00ef\u00f0\u0005\n\u0000"+
		"\u0000\u00f0\u0013\u0001\u0000\u0000\u0000\u00f1\u00f2\u0003\u0018\f\u0000"+
		"\u00f2\u00f3\u0003\u000e\u0007\u0000\u00f3\u0100\u0001\u0000\u0000\u0000"+
		"\u00f4\u00f5\u0003\u0016\u000b\u0000\u00f5\u00f6\u0003\u000e\u0007\u0000"+
		"\u00f6\u0100\u0001\u0000\u0000\u0000\u00f7\u00f8\u0003\u001a\r\u0000\u00f8"+
		"\u00f9\u0003\u000e\u0007\u0000\u00f9\u0100\u0001\u0000\u0000\u0000\u00fa"+
		"\u00fb\u00056\u0000\u0000\u00fb\u00fc\u0003\u000e\u0007\u0000\u00fc\u00fd"+
		"\u0003\u0018\f\u0000\u00fd\u00fe\u0005\n\u0000\u0000\u00fe\u0100\u0001"+
		"\u0000\u0000\u0000\u00ff\u00f1\u0001\u0000\u0000\u0000\u00ff\u00f4\u0001"+
		"\u0000\u0000\u0000\u00ff\u00f7\u0001\u0000\u0000\u0000\u00ff\u00fa\u0001"+
		"\u0000\u0000\u0000\u0100\u0015\u0001\u0000\u0000\u0000\u0101\u0102\u0005"+
		"8\u0000\u0000\u0102\u0103\u0005\u0004\u0000\u0000\u0103\u0104\u0003\n"+
		"\u0005\u0000\u0104\u0105\u0005,\u0000\u0000\u0105\u0106\u0003 \u0010\u0000"+
		"\u0106\u0107\u0005\u0005\u0000\u0000\u0107\u0017\u0001\u0000\u0000\u0000"+
		"\u0108\u0109\u00057\u0000\u0000\u0109\u010a\u0005\u0004\u0000\u0000\u010a"+
		"\u010b\u0003 \u0010\u0000\u010b\u010c\u0005\u0005\u0000\u0000\u010c\u0019"+
		"\u0001\u0000\u0000\u0000\u010d\u010e\u00058\u0000\u0000\u010e\u010f\u0005"+
		"\u0004\u0000\u0000\u010f\u0110\u0003*\u0015\u0000\u0110\u0111\u0005\n"+
		"\u0000\u0000\u0111\u0112\u0003 \u0010\u0000\u0112\u0113\u0005\n\u0000"+
		"\u0000\u0113\u0114\u0003(\u0014\u0000\u0114\u0115\u0005\u0005\u0000\u0000"+
		"\u0115\u001b\u0001\u0000\u0000\u0000\u0116\u011b\u0003\u001e\u000f\u0000"+
		"\u0117\u0118\u0005:\u0000\u0000\u0118\u011a\u0003\u001e\u000f\u0000\u0119"+
		"\u0117\u0001\u0000\u0000\u0000\u011a\u011d\u0001\u0000\u0000\u0000\u011b"+
		"\u0119\u0001\u0000\u0000\u0000\u011b\u011c\u0001\u0000\u0000\u0000\u011c"+
		"\u0120\u0001\u0000\u0000\u0000\u011d\u011b\u0001\u0000\u0000\u0000\u011e"+
		"\u011f\u0005:\u0000\u0000\u011f\u0121\u0003\u000e\u0007\u0000\u0120\u011e"+
		"\u0001\u0000\u0000\u0000\u0120\u0121\u0001\u0000\u0000\u0000\u0121\u001d"+
		"\u0001\u0000\u0000\u0000\u0122\u0123\u00059\u0000\u0000\u0123\u0124\u0005"+
		"\u0004\u0000\u0000\u0124\u0125\u0003 \u0010\u0000\u0125\u0126\u0005\u0005"+
		"\u0000\u0000\u0126\u0127\u0003\u000e\u0007\u0000\u0127\u001f\u0001\u0000"+
		"\u0000\u0000\u0128\u0129\u0006\u0010\uffff\uffff\u0000\u0129\u012a\u0005"+
		"\u0004\u0000\u0000\u012a\u012b\u0003 \u0010\u0000\u012b\u012c\u0005\u0005"+
		"\u0000\u0000\u012c\u01e3\u0001\u0000\u0000\u0000\u012d\u012e\u0005\u0004"+
		"\u0000\u0000\u012e\u012f\u0003\f\u0006\u0000\u012f\u0130\u0005\u0005\u0000"+
		"\u0000\u0130\u0131\u0003 \u0010-\u0131\u01e3\u0001\u0000\u0000\u0000\u0132"+
		"\u0133\u0005D\u0000\u0000\u0133\u0134\u0005\u0004\u0000\u0000\u0134\u0135"+
		"\u0003 \u0010\u0000\u0135\u0136\u0005\u0005\u0000\u0000\u0136\u01e3\u0001"+
		"\u0000\u0000\u0000\u0137\u0138\u0005E\u0000\u0000\u0138\u0139\u0005\u0004"+
		"\u0000\u0000\u0139\u013a\u0003 \u0010\u0000\u013a\u013b\u0005\u0005\u0000"+
		"\u0000\u013b\u01e3\u0001\u0000\u0000\u0000\u013c\u013d\u0005E\u0000\u0000"+
		"\u013d\u013e\u0005\u0004\u0000\u0000\u013e\u013f\u0003 \u0010\u0000\u013f"+
		"\u0140\u0005\f\u0000\u0000\u0140\u0141\u0003 \u0010\u0000\u0141\u0142"+
		"\u0005\u0005\u0000\u0000\u0142\u01e3\u0001\u0000\u0000\u0000\u0143\u0144"+
		"\u0005F\u0000\u0000\u0144\u0145\u0005\u0004\u0000\u0000\u0145\u0146\u0003"+
		" \u0010\u0000\u0146\u0147\u0005\u0005\u0000\u0000\u0147\u01e3\u0001\u0000"+
		"\u0000\u0000\u0148\u0149\u0005F\u0000\u0000\u0149\u014a\u0005\u0004\u0000"+
		"\u0000\u014a\u014b\u0003 \u0010\u0000\u014b\u014c\u0005\f\u0000\u0000"+
		"\u014c\u014d\u0003 \u0010\u0000\u014d\u014e\u0005\u0005\u0000\u0000\u014e"+
		"\u01e3\u0001\u0000\u0000\u0000\u014f\u0150\u0005G\u0000\u0000\u0150\u0151"+
		"\u0005\u0004\u0000\u0000\u0151\u0152\u0003 \u0010\u0000\u0152\u0153\u0005"+
		"\f\u0000\u0000\u0153\u0154\u0003 \u0010\u0000\u0154\u0155\u0005\f\u0000"+
		"\u0000\u0155\u0156\u0003 \u0010\u0000\u0156\u0157\u0005\u0005\u0000\u0000"+
		"\u0157\u01e3\u0001\u0000\u0000\u0000\u0158\u0159\u0005H\u0000\u0000\u0159"+
		"\u015a\u0005\u0004\u0000\u0000\u015a\u01e3\u0005\u0005\u0000\u0000\u015b"+
		"\u015c\u0005H\u0000\u0000\u015c\u015d\u0005\u0004\u0000\u0000\u015d\u015e"+
		"\u0003 \u0010\u0000\u015e\u015f\u0005\f\u0000\u0000\u015f\u0160\u0003"+
		" \u0010\u0000\u0160\u0161\u0005\u0005\u0000\u0000\u0161\u01e3\u0001\u0000"+
		"\u0000\u0000\u0162\u0163\u0005I\u0000\u0000\u0163\u0164\u0005\u0004\u0000"+
		"\u0000\u0164\u0165\u0003 \u0010\u0000\u0165\u0166\u0005\u0005\u0000\u0000"+
		"\u0166\u01e3\u0001\u0000\u0000\u0000\u0167\u0168\u0005J\u0000\u0000\u0168"+
		"\u0169\u0005\u0004\u0000\u0000\u0169\u01e3\u0005\u0005\u0000\u0000\u016a"+
		"\u016b\u0005J\u0000\u0000\u016b\u016c\u0005\u0004\u0000\u0000\u016c\u016d"+
		"\u0003 \u0010\u0000\u016d\u016e\u0005\f\u0000\u0000\u016e\u016f\u0003"+
		" \u0010\u0000\u016f\u0170\u0005\u0005\u0000\u0000\u0170\u01e3\u0001\u0000"+
		"\u0000\u0000\u0171\u0172\u0005>\u0000\u0000\u0172\u0173\u0005\u0004\u0000"+
		"\u0000\u0173\u0174\u0003 \u0010\u0000\u0174\u0175\u0005\u0005\u0000\u0000"+
		"\u0175\u01e3\u0001\u0000\u0000\u0000\u0176\u0177\u0005A\u0000\u0000\u0177"+
		"\u0178\u0005\u0004\u0000\u0000\u0178\u0179\u0003 \u0010\u0000\u0179\u017a"+
		"\u0005\f\u0000\u0000\u017a\u017b\u0003 \u0010\u0000\u017b\u017c\u0005"+
		"\u0005\u0000\u0000\u017c\u01e3\u0001\u0000\u0000\u0000\u017d\u017e\u0005"+
		"B\u0000\u0000\u017e\u017f\u0005\u0004\u0000\u0000\u017f\u0180\u0003 \u0010"+
		"\u0000\u0180\u0181\u0005\f\u0000\u0000\u0181\u0182\u0003 \u0010\u0000"+
		"\u0182\u0183\u0005\f\u0000\u0000\u0183\u0184\u0003 \u0010\u0000\u0184"+
		"\u0185\u0005\u0005\u0000\u0000\u0185\u01e3\u0001\u0000\u0000\u0000\u0186"+
		"\u0187\u0005C\u0000\u0000\u0187\u0188\u0005\u0004\u0000\u0000\u0188\u0189"+
		"\u0003 \u0010\u0000\u0189\u018a\u0005\f\u0000\u0000\u018a\u018b\u0003"+
		" \u0010\u0000\u018b\u018c\u0005\u0005\u0000\u0000\u018c\u01e3\u0001\u0000"+
		"\u0000\u0000\u018d\u018e\u0005@\u0000\u0000\u018e\u018f\u0005\u0004\u0000"+
		"\u0000\u018f\u0190\u0003 \u0010\u0000\u0190\u0191\u0005\f\u0000\u0000"+
		"\u0191\u0192\u0003 \u0010\u0000\u0192\u0193\u0005\f\u0000\u0000\u0193"+
		"\u0194\u0003 \u0010\u0000\u0194\u0195\u0005\u0005\u0000\u0000\u0195\u01e3"+
		"\u0001\u0000\u0000\u0000\u0196\u0197\u0005?\u0000\u0000\u0197\u0198\u0005"+
		"\u0004\u0000\u0000\u0198\u0199\u0003 \u0010\u0000\u0199\u019a\u0005\f"+
		"\u0000\u0000\u019a\u019b\u0003 \u0010\u0000\u019b\u019c\u0005\f\u0000"+
		"\u0000\u019c\u019d\u0003 \u0010\u0000\u019d\u019e\u0005\f\u0000\u0000"+
		"\u019e\u019f\u0003 \u0010\u0000\u019f\u01a0\u0005\u0005\u0000\u0000\u01a0"+
		"\u01e3\u0001\u0000\u0000\u0000\u01a1\u01a2\u0007\u0000\u0000\u0000\u01a2"+
		"\u01e3\u0003 \u0010\u0012\u01a3\u01a4\u0005\b\u0000\u0000\u01a4\u01a5"+
		"\u0003\"\u0011\u0000\u01a5\u01a6\u0005\t\u0000\u0000\u01a6\u01e3\u0001"+
		"\u0000\u0000\u0000\u01a7\u01a8\u0005\u0006\u0000\u0000\u01a8\u01ad\u0003"+
		" \u0010\u0000\u01a9\u01aa\u0005\f\u0000\u0000\u01aa\u01ac\u0003 \u0010"+
		"\u0000\u01ab\u01a9\u0001\u0000\u0000\u0000\u01ac\u01af\u0001\u0000\u0000"+
		"\u0000\u01ad\u01ab\u0001\u0000\u0000\u0000\u01ad\u01ae\u0001\u0000\u0000"+
		"\u0000\u01ae\u01b0\u0001\u0000\u0000\u0000\u01af\u01ad\u0001\u0000\u0000"+
		"\u0000\u01b0\u01b1\u0005\u0007\u0000\u0000\u01b1\u01e3\u0001\u0000\u0000"+
		"\u0000\u01b2\u01b3\u0005\u001f\u0000\u0000\u01b3\u01b8\u0003 \u0010\u0000"+
		"\u01b4\u01b5\u0005\f\u0000\u0000\u01b5\u01b7\u0003 \u0010\u0000\u01b6"+
		"\u01b4\u0001\u0000\u0000\u0000\u01b7\u01ba\u0001\u0000\u0000\u0000\u01b8"+
		"\u01b6\u0001\u0000\u0000\u0000\u01b8\u01b9\u0001\u0000\u0000\u0000\u01b9"+
		"\u01bb\u0001\u0000\u0000\u0000\u01ba\u01b8\u0001\u0000\u0000\u0000\u01bb"+
		"\u01bc\u0005\u001e\u0000\u0000\u01bc\u01e3\u0001\u0000\u0000\u0000\u01bd"+
		"\u01be\u0005\b\u0000\u0000\u01be\u01c3\u0003 \u0010\u0000\u01bf\u01c0"+
		"\u0005\f\u0000\u0000\u01c0\u01c2\u0003 \u0010\u0000\u01c1\u01bf\u0001"+
		"\u0000\u0000\u0000\u01c2\u01c5\u0001\u0000\u0000\u0000\u01c3\u01c1\u0001"+
		"\u0000\u0000\u0000\u01c3\u01c4\u0001\u0000\u0000\u0000\u01c4\u01c6\u0001"+
		"\u0000\u0000\u0000\u01c5\u01c3\u0001\u0000\u0000\u0000\u01c6\u01c7\u0005"+
		"\t\u0000\u0000\u01c7\u01e3\u0001\u0000\u0000\u0000\u01c8\u01c9\u0005="+
		"\u0000\u0000\u01c9\u01ca\u0003\f\u0006\u0000\u01ca\u01cb\u0005\u0006\u0000"+
		"\u0000\u01cb\u01cc\u0003 \u0010\u0000\u01cc\u01cd\u0005\u0007\u0000\u0000"+
		"\u01cd\u01e3\u0001\u0000\u0000\u0000\u01ce\u01cf\u0005=\u0000\u0000\u01cf"+
		"\u01d0\u0003\f\u0006\u0000\u01d0\u01d1\u0005\u001f\u0000\u0000\u01d1\u01d2"+
		"\u0005\u001e\u0000\u0000\u01d2\u01e3\u0001\u0000\u0000\u0000\u01d3\u01d4"+
		"\u0005=\u0000\u0000\u01d4\u01d5\u0003\f\u0006\u0000\u01d5\u01d6\u0005"+
		"\b\u0000\u0000\u01d6\u01d7\u0005\t\u0000\u0000\u01d7\u01e3\u0001\u0000"+
		"\u0000\u0000\u01d8\u01d9\u0005=\u0000\u0000\u01d9\u01da\u0005\b\u0000"+
		"\u0000\u01da\u01db\u0003\f\u0006\u0000\u01db\u01dc\u0005\u000b\u0000\u0000"+
		"\u01dc\u01dd\u0003\f\u0006\u0000\u01dd\u01de\u0005\t\u0000\u0000\u01de"+
		"\u01e3\u0001\u0000\u0000\u0000\u01df\u01e3\u0003&\u0013\u0000\u01e0\u01e3"+
		"\u0003.\u0017\u0000\u01e1\u01e3\u00032\u0019\u0000\u01e2\u0128\u0001\u0000"+
		"\u0000\u0000\u01e2\u012d\u0001\u0000\u0000\u0000\u01e2\u0132\u0001\u0000"+
		"\u0000\u0000\u01e2\u0137\u0001\u0000\u0000\u0000\u01e2\u013c\u0001\u0000"+
		"\u0000\u0000\u01e2\u0143\u0001\u0000\u0000\u0000\u01e2\u0148\u0001\u0000"+
		"\u0000\u0000\u01e2\u014f\u0001\u0000\u0000\u0000\u01e2\u0158\u0001\u0000"+
		"\u0000\u0000\u01e2\u015b\u0001\u0000\u0000\u0000\u01e2\u0162\u0001\u0000"+
		"\u0000\u0000\u01e2\u0167\u0001\u0000\u0000\u0000\u01e2\u016a\u0001\u0000"+
		"\u0000\u0000\u01e2\u0171\u0001\u0000\u0000\u0000\u01e2\u0176\u0001\u0000"+
		"\u0000\u0000\u01e2\u017d\u0001\u0000\u0000\u0000\u01e2\u0186\u0001\u0000"+
		"\u0000\u0000\u01e2\u018d\u0001\u0000\u0000\u0000\u01e2\u0196\u0001\u0000"+
		"\u0000\u0000\u01e2\u01a1\u0001\u0000\u0000\u0000\u01e2\u01a3\u0001\u0000"+
		"\u0000\u0000\u01e2\u01a7\u0001\u0000\u0000\u0000\u01e2\u01b2\u0001\u0000"+
		"\u0000\u0000\u01e2\u01bd\u0001\u0000\u0000\u0000\u01e2\u01c8\u0001\u0000"+
		"\u0000\u0000\u01e2\u01ce\u0001\u0000\u0000\u0000\u01e2\u01d3\u0001\u0000"+
		"\u0000\u0000\u01e2\u01d8\u0001\u0000\u0000\u0000\u01e2\u01df\u0001\u0000"+
		"\u0000\u0000\u01e2\u01e0\u0001\u0000\u0000\u0000\u01e2\u01e1\u0001\u0000"+
		"\u0000\u0000\u01e3\u0231\u0001\u0000\u0000\u0000\u01e4\u01e5\n\u0011\u0000"+
		"\u0000\u01e5\u01e6\u0007\u0001\u0000\u0000\u01e6\u0230\u0003 \u0010\u0012"+
		"\u01e7\u01e8\n\u0010\u0000\u0000\u01e8\u01e9\u0007\u0002\u0000\u0000\u01e9"+
		"\u0230\u0003 \u0010\u0011\u01ea\u01eb\n\u000f\u0000\u0000\u01eb\u01ec"+
		"\u0005\"\u0000\u0000\u01ec\u0230\u0003 \u0010\u0010\u01ed\u01ee\n\u000e"+
		"\u0000\u0000\u01ee\u01ef\u0007\u0003\u0000\u0000\u01ef\u0230\u0003 \u0010"+
		"\u000f\u01f0\u01f1\n\r\u0000\u0000\u01f1\u01f2\u0007\u0004\u0000\u0000"+
		"\u01f2\u0230\u0003 \u0010\u000e\u01f3\u01f4\n\f\u0000\u0000\u01f4\u01f5"+
		"\u0005\u000f\u0000\u0000\u01f5\u01f6\u0003 \u0010\u0000\u01f6\u01f7\u0005"+
		"\u000b\u0000\u0000\u01f7\u01f8\u0003 \u0010\r\u01f8\u0230\u0001\u0000"+
		"\u0000\u0000\u01f9\u01fa\n,\u0000\u0000\u01fa\u01fb\u0005Q\u0000\u0000"+
		"\u01fb\u01fc\u0005\u0004\u0000\u0000\u01fc\u01fd\u0003 \u0010\u0000\u01fd"+
		"\u01fe\u0005\u0005\u0000\u0000\u01fe\u0230\u0001\u0000\u0000\u0000\u01ff"+
		"\u0200\n+\u0000\u0000\u0200\u0201\u0005R\u0000\u0000\u0201\u0202\u0005"+
		"\u0004\u0000\u0000\u0202\u0203\u0003 \u0010\u0000\u0203\u0204\u0005\u0005"+
		"\u0000\u0000\u0204\u0230\u0001\u0000\u0000\u0000\u0205\u0206\n*\u0000"+
		"\u0000\u0206\u0207\u0005S\u0000\u0000\u0207\u0208\u0005\u0004\u0000\u0000"+
		"\u0208\u0230\u0005\u0005\u0000\u0000\u0209\u020a\n)\u0000\u0000\u020a"+
		"\u0230\u0007\u0005\u0000\u0000\u020b\u020c\n\u0019\u0000\u0000\u020c\u020d"+
		"\u0005^\u0000\u0000\u020d\u020e\u0005\u0004\u0000\u0000\u020e\u020f\u0003"+
		" \u0010\u0000\u020f\u0210\u0005\f\u0000\u0000\u0210\u0211\u0003 \u0010"+
		"\u0000\u0211\u0212\u0005\f\u0000\u0000\u0212\u0213\u0003 \u0010\u0000"+
		"\u0213\u0214\u0005\f\u0000\u0000\u0214\u0215\u0003 \u0010\u0000\u0215"+
		"\u0216\u0005\u0005\u0000\u0000\u0216\u0230\u0001\u0000\u0000\u0000\u0217"+
		"\u0218\n\u0018\u0000\u0000\u0218\u0219\u0005T\u0000\u0000\u0219\u021a"+
		"\u0005\u0004\u0000\u0000\u021a\u021b\u0003 \u0010\u0000\u021b\u021c\u0005"+
		"\f\u0000\u0000\u021c\u021d\u0003 \u0010\u0000\u021d\u021e\u0005\u0005"+
		"\u0000\u0000\u021e\u0230\u0001\u0000\u0000\u0000\u021f\u0220\n\u0017\u0000"+
		"\u0000\u0220\u0230\u0007\u0006\u0000\u0000\u0221\u0222\n\u0014\u0000\u0000"+
		"\u0222\u0223\u0005Y\u0000\u0000\u0223\u0224\u0005\u0004\u0000\u0000\u0224"+
		"\u0225\u0003 \u0010\u0000\u0225\u0226\u0005\u0005\u0000\u0000\u0226\u0230"+
		"\u0001\u0000\u0000\u0000\u0227\u0228\n\u0013\u0000\u0000\u0228\u0229\u0005"+
		"Z\u0000\u0000\u0229\u022a\u0005\u0004\u0000\u0000\u022a\u022b\u0003 \u0010"+
		"\u0000\u022b\u022c\u0005\f\u0000\u0000\u022c\u022d\u0003 \u0010\u0000"+
		"\u022d\u022e\u0005\u0005\u0000\u0000\u022e\u0230\u0001\u0000\u0000\u0000"+
		"\u022f\u01e4\u0001\u0000\u0000\u0000\u022f\u01e7\u0001\u0000\u0000\u0000"+
		"\u022f\u01ea\u0001\u0000\u0000\u0000\u022f\u01ed\u0001\u0000\u0000\u0000"+
		"\u022f\u01f0\u0001\u0000\u0000\u0000\u022f\u01f3\u0001\u0000\u0000\u0000"+
		"\u022f\u01f9\u0001\u0000\u0000\u0000\u022f\u01ff\u0001\u0000\u0000\u0000"+
		"\u022f\u0205\u0001\u0000\u0000\u0000\u022f\u0209\u0001\u0000\u0000\u0000"+
		"\u022f\u020b\u0001\u0000\u0000\u0000\u022f\u0217\u0001\u0000\u0000\u0000"+
		"\u022f\u021f\u0001\u0000\u0000\u0000\u022f\u0221\u0001\u0000\u0000\u0000"+
		"\u022f\u0227\u0001\u0000\u0000\u0000\u0230\u0233\u0001\u0000\u0000\u0000"+
		"\u0231\u022f\u0001\u0000\u0000\u0000\u0231\u0232\u0001\u0000\u0000\u0000"+
		"\u0232!\u0001\u0000\u0000\u0000\u0233\u0231\u0001\u0000\u0000\u0000\u0234"+
		"\u0239\u0003$\u0012\u0000\u0235\u0236\u0005\f\u0000\u0000\u0236\u0238"+
		"\u0003$\u0012\u0000\u0237\u0235\u0001\u0000\u0000\u0000\u0238\u023b\u0001"+
		"\u0000\u0000\u0000\u0239\u0237\u0001\u0000\u0000\u0000\u0239\u023a\u0001"+
		"\u0000\u0000\u0000\u023a#\u0001\u0000\u0000\u0000\u023b\u0239\u0001\u0000"+
		"\u0000\u0000\u023c\u023d\u0003 \u0010\u0000\u023d\u023e\u0005\u000b\u0000"+
		"\u0000\u023e\u023f\u0003 \u0010\u0000\u023f%\u0001\u0000\u0000\u0000\u0240"+
		"\u0241\u00030\u0018\u0000\u0241\u024a\u0005\u0004\u0000\u0000\u0242\u0247"+
		"\u0003 \u0010\u0000\u0243\u0244\u0005\f\u0000\u0000\u0244\u0246\u0003"+
		" \u0010\u0000\u0245\u0243\u0001\u0000\u0000\u0000\u0246\u0249\u0001\u0000"+
		"\u0000\u0000\u0247\u0245\u0001\u0000\u0000\u0000\u0247\u0248\u0001\u0000"+
		"\u0000\u0000\u0248\u024b\u0001\u0000\u0000\u0000\u0249\u0247\u0001\u0000"+
		"\u0000\u0000\u024a\u0242\u0001\u0000\u0000\u0000\u024a\u024b\u0001\u0000"+
		"\u0000\u0000\u024b\u024c\u0001\u0000\u0000\u0000\u024c\u024d\u0005\u0005"+
		"\u0000\u0000\u024d\'\u0001\u0000\u0000\u0000\u024e\u024f\u0003.\u0017"+
		"\u0000\u024f\u0250\u0005\u0010\u0000\u0000\u0250\u0251\u0003 \u0010\u0000"+
		"\u0251\u0275\u0001\u0000\u0000\u0000\u0252\u0253\u0003.\u0017\u0000\u0253"+
		"\u0254\u0005\u0011\u0000\u0000\u0254\u0275\u0001\u0000\u0000\u0000\u0255"+
		"\u0256\u0003.\u0017\u0000\u0256\u0257\u0005\u0012\u0000\u0000\u0257\u0275"+
		"\u0001\u0000\u0000\u0000\u0258\u0259\u0003.\u0017\u0000\u0259\u025a\u0005"+
		"\u0013\u0000\u0000\u025a\u025b\u0003 \u0010\u0000\u025b\u0275\u0001\u0000"+
		"\u0000\u0000\u025c\u025d\u0003.\u0017\u0000\u025d\u025e\u0005\u0014\u0000"+
		"\u0000\u025e\u025f\u0003 \u0010\u0000\u025f\u0275\u0001\u0000\u0000\u0000"+
		"\u0260\u0261\u0003.\u0017\u0000\u0261\u0262\u0005\u0015\u0000\u0000\u0262"+
		"\u0263\u0003 \u0010\u0000\u0263\u0275\u0001\u0000\u0000\u0000\u0264\u0265"+
		"\u0003.\u0017\u0000\u0265\u0266\u0005\u0016\u0000\u0000\u0266\u0267\u0003"+
		" \u0010\u0000\u0267\u0275\u0001\u0000\u0000\u0000\u0268\u0269\u0003.\u0017"+
		"\u0000\u0269\u026a\u0005\u0017\u0000\u0000\u026a\u026b\u0003 \u0010\u0000"+
		"\u026b\u0275\u0001\u0000\u0000\u0000\u026c\u026d\u0003.\u0017\u0000\u026d"+
		"\u026e\u0005\u0018\u0000\u0000\u026e\u026f\u0003 \u0010\u0000\u026f\u0275"+
		"\u0001\u0000\u0000\u0000\u0270\u0271\u0003.\u0017\u0000\u0271\u0272\u0005"+
		"\u0019\u0000\u0000\u0272\u0273\u0003 \u0010\u0000\u0273\u0275\u0001\u0000"+
		"\u0000\u0000\u0274\u024e\u0001\u0000\u0000\u0000\u0274\u0252\u0001\u0000"+
		"\u0000\u0000\u0274\u0255\u0001\u0000\u0000\u0000\u0274\u0258\u0001\u0000"+
		"\u0000\u0000\u0274\u025c\u0001\u0000\u0000\u0000\u0274\u0260\u0001\u0000"+
		"\u0000\u0000\u0274\u0264\u0001\u0000\u0000\u0000\u0274\u0268\u0001\u0000"+
		"\u0000\u0000\u0274\u026c\u0001\u0000\u0000\u0000\u0274\u0270\u0001\u0000"+
		"\u0000\u0000\u0275)\u0001\u0000\u0000\u0000\u0276\u0277\u0003\n\u0005"+
		"\u0000\u0277\u0278\u0005\u0010\u0000\u0000\u0278\u0279\u0003 \u0010\u0000"+
		"\u0279+\u0001\u0000\u0000\u0000\u027a\u027d\u0003\n\u0005\u0000\u027b"+
		"\u027d\u0003*\u0015\u0000\u027c\u027a\u0001\u0000\u0000\u0000\u027c\u027b"+
		"\u0001\u0000\u0000\u0000\u027d-\u0001\u0000\u0000\u0000\u027e\u028a\u0003"+
		"0\u0018\u0000\u027f\u0280\u00030\u0018\u0000\u0280\u0281\u0005\u001f\u0000"+
		"\u0000\u0281\u0282\u0003 \u0010\u0000\u0282\u0283\u0005\u001e\u0000\u0000"+
		"\u0283\u028a\u0001\u0000\u0000\u0000\u0284\u0285\u00030\u0018\u0000\u0285"+
		"\u0286\u0005\u0006\u0000\u0000\u0286\u0287\u0003 \u0010\u0000\u0287\u0288"+
		"\u0005\u0007\u0000\u0000\u0288\u028a\u0001\u0000\u0000\u0000\u0289\u027e"+
		"\u0001\u0000\u0000\u0000\u0289\u027f\u0001\u0000\u0000\u0000\u0289\u0284"+
		"\u0001\u0000\u0000\u0000\u028a/\u0001\u0000\u0000\u0000\u028b\u028c\u0005"+
		"h\u0000\u0000\u028c1\u0001\u0000\u0000\u0000\u028d\u0294\u0005e\u0000"+
		"\u0000\u028e\u0294\u0005f\u0000\u0000\u028f\u0294\u0005b\u0000\u0000\u0290"+
		"\u0294\u00034\u001a\u0000\u0291\u0294\u0005_\u0000\u0000\u0292\u0294\u0003"+
		"6\u001b\u0000\u0293\u028d\u0001\u0000\u0000\u0000\u0293\u028e\u0001\u0000"+
		"\u0000\u0000\u0293\u028f\u0001\u0000\u0000\u0000\u0293\u0290\u0001\u0000"+
		"\u0000\u0000\u0293\u0291\u0001\u0000\u0000\u0000\u0293\u0292\u0001\u0000"+
		"\u0000\u0000\u02943\u0001\u0000\u0000\u0000\u0295\u0298\u0005a\u0000\u0000"+
		"\u0296\u0298\u0005`\u0000\u0000\u0297\u0295\u0001\u0000\u0000\u0000\u0297"+
		"\u0296\u0001\u0000\u0000\u0000\u02985\u0001\u0000\u0000\u0000\u0299\u029c"+
		"\u0005;\u0000\u0000\u029a\u029c\u0005<\u0000\u0000\u029b\u0299\u0001\u0000"+
		"\u0000\u0000\u029b\u029a\u0001\u0000\u0000\u0000\u029c7\u0001\u0000\u0000"+
		"\u0000!=GKPV]at\u007f\u0081\u0089\u008d\u009e\u00e9\u00ed\u00ff\u011b"+
		"\u0120\u01ad\u01b8\u01c3\u01e2\u022f\u0231\u0239\u0247\u024a\u0274\u027c"+
		"\u0289\u0293\u0297\u029b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}