package Othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ParserThread extends Thread {
	
	String gameMoves;
	int gameNumber;
	static Random rand = new Random();
	private static float beta = 1;
	
	public ParserThread(String gameMoves, int gameNumber){
		this.gameMoves = gameMoves;
		this.gameNumber = gameNumber;
	}

	public void run() {
		float[][] whiteHeur = new float[61][4];
		float[][] blackHeur = new float[61][4];
		int[][] moves = new int[60][2];
		for(int i =0;i<60;i++){
			if(gameMoves.charAt(2*i)=='a'){
				moves[i][0] = 0;
			}else if(gameMoves.charAt(2*i)=='b'){
				moves[i][0] = 1;
			}else if(gameMoves.charAt(2*i)=='c'){
				moves[i][0] = 2;
			}else if(gameMoves.charAt(2*i)=='d'){
				moves[i][0] = 3;
			}else if(gameMoves.charAt(2*i)=='e'){
				moves[i][0] = 4;
			}else if(gameMoves.charAt(2*i)=='f'){
				moves[i][0] = 5;
			}else if(gameMoves.charAt(2*i)=='g'){
				moves[i][0] = 6;
			}else if(gameMoves.charAt(2*i)=='h'){
				moves[i][0] = 7;
			}else if(gameMoves.charAt(2*i)=='0'){
				moves[i][0] = -1;
			}
			
			moves[i][1] = Integer.parseInt(""+gameMoves.charAt(2*i+1))-1;
		}
		char[][] board = new char[8][8];
		board[3][3] = 'w';
		board[4][3] = 'b';
		board[4][4] = 'w';
		board[3][4] = 'b';
		char current = 'b';
		for(int i = 0;i<60;i++){
			setHeur(board, whiteHeur, i, 'w');
			setHeur(board, blackHeur, i, 'b');
			if(moves[i][0]!=-1){
				if(current=='b'){
					if(legalMove(board, current, moves[i][0],moves[i][1])){
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						current = 'w';
					}else{
						current = 'w';
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						current = 'b';
					}
				}else{
					if(legalMove(board, current, moves[i][0],moves[i][1])){
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						current = 'b';
					}else{
						current = 'b';
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						current = 'w';
					}
				}
			}
		}
		whiteHeur=setHeur(board, whiteHeur, 60, 'w');
		blackHeur=setHeur(board, blackHeur, 60, 'b');
		FileWriter writer;
		try {
			writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Downloads\\Games\\"+gameNumber+".txt"));
			writer.append("w\r\n");
			for(int j =0;j<4;j++){
				for(int i = 0;i<61;i++){
					writer.append(""+whiteHeur[i][j]+", ");
				}
				writer.append("\r\n");
			}
			writer.append("b\r\n");
			for(int j =0;j<4;j++){
				for(int i = 0;i<61;i++){
					writer.append(""+blackHeur[i][j]+", ");
				}
				writer.append("\r\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static float[][] setHeur(char[][] board, float[][] heur, int i, char c) {
		heur[i][0] = minimaxCoin(board, 4, true, c);
		heur[i][1] = minimaxCorner(board, 4, true, c);
		heur[i][2] = minimaxMobility(board, 4, true, c);
		heur[i][3] = empHelper(board, c);
		return heur;
	}
	
	private static int minimaxCoin(char[][] board, int depth2, boolean player, char c) {
		if(depth2==0){
			return coinParity(board, c);
		}
		if(player){
			int min = Integer.MAX_VALUE;
			for(int i = 0;i<8;i++){
				for(int j = 0;j<8;j++){
					if(c=='b'&&!legalMove(board, 'w', i,j)){
						continue;
					}
					if(c=='w'&&!legalMove(board, 'b', i,j)){
						continue;
					}
					char[][] newBoard = new char[8][8];
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							newBoard[a][b] = board[a][b];
							if(a==i&&b==j){
								if(c=='w'){
									newBoard[a][b] = 'b';
								}
								if(c=='b'){
									newBoard[a][b] = 'w';
								}
							}
						}
					}
					if(c=='w'){
						flip(newBoard,'b',i,j);
						newBoard[i][j]='b';
					}else{
						flip(newBoard, 'w',i,j);
						newBoard[i][j]='w';
					}
					int current = minimaxCoin(newBoard, depth2-1, false, c);
					if(current<min){
						min = current;
					}
				}
			}
			return min;
		}else{
			int max = Integer.MIN_VALUE;
			for(int i = 0;i<8;i++){
				for(int j = 0;j<8;j++){
					if(!legalMove(board, c, i,j)){
						continue;
					}
					char[][] newBoard = new char[8][8];
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							newBoard[a][b] = board[a][b];
							if(a==i&&b==j){
								newBoard[a][b] = c;
							}
						}
					}
					flip(newBoard,c,i,j);
					newBoard[i][j]=c;
					int current = minimaxCoin(newBoard, depth2-1, true, c);
					if(current>max){
						max = current;
					}
				}
			}
			return max;
		}
	}
	
	private static int minimaxCorner(char[][] board, int depth2, boolean player, char c) {
		if(depth2==0){
			return corner(board, c);
		}
		if(player){
			int min = Integer.MAX_VALUE;
			for(int i = 0;i<8;i++){
				for(int j = 0;j<8;j++){
					if(c=='b'&&!legalMove(board, 'w', i,j)){
						continue;
					}
					if(c=='w'&&!legalMove(board, 'b', i,j)){
						continue;
					}
					char[][] newBoard = new char[8][8];
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							newBoard[a][b] = board[a][b];
							if(a==i&&b==j){
								if(c=='w'){
									newBoard[a][b] = 'b';
								}
								if(c=='b'){
									newBoard[a][b] = 'w';
								}
							}
						}
					}
					if(c=='w'){
						flip(newBoard,'b',i,j);
						newBoard[i][j]='b';
					}else{
						flip(newBoard, 'w',i,j);
						newBoard[i][j]='w';
					}
					int current = minimaxCorner(newBoard, depth2-1, false, c);
					if(current<min){
						min = current;
					}
				}
			}
			return min;
		}else{
			int max = Integer.MIN_VALUE;
			for(int i = 0;i<8;i++){
				for(int j = 0;j<8;j++){
					if(!legalMove(board, c, i,j)){
						continue;
					}
					char[][] newBoard = new char[8][8];
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							newBoard[a][b] = board[a][b];
							if(a==i&&b==j){
								newBoard[a][b] = c;
							}
						}
					}
					flip(newBoard,c,i,j);
					newBoard[i][j]=c;
					int current = minimaxCorner(newBoard, depth2-1, true, c);
					if(current>max){
						max = current;
					}
				}
			}
			return max;
		}
	}
	
	private static int minimaxMobility(char[][] board, int depth2, boolean player, char c) {
		if(depth2==0){
			return coinParity(board, c);
		}
		if(player){
			int min = Integer.MAX_VALUE;
			for(int i = 0;i<8;i++){
				for(int j = 0;j<8;j++){
					if(c=='b'&&!legalMove(board, 'w', i,j)){
						continue;
					}
					if(c=='w'&&!legalMove(board, 'b', i,j)){
						continue;
					}
					char[][] newBoard = new char[8][8];
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							newBoard[a][b] = board[a][b];
							if(a==i&&b==j){
								if(c=='w'){
									newBoard[a][b] = 'b';
								}
								if(c=='b'){
									newBoard[a][b] = 'w';
								}
							}
						}
					}
					if(c=='w'){
						flip(newBoard,'b',i,j);
						newBoard[i][j]='b';
					}else{
						flip(newBoard, 'w',i,j);
						newBoard[i][j]='w';
					}
					int current = minimaxMobility(newBoard, depth2-1, false, c);
					if(current<min){
						min = current;
					}
				}
			}
			return min;
		}else{
			int max = Integer.MIN_VALUE;
			for(int i = 0;i<8;i++){
				for(int j = 0;j<8;j++){
					if(!legalMove(board, c, i,j)){
						continue;
					}
					char[][] newBoard = new char[8][8];
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							newBoard[a][b] = board[a][b];
							if(a==i&&b==j){
								newBoard[a][b] = c;
							}
						}
					}
					flip(newBoard,c,i,j);
					newBoard[i][j]=c;
					int current = minimaxMobility(newBoard, depth2-1, true, c);
					if(current>max){
						max = current;
					}
				}
			}
			return max;
		}
	}

	private static int coinParity(char[][] board, char c){
		int total = 0;
		int max = 0;

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j]==c){
					max++;
				}
				if(board[i][j]!=0){
					total++;
				}
			}
		}

		return (100*(max-(total-max)))/total;
	}

	private static int corner(char[][] board, char c){
		int total = 0;
		int max = 0;

		if(board[0][0]==c){
			total++;
			max++;
		}
		if(board[0][0]!=0){
			total++;
		}

		if(board[7][0]==c){
			total++;
			max++;
		}
		if(board[7][0]!=0){
			total++;
		}

		if(board[7][7]==c){
			total++;
			max++;
		}
		if(board[7][7]!=0){
			total++;
		}

		if(board[0][7]==c){
			total++;
			max++;
		}
		if(board[0][7]!=0){
			total++;
		}

		if(total==0){
			return coinParity(board, c);
		}

		return (100*(max-(total-max)))/total;
	}

	public static boolean legalMove(char[][] board, char looking, int row, int column) {
		if(board[row][column]!=0)
			return false;
		if(checkUpLeft(board, looking, row, column))
			return true;
		if(checkDownLeft(board, looking, row, column))
			return true;
		if(checkUpRight(board, looking, row, column))
			return true;
		if(checkDownRight(board, looking, row, column))
			return true;
		if(checkLeft(board, looking, row, column))
			return true;
		if(checkUp(board, looking, row, column))
			return true;
		if(checkDown(board, looking, row, column))
			return true;
		if(checkRight(board, looking, row, column))
			return true;
		return false;
	}


	public static boolean checkRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column+i<8&&board[row][column+i]!=looking&&board[row][column+i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(column+i<8&&board[row][column+i]==looking){
					return true;
				}else if(column+i<8&&board[row][column+i]==0){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkDown(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&board[row+i][column]!=looking&&board[row+i][column]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&board[row+i][column]==looking){
					return true;
				}if(row+i<8&&board[row+i][column]==0){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkUp(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=0&&board[row-i][column]!=looking&&board[row-i][column]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=0&&board[row-i][column]==looking){
					return true;
				}if(row-i>=0&&board[row-i][column]==0){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column-i>=0&&board[row][column-i]!=looking&&board[row][column-i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(column-i>=0&&board[row][column-i]==looking){
					return true;
				}if(column-i>=0&&board[row][column-i]==0){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkDownRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column+i<8&&board[row+i][column+i]!=looking&&board[row+i][column+i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&column+i<8&&board[row+i][column+i]==looking){
					return true;
				}if(row+i<8&&column+i<8&&board[row+i][column+i]==0){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean checkUpRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=0&&column+i<8&&board[row-i][column+i]!=looking&&board[row-i][column+i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=0&&column+i<8&&board[row-i][column+i]==looking){
					return true;
				}if(row-i>=0&&column+i<8&&board[row-i][column+i]==0){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean checkDownLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column-i>=0&&board[row+i][column-i]!=looking&&board[row+i][column-i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&column-i>=0&&board[row+i][column-i]==looking){
					return true;
				}if(row+i<8&&column-i>=0&&board[row+i][column-i]==0){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean checkUpLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=0&&column-i>=0&&board[row-i][column-i]!=looking&&board[row-i][column-i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=0&&column-i>=0&&board[row-i][column-i]==looking){
					return true;
				}if(row-i>=0&&column-i>=0&&board[row-i][column-i]==0){
					return false;
				}
			}
		}
		return false;
	}
	
	public static void flip(char[][] board, char c, int i, int j) {
		if(checkRight(board, c,i,j)){
			flipRight(board, c,i,j);
		}
		if(checkLeft(board, c,i,j)){
			flipLeft(board, c,i,j);
		}
		if(checkUp(board, c,i,j)){
			flipUp(board, c,i,j);
		}
		if(checkDown(board, c,i,j)){
			flipDown(board, c,i,j);
		}
		if(checkUpLeft(board, c,i,j)){
			flipUpLeft(board, c,i,j);
		}
		if(checkDownLeft(board, c,i,j)){
			flipDownLeft(board, c,i,j);
		}
		if(checkUpRight(board, c,i,j)){
			flipUpRight(board, c,i,j);
		}
		if(checkDownRight(board, c,i,j)){
			flipDownRight(board, c,i,j);
		}
	}

	public static void flipUpLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i-index][j-index]!=0&&board[i-index][j-index]!=c){
			board[i-index][j-index]=c;
			index++;
		}
		board[i-index][j-index]=c;
	}

	public static void flipDownLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j-index]!=0&&board[i+index][j-index]!=c){
			board[i+index][j-index]=c;
			index++;
		}
		board[i+index][j-index]=c;
	}

	public static void flipUpRight(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i-index][j+index]!=0&&board[i-index][j+index]!=c){
			board[i-index][j+index]=c;
			index++;
		}
		board[i-index][j+index]=c;
	}

	public static void flipDownRight(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j+index]!=0&&board[i+index][j+index]!=c){
			board[i+index][j+index]=c;
			index++;
		}
		board[i+index][j+index]=c;
	}

	public static void flipLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i][j-index]!=0&&board[i][j-index]!=c){
			board[i][j-index]=c;
			index++;
		}
		board[i][j-index]=c;
	}

	public static void flipDown(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j]!=0&&board[i+index][j]!=c){
			board[index+i][j]=c;
			index++;
		}
		board[i+index][j]=c;
	}


	public static void flipUp(char[][] board, char c, int i, int j) {
		int index = 1;
		while(board[i-index][j]!=0&&board[i-index][j]!=c){
			board[i-index][j]=c;
			index++;
		}
		board[i-index][j]=c;
	}


	public static void flipRight(char[][] board, char c, int i, int j) {
		int index = 1;
		while(board[i][j+index]!=0&&board[i][j+index]!=c){
			board[i][j+index]=c;
			index++;
		}
		board[i][j+index]=c;
	}
	
	private static float empHelper(char[][] board, char looking){
		int depth = 1;
		while(rand.nextInt(10)<5&&depth<4){
			depth++;
		}
		return empowerment(board, looking,depth);
	}
	
	private static float empowerment(char[][] board, char looking, int depth){
		char oppPiece = 'b';
		if(looking=='b'){
			oppPiece = 'w';
		}
		
		ArrayList<Float> lookingEmp = new ArrayList<Float>();
		ArrayList<Float> oppEmp = new ArrayList<Float>();
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(legalMove(board, looking, i, j)){
					char[][] newBoard = new char[8][8];
					for(int a=0;a<8;a++){
						for(int b=0;b<8;b++){
							newBoard[a][b] = board[a][b];
						}
					}
					flip(newBoard,looking,i,j);
					newBoard[i][j]=looking;
					if(depth>0){
						lookingEmp.add(empowerment(newBoard, oppPiece, depth-1));
					}else{
						float moves = 0;
						for(int a = 0;a<8;a++){
							for(int b = 0;b<8;b++){
								if(legalMove(newBoard, looking, a,b)){
									moves++;
								}
							}
						}
						lookingEmp.add(moves);
					}
					float oppMoves = 0;
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							if(legalMove(newBoard, oppPiece, a,b)){
								oppMoves++;
							}
						}
					}
					oppEmp.add(oppMoves);
				}
			}
		}

		float total = (float) 0.0;
		for(int i = 0;i<lookingEmp.size();i++){
			total+=lookingEmp.get(i);
		}
		if(total==0.0){
			return coinParity(board, looking);
		}
		float value = (float) 0.0;
		for(int i = 0;i<lookingEmp.size();i++){
			value+= (oppEmp.get(i)-beta*lookingEmp.get(i))*(lookingEmp.get(i)/total);
		}
		
		return value;
	}

}
