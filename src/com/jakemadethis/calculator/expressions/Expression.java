package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;

public abstract class Expression {
	public abstract void print(StringBuilder builder);
	public abstract <R, C> R accept(ExpressionVisitor<R, C> visitor, C context);

	private final CharPosition position;
	public CharPosition getPosition() {
		return position;
	}
	public Expression(CharPosition position) {
		this.position = position;
	}
}
