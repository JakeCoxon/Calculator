package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;

public class NameExpression extends Expression {

	private final String name;
	public NameExpression(String name, CharPosition position) {
		super(position);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public <R, C> R accept(ExpressionVisitor<R,C> visitor, C context) {
		return visitor.visit(this, context);
	};
	
	@Override
	public void print(StringBuilder builder) {
		builder.append(name);
	}

}
