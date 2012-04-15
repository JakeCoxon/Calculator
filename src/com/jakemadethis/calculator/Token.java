package com.jakemadethis.calculator;

public class Token {
	
	
	private final TokenType type;
	private final String label;
	private final int pos;
	
	
	public Token(TokenType type, String label, int pos) {
		this.type = type;
		this.label = label;
		this.pos = pos;
	}
	
	public TokenType getType() {
		return type;
	}
	public String getLabel() {
		return label;
	}
	public int getStart() {
		return pos;
	}
	public int getEnd() {
		return pos + label.length();
	}
	
	@Override
	public String toString() {
		return label + " " + type.toString();
	}
}
