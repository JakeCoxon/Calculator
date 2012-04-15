package com.jakemadethis.guiconsole;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiConsole extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	protected static final int charWidth = 8;
	protected static final int charHeight = 15;
	static final public Color COLOR_CURSOR = new Color(150, 150, 150);
	static final public Color COLOR_TEXT = new Color(150, 150, 150);
	static final public Color COLOR_CURSOR_TEXT = new Color(0, 0, 0);
	static final public int columns = 50;
	static final public int rows = 32;
	

	protected LinedBuffer buffer;
	protected final Font font;
	protected int bufferX = 0;
	protected int bufferY = 0;
	
	public GuiConsole() {
		setBackground(new Color(0,0,0));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		buffer = new LinedBuffer(columns, rows);
		buffer.defaultColor = COLOR_TEXT.getRGB();
		font = new Font("Lucida Console", Font.PLAIN, 14);
	}
	
	@Override
	public Dimension preferredSize() {
		return new Dimension(columns * charWidth, rows * charHeight);
	}
	
	protected void drawChar(Graphics g, char ch, int x, int y) {
		g.drawString(String.valueOf(ch), x * charWidth, (y+1)*charHeight - 2);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setFont(font);
		
		
		// Main buffer
		for (int j = 0; j < rows; j ++) {
			
			for (int i = 0; i < columns; i++) {
				g.setColor(new Color(buffer.getColor(i, j)));
				drawChar(g, buffer.getChar(i, j), bufferX + i, bufferY + j);
			}
		}
		
		g.setColor(COLOR_CURSOR);
		g.fillRect((bufferX + buffer.getCursorX()) * charWidth, (bufferY + buffer.getCursorY()) * charHeight, charWidth, charHeight);
		g.setColor(COLOR_CURSOR_TEXT);
		drawChar(g, buffer.getCursorChar(), bufferX + buffer.getCursorX(), bufferY + buffer.getCursorY());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) buffer.cursorRight();
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) buffer.cursorLeft();
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) buffer.cursorDown();
		else if (e.getKeyCode() == KeyEvent.VK_UP) buffer.cursorUp();
		System.out.println(e.getKeyCode());
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '\n') {
			buffer.newLine();
			return;
		}
		if (e.getKeyChar() == '\b') {
			buffer.deleteLeft();
			return;
		}
		if (e.getKeyChar() == 127) {
			buffer.deleteRight();
			return;
		}
		//System.out.println((int)e.getKeyChar());
		buffer.putChar(e.getKeyChar(), buffer.defaultColor);
		repaint();
	}
	
	public static void showWindow(GuiConsole console) {
		JFrame window = new JFrame();
		window.add(console);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
		// http://stackoverflow.com/questions/286727/java-keylistener-for-jframe-is-being-unresponsive
		window.addKeyListener(console);
	}
	public static void main(String[] args) {
		showWindow(new GuiConsole());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buffer.setCursorPos(e.getX() / charWidth - bufferX, e.getY() / charHeight - bufferY);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		buffer.setCursorPos(e.getX() / charWidth - bufferX, e.getY() / charHeight - bufferY);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
