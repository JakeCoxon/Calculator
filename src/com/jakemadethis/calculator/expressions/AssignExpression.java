package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;

public class AssignExpression extends Expression {

	private final String name;
	private final Expression body;
	
	public AssignExpression(String name, Expression body, CharPosition position) {
		super(position);
		this.name = name;
		this.body = body;
	}
	
	public String getName() {
		return name;
	}
	public Expression getBody() {
		return body;
	}
	
	@Override
	public <R, C> R accept(ExpressionVisitor<R,C> visitor, C context) {
		return visitor.visit(this, context);
	};
	
	@Override
	public void print(StringBuilder builder) {
		builder.append('(')
			.append(name)
			.append(" = ");
		body.print(builder);
		builder.append(')');
	}

}
