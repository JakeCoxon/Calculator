package com.jakemadethis.calculator.parselets;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.ExpressionException;
import com.jakemadethis.calculator.Parser;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.AssignExpression;
import com.jakemadethis.calculator.expressions.CallExpression;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.FunctionAssignExpression;
import com.jakemadethis.calculator.expressions.NameExpression;

public class AssignParselet implements InfixParselet {

	private final int precedence;
	public AssignParselet(int precedence) {
		this.precedence = precedence;
	}
	
	@Override
	public int getPrecedence() {
		return precedence;
	}

	@Override
	public Expression parse(Parser parser, Expression left, Token token) {
		if (left instanceof CallExpression) {
			CallExpression functionExpr = (CallExpression)left;
			Expression body = parser.parseExpression(precedence-1);
			
			CharPosition position = CharPosition.create(
					functionExpr.getPosition(), body.getPosition());
			
			Expression[] exprArgs = functionExpr.getArgs();
			String[] args = new String[exprArgs.length];
			
			for (int i=0; i < exprArgs.length; i++) {
				
				if (!(exprArgs[i] instanceof NameExpression))
					throw new ExpressionException("Argument must be an identifier", exprArgs[i].getPosition());
				
				args[i] = ((NameExpression)exprArgs[i]).getName();
			}
			
			return new FunctionAssignExpression(functionExpr.getName(), args, 
					body, position);
		}
		if (left instanceof NameExpression) {
			NameExpression nameExpr = (NameExpression)left;
			Expression body = parser.parseExpression(precedence-1);
			
			CharPosition position = CharPosition.create(
					left.getPosition(), body.getPosition());
			return new AssignExpression(nameExpr.getName(), body, position);
		}

		throw new ExpressionException("Left side must be an identifier", left.getPosition());
	}

}
