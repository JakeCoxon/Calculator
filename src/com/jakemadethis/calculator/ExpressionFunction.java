package com.jakemadethis.calculator;

import java.util.HashMap;
import java.util.Map;

import com.jakemadethis.calculator.expressions.Expression;

public class ExpressionFunction extends Function {

	private final Expression body;
	private final String[] argumentNames;
	private final Map<String, Double> argMap;
	private final Scope scope;
	
	public ExpressionFunction(Expression body, String[] args, Scope newScope) {
		super(args.length);
		this.argumentNames = args;
		this.body = body;
		this.scope = newScope;
		
		
		argMap = new HashMap<String, Double>();
		for (int i=0; i < args.length; i++) {
			argMap.put(args[i], 0.0);
		}
	}
	
	protected Scope getScope() {
		return scope;
	}
	
	@Override
	public Double invoke(Double[] argumentValues, ExpressionEvaluator evaluator) {
		if (argumentValues.length != argumentNames.length)
			throw new RuntimeException("Invalid argument count");

		Scope scope = getScope();

		scope.clearLocals();
		for (int i=0; i < argumentValues.length; i++) {
			scope.define(argumentNames[i], argumentValues[i]);
		}

		return body.accept(evaluator, scope);
	}
	
	/*private static class FunctionEvaluator extends ExpressionEvaluator
	{
		private final Map<String, Double> argMap;

		public FunctionEvaluator(Map<String, Double> argMap) {
			this.argMap = argMap;
		}

		@Override
		public Double visit(NameExpression expr, Scope scope) {
			String name = expr.getName();
			Double value = argMap.get(name);
			
			// if this doesn't exist in map, we didn't remove all 'global' vars
			if (value == null)
				throw new ExpressionException("Variable doesn't exist", expr.getPosition());
			return value;
		}
	}*/

}
