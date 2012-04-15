package com.jakemadethis.calculator;

public class SingleTokenType implements TokenType {
	private final char punctuator;
	public SingleTokenType(char punctuator) {
		this.punctuator = punctuator;
	}
	public SingleTokenType() {
		punctuator = 0;
	}
	
	@Override
	public Character punctuator() {
		return punctuator != 0 ? punctuator : null;
	}
}
