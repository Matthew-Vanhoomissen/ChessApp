package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Game extends JPanel{
	final int dimensions = 512;
	ArrayList<Piece> whiteTeam = new ArrayList<>();
	ArrayList<Piece> blackTeam = new ArrayList<>();
	ArrayList<String> targetSquares = new ArrayList<>();
	ArrayList<String> whiteSquares = new ArrayList<>();
	ArrayList<String> blackSquares = new ArrayList<>();
	String[] enPassant = new String[3];
	boolean enPassant2 = false;
	boolean enPassant3 = false;
	int move = 0;
	boolean skip = false;
	boolean openSpace = true;
	boolean whiteCheck = false;
	boolean blackCheck = false;
	boolean enPassW = true;
	boolean enPassB = true;
	boolean testing = true;
	ArrayList<Piece> whitePieces = new ArrayList<>();
	ArrayList<Piece> blackPieces = new ArrayList<>();
	
	
	
	
	
	
	public Game() {
		this.setPreferredSize(new Dimension(dimensions, dimensions));
		
		this.setDoubleBuffered(true);
		placePieces();
		
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mouseX = e.getX();
                int mouseY = e.getY();
                int bMouseX = (int)(Math.floor(mouseX/64));
                int bMouseY = (int)(Math.floor(mouseY/64));
                openSpace = true;
                skip = false;
                if(move % 2 == 0) {
                for(Piece p : whiteTeam) {
                	
                	if(p.getSelected()) {
                	
                		enPassW = true;
                		testing = false;
                		if(validMove(p, (int)(Math.floor(mouseX/64) * 64), (int)(Math.floor(mouseY/64) * 64))) {
                			enPassW = false;
                			if(pieceThere(bMouseX, bMouseY) != null && !(pieceThere(bMouseX, bMouseY).equalsIgnoreCase(p.getColor()))) {
                				int tempX2 = p.getX();
            					int tempY2 = p.getY();
            					String tempo = "";
            					boolean pMoved = false;
            					for(Piece piece : blackTeam) {
            						if(bMouseX*64 == piece.getX() && bMouseY*64 == piece.getY()) {
            							tempo = piece.getType();
            							pMoved = p.getMoved();
            						}
            					}
            					capture(bMouseX, bMouseY);
            					p.setX((int)(Math.floor(mouseX/64)*64));
            					p.setY((int)(Math.floor(mouseY/64) * 64));
            					
            					if(inCheck("white")) {
            						p.setX(tempX2);
            						p.setY(tempY2);
            						
            						p.setSelected(false);
                        			targetSquares.clear();
                        			blackTeam.add(new Piece("black", tempo, (int)(Math.floor(mouseX/64) * 64), (int)(Math.floor(mouseY/64) * 64), false, pMoved));
                        			repaint();
                        			
                        			continue;
            						
            					}
            					else {
            					
            					p.setX(tempX2);
        						p.setY(tempY2);
                				
                				capture(bMouseX, bMouseY);
                				repaint();
            					}
                			}
                			
                			if(enPassant2) {
                				int tempX2 = p.getX();
            					int tempY2 = p.getY();
            					p.setX((int)(Math.floor(mouseX/64)*64));
            					p.setY((int)(Math.floor(mouseY/64) * 64));
            					if(inCheck("white")) {
            						p.setX(tempX2);
            						p.setY(tempY2);
            						
            						
            					}
            					else {
            						p.setX((int)(Math.floor(mouseX/64)*64));
                					p.setY((int)(Math.floor(mouseY/64) * 64));
                				
                				capture(Integer.parseInt(enPassant[1]), Integer.parseInt(enPassant[2]));
                				enPassant2 = false;
                				repaint();
                        		targetSquares.clear();
                        		continue;
                				
            					}
                			}
                			
                			int tempX2 = p.getX();
        					int tempY2 = p.getY();
        					p.setX((int)(Math.floor(mouseX/64)*64));
        					p.setY((int)(Math.floor(mouseY/64) * 64));
        					if(inCheck("white")) {
        						p.setX(tempX2);
        						p.setY(tempY2);
        						skip = true;
                    			p.setSelected(false);
                    			targetSquares.clear();
                    			repaint();
                    			continue;
        						
        					}
        					else {
        						p.setX(tempX2);
        						p.setY(tempY2);
        						
                			
                			p.setX((int)(Math.floor(mouseX/64) * 64));
                    		p.setY((int)(Math.floor(mouseY/64) * 64));
                    		repaint();
                    		targetSquares.clear();
        					}
                    		
                    		//Tmr instead of checking one piece check every piece 
                    		//to see if it is in check and then you can make a 
                    		//conditional in the validMove() to not be in those squares
                    		
                		}
                		else {
                			skip = true;
                			p.setSelected(false);
                			targetSquares.clear();
                			repaint();
                			continue;
                		}
                		if(enPassant[0] != null && !enPassant[0].equalsIgnoreCase(p.getColor())) {
                			enPassant[0] = null;
                		}
                		
                		targetSquares.clear();
                		p.setSelected(false);
                		openSpace = false;
                		
                		
                		if(p.getType().equals("pawn")) {
                			if(p.getY()/64 == 0) {
                				p.setImage("white", "queen");
                			}
                		}
                		
                		whiteCheck = inCheck("white");
                		System.out.println("White is in check: " + whiteCheck);
                		blackCheck = inCheck("black");
                		
                		System.out.println("black is in  check: " + blackCheck);
                		
                		
                		
                		p.setMoved(true);
                		
                		repaint();
                		move++;
                		endGame(blackTeam, whiteTeam, "black", "white");
                		break;
                		//p.setX((int)(Math.floor(mouseX/64) * 64));
                		//p.setY((int)(Math.floor(mouseY/64) * 64));
                		//repaint();
                		//p.setSelected(false);
                		//openSpace = false;
                		//targetSquares.clear();
                		
                	}
                }
                
                }
               
                if(move % 2 == 1) {
                for(Piece p : blackTeam) {
                	if(p.getSelected()) {
                		enPassB = true;
                		testing = false;
                		if(validMove(p, (int)(Math.floor(mouseX/64) * 64), (int)(Math.floor(mouseY/64) * 64))) {
                			enPassB = false;
                			if(pieceThere(bMouseX, bMouseY) != null && !(pieceThere(bMouseX, bMouseY).equalsIgnoreCase(p.getColor()))) {
                				int tempX2 = p.getX();
            					int tempY2 = p.getY();
            					String tempo = "";
            					boolean bMoved = false;
            					for(Piece piece : whiteTeam) {
            						if(bMouseX*64 == piece.getX() && bMouseY*64 == piece.getY()) {
            							tempo = piece.getType();
            							bMoved = piece.getMoved();
            						}
            					}
            					capture(bMouseX, bMouseY);
            					p.setX((int)(Math.floor(mouseX/64)*64));
            					p.setY((int)(Math.floor(mouseY/64) * 64));
            					if(inCheck("black")) {
            						
            						p.setX(tempX2);
            						p.setY(tempY2);
            						
            						p.setSelected(false);
                        			targetSquares.clear();
                        			whiteTeam.add(new Piece("white", tempo, (int)(Math.floor(mouseX/64) * 64), (int)(Math.floor(mouseY/64) * 64), false, bMoved));
                        			repaint();
                        			continue;
            						
            					}
            					//make a boolean method that switches the color of the piece
            					//then that square can be a target square
            					else {
            						p.setX(tempX2);
            						p.setY(tempY2);
                				
                				capture(bMouseX, bMouseY);
                				
                				repaint();
            					}
                			}
                			else if(enPassant3) {
                				int tempX2 = p.getX();
            					int tempY2 = p.getY();
            					p.setX((int)(Math.floor(mouseX/64)*64));
            					p.setY((int)(Math.floor(mouseY/64) * 64));
            					if(inCheck("black")) {
            						p.setX(tempX2);
            						p.setY(tempY2);
            						
            						
            					}
            					else {
            						p.setX((int)(Math.floor(mouseX/64)*64));
                					p.setY((int)(Math.floor(mouseY/64) * 64));
                				
                				capture(Integer.parseInt(enPassant[1]), Integer.parseInt(enPassant[2]));
                				enPassant3 = false;
            					}
                			}
                			
                			int tempX2 = p.getX();
        					int tempY2 = p.getY();
        					p.setX((int)(Math.floor(mouseX/64)*64));
        					p.setY((int)(Math.floor(mouseY/64) * 64));
        					if(inCheck("black")) {
        						p.setX(tempX2);
        						p.setY(tempY2);
        						p.setSelected(false);
                    			targetSquares.clear();
                    			repaint();
                    			continue;
        						
        					}
        					else {
        						
        						
                			p.setX((int)(Math.floor(mouseX/64) * 64));
                    		p.setY((int)(Math.floor(mouseY/64) * 64));
                    		repaint();
                    		targetSquares.clear();
        					}
                    		
                    		
                		}
                		else {
                			p.setSelected(false);
                			targetSquares.clear();
                			repaint();
                			continue;
                		}
                		if(enPassant[0] != null && !enPassant[0].equalsIgnoreCase(p.getColor())) {
                			enPassant[0] = null;
                		}
                		targetSquares.clear();
                		p.setSelected(false);
                		openSpace = false;
                		
                		if(p.getType().equals("pawn")) {
                			if(p.getY()/64 == 7) {
                				p.setImage("black", "queen");
                			}
                		}
                		
                		whiteCheck = inCheck("white");
                		blackCheck = inCheck("black");
                		System.out.println("Black is in check: " + blackCheck);
                		
                		
                		
                		
                		System.out.println("White is in check: " + whiteCheck);
                		
                		p.setMoved(true);
                		
                		repaint();
                		move++;
                		endGame(whiteTeam, blackTeam, "white", "black");
                		break;
                		
                		/*
                		p.setX((int)(Math.floor(mouseX/64) * 64));
                		p.setY((int)(Math.floor(mouseY/64) * 64));
                		repaint();
                		p.setSelected(false);
                		openSpace = false;
                		break;
                		*/
                	}
                }
               
                }
                
                
                
                
                if(openSpace) {
                	
                if(move % 2 == 0) {
                	int counter = 0;
                	whiteSquares.clear();
                for(Piece p : whiteTeam) {
                	
                	if((mouseX - p.getX() > 0 && mouseX - p.getX() < 64) && (mouseY - p.getY() > 0 && mouseY - p.getY() < 64)) {
                		p.setSelected(true);
                		for(int a = 0; a < 8; a++) {
                			for(int b = 0; b < 8; b++) {
                				
                				enPassW = true;
                				testing = true;
                				if(validMove(p, a*64, b*64)) {
                					enPassW = false;
                					int tempX2 = p.getX();
                					int tempY2 = p.getY();
                					p.setX(a*64);
                					p.setY(b*64);
                					
                					
                					if(inCheck("white")) {
                						p.setX(tempX2);
                						p.setY(tempY2);
                						continue;
                					}
                					
                					p.setX(tempX2);
            						p.setY(tempY2);
                        			targetSquares.add(String.valueOf(a) + " " + String.valueOf(b));
                        			
                            		repaint();
                            		openSpace = false;
                            		counter++;
                        		}
                				
                			}
                		}
                		
                		
                		
                		
                		
                		System.out.println(p.getType());
                		
                	}
                }
                
                }
                if(move % 2 == 1) {
                	int counter2 = 0;
                	blackSquares.clear();
                for(Piece p : blackTeam) {
                	if((mouseX - p.getX() > 0 && mouseX - p.getX() < 64) && (mouseY - p.getY() > 0 && mouseY - p.getY() < 64)) {
                		p.setSelected(true);
                		for(int a = 0; a < 8; a++) {
                			
                			for(int b = 0; b < 8; b++) {
                				boolean checking = false;
                				String tempo = "";
                				boolean bMoved = false;
                				enPassB = true;
                				testing = true;
                				if(validMove(p, a*64, b*64)) {
                					enPassB = false;
                					int tempX2 = p.getX();
                					int tempY2 = p.getY();
                					
                					
                					
                					if(p.getType().equalsIgnoreCase("king")) {
                						for(Piece piece : whiteTeam) {
                							if(piece.getX() == a*64 && piece.getY() == b*64) {
                								checking = true;
                								tempo = piece.getType();
                								bMoved = piece.getMoved();
                								capture(a, b);
                								break;
                							}
                						}
                					}
                					p.setX(a*64);
                					p.setY(b*64);
                					if(inCheck("black")) {
                						p.setX(tempX2);
                						p.setY(tempY2);
                						if(checking) {
                						whiteTeam.add(new Piece("white", tempo, a*64, b*64, false, bMoved));
                						}
                						continue;
                					}
                					p.setX(tempX2);
            						p.setY(tempY2);
            						if(checking) {
            						whiteTeam.add(new Piece("white", tempo, a*64, b*64, false, bMoved));
            						}
                					
            						
            						targetSquares.add(String.valueOf(a) + " " + String.valueOf(b));
                        			
                            		repaint();
                            		openSpace = false;
                            		counter2++;
                        		}
                				
                			}
                		}
                		
                		
                		System.out.println(p.getType());
                		
                	}
                }
                
                }
                
                
			}
                
                
                
                
                //System.out.println("Mouse clicked at: (" + mouseX + ", " + mouseY + ")");
			}
			
		});
	}
	
	public void endGame(ArrayList<Piece> team, ArrayList<Piece> oppositeTeam,String color, String oppositeColor) {
		ArrayList<String> sq = updatedSquares(color);
		if(color.equals("white")) {
		if(sq.size() == 0 && whiteCheck) {
			System.out.println("Checkmate: white loses");
			System.out.println("Black wins");
		}
		else if(sq.size() == 0) {
			System.out.println("Stalemate");
		}
		
		
		}
		else {
		if(sq.size() == 0 && blackCheck) {
			System.out.println("Checkmate: black loses");
			System.out.println("White wins");
		}
		else if(sq.size() == 0) {
			System.out.println("Stalemate");
		}
		
		
		}
	}
	
	public boolean castle(Piece king, ArrayList<Piece> team, int x, int y) {
		ArrayList<Piece> tempTeam = new ArrayList<>();
		ArrayList<String> tempSquares = new ArrayList<>();
		for(Piece p : team) {
			if(p.getType().equals("rook")) {
				tempTeam.add(p);
			}
		}
		if(king.getColor().equals("white") && whiteCheck == true) {
			return false;
		}
		if(king.getColor().equals("black") && blackCheck == true) {
			return false;
		}
		if(king.getColor().equals("white")) {
			tempSquares = blackSquares;
		}
		else {
			tempSquares = whiteSquares;
		}
		
		
		
			
			if(x - king.getX() > 0) {
				if(king.getColor().equals("white")) {
					
					if(pieceThere(5, 7) != null) {
						return false;
					}
				}
				
				
				for(Piece pi : tempTeam) {
					
					if((pi.getX() > king.getX())) {
						for(int i = king.getX()/64 + 1; i < pi.getX()/64; i++) {
							for(String s : tempSquares) {
								
								if(s.charAt(0) - '0' == i && s.charAt(2) - '0' == king.getY()) {
									return false;
								}
							}
							if(pieceThere(i, king.getY()) != null) {
								return false;
							}
						}
						if(pi.getMoved() == false && king.getMoved() == false) {
							if(!testing) {
							pi.setX(5*64);
							repaint();
							}
						return true;
						}
					}
				}
			}
			if(x - king.getX() < 0) {
				if(king.getColor().equals("white")) {
					
					if(pieceThere(3, 7) != null) {
						return false;
					}
				}
				for(Piece pi : tempTeam) {
					
					if((pi.getX() < king.getX())) {
						for(int i = king.getX()/64 - 1; i > pi.getX()/64; i--) {
							for(String s : tempSquares) {
								
								if(s.charAt(0) - '0' == i && s.charAt(2) - '0' == king.getY()) {
									return false;
								}
							}
							if(pieceThere(i, king.getY()) != null) {
								return false;
							}
						}
						if(pi.getMoved() == false && king.getMoved() == false) {
							if(!testing) {
							pi.setX(3*64);
							repaint();
							}
						return true;
						}
					}
				}
			}
		
		return false;
	}
	public void teamSquares() {
		
			
			whitePieces.clear();
			whiteSquares.clear();
			for(Piece p : whiteTeam) {
				
				for(int a = 0; a < 8; a++) {
					for(int b = 0; b < 8; b++) {
						testing = true;
						if(validMove(p, a*64, b*64)) {
							
							whiteSquares.add(String.valueOf(a) + " " + String.valueOf(b));
							whitePieces.add(p);
						}
					}
				}
			}
			
		
		
			blackPieces.clear();
			blackSquares.clear();
			for(Piece p : blackTeam) {
				
				for(int a = 0; a < 8; a++) {
					for(int b = 0; b < 8; b++) {
						testing = true;
						if(validMove(p, a*64, b*64)) {
							blackSquares.add(String.valueOf(a) + " " + String.valueOf(b));
							blackPieces.add(p);
						}
					}
				}
			}
			
			
		
	}
	public ArrayList<String> updatedSquares(String color) {
		ArrayList<String> finalArray = new ArrayList<>();
		if(color.equals("black")) {
			ArrayList<Piece> tempy = blackTeam;
			for(Piece p : tempy) {
				
				for(int a = 0; a < 8; a++) {
					
					for(int b = 0; b < 8; b++) {
						boolean checking = false;
	    				String tempo = "";
	    				boolean bMoved = false;
	    				enPassB = true;
	    				testing = true;
						testing = true;
						if(validMove(p, a*64, b*64)) {
							enPassB = false;
        					int tempX2 = p.getX();
        					int tempY2 = p.getY();
        					
        					
        					
        					if(p.getType().equalsIgnoreCase("king")) {
        						for(Piece piece : whiteTeam) {
        							if(piece.getX() == a*64 && piece.getY() == b*64) {
        								checking = true;
        								tempo = piece.getType();
        								bMoved = piece.getMoved();
        								whiteTeam.remove(piece);
        								break;
        							}
        						}
        					}
        					p.setX(a*64);
        					p.setY(b*64);
        					if(inCheck("black")) {
        						p.setX(tempX2);
        						p.setY(tempY2);
        						if(checking) {
        						whiteTeam.add(new Piece("white", tempo, a*64, b*64, false, bMoved));
        						}
        						continue;
        					}
        					p.setX(tempX2);
    						p.setY(tempY2);
    						if(checking) {
    						whiteTeam.add(new Piece("white", tempo, a*64, b*64, false, bMoved));
    						}
        					
    						
    						finalArray.add(String.valueOf(a) + " " + String.valueOf(b));
                			
                    		repaint();
                    		openSpace = false;
                    		
                		}
						}
					}
				}
			
			}
		else {
			ArrayList<Piece> tempy = whiteTeam;
			for(Piece p : tempy) {
				
				for(int a = 0; a < 8; a++) {
					
					for(int b = 0; b < 8; b++) {
						boolean checking = false;
	    				String tempo = "";
	    				boolean bMoved = false;
	    				enPassB = true;
	    				testing = true;
						testing = true;
						if(validMove(p, a*64, b*64)) {
							enPassB = false;
        					int tempX2 = p.getX();
        					int tempY2 = p.getY();
        					
        					
        					
        					if(p.getType().equalsIgnoreCase("king")) {
        						for(Piece piece : blackTeam) {
        							if(piece.getX() == a*64 && piece.getY() == b*64) {
        								checking = true;
        								tempo = piece.getType();
        								bMoved = piece.getMoved();
        								blackTeam.remove(piece);
        								break;
        							}
        						}
        					}
        					p.setX(a*64);
        					p.setY(b*64);
        					if(inCheck("white")) {
        						p.setX(tempX2);
        						p.setY(tempY2);
        						if(checking) {
        						blackTeam.add(new Piece("black", tempo, a*64, b*64, false, bMoved));
        						}
        						continue;
        					}
        					p.setX(tempX2);
    						p.setY(tempY2);
    						if(checking) {
    						blackTeam.add(new Piece("black", tempo, a*64, b*64, false, bMoved));
    						}
        					
    						
    						finalArray.add(String.valueOf(a) + " " + String.valueOf(b));
                			
                    		repaint();
                    		openSpace = false;
                    		
                		}
						}
					}
				}	
			}
		return finalArray;
		}
		
		
		
	
	
	public boolean inCheck(String color) {
		
		
		teamSquares();
		
		
		
		//targetSquares is at the old position
		//we will have to update it for the new position temporarily;
		if(color.equalsIgnoreCase("white")) {
		for(Piece p : whiteTeam) {
			if(p.getType().equalsIgnoreCase("king")) {
				for(String s : blackSquares) {
					if(s.charAt(0) - '0' == p.getX()/64 && s.charAt(2) - '0' == p.getY()/64) {
						
						
						
						return true;
					}
				}
			}
		}
		}
		else if(color.equalsIgnoreCase("black")){
		for(Piece p : blackTeam) {
			if(p.getType().equalsIgnoreCase("king")) {
				for(String s : whiteSquares) {
					
					if(s.charAt(0) - '0' == p.getX()/64 && s.charAt(2) - '0' == p.getY()/64) {
						
						
						return true;
					}
				}
			}
		}
		}
		
		
		return false;
	}
	
	public void capture(int x, int y) {
		for(Piece p : whiteTeam) {
			if(x == (int)(Math.floor(p.getX()/64)) && y == (int)(Math.floor(p.getY()/64))) {
					
				
				whiteTeam.remove(p);
				break;
			}
		}
		for(Piece p : blackTeam) {
			if(x == (int)(Math.floor(p.getX()/64)) && y == (int)(Math.floor(p.getY()/64))) {
				
				
				blackTeam.remove(p);
				break;
			}
		}
		repaint();
		
	}
	
	public boolean validMove(Piece piece, int x, int y) {
		String type = piece.getType();
		String color2 = piece.getColor();
		int coordX = piece.getX();
		int coordY = piece.getY();
		int gridX = (int)(Math.floor(coordX / 64));
		int gridY = (int)(Math.floor(coordY / 64));
		int testingX = (int)(Math.floor(x / 64));
		int testingY = (int)(Math.floor(y / 64));
		

		if(pieceThere(testingX, testingY) != null && pieceThere(testingX, testingY).equalsIgnoreCase(color2)) {
			return false;
		}
		else if(pieceThere(testingX, testingY) != null) {
			
			//capture(testingX*64, testingY*64);
			//System.out.println("capture");
		}
		
		if(color2.equalsIgnoreCase("white") && whiteCheck && type.equals("king")) {
			for(String s : blackSquares) {
				if(s.charAt(0) - '0' == testingX && s.charAt(2) - '0' == testingY) {
					return false;
				}
			}
			
		}
		if(color2.equalsIgnoreCase("black") && blackCheck && type.equals("king")) {
			for(String s : whiteSquares) {
				if(s.charAt(0) - '0' == testingX && s.charAt(2) - '0' == testingY) {
					return false;
				}
			}
			
		}
		//Another thing for if other piece can move
		//Should only be if they can capture or block
		// We could make a temp variable for the pieces position and then
		//check to see if the new position stops the check
		
		if(type.equalsIgnoreCase("pawn")) {
			if(color2.equalsIgnoreCase("white")) {
				
			if(enPassant[0] != null && enPassant[0] != color2) {
				int tempX = Integer.parseInt(enPassant[1]);
				int tempY = Integer.parseInt(enPassant[2]);
				System.out.println(tempX + "." + tempY);
				if(tempX == testingX && tempY == testingY + 1 && (tempX + 1 == gridX || tempX - 1 == gridX) && tempY == gridY) {
					enPassant2 = true;
					return true;
				}
				
			}
			
			
			if(testingX == gridX && (gridY - 1) == testingY) {
				if(pieceThere(testingX, testingY) == null) {
					return true;
				}
				
				//System.out.println("Move up by 1");
				
			}
			else if(pieceThere(testingX, testingY) != null) {
				if(!pieceThere(testingX, testingY).equalsIgnoreCase(color2) && ((testingX - 1 == gridX && testingY == gridY - 1) || (testingX + 1 == gridX && testingY == gridY - 1))) {
					return true;
				}
				return false;
			}
			
			
			
			
			if(testingX == gridX && testingY == 4 && gridY == 6) {
				if(pieceThere(testingX, testingY + 1) != null) {
					return false;
				}
				//System.out.println("Move up by 2");
				
				if(enPassW) {
				enPassant[0] = color2;
				enPassant[1] = "" + testingX;
				enPassant[2] = "" + testingY;
				
				return true;
				}
			}
		}
			else if (color2.equalsIgnoreCase("black")) {
				if(enPassant[0] != null && enPassant[0] != color2) {
					int tempX = Integer.parseInt(enPassant[1]);
					int tempY = Integer.parseInt(enPassant[2]);
					System.out.println(tempX + "." + tempY);
					if(tempX == testingX && tempY + 1 == testingY && (tempX + 1 == gridX || tempX - 1 == gridX) && tempY == gridY) {
						enPassant3 = true;
						return true;
					}
				}
				
				
				if(testingX == gridX && (gridY + 1) == testingY) {
					if(pieceThere(testingX, testingY) == null) {
						return true;
					}
					
					//System.out.println("Move up by 1");
					
				}
				else if(pieceThere(testingX, testingY) != null) {
					if(!pieceThere(testingX, testingY).equalsIgnoreCase(color2) && ((testingX + 1 == gridX && testingY == gridY + 1) || (testingX - 1 == gridX && testingY == gridY + 1))) {
						return true;
					}
					return false;
				}
				
				
				
				
				if(testingX == gridX && testingY == 3 && gridY == 1) {
					if(pieceThere(testingX, testingY - 1) != null) {
						return false;
					}
					
					if(enPassB) {
					enPassant[0] = color2;
					enPassant[1] = "" + testingX;
					enPassant[2] = "" + testingY;
					return true;
					}
				}
			}
		}
		else if(type.equalsIgnoreCase("king")) {
			if(testingX - gridX <= 1 && testingX - gridX >= -1 && testingY - gridY >= -1 && testingY - gridY <= 1 && (testingX != gridX || gridY != testingY)) {
				return true;
			}
			
			if(color2.equals("white") && testingY == gridY && (testingX - gridX == 2 || testingX - gridX == -2)) {
				return castle(piece, whiteTeam, testingX*64, testingY*64);
			}
			else if(color2.equals("black") && testingY == gridY && (testingX - gridX == 2 || testingX - gridX == -2)) {
				return castle(piece, blackTeam, testingX*64, testingY*64);
			}
		}
		else if(type.equalsIgnoreCase("knight")) {
			if((Math.abs(testingX - gridX) == 2 && Math.abs(testingY - gridY) == 1) || (Math.abs(testingY - gridY) == 2 && Math.abs(testingX - gridX) == 1)) {
				return true;
			}
		}
		else if(type.equalsIgnoreCase("rook")) {
			for(int i = gridY; i > testingY; i--) {
				if(i == gridY) {
					continue;
				}
				if(pieceThere(gridX, i) != null) {
					return false;
				}
			}
			for(int i = gridY; i < testingY; i++) {
				if(i == gridY) {
					continue;
				}
				if(pieceThere(gridX, i) != null) {
					return false;
				}
			}
			//
			
			for(int i = gridX; i > testingX; i--) {
				if(i == gridX) {
					continue;
				}
				if(pieceThere(i, gridY) != null) {
					return false;
				}
			}
			for(int i = gridX; i < testingX; i++) {
				if(i == gridX) {
					continue;
				}
				if(pieceThere(i, gridY) != null) {
					return false;
				}
			}
			
			if((testingX == gridX || testingY == gridY) && !(testingX == gridX && testingY == gridY)) {
				return true;
			}
		}
		
		else if(type.equalsIgnoreCase("bishop")) {
			
			ArrayList<String> diagonalSquares = new ArrayList<>();
			
			
			int X = gridX - 1;
			int Y = gridY - 1;
			
			while(X >= 0 && Y >= 0) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X--;
				Y--;
				
			}
			X = gridX + 1;
			Y = gridY - 1;
			
			while(X <= 7 && Y >= 0) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X++;
				Y--;
				
			}
			
			X = gridX + 1;
			Y = gridY + 1;
			
			while(X <= 7 && Y <= 7) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X++;
				Y++;
				
			}
			
			X = gridX - 1;
			Y = gridY + 1;
			
			while(X >= 0 && Y <= 7) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X--;
				Y++;
				
			}
			
			
			
			if(diagonalSquares.contains(testingX + "." + testingY)) {
				return true;
			}
			
			
		
			
			
			
		
			
		}
		else if(type.equalsIgnoreCase("queen")) {
			ArrayList<String> diagonalSquares = new ArrayList<>();
			
			
			int X = gridX - 1;
			int Y = gridY - 1;
			
			while(X >= 0 && Y >= 0) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X--;
				Y--;
				
			}
			X = gridX + 1;
			Y = gridY - 1;
			
			while(X <= 7 && Y >= 0) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X++;
				Y--;
				
			}
			
			X = gridX + 1;
			Y = gridY + 1;
			
			while(X <= 7 && Y <= 7) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X++;
				Y++;
				
			}
			
			X = gridX - 1;
			Y = gridY + 1;
			
			while(X >= 0 && Y <= 7) {
			
				if(pieceThere(X, Y) != null && pieceThere(X, Y).equalsIgnoreCase(color2)) {
					break;
				}
				else if(pieceThere(X, Y) != null) {
					diagonalSquares.add(X + "." + Y);
					break;
				}
				diagonalSquares.add(X + "." + Y);
				X--;
				Y++;
				
			}
			
			
			ArrayList<String> lineSquares = new ArrayList<>();
			
			for(int i = gridX - 1; i >= 0; i--) {
				
				if(pieceThere(i, gridY) != null && pieceThere(i, gridY).equals(color2)) {
					break;
					
				}
				else if(pieceThere(i, gridY) != null) {
					lineSquares.add(i + "." + gridY);
					break;
				}
				else {
					lineSquares.add(i + "." + gridY);
					
				}
			}
			for(int i = gridX + 1; i < 8; i++) {
				
				if(pieceThere(i, gridY) != null && pieceThere(i, gridY).equals(color2)) {
					break;
					
				}
				else if(pieceThere(i, gridY) != null) {
					lineSquares.add(i + "." + gridY);
					break;
				}
				else {
					lineSquares.add(i + "." + gridY);
					
				}
			}
			for(int i = gridY - 1; i >= 0; i--) {
				
				if(pieceThere(gridX, i) != null && pieceThere(gridX, i).equals(color2)) {
					break;
					
				}
				else if(pieceThere(gridX, i) != null) {
					lineSquares.add(gridX + "." + i);
					break;
				}
				else {
					lineSquares.add(gridX + "." + i);
					
				}
			}
			for(int i = gridY + 1; i < 8; i++) {
				
				if(pieceThere(gridX, i) != null && pieceThere(gridX, i).equals(color2)) {
					break;
					
				}
				else if(pieceThere(gridX, i) != null) {
					lineSquares.add(gridX + "." + i);
					break;
				}
				else {
					lineSquares.add(gridX + "." + i);
					
				}
			}
			
			
			if(lineSquares.contains(testingX + "." + testingY)) {
				return true;
			}
			if(diagonalSquares.contains(testingX + "." + testingY)) {
				return true;
			}
			
			return false;
			
			
		}
	
		
		
		return false;
	}
	
	public String pieceThere(int x, int y) {
		for(Piece p : whiteTeam) {
			if((int)(Math.floor(p.getX() / 64)) == x && (int)(Math.floor(p.getY() / 64)) == y) {
				return "white";
			}
		}
		for(Piece p : blackTeam) {
			if((int)(Math.floor(p.getX() / 64)) == x && (int)(Math.floor(p.getY() / 64)) == y) {
				return "black";
			}
		}
		return null;
	}
	
	
	
	public void placePieces() {
		String piece = "";
		int x = 0;
		int y = 320;
		//white pieces
		for(int i = 0; i < 2; i++) {
			y += 64;
			x = -64;
			for(int j = 0; j < 8; j++) {
				x += 64;
				if(i == 0) {
					piece = "pawn";
				}
				else if(j == 0 || j == 7) {
					piece = "rook";
				}
				else if(j == 1 || j == 6) {
					piece = "knight";
				}
				else if(j == 2 || j == 5) {
					piece = "bishop";
				}
				else if(j == 3) {
					piece = "queen";
				}
				else {
					piece = "king";
				}
				
				whiteTeam.add(new Piece("white", piece, x, y, false, false));
				
			}
		}
		x = 0;
		y = -64;
		//black pieces
		for(int i = 0; i < 2; i++) {
			y += 64;
			x = -64;
			for(int j = 0; j < 8; j++) {
				x += 64;
				if(i == 1) {
					piece = "pawn";
				}
				else if(j == 0 || j == 7) {
					piece = "rook";
				}
				else if(j == 1 || j == 6) {
					piece = "knight";
				}
				else if(j == 2 || j == 5) {
					piece = "bishop";
				}
				else if(j == 3) {
					piece = "queen";
				}
				else {
					piece = "king";
				}
				blackTeam.add(new Piece("black", piece, x, y, false, false));
				
			}
		}
		
	}
	
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		boolean painted = true;
		
		boolean white = true;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(white) {
					g.setColor(Color.white);
				}
				else {
					g.setColor(Color.darkGray);
				}
				g.fillRect(i*64, j*64, 64, 64);
				white = !white;
			}
			white = !white;
		}
		
		
		for(String s : targetSquares) {
			char[] coordinates = s.toCharArray();
			int x = (coordinates[0] - '0');
			int y = (coordinates[2] - '0');
			g.setColor(Color.green);
			g.fillRect(x*64, y*64, 64, 64);
			
			painted = false;
			
			
		}
		if(painted) {
			white = true;
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(white) {
						g.setColor(Color.white);
					}
					else {
						g.setColor(Color.darkGray);
					}
					g.fillRect(i*64, j*64, 64, 64);
					white = !white;
				}
				white = !white;
			}
			
		}
		
		for(Piece p : blackTeam) {
			p.draw(g, this);
		}
		for(Piece p : whiteTeam) {
			p.draw(g, this);
		}
		
		
		
		
		
		
	}
	
	

}
