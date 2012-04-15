package com.jakemadethis.calculator.expressions;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.TokenType;

public class OperatorExpression extends Expression {

	private final Expression left;
	private final Expression right;
	private final TokenType operator;
	private final Token operatorToken;
	
	public OperatorExpression(Expression left, Token operatorToken, 
			Expression right) {
		super(CharPosition.create(left.getPosition(), right.getPosition()));
		this.left = left;
		this.operatorToken = operatorToken;
		this.operator = operatorToken.getType();
		this.right = right;
	}
	public OperatorExpression(Expression left, TokenType operator, 
			Expression right) {
		super(CharPosition.create(left.getPosition(), left.getPosition()));
		this.left = left;
		this.operatorToken = null;
		this.operator = operator;
		this.right = right;
	}
	
	public Token getOperatorToken() {
		return operatorToken;
	}
	public TokenType getOperatorType() {
		return operator;
	}
	
	public Expression getLeft() {
		return left;
	}
	public Expression getRight() {
		return right;
	}
	
	@Override
	public <R, C> R accept(ExpressionVisitor<R,C> visitor, C context) {
		return visitor.visit(this, context);
	};
	
	@Override
	public void print(StringBuilder builder) {
		builder.append('(');
		left.print(builder);
		if (operatorToken == null)
			builder.append(' ').append(operator.toString()).append(' ');
		else
			builder.append(' ').append(operatorToken.getLabel()).append(' ');
		right.print(builder);
		builder.append(')');
	}

}
