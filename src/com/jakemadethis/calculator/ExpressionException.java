package com.jakemadethis.calculator;

public class ExpressionException extends RuntimeException {
	
	private final CharPosition position;
	
	public ExpressionException(String message, CharPosition position) {
		super(message);
		this.position = position;
	}
	
	public CharPosition getPosition() {
		return position;
	}
}
