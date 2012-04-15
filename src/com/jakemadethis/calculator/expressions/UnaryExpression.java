package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.TokenType;

public class UnaryExpression extends Expression {

	public enum Fixity {
		POST, PRE
	}
	private final Token operator;
	private final Expression body;
	private final Fixity fix;
	
	public UnaryExpression(Token operator, Expression body, Fixity fix) {
		super(getPosition(operator, body, fix));
		this.operator = operator;
		this.body = body;
		this.fix = fix;
	}
	
	private static CharPosition getPosition(Token operator, Expression body, Fixity fix) {
		if (fix == Fixity.POST) {
			int start = body.getPosition().getStart();
			return new CharPosition(start,
					operator.getEnd() - start);
		}
		else {
			int start = operator.getStart();
			return new CharPosition(start,
					body.getPosition().getEnd() - start);
		}
	}
	
	public TokenType getOperatorType() {
		return operator.getType();
	}
	public Token getOperator() {
		return operator;
	}
	public Expression getBody() {
		return body;
	}
	public Fixity getFix() {
		return fix;
	}
	
	@Override
	public <R, C> R accept(ExpressionVisitor<R,C> visitor, C context) {
		return visitor.visit(this, context);
	};
	
	@Override
	public void print(StringBuilder builder) {
		if (fix == Fixity.PRE) {
			builder.append(operator.getLabel());
			body.print(builder);
		}
		else {
			body.print(builder);
			builder.append(operator.getLabel());
		}
	}

}
