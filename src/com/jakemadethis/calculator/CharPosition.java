package com.jakemadethis.calculator;

public class CharPosition {
	
	public static CharPosition create(CharPosition start, CharPosition end) {

		if (start == null || end == null) return null;

		int startPos = start.getStart();
		int length = end.getStart() + end.getLength() - startPos;
		return new CharPosition(startPos, length);
	}
	
	private final int start;
	private final int length;
	public CharPosition(int start, int length) {
		this.start = start;
		this.length = length;
	}
	
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return start+length;
	}
	public int getLength() {
		return length;
	}
}
