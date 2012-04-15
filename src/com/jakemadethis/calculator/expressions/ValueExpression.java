package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;

public class ValueExpression extends Expression {

	private final double value;
	public ValueExpression(double value, CharPosition position) {
		super(position);
		this.value = value;
	}
	public ValueExpression(double value) {
		super(null);
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public void print(StringBuilder builder) {
		builder.append(value);
	}

	@Override
	public <R, C> R accept(ExpressionVisitor<R,C> visitor, C context) {
		return visitor.visit(this, context);
	};

}
