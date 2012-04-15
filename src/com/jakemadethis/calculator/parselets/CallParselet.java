package com.jakemadethis.calculator.parselets;

import java.util.LinkedList;
import java.util.List;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.DefaultTokenTypes;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.CallExpression;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.NameExpression;

public class CallParselet implements InfixParselet {

	private final int precedence;
	public CallParselet(int precdence) {
		this.precedence = precdence;
	}
	
	@Override
	public int getPrecedence() {
		return precedence;
	}

	@Override
	public Expression parse(Parser parser, Expression left, Token token) {
		if (!(left instanceof NameExpression))
			throw new RuntimeException("Function name must be a NameExpression");
		
		String name = ((NameExpression)left).getName();
		
		List<Expression> args = new LinkedList<Expression>();
		
		Token finalToken;
		
		// At least one arg
		if (parser.lookAhead(0).getType() == DefaultTokenTypes.RIGHT_BRACKET) {
			finalToken = parser.consume();
		} else {
			while (true) {
				Expression arg = parser.parseExpression();
				args.add(arg);
				
				if (parser.lookAhead(0).getType() == DefaultTokenTypes.RIGHT_BRACKET) {
					finalToken = parser.consume();
					break;
				}
				
				parser.consume(DefaultTokenTypes.COMMA);
			}
		}
		
		int endpos = finalToken.getStart() + finalToken.getLabel().length();
		int startpos = left.getPosition().getStart();
		CharPosition position = new CharPosition(startpos, endpos-startpos);
		
		return new CallExpression(name, args.toArray(new Expression[0]), position);
	}

}
