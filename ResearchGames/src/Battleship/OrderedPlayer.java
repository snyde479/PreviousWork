package Battleship;

public class OrderedPlayer extends Player {

	int g1 = 0;
	int g2 = 0;
	
	@Override
	public int[] getGuess(char[][] p1Guesses,int turn) {
		int[] toR = new int[]{g1, g2};
		g1++;
		if(g1==AllBoardsGenerator.BOARD_SIZE){
			g1=0;
			g2++;
		}
		return toR;
	}

	@Override
	public void printEnd() {
		// TODO Auto-generated method stub
		
	}

}
