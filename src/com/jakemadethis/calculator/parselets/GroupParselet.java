package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.DefaultTokenTypes;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.Expression;

public class GroupParselet implements PrefixParselet {

	@Override
	public Expression parse(Parser parser, Token token) {
		Expression expr = parser.parseExpression();
		parser.consume(DefaultTokenTypes.RIGHT_BRACKET);
		return expr;
	}

}
