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
		T__45=46, VOID=47, BoolLiteral=48, IntLiteral=49, StringLiteral=50, ESC=51, 
		Identifier=52, WS=53, NewLine=54, LineComment=55, BlockComment=56;
	public static final int
		RULE_program = 0, RULE_def = 1, RULE_classDef = 2, RULE_varDefList = 3, 
		RULE_varDef = 4, RULE_functDef = 5, RULE_paraList = 6, RULE_para = 7, 
		RULE_block = 8, RULE_stmt = 9, RULE_exprList = 10, RULE_expr = 11, RULE_creator = 12, 
		RULE_type = 13, RULE_varType = 14, RULE_arrayType = 15, RULE_primType = 16, 
		RULE_literal = 17;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "def", "classDef", "varDefList", "varDef", "functDef", "paraList", 
			"para", "block", "stmt", "exprList", "expr", "creator", "type", "varType", 
			"arrayType", "primType", "literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "';'", "','", "'='", "'('", "')'", "'if'", 
			"'else'", "'for'", "'while'", "'return'", "'break'", "'continue'", "'this'", 
			"'new'", "'.'", "'['", "']'", "'++'", "'--'", "'+'", "'-'", "'~'", "'!'", 
			"'*'", "'/'", "'%'", "'<<'", "'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", 
			"'!='", "'&'", "'^'", "'|'", "'&&'", "'||'", "'bool'", "'int'", "'string'", 
			"'null'", "'void'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "VOID", 
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
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << VOID) | (1L << Identifier))) != 0)) {
				{
				{
				setState(36);
				def();
				}
				}
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(42);
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
			setState(47);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				classDef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				varDefList();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
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
			setState(49);
			match(T__0);
			setState(50);
			match(Identifier);
			setState(51);
			match(T__1);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << VOID) | (1L << Identifier))) != 0)) {
				{
				setState(54);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(52);
					varDefList();
					}
					break;
				case 2:
					{
					setState(53);
					functDef();
					}
					break;
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(59);
			match(T__2);
			setState(60);
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
		enterRule(_localctx, 6, RULE_varDefList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			type();
			setState(63);
			varDef();
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(64);
				match(T__4);
				setState(65);
				varDef();
				}
				}
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(71);
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
		enterRule(_localctx, 8, RULE_varDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(Identifier);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(74);
				match(T__5);
				setState(75);
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
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
		public ParaListContext paraList() {
			return getRuleContext(ParaListContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode VOID() { return getToken(MxstarParser.VOID, 0); }
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
		enterRule(_localctx, 10, RULE_functDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__42:
			case T__43:
			case T__44:
			case Identifier:
				{
				setState(78);
				type();
				}
				break;
			case VOID:
				{
				setState(79);
				match(VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(82);
			match(Identifier);
			setState(83);
			match(T__6);
			setState(84);
			paraList();
			setState(85);
			match(T__7);
			setState(86);
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
		enterRule(_localctx, 12, RULE_paraList);
		int _la;
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case T__42:
			case T__43:
			case T__44:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				para();
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(90);
					match(T__4);
					setState(91);
					para();
					}
					}
					setState(96);
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
		enterRule(_localctx, 14, RULE_para);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			type();
			setState(100);
			match(Identifier);
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(101);
				match(T__5);
				setState(102);
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
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			match(T__1);
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__6) | (1L << T__8) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
				{
				{
				setState(106);
				stmt();
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(112);
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
		enterRule(_localctx, 18, RULE_stmt);
		int _la;
		try {
			setState(159);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				block();
				}
				break;
			case 2:
				_localctx = new IfStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				match(T__8);
				setState(116);
				match(T__6);
				setState(117);
				expr(0);
				setState(118);
				match(T__7);
				setState(119);
				stmt();
				setState(122);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(120);
					match(T__9);
					setState(121);
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
				setState(124);
				match(T__10);
				setState(125);
				match(T__6);
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__15) | (1L << T__16) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__45) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(126);
					((ForStmtContext)_localctx).init = expr(0);
					}
				}

				setState(129);
				match(T__3);
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__15) | (1L << T__16) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__45) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(130);
					((ForStmtContext)_localctx).cond = expr(0);
					}
				}

				setState(133);
				match(T__3);
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__15) | (1L << T__16) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__45) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(134);
					((ForStmtContext)_localctx).step = expr(0);
					}
				}

				setState(137);
				match(T__7);
				setState(138);
				stmt();
				}
				break;
			case 4:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(139);
				match(T__11);
				setState(140);
				match(T__6);
				setState(141);
				expr(0);
				setState(142);
				match(T__7);
				setState(143);
				stmt();
				}
				break;
			case 5:
				_localctx = new ReturnStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(145);
				match(T__12);
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__15) | (1L << T__16) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__45) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
					{
					setState(146);
					expr(0);
					}
				}

				setState(149);
				match(T__3);
				}
				break;
			case 6:
				_localctx = new BreakStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(150);
				match(T__13);
				setState(151);
				match(T__3);
				}
				break;
			case 7:
				_localctx = new ContinueStmtContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(152);
				match(T__14);
				setState(153);
				match(T__3);
				}
				break;
			case 8:
				_localctx = new VarDefStmtContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(154);
				varDefList();
				}
				break;
			case 9:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(155);
				expr(0);
				setState(156);
				match(T__3);
				}
				break;
			case 10:
				_localctx = new BrankStmtContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(158);
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

	public static class ExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).enterExprList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxstarListener ) ((MxstarListener)listener).exitExprList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxstarVisitor ) return ((MxstarVisitor<? extends T>)visitor).visitExprList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprListContext exprList() throws RecognitionException {
		ExprListContext _localctx = new ExprListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__15) | (1L << T__16) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__45) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
				{
				setState(161);
				expr(0);
				setState(166);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(162);
					match(T__4);
					setState(163);
					expr(0);
					}
					}
					setState(168);
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
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
		public TerminalNode Identifier() { return getToken(MxstarParser.Identifier, 0); }
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
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
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
			setState(187);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				{
				_localctx = new BracketExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(172);
				match(T__6);
				setState(173);
				expr(0);
				setState(174);
				match(T__7);
				}
				break;
			case T__15:
				{
				_localctx = new ThisExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(176);
				match(T__15);
				}
				break;
			case T__16:
				{
				_localctx = new CreatorExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(177);
				match(T__16);
				setState(178);
				creator();
				}
				break;
			case T__45:
			case BoolLiteral:
			case IntLiteral:
			case StringLiteral:
				{
				_localctx = new LiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(179);
				literal();
				}
				break;
			case Identifier:
				{
				_localctx = new VarExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(180);
				match(Identifier);
				}
				break;
			case T__22:
			case T__23:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(181);
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
				setState(182);
				expr(14);
				}
				break;
			case T__20:
			case T__21:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(183);
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
				setState(184);
				expr(13);
				}
				break;
			case T__24:
			case T__25:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(185);
				((PrefixExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__24 || _la==T__25) ) {
					((PrefixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(186);
				expr(12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(239);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(237);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(189);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(190);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__26) | (1L << T__27) | (1L << T__28))) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(191);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(192);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(193);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__22 || _la==T__23) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(194);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(195);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(196);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__29 || _la==T__30) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(197);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(198);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(199);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34))) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(200);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(201);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(202);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__35 || _la==T__36) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(203);
						expr(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(204);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(205);
						((BinaryExprContext)_localctx).op = match(T__37);
						setState(206);
						expr(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(207);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(208);
						((BinaryExprContext)_localctx).op = match(T__38);
						setState(209);
						expr(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(210);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(211);
						((BinaryExprContext)_localctx).op = match(T__39);
						setState(212);
						expr(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(213);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(214);
						((BinaryExprContext)_localctx).op = match(T__40);
						setState(215);
						expr(4);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(216);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(217);
						((BinaryExprContext)_localctx).op = match(T__41);
						setState(218);
						expr(3);
						}
						break;
					case 11:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(219);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(220);
						((BinaryExprContext)_localctx).op = match(T__5);
						setState(221);
						expr(1);
						}
						break;
					case 12:
						{
						_localctx = new MemberExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(222);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(223);
						match(T__17);
						setState(224);
						match(Identifier);
						}
						break;
					case 13:
						{
						_localctx = new ArrayExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(225);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(226);
						match(T__18);
						setState(227);
						expr(0);
						setState(228);
						match(T__19);
						}
						break;
					case 14:
						{
						_localctx = new FunctExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(230);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(231);
						match(T__6);
						setState(232);
						exprList();
						setState(233);
						match(T__7);
						}
						break;
					case 15:
						{
						_localctx = new SuffixExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(235);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(236);
						((SuffixExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__20 || _la==T__21) ) {
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
				setState(241);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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
		enterRule(_localctx, 24, RULE_creator);
		try {
			int _alt;
			setState(286);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				_localctx = new InvalidCreatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(242);
				varType();
				setState(247); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(243);
						match(T__18);
						setState(244);
						expr(0);
						setState(245);
						match(T__19);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(249); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(253); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(251);
						match(T__18);
						setState(252);
						match(T__19);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(255); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(261); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(257);
						match(T__18);
						setState(258);
						expr(0);
						setState(259);
						match(T__19);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(263); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				_localctx = new ArrayCreatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(265);
				varType();
				setState(270); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(266);
						match(T__18);
						setState(267);
						expr(0);
						setState(268);
						match(T__19);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(272); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(278);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(274);
						match(T__18);
						setState(275);
						match(T__19);
						}
						} 
					}
					setState(280);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
				}
				}
				break;
			case 3:
				_localctx = new ClassCreatorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(281);
				varType();
				setState(282);
				match(T__6);
				setState(283);
				match(T__7);
				}
				break;
			case 4:
				_localctx = new NaiveCreatorContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(285);
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
		enterRule(_localctx, 26, RULE_type);
		try {
			setState(290);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				varType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(289);
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
		enterRule(_localctx, 28, RULE_varType);
		try {
			setState(294);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__42:
			case T__43:
			case T__44:
				enterOuterAlt(_localctx, 1);
				{
				setState(292);
				primType();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(293);
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
		enterRule(_localctx, 30, RULE_arrayType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			varType();
			setState(301);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(297);
				match(T__18);
				setState(298);
				match(T__19);
				}
				}
				setState(303);
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
		enterRule(_localctx, 32, RULE_primType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__42) | (1L << T__43) | (1L << T__44))) != 0)) ) {
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
		enterRule(_localctx, 34, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__45) | (1L << BoolLiteral) | (1L << IntLiteral) | (1L << StringLiteral))) != 0)) ) {
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
			return precpred(_ctx, 18);
		case 12:
			return precpred(_ctx, 17);
		case 13:
			return precpred(_ctx, 16);
		case 14:
			return precpred(_ctx, 15);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3:\u0137\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\7\2(\n\2\f\2\16\2+\13\2\3\2\3\2\3\3\3\3\3\3\5\3\62\n\3"+
		"\3\4\3\4\3\4\3\4\3\4\7\49\n\4\f\4\16\4<\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\7\5E\n\5\f\5\16\5H\13\5\3\5\3\5\3\6\3\6\3\6\5\6O\n\6\3\7\3\7\5\7S\n"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\7\b_\n\b\f\b\16\bb\13\b\5\b"+
		"d\n\b\3\t\3\t\3\t\3\t\5\tj\n\t\3\n\3\n\7\nn\n\n\f\n\16\nq\13\n\3\n\3\n"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13}\n\13\3\13\3\13\3\13\5\13"+
		"\u0082\n\13\3\13\3\13\5\13\u0086\n\13\3\13\3\13\5\13\u008a\n\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u0096\n\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00a2\n\13\3\f\3\f\3\f\7"+
		"\f\u00a7\n\f\f\f\16\f\u00aa\13\f\5\f\u00ac\n\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00be\n\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00f0\n\r\f\r\16\r\u00f3\13\r"+
		"\3\16\3\16\3\16\3\16\3\16\6\16\u00fa\n\16\r\16\16\16\u00fb\3\16\3\16\6"+
		"\16\u0100\n\16\r\16\16\16\u0101\3\16\3\16\3\16\3\16\6\16\u0108\n\16\r"+
		"\16\16\16\u0109\3\16\3\16\3\16\3\16\3\16\6\16\u0111\n\16\r\16\16\16\u0112"+
		"\3\16\3\16\7\16\u0117\n\16\f\16\16\16\u011a\13\16\3\16\3\16\3\16\3\16"+
		"\3\16\5\16\u0121\n\16\3\17\3\17\5\17\u0125\n\17\3\20\3\20\5\20\u0129\n"+
		"\20\3\21\3\21\3\21\7\21\u012e\n\21\f\21\16\21\u0131\13\21\3\22\3\22\3"+
		"\23\3\23\3\23\2\3\30\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2\13"+
		"\3\2\31\32\3\2\27\30\3\2\33\34\3\2\35\37\3\2 !\3\2\"%\3\2&\'\3\2-/\4\2"+
		"\60\60\62\64\2\u0161\2)\3\2\2\2\4\61\3\2\2\2\6\63\3\2\2\2\b@\3\2\2\2\n"+
		"K\3\2\2\2\fR\3\2\2\2\16c\3\2\2\2\20e\3\2\2\2\22k\3\2\2\2\24\u00a1\3\2"+
		"\2\2\26\u00ab\3\2\2\2\30\u00bd\3\2\2\2\32\u0120\3\2\2\2\34\u0124\3\2\2"+
		"\2\36\u0128\3\2\2\2 \u012a\3\2\2\2\"\u0132\3\2\2\2$\u0134\3\2\2\2&(\5"+
		"\4\3\2\'&\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*,\3\2\2\2+)\3\2\2\2,"+
		"-\7\2\2\3-\3\3\2\2\2.\62\5\6\4\2/\62\5\b\5\2\60\62\5\f\7\2\61.\3\2\2\2"+
		"\61/\3\2\2\2\61\60\3\2\2\2\62\5\3\2\2\2\63\64\7\3\2\2\64\65\7\66\2\2\65"+
		":\7\4\2\2\669\5\b\5\2\679\5\f\7\28\66\3\2\2\28\67\3\2\2\29<\3\2\2\2:8"+
		"\3\2\2\2:;\3\2\2\2;=\3\2\2\2<:\3\2\2\2=>\7\5\2\2>?\7\6\2\2?\7\3\2\2\2"+
		"@A\5\34\17\2AF\5\n\6\2BC\7\7\2\2CE\5\n\6\2DB\3\2\2\2EH\3\2\2\2FD\3\2\2"+
		"\2FG\3\2\2\2GI\3\2\2\2HF\3\2\2\2IJ\7\6\2\2J\t\3\2\2\2KN\7\66\2\2LM\7\b"+
		"\2\2MO\5\30\r\2NL\3\2\2\2NO\3\2\2\2O\13\3\2\2\2PS\5\34\17\2QS\7\61\2\2"+
		"RP\3\2\2\2RQ\3\2\2\2ST\3\2\2\2TU\7\66\2\2UV\7\t\2\2VW\5\16\b\2WX\7\n\2"+
		"\2XY\5\22\n\2Y\r\3\2\2\2Zd\3\2\2\2[`\5\20\t\2\\]\7\7\2\2]_\5\20\t\2^\\"+
		"\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ad\3\2\2\2b`\3\2\2\2cZ\3\2\2\2c"+
		"[\3\2\2\2d\17\3\2\2\2ef\5\34\17\2fi\7\66\2\2gh\7\b\2\2hj\5\30\r\2ig\3"+
		"\2\2\2ij\3\2\2\2j\21\3\2\2\2ko\7\4\2\2ln\5\24\13\2ml\3\2\2\2nq\3\2\2\2"+
		"om\3\2\2\2op\3\2\2\2pr\3\2\2\2qo\3\2\2\2rs\7\5\2\2s\23\3\2\2\2t\u00a2"+
		"\5\22\n\2uv\7\13\2\2vw\7\t\2\2wx\5\30\r\2xy\7\n\2\2y|\5\24\13\2z{\7\f"+
		"\2\2{}\5\24\13\2|z\3\2\2\2|}\3\2\2\2}\u00a2\3\2\2\2~\177\7\r\2\2\177\u0081"+
		"\7\t\2\2\u0080\u0082\5\30\r\2\u0081\u0080\3\2\2\2\u0081\u0082\3\2\2\2"+
		"\u0082\u0083\3\2\2\2\u0083\u0085\7\6\2\2\u0084\u0086\5\30\r\2\u0085\u0084"+
		"\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0089\7\6\2\2\u0088"+
		"\u008a\5\30\r\2\u0089\u0088\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\3"+
		"\2\2\2\u008b\u008c\7\n\2\2\u008c\u00a2\5\24\13\2\u008d\u008e\7\16\2\2"+
		"\u008e\u008f\7\t\2\2\u008f\u0090\5\30\r\2\u0090\u0091\7\n\2\2\u0091\u0092"+
		"\5\24\13\2\u0092\u00a2\3\2\2\2\u0093\u0095\7\17\2\2\u0094\u0096\5\30\r"+
		"\2\u0095\u0094\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u00a2"+
		"\7\6\2\2\u0098\u0099\7\20\2\2\u0099\u00a2\7\6\2\2\u009a\u009b\7\21\2\2"+
		"\u009b\u00a2\7\6\2\2\u009c\u00a2\5\b\5\2\u009d\u009e\5\30\r\2\u009e\u009f"+
		"\7\6\2\2\u009f\u00a2\3\2\2\2\u00a0\u00a2\7\6\2\2\u00a1t\3\2\2\2\u00a1"+
		"u\3\2\2\2\u00a1~\3\2\2\2\u00a1\u008d\3\2\2\2\u00a1\u0093\3\2\2\2\u00a1"+
		"\u0098\3\2\2\2\u00a1\u009a\3\2\2\2\u00a1\u009c\3\2\2\2\u00a1\u009d\3\2"+
		"\2\2\u00a1\u00a0\3\2\2\2\u00a2\25\3\2\2\2\u00a3\u00a8\5\30\r\2\u00a4\u00a5"+
		"\7\7\2\2\u00a5\u00a7\5\30\r\2\u00a6\u00a4\3\2\2\2\u00a7\u00aa\3\2\2\2"+
		"\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8"+
		"\3\2\2\2\u00ab\u00a3\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\27\3\2\2\2\u00ad"+
		"\u00ae\b\r\1\2\u00ae\u00af\7\t\2\2\u00af\u00b0\5\30\r\2\u00b0\u00b1\7"+
		"\n\2\2\u00b1\u00be\3\2\2\2\u00b2\u00be\7\22\2\2\u00b3\u00b4\7\23\2\2\u00b4"+
		"\u00be\5\32\16\2\u00b5\u00be\5$\23\2\u00b6\u00be\7\66\2\2\u00b7\u00b8"+
		"\t\2\2\2\u00b8\u00be\5\30\r\20\u00b9\u00ba\t\3\2\2\u00ba\u00be\5\30\r"+
		"\17\u00bb\u00bc\t\4\2\2\u00bc\u00be\5\30\r\16\u00bd\u00ad\3\2\2\2\u00bd"+
		"\u00b2\3\2\2\2\u00bd\u00b3\3\2\2\2\u00bd\u00b5\3\2\2\2\u00bd\u00b6\3\2"+
		"\2\2\u00bd\u00b7\3\2\2\2\u00bd\u00b9\3\2\2\2\u00bd\u00bb\3\2\2\2\u00be"+
		"\u00f1\3\2\2\2\u00bf\u00c0\f\r\2\2\u00c0\u00c1\t\5\2\2\u00c1\u00f0\5\30"+
		"\r\16\u00c2\u00c3\f\f\2\2\u00c3\u00c4\t\2\2\2\u00c4\u00f0\5\30\r\r\u00c5"+
		"\u00c6\f\13\2\2\u00c6\u00c7\t\6\2\2\u00c7\u00f0\5\30\r\f\u00c8\u00c9\f"+
		"\n\2\2\u00c9\u00ca\t\7\2\2\u00ca\u00f0\5\30\r\13\u00cb\u00cc\f\t\2\2\u00cc"+
		"\u00cd\t\b\2\2\u00cd\u00f0\5\30\r\n\u00ce\u00cf\f\b\2\2\u00cf\u00d0\7"+
		"(\2\2\u00d0\u00f0\5\30\r\t\u00d1\u00d2\f\7\2\2\u00d2\u00d3\7)\2\2\u00d3"+
		"\u00f0\5\30\r\b\u00d4\u00d5\f\6\2\2\u00d5\u00d6\7*\2\2\u00d6\u00f0\5\30"+
		"\r\7\u00d7\u00d8\f\5\2\2\u00d8\u00d9\7+\2\2\u00d9\u00f0\5\30\r\6\u00da"+
		"\u00db\f\4\2\2\u00db\u00dc\7,\2\2\u00dc\u00f0\5\30\r\5\u00dd\u00de\f\3"+
		"\2\2\u00de\u00df\7\b\2\2\u00df\u00f0\5\30\r\3\u00e0\u00e1\f\24\2\2\u00e1"+
		"\u00e2\7\24\2\2\u00e2\u00f0\7\66\2\2\u00e3\u00e4\f\23\2\2\u00e4\u00e5"+
		"\7\25\2\2\u00e5\u00e6\5\30\r\2\u00e6\u00e7\7\26\2\2\u00e7\u00f0\3\2\2"+
		"\2\u00e8\u00e9\f\22\2\2\u00e9\u00ea\7\t\2\2\u00ea\u00eb\5\26\f\2\u00eb"+
		"\u00ec\7\n\2\2\u00ec\u00f0\3\2\2\2\u00ed\u00ee\f\21\2\2\u00ee\u00f0\t"+
		"\3\2\2\u00ef\u00bf\3\2\2\2\u00ef\u00c2\3\2\2\2\u00ef\u00c5\3\2\2\2\u00ef"+
		"\u00c8\3\2\2\2\u00ef\u00cb\3\2\2\2\u00ef\u00ce\3\2\2\2\u00ef\u00d1\3\2"+
		"\2\2\u00ef\u00d4\3\2\2\2\u00ef\u00d7\3\2\2\2\u00ef\u00da\3\2\2\2\u00ef"+
		"\u00dd\3\2\2\2\u00ef\u00e0\3\2\2\2\u00ef\u00e3\3\2\2\2\u00ef\u00e8\3\2"+
		"\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1"+
		"\u00f2\3\2\2\2\u00f2\31\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00f9\5\36\20"+
		"\2\u00f5\u00f6\7\25\2\2\u00f6\u00f7\5\30\r\2\u00f7\u00f8\7\26\2\2\u00f8"+
		"\u00fa\3\2\2\2\u00f9\u00f5\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00f9\3\2"+
		"\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00fe\7\25\2\2\u00fe"+
		"\u0100\7\26\2\2\u00ff\u00fd\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u00ff\3"+
		"\2\2\2\u0101\u0102\3\2\2\2\u0102\u0107\3\2\2\2\u0103\u0104\7\25\2\2\u0104"+
		"\u0105\5\30\r\2\u0105\u0106\7\26\2\2\u0106\u0108\3\2\2\2\u0107\u0103\3"+
		"\2\2\2\u0108\u0109\3\2\2\2\u0109\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a"+
		"\u0121\3\2\2\2\u010b\u0110\5\36\20\2\u010c\u010d\7\25\2\2\u010d\u010e"+
		"\5\30\r\2\u010e\u010f\7\26\2\2\u010f\u0111\3\2\2\2\u0110\u010c\3\2\2\2"+
		"\u0111\u0112\3\2\2\2\u0112\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0118"+
		"\3\2\2\2\u0114\u0115\7\25\2\2\u0115\u0117\7\26\2\2\u0116\u0114\3\2\2\2"+
		"\u0117\u011a\3\2\2\2\u0118\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u0121"+
		"\3\2\2\2\u011a\u0118\3\2\2\2\u011b\u011c\5\36\20\2\u011c\u011d\7\t\2\2"+
		"\u011d\u011e\7\n\2\2\u011e\u0121\3\2\2\2\u011f\u0121\5\36\20\2\u0120\u00f4"+
		"\3\2\2\2\u0120\u010b\3\2\2\2\u0120\u011b\3\2\2\2\u0120\u011f\3\2\2\2\u0121"+
		"\33\3\2\2\2\u0122\u0125\5\36\20\2\u0123\u0125\5 \21\2\u0124\u0122\3\2"+
		"\2\2\u0124\u0123\3\2\2\2\u0125\35\3\2\2\2\u0126\u0129\5\"\22\2\u0127\u0129"+
		"\7\66\2\2\u0128\u0126\3\2\2\2\u0128\u0127\3\2\2\2\u0129\37\3\2\2\2\u012a"+
		"\u012f\5\36\20\2\u012b\u012c\7\25\2\2\u012c\u012e\7\26\2\2\u012d\u012b"+
		"\3\2\2\2\u012e\u0131\3\2\2\2\u012f\u012d\3\2\2\2\u012f\u0130\3\2\2\2\u0130"+
		"!\3\2\2\2\u0131\u012f\3\2\2\2\u0132\u0133\t\t\2\2\u0133#\3\2\2\2\u0134"+
		"\u0135\t\n\2\2\u0135%\3\2\2\2!)\618:FNR`cio|\u0081\u0085\u0089\u0095\u00a1"+
		"\u00a8\u00ab\u00bd\u00ef\u00f1\u00fb\u0101\u0109\u0112\u0118\u0120\u0124"+
		"\u0128\u012f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}