package com.jakemadethis.calculator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jakemadethis.calculator.parselets.BinaryOperatorParselet;
import com.jakemadethis.calculator.parselets.InfixParselet;
import com.jakemadethis.calculator.parselets.PrefixOperatorParselet;
import com.jakemadethis.calculator.parselets.PrefixParselet;

public class ParserDefinitions {
	

	
	private final Map<TokenType, PrefixParselet> 
		prefixParselets = new HashMap<TokenType, PrefixParselet>();
	private final Map<TokenType, InfixParselet> 
		infixParselets = new HashMap<TokenType, InfixParselet>();
	private final HashSet<TokenType> 
		tokenTypes = new HashSet<TokenType>();
	private final HashMap<TokenType, Operator> 
		unaryOperators = new HashMap<TokenType, Operator>();
	private final HashMap<TokenType, Operator> 
		binaryOperators = new HashMap<TokenType, Operator>();

	// Register parsers
	public void registerParser(TokenType type, InfixParselet parselet) {
		tokenTypes.add(type);
		infixParselets.put(type, parselet);
	}
	public void registerParser(TokenType type, PrefixParselet parselet) {
		tokenTypes.add(type);
		prefixParselets.put(type, parselet);
	}
	
	// Register operators

	public void registerBinaryOperator(TokenType type, Operator operator) {
		binaryOperators.put(type, operator);
	}
	public void registerUnaryOperator(TokenType type, Operator operator) {
		unaryOperators.put(type, operator);
	}
	
	public void prefix(TokenType type, int precedence, Operator operator) {
		registerUnaryOperator(type, operator);
		registerParser(type, new PrefixOperatorParselet(type, precedence));
	}
	public void infixLeft(TokenType type, int precedence, Operator operator) {
		registerBinaryOperator(type, operator);
		registerParser(type, new BinaryOperatorParselet(type, precedence, false));
	}
	public void infixRight(TokenType type, int precedence, Operator operator) {
		registerBinaryOperator(type, operator);
		registerParser(type, new BinaryOperatorParselet(type, precedence, true));
	}

	
	
	public PrefixParselet getPrefixParselet(TokenType type) {
		return prefixParselets.get(type);
	}
	public InfixParselet getInfixParselet(TokenType type) {
		return infixParselets.get(type);
	}
	public Operator getUnaryOperator(TokenType type) {
		return unaryOperators.get(type);
	}
	public Operator getBinaryOperator(TokenType type) {
		return binaryOperators.get(type);
	}
	public Set<TokenType> getTokens() {
		return tokenTypes;
	}
}
