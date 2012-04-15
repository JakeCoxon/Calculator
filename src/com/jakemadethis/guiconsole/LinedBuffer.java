package com.jakemadethis.guiconsole;


public class LinedBuffer extends Buffer {
	public interface Listener {
		void lineChanged(int lineNumber, String newString);
		void lineAdded(int lineNumber);
		void lineRemoved(int lineNumber);
	}

	public int lines;
	int[] widthBuffer;
	private Listener listener = null;
	
	public LinedBuffer(int width, int height) {
		super(width, height);
		widthBuffer = new int[height];
		lines = 1;
		for (int i = 0; i < height; i++) widthBuffer[i] = 0;
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@Override
	public void setCursorPos(int x, int y) {
		super.setCursorPos(x, y);
		if (cursorY > lines-1)
			cursorY = lines-1;
		if (cursorX > widthBuffer[cursorY])
			cursorX = widthBuffer[cursorY];
		
	}

	
	@Override
	public void newLine() {
		if (lines < height) {
			int line = cursorY;
			if (cursorX != 0) line ++;
			shiftDown(line);
			clearLine(line);
			super.newLine();
			if (listener != null) listener.lineAdded(line);
		}
	}
	

	private void clearLine(int line) {
		for (int i = 0; i < width; i++) {
			buffer[line][i] = ' ';
		}
		widthBuffer[line] = 0;
	}

	@Override
	public void putChar(char ch, int color) {
		if (widthBuffer[cursorY] < width-1) {
			shiftRight(cursorX, cursorY);
			super.putChar(ch, color);
			if (listener != null) listener.lineChanged(cursorY, getString(cursorY));
		}
	}
	
	private String getString(int y) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < widthBuffer[y]; i++) {
			b.append(buffer[y][i]);
		}
		return b.toString();
	}

	@Override
	public void deleteLeft() {
		if (cursorX > 0) {
			cursorX --;
			shiftLeft(cursorX, cursorY);
			if (listener != null) listener.lineChanged(cursorY, getString(cursorY));
		}
		else if (widthBuffer[cursorY] == 0) {
			if (cursorY > 0) {
				int line = cursorY;
				shiftUp(line);
				cursorY --;
				cursorX = widthBuffer[cursorY];
				if (listener != null) listener.lineRemoved(line);
			}
		}
	}
	
	

	@Override
	public void deleteRight() {
		if (cursorX < widthBuffer[cursorY]) {
			shiftLeft(cursorX, cursorY);
			if (listener != null) listener.lineChanged(cursorY, getString(cursorY));
		}
		// TODO remove line
	}
	public void setColor(int rgb, int x, int y) {
		colors[y][x] = rgb;
	}
	public void setColor(int rgb, int x, int y, int length) {
		for (int i = x; i < x + length; i++) {
			colors[y][i] = rgb;
		}
	}
	
	

	private void shiftRight(int x, int y) {
		for (int i = widthBuffer[y]; i > x; i--) {
			buffer[y][i] = buffer[y][i-1];
			colors[y][i] = colors[y][i-1];
		}
		widthBuffer[cursorY] ++;
	}
	
	private void shiftLeft(int x, int y) {
		for (int i = x; i < widthBuffer[y] && x < width-1; i++) {
			buffer[y][i] = buffer[y][i+1];
			colors[y][i] = colors[y][i+1];
		}
		buffer[y][width-1] = ' ';
		widthBuffer[cursorY] --;
	}
	

	private void shiftDown(int y) {
		for (int j = lines; j >= y && j > 0; j--) {
			for (int i = 0; i < width; i++) {
				buffer[j][i] = buffer[j-1][i];
				colors[j][i] = colors[j-1][i];
				widthBuffer[j] = widthBuffer[j-1];
			}
		}
		if (y == 0) {
			for (int i = 0; i < width; i++) {
				buffer[0][i] = ' ';
				//colors[0][i] = colors[j-1][i];
				widthBuffer[0] = 0;
			}
		}
		lines ++;
	}
	
	private void shiftUp(int y) {
		for (int j = y; j < lines-1; j++) {
			for (int i = 0; i < width; i++) {
				buffer[j][i] = buffer[j+1][i];
				colors[j][i] = colors[j+1][i];
				widthBuffer[j] = widthBuffer[j+1];
			}
		}
		for (int i = 0; i < width; i++) {
			buffer[lines-1][i] = ' ';
			widthBuffer[lines-1] = 0;
		}
		lines --;
	}

}
