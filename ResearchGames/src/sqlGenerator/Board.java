package sqlGenerator;

public class Board {
	//To store, board, whose turn, move (player who took it), opponent, year, winner, score
	private long[] board = new long[2];
	private char[][] positions = new char[8][8];
	private char turn;
	
	public Board(char[][] positions, char turn){
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				this.positions[i][j] = positions[i][j];
				board[0]*=2;
				board[1]*=2;
				if(positions[i][j]=='b') {
					board[0]+=1;
					board[1]+=1;
				}else if(positions[i][j]=='w') {
					board[0]+=1;
				}
			}
		}
		this.turn = turn;
	}
	
	public char whoTurn() {
		return turn;
	}
	
	public int hashCode() {
		return (int)(board[0]/2);
	}
	
	public boolean equals(Board o) {
		return this.board[0]==o.board[0]&&this.board[1]==o.board[1];
	}
}
