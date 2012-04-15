package com.jakemadethis.guiconsole;

public class Buffer {

	int cursorX = 0;
	int cursorY = 0;
	char[][] buffer;
	int[][] colors; // rgb
	public int defaultColor = 0;
	protected final int width;
	protected final int height;
	
	
	public Buffer(int width, int height) {
		this.width = width;
		this.height = height;
		buffer = new char[height][width];
		colors = new int[height][width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				buffer[j][i] = ' ';
				colors[j][i] = 0;
			}
		}
		

	}
	
	public void cursorRight() {
		setCursorPos(cursorX+1, cursorY);
	}
	public void cursorLeft() {
		setCursorPos(cursorX-1, cursorY);
	}
	public void cursorUp() {
		setCursorPos(cursorX, cursorY-1);
	}
	public void cursorDown() {
		setCursorPos(cursorX, cursorY+1);
	}
	
	public char getChar(int x, int y) {
		return buffer[y][x];
	}
	public char getCursorChar() {
		return buffer[cursorY][cursorX];
	}
	
	public int getCursorX() {
		return cursorX;
	}
	public int getCursorY() {
		return cursorY;
	}

	public void deleteLeft() {
		if (cursorX > 0) {
			cursorX --;
			buffer[cursorY][cursorX] = ' ';
		}
	}
	
	public void deleteRight() {
		if (cursorX < width-1) {
			buffer[cursorY][cursorY] = ' ';
			cursorX ++;
		}
	}

	public void newLine() {
		cursorX = 0;
		cursorY ++;
	}

	public void putChar(char ch) {
		putChar(ch, defaultColor);
	}
	public void putChar(char ch, int color) {
		colors[cursorY][cursorX] = color;
		buffer[cursorY][cursorX] = ch;
		cursorX++;
	}

	public void setCursorPos(int x, int y) {
		x = Math.min(Math.max(x, 0), width);
		y = Math.min(Math.max(y, 0), height);
		cursorX = x;
		cursorY = y;
	}

	public int getColor(int x, int y) {
		return colors[y][x];
	}

}
