package com.jakemadethis.calculator;

public class TokenException extends RuntimeException {
	
	private final Token token;
	
	public TokenException(String message, Token token) {
		super(message);
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
}
