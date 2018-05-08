package Battleship;

public abstract class Player {

	public abstract int[] getGuess(char[][] p1Guesses, int turn);
	
	public abstract void printEnd();
}
