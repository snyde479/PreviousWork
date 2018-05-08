package Othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HumanParser {
	public static int threadNum = 5;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<HumanMoves>> boards = new ArrayList<ArrayList<HumanMoves>>();
		Scanner scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Downloads\\Games.txt"));
		int game = 1;

		/*while(game<=1){
			scan.nextLine();
			game++;
		}*/
		for(int i = 0;i<60;i++){
			boards.add(new ArrayList<HumanMoves>());
		}

		while(scan.hasNextLine()){
			if(game%1000==0){
				System.out.println(game);
			}
			String gameMoves = scan.nextLine();
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
			char winner = 0;
			int sum = 0;
			for(int i = 0; i<8;i++){
				for(int j = 0; j<8; j++){
					if(board[i][j] == 'w'){
						sum++;
					}else if(board[i][j]=='b'){
						sum--;
					}
				}
			}
			if(sum>0){
				winner = 'w';
			}else if(sum<0){
				winner = 'b';
			}

			board = new char[8][8];
			board[3][3] = 'w';
			board[4][3] = 'b';
			board[4][4] = 'w';
			board[3][4] = 'b';
			current = 'b';
			for(int i = 0;i<60;i++){
				if(moves[i][0]!=-1){
					boolean added = false;
					HumanMoves toAdd = new HumanMoves();
					char[][] newBoard = new char[8][8];
					for(int q = 0;q<8;q++){
						for(int p = 0;p<8;p++){
							newBoard[p][q] = board[p][q];
						}
					}
					toAdd.board = newBoard;
					toAdd.current = current;
					toAdd.seen = 1;
					if(winner=='w'){
						toAdd.wWin=1;
					}else if(winner =='b'){
						toAdd.bWin = 1;
					}
					for(int j = 0;!added&& j<boards.get(i).size();j++){
						if(boards.get(i).get(j).equals(toAdd)){
							boards.get(i).get(j).addMove(new int[]{moves[i][0], moves[i][1]},  board, winner);
							added=true;
						}
					}
					if(!added){
						ArrayList<Integer> move = new ArrayList<Integer>();
						move.add(moves[i][0]);
						move.add(moves[i][1]);
						toAdd.moves.add(move);
						boards.get(i).add(toAdd);
					}
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
			game++;
		}
		scan.close();

		int board = 0;
		for(int i = 0; i<boards.size();i++){
			for(int j = 0;j<boards.get(i).size();j++){
				File file = new File("C:\\Users\\Tim Snyder\\Downloads\\CNNData\\"+board+".txt");
				FileWriter writer = new FileWriter(file);
				writer.write(boards.get(i).get(j).print());
				writer.flush();
				writer.close();
				board++;
			}
		}
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
}
