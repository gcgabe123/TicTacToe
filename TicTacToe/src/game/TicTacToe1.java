package game;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe1 {

	static char [][] board = {{' ','|',' ','|',' '},
 		   {'-','+','-','+','-'},
 		   {' ','|',' ','|',' '},
 		   {'-','+','-','+','-'},
 		   {' ','|',' ','|',' '}};
	
	static ArrayList<Placement> moves = new ArrayList<Placement>();
	static ArrayList<Integer> pMoves = new ArrayList<Integer>();
	static ArrayList<Integer> cMoves = new ArrayList<Integer>();
	
	static char turn;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Welcome to TicTacToe!");
		
		char [][] ex = {{'1','|','2','|','3'},
		 		   {'-','+','-','+','-'},
		 		   {'4','|','5','|','6'},
		 		   {'-','+','-','+','-'},
		 		   {'7','|','8','|','9'}};
		System.out.println();
		updateGame(ex);
		
		StdDraw.setCanvasSize(600, 600);
		StdDraw.setXscale(0, 600);
		StdDraw.setYscale(0, 600);
		
		gameLoop();
	}
	
	private static void select() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(0, 0, 600, 600);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.setPenRadius(0.075);
		StdDraw.line(25, 200, 575, 200);
		StdDraw.line(25, 400, 575, 400);
		StdDraw.line(200, 25, 200, 575);
		StdDraw.line(400, 25, 400, 575);
		Font font = new Font("Arial", Font.BOLD, 50);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.setFont(font);
		StdDraw.text(100, 500, "Tic");
		StdDraw.text(300, 500, "Tac");
		StdDraw.text(500, 500, "Toe");
		Font font2 = new Font("Arial", Font.BOLD, 30);
		StdDraw.setFont(font2);
		StdDraw.text(100, 300, "Regular");
		StdDraw.text(515, 300, "Impossible");
	}
	
	private static void blank() {
		//Draw game board
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(0, 0, 600, 600);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.setPenRadius(0.075);
		StdDraw.line(25, 200, 575, 200);
		StdDraw.line(25, 400, 575, 400);
		StdDraw.line(200, 25, 200, 575);
		StdDraw.line(400, 25, 400, 575);
	}
	
	private static void gameLoop() {
		Scanner input = new Scanner(System.in);
		
		boolean play = true;
		
		//*****Game Loop*****
		while(play) {
			Font font = new Font("Arial", Font.BOLD, 25);
			initializeMoves(board);
			select();
			
			System.out.println("Choose Difficulty: 1(Regular) 2(Impossible)");
			//select();
			//int dif = input.nextInt();
			int dif = 0;
			
			while(dif == 0) {
				if(StdDraw.isMousePressed()) {
					if(StdDraw.mouseX() < 200) {
						dif = 1;
						break;
					}else if(StdDraw.mouseX() > 400) {
						dif = 2;
						break;
					}
				}
			}
			//input.nextLine();
			
			blank();
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.text(300, 395, "Randomizing Turn...");
			StdDraw.pause(1500);
			System.out.println("Randomizing turn... ");
			System.out.println();
			Random rand = new Random();
			int t = rand.nextInt(3) + 1;
			if(t == 1) {
				turn = 'X';
			}else {
				turn = 'O';
			}
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.line(25, 400, 575, 400);
			
			while(true) {
				if(checkWin() == true) {
					break;
				}
				if(turn == 'X') {
					System.out.println("Your turn! pick number 1-9");
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.setFont(font);
					StdDraw.text(300, 395, "Your Turn!");
					//int pos = input.nextInt();
					int pos = checkClick();
					boolean done = placePiece(turn, pos);
					if(done == true) {
						turn = 'O';
					}
				}else {
					int cpuPos;
					if(dif == 1) {
						cpuPos = pc();
					}else {
						cpuPos = bestMove();
					}
					System.out.println("Computer's turn...");
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.setFont(font);
					StdDraw.text(300, 395, "Computer's Turn...");
					StdDraw.pause(2000);
					//System.out.println(cpuPos);
					boolean done = placePiece(turn, cpuPos);
					if(done == true) {
						turn = 'X';
					}
				}
			}
			
			pMoves.clear();
			cMoves.clear();
			System.out.println("Do you want to play again? 1(Yes) 2(No)");
			StdDraw.text(300, 195, "Do you want to play again?");
			StdDraw.text(100, 195, "Yes!");
			StdDraw.text(500, 195, "No!");
			int redo = 0;
			while(redo == 0) {
				StdDraw.pause(200);
				if(StdDraw.isMousePressed()) {
					System.out.println("CLick");
					if(StdDraw.mouseX() < 200) {
						redo = 1;
						break;
					}else if(StdDraw.mouseX() > 400) {
						redo = 2;
						break;
					}
				}
			}
			//int redo = input.nextInt();
			if(redo == 2) {
				break;
			}
			StdDraw.clear(StdDraw.BLACK);
			StdDraw.pause(200);
			System.out.println();
		}
		
		System.exit(0);
		
	}

	private static void availableMoves(char [][] b) {
		// TODO Auto-generated method stub
		//Assigns a number to each position for cpu to use
		moves.clear();
		int num = 1;
		for(int i = 0; i < b.length; i++){
		    for(int j = 0; j < b[i].length; j++){ 
		    	if(b[i][j] == ' ' || b[i][j] == 'X' || b[i][j] == 'O') {
		    		Placement space = new Placement(i,j, num);
		    		moves.add(space);
		    		num++;
//		    		if(pMoves.contains() || b[i][j] == 'O') {
//			    		moves.remove(space);
//			    	}
		    	}
		    }
		}
		
		for(int i = 0; i < moves.size(); i++) {
			if(pMoves.contains(moves.get(i).getNum()) || cMoves.contains(moves.get(i).getNum())) {
				moves.remove(i);
				i = -1;
			}
		}
		
		
		//System.out.println(moves.size());
	}
	
	private static void initializeMoves(char [][] b) {
		// TODO Auto-generated method stub
		//Assigns a number to each position for cpu to use
		moves.clear();
		int num = 1;
		for(int i = 0; i < b.length; i++){
		    for(int j = 0; j < b[i].length; j++){ 
		    	if(b[i][j] == 'X' || b[i][j] == 'O') {
		    		b[i][j] = ' ';
		    	}
		    	if(b[i][j] == ' ') {
		    		Placement space = new Placement(i,j, num);
		    		moves.add(space);
		    		num++;
		    	}
		    }
		  }
		
		//System.out.println(moves.size());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Boolean checkWin() {
		// TODO Auto-generated method stub
		
		List topRow = Arrays.asList(1, 2, 3);
		List midRow = Arrays.asList(4, 5, 6);
		List botRow = Arrays.asList(7, 8, 9);
		List lefCol = Arrays.asList(1, 4, 7);
		List midCol = Arrays.asList(2, 5, 8);
		List rigCol = Arrays.asList(3, 6, 9);
		List cross1 = Arrays.asList(1, 5, 9);
		List cross2 = Arrays.asList(3, 5, 7);
		
		List<List> winning = new ArrayList<List>();
		winning.add(topRow);
		winning.add(midRow);
		winning.add(botRow);
		winning.add(lefCol);
		winning.add(midCol);
		winning.add(rigCol);
		winning.add(cross1);
		winning.add(cross2);
		
		Font font = new Font("Arial", Font.BOLD, 20);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.setFont(font);
		
		for(List l : winning) {
			if(pMoves.containsAll(l)) {
				System.out.println("Congrats! Player wins!");
				StdDraw.text(300, 395, "Congrats! You Win!");
				return true;
			}else if(cMoves.containsAll(l)) {
				System.out.println("Sorry, You lost!");
				StdDraw.text(300, 395, "Sorry! You Lost!");
				return true;
			}else if(moves.isEmpty()) {
				System.out.println("Its a tie!!");
				StdDraw.text(300, 395, "Its A Tie!");
				return true;
			}
		}
		
		return false;
		
	}

	private static void updateGame(char [][] b) {
		// TODO Auto-generated method stub
		for(int i = 0; i < b.length; i++){
		    for(int j = 0; j < b[i].length; j++){
		      System.out.print(b[i][j]);
		    }
		    System.out.println();
		  }
		System.out.println();
	}
	
	private static int checkClick() {
		boolean done = false;
		int x = 0;
		int y = 0;
		
		StdDraw.pause(50);
		while(!done) {
			if(StdDraw.isMousePressed()) {
				x = (int) StdDraw.mouseX();
				y = (int) StdDraw.mouseY();
				done = true;
			}
		}
		
		StdDraw.pause(20);
		if(x < 190 && y > 410) {
			return 1;
		}else if(x < 190 && y > 210 && y < 390) {
			return 4;
		}else if(x < 190 && y < 190) {
			return 7;
		}else if(x > 210 && x < 390 && y > 410) {
			return 2;
		}else if(x > 210 && x < 390 && y > 210 && y < 390) {
			return 5;
		}else if(x > 210 && x < 390 && y < 190) {
			return 8;
		}else if(x > 410 && y > 410) {
			return 3;
		}else if(x > 410 && y > 210 && y < 390) {
			return 6;
		}else if(x > 410 && y < 190) {
			return 9;
		}else {
			
		}
		return checkClick();
	}
	private static void updateBoard(int pos) {
		Font font = new Font("Arial", Font.BOLD, 100);
		StdDraw.setFont(font);
		if(turn == 'X') {
			StdDraw.setPenColor(StdDraw.ORANGE);
		}else {
			StdDraw.setPenColor(StdDraw.CYAN);
		}
		
		switch(pos) {
		case 1:
			StdDraw.text(100, 500, Character.toString(turn));
			break;
		case 2:
			StdDraw.text(300, 500, Character.toString(turn));
			break;
		case 3:
			StdDraw.text(500, 500, Character.toString(turn));
			break;
		case 4:
			StdDraw.text(100, 300, Character.toString(turn));
			break;
		case 5:
			StdDraw.text(300, 300, Character.toString(turn));
			break;
		case 6:
			StdDraw.text(500, 300, Character.toString(turn));
			break;
		case 7:
			StdDraw.text(100, 100, Character.toString(turn));
			break;
		case 8:
			StdDraw.text(300, 100, Character.toString(turn));
			break;
		case 9:
			StdDraw.text(500, 100, Character.toString(turn));
			break;
		}
		
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.line(25, 400, 575, 400);
	}

	private static int pc() {
		Random rand = new Random();
		int idx = rand.nextInt(moves.size() - 1);
		
		int cpuPos = moves.get(idx).getNum();
		
		return cpuPos;
	}
	
	private static int bestMove() {
		//Check through available moves
		//try a move and see the resulting score of said move
		
		int bestScore = -100;
		int score = -100;
		Placement bMove = moves.get(0);
		for(int i = 0; i < moves.size(); i++) {
			cMoves.add(moves.get(i).getNum());
			moves.remove(i);
			score = miniMax(0, false);
			cMoves.remove(cMoves.size() - 1);
			availableMoves(board);
			if(score > bestScore) {
				bestScore = score;
				bMove = moves.get(i);
			}
			
		}
		
		return bMove.getNum();
		
	}
	
	private static int miniMax(int depth, boolean isMaximizing) {
		String result = win2();
		int score;
		//availableMoves(board);
		
		switch(result) {
		case "pWin":
			//availableMoves(board);
			return -1;
		case "cWin":
			//availableMoves(board);
			return 1;
		case "tie":
			//availableMoves(board);
			return 0;
		}
		
		if(isMaximizing) {
			int bestScore = -100;
			Placement item = null;
			for(int i = 0; i < moves.size(); i++) {
				item = moves.get(i);
				cMoves.add(moves.get(i).getNum());
				moves.remove(i);
				score = miniMax(depth + 1, false);
				bestScore = Math.max(score, bestScore);
				cMoves.remove(cMoves.size() - 1);
				availableMoves(board);
				//moves.add(0, item);
			}
			//moves.add(item);
			return bestScore;
		}else {
			int bestScore = 100;
			Placement item = null;
			for(int i = 0; i < moves.size(); i++) {
				item = moves.get(i);
				pMoves.add(moves.get(i).getNum());
				moves.remove(i);
				score = miniMax(depth + 1, true);
				bestScore = Math.min(score, bestScore);
				pMoves.remove(pMoves.size() - 1);
				availableMoves(board);
				//moves.add(0, item);
			}
			//moves.add(item);
			return bestScore;
		}
		
	}
	
	private static String win2() {
		// TODO Auto-generated method stub
		
		List topRow = Arrays.asList(1, 2, 3);
		List midRow = Arrays.asList(4, 5, 6);
		List botRow = Arrays.asList(7, 8, 9);
		List lefCol = Arrays.asList(1, 4, 7);
		List midCol = Arrays.asList(2, 5, 8);
		List rigCol = Arrays.asList(3, 6, 9);
		List cross1 = Arrays.asList(1, 5, 9);
		List cross2 = Arrays.asList(3, 5, 7);
		
		List<List> winning = new ArrayList<List>();
		winning.add(topRow);
		winning.add(midRow);
		winning.add(botRow);
		winning.add(lefCol);
		winning.add(midCol);
		winning.add(rigCol);
		winning.add(cross1);
		winning.add(cross2);
		
		for(List l : winning) {
			if(pMoves.containsAll(l)) {
				return "pWin";
			}else if(cMoves.containsAll(l)) {
				return "cWin";
			}
		}
		
		if(moves.isEmpty()) {
			return "tie";
		}
		
		return "notOver";
		
	}

	private static boolean placePiece(char player, int pos) {
		// TODO Auto-generated method stub
		
		Placement Move = new Placement(0,0);
		
		
		switch(pos) {
		case 1:
			Move.setX(0);
			Move.setY(0);
			break;
		case 2:
			Move.setX(0);
			Move.setY(2);
			break;
		case 3:
			Move.setX(0);
			Move.setY(4);
			break;
		case 4:
			Move.setX(2);
			Move.setY(0);
			break;
		case 5:
			Move.setX(2);
			Move.setY(2);
			break;
		case 6:
			Move.setX(2);
			Move.setY(4);
			break;
		case 7:
			Move.setX(4);
			Move.setY(0);
			break;
		case 8:
			Move.setX(4);
			Move.setY(2);
			break;
		case 9:
			Move.setX(4);
			Move.setY(4);
			break;
		}
		
		//Checks to see if move is in list of avaliable moves
		boolean valid = false;
		for(int i = 0; i < moves.size(); i++) {
			if(moves.get(i).getNum() == pos) {
				valid = true;
				break;
			}
		}
		
		//If move is valid, makes move and adds move to current players list of moves
		if(valid == true) {
			board[Move.getX()][Move.getY()] = player;
			if(player == 'X') {
				pMoves.add(pos);
				updateBoard(pos);
			}else {
				cMoves.add(pos);
				StdDraw.pause(50);
				updateBoard(pos);
			}
		}else {
			if(player == 'X') {
				System.out.println("Not a valid move, please select another!");
			}
			return false;
		}
		
		//Removes players move from available moves
		for(int i = 0; i < moves.size(); i++) {
			if(moves.get(i).getNum() == pos) {
				moves.remove(i);
			}
		}
		
		//Prints available moves
//		for(int i = 0; i < moves.size(); i++) {
//			System.out.print(moves.get(i) + " ");
//		}
		
		System.out.println();
		updateGame(board);
		return true;
		
	}

}
