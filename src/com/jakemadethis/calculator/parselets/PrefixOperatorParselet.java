package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.TokenType;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.UnaryExpression;
import com.jakemadethis.calculator.expressions.UnaryExpression.Fixity;

public class PrefixOperatorParselet implements PrefixParselet {
	
	private final TokenType operator;
	private final int precedence;
	public PrefixOperatorParselet(TokenType operator, int precedence) {
		this.operator = operator;
		this.precedence = precedence;
	}
	
	@Override
	public Expression parse(Parser parser, Token token) {
		Expression operand = parser.parseExpression(precedence);
		
		return new UnaryExpression(token, operand, Fixity.PRE);
	}
	
	public int getPrecedence() {
		return precedence;
	}

}
