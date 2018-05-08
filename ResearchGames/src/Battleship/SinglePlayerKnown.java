package Battleship;

public class SinglePlayerKnown {

	private Player p1;
	private char[][] p1Guesses = new char[AllBoardsGenerator.BOARD_SIZE][AllBoardsGenerator.BOARD_SIZE];
	private Board ships;
	
	public SinglePlayerKnown(Player p1, Board ships){
		this.p1 = p1;
		this.ships = ships;
	}
	
	public int playGame(){
		int turn = 0;
		while(!sunk(ships)){
			int[] guess = p1.getGuess(p1Guesses,turn);
			if(ships.board[guess[0]][guess[1]]==0){
				p1Guesses[guess[0]][guess[1]] = 'm';
			}else{
				p1Guesses[guess[0]][guess[1]] = ships.board[guess[0]][guess[1]];
			}
			turn++;
		}
		p1.printEnd();

		return turn;
	}

	public boolean hit(Board ships2, int[] guess) {
		return ships2.board[guess[0]][guess[1]]!=0;
	}

	public boolean sunk(Board ships2) {
		for(int i =0;i<AllBoardsGenerator.BOARD_SIZE;i++){
			for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
				if(ships2.board[i][j]!=0&&p1Guesses[i][j]==0){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean sinks(Board ships2, int[] guess){
		int hit = ships2.board[guess[0]][guess[1]];
		for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
			for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
				if(i!=guess[0]&&j!=guess[1]&&ships2.board[i][j]==hit&&p1Guesses[i][j]==0){
					return false;
				}
			}
		}
		return true;
	}
}
