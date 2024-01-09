package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CustomButton {
	
	public int x, y, width, height, id;
	private String text;
	private Rectangle bounds;
	
	private boolean mouseOver, mousePressed;
	
	//Sima gomb
	public CustomButton(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.id = -1;
		
		initBounds();
	}
	
	
	//Tobb gomb egyszerre felvetelehez
	public CustomButton(int x, int y, int width, int height, int id, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.id = id;
		
		initBounds();
	}
	
	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		//Body
		drawBody(g);
		
		//Border
		drawBorder(g);
		
		//Text
		drawText(g);
	}
	
	private void drawBorder(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		if(mousePressed) {
			g.drawRect(x+1, y+1, width-2, height-2);
			g.drawRect(x+2, y+2, width-4, height-4);
		}
	}

	private void drawBody(Graphics g) {
		if(mouseOver)
			g.setColor(new Color(153, 76, 0));
		else
			g.setColor(new Color(226, 181, 75));
		g.fillRect(x, y, width, height);
	}

	private void drawText(Graphics g) {
		int w = g.getFontMetrics().stringWidth(text);
		int h = g.getFontMetrics().getHeight();
		int a = g.getFontMetrics().getAscent();
		g.drawString(text, x + (width - w)/2, y + ((height - h)/2) + a);
		
	}
	
	public void resetBooleans() {
		this.mouseOver = false;
		this.mousePressed = false;
	}
	
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public int getId() {
		return id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
