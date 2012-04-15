package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.TokenType;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.OperatorExpression;

public class BinaryOperatorParselet implements InfixParselet {

	private final TokenType operator;
	private final int precedence;
	private final boolean isRightAssociation;
	public BinaryOperatorParselet(TokenType type, int precedence, 
			boolean isRightAssociation) {
		this.operator = type;
		this.precedence = precedence;
		this.isRightAssociation = isRightAssociation;
	}
	
	@Override
	public Expression parse(Parser parser, Expression left, Token token) {
		Expression right = parser
			.parseExpression(precedence - (isRightAssociation ? 1 : 0));
		
		return new OperatorExpression(left, token, right);
	}

	@Override
	public int getPrecedence() {
		return precedence;
	}

}
