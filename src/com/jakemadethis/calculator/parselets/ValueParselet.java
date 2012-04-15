package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.DefaultTokenTypes;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.OperatorExpression;
import com.jakemadethis.calculator.expressions.ValueExpression;

public class ValueParselet implements PrefixParselet {

	private final int multiplyPrecedence;
	public ValueParselet(int multiplyPrecedence) {
		this.multiplyPrecedence = multiplyPrecedence;
	}
	
	@Override
	public Expression parse(Parser parser, Token token) {
		String text = token.getLabel();
		CharPosition position = new CharPosition(
				token.getStart(), text.length());
		
		ValueExpression expr = new ValueExpression(Double.valueOf(text), position);
		
		// Now check if :
		// 2x
		// 2f(x)
		Token ahead = parser.lookAhead(0);
		if (ahead.getType() == DefaultTokenTypes.NAME || ahead.getType() == DefaultTokenTypes.LEFT_BRACKET) {
			Expression right = parser.parseExpression(multiplyPrecedence);
			
			Token operator = new Token(DefaultTokenTypes.MULTIPLY, "*", -1);
			return new OperatorExpression(expr, operator, right);
		}
		
		return expr;
	}

}
