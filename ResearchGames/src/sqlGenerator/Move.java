package sqlGenerator;

public class Move {

	private long[] board;
	private int location;
	private char color;
	private String player;
	private String opponent;
	private int year;
	private char winner;
	private int wScore;
	private int bScore;
	
	public Move(long[] board, int location, char color, String player, String opponent, int year, char winner, int wScore, int bScore) {
		this.location = location;
		this.color=color;
		this.player=player;
		this.opponent=opponent;
		this.year=year;
		this.winner=winner;
		this.wScore=wScore;
		this.bScore=bScore;
	}
	
	public long[] getBoard(){
		return board;
	}
	
	public boolean sameBoard(long[] board) {
		return this.board.length==board.length&&this.board[0]==board[0]&&this.board[1]==board[1];
	}
	
	public int getLocation() {
		return location;
	}
	
	public char getColor() {
		return color;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public String getOpponent() {
		return opponent;
	}
	
	public int getYear() {
		return year;
	}
	
	public char getWinner() {
		return winner;
	}

	public int getWScore() {
		return wScore;
	}
	
	public int getBScore() {
		return bScore;
	}
}
