package Battleship;

import java.util.Random;

public class RandomPlayer extends Player {

	Random rand = new Random();
	
	@Override
	public int[] getGuess(char[][] p1Guesses,int turn) {
		int g1 = rand.nextInt(AllBoardsGenerator.BOARD_SIZE);
		int g2 = rand.nextInt(AllBoardsGenerator.BOARD_SIZE);
		while(p1Guesses[g1][g2]!=0){
			g1 = rand.nextInt(AllBoardsGenerator.BOARD_SIZE);
			g2 = rand.nextInt(AllBoardsGenerator.BOARD_SIZE);
		}
		return new int[]{g1,g2};
	}

	@Override
	public void printEnd() {
		// TODO Auto-generated method stub
		
	}

}
