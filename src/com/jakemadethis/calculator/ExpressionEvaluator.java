package com.jakemadethis.calculator;

import com.jakemadethis.calculator.expressions.AssignExpression;
import com.jakemadethis.calculator.expressions.CallExpression;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.ExpressionVisitor;
import com.jakemadethis.calculator.expressions.FunctionAssignExpression;
import com.jakemadethis.calculator.expressions.NameExpression;
import com.jakemadethis.calculator.expressions.OperatorExpression;
import com.jakemadethis.calculator.expressions.UnaryExpression;
import com.jakemadethis.calculator.expressions.ValueExpression;

public class ExpressionEvaluator implements ExpressionVisitor<Double, Scope> {

	private final ParserDefinitions parserDefinitions;
	public ExpressionEvaluator(ParserDefinitions parserDefinitions) {
		this.parserDefinitions = parserDefinitions;
	}
	
	
	@Override
	public Double visit(AssignExpression expr, Scope scope) {
		Double value = expr.getBody().accept(this, scope);
		scope.define(expr.getName(), value);
		return value;
	}
	
	@Override
	public Double visit(FunctionAssignExpression expr, Scope scope) {
		//Expression newExpr = ExpressionReplacer.replaceVariablesExcept(expr.getArgs(), expr.getBody(), this);
		Function function = new ExpressionFunction(expr.getBody(), expr.getArgs(), scope.push());
		scope.define(expr.getName(), function);
		return null;
	}

	@Override
	public Double visit(CallExpression expr, Scope scope) {
		// Evaluate all args
		Expression[] exprArgs = expr.getArgs();
		Double[] args = new Double[exprArgs.length];
		
		for (int i = 0; i < exprArgs.length; i ++) {
			args[i] = exprArgs[i].accept(this, scope);
		}
		
		Double value;
		
		Function function = scope.getFunction(expr.getName());
		if (function == null)
			throw new ExpressionException("Function doesn't exist", expr.getPosition());
		
		try {
			value = function.invoke(args, this);
		} catch (RuntimeException e) {
			throw new ExpressionException(e.getMessage(), expr.getPosition());
		}
		
		return value;
	}

	@Override
	public Double visit(NameExpression expr, Scope scope) {
		Double value = scope.getVariable(expr.getName());
		if (value == null) 
			throw new ExpressionException("Variable doesn't exist", expr.getPosition());
		return value;
	}

	@Override
	public Double visit(OperatorExpression expr, Scope scope) {
		Double a = expr.getLeft().accept(this, scope);
		Double b = expr.getRight().accept(this, scope);
		try {
			return binaryOperator(expr.getOperatorType(), a, b);
		}
		catch(RuntimeException ex) {
			throw new TokenException("Invalid operator", expr.getOperatorToken());
		}
		
	}

	@Override
	public Double visit(UnaryExpression expr, Scope scope) {
		Double body = expr.getBody().accept(this, scope);
		try {
			return unaryOperator(expr.getOperatorType(), body);
		}
		catch(RuntimeException ex) {
			throw new TokenException("Invalid operator", expr.getOperator());
		}
	}

	@Override
	public Double visit(ValueExpression expr, Scope scope) {
		return expr.getValue();
	}
	
	protected Double binaryOperator(TokenType operator, Double value1, Double value2) {
		return parserDefinitions.getBinaryOperator(operator).evaluate(new Double[] {value1, value2});
	}
	protected Double unaryOperator(TokenType operator, Double value) {
		return parserDefinitions.getUnaryOperator(operator).evaluate(new Double[] {value});
	}


}
