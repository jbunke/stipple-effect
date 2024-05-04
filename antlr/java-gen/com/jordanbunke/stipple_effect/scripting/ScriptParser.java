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
		TEX_COL_REPL=66, GEN_LOOKUP=67, RED=68, GREEN=69, BLUE=70, ALPHA=71, WIDTH=72, 
		HEIGHT=73, HAS=74, LOOKUP=75, KEYS=76, PIXEL=77, ADD=78, REMOVE=79, DEFINE=80, 
		DRAW=81, AT=82, SUB=83, DOT=84, LINE=85, FILL=86, SECTION=87, FLOAT_LIT=88, 
		DEC_LIT=89, HEX_LIT=90, CHAR_QUOTE=91, STR_QUOTE=92, STRING_LIT=93, CHAR_LIT=94, 
		ESC_CHAR=95, IDENTIFIER=96;
	public static final int
		RULE_head_rule = 0, RULE_func_body = 1, RULE_signature = 2, RULE_param_list = 3, 
		RULE_declaration = 4, RULE_type = 5, RULE_body = 6, RULE_stat = 7, RULE_return_stat = 8, 
		RULE_loop_stat = 9, RULE_iteration_def = 10, RULE_while_def = 11, RULE_for_def = 12, 
		RULE_if_stat = 13, RULE_if_def = 14, RULE_expr = 15, RULE_assignment = 16, 
		RULE_var_init = 17, RULE_var_def = 18, RULE_assignable = 19, RULE_ident = 20, 
		RULE_literal = 21, RULE_int_lit = 22, RULE_bool_lit = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"head_rule", "func_body", "signature", "param_list", "declaration", "type", 
			"body", "stat", "return_stat", "loop_stat", "iteration_def", "while_def", 
			"for_def", "if_stat", "if_def", "expr", "assignment", "var_init", "var_def", 
			"assignable", "ident", "literal", "int_lit", "bool_lit"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'('", "')'", "'['", "']'", "'{'", "'}'", "';'", 
			"':'", "','", "'.'", "'|'", "'?'", "'='", "'++'", "'--'", "'+='", "'-='", 
			"'*='", "'/='", "'%='", "'&='", "'|='", "'->'", "'::'", "'=='", "'!='", 
			"'>'", "'<'", "'>='", "'<='", "'^'", "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'&&'", "'||'", "'!'", "'#|'", "'in'", "'final'", "'bool'", "'float'", 
			"'int'", "'char'", "'color'", "'image'", "'string'", "'return'", "'do'", 
			"'while'", "'for'", "'if'", "'else'", "'true'", "'false'", "'new'", "'from'", 
			"'rgba'", "'rgb'", "'blank'", "'tex_col_repl'", "'gen_lookup'", null, 
			null, null, null, null, null, "'.has'", "'.lookup'", "'.keys'", "'.pixel'", 
			"'.add'", "'.remove'", "'.define'", "'.draw'", "'.at'", "'.sub'", "'.dot'", 
			"'.line'", "'.fill'", "'.section'", null, null, null, "'''", "'\"'"
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
			"RED", "GREEN", "BLUE", "ALPHA", "WIDTH", "HEIGHT", "HAS", "LOOKUP", 
			"KEYS", "PIXEL", "ADD", "REMOVE", "DEFINE", "DRAW", "AT", "SUB", "DOT", 
			"LINE", "FILL", "SECTION", "FLOAT_LIT", "DEC_LIT", "HEX_LIT", "CHAR_QUOTE", 
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			signature();
			setState(49);
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
		public TerminalNode DEF() { return getToken(ScriptParser.DEF, 0); }
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
		enterRule(_localctx, 2, RULE_func_body);
		try {
			setState(54);
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
			case FLOAT_LIT:
			case DEC_LIT:
			case HEX_LIT:
			case STRING_LIT:
			case CHAR_LIT:
			case IDENTIFIER:
				_localctx = new StandardFuncBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(51);
				body();
				}
				break;
			case DEF:
				_localctx = new FunctionalFuncBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				match(DEF);
				setState(53);
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
		enterRule(_localctx, 4, RULE_signature);
		int _la;
		try {
			setState(69);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				_localctx = new VoidReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				match(LPAREN);
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8972014882652416L) != 0)) {
					{
					setState(57);
					param_list();
					}
				}

				setState(60);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new TypeReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				match(LPAREN);
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8972014882652416L) != 0)) {
					{
					setState(62);
					param_list();
					}
				}

				setState(65);
				match(ARROW);
				setState(66);
				type(0);
				setState(67);
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
		enterRule(_localctx, 6, RULE_param_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			declaration();
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(72);
				match(COMMA);
				setState(73);
				declaration();
				}
				}
				setState(78);
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
		enterRule(_localctx, 8, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINAL) {
				{
				setState(79);
				match(FINAL);
				}
			}

			setState(82);
			type(0);
			setState(83);
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
		public TerminalNode LT() { return getToken(ScriptParser.LT, 0); }
		public TerminalNode GT() { return getToken(ScriptParser.GT, 0); }
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
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
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
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
				{
				_localctx = new BoolTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(86);
				match(BOOL);
				}
				break;
			case INT:
				{
				_localctx = new IntTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(87);
				match(INT);
				}
				break;
			case FLOAT:
				{
				_localctx = new FloatTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(88);
				match(FLOAT);
				}
				break;
			case CHAR:
				{
				_localctx = new CharTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(89);
				match(CHAR);
				}
				break;
			case STRING:
				{
				_localctx = new StringTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90);
				match(STRING);
				}
				break;
			case IMAGE:
				{
				_localctx = new ImageTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(91);
				match(IMAGE);
				}
				break;
			case COLOR:
				{
				_localctx = new ColorTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(92);
				match(COLOR);
				}
				break;
			case LCURLY:
				{
				_localctx = new MapTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(93);
				match(LCURLY);
				setState(94);
				((MapTypeContext)_localctx).key = type(0);
				setState(95);
				match(COLON);
				setState(96);
				((MapTypeContext)_localctx).val = type(0);
				setState(97);
				match(RCURLY);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(112);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(110);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(101);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(102);
						match(LBRACKET);
						setState(103);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new SetTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(104);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(105);
						match(LT);
						setState(106);
						match(GT);
						}
						break;
					case 3:
						{
						_localctx = new ListTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(107);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(108);
						match(LCURLY);
						setState(109);
						match(RCURLY);
						}
						break;
					}
					} 
				}
				setState(114);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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
		enterRule(_localctx, 12, RULE_body);
		int _la;
		try {
			setState(124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new SingleStatBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(115);
				stat();
				}
				break;
			case 2:
				_localctx = new ComplexBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(116);
				match(LCURLY);
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -288252295517306544L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 6023020559L) != 0)) {
					{
					{
					setState(117);
					stat();
					}
					}
					setState(122);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(123);
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
		enterRule(_localctx, 14, RULE_stat);
		int _la;
		try {
			setState(216);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new LoopStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				loop_stat();
				}
				break;
			case 2:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(127);
				if_stat();
				}
				break;
			case 3:
				_localctx = new VarDefStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(128);
				var_def();
				setState(129);
				match(SEMICOLON);
				}
				break;
			case 4:
				_localctx = new AssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(131);
				assignment();
				setState(132);
				match(SEMICOLON);
				}
				break;
			case 5:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(134);
				return_stat();
				}
				break;
			case 6:
				_localctx = new AddToCollectionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(135);
				((AddToCollectionContext)_localctx).col = expr(0);
				setState(136);
				match(ADD);
				setState(137);
				match(LPAREN);
				setState(138);
				((AddToCollectionContext)_localctx).elem = expr(0);
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(139);
					match(COMMA);
					setState(140);
					((AddToCollectionContext)_localctx).index = expr(0);
					}
				}

				setState(143);
				match(RPAREN);
				setState(144);
				match(SEMICOLON);
				}
				break;
			case 7:
				_localctx = new RemoveFromCollectionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(146);
				((RemoveFromCollectionContext)_localctx).col = expr(0);
				setState(147);
				match(REMOVE);
				setState(148);
				match(LPAREN);
				setState(149);
				((RemoveFromCollectionContext)_localctx).arg = expr(0);
				setState(150);
				match(RPAREN);
				setState(151);
				match(SEMICOLON);
				}
				break;
			case 8:
				_localctx = new DefineMapEntryStatementContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(153);
				((DefineMapEntryStatementContext)_localctx).map = expr(0);
				setState(154);
				match(DEFINE);
				setState(155);
				match(LPAREN);
				setState(156);
				((DefineMapEntryStatementContext)_localctx).key = expr(0);
				setState(157);
				match(COMMA);
				setState(158);
				((DefineMapEntryStatementContext)_localctx).val = expr(0);
				setState(159);
				match(RPAREN);
				setState(160);
				match(SEMICOLON);
				}
				break;
			case 9:
				_localctx = new DrawOntoImageStatementContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(162);
				((DrawOntoImageStatementContext)_localctx).canvas = expr(0);
				setState(163);
				match(DRAW);
				setState(164);
				match(LPAREN);
				setState(165);
				((DrawOntoImageStatementContext)_localctx).img = expr(0);
				setState(166);
				match(COMMA);
				setState(167);
				((DrawOntoImageStatementContext)_localctx).x = expr(0);
				setState(168);
				match(COMMA);
				setState(169);
				((DrawOntoImageStatementContext)_localctx).y = expr(0);
				setState(170);
				match(RPAREN);
				setState(171);
				match(SEMICOLON);
				}
				break;
			case 10:
				_localctx = new DotStatementContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(173);
				((DotStatementContext)_localctx).canvas = expr(0);
				setState(174);
				match(DOT);
				setState(175);
				match(LPAREN);
				setState(176);
				((DotStatementContext)_localctx).col = expr(0);
				setState(177);
				match(COMMA);
				setState(178);
				((DotStatementContext)_localctx).x = expr(0);
				setState(179);
				match(COMMA);
				setState(180);
				((DotStatementContext)_localctx).y = expr(0);
				setState(181);
				match(RPAREN);
				setState(182);
				match(SEMICOLON);
				}
				break;
			case 11:
				_localctx = new DrawLineStatementContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(184);
				((DrawLineStatementContext)_localctx).canvas = expr(0);
				setState(185);
				match(LINE);
				setState(186);
				match(LPAREN);
				setState(187);
				((DrawLineStatementContext)_localctx).col = expr(0);
				setState(188);
				match(COMMA);
				setState(189);
				((DrawLineStatementContext)_localctx).breadth = expr(0);
				setState(190);
				match(COMMA);
				setState(191);
				((DrawLineStatementContext)_localctx).x1 = expr(0);
				setState(192);
				match(COMMA);
				setState(193);
				((DrawLineStatementContext)_localctx).y1 = expr(0);
				setState(194);
				match(COMMA);
				setState(195);
				((DrawLineStatementContext)_localctx).x2 = expr(0);
				setState(196);
				match(COMMA);
				setState(197);
				((DrawLineStatementContext)_localctx).y2 = expr(0);
				setState(198);
				match(RPAREN);
				setState(199);
				match(SEMICOLON);
				}
				break;
			case 12:
				_localctx = new FillStatementContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(201);
				((FillStatementContext)_localctx).canvas = expr(0);
				setState(202);
				match(FILL);
				setState(203);
				match(LPAREN);
				setState(204);
				((FillStatementContext)_localctx).col = expr(0);
				setState(205);
				match(COMMA);
				setState(206);
				((FillStatementContext)_localctx).x = expr(0);
				setState(207);
				match(COMMA);
				setState(208);
				((FillStatementContext)_localctx).y = expr(0);
				setState(209);
				match(COMMA);
				setState(210);
				((FillStatementContext)_localctx).w = expr(0);
				setState(211);
				match(COMMA);
				setState(212);
				((FillStatementContext)_localctx).h = expr(0);
				setState(213);
				match(RPAREN);
				setState(214);
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
		enterRule(_localctx, 16, RULE_return_stat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(RETURN);
			setState(220);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -576447487296929456L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 6023020559L) != 0)) {
				{
				setState(219);
				expr(0);
				}
			}

			setState(222);
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
		enterRule(_localctx, 18, RULE_loop_stat);
		try {
			setState(238);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new WhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(224);
				while_def();
				setState(225);
				body();
				}
				break;
			case 2:
				_localctx = new IteratorLoopContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(227);
				iteration_def();
				setState(228);
				body();
				}
				break;
			case 3:
				_localctx = new ForLoopContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(230);
				for_def();
				setState(231);
				body();
				}
				break;
			case 4:
				_localctx = new DoWhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(233);
				match(DO);
				setState(234);
				body();
				setState(235);
				while_def();
				setState(236);
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
		enterRule(_localctx, 20, RULE_iteration_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(FOR);
			setState(241);
			match(LPAREN);
			setState(242);
			declaration();
			setState(243);
			match(IN);
			setState(244);
			expr(0);
			setState(245);
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
		enterRule(_localctx, 22, RULE_while_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(WHILE);
			setState(248);
			match(LPAREN);
			setState(249);
			expr(0);
			setState(250);
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
		enterRule(_localctx, 24, RULE_for_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(FOR);
			setState(253);
			match(LPAREN);
			setState(254);
			var_init();
			setState(255);
			match(SEMICOLON);
			setState(256);
			expr(0);
			setState(257);
			match(SEMICOLON);
			setState(258);
			assignment();
			setState(259);
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
		enterRule(_localctx, 26, RULE_if_stat);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			if_def();
			setState(266);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(262);
					match(ELSE);
					setState(263);
					if_def();
					}
					} 
				}
				setState(268);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			setState(271);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(269);
				match(ELSE);
				setState(270);
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
		enterRule(_localctx, 28, RULE_if_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(IF);
			setState(274);
			match(LPAREN);
			setState(275);
			((If_defContext)_localctx).cond = expr(0);
			setState(276);
			match(RPAREN);
			setState(277);
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
	public static class NewMapExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScriptParser.NEW, 0); }
		public TerminalNode LCURLY() { return getToken(ScriptParser.LCURLY, 0); }
		public TerminalNode COLON() { return getToken(ScriptParser.COLON, 0); }
		public TerminalNode RCURLY() { return getToken(ScriptParser.RCURLY, 0); }
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
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				_localctx = new NestedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(280);
				match(LPAREN);
				setState(281);
				expr(0);
				setState(282);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new ImageFromPathExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(284);
				match(FROM);
				setState(285);
				match(LPAREN);
				setState(286);
				expr(0);
				setState(287);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new ImageOfBoundsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(289);
				match(BLANK);
				setState(290);
				match(LPAREN);
				setState(291);
				((ImageOfBoundsExpressionContext)_localctx).width = expr(0);
				setState(292);
				match(COMMA);
				setState(293);
				((ImageOfBoundsExpressionContext)_localctx).height = expr(0);
				setState(294);
				match(RPAREN);
				}
				break;
			case 4:
				{
				_localctx = new TextureColorReplaceExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(296);
				match(TEX_COL_REPL);
				setState(297);
				match(LPAREN);
				setState(298);
				((TextureColorReplaceExpressionContext)_localctx).texture = expr(0);
				setState(299);
				match(COMMA);
				setState(300);
				((TextureColorReplaceExpressionContext)_localctx).lookup = expr(0);
				setState(301);
				match(COMMA);
				setState(302);
				((TextureColorReplaceExpressionContext)_localctx).replace = expr(0);
				setState(303);
				match(RPAREN);
				}
				break;
			case 5:
				{
				_localctx = new GenLookupExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(305);
				match(GEN_LOOKUP);
				setState(306);
				match(LPAREN);
				setState(307);
				((GenLookupExpressionContext)_localctx).source = expr(0);
				setState(308);
				match(COMMA);
				setState(309);
				((GenLookupExpressionContext)_localctx).vert = expr(0);
				setState(310);
				match(RPAREN);
				}
				break;
			case 6:
				{
				_localctx = new RGBColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(312);
				match(RGB);
				setState(313);
				match(LPAREN);
				setState(314);
				((RGBColorExpressionContext)_localctx).r = expr(0);
				setState(315);
				match(COMMA);
				setState(316);
				((RGBColorExpressionContext)_localctx).g = expr(0);
				setState(317);
				match(COMMA);
				setState(318);
				((RGBColorExpressionContext)_localctx).b = expr(0);
				setState(319);
				match(RPAREN);
				}
				break;
			case 7:
				{
				_localctx = new RGBAColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(321);
				match(RGBA);
				setState(322);
				match(LPAREN);
				setState(323);
				((RGBAColorExpressionContext)_localctx).r = expr(0);
				setState(324);
				match(COMMA);
				setState(325);
				((RGBAColorExpressionContext)_localctx).g = expr(0);
				setState(326);
				match(COMMA);
				setState(327);
				((RGBAColorExpressionContext)_localctx).b = expr(0);
				setState(328);
				match(COMMA);
				setState(329);
				((RGBAColorExpressionContext)_localctx).a = expr(0);
				setState(330);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(332);
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
				setState(333);
				expr(16);
				}
				break;
			case 9:
				{
				_localctx = new ExplicitArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(334);
				match(LBRACKET);
				setState(335);
				expr(0);
				setState(340);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(336);
					match(COMMA);
					setState(337);
					expr(0);
					}
					}
					setState(342);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(343);
				match(RBRACKET);
				}
				break;
			case 10:
				{
				_localctx = new ExplicitListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(345);
				match(LT);
				setState(346);
				expr(0);
				setState(351);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(347);
					match(COMMA);
					setState(348);
					expr(0);
					}
					}
					setState(353);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(354);
				match(GT);
				}
				break;
			case 11:
				{
				_localctx = new ExplicitSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(356);
				match(LCURLY);
				setState(357);
				expr(0);
				setState(362);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(358);
					match(COMMA);
					setState(359);
					expr(0);
					}
					}
					setState(364);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(365);
				match(RCURLY);
				}
				break;
			case 12:
				{
				_localctx = new NewArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(367);
				match(NEW);
				setState(368);
				type(0);
				setState(369);
				match(LBRACKET);
				setState(370);
				expr(0);
				setState(371);
				match(RBRACKET);
				}
				break;
			case 13:
				{
				_localctx = new NewListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(373);
				match(NEW);
				setState(374);
				match(LT);
				setState(375);
				match(GT);
				}
				break;
			case 14:
				{
				_localctx = new NewSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(376);
				match(NEW);
				setState(377);
				match(LCURLY);
				setState(378);
				match(RCURLY);
				}
				break;
			case 15:
				{
				_localctx = new NewMapExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(379);
				match(NEW);
				setState(380);
				match(LCURLY);
				setState(381);
				match(COLON);
				setState(382);
				match(RCURLY);
				}
				break;
			case 16:
				{
				_localctx = new AssignableExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(383);
				assignable();
				}
				break;
			case 17:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(384);
				literal();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(464);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(462);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ArithmeticBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(387);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(388);
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
						setState(389);
						((ArithmeticBinExpressionContext)_localctx).b = expr(16);
						}
						break;
					case 2:
						{
						_localctx = new MultBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((MultBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(390);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(391);
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
						setState(392);
						((MultBinExpressionContext)_localctx).b = expr(15);
						}
						break;
					case 3:
						{
						_localctx = new PowerBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((PowerBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(393);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(394);
						match(RAISE);
						setState(395);
						((PowerBinExpressionContext)_localctx).b = expr(14);
						}
						break;
					case 4:
						{
						_localctx = new ComparisonBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ComparisonBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(396);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(397);
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
						setState(398);
						((ComparisonBinExpressionContext)_localctx).b = expr(13);
						}
						break;
					case 5:
						{
						_localctx = new LogicBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((LogicBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(399);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(400);
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
						setState(401);
						((LogicBinExpressionContext)_localctx).b = expr(12);
						}
						break;
					case 6:
						{
						_localctx = new TernaryExpressionContext(new ExprContext(_parentctx, _parentState));
						((TernaryExpressionContext)_localctx).cond = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(402);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(403);
						match(QUESTION);
						setState(404);
						((TernaryExpressionContext)_localctx).if_ = expr(0);
						setState(405);
						match(COLON);
						setState(406);
						((TernaryExpressionContext)_localctx).else_ = expr(11);
						}
						break;
					case 7:
						{
						_localctx = new ContainsExpressionContext(new ExprContext(_parentctx, _parentState));
						((ContainsExpressionContext)_localctx).col = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(408);
						if (!(precpred(_ctx, 31))) throw new FailedPredicateException(this, "precpred(_ctx, 31)");
						setState(409);
						match(HAS);
						setState(410);
						match(LPAREN);
						setState(411);
						((ContainsExpressionContext)_localctx).elem = expr(0);
						setState(412);
						match(RPAREN);
						}
						break;
					case 8:
						{
						_localctx = new MapLookupExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapLookupExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(414);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(415);
						match(LOOKUP);
						setState(416);
						match(LPAREN);
						setState(417);
						((MapLookupExpressionContext)_localctx).elem = expr(0);
						setState(418);
						match(RPAREN);
						}
						break;
					case 9:
						{
						_localctx = new MapKeysetExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapKeysetExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(420);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(421);
						match(KEYS);
						setState(422);
						match(LPAREN);
						setState(423);
						match(RPAREN);
						}
						break;
					case 10:
						{
						_localctx = new ColorChannelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorChannelExpressionContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(424);
						if (!(precpred(_ctx, 28))) throw new FailedPredicateException(this, "precpred(_ctx, 28)");
						setState(425);
						((ColorChannelExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & 15L) != 0)) ) {
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
						setState(426);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(427);
						match(SECTION);
						setState(428);
						match(LPAREN);
						setState(429);
						((ImageSectionExpressionContext)_localctx).x = expr(0);
						setState(430);
						match(COMMA);
						setState(431);
						((ImageSectionExpressionContext)_localctx).y = expr(0);
						setState(432);
						match(COMMA);
						setState(433);
						((ImageSectionExpressionContext)_localctx).w = expr(0);
						setState(434);
						match(COMMA);
						setState(435);
						((ImageSectionExpressionContext)_localctx).h = expr(0);
						setState(436);
						match(RPAREN);
						}
						break;
					case 12:
						{
						_localctx = new ColorAtPixelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorAtPixelExpressionContext)_localctx).img = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(438);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(439);
						match(PIXEL);
						setState(440);
						match(LPAREN);
						setState(441);
						((ColorAtPixelExpressionContext)_localctx).x = expr(0);
						setState(442);
						match(COMMA);
						setState(443);
						((ColorAtPixelExpressionContext)_localctx).y = expr(0);
						setState(444);
						match(RPAREN);
						}
						break;
					case 13:
						{
						_localctx = new ImageBoundExpressionContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(446);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(447);
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
						setState(448);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(449);
						match(AT);
						setState(450);
						match(LPAREN);
						setState(451);
						((CharAtExpressionContext)_localctx).index = expr(0);
						setState(452);
						match(RPAREN);
						}
						break;
					case 15:
						{
						_localctx = new SubstringExpressionContext(new ExprContext(_parentctx, _parentState));
						((SubstringExpressionContext)_localctx).string = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(454);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(455);
						match(SUB);
						setState(456);
						match(LPAREN);
						setState(457);
						((SubstringExpressionContext)_localctx).beg = expr(0);
						setState(458);
						match(COMMA);
						setState(459);
						((SubstringExpressionContext)_localctx).end = expr(0);
						setState(460);
						match(RPAREN);
						}
						break;
					}
					} 
				}
				setState(466);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
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
		enterRule(_localctx, 32, RULE_assignment);
		try {
			setState(505);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new StandardAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(467);
				assignable();
				setState(468);
				match(ASSIGN);
				setState(469);
				expr(0);
				}
				break;
			case 2:
				_localctx = new IncrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(471);
				assignable();
				setState(472);
				match(INCREMENT);
				}
				break;
			case 3:
				_localctx = new DecrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(474);
				assignable();
				setState(475);
				match(DECREMENT);
				}
				break;
			case 4:
				_localctx = new AddAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(477);
				assignable();
				setState(478);
				match(ADD_ASSIGN);
				setState(479);
				expr(0);
				}
				break;
			case 5:
				_localctx = new SubAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(481);
				assignable();
				setState(482);
				match(SUB_ASSIGN);
				setState(483);
				expr(0);
				}
				break;
			case 6:
				_localctx = new MultAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(485);
				assignable();
				setState(486);
				match(MUL_ASSIGN);
				setState(487);
				expr(0);
				}
				break;
			case 7:
				_localctx = new DivAssignmnetContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(489);
				assignable();
				setState(490);
				match(DIV_ASSIGN);
				setState(491);
				expr(0);
				}
				break;
			case 8:
				_localctx = new ModAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(493);
				assignable();
				setState(494);
				match(MOD_ASSIGN);
				setState(495);
				expr(0);
				}
				break;
			case 9:
				_localctx = new AndAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(497);
				assignable();
				setState(498);
				match(AND_ASSIGN);
				setState(499);
				expr(0);
				}
				break;
			case 10:
				_localctx = new OrAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(501);
				assignable();
				setState(502);
				match(OR_ASSIGN);
				setState(503);
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
		enterRule(_localctx, 34, RULE_var_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(507);
			declaration();
			setState(508);
			match(ASSIGN);
			setState(509);
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
		enterRule(_localctx, 36, RULE_var_def);
		try {
			setState(513);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new ImplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(511);
				declaration();
				}
				break;
			case 2:
				_localctx = new ExplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(512);
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
		enterRule(_localctx, 38, RULE_assignable);
		try {
			setState(526);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				_localctx = new SimpleAssignableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(515);
				ident();
				}
				break;
			case 2:
				_localctx = new ListAssignableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(516);
				ident();
				setState(517);
				match(LT);
				setState(518);
				expr(0);
				setState(519);
				match(GT);
				}
				break;
			case 3:
				_localctx = new ArrayAssignableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(521);
				ident();
				setState(522);
				match(LBRACKET);
				setState(523);
				expr(0);
				setState(524);
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
		enterRule(_localctx, 40, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(528);
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
		enterRule(_localctx, 42, RULE_literal);
		try {
			setState(535);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LIT:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(530);
				match(STRING_LIT);
				}
				break;
			case CHAR_LIT:
				_localctx = new CharLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(531);
				match(CHAR_LIT);
				}
				break;
			case DEC_LIT:
			case HEX_LIT:
				_localctx = new IntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(532);
				int_lit();
				}
				break;
			case FLOAT_LIT:
				_localctx = new FloatLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(533);
				match(FLOAT_LIT);
				}
				break;
			case TRUE:
			case FALSE:
				_localctx = new BoolLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(534);
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
		enterRule(_localctx, 44, RULE_int_lit);
		try {
			setState(539);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HEX_LIT:
				_localctx = new HexadecimalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(537);
				match(HEX_LIT);
				}
				break;
			case DEC_LIT:
				_localctx = new DecimalContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(538);
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
		enterRule(_localctx, 46, RULE_bool_lit);
		try {
			setState(543);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(541);
				match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(542);
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
		case 5:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 15:
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
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 13);
		case 6:
			return precpred(_ctx, 12);
		case 7:
			return precpred(_ctx, 11);
		case 8:
			return precpred(_ctx, 10);
		case 9:
			return precpred(_ctx, 31);
		case 10:
			return precpred(_ctx, 30);
		case 11:
			return precpred(_ctx, 29);
		case 12:
			return precpred(_ctx, 28);
		case 13:
			return precpred(_ctx, 23);
		case 14:
			return precpred(_ctx, 22);
		case 15:
			return precpred(_ctx, 21);
		case 16:
			return precpred(_ctx, 18);
		case 17:
			return precpred(_ctx, 17);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001`\u0222\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u00017\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0003\u0002;\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002@\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002F\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0005\u0003K\b\u0003\n\u0003\f\u0003N\t\u0003\u0001\u0004\u0003\u0004"+
		"Q\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u0005d\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"o\b\u0005\n\u0005\f\u0005r\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0005\u0006w\b\u0006\n\u0006\f\u0006z\t\u0006\u0001\u0006\u0003\u0006"+
		"}\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u008e\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007\u00d9\b\u0007\u0001\b\u0001\b\u0003\b\u00dd\b"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u00ef"+
		"\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0005\r\u0109\b\r\n\r\f\r\u010c\t\r\u0001\r\u0001\r\u0003\r\u0110\b"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f"+
		"\u0153\b\u000f\n\u000f\f\u000f\u0156\t\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u015e\b\u000f\n"+
		"\u000f\f\u000f\u0161\t\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u0169\b\u000f\n\u000f\f\u000f"+
		"\u016c\t\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u0182\b\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0005\u000f\u01cf\b\u000f\n\u000f\f\u000f\u01d2"+
		"\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u01fa\b\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0003\u0012\u0202"+
		"\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u020f\b\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u0218\b\u0015\u0001\u0016\u0001"+
		"\u0016\u0003\u0016\u021c\b\u0016\u0001\u0017\u0001\u0017\u0003\u0017\u0220"+
		"\b\u0017\u0001\u0017\u0000\u0002\n\u001e\u0018\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,."+
		"\u0000\u0007\u0002\u0000$$*+\u0001\u0000#$\u0001\u0000%\'\u0001\u0000"+
		"\u001c!\u0001\u0000()\u0001\u0000DG\u0001\u0000HI\u0261\u00000\u0001\u0000"+
		"\u0000\u0000\u00026\u0001\u0000\u0000\u0000\u0004E\u0001\u0000\u0000\u0000"+
		"\u0006G\u0001\u0000\u0000\u0000\bP\u0001\u0000\u0000\u0000\nc\u0001\u0000"+
		"\u0000\u0000\f|\u0001\u0000\u0000\u0000\u000e\u00d8\u0001\u0000\u0000"+
		"\u0000\u0010\u00da\u0001\u0000\u0000\u0000\u0012\u00ee\u0001\u0000\u0000"+
		"\u0000\u0014\u00f0\u0001\u0000\u0000\u0000\u0016\u00f7\u0001\u0000\u0000"+
		"\u0000\u0018\u00fc\u0001\u0000\u0000\u0000\u001a\u0105\u0001\u0000\u0000"+
		"\u0000\u001c\u0111\u0001\u0000\u0000\u0000\u001e\u0181\u0001\u0000\u0000"+
		"\u0000 \u01f9\u0001\u0000\u0000\u0000\"\u01fb\u0001\u0000\u0000\u0000"+
		"$\u0201\u0001\u0000\u0000\u0000&\u020e\u0001\u0000\u0000\u0000(\u0210"+
		"\u0001\u0000\u0000\u0000*\u0217\u0001\u0000\u0000\u0000,\u021b\u0001\u0000"+
		"\u0000\u0000.\u021f\u0001\u0000\u0000\u000001\u0003\u0004\u0002\u0000"+
		"12\u0003\u0002\u0001\u00002\u0001\u0001\u0000\u0000\u000037\u0003\f\u0006"+
		"\u000045\u0005\u001b\u0000\u000057\u0003\u001e\u000f\u000063\u0001\u0000"+
		"\u0000\u000064\u0001\u0000\u0000\u00007\u0003\u0001\u0000\u0000\u0000"+
		"8:\u0005\u0004\u0000\u00009;\u0003\u0006\u0003\u0000:9\u0001\u0000\u0000"+
		"\u0000:;\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<F\u0005\u0005"+
		"\u0000\u0000=?\u0005\u0004\u0000\u0000>@\u0003\u0006\u0003\u0000?>\u0001"+
		"\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000"+
		"AB\u0005\u001a\u0000\u0000BC\u0003\n\u0005\u0000CD\u0005\u0005\u0000\u0000"+
		"DF\u0001\u0000\u0000\u0000E8\u0001\u0000\u0000\u0000E=\u0001\u0000\u0000"+
		"\u0000F\u0005\u0001\u0000\u0000\u0000GL\u0003\b\u0004\u0000HI\u0005\f"+
		"\u0000\u0000IK\u0003\b\u0004\u0000JH\u0001\u0000\u0000\u0000KN\u0001\u0000"+
		"\u0000\u0000LJ\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000M\u0007"+
		"\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000OQ\u0005-\u0000\u0000"+
		"PO\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000"+
		"\u0000RS\u0003\n\u0005\u0000ST\u0003(\u0014\u0000T\t\u0001\u0000\u0000"+
		"\u0000UV\u0006\u0005\uffff\uffff\u0000Vd\u0005.\u0000\u0000Wd\u00050\u0000"+
		"\u0000Xd\u0005/\u0000\u0000Yd\u00051\u0000\u0000Zd\u00054\u0000\u0000"+
		"[d\u00053\u0000\u0000\\d\u00052\u0000\u0000]^\u0005\b\u0000\u0000^_\u0003"+
		"\n\u0005\u0000_`\u0005\u000b\u0000\u0000`a\u0003\n\u0005\u0000ab\u0005"+
		"\t\u0000\u0000bd\u0001\u0000\u0000\u0000cU\u0001\u0000\u0000\u0000cW\u0001"+
		"\u0000\u0000\u0000cX\u0001\u0000\u0000\u0000cY\u0001\u0000\u0000\u0000"+
		"cZ\u0001\u0000\u0000\u0000c[\u0001\u0000\u0000\u0000c\\\u0001\u0000\u0000"+
		"\u0000c]\u0001\u0000\u0000\u0000dp\u0001\u0000\u0000\u0000ef\n\u0004\u0000"+
		"\u0000fg\u0005\u0006\u0000\u0000go\u0005\u0007\u0000\u0000hi\n\u0003\u0000"+
		"\u0000ij\u0005\u001f\u0000\u0000jo\u0005\u001e\u0000\u0000kl\n\u0002\u0000"+
		"\u0000lm\u0005\b\u0000\u0000mo\u0005\t\u0000\u0000ne\u0001\u0000\u0000"+
		"\u0000nh\u0001\u0000\u0000\u0000nk\u0001\u0000\u0000\u0000or\u0001\u0000"+
		"\u0000\u0000pn\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000q\u000b"+
		"\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000s}\u0003\u000e\u0007"+
		"\u0000tx\u0005\b\u0000\u0000uw\u0003\u000e\u0007\u0000vu\u0001\u0000\u0000"+
		"\u0000wz\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000xy\u0001\u0000"+
		"\u0000\u0000y{\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000{}\u0005"+
		"\t\u0000\u0000|s\u0001\u0000\u0000\u0000|t\u0001\u0000\u0000\u0000}\r"+
		"\u0001\u0000\u0000\u0000~\u00d9\u0003\u0012\t\u0000\u007f\u00d9\u0003"+
		"\u001a\r\u0000\u0080\u0081\u0003$\u0012\u0000\u0081\u0082\u0005\n\u0000"+
		"\u0000\u0082\u00d9\u0001\u0000\u0000\u0000\u0083\u0084\u0003 \u0010\u0000"+
		"\u0084\u0085\u0005\n\u0000\u0000\u0085\u00d9\u0001\u0000\u0000\u0000\u0086"+
		"\u00d9\u0003\u0010\b\u0000\u0087\u0088\u0003\u001e\u000f\u0000\u0088\u0089"+
		"\u0005N\u0000\u0000\u0089\u008a\u0005\u0004\u0000\u0000\u008a\u008d\u0003"+
		"\u001e\u000f\u0000\u008b\u008c\u0005\f\u0000\u0000\u008c\u008e\u0003\u001e"+
		"\u000f\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000"+
		"\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f\u0090\u0005\u0005"+
		"\u0000\u0000\u0090\u0091\u0005\n\u0000\u0000\u0091\u00d9\u0001\u0000\u0000"+
		"\u0000\u0092\u0093\u0003\u001e\u000f\u0000\u0093\u0094\u0005O\u0000\u0000"+
		"\u0094\u0095\u0005\u0004\u0000\u0000\u0095\u0096\u0003\u001e\u000f\u0000"+
		"\u0096\u0097\u0005\u0005\u0000\u0000\u0097\u0098\u0005\n\u0000\u0000\u0098"+
		"\u00d9\u0001\u0000\u0000\u0000\u0099\u009a\u0003\u001e\u000f\u0000\u009a"+
		"\u009b\u0005P\u0000\u0000\u009b\u009c\u0005\u0004\u0000\u0000\u009c\u009d"+
		"\u0003\u001e\u000f\u0000\u009d\u009e\u0005\f\u0000\u0000\u009e\u009f\u0003"+
		"\u001e\u000f\u0000\u009f\u00a0\u0005\u0005\u0000\u0000\u00a0\u00a1\u0005"+
		"\n\u0000\u0000\u00a1\u00d9\u0001\u0000\u0000\u0000\u00a2\u00a3\u0003\u001e"+
		"\u000f\u0000\u00a3\u00a4\u0005Q\u0000\u0000\u00a4\u00a5\u0005\u0004\u0000"+
		"\u0000\u00a5\u00a6\u0003\u001e\u000f\u0000\u00a6\u00a7\u0005\f\u0000\u0000"+
		"\u00a7\u00a8\u0003\u001e\u000f\u0000\u00a8\u00a9\u0005\f\u0000\u0000\u00a9"+
		"\u00aa\u0003\u001e\u000f\u0000\u00aa\u00ab\u0005\u0005\u0000\u0000\u00ab"+
		"\u00ac\u0005\n\u0000\u0000\u00ac\u00d9\u0001\u0000\u0000\u0000\u00ad\u00ae"+
		"\u0003\u001e\u000f\u0000\u00ae\u00af\u0005T\u0000\u0000\u00af\u00b0\u0005"+
		"\u0004\u0000\u0000\u00b0\u00b1\u0003\u001e\u000f\u0000\u00b1\u00b2\u0005"+
		"\f\u0000\u0000\u00b2\u00b3\u0003\u001e\u000f\u0000\u00b3\u00b4\u0005\f"+
		"\u0000\u0000\u00b4\u00b5\u0003\u001e\u000f\u0000\u00b5\u00b6\u0005\u0005"+
		"\u0000\u0000\u00b6\u00b7\u0005\n\u0000\u0000\u00b7\u00d9\u0001\u0000\u0000"+
		"\u0000\u00b8\u00b9\u0003\u001e\u000f\u0000\u00b9\u00ba\u0005U\u0000\u0000"+
		"\u00ba\u00bb\u0005\u0004\u0000\u0000\u00bb\u00bc\u0003\u001e\u000f\u0000"+
		"\u00bc\u00bd\u0005\f\u0000\u0000\u00bd\u00be\u0003\u001e\u000f\u0000\u00be"+
		"\u00bf\u0005\f\u0000\u0000\u00bf\u00c0\u0003\u001e\u000f\u0000\u00c0\u00c1"+
		"\u0005\f\u0000\u0000\u00c1\u00c2\u0003\u001e\u000f\u0000\u00c2\u00c3\u0005"+
		"\f\u0000\u0000\u00c3\u00c4\u0003\u001e\u000f\u0000\u00c4\u00c5\u0005\f"+
		"\u0000\u0000\u00c5\u00c6\u0003\u001e\u000f\u0000\u00c6\u00c7\u0005\u0005"+
		"\u0000\u0000\u00c7\u00c8\u0005\n\u0000\u0000\u00c8\u00d9\u0001\u0000\u0000"+
		"\u0000\u00c9\u00ca\u0003\u001e\u000f\u0000\u00ca\u00cb\u0005V\u0000\u0000"+
		"\u00cb\u00cc\u0005\u0004\u0000\u0000\u00cc\u00cd\u0003\u001e\u000f\u0000"+
		"\u00cd\u00ce\u0005\f\u0000\u0000\u00ce\u00cf\u0003\u001e\u000f\u0000\u00cf"+
		"\u00d0\u0005\f\u0000\u0000\u00d0\u00d1\u0003\u001e\u000f\u0000\u00d1\u00d2"+
		"\u0005\f\u0000\u0000\u00d2\u00d3\u0003\u001e\u000f\u0000\u00d3\u00d4\u0005"+
		"\f\u0000\u0000\u00d4\u00d5\u0003\u001e\u000f\u0000\u00d5\u00d6\u0005\u0005"+
		"\u0000\u0000\u00d6\u00d7\u0005\n\u0000\u0000\u00d7\u00d9\u0001\u0000\u0000"+
		"\u0000\u00d8~\u0001\u0000\u0000\u0000\u00d8\u007f\u0001\u0000\u0000\u0000"+
		"\u00d8\u0080\u0001\u0000\u0000\u0000\u00d8\u0083\u0001\u0000\u0000\u0000"+
		"\u00d8\u0086\u0001\u0000\u0000\u0000\u00d8\u0087\u0001\u0000\u0000\u0000"+
		"\u00d8\u0092\u0001\u0000\u0000\u0000\u00d8\u0099\u0001\u0000\u0000\u0000"+
		"\u00d8\u00a2\u0001\u0000\u0000\u0000\u00d8\u00ad\u0001\u0000\u0000\u0000"+
		"\u00d8\u00b8\u0001\u0000\u0000\u0000\u00d8\u00c9\u0001\u0000\u0000\u0000"+
		"\u00d9\u000f\u0001\u0000\u0000\u0000\u00da\u00dc\u00055\u0000\u0000\u00db"+
		"\u00dd\u0003\u001e\u000f\u0000\u00dc\u00db\u0001\u0000\u0000\u0000\u00dc"+
		"\u00dd\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000\u00de"+
		"\u00df\u0005\n\u0000\u0000\u00df\u0011\u0001\u0000\u0000\u0000\u00e0\u00e1"+
		"\u0003\u0016\u000b\u0000\u00e1\u00e2\u0003\f\u0006\u0000\u00e2\u00ef\u0001"+
		"\u0000\u0000\u0000\u00e3\u00e4\u0003\u0014\n\u0000\u00e4\u00e5\u0003\f"+
		"\u0006\u0000\u00e5\u00ef\u0001\u0000\u0000\u0000\u00e6\u00e7\u0003\u0018"+
		"\f\u0000\u00e7\u00e8\u0003\f\u0006\u0000\u00e8\u00ef\u0001\u0000\u0000"+
		"\u0000\u00e9\u00ea\u00056\u0000\u0000\u00ea\u00eb\u0003\f\u0006\u0000"+
		"\u00eb\u00ec\u0003\u0016\u000b\u0000\u00ec\u00ed\u0005\n\u0000\u0000\u00ed"+
		"\u00ef\u0001\u0000\u0000\u0000\u00ee\u00e0\u0001\u0000\u0000\u0000\u00ee"+
		"\u00e3\u0001\u0000\u0000\u0000\u00ee\u00e6\u0001\u0000\u0000\u0000\u00ee"+
		"\u00e9\u0001\u0000\u0000\u0000\u00ef\u0013\u0001\u0000\u0000\u0000\u00f0"+
		"\u00f1\u00058\u0000\u0000\u00f1\u00f2\u0005\u0004\u0000\u0000\u00f2\u00f3"+
		"\u0003\b\u0004\u0000\u00f3\u00f4\u0005,\u0000\u0000\u00f4\u00f5\u0003"+
		"\u001e\u000f\u0000\u00f5\u00f6\u0005\u0005\u0000\u0000\u00f6\u0015\u0001"+
		"\u0000\u0000\u0000\u00f7\u00f8\u00057\u0000\u0000\u00f8\u00f9\u0005\u0004"+
		"\u0000\u0000\u00f9\u00fa\u0003\u001e\u000f\u0000\u00fa\u00fb\u0005\u0005"+
		"\u0000\u0000\u00fb\u0017\u0001\u0000\u0000\u0000\u00fc\u00fd\u00058\u0000"+
		"\u0000\u00fd\u00fe\u0005\u0004\u0000\u0000\u00fe\u00ff\u0003\"\u0011\u0000"+
		"\u00ff\u0100\u0005\n\u0000\u0000\u0100\u0101\u0003\u001e\u000f\u0000\u0101"+
		"\u0102\u0005\n\u0000\u0000\u0102\u0103\u0003 \u0010\u0000\u0103\u0104"+
		"\u0005\u0005\u0000\u0000\u0104\u0019\u0001\u0000\u0000\u0000\u0105\u010a"+
		"\u0003\u001c\u000e\u0000\u0106\u0107\u0005:\u0000\u0000\u0107\u0109\u0003"+
		"\u001c\u000e\u0000\u0108\u0106\u0001\u0000\u0000\u0000\u0109\u010c\u0001"+
		"\u0000\u0000\u0000\u010a\u0108\u0001\u0000\u0000\u0000\u010a\u010b\u0001"+
		"\u0000\u0000\u0000\u010b\u010f\u0001\u0000\u0000\u0000\u010c\u010a\u0001"+
		"\u0000\u0000\u0000\u010d\u010e\u0005:\u0000\u0000\u010e\u0110\u0003\f"+
		"\u0006\u0000\u010f\u010d\u0001\u0000\u0000\u0000\u010f\u0110\u0001\u0000"+
		"\u0000\u0000\u0110\u001b\u0001\u0000\u0000\u0000\u0111\u0112\u00059\u0000"+
		"\u0000\u0112\u0113\u0005\u0004\u0000\u0000\u0113\u0114\u0003\u001e\u000f"+
		"\u0000\u0114\u0115\u0005\u0005\u0000\u0000\u0115\u0116\u0003\f\u0006\u0000"+
		"\u0116\u001d\u0001\u0000\u0000\u0000\u0117\u0118\u0006\u000f\uffff\uffff"+
		"\u0000\u0118\u0119\u0005\u0004\u0000\u0000\u0119\u011a\u0003\u001e\u000f"+
		"\u0000\u011a\u011b\u0005\u0005\u0000\u0000\u011b\u0182\u0001\u0000\u0000"+
		"\u0000\u011c\u011d\u0005>\u0000\u0000\u011d\u011e\u0005\u0004\u0000\u0000"+
		"\u011e\u011f\u0003\u001e\u000f\u0000\u011f\u0120\u0005\u0005\u0000\u0000"+
		"\u0120\u0182\u0001\u0000\u0000\u0000\u0121\u0122\u0005A\u0000\u0000\u0122"+
		"\u0123\u0005\u0004\u0000\u0000\u0123\u0124\u0003\u001e\u000f\u0000\u0124"+
		"\u0125\u0005\f\u0000\u0000\u0125\u0126\u0003\u001e\u000f\u0000\u0126\u0127"+
		"\u0005\u0005\u0000\u0000\u0127\u0182\u0001\u0000\u0000\u0000\u0128\u0129"+
		"\u0005B\u0000\u0000\u0129\u012a\u0005\u0004\u0000\u0000\u012a\u012b\u0003"+
		"\u001e\u000f\u0000\u012b\u012c\u0005\f\u0000\u0000\u012c\u012d\u0003\u001e"+
		"\u000f\u0000\u012d\u012e\u0005\f\u0000\u0000\u012e\u012f\u0003\u001e\u000f"+
		"\u0000\u012f\u0130\u0005\u0005\u0000\u0000\u0130\u0182\u0001\u0000\u0000"+
		"\u0000\u0131\u0132\u0005C\u0000\u0000\u0132\u0133\u0005\u0004\u0000\u0000"+
		"\u0133\u0134\u0003\u001e\u000f\u0000\u0134\u0135\u0005\f\u0000\u0000\u0135"+
		"\u0136\u0003\u001e\u000f\u0000\u0136\u0137\u0005\u0005\u0000\u0000\u0137"+
		"\u0182\u0001\u0000\u0000\u0000\u0138\u0139\u0005@\u0000\u0000\u0139\u013a"+
		"\u0005\u0004\u0000\u0000\u013a\u013b\u0003\u001e\u000f\u0000\u013b\u013c"+
		"\u0005\f\u0000\u0000\u013c\u013d\u0003\u001e\u000f\u0000\u013d\u013e\u0005"+
		"\f\u0000\u0000\u013e\u013f\u0003\u001e\u000f\u0000\u013f\u0140\u0005\u0005"+
		"\u0000\u0000\u0140\u0182\u0001\u0000\u0000\u0000\u0141\u0142\u0005?\u0000"+
		"\u0000\u0142\u0143\u0005\u0004\u0000\u0000\u0143\u0144\u0003\u001e\u000f"+
		"\u0000\u0144\u0145\u0005\f\u0000\u0000\u0145\u0146\u0003\u001e\u000f\u0000"+
		"\u0146\u0147\u0005\f\u0000\u0000\u0147\u0148\u0003\u001e\u000f\u0000\u0148"+
		"\u0149\u0005\f\u0000\u0000\u0149\u014a\u0003\u001e\u000f\u0000\u014a\u014b"+
		"\u0005\u0005\u0000\u0000\u014b\u0182\u0001\u0000\u0000\u0000\u014c\u014d"+
		"\u0007\u0000\u0000\u0000\u014d\u0182\u0003\u001e\u000f\u0010\u014e\u014f"+
		"\u0005\u0006\u0000\u0000\u014f\u0154\u0003\u001e\u000f\u0000\u0150\u0151"+
		"\u0005\f\u0000\u0000\u0151\u0153\u0003\u001e\u000f\u0000\u0152\u0150\u0001"+
		"\u0000\u0000\u0000\u0153\u0156\u0001\u0000\u0000\u0000\u0154\u0152\u0001"+
		"\u0000\u0000\u0000\u0154\u0155\u0001\u0000\u0000\u0000\u0155\u0157\u0001"+
		"\u0000\u0000\u0000\u0156\u0154\u0001\u0000\u0000\u0000\u0157\u0158\u0005"+
		"\u0007\u0000\u0000\u0158\u0182\u0001\u0000\u0000\u0000\u0159\u015a\u0005"+
		"\u001f\u0000\u0000\u015a\u015f\u0003\u001e\u000f\u0000\u015b\u015c\u0005"+
		"\f\u0000\u0000\u015c\u015e\u0003\u001e\u000f\u0000\u015d\u015b\u0001\u0000"+
		"\u0000\u0000\u015e\u0161\u0001\u0000\u0000\u0000\u015f\u015d\u0001\u0000"+
		"\u0000\u0000\u015f\u0160\u0001\u0000\u0000\u0000\u0160\u0162\u0001\u0000"+
		"\u0000\u0000\u0161\u015f\u0001\u0000\u0000\u0000\u0162\u0163\u0005\u001e"+
		"\u0000\u0000\u0163\u0182\u0001\u0000\u0000\u0000\u0164\u0165\u0005\b\u0000"+
		"\u0000\u0165\u016a\u0003\u001e\u000f\u0000\u0166\u0167\u0005\f\u0000\u0000"+
		"\u0167\u0169\u0003\u001e\u000f\u0000\u0168\u0166\u0001\u0000\u0000\u0000"+
		"\u0169\u016c\u0001\u0000\u0000\u0000\u016a\u0168\u0001\u0000\u0000\u0000"+
		"\u016a\u016b\u0001\u0000\u0000\u0000\u016b\u016d\u0001\u0000\u0000\u0000"+
		"\u016c\u016a\u0001\u0000\u0000\u0000\u016d\u016e\u0005\t\u0000\u0000\u016e"+
		"\u0182\u0001\u0000\u0000\u0000\u016f\u0170\u0005=\u0000\u0000\u0170\u0171"+
		"\u0003\n\u0005\u0000\u0171\u0172\u0005\u0006\u0000\u0000\u0172\u0173\u0003"+
		"\u001e\u000f\u0000\u0173\u0174\u0005\u0007\u0000\u0000\u0174\u0182\u0001"+
		"\u0000\u0000\u0000\u0175\u0176\u0005=\u0000\u0000\u0176\u0177\u0005\u001f"+
		"\u0000\u0000\u0177\u0182\u0005\u001e\u0000\u0000\u0178\u0179\u0005=\u0000"+
		"\u0000\u0179\u017a\u0005\b\u0000\u0000\u017a\u0182\u0005\t\u0000\u0000"+
		"\u017b\u017c\u0005=\u0000\u0000\u017c\u017d\u0005\b\u0000\u0000\u017d"+
		"\u017e\u0005\u000b\u0000\u0000\u017e\u0182\u0005\t\u0000\u0000\u017f\u0182"+
		"\u0003&\u0013\u0000\u0180\u0182\u0003*\u0015\u0000\u0181\u0117\u0001\u0000"+
		"\u0000\u0000\u0181\u011c\u0001\u0000\u0000\u0000\u0181\u0121\u0001\u0000"+
		"\u0000\u0000\u0181\u0128\u0001\u0000\u0000\u0000\u0181\u0131\u0001\u0000"+
		"\u0000\u0000\u0181\u0138\u0001\u0000\u0000\u0000\u0181\u0141\u0001\u0000"+
		"\u0000\u0000\u0181\u014c\u0001\u0000\u0000\u0000\u0181\u014e\u0001\u0000"+
		"\u0000\u0000\u0181\u0159\u0001\u0000\u0000\u0000\u0181\u0164\u0001\u0000"+
		"\u0000\u0000\u0181\u016f\u0001\u0000\u0000\u0000\u0181\u0175\u0001\u0000"+
		"\u0000\u0000\u0181\u0178\u0001\u0000\u0000\u0000\u0181\u017b\u0001\u0000"+
		"\u0000\u0000\u0181\u017f\u0001\u0000\u0000\u0000\u0181\u0180\u0001\u0000"+
		"\u0000\u0000\u0182\u01d0\u0001\u0000\u0000\u0000\u0183\u0184\n\u000f\u0000"+
		"\u0000\u0184\u0185\u0007\u0001\u0000\u0000\u0185\u01cf\u0003\u001e\u000f"+
		"\u0010\u0186\u0187\n\u000e\u0000\u0000\u0187\u0188\u0007\u0002\u0000\u0000"+
		"\u0188\u01cf\u0003\u001e\u000f\u000f\u0189\u018a\n\r\u0000\u0000\u018a"+
		"\u018b\u0005\"\u0000\u0000\u018b\u01cf\u0003\u001e\u000f\u000e\u018c\u018d"+
		"\n\f\u0000\u0000\u018d\u018e\u0007\u0003\u0000\u0000\u018e\u01cf\u0003"+
		"\u001e\u000f\r\u018f\u0190\n\u000b\u0000\u0000\u0190\u0191\u0007\u0004"+
		"\u0000\u0000\u0191\u01cf\u0003\u001e\u000f\f\u0192\u0193\n\n\u0000\u0000"+
		"\u0193\u0194\u0005\u000f\u0000\u0000\u0194\u0195\u0003\u001e\u000f\u0000"+
		"\u0195\u0196\u0005\u000b\u0000\u0000\u0196\u0197\u0003\u001e\u000f\u000b"+
		"\u0197\u01cf\u0001\u0000\u0000\u0000\u0198\u0199\n\u001f\u0000\u0000\u0199"+
		"\u019a\u0005J\u0000\u0000\u019a\u019b\u0005\u0004\u0000\u0000\u019b\u019c"+
		"\u0003\u001e\u000f\u0000\u019c\u019d\u0005\u0005\u0000\u0000\u019d\u01cf"+
		"\u0001\u0000\u0000\u0000\u019e\u019f\n\u001e\u0000\u0000\u019f\u01a0\u0005"+
		"K\u0000\u0000\u01a0\u01a1\u0005\u0004\u0000\u0000\u01a1\u01a2\u0003\u001e"+
		"\u000f\u0000\u01a2\u01a3\u0005\u0005\u0000\u0000\u01a3\u01cf\u0001\u0000"+
		"\u0000\u0000\u01a4\u01a5\n\u001d\u0000\u0000\u01a5\u01a6\u0005L\u0000"+
		"\u0000\u01a6\u01a7\u0005\u0004\u0000\u0000\u01a7\u01cf\u0005\u0005\u0000"+
		"\u0000\u01a8\u01a9\n\u001c\u0000\u0000\u01a9\u01cf\u0007\u0005\u0000\u0000"+
		"\u01aa\u01ab\n\u0017\u0000\u0000\u01ab\u01ac\u0005W\u0000\u0000\u01ac"+
		"\u01ad\u0005\u0004\u0000\u0000\u01ad\u01ae\u0003\u001e\u000f\u0000\u01ae"+
		"\u01af\u0005\f\u0000\u0000\u01af\u01b0\u0003\u001e\u000f\u0000\u01b0\u01b1"+
		"\u0005\f\u0000\u0000\u01b1\u01b2\u0003\u001e\u000f\u0000\u01b2\u01b3\u0005"+
		"\f\u0000\u0000\u01b3\u01b4\u0003\u001e\u000f\u0000\u01b4\u01b5\u0005\u0005"+
		"\u0000\u0000\u01b5\u01cf\u0001\u0000\u0000\u0000\u01b6\u01b7\n\u0016\u0000"+
		"\u0000\u01b7\u01b8\u0005M\u0000\u0000\u01b8\u01b9\u0005\u0004\u0000\u0000"+
		"\u01b9\u01ba\u0003\u001e\u000f\u0000\u01ba\u01bb\u0005\f\u0000\u0000\u01bb"+
		"\u01bc\u0003\u001e\u000f\u0000\u01bc\u01bd\u0005\u0005\u0000\u0000\u01bd"+
		"\u01cf\u0001\u0000\u0000\u0000\u01be\u01bf\n\u0015\u0000\u0000\u01bf\u01cf"+
		"\u0007\u0006\u0000\u0000\u01c0\u01c1\n\u0012\u0000\u0000\u01c1\u01c2\u0005"+
		"R\u0000\u0000\u01c2\u01c3\u0005\u0004\u0000\u0000\u01c3\u01c4\u0003\u001e"+
		"\u000f\u0000\u01c4\u01c5\u0005\u0005\u0000\u0000\u01c5\u01cf\u0001\u0000"+
		"\u0000\u0000\u01c6\u01c7\n\u0011\u0000\u0000\u01c7\u01c8\u0005S\u0000"+
		"\u0000\u01c8\u01c9\u0005\u0004\u0000\u0000\u01c9\u01ca\u0003\u001e\u000f"+
		"\u0000\u01ca\u01cb\u0005\f\u0000\u0000\u01cb\u01cc\u0003\u001e\u000f\u0000"+
		"\u01cc\u01cd\u0005\u0005\u0000\u0000\u01cd\u01cf\u0001\u0000\u0000\u0000"+
		"\u01ce\u0183\u0001\u0000\u0000\u0000\u01ce\u0186\u0001\u0000\u0000\u0000"+
		"\u01ce\u0189\u0001\u0000\u0000\u0000\u01ce\u018c\u0001\u0000\u0000\u0000"+
		"\u01ce\u018f\u0001\u0000\u0000\u0000\u01ce\u0192\u0001\u0000\u0000\u0000"+
		"\u01ce\u0198\u0001\u0000\u0000\u0000\u01ce\u019e\u0001\u0000\u0000\u0000"+
		"\u01ce\u01a4\u0001\u0000\u0000\u0000\u01ce\u01a8\u0001\u0000\u0000\u0000"+
		"\u01ce\u01aa\u0001\u0000\u0000\u0000\u01ce\u01b6\u0001\u0000\u0000\u0000"+
		"\u01ce\u01be\u0001\u0000\u0000\u0000\u01ce\u01c0\u0001\u0000\u0000\u0000"+
		"\u01ce\u01c6\u0001\u0000\u0000\u0000\u01cf\u01d2\u0001\u0000\u0000\u0000"+
		"\u01d0\u01ce\u0001\u0000\u0000\u0000\u01d0\u01d1\u0001\u0000\u0000\u0000"+
		"\u01d1\u001f\u0001\u0000\u0000\u0000\u01d2\u01d0\u0001\u0000\u0000\u0000"+
		"\u01d3\u01d4\u0003&\u0013\u0000\u01d4\u01d5\u0005\u0010\u0000\u0000\u01d5"+
		"\u01d6\u0003\u001e\u000f\u0000\u01d6\u01fa\u0001\u0000\u0000\u0000\u01d7"+
		"\u01d8\u0003&\u0013\u0000\u01d8\u01d9\u0005\u0011\u0000\u0000\u01d9\u01fa"+
		"\u0001\u0000\u0000\u0000\u01da\u01db\u0003&\u0013\u0000\u01db\u01dc\u0005"+
		"\u0012\u0000\u0000\u01dc\u01fa\u0001\u0000\u0000\u0000\u01dd\u01de\u0003"+
		"&\u0013\u0000\u01de\u01df\u0005\u0013\u0000\u0000\u01df\u01e0\u0003\u001e"+
		"\u000f\u0000\u01e0\u01fa\u0001\u0000\u0000\u0000\u01e1\u01e2\u0003&\u0013"+
		"\u0000\u01e2\u01e3\u0005\u0014\u0000\u0000\u01e3\u01e4\u0003\u001e\u000f"+
		"\u0000\u01e4\u01fa\u0001\u0000\u0000\u0000\u01e5\u01e6\u0003&\u0013\u0000"+
		"\u01e6\u01e7\u0005\u0015\u0000\u0000\u01e7\u01e8\u0003\u001e\u000f\u0000"+
		"\u01e8\u01fa\u0001\u0000\u0000\u0000\u01e9\u01ea\u0003&\u0013\u0000\u01ea"+
		"\u01eb\u0005\u0016\u0000\u0000\u01eb\u01ec\u0003\u001e\u000f\u0000\u01ec"+
		"\u01fa\u0001\u0000\u0000\u0000\u01ed\u01ee\u0003&\u0013\u0000\u01ee\u01ef"+
		"\u0005\u0017\u0000\u0000\u01ef\u01f0\u0003\u001e\u000f\u0000\u01f0\u01fa"+
		"\u0001\u0000\u0000\u0000\u01f1\u01f2\u0003&\u0013\u0000\u01f2\u01f3\u0005"+
		"\u0018\u0000\u0000\u01f3\u01f4\u0003\u001e\u000f\u0000\u01f4\u01fa\u0001"+
		"\u0000\u0000\u0000\u01f5\u01f6\u0003&\u0013\u0000\u01f6\u01f7\u0005\u0019"+
		"\u0000\u0000\u01f7\u01f8\u0003\u001e\u000f\u0000\u01f8\u01fa\u0001\u0000"+
		"\u0000\u0000\u01f9\u01d3\u0001\u0000\u0000\u0000\u01f9\u01d7\u0001\u0000"+
		"\u0000\u0000\u01f9\u01da\u0001\u0000\u0000\u0000\u01f9\u01dd\u0001\u0000"+
		"\u0000\u0000\u01f9\u01e1\u0001\u0000\u0000\u0000\u01f9\u01e5\u0001\u0000"+
		"\u0000\u0000\u01f9\u01e9\u0001\u0000\u0000\u0000\u01f9\u01ed\u0001\u0000"+
		"\u0000\u0000\u01f9\u01f1\u0001\u0000\u0000\u0000\u01f9\u01f5\u0001\u0000"+
		"\u0000\u0000\u01fa!\u0001\u0000\u0000\u0000\u01fb\u01fc\u0003\b\u0004"+
		"\u0000\u01fc\u01fd\u0005\u0010\u0000\u0000\u01fd\u01fe\u0003\u001e\u000f"+
		"\u0000\u01fe#\u0001\u0000\u0000\u0000\u01ff\u0202\u0003\b\u0004\u0000"+
		"\u0200\u0202\u0003\"\u0011\u0000\u0201\u01ff\u0001\u0000\u0000\u0000\u0201"+
		"\u0200\u0001\u0000\u0000\u0000\u0202%\u0001\u0000\u0000\u0000\u0203\u020f"+
		"\u0003(\u0014\u0000\u0204\u0205\u0003(\u0014\u0000\u0205\u0206\u0005\u001f"+
		"\u0000\u0000\u0206\u0207\u0003\u001e\u000f\u0000\u0207\u0208\u0005\u001e"+
		"\u0000\u0000\u0208\u020f\u0001\u0000\u0000\u0000\u0209\u020a\u0003(\u0014"+
		"\u0000\u020a\u020b\u0005\u0006\u0000\u0000\u020b\u020c\u0003\u001e\u000f"+
		"\u0000\u020c\u020d\u0005\u0007\u0000\u0000\u020d\u020f\u0001\u0000\u0000"+
		"\u0000\u020e\u0203\u0001\u0000\u0000\u0000\u020e\u0204\u0001\u0000\u0000"+
		"\u0000\u020e\u0209\u0001\u0000\u0000\u0000\u020f\'\u0001\u0000\u0000\u0000"+
		"\u0210\u0211\u0005`\u0000\u0000\u0211)\u0001\u0000\u0000\u0000\u0212\u0218"+
		"\u0005]\u0000\u0000\u0213\u0218\u0005^\u0000\u0000\u0214\u0218\u0003,"+
		"\u0016\u0000\u0215\u0218\u0005X\u0000\u0000\u0216\u0218\u0003.\u0017\u0000"+
		"\u0217\u0212\u0001\u0000\u0000\u0000\u0217\u0213\u0001\u0000\u0000\u0000"+
		"\u0217\u0214\u0001\u0000\u0000\u0000\u0217\u0215\u0001\u0000\u0000\u0000"+
		"\u0217\u0216\u0001\u0000\u0000\u0000\u0218+\u0001\u0000\u0000\u0000\u0219"+
		"\u021c\u0005Z\u0000\u0000\u021a\u021c\u0005Y\u0000\u0000\u021b\u0219\u0001"+
		"\u0000\u0000\u0000\u021b\u021a\u0001\u0000\u0000\u0000\u021c-\u0001\u0000"+
		"\u0000\u0000\u021d\u0220\u0005;\u0000\u0000\u021e\u0220\u0005<\u0000\u0000"+
		"\u021f\u021d\u0001\u0000\u0000\u0000\u021f\u021e\u0001\u0000\u0000\u0000"+
		"\u0220/\u0001\u0000\u0000\u0000\u001d6:?ELPcnpx|\u008d\u00d8\u00dc\u00ee"+
		"\u010a\u010f\u0154\u015f\u016a\u0181\u01ce\u01d0\u01f9\u0201\u020e\u0217"+
		"\u021b\u021f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}