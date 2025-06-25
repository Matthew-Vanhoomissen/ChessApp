package main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Piece {
	String color;
	String type;
	int x;
	int y;
	Image image;
	boolean selected;
	boolean moved;
	
	public Piece(String color, String type, int x, int y, boolean selected, boolean moved) {
		this.color = color;
		this.type = type;
		this.x = x;
		this.y = y;
		this.selected = selected;
		this.moved = moved;
		
		ImageIcon imageI = new ImageIcon("C:\\Users\\mike_\\OneDrive\\Desktop\\ChessSet\\" + color + "-" + type + ".png");
		image = imageI.getImage();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public String getColor() {
		return color;
	}
	public boolean getMoved() {
		return moved;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void draw(Graphics g, JPanel p) {
		if(image != null) {
			g.drawImage(image, x, y, p);
		}
	}
	
	
	

	
	

}

