// Generated from C:/Users/Jordan Bunke/Documents/Java/2022/stipple-effect/antlr/ScrippleParser.g4 by ANTLR 4.13.1
package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ScrippleParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, LINE_COMMENT=2, MULTILINE_COMMENT=3, LPAREN=4, RPAREN=5, LBRACKET=6, 
		RBRACKET=7, LCURLY=8, RCURLY=9, SEMICOLON=10, COLON=11, COMMA=12, PERIOD=13, 
		PIPE=14, QUESTION=15, ASSIGN=16, INCREMENT=17, DECREMENT=18, ADD_ASSIGN=19, 
		SUB_ASSIGN=20, MUL_ASSIGN=21, DIV_ASSIGN=22, MOD_ASSIGN=23, AND_ASSIGN=24, 
		OR_ASSIGN=25, ARROW=26, EQUAL=27, NOT_EQUAL=28, GT=29, LT=30, GEQ=31, 
		LEQ=32, RAISE=33, PLUS=34, MINUS=35, TIMES=36, DIVIDE=37, MOD=38, AND=39, 
		OR=40, NOT=41, SIZE=42, IN=43, FUNC=44, FINAL=45, BOOL=46, FLOAT=47, INT=48, 
		CHAR=49, COLOR=50, IMAGE=51, STRING=52, RETURN=53, DO=54, WHILE=55, FOREACH=56, 
		FOR=57, IF=58, ELSE=59, TRUE=60, FALSE=61, NEW=62, FROM=63, RGBA=64, RGB=65, 
		BLANK=66, TEX_COL_REPL=67, RED=68, GREEN=69, BLUE=70, ALPHA=71, WIDTH=72, 
		HEIGHT=73, HAS=74, LOOKUP=75, KEYS=76, PIXEL=77, ADD=78, REMOVE=79, DEFINE=80, 
		DRAW=81, BOOL_LIT=82, FLOAT_LIT=83, DEC_LIT=84, HEX_LIT=85, CHAR_QUOTE=86, 
		STR_QUOTE=87, STRING_LIT=88, CHAR_LIT=89, ESC_CHAR=90, IDENTIFIER=91;
	public static final int
		RULE_head_rule = 0, RULE_declaration = 1, RULE_signature = 2, RULE_param_list = 3, 
		RULE_type = 4, RULE_body = 5, RULE_stat = 6, RULE_return_stat = 7, RULE_loop_stat = 8, 
		RULE_iteration_def = 9, RULE_while_def = 10, RULE_for_def = 11, RULE_if_stat = 12, 
		RULE_expr = 13, RULE_assignment = 14, RULE_var_init = 15, RULE_var_def = 16, 
		RULE_assignable = 17, RULE_ident = 18, RULE_literal = 19, RULE_int_lit = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"head_rule", "declaration", "signature", "param_list", "type", "body", 
			"stat", "return_stat", "loop_stat", "iteration_def", "while_def", "for_def", 
			"if_stat", "expr", "assignment", "var_init", "var_def", "assignable", 
			"ident", "literal", "int_lit"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'('", "')'", "'['", "']'", "'{'", "'}'", "';'", 
			"':'", "','", "'.'", "'|'", "'?'", "'='", "'++'", "'--'", "'+='", "'-='", 
			"'*='", "'/='", "'%='", "'&='", "'|='", "'->'", "'=='", "'!='", "'>'", 
			"'<'", "'>='", "'<='", "'^'", "'+'", "'-'", "'*'", "'/'", "'%'", "'&&'", 
			"'||'", "'!'", "'#'", "'in'", "'func'", "'final'", "'bool'", "'float'", 
			"'int'", "'char'", "'color'", "'image'", "'string'", "'return'", "'do'", 
			"'while'", "'foreach'", "'for'", "'if'", "'else'", "'true'", "'false'", 
			"'new'", "'from'", "'rgba'", "'rgb'", "'blank'", "'tex_col_repl'", null, 
			null, null, null, null, null, "'.has'", "'.lookup'", "'.keys'", "'.pixel'", 
			"'.add'", "'.remove'", "'.define'", "'.draw'", null, null, null, null, 
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
			"OR_ASSIGN", "ARROW", "EQUAL", "NOT_EQUAL", "GT", "LT", "GEQ", "LEQ", 
			"RAISE", "PLUS", "MINUS", "TIMES", "DIVIDE", "MOD", "AND", "OR", "NOT", 
			"SIZE", "IN", "FUNC", "FINAL", "BOOL", "FLOAT", "INT", "CHAR", "COLOR", 
			"IMAGE", "STRING", "RETURN", "DO", "WHILE", "FOREACH", "FOR", "IF", "ELSE", 
			"TRUE", "FALSE", "NEW", "FROM", "RGBA", "RGB", "BLANK", "TEX_COL_REPL", 
			"RED", "GREEN", "BLUE", "ALPHA", "WIDTH", "HEIGHT", "HAS", "LOOKUP", 
			"KEYS", "PIXEL", "ADD", "REMOVE", "DEFINE", "DRAW", "BOOL_LIT", "FLOAT_LIT", 
			"DEC_LIT", "HEX_LIT", "CHAR_QUOTE", "STR_QUOTE", "STRING_LIT", "CHAR_LIT", 
			"ESC_CHAR", "IDENTIFIER"
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
	public String getGrammarFileName() { return "ScrippleParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ScrippleParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Head_ruleContext extends ParserRuleContext {
		public TerminalNode FUNC() { return getToken(ScrippleParser.FUNC, 0); }
		public SignatureContext signature() {
			return getRuleContext(SignatureContext.class,0);
		}
		public TerminalNode LCURLY() { return getToken(ScrippleParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScrippleParser.RCURLY, 0); }
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public Head_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_head_rule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterHead_rule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitHead_rule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitHead_rule(this);
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
			setState(42);
			match(FUNC);
			setState(43);
			signature();
			setState(44);
			match(LCURLY);
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -4035253817992806064L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 188481551L) != 0)) {
				{
				{
				setState(45);
				stat();
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			match(RCURLY);
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
		public TerminalNode FINAL() { return getToken(ScrippleParser.FINAL, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINAL) {
				{
				setState(53);
				match(FINAL);
				}
			}

			setState(56);
			type(0);
			setState(57);
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
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public VoidReturnSignatureContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterVoidReturnSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitVoidReturnSignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitVoidReturnSignature(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeReturnSignatureContext extends SignatureContext {
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode ARROW() { return getToken(ScrippleParser.ARROW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public TypeReturnSignatureContext(SignatureContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterTypeReturnSignature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitTypeReturnSignature(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitTypeReturnSignature(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignatureContext signature() throws RecognitionException {
		SignatureContext _localctx = new SignatureContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_signature);
		int _la;
		try {
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new VoidReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				match(LPAREN);
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8972014882652416L) != 0)) {
					{
					setState(60);
					param_list();
					}
				}

				setState(63);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new TypeReturnSignatureContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				match(LPAREN);
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8972014882652416L) != 0)) {
					{
					setState(65);
					param_list();
					}
				}

				setState(68);
				match(ARROW);
				setState(69);
				type(0);
				setState(70);
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
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public Param_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterParam_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitParam_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitParam_list(this);
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
			setState(74);
			declaration();
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(75);
				match(COMMA);
				setState(76);
				declaration();
				}
				}
				setState(81);
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
		public TerminalNode LBRACKET() { return getToken(ScrippleParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(ScrippleParser.RBRACKET, 0); }
		public ArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolTypeContext extends TypeContext {
		public TerminalNode BOOL() { return getToken(ScrippleParser.BOOL, 0); }
		public BoolTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterBoolType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitBoolType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringTypeContext extends TypeContext {
		public TerminalNode STRING() { return getToken(ScrippleParser.STRING, 0); }
		public StringTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterStringType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitStringType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitStringType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SetTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LT() { return getToken(ScrippleParser.LT, 0); }
		public TerminalNode GT() { return getToken(ScrippleParser.GT, 0); }
		public SetTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterSetType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitSetType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitSetType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LCURLY() { return getToken(ScrippleParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScrippleParser.RCURLY, 0); }
		public ListTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterListType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitListType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitListType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorTypeContext extends TypeContext {
		public TerminalNode COLOR() { return getToken(ScrippleParser.COLOR, 0); }
		public ColorTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterColorType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitColorType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitColorType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CharTypeContext extends TypeContext {
		public TerminalNode CHAR() { return getToken(ScrippleParser.CHAR, 0); }
		public CharTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterCharType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitCharType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitCharType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MapTypeContext extends TypeContext {
		public TypeContext key;
		public TypeContext val;
		public TerminalNode LCURLY() { return getToken(ScrippleParser.LCURLY, 0); }
		public TerminalNode COLON() { return getToken(ScrippleParser.COLON, 0); }
		public TerminalNode RCURLY() { return getToken(ScrippleParser.RCURLY, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public MapTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterMapType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitMapType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitMapType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntTypeContext extends TypeContext {
		public TerminalNode INT() { return getToken(ScrippleParser.INT, 0); }
		public IntTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIntType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIntType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatTypeContext extends TypeContext {
		public TerminalNode FLOAT() { return getToken(ScrippleParser.FLOAT, 0); }
		public FloatTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterFloatType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitFloatType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitFloatType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageTypeContext extends TypeContext {
		public TerminalNode IMAGE() { return getToken(ScrippleParser.IMAGE, 0); }
		public ImageTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterImageType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitImageType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitImageType(this);
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
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
				{
				_localctx = new BoolTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(83);
				match(BOOL);
				}
				break;
			case INT:
				{
				_localctx = new IntTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(84);
				match(INT);
				}
				break;
			case FLOAT:
				{
				_localctx = new FloatTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(85);
				match(FLOAT);
				}
				break;
			case CHAR:
				{
				_localctx = new CharTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(86);
				match(CHAR);
				}
				break;
			case STRING:
				{
				_localctx = new StringTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(87);
				match(STRING);
				}
				break;
			case IMAGE:
				{
				_localctx = new ImageTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(88);
				match(IMAGE);
				}
				break;
			case COLOR:
				{
				_localctx = new ColorTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(89);
				match(COLOR);
				}
				break;
			case LCURLY:
				{
				_localctx = new MapTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90);
				match(LCURLY);
				setState(91);
				((MapTypeContext)_localctx).key = type(0);
				setState(92);
				match(COLON);
				setState(93);
				((MapTypeContext)_localctx).val = type(0);
				setState(94);
				match(RCURLY);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(109);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(107);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(98);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(99);
						match(LBRACKET);
						setState(100);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new SetTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(101);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(102);
						match(LT);
						setState(103);
						match(GT);
						}
						break;
					case 3:
						{
						_localctx = new ListTypeContext(new TypeContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_type);
						setState(104);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(105);
						match(LCURLY);
						setState(106);
						match(RCURLY);
						}
						break;
					}
					} 
				}
				setState(111);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterSingleStatBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitSingleStatBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitSingleStatBody(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComplexBodyContext extends BodyContext {
		public TerminalNode LCURLY() { return getToken(ScrippleParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScrippleParser.RCURLY, 0); }
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ComplexBodyContext(BodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterComplexBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitComplexBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitComplexBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_body);
		int _la;
		try {
			setState(121);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new SingleStatBodyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				stat();
				}
				break;
			case 2:
				_localctx = new ComplexBodyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
				match(LCURLY);
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -4035253817992806064L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 188481551L) != 0)) {
					{
					{
					setState(114);
					stat();
					}
					}
					setState(119);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(120);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RemoveFromCollectionContext extends StatContext {
		public ExprContext col;
		public ExprContext arg;
		public TerminalNode REMOVE() { return getToken(ScrippleParser.REMOVE, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RemoveFromCollectionContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterRemoveFromCollection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitRemoveFromCollection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitRemoveFromCollection(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DefineMapEntryStatementContext extends StatContext {
		public ExprContext map;
		public ExprContext key;
		public ExprContext val;
		public TerminalNode DEFINE() { return getToken(ScrippleParser.DEFINE, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScrippleParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DefineMapEntryStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterDefineMapEntryStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitDefineMapEntryStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitDefineMapEntryStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentStatementContext extends StatContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public AssignmentStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterAssignmentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitAssignmentStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DrawOntoImageStatementContext extends StatContext {
		public ExprContext canvas;
		public ExprContext img;
		public ExprContext x;
		public ExprContext y;
		public TerminalNode DRAW() { return getToken(ScrippleParser.DRAW, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DrawOntoImageStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterDrawOntoImageStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitDrawOntoImageStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitDrawOntoImageStatement(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddToCollectionContext extends StatContext {
		public ExprContext col;
		public ExprContext elem;
		public ExprContext index;
		public TerminalNode ADD() { return getToken(ScrippleParser.ADD, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(ScrippleParser.COMMA, 0); }
		public AddToCollectionContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterAddToCollection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitAddToCollection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitAddToCollection(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterLoopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitLoopStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarDefStatementContext extends StatContext {
		public Var_defContext var_def() {
			return getRuleContext(Var_defContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public VarDefStatementContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterVarDefStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitVarDefStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitVarDefStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_stat);
		int _la;
		try {
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new LoopStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(123);
				loop_stat();
				}
				break;
			case 2:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(124);
				if_stat();
				}
				break;
			case 3:
				_localctx = new VarDefStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(125);
				var_def();
				setState(126);
				match(SEMICOLON);
				}
				break;
			case 4:
				_localctx = new AssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(128);
				assignment();
				setState(129);
				match(SEMICOLON);
				}
				break;
			case 5:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(131);
				return_stat();
				}
				break;
			case 6:
				_localctx = new AddToCollectionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(132);
				((AddToCollectionContext)_localctx).col = expr(0);
				setState(133);
				match(ADD);
				setState(134);
				match(LPAREN);
				setState(135);
				((AddToCollectionContext)_localctx).elem = expr(0);
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(136);
					match(COMMA);
					setState(137);
					((AddToCollectionContext)_localctx).index = expr(0);
					}
				}

				setState(140);
				match(RPAREN);
				setState(141);
				match(SEMICOLON);
				}
				break;
			case 7:
				_localctx = new RemoveFromCollectionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(143);
				((RemoveFromCollectionContext)_localctx).col = expr(0);
				setState(144);
				match(REMOVE);
				setState(145);
				match(LPAREN);
				setState(146);
				((RemoveFromCollectionContext)_localctx).arg = expr(0);
				setState(147);
				match(RPAREN);
				setState(148);
				match(SEMICOLON);
				}
				break;
			case 8:
				_localctx = new DefineMapEntryStatementContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(150);
				((DefineMapEntryStatementContext)_localctx).map = expr(0);
				setState(151);
				match(DEFINE);
				setState(152);
				match(LPAREN);
				setState(153);
				((DefineMapEntryStatementContext)_localctx).key = expr(0);
				setState(154);
				match(COMMA);
				setState(155);
				((DefineMapEntryStatementContext)_localctx).val = expr(0);
				setState(156);
				match(RPAREN);
				setState(157);
				match(SEMICOLON);
				}
				break;
			case 9:
				_localctx = new DrawOntoImageStatementContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(159);
				((DrawOntoImageStatementContext)_localctx).canvas = expr(0);
				setState(160);
				match(DRAW);
				setState(161);
				match(LPAREN);
				setState(162);
				((DrawOntoImageStatementContext)_localctx).img = expr(0);
				setState(163);
				match(COMMA);
				setState(164);
				((DrawOntoImageStatementContext)_localctx).x = expr(0);
				setState(165);
				match(COMMA);
				setState(166);
				((DrawOntoImageStatementContext)_localctx).y = expr(0);
				setState(167);
				match(RPAREN);
				setState(168);
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
		public TerminalNode RETURN() { return getToken(ScrippleParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Return_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterReturn_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitReturn_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitReturn_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Return_statContext return_stat() throws RecognitionException {
		Return_statContext _localctx = new Return_statContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_return_stat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(RETURN);
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -4611679385924140720L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 188481551L) != 0)) {
				{
				setState(173);
				expr(0);
				}
			}

			setState(176);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterForLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitForLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitForLoop(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIteratorLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIteratorLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIteratorLoop(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoWhileLoopContext extends Loop_statContext {
		public TerminalNode DO() { return getToken(ScrippleParser.DO, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public While_defContext while_def() {
			return getRuleContext(While_defContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ScrippleParser.SEMICOLON, 0); }
		public DoWhileLoopContext(Loop_statContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterDoWhileLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitDoWhileLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitDoWhileLoop(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterWhileLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitWhileLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Loop_statContext loop_stat() throws RecognitionException {
		Loop_statContext _localctx = new Loop_statContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_loop_stat);
		try {
			setState(192);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
				_localctx = new WhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				while_def();
				setState(179);
				body();
				}
				break;
			case FOREACH:
				_localctx = new IteratorLoopContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(181);
				iteration_def();
				setState(182);
				body();
				}
				break;
			case FOR:
				_localctx = new ForLoopContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(184);
				for_def();
				setState(185);
				body();
				}
				break;
			case DO:
				_localctx = new DoWhileLoopContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(187);
				match(DO);
				setState(188);
				body();
				setState(189);
				while_def();
				setState(190);
				match(SEMICOLON);
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
	public static class Iteration_defContext extends ParserRuleContext {
		public TerminalNode FOREACH() { return getToken(ScrippleParser.FOREACH, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public TerminalNode IN() { return getToken(ScrippleParser.IN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public Iteration_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteration_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIteration_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIteration_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIteration_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Iteration_defContext iteration_def() throws RecognitionException {
		Iteration_defContext _localctx = new Iteration_defContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_iteration_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(FOREACH);
			setState(195);
			match(LPAREN);
			setState(196);
			declaration();
			setState(197);
			match(IN);
			setState(198);
			expr(0);
			setState(199);
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
		public TerminalNode WHILE() { return getToken(ScrippleParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public While_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterWhile_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitWhile_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitWhile_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_defContext while_def() throws RecognitionException {
		While_defContext _localctx = new While_defContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_while_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(WHILE);
			setState(202);
			match(LPAREN);
			setState(203);
			expr(0);
			setState(204);
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
		public TerminalNode FOR() { return getToken(ScrippleParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public Var_initContext var_init() {
			return getRuleContext(Var_initContext.class,0);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(ScrippleParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(ScrippleParser.SEMICOLON, i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public For_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterFor_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitFor_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitFor_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final For_defContext for_def() throws RecognitionException {
		For_defContext _localctx = new For_defContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_for_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(FOR);
			setState(207);
			match(LPAREN);
			setState(208);
			var_init();
			setState(209);
			match(SEMICOLON);
			setState(210);
			expr(0);
			setState(211);
			match(SEMICOLON);
			setState(212);
			assignment();
			setState(213);
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
		public BodyContext ifBody;
		public BodyContext elseBody;
		public TerminalNode IF() { return getToken(ScrippleParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public List<TerminalNode> ELSE() { return getTokens(ScrippleParser.ELSE); }
		public TerminalNode ELSE(int i) {
			return getToken(ScrippleParser.ELSE, i);
		}
		public List<If_statContext> if_stat() {
			return getRuleContexts(If_statContext.class);
		}
		public If_statContext if_stat(int i) {
			return getRuleContext(If_statContext.class,i);
		}
		public If_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIf_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIf_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIf_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_statContext if_stat() throws RecognitionException {
		If_statContext _localctx = new If_statContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_if_stat);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			match(IF);
			setState(216);
			match(LPAREN);
			setState(217);
			expr(0);
			setState(218);
			match(RPAREN);
			setState(219);
			((If_statContext)_localctx).ifBody = body();
			setState(224);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(220);
					match(ELSE);
					setState(221);
					if_stat();
					}
					} 
				}
				setState(226);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			setState(229);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(227);
				match(ELSE);
				setState(228);
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
		public TerminalNode OR() { return getToken(ScrippleParser.OR, 0); }
		public TerminalNode AND() { return getToken(ScrippleParser.AND, 0); }
		public LogicBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterLogicBinExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitLogicBinExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitLogicBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TernaryExpressionContext extends ExprContext {
		public ExprContext cond;
		public ExprContext if_;
		public ExprContext else_;
		public TerminalNode QUESTION() { return getToken(ScrippleParser.QUESTION, 0); }
		public TerminalNode COLON() { return getToken(ScrippleParser.COLON, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TernaryExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterTernaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitTernaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitTernaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitArrayExpressionContext extends ExprContext {
		public TerminalNode LBRACKET() { return getToken(ScrippleParser.LBRACKET, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RBRACKET() { return getToken(ScrippleParser.RBRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public ExplicitArrayExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterExplicitArrayExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitExplicitArrayExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitExplicitArrayExpression(this);
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
		public TerminalNode TIMES() { return getToken(ScrippleParser.TIMES, 0); }
		public TerminalNode DIVIDE() { return getToken(ScrippleParser.DIVIDE, 0); }
		public TerminalNode MOD() { return getToken(ScrippleParser.MOD, 0); }
		public MultBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterMultBinExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitMultBinExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitMultBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageBoundExpressionContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode WIDTH() { return getToken(ScrippleParser.WIDTH, 0); }
		public TerminalNode HEIGHT() { return getToken(ScrippleParser.HEIGHT, 0); }
		public ImageBoundExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterImageBoundExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitImageBoundExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitImageBoundExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExpressionContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(ScrippleParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(ScrippleParser.NOT, 0); }
		public TerminalNode SIZE() { return getToken(ScrippleParser.SIZE, 0); }
		public UnaryExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MapLookupExpressionContext extends ExprContext {
		public ExprContext map;
		public ExprContext elem;
		public TerminalNode LOOKUP() { return getToken(ScrippleParser.LOOKUP, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public MapLookupExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterMapLookupExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitMapLookupExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitMapLookupExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PowerBinExpressionContext extends ExprContext {
		public ExprContext a;
		public ExprContext b;
		public TerminalNode RAISE() { return getToken(ScrippleParser.RAISE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public PowerBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterPowerBinExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitPowerBinExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitPowerBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitListExpressionContext extends ExprContext {
		public TerminalNode LT() { return getToken(ScrippleParser.LT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode GT() { return getToken(ScrippleParser.GT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public ExplicitListExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterExplicitListExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitExplicitListExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitExplicitListExpression(this);
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
		public TerminalNode EQUAL() { return getToken(ScrippleParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ScrippleParser.NOT_EQUAL, 0); }
		public TerminalNode GT() { return getToken(ScrippleParser.GT, 0); }
		public TerminalNode LT() { return getToken(ScrippleParser.LT, 0); }
		public TerminalNode GEQ() { return getToken(ScrippleParser.GEQ, 0); }
		public TerminalNode LEQ() { return getToken(ScrippleParser.LEQ, 0); }
		public ComparisonBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterComparisonBinExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitComparisonBinExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitComparisonBinExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RGBColorExpressionContext extends ExprContext {
		public ExprContext r;
		public ExprContext g;
		public ExprContext b;
		public TerminalNode RGB() { return getToken(ScrippleParser.RGB, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RGBColorExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterRGBColorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitRGBColorExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitRGBColorExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageOfBoundsExpressionContext extends ExprContext {
		public ExprContext width;
		public ExprContext height;
		public TerminalNode BLANK() { return getToken(ScrippleParser.BLANK, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScrippleParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ImageOfBoundsExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterImageOfBoundsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitImageOfBoundsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitImageOfBoundsExpression(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterAssignableExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitAssignableExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitAssignableExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExplicitSetExpressionContext extends ExprContext {
		public TerminalNode LCURLY() { return getToken(ScrippleParser.LCURLY, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RCURLY() { return getToken(ScrippleParser.RCURLY, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public ExplicitSetExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterExplicitSetExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitExplicitSetExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitExplicitSetExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewMapExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScrippleParser.NEW, 0); }
		public TerminalNode LCURLY() { return getToken(ScrippleParser.LCURLY, 0); }
		public TerminalNode COLON() { return getToken(ScrippleParser.COLON, 0); }
		public TerminalNode RCURLY() { return getToken(ScrippleParser.RCURLY, 0); }
		public NewMapExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterNewMapExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitNewMapExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitNewMapExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RGBAColorExpressionContext extends ExprContext {
		public ExprContext r;
		public ExprContext g;
		public ExprContext b;
		public ExprContext a;
		public TerminalNode RGBA() { return getToken(ScrippleParser.RGBA, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RGBAColorExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterRGBAColorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitRGBAColorExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitRGBAColorExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewSetExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScrippleParser.NEW, 0); }
		public TerminalNode LCURLY() { return getToken(ScrippleParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(ScrippleParser.RCURLY, 0); }
		public NewSetExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterNewSetExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitNewSetExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitNewSetExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ContainsExpressionContext extends ExprContext {
		public ExprContext col;
		public ExprContext elem;
		public TerminalNode HAS() { return getToken(ScrippleParser.HAS, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ContainsExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterContainsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitContainsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitContainsExpression(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterLiteralExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitLiteralExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewListExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScrippleParser.NEW, 0); }
		public TerminalNode LT() { return getToken(ScrippleParser.LT, 0); }
		public TerminalNode GT() { return getToken(ScrippleParser.GT, 0); }
		public NewListExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterNewListExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitNewListExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitNewListExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MapKeysetExpressionContext extends ExprContext {
		public ExprContext map;
		public TerminalNode KEYS() { return getToken(ScrippleParser.KEYS, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MapKeysetExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterMapKeysetExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitMapKeysetExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitMapKeysetExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TextureColorReplaceExpressionContext extends ExprContext {
		public ExprContext texture;
		public ExprContext lookup;
		public ExprContext replace;
		public TerminalNode TEX_COL_REPL() { return getToken(ScrippleParser.TEX_COL_REPL, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(ScrippleParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ScrippleParser.COMMA, i);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TextureColorReplaceExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterTextureColorReplaceExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitTextureColorReplaceExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitTextureColorReplaceExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NestedExpressionContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public NestedExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterNestedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitNestedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitNestedExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorAtPixelExpressionContext extends ExprContext {
		public ExprContext img;
		public ExprContext x;
		public ExprContext y;
		public TerminalNode PIXEL() { return getToken(ScrippleParser.PIXEL, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(ScrippleParser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ColorAtPixelExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterColorAtPixelExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitColorAtPixelExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitColorAtPixelExpression(this);
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
		public TerminalNode PLUS() { return getToken(ScrippleParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(ScrippleParser.MINUS, 0); }
		public ArithmeticBinExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterArithmeticBinExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitArithmeticBinExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitArithmeticBinExpression(this);
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
		public TerminalNode RED() { return getToken(ScrippleParser.RED, 0); }
		public TerminalNode GREEN() { return getToken(ScrippleParser.GREEN, 0); }
		public TerminalNode BLUE() { return getToken(ScrippleParser.BLUE, 0); }
		public TerminalNode ALPHA() { return getToken(ScrippleParser.ALPHA, 0); }
		public ColorChannelExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterColorChannelExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitColorChannelExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitColorChannelExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayExpressionContext extends ExprContext {
		public TerminalNode NEW() { return getToken(ScrippleParser.NEW, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(ScrippleParser.LBRACKET, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(ScrippleParser.RBRACKET, 0); }
		public NewArrayExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterNewArrayExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitNewArrayExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitNewArrayExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImageFromPathExpressionContext extends ExprContext {
		public TerminalNode FROM() { return getToken(ScrippleParser.FROM, 0); }
		public TerminalNode LPAREN() { return getToken(ScrippleParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ScrippleParser.RPAREN, 0); }
		public ImageFromPathExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterImageFromPathExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitImageFromPathExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitImageFromPathExpression(this);
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
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				_localctx = new NestedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(232);
				match(LPAREN);
				setState(233);
				expr(0);
				setState(234);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new UnaryExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				((UnaryExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 6631429505024L) != 0)) ) {
					((UnaryExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(237);
				expr(27);
				}
				break;
			case 3:
				{
				_localctx = new ImageFromPathExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(238);
				match(FROM);
				setState(239);
				match(LPAREN);
				setState(240);
				expr(0);
				setState(241);
				match(RPAREN);
				}
				break;
			case 4:
				{
				_localctx = new ImageOfBoundsExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(243);
				match(BLANK);
				setState(244);
				match(LPAREN);
				setState(245);
				((ImageOfBoundsExpressionContext)_localctx).width = expr(0);
				setState(246);
				match(COMMA);
				setState(247);
				((ImageOfBoundsExpressionContext)_localctx).height = expr(0);
				setState(248);
				match(RPAREN);
				}
				break;
			case 5:
				{
				_localctx = new TextureColorReplaceExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(250);
				match(TEX_COL_REPL);
				setState(251);
				match(LPAREN);
				setState(252);
				((TextureColorReplaceExpressionContext)_localctx).texture = expr(0);
				setState(253);
				match(COMMA);
				setState(254);
				((TextureColorReplaceExpressionContext)_localctx).lookup = expr(0);
				setState(255);
				match(COMMA);
				setState(256);
				((TextureColorReplaceExpressionContext)_localctx).replace = expr(0);
				setState(257);
				match(RPAREN);
				}
				break;
			case 6:
				{
				_localctx = new RGBColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(259);
				match(RGB);
				setState(260);
				match(LPAREN);
				setState(261);
				((RGBColorExpressionContext)_localctx).r = expr(0);
				setState(262);
				match(COMMA);
				setState(263);
				((RGBColorExpressionContext)_localctx).g = expr(0);
				setState(264);
				match(COMMA);
				setState(265);
				((RGBColorExpressionContext)_localctx).b = expr(0);
				setState(266);
				match(RPAREN);
				}
				break;
			case 7:
				{
				_localctx = new RGBAColorExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(268);
				match(RGBA);
				setState(269);
				match(LPAREN);
				setState(270);
				((RGBAColorExpressionContext)_localctx).r = expr(0);
				setState(271);
				match(COMMA);
				setState(272);
				((RGBAColorExpressionContext)_localctx).g = expr(0);
				setState(273);
				match(COMMA);
				setState(274);
				((RGBAColorExpressionContext)_localctx).b = expr(0);
				setState(275);
				match(COMMA);
				setState(276);
				((RGBAColorExpressionContext)_localctx).a = expr(0);
				setState(277);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new ExplicitArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(279);
				match(LBRACKET);
				setState(280);
				expr(0);
				setState(285);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(281);
					match(COMMA);
					setState(282);
					expr(0);
					}
					}
					setState(287);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(288);
				match(RBRACKET);
				}
				break;
			case 9:
				{
				_localctx = new ExplicitListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(290);
				match(LT);
				setState(291);
				expr(0);
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(292);
					match(COMMA);
					setState(293);
					expr(0);
					}
					}
					setState(298);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(299);
				match(GT);
				}
				break;
			case 10:
				{
				_localctx = new ExplicitSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(301);
				match(LCURLY);
				setState(302);
				expr(0);
				setState(307);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(303);
					match(COMMA);
					setState(304);
					expr(0);
					}
					}
					setState(309);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(310);
				match(RCURLY);
				}
				break;
			case 11:
				{
				_localctx = new NewArrayExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(312);
				match(NEW);
				setState(313);
				type(0);
				setState(314);
				match(LBRACKET);
				setState(315);
				expr(0);
				setState(316);
				match(RBRACKET);
				}
				break;
			case 12:
				{
				_localctx = new NewListExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(318);
				match(NEW);
				setState(319);
				match(LT);
				setState(320);
				match(GT);
				}
				break;
			case 13:
				{
				_localctx = new NewSetExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(321);
				match(NEW);
				setState(322);
				match(LCURLY);
				setState(323);
				match(RCURLY);
				}
				break;
			case 14:
				{
				_localctx = new NewMapExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(324);
				match(NEW);
				setState(325);
				match(LCURLY);
				setState(326);
				match(COLON);
				setState(327);
				match(RCURLY);
				}
				break;
			case 15:
				{
				_localctx = new AssignableExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(328);
				assignable();
				}
				break;
			case 16:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(329);
				literal();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(383);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(381);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ArithmeticBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(332);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(333);
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
						setState(334);
						((ArithmeticBinExpressionContext)_localctx).b = expr(27);
						}
						break;
					case 2:
						{
						_localctx = new MultBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((MultBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(335);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(336);
						((MultBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 481036337152L) != 0)) ) {
							((MultBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(337);
						((MultBinExpressionContext)_localctx).b = expr(26);
						}
						break;
					case 3:
						{
						_localctx = new PowerBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((PowerBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(338);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(339);
						match(RAISE);
						setState(340);
						((PowerBinExpressionContext)_localctx).b = expr(25);
						}
						break;
					case 4:
						{
						_localctx = new ComparisonBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((ComparisonBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(341);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(342);
						((ComparisonBinExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8455716864L) != 0)) ) {
							((ComparisonBinExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(343);
						((ComparisonBinExpressionContext)_localctx).b = expr(24);
						}
						break;
					case 5:
						{
						_localctx = new LogicBinExpressionContext(new ExprContext(_parentctx, _parentState));
						((LogicBinExpressionContext)_localctx).a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(344);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(345);
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
						setState(346);
						((LogicBinExpressionContext)_localctx).b = expr(23);
						}
						break;
					case 6:
						{
						_localctx = new TernaryExpressionContext(new ExprContext(_parentctx, _parentState));
						((TernaryExpressionContext)_localctx).cond = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(347);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(348);
						match(QUESTION);
						setState(349);
						((TernaryExpressionContext)_localctx).if_ = expr(0);
						setState(350);
						match(COLON);
						setState(351);
						((TernaryExpressionContext)_localctx).else_ = expr(22);
						}
						break;
					case 7:
						{
						_localctx = new ContainsExpressionContext(new ExprContext(_parentctx, _parentState));
						((ContainsExpressionContext)_localctx).col = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(353);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(354);
						match(HAS);
						setState(355);
						match(LPAREN);
						setState(356);
						((ContainsExpressionContext)_localctx).elem = expr(0);
						setState(357);
						match(RPAREN);
						}
						break;
					case 8:
						{
						_localctx = new MapLookupExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapLookupExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(359);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(360);
						match(LOOKUP);
						setState(361);
						match(LPAREN);
						setState(362);
						((MapLookupExpressionContext)_localctx).elem = expr(0);
						setState(363);
						match(RPAREN);
						}
						break;
					case 9:
						{
						_localctx = new MapKeysetExpressionContext(new ExprContext(_parentctx, _parentState));
						((MapKeysetExpressionContext)_localctx).map = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(365);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(366);
						match(KEYS);
						setState(367);
						match(LPAREN);
						setState(368);
						match(RPAREN);
						}
						break;
					case 10:
						{
						_localctx = new ColorChannelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorChannelExpressionContext)_localctx).c = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(369);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(370);
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
						_localctx = new ColorAtPixelExpressionContext(new ExprContext(_parentctx, _parentState));
						((ColorAtPixelExpressionContext)_localctx).img = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(371);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(372);
						match(PIXEL);
						setState(373);
						match(LPAREN);
						setState(374);
						((ColorAtPixelExpressionContext)_localctx).x = expr(0);
						setState(375);
						match(COMMA);
						setState(376);
						((ColorAtPixelExpressionContext)_localctx).y = expr(0);
						setState(377);
						match(RPAREN);
						}
						break;
					case 12:
						{
						_localctx = new ImageBoundExpressionContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(379);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(380);
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
					}
					} 
				}
				setState(385);
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
		public TerminalNode ASSIGN() { return getToken(ScrippleParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StandardAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterStandardAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitStandardAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitStandardAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode ADD_ASSIGN() { return getToken(ScrippleParser.ADD_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AddAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterAddAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitAddAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitAddAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DivAssignmnetContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode DIV_ASSIGN() { return getToken(ScrippleParser.DIV_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DivAssignmnetContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterDivAssignmnet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitDivAssignmnet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitDivAssignmnet(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IncrementAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode INCREMENT() { return getToken(ScrippleParser.INCREMENT, 0); }
		public IncrementAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIncrementAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIncrementAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIncrementAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode OR_ASSIGN() { return getToken(ScrippleParser.OR_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public OrAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterOrAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitOrAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitOrAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode SUB_ASSIGN() { return getToken(ScrippleParser.SUB_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SubAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterSubAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitSubAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitSubAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode AND_ASSIGN() { return getToken(ScrippleParser.AND_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AndAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterAndAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitAndAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitAndAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DecrementAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode DECREMENT() { return getToken(ScrippleParser.DECREMENT, 0); }
		public DecrementAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterDecrementAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitDecrementAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitDecrementAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode MUL_ASSIGN() { return getToken(ScrippleParser.MUL_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MultAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterMultAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitMultAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitMultAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ModAssignmentContext extends AssignmentContext {
		public AssignableContext assignable() {
			return getRuleContext(AssignableContext.class,0);
		}
		public TerminalNode MOD_ASSIGN() { return getToken(ScrippleParser.MOD_ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ModAssignmentContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterModAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitModAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitModAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_assignment);
		try {
			setState(424);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new StandardAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(386);
				assignable();
				setState(387);
				match(ASSIGN);
				setState(388);
				expr(0);
				}
				break;
			case 2:
				_localctx = new IncrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(390);
				assignable();
				setState(391);
				match(INCREMENT);
				}
				break;
			case 3:
				_localctx = new DecrementAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(393);
				assignable();
				setState(394);
				match(DECREMENT);
				}
				break;
			case 4:
				_localctx = new AddAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(396);
				assignable();
				setState(397);
				match(ADD_ASSIGN);
				setState(398);
				expr(0);
				}
				break;
			case 5:
				_localctx = new SubAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(400);
				assignable();
				setState(401);
				match(SUB_ASSIGN);
				setState(402);
				expr(0);
				}
				break;
			case 6:
				_localctx = new MultAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(404);
				assignable();
				setState(405);
				match(MUL_ASSIGN);
				setState(406);
				expr(0);
				}
				break;
			case 7:
				_localctx = new DivAssignmnetContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(408);
				assignable();
				setState(409);
				match(DIV_ASSIGN);
				setState(410);
				expr(0);
				}
				break;
			case 8:
				_localctx = new ModAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(412);
				assignable();
				setState(413);
				match(MOD_ASSIGN);
				setState(414);
				expr(0);
				}
				break;
			case 9:
				_localctx = new AndAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(416);
				assignable();
				setState(417);
				match(AND_ASSIGN);
				setState(418);
				expr(0);
				}
				break;
			case 10:
				_localctx = new OrAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(420);
				assignable();
				setState(421);
				match(OR_ASSIGN);
				setState(422);
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
		public TerminalNode ASSIGN() { return getToken(ScrippleParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Var_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterVar_init(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitVar_init(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitVar_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_initContext var_init() throws RecognitionException {
		Var_initContext _localctx = new Var_initContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_var_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			declaration();
			setState(427);
			match(ASSIGN);
			setState(428);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterExplicitVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitExplicitVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitExplicitVarDef(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterImplicitVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitImplicitVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitImplicitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_defContext var_def() throws RecognitionException {
		Var_defContext _localctx = new Var_defContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_var_def);
		try {
			setState(432);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new ImplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(430);
				declaration();
				}
				break;
			case 2:
				_localctx = new ExplicitVarDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(431);
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
		public TerminalNode LBRACKET() { return getToken(ScrippleParser.LBRACKET, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(ScrippleParser.RBRACKET, 0); }
		public ArrayAssignableContext(AssignableContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterArrayAssignable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitArrayAssignable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitArrayAssignable(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterSimpleAssignable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitSimpleAssignable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitSimpleAssignable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListAssignableContext extends AssignableContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode LT() { return getToken(ScrippleParser.LT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode GT() { return getToken(ScrippleParser.GT, 0); }
		public ListAssignableContext(AssignableContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterListAssignable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitListAssignable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitListAssignable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignableContext assignable() throws RecognitionException {
		AssignableContext _localctx = new AssignableContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_assignable);
		try {
			setState(445);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				_localctx = new SimpleAssignableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(434);
				ident();
				}
				break;
			case 2:
				_localctx = new ListAssignableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(435);
				ident();
				setState(436);
				match(LT);
				setState(437);
				expr(0);
				setState(438);
				match(GT);
				}
				break;
			case 3:
				_localctx = new ArrayAssignableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(440);
				ident();
				setState(441);
				match(LBRACKET);
				setState(442);
				expr(0);
				setState(443);
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
		public TerminalNode IDENTIFIER() { return getToken(ScrippleParser.IDENTIFIER, 0); }
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIdent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIdent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447);
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
		public TerminalNode STRING_LIT() { return getToken(ScrippleParser.STRING_LIT, 0); }
		public StringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CharLiteralContext extends LiteralContext {
		public TerminalNode CHAR_LIT() { return getToken(ScrippleParser.CHAR_LIT, 0); }
		public CharLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterCharLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitCharLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitCharLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolLiteralContext extends LiteralContext {
		public TerminalNode BOOL_LIT() { return getToken(ScrippleParser.BOOL_LIT, 0); }
		public BoolLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterBoolLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitBoolLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitBoolLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatLiteralContext extends LiteralContext {
		public TerminalNode FLOAT_LIT() { return getToken(ScrippleParser.FLOAT_LIT, 0); }
		public FloatLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterFloatLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitFloatLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitFloatLiteral(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_literal);
		try {
			setState(454);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_LIT:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(449);
				match(STRING_LIT);
				}
				break;
			case CHAR_LIT:
				_localctx = new CharLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(450);
				match(CHAR_LIT);
				}
				break;
			case DEC_LIT:
			case HEX_LIT:
				_localctx = new IntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(451);
				int_lit();
				}
				break;
			case FLOAT_LIT:
				_localctx = new FloatLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(452);
				match(FLOAT_LIT);
				}
				break;
			case BOOL_LIT:
				_localctx = new BoolLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(453);
				match(BOOL_LIT);
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
		public TerminalNode HEX_LIT() { return getToken(ScrippleParser.HEX_LIT, 0); }
		public HexadecimalContext(Int_litContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterHexadecimal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitHexadecimal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitHexadecimal(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DecimalContext extends Int_litContext {
		public TerminalNode DEC_LIT() { return getToken(ScrippleParser.DEC_LIT, 0); }
		public DecimalContext(Int_litContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).enterDecimal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ScrippleParserListener ) ((ScrippleParserListener)listener).exitDecimal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ScrippleParserVisitor ) return ((ScrippleParserVisitor<? extends T>)visitor).visitDecimal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Int_litContext int_lit() throws RecognitionException {
		Int_litContext _localctx = new Int_litContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_int_lit);
		try {
			setState(458);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HEX_LIT:
				_localctx = new HexadecimalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(456);
				match(HEX_LIT);
				}
				break;
			case DEC_LIT:
				_localctx = new DecimalContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(457);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 13:
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
			return precpred(_ctx, 26);
		case 4:
			return precpred(_ctx, 25);
		case 5:
			return precpred(_ctx, 24);
		case 6:
			return precpred(_ctx, 23);
		case 7:
			return precpred(_ctx, 22);
		case 8:
			return precpred(_ctx, 21);
		case 9:
			return precpred(_ctx, 20);
		case 10:
			return precpred(_ctx, 19);
		case 11:
			return precpred(_ctx, 18);
		case 12:
			return precpred(_ctx, 17);
		case 13:
			return precpred(_ctx, 13);
		case 14:
			return precpred(_ctx, 12);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001[\u01cd\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000/\b\u0000\n\u0000\f\u00002\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0003\u00017\b\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0003\u0002>\b\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002C\b\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002I\b\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u0003N\b\u0003\n\u0003\f\u0003Q\t\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004a\b\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0005\u0004l\b\u0004\n\u0004\f\u0004o\t\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0005\u0005t\b\u0005\n\u0005\f\u0005w\t\u0005"+
		"\u0001\u0005\u0003\u0005z\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0003\u0006\u008b\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0003\u0006\u00ab\b\u0006\u0001\u0007\u0001\u0007"+
		"\u0003\u0007\u00af\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u00c1\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0005\f\u00df\b\f\n\f\f\f\u00e2\t\f\u0001\f\u0001\f\u0003\f"+
		"\u00e6\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005"+
		"\r\u011c\b\r\n\r\f\r\u011f\t\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r"+
		"\u0001\r\u0005\r\u0127\b\r\n\r\f\r\u012a\t\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0005\r\u0132\b\r\n\r\f\r\u0135\t\r\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0003\r\u014b\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u017e\b\r\n"+
		"\r\f\r\u0181\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u01a9\b\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0003"+
		"\u0010\u01b1\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0003\u0011\u01be\b\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u01c7\b\u0013\u0001"+
		"\u0014\u0001\u0014\u0003\u0014\u01cb\b\u0014\u0001\u0014\u0000\u0002\b"+
		"\u001a\u0015\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(\u0000\u0007\u0002\u0000##)*\u0001\u0000"+
		"\"#\u0001\u0000$&\u0001\u0000\u001b \u0001\u0000\'(\u0001\u0000DG\u0001"+
		"\u0000HI\u0207\u0000*\u0001\u0000\u0000\u0000\u00026\u0001\u0000\u0000"+
		"\u0000\u0004H\u0001\u0000\u0000\u0000\u0006J\u0001\u0000\u0000\u0000\b"+
		"`\u0001\u0000\u0000\u0000\ny\u0001\u0000\u0000\u0000\f\u00aa\u0001\u0000"+
		"\u0000\u0000\u000e\u00ac\u0001\u0000\u0000\u0000\u0010\u00c0\u0001\u0000"+
		"\u0000\u0000\u0012\u00c2\u0001\u0000\u0000\u0000\u0014\u00c9\u0001\u0000"+
		"\u0000\u0000\u0016\u00ce\u0001\u0000\u0000\u0000\u0018\u00d7\u0001\u0000"+
		"\u0000\u0000\u001a\u014a\u0001\u0000\u0000\u0000\u001c\u01a8\u0001\u0000"+
		"\u0000\u0000\u001e\u01aa\u0001\u0000\u0000\u0000 \u01b0\u0001\u0000\u0000"+
		"\u0000\"\u01bd\u0001\u0000\u0000\u0000$\u01bf\u0001\u0000\u0000\u0000"+
		"&\u01c6\u0001\u0000\u0000\u0000(\u01ca\u0001\u0000\u0000\u0000*+\u0005"+
		",\u0000\u0000+,\u0003\u0004\u0002\u0000,0\u0005\b\u0000\u0000-/\u0003"+
		"\f\u0006\u0000.-\u0001\u0000\u0000\u0000/2\u0001\u0000\u0000\u00000.\u0001"+
		"\u0000\u0000\u000001\u0001\u0000\u0000\u000013\u0001\u0000\u0000\u0000"+
		"20\u0001\u0000\u0000\u000034\u0005\t\u0000\u00004\u0001\u0001\u0000\u0000"+
		"\u000057\u0005-\u0000\u000065\u0001\u0000\u0000\u000067\u0001\u0000\u0000"+
		"\u000078\u0001\u0000\u0000\u000089\u0003\b\u0004\u00009:\u0003$\u0012"+
		"\u0000:\u0003\u0001\u0000\u0000\u0000;=\u0005\u0004\u0000\u0000<>\u0003"+
		"\u0006\u0003\u0000=<\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000"+
		">?\u0001\u0000\u0000\u0000?I\u0005\u0005\u0000\u0000@B\u0005\u0004\u0000"+
		"\u0000AC\u0003\u0006\u0003\u0000BA\u0001\u0000\u0000\u0000BC\u0001\u0000"+
		"\u0000\u0000CD\u0001\u0000\u0000\u0000DE\u0005\u001a\u0000\u0000EF\u0003"+
		"\b\u0004\u0000FG\u0005\u0005\u0000\u0000GI\u0001\u0000\u0000\u0000H;\u0001"+
		"\u0000\u0000\u0000H@\u0001\u0000\u0000\u0000I\u0005\u0001\u0000\u0000"+
		"\u0000JO\u0003\u0002\u0001\u0000KL\u0005\f\u0000\u0000LN\u0003\u0002\u0001"+
		"\u0000MK\u0001\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000"+
		"\u0000\u0000OP\u0001\u0000\u0000\u0000P\u0007\u0001\u0000\u0000\u0000"+
		"QO\u0001\u0000\u0000\u0000RS\u0006\u0004\uffff\uffff\u0000Sa\u0005.\u0000"+
		"\u0000Ta\u00050\u0000\u0000Ua\u0005/\u0000\u0000Va\u00051\u0000\u0000"+
		"Wa\u00054\u0000\u0000Xa\u00053\u0000\u0000Ya\u00052\u0000\u0000Z[\u0005"+
		"\b\u0000\u0000[\\\u0003\b\u0004\u0000\\]\u0005\u000b\u0000\u0000]^\u0003"+
		"\b\u0004\u0000^_\u0005\t\u0000\u0000_a\u0001\u0000\u0000\u0000`R\u0001"+
		"\u0000\u0000\u0000`T\u0001\u0000\u0000\u0000`U\u0001\u0000\u0000\u0000"+
		"`V\u0001\u0000\u0000\u0000`W\u0001\u0000\u0000\u0000`X\u0001\u0000\u0000"+
		"\u0000`Y\u0001\u0000\u0000\u0000`Z\u0001\u0000\u0000\u0000am\u0001\u0000"+
		"\u0000\u0000bc\n\u0004\u0000\u0000cd\u0005\u0006\u0000\u0000dl\u0005\u0007"+
		"\u0000\u0000ef\n\u0003\u0000\u0000fg\u0005\u001e\u0000\u0000gl\u0005\u001d"+
		"\u0000\u0000hi\n\u0002\u0000\u0000ij\u0005\b\u0000\u0000jl\u0005\t\u0000"+
		"\u0000kb\u0001\u0000\u0000\u0000ke\u0001\u0000\u0000\u0000kh\u0001\u0000"+
		"\u0000\u0000lo\u0001\u0000\u0000\u0000mk\u0001\u0000\u0000\u0000mn\u0001"+
		"\u0000\u0000\u0000n\t\u0001\u0000\u0000\u0000om\u0001\u0000\u0000\u0000"+
		"pz\u0003\f\u0006\u0000qu\u0005\b\u0000\u0000rt\u0003\f\u0006\u0000sr\u0001"+
		"\u0000\u0000\u0000tw\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000"+
		"uv\u0001\u0000\u0000\u0000vx\u0001\u0000\u0000\u0000wu\u0001\u0000\u0000"+
		"\u0000xz\u0005\t\u0000\u0000yp\u0001\u0000\u0000\u0000yq\u0001\u0000\u0000"+
		"\u0000z\u000b\u0001\u0000\u0000\u0000{\u00ab\u0003\u0010\b\u0000|\u00ab"+
		"\u0003\u0018\f\u0000}~\u0003 \u0010\u0000~\u007f\u0005\n\u0000\u0000\u007f"+
		"\u00ab\u0001\u0000\u0000\u0000\u0080\u0081\u0003\u001c\u000e\u0000\u0081"+
		"\u0082\u0005\n\u0000\u0000\u0082\u00ab\u0001\u0000\u0000\u0000\u0083\u00ab"+
		"\u0003\u000e\u0007\u0000\u0084\u0085\u0003\u001a\r\u0000\u0085\u0086\u0005"+
		"N\u0000\u0000\u0086\u0087\u0005\u0004\u0000\u0000\u0087\u008a\u0003\u001a"+
		"\r\u0000\u0088\u0089\u0005\f\u0000\u0000\u0089\u008b\u0003\u001a\r\u0000"+
		"\u008a\u0088\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000"+
		"\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u008d\u0005\u0005\u0000\u0000"+
		"\u008d\u008e\u0005\n\u0000\u0000\u008e\u00ab\u0001\u0000\u0000\u0000\u008f"+
		"\u0090\u0003\u001a\r\u0000\u0090\u0091\u0005O\u0000\u0000\u0091\u0092"+
		"\u0005\u0004\u0000\u0000\u0092\u0093\u0003\u001a\r\u0000\u0093\u0094\u0005"+
		"\u0005\u0000\u0000\u0094\u0095\u0005\n\u0000\u0000\u0095\u00ab\u0001\u0000"+
		"\u0000\u0000\u0096\u0097\u0003\u001a\r\u0000\u0097\u0098\u0005P\u0000"+
		"\u0000\u0098\u0099\u0005\u0004\u0000\u0000\u0099\u009a\u0003\u001a\r\u0000"+
		"\u009a\u009b\u0005\f\u0000\u0000\u009b\u009c\u0003\u001a\r\u0000\u009c"+
		"\u009d\u0005\u0005\u0000\u0000\u009d\u009e\u0005\n\u0000\u0000\u009e\u00ab"+
		"\u0001\u0000\u0000\u0000\u009f\u00a0\u0003\u001a\r\u0000\u00a0\u00a1\u0005"+
		"Q\u0000\u0000\u00a1\u00a2\u0005\u0004\u0000\u0000\u00a2\u00a3\u0003\u001a"+
		"\r\u0000\u00a3\u00a4\u0005\f\u0000\u0000\u00a4\u00a5\u0003\u001a\r\u0000"+
		"\u00a5\u00a6\u0005\f\u0000\u0000\u00a6\u00a7\u0003\u001a\r\u0000\u00a7"+
		"\u00a8\u0005\u0005\u0000\u0000\u00a8\u00a9\u0005\n\u0000\u0000\u00a9\u00ab"+
		"\u0001\u0000\u0000\u0000\u00aa{\u0001\u0000\u0000\u0000\u00aa|\u0001\u0000"+
		"\u0000\u0000\u00aa}\u0001\u0000\u0000\u0000\u00aa\u0080\u0001\u0000\u0000"+
		"\u0000\u00aa\u0083\u0001\u0000\u0000\u0000\u00aa\u0084\u0001\u0000\u0000"+
		"\u0000\u00aa\u008f\u0001\u0000\u0000\u0000\u00aa\u0096\u0001\u0000\u0000"+
		"\u0000\u00aa\u009f\u0001\u0000\u0000\u0000\u00ab\r\u0001\u0000\u0000\u0000"+
		"\u00ac\u00ae\u00055\u0000\u0000\u00ad\u00af\u0003\u001a\r\u0000\u00ae"+
		"\u00ad\u0001\u0000\u0000\u0000\u00ae\u00af\u0001\u0000\u0000\u0000\u00af"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b0\u00b1\u0005\n\u0000\u0000\u00b1\u000f"+
		"\u0001\u0000\u0000\u0000\u00b2\u00b3\u0003\u0014\n\u0000\u00b3\u00b4\u0003"+
		"\n\u0005\u0000\u00b4\u00c1\u0001\u0000\u0000\u0000\u00b5\u00b6\u0003\u0012"+
		"\t\u0000\u00b6\u00b7\u0003\n\u0005\u0000\u00b7\u00c1\u0001\u0000\u0000"+
		"\u0000\u00b8\u00b9\u0003\u0016\u000b\u0000\u00b9\u00ba\u0003\n\u0005\u0000"+
		"\u00ba\u00c1\u0001\u0000\u0000\u0000\u00bb\u00bc\u00056\u0000\u0000\u00bc"+
		"\u00bd\u0003\n\u0005\u0000\u00bd\u00be\u0003\u0014\n\u0000\u00be\u00bf"+
		"\u0005\n\u0000\u0000\u00bf\u00c1\u0001\u0000\u0000\u0000\u00c0\u00b2\u0001"+
		"\u0000\u0000\u0000\u00c0\u00b5\u0001\u0000\u0000\u0000\u00c0\u00b8\u0001"+
		"\u0000\u0000\u0000\u00c0\u00bb\u0001\u0000\u0000\u0000\u00c1\u0011\u0001"+
		"\u0000\u0000\u0000\u00c2\u00c3\u00058\u0000\u0000\u00c3\u00c4\u0005\u0004"+
		"\u0000\u0000\u00c4\u00c5\u0003\u0002\u0001\u0000\u00c5\u00c6\u0005+\u0000"+
		"\u0000\u00c6\u00c7\u0003\u001a\r\u0000\u00c7\u00c8\u0005\u0005\u0000\u0000"+
		"\u00c8\u0013\u0001\u0000\u0000\u0000\u00c9\u00ca\u00057\u0000\u0000\u00ca"+
		"\u00cb\u0005\u0004\u0000\u0000\u00cb\u00cc\u0003\u001a\r\u0000\u00cc\u00cd"+
		"\u0005\u0005\u0000\u0000\u00cd\u0015\u0001\u0000\u0000\u0000\u00ce\u00cf"+
		"\u00059\u0000\u0000\u00cf\u00d0\u0005\u0004\u0000\u0000\u00d0\u00d1\u0003"+
		"\u001e\u000f\u0000\u00d1\u00d2\u0005\n\u0000\u0000\u00d2\u00d3\u0003\u001a"+
		"\r\u0000\u00d3\u00d4\u0005\n\u0000\u0000\u00d4\u00d5\u0003\u001c\u000e"+
		"\u0000\u00d5\u00d6\u0005\u0005\u0000\u0000\u00d6\u0017\u0001\u0000\u0000"+
		"\u0000\u00d7\u00d8\u0005:\u0000\u0000\u00d8\u00d9\u0005\u0004\u0000\u0000"+
		"\u00d9\u00da\u0003\u001a\r\u0000\u00da\u00db\u0005\u0005\u0000\u0000\u00db"+
		"\u00e0\u0003\n\u0005\u0000\u00dc\u00dd\u0005;\u0000\u0000\u00dd\u00df"+
		"\u0003\u0018\f\u0000\u00de\u00dc\u0001\u0000\u0000\u0000\u00df\u00e2\u0001"+
		"\u0000\u0000\u0000\u00e0\u00de\u0001\u0000\u0000\u0000\u00e0\u00e1\u0001"+
		"\u0000\u0000\u0000\u00e1\u00e5\u0001\u0000\u0000\u0000\u00e2\u00e0\u0001"+
		"\u0000\u0000\u0000\u00e3\u00e4\u0005;\u0000\u0000\u00e4\u00e6\u0003\n"+
		"\u0005\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000"+
		"\u0000\u0000\u00e6\u0019\u0001\u0000\u0000\u0000\u00e7\u00e8\u0006\r\uffff"+
		"\uffff\u0000\u00e8\u00e9\u0005\u0004\u0000\u0000\u00e9\u00ea\u0003\u001a"+
		"\r\u0000\u00ea\u00eb\u0005\u0005\u0000\u0000\u00eb\u014b\u0001\u0000\u0000"+
		"\u0000\u00ec\u00ed\u0007\u0000\u0000\u0000\u00ed\u014b\u0003\u001a\r\u001b"+
		"\u00ee\u00ef\u0005?\u0000\u0000\u00ef\u00f0\u0005\u0004\u0000\u0000\u00f0"+
		"\u00f1\u0003\u001a\r\u0000\u00f1\u00f2\u0005\u0005\u0000\u0000\u00f2\u014b"+
		"\u0001\u0000\u0000\u0000\u00f3\u00f4\u0005B\u0000\u0000\u00f4\u00f5\u0005"+
		"\u0004\u0000\u0000\u00f5\u00f6\u0003\u001a\r\u0000\u00f6\u00f7\u0005\f"+
		"\u0000\u0000\u00f7\u00f8\u0003\u001a\r\u0000\u00f8\u00f9\u0005\u0005\u0000"+
		"\u0000\u00f9\u014b\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005C\u0000\u0000"+
		"\u00fb\u00fc\u0005\u0004\u0000\u0000\u00fc\u00fd\u0003\u001a\r\u0000\u00fd"+
		"\u00fe\u0005\f\u0000\u0000\u00fe\u00ff\u0003\u001a\r\u0000\u00ff\u0100"+
		"\u0005\f\u0000\u0000\u0100\u0101\u0003\u001a\r\u0000\u0101\u0102\u0005"+
		"\u0005\u0000\u0000\u0102\u014b\u0001\u0000\u0000\u0000\u0103\u0104\u0005"+
		"A\u0000\u0000\u0104\u0105\u0005\u0004\u0000\u0000\u0105\u0106\u0003\u001a"+
		"\r\u0000\u0106\u0107\u0005\f\u0000\u0000\u0107\u0108\u0003\u001a\r\u0000"+
		"\u0108\u0109\u0005\f\u0000\u0000\u0109\u010a\u0003\u001a\r\u0000\u010a"+
		"\u010b\u0005\u0005\u0000\u0000\u010b\u014b\u0001\u0000\u0000\u0000\u010c"+
		"\u010d\u0005@\u0000\u0000\u010d\u010e\u0005\u0004\u0000\u0000\u010e\u010f"+
		"\u0003\u001a\r\u0000\u010f\u0110\u0005\f\u0000\u0000\u0110\u0111\u0003"+
		"\u001a\r\u0000\u0111\u0112\u0005\f\u0000\u0000\u0112\u0113\u0003\u001a"+
		"\r\u0000\u0113\u0114\u0005\f\u0000\u0000\u0114\u0115\u0003\u001a\r\u0000"+
		"\u0115\u0116\u0005\u0005\u0000\u0000\u0116\u014b\u0001\u0000\u0000\u0000"+
		"\u0117\u0118\u0005\u0006\u0000\u0000\u0118\u011d\u0003\u001a\r\u0000\u0119"+
		"\u011a\u0005\f\u0000\u0000\u011a\u011c\u0003\u001a\r\u0000\u011b\u0119"+
		"\u0001\u0000\u0000\u0000\u011c\u011f\u0001\u0000\u0000\u0000\u011d\u011b"+
		"\u0001\u0000\u0000\u0000\u011d\u011e\u0001\u0000\u0000\u0000\u011e\u0120"+
		"\u0001\u0000\u0000\u0000\u011f\u011d\u0001\u0000\u0000\u0000\u0120\u0121"+
		"\u0005\u0007\u0000\u0000\u0121\u014b\u0001\u0000\u0000\u0000\u0122\u0123"+
		"\u0005\u001e\u0000\u0000\u0123\u0128\u0003\u001a\r\u0000\u0124\u0125\u0005"+
		"\f\u0000\u0000\u0125\u0127\u0003\u001a\r\u0000\u0126\u0124\u0001\u0000"+
		"\u0000\u0000\u0127\u012a\u0001\u0000\u0000\u0000\u0128\u0126\u0001\u0000"+
		"\u0000\u0000\u0128\u0129\u0001\u0000\u0000\u0000\u0129\u012b\u0001\u0000"+
		"\u0000\u0000\u012a\u0128\u0001\u0000\u0000\u0000\u012b\u012c\u0005\u001d"+
		"\u0000\u0000\u012c\u014b\u0001\u0000\u0000\u0000\u012d\u012e\u0005\b\u0000"+
		"\u0000\u012e\u0133\u0003\u001a\r\u0000\u012f\u0130\u0005\f\u0000\u0000"+
		"\u0130\u0132\u0003\u001a\r\u0000\u0131\u012f\u0001\u0000\u0000\u0000\u0132"+
		"\u0135\u0001\u0000\u0000\u0000\u0133\u0131\u0001\u0000\u0000\u0000\u0133"+
		"\u0134\u0001\u0000\u0000\u0000\u0134\u0136\u0001\u0000\u0000\u0000\u0135"+
		"\u0133\u0001\u0000\u0000\u0000\u0136\u0137\u0005\t\u0000\u0000\u0137\u014b"+
		"\u0001\u0000\u0000\u0000\u0138\u0139\u0005>\u0000\u0000\u0139\u013a\u0003"+
		"\b\u0004\u0000\u013a\u013b\u0005\u0006\u0000\u0000\u013b\u013c\u0003\u001a"+
		"\r\u0000\u013c\u013d\u0005\u0007\u0000\u0000\u013d\u014b\u0001\u0000\u0000"+
		"\u0000\u013e\u013f\u0005>\u0000\u0000\u013f\u0140\u0005\u001e\u0000\u0000"+
		"\u0140\u014b\u0005\u001d\u0000\u0000\u0141\u0142\u0005>\u0000\u0000\u0142"+
		"\u0143\u0005\b\u0000\u0000\u0143\u014b\u0005\t\u0000\u0000\u0144\u0145"+
		"\u0005>\u0000\u0000\u0145\u0146\u0005\b\u0000\u0000\u0146\u0147\u0005"+
		"\u000b\u0000\u0000\u0147\u014b\u0005\t\u0000\u0000\u0148\u014b\u0003\""+
		"\u0011\u0000\u0149\u014b\u0003&\u0013\u0000\u014a\u00e7\u0001\u0000\u0000"+
		"\u0000\u014a\u00ec\u0001\u0000\u0000\u0000\u014a\u00ee\u0001\u0000\u0000"+
		"\u0000\u014a\u00f3\u0001\u0000\u0000\u0000\u014a\u00fa\u0001\u0000\u0000"+
		"\u0000\u014a\u0103\u0001\u0000\u0000\u0000\u014a\u010c\u0001\u0000\u0000"+
		"\u0000\u014a\u0117\u0001\u0000\u0000\u0000\u014a\u0122\u0001\u0000\u0000"+
		"\u0000\u014a\u012d\u0001\u0000\u0000\u0000\u014a\u0138\u0001\u0000\u0000"+
		"\u0000\u014a\u013e\u0001\u0000\u0000\u0000\u014a\u0141\u0001\u0000\u0000"+
		"\u0000\u014a\u0144\u0001\u0000\u0000\u0000\u014a\u0148\u0001\u0000\u0000"+
		"\u0000\u014a\u0149\u0001\u0000\u0000\u0000\u014b\u017f\u0001\u0000\u0000"+
		"\u0000\u014c\u014d\n\u001a\u0000\u0000\u014d\u014e\u0007\u0001\u0000\u0000"+
		"\u014e\u017e\u0003\u001a\r\u001b\u014f\u0150\n\u0019\u0000\u0000\u0150"+
		"\u0151\u0007\u0002\u0000\u0000\u0151\u017e\u0003\u001a\r\u001a\u0152\u0153"+
		"\n\u0018\u0000\u0000\u0153\u0154\u0005!\u0000\u0000\u0154\u017e\u0003"+
		"\u001a\r\u0019\u0155\u0156\n\u0017\u0000\u0000\u0156\u0157\u0007\u0003"+
		"\u0000\u0000\u0157\u017e\u0003\u001a\r\u0018\u0158\u0159\n\u0016\u0000"+
		"\u0000\u0159\u015a\u0007\u0004\u0000\u0000\u015a\u017e\u0003\u001a\r\u0017"+
		"\u015b\u015c\n\u0015\u0000\u0000\u015c\u015d\u0005\u000f\u0000\u0000\u015d"+
		"\u015e\u0003\u001a\r\u0000\u015e\u015f\u0005\u000b\u0000\u0000\u015f\u0160"+
		"\u0003\u001a\r\u0016\u0160\u017e\u0001\u0000\u0000\u0000\u0161\u0162\n"+
		"\u0014\u0000\u0000\u0162\u0163\u0005J\u0000\u0000\u0163\u0164\u0005\u0004"+
		"\u0000\u0000\u0164\u0165\u0003\u001a\r\u0000\u0165\u0166\u0005\u0005\u0000"+
		"\u0000\u0166\u017e\u0001\u0000\u0000\u0000\u0167\u0168\n\u0013\u0000\u0000"+
		"\u0168\u0169\u0005K\u0000\u0000\u0169\u016a\u0005\u0004\u0000\u0000\u016a"+
		"\u016b\u0003\u001a\r\u0000\u016b\u016c\u0005\u0005\u0000\u0000\u016c\u017e"+
		"\u0001\u0000\u0000\u0000\u016d\u016e\n\u0012\u0000\u0000\u016e\u016f\u0005"+
		"L\u0000\u0000\u016f\u0170\u0005\u0004\u0000\u0000\u0170\u017e\u0005\u0005"+
		"\u0000\u0000\u0171\u0172\n\u0011\u0000\u0000\u0172\u017e\u0007\u0005\u0000"+
		"\u0000\u0173\u0174\n\r\u0000\u0000\u0174\u0175\u0005M\u0000\u0000\u0175"+
		"\u0176\u0005\u0004\u0000\u0000\u0176\u0177\u0003\u001a\r\u0000\u0177\u0178"+
		"\u0005\f\u0000\u0000\u0178\u0179\u0003\u001a\r\u0000\u0179\u017a\u0005"+
		"\u0005\u0000\u0000\u017a\u017e\u0001\u0000\u0000\u0000\u017b\u017c\n\f"+
		"\u0000\u0000\u017c\u017e\u0007\u0006\u0000\u0000\u017d\u014c\u0001\u0000"+
		"\u0000\u0000\u017d\u014f\u0001\u0000\u0000\u0000\u017d\u0152\u0001\u0000"+
		"\u0000\u0000\u017d\u0155\u0001\u0000\u0000\u0000\u017d\u0158\u0001\u0000"+
		"\u0000\u0000\u017d\u015b\u0001\u0000\u0000\u0000\u017d\u0161\u0001\u0000"+
		"\u0000\u0000\u017d\u0167\u0001\u0000\u0000\u0000\u017d\u016d\u0001\u0000"+
		"\u0000\u0000\u017d\u0171\u0001\u0000\u0000\u0000\u017d\u0173\u0001\u0000"+
		"\u0000\u0000\u017d\u017b\u0001\u0000\u0000\u0000\u017e\u0181\u0001\u0000"+
		"\u0000\u0000\u017f\u017d\u0001\u0000\u0000\u0000\u017f\u0180\u0001\u0000"+
		"\u0000\u0000\u0180\u001b\u0001\u0000\u0000\u0000\u0181\u017f\u0001\u0000"+
		"\u0000\u0000\u0182\u0183\u0003\"\u0011\u0000\u0183\u0184\u0005\u0010\u0000"+
		"\u0000\u0184\u0185\u0003\u001a\r\u0000\u0185\u01a9\u0001\u0000\u0000\u0000"+
		"\u0186\u0187\u0003\"\u0011\u0000\u0187\u0188\u0005\u0011\u0000\u0000\u0188"+
		"\u01a9\u0001\u0000\u0000\u0000\u0189\u018a\u0003\"\u0011\u0000\u018a\u018b"+
		"\u0005\u0012\u0000\u0000\u018b\u01a9\u0001\u0000\u0000\u0000\u018c\u018d"+
		"\u0003\"\u0011\u0000\u018d\u018e\u0005\u0013\u0000\u0000\u018e\u018f\u0003"+
		"\u001a\r\u0000\u018f\u01a9\u0001\u0000\u0000\u0000\u0190\u0191\u0003\""+
		"\u0011\u0000\u0191\u0192\u0005\u0014\u0000\u0000\u0192\u0193\u0003\u001a"+
		"\r\u0000\u0193\u01a9\u0001\u0000\u0000\u0000\u0194\u0195\u0003\"\u0011"+
		"\u0000\u0195\u0196\u0005\u0015\u0000\u0000\u0196\u0197\u0003\u001a\r\u0000"+
		"\u0197\u01a9\u0001\u0000\u0000\u0000\u0198\u0199\u0003\"\u0011\u0000\u0199"+
		"\u019a\u0005\u0016\u0000\u0000\u019a\u019b\u0003\u001a\r\u0000\u019b\u01a9"+
		"\u0001\u0000\u0000\u0000\u019c\u019d\u0003\"\u0011\u0000\u019d\u019e\u0005"+
		"\u0017\u0000\u0000\u019e\u019f\u0003\u001a\r\u0000\u019f\u01a9\u0001\u0000"+
		"\u0000\u0000\u01a0\u01a1\u0003\"\u0011\u0000\u01a1\u01a2\u0005\u0018\u0000"+
		"\u0000\u01a2\u01a3\u0003\u001a\r\u0000\u01a3\u01a9\u0001\u0000\u0000\u0000"+
		"\u01a4\u01a5\u0003\"\u0011\u0000\u01a5\u01a6\u0005\u0019\u0000\u0000\u01a6"+
		"\u01a7\u0003\u001a\r\u0000\u01a7\u01a9\u0001\u0000\u0000\u0000\u01a8\u0182"+
		"\u0001\u0000\u0000\u0000\u01a8\u0186\u0001\u0000\u0000\u0000\u01a8\u0189"+
		"\u0001\u0000\u0000\u0000\u01a8\u018c\u0001\u0000\u0000\u0000\u01a8\u0190"+
		"\u0001\u0000\u0000\u0000\u01a8\u0194\u0001\u0000\u0000\u0000\u01a8\u0198"+
		"\u0001\u0000\u0000\u0000\u01a8\u019c\u0001\u0000\u0000\u0000\u01a8\u01a0"+
		"\u0001\u0000\u0000\u0000\u01a8\u01a4\u0001\u0000\u0000\u0000\u01a9\u001d"+
		"\u0001\u0000\u0000\u0000\u01aa\u01ab\u0003\u0002\u0001\u0000\u01ab\u01ac"+
		"\u0005\u0010\u0000\u0000\u01ac\u01ad\u0003\u001a\r\u0000\u01ad\u001f\u0001"+
		"\u0000\u0000\u0000\u01ae\u01b1\u0003\u0002\u0001\u0000\u01af\u01b1\u0003"+
		"\u001e\u000f\u0000\u01b0\u01ae\u0001\u0000\u0000\u0000\u01b0\u01af\u0001"+
		"\u0000\u0000\u0000\u01b1!\u0001\u0000\u0000\u0000\u01b2\u01be\u0003$\u0012"+
		"\u0000\u01b3\u01b4\u0003$\u0012\u0000\u01b4\u01b5\u0005\u001e\u0000\u0000"+
		"\u01b5\u01b6\u0003\u001a\r\u0000\u01b6\u01b7\u0005\u001d\u0000\u0000\u01b7"+
		"\u01be\u0001\u0000\u0000\u0000\u01b8\u01b9\u0003$\u0012\u0000\u01b9\u01ba"+
		"\u0005\u0006\u0000\u0000\u01ba\u01bb\u0003\u001a\r\u0000\u01bb\u01bc\u0005"+
		"\u0007\u0000\u0000\u01bc\u01be\u0001\u0000\u0000\u0000\u01bd\u01b2\u0001"+
		"\u0000\u0000\u0000\u01bd\u01b3\u0001\u0000\u0000\u0000\u01bd\u01b8\u0001"+
		"\u0000\u0000\u0000\u01be#\u0001\u0000\u0000\u0000\u01bf\u01c0\u0005[\u0000"+
		"\u0000\u01c0%\u0001\u0000\u0000\u0000\u01c1\u01c7\u0005X\u0000\u0000\u01c2"+
		"\u01c7\u0005Y\u0000\u0000\u01c3\u01c7\u0003(\u0014\u0000\u01c4\u01c7\u0005"+
		"S\u0000\u0000\u01c5\u01c7\u0005R\u0000\u0000\u01c6\u01c1\u0001\u0000\u0000"+
		"\u0000\u01c6\u01c2\u0001\u0000\u0000\u0000\u01c6\u01c3\u0001\u0000\u0000"+
		"\u0000\u01c6\u01c4\u0001\u0000\u0000\u0000\u01c6\u01c5\u0001\u0000\u0000"+
		"\u0000\u01c7\'\u0001\u0000\u0000\u0000\u01c8\u01cb\u0005U\u0000\u0000"+
		"\u01c9\u01cb\u0005T\u0000\u0000\u01ca\u01c8\u0001\u0000\u0000\u0000\u01ca"+
		"\u01c9\u0001\u0000\u0000\u0000\u01cb)\u0001\u0000\u0000\u0000\u001c06"+
		"=BHO`kmuy\u008a\u00aa\u00ae\u00c0\u00e0\u00e5\u011d\u0128\u0133\u014a"+
		"\u017d\u017f\u01a8\u01b0\u01bd\u01c6\u01ca";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}