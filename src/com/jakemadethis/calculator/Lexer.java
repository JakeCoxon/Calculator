package com.jakemadethis.calculator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Lexer implements Iterator<Token> {

	private int textPos;
	private final String text;
	private final Map<Character, TokenType> punctuators;
	private final ParserDefinitions defs;
	
	public Lexer(String text, ParserDefinitions defs) {
		this.defs = defs;
		this.textPos = 0;
		this.text = text;
		
		punctuators = new HashMap<Character, TokenType>();
		
		addTokenTypes(DefaultTokenTypes.values());
		addTokenTypes(defs.getTokens().toArray(new TokenType[] {}));
	}
	public void addTokenTypes(TokenType[] types)	{
		for (TokenType type : types) {
			Character chara = type.punctuator();
			if (chara != null) {
				punctuators.put(chara, type);
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return true;
	}
	
	private char lookAhead(int num) {
		if (textPos + num >= text.length()) 
			return ' ';
		return text.charAt(textPos + num);
	}
	
	private boolean isLetter(char chara) {
		return chara >= 'A' && chara <= 'Z' || chara >= 'a' && chara <= 'z';
	}
	private boolean isDigit(char chara) {
		return chara >= '0' && chara <= '9';
	}
	private boolean isLetterOrDigit(char chara) {
		return isLetter(chara) || isDigit(chara);
	}

	@Override
	public Token next() {
		while (textPos < text.length()) {
			char chara = text.charAt(textPos++);
			
			if (punctuators.containsKey(chara)) {
				return new Token(punctuators.get(chara), Character.toString(chara), textPos-1);
			}
			else if (isLetter(chara)) {
				int startPos = textPos - 1;
				while (textPos < text.length() && 
						isLetterOrDigit(text.charAt(textPos))) {
					textPos ++;
				}
				
				String label = text.substring(startPos, textPos);
				return new Token(DefaultTokenTypes.NAME, label, startPos);
			}
			else if (isDigit(chara)) {
				int startPos = textPos - 1;
				while (textPos < text.length() &&
						isDigit(lookAhead(0)) ||
						(lookAhead(0) == '.' && Character.isDigit(lookAhead(1))) ) {
					textPos ++;
				}
				
				String label = text.substring(startPos, textPos);
				return new Token(DefaultTokenTypes.NUMBER, label, startPos);
			}
			else {
				// Ignore
			}
		}
		
		return new Token(DefaultTokenTypes.EOF, "", -1);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
