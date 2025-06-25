package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame(); 
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("Chess Game");
		
		Game game = new Game();
		window.add(game);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
