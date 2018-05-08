package Battleship;

public class SinglePlayerReveal {

	private Player p1;
	private char[][] p1Guesses = new char[AllBoardsGenerator.BOARD_SIZE][AllBoardsGenerator.BOARD_SIZE];
	private Board ships;
	
	public SinglePlayerReveal(Player p1, Board ships){
		this.p1 = p1;
		this.ships = ships;
	}
	
	public int playGame(){
		int turn = 0;
		while(!sunk(ships)){
			int[] guess = p1.getGuess(p1Guesses,turn);
			if(hit(ships, guess)){
				p1Guesses[guess[0]][guess[1]] = 'h';
				if(sinks(ships,guess)){
					char ship = ships.board[guess[0]][guess[1]];
					p1Guesses[guess[0]][guess[1]] = ship;
					for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
						for(int j=0;j<AllBoardsGenerator.BOARD_SIZE;j++){
							if(ships.board[i][j]==ship){
								p1Guesses[i][j]=ship;
							}
						}
					}
				}
			}else{
				p1Guesses[guess[0]][guess[1]] = 'm';
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
				if(ships2.board[i][j]==hit&&p1Guesses[i][j]=='h'){
					return false;
				}
			}
		}
		return true;
	}
}
