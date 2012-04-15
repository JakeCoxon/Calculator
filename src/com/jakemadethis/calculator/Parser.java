package com.jakemadethis.calculator;

import java.util.ArrayList;
import java.util.List;

import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.parselets.InfixParselet;
import com.jakemadethis.calculator.parselets.PrefixParselet;

public class Parser {
	private final Lexer lexer;
	private final List<Token> readAhead = new ArrayList<Token>();
	private final ParserDefinitions defs;
	
	public Parser(ParserDefinitions defs, Lexer lexer) {
		this.defs = defs;
		this.lexer = lexer;
	}
	
	
	public Expression parseExpression() {
		return parseExpression(0);
	}
	public Expression parseExpression(int precedence) {
		Token token = consume();
		

		PrefixParselet prefixParselet = defs.getPrefixParselet(token.getType());
		
		if (prefixParselet == null) 
			throw new TokenException("Could not parse", token);
		
		
		Expression left = prefixParselet.parse(this, token);
		
		while (precedence < getPrecedence()) {
			token = consume();
			InfixParselet infixParselet = defs.getInfixParselet(token.getType());

			left = infixParselet.parse(this, left, token);
		}
		
		// No more
		return left;
		
	}
	
	private int getPrecedence() {
		InfixParselet parser = defs.getInfixParselet(lookAhead(0).getType());
		if (parser != null) return parser.getPrecedence();
		
		return 0;
	}
	
	public Token consume() {
		lookAhead(0);
		return readAhead.remove(0);
	}
	
	public Token consume(TokenType expectedType) {
		Token token = lookAhead(0);
		if (token.getType() != expectedType) 
			throw new TokenException("Expected "+expectedType.toString(), token);
		
		return consume();
	}
	
	public Token lookAhead(int length) {
		if (lexer == null)
			return new Token(DefaultTokenTypes.EOF, "", -1);
		
		while (readAhead.size() <= length) {
			readAhead.add(lexer.next());
		}
		
		return readAhead.get(length);
	}
}
