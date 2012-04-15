package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;

public class CallExpression extends Expression {

	private final String name;
	private final Expression[] args;
	
	public CallExpression(String name, Expression[] args, CharPosition position) {
		super(position);
		this.name = name;
		this.args = args;
	}
	
	public String getName() {
		return name;
	}

	public Expression[] getArgs() {
		return args;
	}

	@Override
	public <R, C> R accept(ExpressionVisitor<R,C> visitor, C context) {
		return visitor.visit(this, context);
	};
	
	@Override
	public void print(StringBuilder builder) {
		builder.append(name).append('(');
		
		for (int i = 0; i < args.length; i++) {
			args[i].print(builder);
			if (i < args.length - 1)
				builder.append(',');
		}
		
		builder.append(')');
	}

}
