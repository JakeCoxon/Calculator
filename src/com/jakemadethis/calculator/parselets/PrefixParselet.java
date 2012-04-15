package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.Expression;

public interface PrefixParselet {
	Expression parse(Parser parser, Token token);
}
