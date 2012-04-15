package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;

public class FunctionAssignExpression extends Expression {

	private final String name;
	private final String[] args;
	private final Expression body;
	
	public FunctionAssignExpression(String name, String[] args, Expression body, CharPosition position) {
		super(position);
		this.name = name;
		this.args = args;
		this.body = body;
	}
	
	public String getName() {
		return name;
	}

	public String[] getArgs() {
		return args;
	}

	public Expression getBody() {
		return body;
	}

	@Override
	public void print(StringBuilder builder) {
		//TODO: print this
		builder.append(name);//.append('(');
	}

	@Override
	public <R, C> R accept(ExpressionVisitor<R,C> visitor, C context) {
		return visitor.visit(this, context);
	};

}
