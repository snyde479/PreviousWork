package Othello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DatabasePlayer extends Player {

	private final int[][] moves;
	private int index = 0;

	public DatabasePlayer(String file, int game) throws FileNotFoundException{
		File f = new File(file);
		Scanner scan = new Scanner(f);
		while(game!=0){
			scan.nextLine();
			game--;
		}
		String gameMoves = scan.nextLine();
		moves = new int[60][2];
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
		scan.close();
	}

	@Override
	public int[] getMove(char[][] board) {
		int[] move = new int[2];
		move[0] = moves[index][0];
		move[1] = moves[index][1];
		index++;
		return move;
	}

}
