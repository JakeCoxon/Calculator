package com.jakemadethis.calculatorgui;

import java.awt.Color;
import java.awt.Graphics;

import com.jakemadethis.calculator.LinedCalculator;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.guiconsole.GuiConsole;
import com.jakemadethis.guiconsole.LinedBuffer.Listener;

public class CalculatorGui extends GuiConsole {
	public static void main(String[] args) {
		showWindow(new CalculatorGui());
	}
	static final public Color COLOR_GUTTER_TEXT = new Color(200, 200, 200);
	static final public Color COLOR_GUTTER = new Color(100, 0, 0);

	LinedCalculator calculator;

	int gutterWidth = 3;
	int outputWidth = 20;
	private final SyntaxHighlighter syntaxHighliter;
	
	public CalculatorGui() {
		calculator = new MyCalculator();
		syntaxHighliter = new SyntaxHighlighter(buffer);
		buffer.setListener(new Listener() {
			
			@Override
			public void lineRemoved(int lineNumber) {
				calculator.removeLine(lineNumber);
				calculator.recalculateFrom(lineNumber);
			}
			
			@Override
			public void lineChanged(int lineNumber, String newString) {
				calculator.updateLine(lineNumber, newString);
				calculator.recalculateFrom(lineNumber);
				Expression input = calculator.getInput(lineNumber);
				if (input != null)
					input.accept(syntaxHighliter, lineNumber);
			}
			
			@Override
			public void lineAdded(int lineNumber) {
				calculator.addRow(lineNumber);
				calculator.recalculateFrom(lineNumber);
			}
		});
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		bufferX = gutterWidth;

		g.setFont(font);

		// Gutter
		g.setColor(COLOR_GUTTER);
		g.fillRect(0, 0, (gutterWidth-1) * charWidth, buffer.lines * charHeight);

		// Gutter Text
		g.setColor(COLOR_GUTTER_TEXT);
		for (int j = 0; j < buffer.lines; j++) {
			String lineNumber = String.valueOf(j+1);
			for (int i = 0; i < gutterWidth && i < lineNumber.length(); i++) {
				drawChar(g, lineNumber.charAt(i), i, j);
			}
		}
		

		// Output panel
		for (int j = 0; j < buffer.lines; j ++) {

			String output = getOutput(j);
			for (int i = 0; i < outputWidth && i < output.length(); i++) {
				g.setColor(Color.WHITE);
				drawChar(g, output.charAt(i), columns - outputWidth + i, bufferY + j);
			}
		}
		
		
	}

	private String getOutput(int row) {
		return calculator.getOutput(row);
	}
}
