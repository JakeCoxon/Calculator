package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.NameExpression;

public class NameParselet implements PrefixParselet {

	@Override
	public Expression parse(Parser parser, Token token) {
		CharPosition position = new CharPosition(token.getStart(), 
				token.getLabel().length());
		
		return new NameExpression(token.getLabel(), position);
	}

}
