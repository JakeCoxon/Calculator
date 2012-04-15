package com.jakemadethis.calculator;

public abstract class Function extends Obj {
	
	private final int argsCount;
	public Function(int argsCount) {
		this.argsCount = argsCount;
	}
	
	public int getArgsCount() {
		return argsCount;
	}
	
	public abstract Double invoke(Double[] args, ExpressionEvaluator evaluator);

}
