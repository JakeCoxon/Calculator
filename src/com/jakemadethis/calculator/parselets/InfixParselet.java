package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.Expression;

public interface InfixParselet {
	int getPrecedence();
	Expression parse(Parser parser, Expression left, Token token);
}
