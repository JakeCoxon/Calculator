package com.jakemadethis.calculatorgui;

import com.jakemadethis.calculator.DefaultTokenTypes;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.OperatorExpression;
import com.jakemadethis.calculator.expressions.UnaryExpression;
import com.jakemadethis.calculator.expressions.UnaryExpression.Fixity;
import com.jakemadethis.calculator.parselets.InfixParselet;

public class PercentParselet implements InfixParselet {

	private final int precedence;
	public PercentParselet(int precedence) {
		this.precedence = precedence;
	}
	
	@Override
	public int getPrecedence() {
		return precedence;
	}

	@Override
	public Expression parse(Parser parser, Expression left, Token token) {
		int start = left.getPosition().getStart();
		
		Expression expr = new UnaryExpression(token, left, Fixity.POST);
		
		Token ahead = parser.lookAhead(0);
		if (ahead.getType() == DefaultTokenTypes.NAME && ahead.getLabel().equals("of")) {
			parser.consume(); // 'of'
			Expression right = parser.parseExpression(precedence);
			
			Token of = new Token(MyParserDefinitions.PERCENT_OF, ahead.getLabel(), ahead.getStart());
			return new OperatorExpression(expr, of, right);
		}
		else if (ahead.getType() == DefaultTokenTypes.NAME && ahead.getLabel().equals("on")) {
			parser.consume(); // 'on'
			Expression right = parser.parseExpression(precedence);
			
			Token on = new Token(MyParserDefinitions.PERCENT_ON, ahead.getLabel(), ahead.getStart());
			return new OperatorExpression(expr, on, right);
		}
		
		return expr;
	}

}
