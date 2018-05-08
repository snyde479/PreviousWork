package Othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Parser {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		String folder = "C:\\Users\\Tim Snyder\\Downloads\\CNNData\\";
		int numFiles = 4132425;
		char[][][] arr = new char[8][8][numFiles];
		int[] turns = new int[numFiles];
		int[] wWin = new int[numFiles];
		int[] bWin = new int[numFiles];
		int size = 0;
		boolean[] bools = new boolean[numFiles];
		char[] nextPlayer = new char[numFiles];
		ArrayList<ArrayList<String>> moves = new ArrayList<ArrayList<String>>();
		for(int i = 0;i<numFiles;i++){
			Scanner scan = new Scanner(new File(folder+(i)+".txt"));
			int turn = 0;
			for(int j = 0;j<8;j++){
				String[] s = scan.nextLine().split(", ");
				for(int k = 0;k<8;k++){
					if(s[k].charAt(0)=='b'){
						//arr[j][k][i] = 1;
						arr[j][k][i] = 'b';
						turn++;
					}else if(s[k].charAt(0)=='w'){
						//arr[j][k][i] = -1;
						arr[j][k][i] = 'w';
						turn++;
					}else{
						arr[j][k][i] = 0;
					}
				}
			}
			turns[i] = turn;
			String next = scan.nextLine();
			if(next.charAt(0)=='w'){
				nextPlayer[i] = 'w';
				/*for(int j = 0;j<8;j++){
					for(int k = 0;k<8;k++){
						arr[j][k][i] *= -1;
					}
				}*/
			}else{
				nextPlayer[i] = 'b';
			}
			wWin[i] = Integer.parseInt(scan.nextLine());
			bWin[i] = Integer.parseInt(scan.nextLine());
			scan.nextLine();
			bools[i]=true;
			moves.add(new ArrayList<String>());
			while(scan.hasNext()){
				moves.get(i).add(scan.nextLine());
			}
			scan.close();
		}
		Writer writerBoards = new FileWriter(new File("C:\\Users\\Tim Snyder\\matrix.csv"));
		Writer writerSet = new FileWriter(new File("C:\\Users\\Tim Snyder\\set.csv"));
		Writer writerAnswers = new FileWriter(new File("C:\\Users\\Tim Snyder\\answers.csv"));
		Random rand = new Random(100);
		for(int i = 0;i<numFiles;i++){
			for(String s:moves.get(i)){
				char[][] first = new char[8][8];
				for(int a = 0;a<8;a++){
					for(int b = 0;b<8;b++){
						first[a][b] = arr[a][b][i];
					}
				}
				int m = Integer.parseInt(s.split(", ")[0]);
				int n = Integer.parseInt(s.split(", ")[1]);
				if(m==-1||n==-1){
					continue;
				}
				char[][] newBoard = flip(first, nextPlayer[i], m,n);
				for(int j = 0;j<numFiles;j++){
					if(turns[i]+1==turns[j]&&sameBoard(newBoard, arr, j)){
						int move = 0;
						int w = 0;
						int b = 0;
						for(String t:moves.get(j)){
							int m2 = Integer.parseInt(t.split(", ")[0]);
							int n2 = Integer.parseInt(t.split(", ")[1]);
							if(m2==-1||n2==-1){
								continue;
							}
							char[][] newBoard2 = flip(newBoard, nextPlayer[j], m2,n2);
							for(int l = 0;l<numFiles;l++){
								if(turns[i]+2==turns[l]&&sameBoard(newBoard2, arr, l)){
									if(move == 0){
										move = m2+1+n2*8;
										w = wWin[l];
										b = bWin[l];
									}else if(w*bWin[l]>b*wWin[l]&&nextPlayer[i]=='b'){
										move = m2+1+n2*8;
										w = wWin[l];
										b = bWin[l];
									}else if(w*bWin[l]<b*wWin[l]&&nextPlayer[i]=='w'){
										move = m2+1+n2*8;
										w = wWin[l];
										b = bWin[l];
									}
								}
							}
						}
						if(move!=0){
							for(int q = 0;q<8;q++){
								for(int e = 0;e<7;e++){
									if(first[q][e]==0){
										writerBoards.write("0,");
									}else if(first[q][e]==nextPlayer[i]){
										writerBoards.write("1,");
									}else{
										writerBoards.write("-1,");
									}
								}
								if(first[q][7]==0){
									writerBoards.write("0\r\n");
								}else if(first[q][7]==nextPlayer[i]){
									writerBoards.write("1\r\n");
								}else{
									writerBoards.write("-1\r\n");
								}
							}
							for(int q = 0;q<8;q++){
								for(int e = 0;e<7;e++){
									if(newBoard[q][e]==0){
										writerBoards.write("0,");
									}else if(newBoard[q][e]==nextPlayer[i]){
										writerBoards.write("1,");
									}else{
										writerBoards.write("-1,");
									}
								}
								if(newBoard[q][7]==0){
									writerBoards.write("0\r\n");
								}else if(newBoard[q][7]==nextPlayer[i]){
									writerBoards.write("1\r\n");
								}else{
									writerBoards.write("-1\r\n");
								}
							}
							if(rand.nextInt(10)==9){
								writerSet.write(2+"\r\n");
							}else{
								writerSet.write(1+"\r\n");
							}
							writerAnswers.write(move+"\r\n");
							size++;
						}
					}
				}
			}
			System.out.println(i);
		}
		System.out.println(size);
		writerBoards.flush();
		writerSet.flush();
		writerAnswers.flush();
		writerBoards.close();
		writerSet.close();
		writerAnswers.close();
	}
	
	public static boolean sameBoard(char[][] board, char[][][] allBoards, int index){
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j]!=allBoards[i][j][index]){
					return false;
				}
			}
		}
		return true;
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

	public static char[][] flip(char[][] board, char c, int i, int j) {
		char[][] toReturn = new char[8][8];
		for(int a = 0;a<8;a++){
			for(int b = 0;b<8;b++){
				toReturn[a][b] = board[a][b];
			}
		}
		if(checkRight(toReturn, c,i,j)){
			flipRight(toReturn, c,i,j);
		}
		if(checkLeft(toReturn, c,i,j)){
			flipLeft(toReturn, c,i,j);
		}
		if(checkUp(toReturn, c,i,j)){
			flipUp(toReturn, c,i,j);
		}
		if(checkDown(toReturn, c,i,j)){
			flipDown(toReturn, c,i,j);
		}
		if(checkUpLeft(toReturn, c,i,j)){
			flipUpLeft(toReturn, c,i,j);
		}
		if(checkDownLeft(toReturn, c,i,j)){
			flipDownLeft(toReturn, c,i,j);
		}
		if(checkUpRight(toReturn, c,i,j)){
			flipUpRight(toReturn, c,i,j);
		}
		if(checkDownRight(toReturn, c,i,j)){
			flipDownRight(toReturn, c,i,j);
		}
		toReturn[i][j]=c;
		return toReturn;
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
