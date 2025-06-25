package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameScreen extends JPanel{
	
	final int dimensions = 512;
	Game game = new Game();

	public GameScreen() {
		this.setPreferredSize(new Dimension(dimensions, dimensions));
		
		this.setDoubleBuffered(true);
	}
	
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		
		boolean white = false;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(white) {
					g.setColor(Color.white);
				}
				else {
					g.setColor(Color.black);
				}
				g.fillRect(i*64, j*64, 64, 64);
				white = !white;
			}
			white = !white;
		}
		
		game.placePieces();
		
		ImageIcon imageI = new ImageIcon("C:\\Users\\mike_\\Downloads\\whiteKing.png");
		Image image = imageI.getImage();
		
		if(image != null) {
			g.drawImage(image, 100, 100, this);
		}
		
		
	}
	

	

	
}
