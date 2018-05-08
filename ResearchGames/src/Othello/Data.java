package Othello;

public class Data{
	
	public int[][] base;
	public int[][] after;
	public int move;
	
	Data(int[][] base, int[][] after, int move){
		this.base = base;
		this.after = after;
		this.move = move;
	}
}