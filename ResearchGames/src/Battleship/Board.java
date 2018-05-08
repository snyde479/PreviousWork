package Battleship;

public class Board {

	public final char[][] board;
	
	public Board(char[][] board){
		this.board = board;
	}
	
	public Board clone(){
		char[][] newBoard = new char[AllBoardsGenerator.BOARD_SIZE][AllBoardsGenerator.BOARD_SIZE];
		for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
			for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
				newBoard[i][j]=board[i][j];
			}
		}
		
		return new Board(newBoard);
	}
}
