package com.jakemadethis.calculator;

public enum DefaultTokenTypes implements TokenType {
	LEFT_BRACKET ('('),
	RIGHT_BRACKET (')'),
	COMMA (','),
	
	CARET('^'),
	PLUS('+'),
	MINUS('-'),
	DIVIDE('/'),
	MULTIPLY('*'),
	
	NAME,
	NUMBER,
	EOF;
	
	private final char punctuator;
	private DefaultTokenTypes(char punctuator) {
		this.punctuator = punctuator;
	}
	private DefaultTokenTypes() {
		punctuator = 0;
	}
	
	@Override
	public Character punctuator() {
		return punctuator != 0 ? punctuator : null;
	}
	
}
