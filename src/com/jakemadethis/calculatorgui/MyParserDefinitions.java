package com.jakemadethis.calculatorgui;

import com.jakemadethis.calculator.DefaultTokenTypes;
import com.jakemadethis.calculator.Operator;
import com.jakemadethis.calculator.ParserDefinitions;
import com.jakemadethis.calculator.SingleTokenType;
import com.jakemadethis.calculator.TokenType;
import com.jakemadethis.calculator.parselets.AssignParselet;
import com.jakemadethis.calculator.parselets.CallParselet;
import com.jakemadethis.calculator.parselets.GroupParselet;
import com.jakemadethis.calculator.parselets.NameParselet;
import com.jakemadethis.calculator.parselets.ValueParselet;

/**
 * 
 * 
 * @author Jake
 *
 */
public class MyParserDefinitions {
	
	public static final ParserDefinitions PARSER_DEFINITIONS;
	static final TokenType HASH = new SingleTokenType('#');
	static final TokenType ASSIGN = new SingleTokenType('=');
	static final TokenType PERCENT = new SingleTokenType('%');
	static final TokenType PERCENT_OF = new SingleTokenType();
	static final TokenType PERCENT_ON = new SingleTokenType();
	
	private static final Operator OP_POW = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return Math.pow(args[0], args[1]); }; };

	private static final Operator OP_HASH = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return 0d; }; };
			
	private static final Operator OP_PLUS = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return args[0]+args[1]; }; };

	private static final Operator OP_MINUS = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return args[0]-args[1]; }; };
	private static final Operator OP_DIVIDE = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return args[0]/args[1]; }; };
	private static final Operator OP_MULTIPLY = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return args[0]*args[1]; }; };
	private static final Operator OP_PERCENT = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return args[0]/100; }; };
	private static final Operator OP_PERCENT_OF = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return args[0]*args[1]; }; };
	private static final Operator OP_PERCENT_ON = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return (args[0]+1d)*args[1]; }; };
			

	private static final Operator OP_UNARYMINUS = new Operator() {
		@Override public Double evaluate(Double[] args) { 
			return -args[0]; }; };
					
	public static class Precedence {
		public final static int ASSIGNMENT = 1;
		public final static int SUM = 2;
		public final static int PRODUCT = 3;
		public final static int EXPONENT = 4;
		public final static int PREFIX = 5;
		public final static int POSTFIX = 7;
		public final static int CALL = 8;
	}
	
	
	static {
		ParserDefinitions pd = PARSER_DEFINITIONS = new ParserDefinitions();
		pd.registerParser(DefaultTokenTypes.NAME, new NameParselet());
		pd.registerParser(DefaultTokenTypes.NUMBER, new ValueParselet(Precedence.PRODUCT));
		pd.registerParser(DefaultTokenTypes.LEFT_BRACKET, new GroupParselet());
		pd.registerParser(DefaultTokenTypes.LEFT_BRACKET, new CallParselet(Precedence.CALL));
		pd.registerParser(ASSIGN, new AssignParselet(Precedence.ASSIGNMENT));
		pd.registerParser(PERCENT, new PercentParselet(Precedence.POSTFIX));
		
		pd.prefix(DefaultTokenTypes.MINUS, Precedence.SUM, OP_UNARYMINUS);
		pd.prefix(HASH, Precedence.PREFIX, OP_HASH);

		pd.registerParser(PERCENT, new PercentParselet(Precedence.POSTFIX));
		pd.registerBinaryOperator(PERCENT_OF, OP_PERCENT_OF);
		pd.registerBinaryOperator(PERCENT_ON, OP_PERCENT_ON);

		pd.infixLeft(DefaultTokenTypes.PLUS, Precedence.SUM, OP_PLUS);
		pd.infixLeft(DefaultTokenTypes.MINUS, Precedence.SUM, OP_MINUS);
		pd.infixLeft(DefaultTokenTypes.MULTIPLY, Precedence.PRODUCT, OP_MULTIPLY);
		pd.infixLeft(DefaultTokenTypes.DIVIDE, Precedence.PRODUCT, OP_DIVIDE);
		pd.infixRight(DefaultTokenTypes.CARET, Precedence.EXPONENT, OP_POW);
	}
	
	
}
