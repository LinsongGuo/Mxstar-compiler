// Generated from Mxstar.g4 by ANTLR 4.8

	package parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxstarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, BoolLiteral=48, IntLiteral=49, StringLiteral=50, ESC=51, 
		Identifier=52, WS=53, NewLine=54, LineComment=55, BlockComment=56;
	public static final int
		RULE_program = 0, RULE_def = 1, RULE_classDef = 2, RULE_constructorDef = 3, 
		RULE_varDefList = 4, RULE_varDef = 5, RULE_functDef = 6, RULE_paraList = 7, 
		RULE_para = 8, RULE_block = 9, RULE_stmt = 10, RULE_expr = 11, RULE_identifierMember = 12, 
		RULE_arrayCall = 13, RULE_functCall = 14, RULE_creator = 15, RULE_type = 16, 
		RULE_varType = 17, RULE_arrayType = 18, RULE_primType = 19, RULE_literal = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "def", "classDef", "constructorDef", "varDefList", "varDef", 
			"functDef", "paraList", "para", "block", "stmt", "expr", "identifierMember", 
			"arrayCall", "functCall", "creator", "type", "varType", "arrayType", 
			"primType", "literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "';'", "'('", "')'", "','", "'='", "'if'", 
			"'else'", "'for'", "'while'", "'return'", "'break'", "'continue'", "'this'", 
			"'new'", "'.'", "'++'", "'--'", "'+'", "'-'", "'~'", "'!'", "'*'", "'/'", 
			"'%'", "'<<'", "'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", 
			"'&'", "'^'", "'|'", "'&&'", "'||'", "'['", "']'", "'bool'", "'int'", 
			"'string'", "'void'", "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"BoolLiteral", "IntLiteral", "StringLiteral", "ESC", "Identifier", "WS", 
			"NewLine", "LineComment", "BlockComment"
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
	public String getGrammarFileName() { return "Mxstar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxstarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxstarParser.EOF, 0); }
		public List<DefContext> def() {
			return getRuleContexts(DefContext.class);
		}
		public DefContext def(int i) {
			return getRuleContext(DefContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << Identifier))) != 0)) {
				{
				{
				setState(42);
				def();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			match(EOF);
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

	public static class DefContext extends ParserRuleContext {
		public ClassDefContext classDef() {
			return getRuleContext(ClassDefContext.class,0);
		}
		public VarDefListContext varDefList() {
			return getRuleContext(VarDefListContext.class,0);
		}
		public FunctDefContext functDef() {
			return getRuleContext(FunctDefContext.class,0);
		}
		public DefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_def);
		try {
			setState(53);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				classDef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				varDefList();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(52);
				functDef();
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

	public static class ClassDefContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public List<VarDefListContext> varDefList() {
			return getRuleContexts(VarDefListContext.class);
		}
		public VarDefListContext varDefList(int i) {
			return getRuleContext(VarDefListContext.class,i);
		}
		public List<FunctDefContext> functDef() {
			return getRuleContexts(FunctDefContext.class);
		}
		public FunctDefContext functDef(int i) {
			return getRuleContext(FunctDefContext.class,i);
		}
		public List<ConstructorDefContext> constructorDef() {
			return getRuleContexts(ConstructorDefContext.class);
		}
		public ConstructorDefContext constructorDef(int i) {
			return getRuleContext(ConstructorDefContext.class,i);
		}
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitClassDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(T__0);
			setState(56);
			match(Identifier);
			setState(57);
			match(T__1);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << Identifier))) != 0)) {
				{
				setState(61);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(58);
					varDefList();
					}
					break;
				case 2:
					{
					setState(59);
					functDef();
					}
					break;
				case 3:
					{
					setState(60);
					constructorDef();
					}
					break;
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66);
			match(T__2);
			setState(67);
			match(T__3);
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

	public static class ConstructorDefContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructorDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterConstructorDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitConstructorDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitConstructorDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDefContext constructorDef() throws RecognitionException {
		ConstructorDefContext _localctx = new ConstructorDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_constructorDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(Identifier);
			setState(70);
			match(T__4);
			setState(71);
			match(T__5);
			setState(72);
			block();
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

	public static class VarDefListContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public VarDefListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDefList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterVarDefList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitVarDefList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitVarDefList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefListContext varDefList() throws RecognitionException {
		VarDefListContext _localctx = new VarDefListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_varDefList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			type();
			setState(75);
			varDef();
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(76);
				match(T__6);
				setState(77);
				varDef();
				}
				}
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(83);
			match(T__3);
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

	public static class VarDefContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefContext varDef() throws RecognitionException {
		VarDefContext _localctx = new VarDefContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_varDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(Identifier);
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(86);
				match(T__7);
				setState(87);
				expr(0);
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

	public static class FunctDefContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public ParaListContext paraList() {
			return getRuleContext(ParaListContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterFunctDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitFunctDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitFunctDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctDefContext functDef() throws RecognitionException {
		FunctDefContext _localctx = new FunctDefContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_functDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			type();
			setState(91);
			match(Identifier);
			setState(92);
			match(T__4);
			setState(93);
			paraList();
			setState(94);
			match(T__5);
			setState(95);
			block();
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

	public static class ParaListContext extends ParserRuleContext {
		public List<ParaContext> para() {
			return getRuleContexts(ParaContext.class);
		}
		public ParaContext para(int i) {
			return getRuleContext(ParaContext.class,i);
		}
		public ParaListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paraList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterParaList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitParaList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitParaList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParaListContext paraList() throws RecognitionException {
		ParaListContext _localctx = new ParaListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_paraList);
		int _la;
		try {
			setState(106);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				para();
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(99);
					match(T__6);
					setState(100);
					para();
					}
					}
					setState(105);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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

	public static class ParaContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_para; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterPara(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitPara(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitPara(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParaContext para() throws RecognitionException {
		ParaContext _localctx = new ParaContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_para);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			type();
			setState(109);
			match(Identifier);
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(110);
				match(T__7);
				setState(111);
				expr(0);
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

	public static class BlockContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(T__1);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
				{
				{
				setState(115);
				stmt();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
			match(T__2);
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

	public static class StmtContext extends ParserRuleContext {
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	 
		public StmtContext() { }
		public void copyFrom(StmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ForStmtContext extends StmtContext {
		public ExprContext init;
		public ExprContext cond;
		public ExprContext step;
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ForStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterForStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitForStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarDefStmtContext extends StmtContext {
		public VarDefListContext varDefList() {
			return getRuleContext(VarDefListContext.class,0);
		}
		public VarDefStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterVarDefStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitVarDefStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitVarDefStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprStmtContext extends StmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileStmtContext extends StmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public WhileStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BrankStmtContext extends StmtContext {
		public BrankStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterBrankStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitBrankStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitBrankStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfStmtContext extends StmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public IfStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockStmtContext extends StmtContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterBlockStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitBlockStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitBlockStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BreakStmtContext extends StmtContext {
		public BreakStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterBreakStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitBreakStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitBreakStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnStmtContext extends StmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitReturnStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ContinueStmtContext extends StmtContext {
		public ContinueStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterContinueStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitContinueStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitContinueStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_stmt);
		int _la;
		try {
			setState(168);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(123);
				block();
				}
				break;
			case 2:
				_localctx = new IfStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(124);
				match(T__8);
				setState(125);
				match(T__4);
				setState(126);
				expr(0);
				setState(127);
				match(T__5);
				setState(128);
				stmt();
				setState(131);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(129);
					match(T__9);
					setState(130);
					stmt();
					}
					break;
				}
				}
				break;
			case 3:
				_localctx = new ForStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(133);
				match(T__10);
				setState(134);
				match(T__4);
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__15) | (1L << T__16) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__46) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(135);
					((ForStmtContext)_localctx).init = expr(0);
					}
				}

				setState(138);
				match(T__3);
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__15) | (1L << T__16) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__46) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(139);
					((ForStmtContext)_localctx).cond = expr(0);
					}
				}

				setState(142);
				match(T__3);
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__15) | (1L << T__16) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__46) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(143);
					((ForStmtContext)_localctx).step = expr(0);
					}
				}

				setState(146);
				match(T__5);
				setState(147);
				stmt();
				}
				break;
			case 4:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(148);
				match(T__11);
				setState(149);
				match(T__4);
				setState(150);
				expr(0);
				setState(151);
				match(T__5);
				setState(152);
				stmt();
				}
				break;
			case 5:
				_localctx = new ReturnStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(154);
				match(T__12);
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__15) | (1L << T__16) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__46) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(155);
					expr(0);
					}
				}

				setState(158);
				match(T__3);
				}
				break;
			case 6:
				_localctx = new BreakStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(159);
				match(T__13);
				setState(160);
				match(T__3);
				}
				break;
			case 7:
				_localctx = new ContinueStmtContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(161);
				match(T__14);
				setState(162);
				match(T__3);
				}
				break;
			case 8:
				_localctx = new VarDefStmtContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(163);
				varDefList();
				}
				break;
			case 9:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(164);
				expr(0);
				setState(165);
				match(T__3);
				}
				break;
			case 10:
				_localctx = new BrankStmtContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(167);
				match(T__3);
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
	public static class ThisExprContext extends ExprContext {
		public ThisExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterThisExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitThisExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitThisExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarExprContext extends ExprContext {
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public VarExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterVarExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitVarExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitVarExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrefixExprContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrefixExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterPrefixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitPrefixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitPrefixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayExprContext extends ExprContext {
		public ArrayCallContext arrayCall() {
			return getRuleContext(ArrayCallContext.class,0);
		}
		public ArrayExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterArrayExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitArrayExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitArrayExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BracketExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BracketExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterBracketExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitBracketExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitBracketExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralExprContext extends ExprContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterLiteralExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitLiteralExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitLiteralExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MemberExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IdentifierMemberContext identifierMember() {
			return getRuleContext(IdentifierMemberContext.class,0);
		}
		public FunctCallContext functCall() {
			return getRuleContext(FunctCallContext.class,0);
		}
		public ArrayCallContext arrayCall() {
			return getRuleContext(ArrayCallContext.class,0);
		}
		public MemberExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterMemberExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitMemberExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitMemberExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SuffixExprContext extends ExprContext {
		public Token op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SuffixExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterSuffixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitSuffixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitSuffixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BinaryExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitBinaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreatorExprContext extends ExprContext {
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public CreatorExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterCreatorExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitCreatorExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitCreatorExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctExprContext extends ExprContext {
		public FunctCallContext functCall() {
			return getRuleContext(FunctCallContext.class,0);
		}
		public FunctExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterFunctExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitFunctExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitFunctExpr(this);
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
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				_localctx = new BracketExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(171);
				match(T__4);
				setState(172);
				expr(0);
				setState(173);
				match(T__5);
				}
				break;
			case 2:
				{
				_localctx = new ThisExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(175);
				match(T__15);
				}
				break;
			case 3:
				{
				_localctx = new CreatorExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(176);
				match(T__16);
				setState(177);
				creator();
				}
				break;
			case 4:
				{
				_localctx = new LiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(178);
				literal();
				}
				break;
			case 5:
				{
				_localctx = new VarExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(179);
				match(Identifier);
				}
				break;
			case 6:
				{
				_localctx = new FunctExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(180);
				functCall();
				}
				break;
			case 7:
				{
				_localctx = new ArrayExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(181);
				arrayCall();
				}
				break;
			case 8:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(182);
				((PrefixExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__20 || _la==T__21) ) {
					((PrefixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(183);
				expr(14);
				}
				break;
			case 9:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(184);
				((PrefixExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__18 || _la==T__19) ) {
					((PrefixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(185);
				expr(13);
				}
				break;
			case 10:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(186);
				((PrefixExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__22 || _la==T__23) ) {
					((PrefixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(187);
				expr(12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(234);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(232);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(190);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(191);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26))) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(192);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(193);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(194);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__20 || _la==T__21) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(195);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(196);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(197);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__27 || _la==T__28) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(198);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(199);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(200);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32))) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(201);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(202);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(203);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__33 || _la==T__34) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(204);
						expr(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(205);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(206);
						((BinaryExprContext)_localctx).op = match(T__35);
						setState(207);
						expr(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(208);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(209);
						((BinaryExprContext)_localctx).op = match(T__36);
						setState(210);
						expr(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(211);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(212);
						((BinaryExprContext)_localctx).op = match(T__37);
						setState(213);
						expr(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(214);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(215);
						((BinaryExprContext)_localctx).op = match(T__38);
						setState(216);
						expr(4);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(217);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(218);
						((BinaryExprContext)_localctx).op = match(T__39);
						setState(219);
						expr(3);
						}
						break;
					case 11:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(220);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(221);
						((BinaryExprContext)_localctx).op = match(T__7);
						setState(222);
						expr(1);
						}
						break;
					case 12:
						{
						_localctx = new MemberExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(223);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(224);
						match(T__17);
						setState(228);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
						case 1:
							{
							setState(225);
							identifierMember();
							}
							break;
						case 2:
							{
							setState(226);
							functCall();
							}
							break;
						case 3:
							{
							setState(227);
							arrayCall();
							}
							break;
						}
						}
						break;
					case 13:
						{
						_localctx = new SuffixExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(230);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(231);
						((SuffixExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__18 || _la==T__19) ) {
							((SuffixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
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
				setState(236);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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

	public static class IdentifierMemberContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public IdentifierMemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierMember; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterIdentifierMember(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitIdentifierMember(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitIdentifierMember(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierMemberContext identifierMember() throws RecognitionException {
		IdentifierMemberContext _localctx = new IdentifierMemberContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_identifierMember);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(Identifier);
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

	public static class ArrayCallContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ArrayCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterArrayCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitArrayCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitArrayCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayCallContext arrayCall() throws RecognitionException {
		ArrayCallContext _localctx = new ArrayCallContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_arrayCall);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(Identifier);
			setState(244); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(240);
					match(T__40);
					setState(241);
					expr(0);
					setState(242);
					match(T__41);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(246); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class FunctCallContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public FunctCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterFunctCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitFunctCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitFunctCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctCallContext functCall() throws RecognitionException {
		FunctCallContext _localctx = new FunctCallContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_functCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			match(Identifier);
			setState(249);
			match(T__4);
			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__15) | (1L << T__16) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__46) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
				{
				setState(250);
				expr(0);
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(251);
					match(T__6);
					setState(252);
					expr(0);
					}
					}
					setState(257);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(260);
			match(T__5);
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

	public static class CreatorContext extends ParserRuleContext {
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
	 
		public CreatorContext() { }
		public void copyFrom(CreatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NaiveCreatorContext extends CreatorContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public NaiveCreatorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterNaiveCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitNaiveCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitNaiveCreator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ClassCreatorContext extends CreatorContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public ClassCreatorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterClassCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitClassCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitClassCreator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InvalidCreatorContext extends CreatorContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public InvalidCreatorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterInvalidCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitInvalidCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitInvalidCreator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayCreatorContext extends CreatorContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ArrayCreatorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterArrayCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitArrayCreator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitArrayCreator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_creator);
		try {
			int _alt;
			setState(306);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				_localctx = new InvalidCreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				varType();
				setState(267); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(263);
						match(T__40);
						setState(264);
						expr(0);
						setState(265);
						match(T__41);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(269); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(273); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(271);
						match(T__40);
						setState(272);
						match(T__41);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(275); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(281); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(277);
						match(T__40);
						setState(278);
						expr(0);
						setState(279);
						match(T__41);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(283); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				_localctx = new ArrayCreatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(285);
				varType();
				setState(290); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(286);
						match(T__40);
						setState(287);
						expr(0);
						setState(288);
						match(T__41);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(292); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(298);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(294);
						match(T__40);
						setState(295);
						match(T__41);
						}
						} 
					}
					setState(300);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
				}
				}
				break;
			case 3:
				_localctx = new ClassCreatorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(301);
				varType();
				setState(302);
				match(T__4);
				setState(303);
				match(T__5);
				}
				break;
			case 4:
				_localctx = new NaiveCreatorContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(305);
				varType();
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

	public static class TypeContext extends ParserRuleContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_type);
		try {
			setState(310);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(308);
				varType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(309);
				arrayType();
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

	public static class VarTypeContext extends ParserRuleContext {
		public PrimTypeContext primType() {
			return getRuleContext(PrimTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public VarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterVarType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitVarType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitVarType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarTypeContext varType() throws RecognitionException {
		VarTypeContext _localctx = new VarTypeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_varType);
		try {
			setState(314);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__42:
			case T__43:
			case T__44:
			case T__45:
				enterOuterAlt(_localctx, 1);
				{
				setState(312);
				primType();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(313);
				match(Identifier);
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

	public static class ArrayTypeContext extends ParserRuleContext {
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_arrayType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			varType();
			setState(321);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__40) {
				{
				{
				setState(317);
				match(T__40);
				setState(318);
				match(T__41);
				}
				}
				setState(323);
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

	public static class PrimTypeContext extends ParserRuleContext {
		public PrimTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterPrimType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitPrimType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitPrimType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimTypeContext primType() throws RecognitionException {
		PrimTypeContext _localctx = new PrimTypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_primType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode BoolLiteral() { return getToken(MxstarParser.BoolLiteral, 0); }
		public TerminalNode IntLiteral() { return getToken(MxstarParser.IntLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(MxstarParser.StringLiteral, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__46) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 11:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		case 11:
			return precpred(_ctx, 16);
		case 12:
			return precpred(_ctx, 15);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3:\u014b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\7\2.\n\2\f\2\16\2\61\13\2"+
		"\3\2\3\2\3\3\3\3\3\3\5\38\n\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4@\n\4\f\4\16"+
		"\4C\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\7\6Q\n\6\f\6"+
		"\16\6T\13\6\3\6\3\6\3\7\3\7\3\7\5\7[\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\7\th\n\t\f\t\16\tk\13\t\5\tm\n\t\3\n\3\n\3\n\3\n\5\ns\n"+
		"\n\3\13\3\13\7\13w\n\13\f\13\16\13z\13\13\3\13\3\13\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\5\f\u0086\n\f\3\f\3\f\3\f\5\f\u008b\n\f\3\f\3\f\5\f\u008f"+
		"\n\f\3\f\3\f\5\f\u0093\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f"+
		"\u009f\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00ab\n\f\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r"+
		"\u00bf\n\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\5\r\u00e7\n\r\3\r\3\r\7\r\u00eb\n\r\f\r\16\r\u00ee"+
		"\13\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\6\17\u00f7\n\17\r\17\16\17\u00f8"+
		"\3\20\3\20\3\20\3\20\3\20\7\20\u0100\n\20\f\20\16\20\u0103\13\20\5\20"+
		"\u0105\n\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\6\21\u010e\n\21\r\21\16"+
		"\21\u010f\3\21\3\21\6\21\u0114\n\21\r\21\16\21\u0115\3\21\3\21\3\21\3"+
		"\21\6\21\u011c\n\21\r\21\16\21\u011d\3\21\3\21\3\21\3\21\3\21\6\21\u0125"+
		"\n\21\r\21\16\21\u0126\3\21\3\21\7\21\u012b\n\21\f\21\16\21\u012e\13\21"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u0135\n\21\3\22\3\22\5\22\u0139\n\22\3"+
		"\23\3\23\5\23\u013d\n\23\3\24\3\24\3\24\7\24\u0142\n\24\f\24\16\24\u0145"+
		"\13\24\3\25\3\25\3\26\3\26\3\26\2\3\30\27\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(*\2\13\3\2\27\30\3\2\25\26\3\2\31\32\3\2\33\35\3\2\36\37"+
		"\3\2 #\3\2$%\3\2-\60\3\2\61\64\2\u0175\2/\3\2\2\2\4\67\3\2\2\2\69\3\2"+
		"\2\2\bG\3\2\2\2\nL\3\2\2\2\fW\3\2\2\2\16\\\3\2\2\2\20l\3\2\2\2\22n\3\2"+
		"\2\2\24t\3\2\2\2\26\u00aa\3\2\2\2\30\u00be\3\2\2\2\32\u00ef\3\2\2\2\34"+
		"\u00f1\3\2\2\2\36\u00fa\3\2\2\2 \u0134\3\2\2\2\"\u0138\3\2\2\2$\u013c"+
		"\3\2\2\2&\u013e\3\2\2\2(\u0146\3\2\2\2*\u0148\3\2\2\2,.\5\4\3\2-,\3\2"+
		"\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63"+
		"\7\2\2\3\63\3\3\2\2\2\648\5\6\4\2\658\5\n\6\2\668\5\16\b\2\67\64\3\2\2"+
		"\2\67\65\3\2\2\2\67\66\3\2\2\28\5\3\2\2\29:\7\3\2\2:;\7\66\2\2;A\7\4\2"+
		"\2<@\5\n\6\2=@\5\16\b\2>@\5\b\5\2?<\3\2\2\2?=\3\2\2\2?>\3\2\2\2@C\3\2"+
		"\2\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7\5\2\2EF\7\6\2\2F\7\3"+
		"\2\2\2GH\7\66\2\2HI\7\7\2\2IJ\7\b\2\2JK\5\24\13\2K\t\3\2\2\2LM\5\"\22"+
		"\2MR\5\f\7\2NO\7\t\2\2OQ\5\f\7\2PN\3\2\2\2QT\3\2\2\2RP\3\2\2\2RS\3\2\2"+
		"\2SU\3\2\2\2TR\3\2\2\2UV\7\6\2\2V\13\3\2\2\2WZ\7\66\2\2XY\7\n\2\2Y[\5"+
		"\30\r\2ZX\3\2\2\2Z[\3\2\2\2[\r\3\2\2\2\\]\5\"\22\2]^\7\66\2\2^_\7\7\2"+
		"\2_`\5\20\t\2`a\7\b\2\2ab\5\24\13\2b\17\3\2\2\2cm\3\2\2\2di\5\22\n\2e"+
		"f\7\t\2\2fh\5\22\n\2ge\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2jm\3\2\2\2"+
		"ki\3\2\2\2lc\3\2\2\2ld\3\2\2\2m\21\3\2\2\2no\5\"\22\2or\7\66\2\2pq\7\n"+
		"\2\2qs\5\30\r\2rp\3\2\2\2rs\3\2\2\2s\23\3\2\2\2tx\7\4\2\2uw\5\26\f\2v"+
		"u\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2{|\7\5\2\2"+
		"|\25\3\2\2\2}\u00ab\5\24\13\2~\177\7\13\2\2\177\u0080\7\7\2\2\u0080\u0081"+
		"\5\30\r\2\u0081\u0082\7\b\2\2\u0082\u0085\5\26\f\2\u0083\u0084\7\f\2\2"+
		"\u0084\u0086\5\26\f\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u00ab"+
		"\3\2\2\2\u0087\u0088\7\r\2\2\u0088\u008a\7\7\2\2\u0089\u008b\5\30\r\2"+
		"\u008a\u0089\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e"+
		"\7\6\2\2\u008d\u008f\5\30\r\2\u008e\u008d\3\2\2\2\u008e\u008f\3\2\2\2"+
		"\u008f\u0090\3\2\2\2\u0090\u0092\7\6\2\2\u0091\u0093\5\30\r\2\u0092\u0091"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0095\7\b\2\2\u0095"+
		"\u00ab\5\26\f\2\u0096\u0097\7\16\2\2\u0097\u0098\7\7\2\2\u0098\u0099\5"+
		"\30\r\2\u0099\u009a\7\b\2\2\u009a\u009b\5\26\f\2\u009b\u00ab\3\2\2\2\u009c"+
		"\u009e\7\17\2\2\u009d\u009f\5\30\r\2\u009e\u009d\3\2\2\2\u009e\u009f\3"+
		"\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00ab\7\6\2\2\u00a1\u00a2\7\20\2\2\u00a2"+
		"\u00ab\7\6\2\2\u00a3\u00a4\7\21\2\2\u00a4\u00ab\7\6\2\2\u00a5\u00ab\5"+
		"\n\6\2\u00a6\u00a7\5\30\r\2\u00a7\u00a8\7\6\2\2\u00a8\u00ab\3\2\2\2\u00a9"+
		"\u00ab\7\6\2\2\u00aa}\3\2\2\2\u00aa~\3\2\2\2\u00aa\u0087\3\2\2\2\u00aa"+
		"\u0096\3\2\2\2\u00aa\u009c\3\2\2\2\u00aa\u00a1\3\2\2\2\u00aa\u00a3\3\2"+
		"\2\2\u00aa\u00a5\3\2\2\2\u00aa\u00a6\3\2\2\2\u00aa\u00a9\3\2\2\2\u00ab"+
		"\27\3\2\2\2\u00ac\u00ad\b\r\1\2\u00ad\u00ae\7\7\2\2\u00ae\u00af\5\30\r"+
		"\2\u00af\u00b0\7\b\2\2\u00b0\u00bf\3\2\2\2\u00b1\u00bf\7\22\2\2\u00b2"+
		"\u00b3\7\23\2\2\u00b3\u00bf\5 \21\2\u00b4\u00bf\5*\26\2\u00b5\u00bf\7"+
		"\66\2\2\u00b6\u00bf\5\36\20\2\u00b7\u00bf\5\34\17\2\u00b8\u00b9\t\2\2"+
		"\2\u00b9\u00bf\5\30\r\20\u00ba\u00bb\t\3\2\2\u00bb\u00bf\5\30\r\17\u00bc"+
		"\u00bd\t\4\2\2\u00bd\u00bf\5\30\r\16\u00be\u00ac\3\2\2\2\u00be\u00b1\3"+
		"\2\2\2\u00be\u00b2\3\2\2\2\u00be\u00b4\3\2\2\2\u00be\u00b5\3\2\2\2\u00be"+
		"\u00b6\3\2\2\2\u00be\u00b7\3\2\2\2\u00be\u00b8\3\2\2\2\u00be\u00ba\3\2"+
		"\2\2\u00be\u00bc\3\2\2\2\u00bf\u00ec\3\2\2\2\u00c0\u00c1\f\r\2\2\u00c1"+
		"\u00c2\t\5\2\2\u00c2\u00eb\5\30\r\16\u00c3\u00c4\f\f\2\2\u00c4\u00c5\t"+
		"\2\2\2\u00c5\u00eb\5\30\r\r\u00c6\u00c7\f\13\2\2\u00c7\u00c8\t\6\2\2\u00c8"+
		"\u00eb\5\30\r\f\u00c9\u00ca\f\n\2\2\u00ca\u00cb\t\7\2\2\u00cb\u00eb\5"+
		"\30\r\13\u00cc\u00cd\f\t\2\2\u00cd\u00ce\t\b\2\2\u00ce\u00eb\5\30\r\n"+
		"\u00cf\u00d0\f\b\2\2\u00d0\u00d1\7&\2\2\u00d1\u00eb\5\30\r\t\u00d2\u00d3"+
		"\f\7\2\2\u00d3\u00d4\7\'\2\2\u00d4\u00eb\5\30\r\b\u00d5\u00d6\f\6\2\2"+
		"\u00d6\u00d7\7(\2\2\u00d7\u00eb\5\30\r\7\u00d8\u00d9\f\5\2\2\u00d9\u00da"+
		"\7)\2\2\u00da\u00eb\5\30\r\6\u00db\u00dc\f\4\2\2\u00dc\u00dd\7*\2\2\u00dd"+
		"\u00eb\5\30\r\5\u00de\u00df\f\3\2\2\u00df\u00e0\7\n\2\2\u00e0\u00eb\5"+
		"\30\r\3\u00e1\u00e2\f\22\2\2\u00e2\u00e6\7\24\2\2\u00e3\u00e7\5\32\16"+
		"\2\u00e4\u00e7\5\36\20\2\u00e5\u00e7\5\34\17\2\u00e6\u00e3\3\2\2\2\u00e6"+
		"\u00e4\3\2\2\2\u00e6\u00e5\3\2\2\2\u00e7\u00eb\3\2\2\2\u00e8\u00e9\f\21"+
		"\2\2\u00e9\u00eb\t\3\2\2\u00ea\u00c0\3\2\2\2\u00ea\u00c3\3\2\2\2\u00ea"+
		"\u00c6\3\2\2\2\u00ea\u00c9\3\2\2\2\u00ea\u00cc\3\2\2\2\u00ea\u00cf\3\2"+
		"\2\2\u00ea\u00d2\3\2\2\2\u00ea\u00d5\3\2\2\2\u00ea\u00d8\3\2\2\2\u00ea"+
		"\u00db\3\2\2\2\u00ea\u00de\3\2\2\2\u00ea\u00e1\3\2\2\2\u00ea\u00e8\3\2"+
		"\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed"+
		"\31\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef\u00f0\7\66\2\2\u00f0\33\3\2\2\2"+
		"\u00f1\u00f6\7\66\2\2\u00f2\u00f3\7+\2\2\u00f3\u00f4\5\30\r\2\u00f4\u00f5"+
		"\7,\2\2\u00f5\u00f7\3\2\2\2\u00f6\u00f2\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8"+
		"\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\35\3\2\2\2\u00fa\u00fb\7\66\2"+
		"\2\u00fb\u0104\7\7\2\2\u00fc\u0101\5\30\r\2\u00fd\u00fe\7\t\2\2\u00fe"+
		"\u0100\5\30\r\2\u00ff\u00fd\3\2\2\2\u0100\u0103\3\2\2\2\u0101\u00ff\3"+
		"\2\2\2\u0101\u0102\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0104"+
		"\u00fc\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0107\7\b"+
		"\2\2\u0107\37\3\2\2\2\u0108\u010d\5$\23\2\u0109\u010a\7+\2\2\u010a\u010b"+
		"\5\30\r\2\u010b\u010c\7,\2\2\u010c\u010e\3\2\2\2\u010d\u0109\3\2\2\2\u010e"+
		"\u010f\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0113\3\2"+
		"\2\2\u0111\u0112\7+\2\2\u0112\u0114\7,\2\2\u0113\u0111\3\2\2\2\u0114\u0115"+
		"\3\2\2\2\u0115\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u011b\3\2\2\2\u0117"+
		"\u0118\7+\2\2\u0118\u0119\5\30\r\2\u0119\u011a\7,\2\2\u011a\u011c\3\2"+
		"\2\2\u011b\u0117\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011b\3\2\2\2\u011d"+
		"\u011e\3\2\2\2\u011e\u0135\3\2\2\2\u011f\u0124\5$\23\2\u0120\u0121\7+"+
		"\2\2\u0121\u0122\5\30\r\2\u0122\u0123\7,\2\2\u0123\u0125\3\2\2\2\u0124"+
		"\u0120\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2"+
		"\2\2\u0127\u012c\3\2\2\2\u0128\u0129\7+\2\2\u0129\u012b\7,\2\2\u012a\u0128"+
		"\3\2\2\2\u012b\u012e\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d"+
		"\u0135\3\2\2\2\u012e\u012c\3\2\2\2\u012f\u0130\5$\23\2\u0130\u0131\7\7"+
		"\2\2\u0131\u0132\7\b\2\2\u0132\u0135\3\2\2\2\u0133\u0135\5$\23\2\u0134"+
		"\u0108\3\2\2\2\u0134\u011f\3\2\2\2\u0134\u012f\3\2\2\2\u0134\u0133\3\2"+
		"\2\2\u0135!\3\2\2\2\u0136\u0139\5$\23\2\u0137\u0139\5&\24\2\u0138\u0136"+
		"\3\2\2\2\u0138\u0137\3\2\2\2\u0139#\3\2\2\2\u013a\u013d\5(\25\2\u013b"+
		"\u013d\7\66\2\2\u013c\u013a\3\2\2\2\u013c\u013b\3\2\2\2\u013d%\3\2\2\2"+
		"\u013e\u0143\5$\23\2\u013f\u0140\7+\2\2\u0140\u0142\7,\2\2\u0141\u013f"+
		"\3\2\2\2\u0142\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144"+
		"\'\3\2\2\2\u0145\u0143\3\2\2\2\u0146\u0147\t\t\2\2\u0147)\3\2\2\2\u0148"+
		"\u0149\t\n\2\2\u0149+\3\2\2\2\"/\67?ARZilrx\u0085\u008a\u008e\u0092\u009e"+
		"\u00aa\u00be\u00e6\u00ea\u00ec\u00f8\u0101\u0104\u010f\u0115\u011d\u0126"+
		"\u012c\u0134\u0138\u013c\u0143";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}