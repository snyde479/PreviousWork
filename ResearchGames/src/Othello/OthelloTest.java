package Othello;

import static org.junit.Assert.*;

import org.junit.Test;

public class OthelloTest {

	@Test
	public void testWin() {
		OthelloGame game = new OthelloGame(null, null);
		char[][] board = new char[8][8];
		assertFalse(game.p2Win());
		assertFalse(game.p1Win());
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				board[i][j] ='w';
			}
		}
		game.board = board;
		assertFalse(game.p1Win());
		assertTrue(game.p2Win());
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				board[i][j] ='b';
			}
		}
		game.board = board;
		assertFalse(game.p2Win());
		assertTrue(game.p1Win());
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if((i+j)%2==0)
					board[i][j] ='b';
				else
					board[i][j] = 'w';
			}
		}
		game.board = board;
		assertFalse(game.p1Win());
		assertFalse(game.p2Win());
		board[1][1] = 'w';
		game.board = board;
		assertFalse(game.p1Win());
		assertTrue(game.p2Win());
	}
	
	@Test
	public void testCheck(){
		OthelloGame game = new OthelloGame(null, null);
		char[][] board = new char[8][8];
		board[1][1] = 'w';
		board[0][0] = 'b';
		board[1][0] = 'b';
		board[0][1] = 'b';
		board[0][2] = 'b';
		
		board[6][6] = 'w';
		board[7][7] = 'b';
		board[6][7] = 'b';
		board[7][6] = 'b';
		board[7][5] = 'b';
		
		board[1][6] = 'b';
		board[0][5] = 'w';
		board[0][6] = 'w';
		board[0][7] = 'w';
		board[1][5] = 'w';
		
		board[6][1] = 'b';
		board[7][2] = 'w';
		board[6][2] = 'w';
		board[7][1] = 'w';
		board[7][0] = 'w';

		game.board=board;
		
		assertTrue(game.legalMove('b',1,2));
		assertTrue(game.legalMove('b',2,2));
		assertTrue(game.legalMove('b',2,1));
		assertTrue(game.legalMove('b',2,0));

		assertTrue(game.legalMove('b',5,6));
		assertTrue(game.legalMove('b',6,5));
		assertTrue(game.legalMove('b',5,5));
		assertTrue(game.legalMove('b',5,7));

		assertTrue(game.legalMove('w',1,7));
		assertTrue(game.legalMove('w',2,7));
		assertTrue(game.legalMove('w',2,6));
		assertTrue(game.legalMove('w',2,5));

		assertTrue(game.legalMove('w',5,2));
		assertTrue(game.legalMove('w',6,0));
		assertTrue(game.legalMove('w',5,0));
		assertTrue(game.legalMove('w',5,1));
		

		
		assertTrue(game.checkLeft('b',1,2));
		assertTrue(game.checkUpLeft('b',2,2));
		assertTrue(game.checkUp('b',2,1));
		assertTrue(game.checkUpRight('b',2,0));

		assertTrue(game.checkDown('b',5,6));
		assertTrue(game.checkRight('b',6,5));
		assertTrue(game.checkDownRight('b',5,5));
		assertTrue(game.checkDownLeft('b',5,7));

		assertTrue(game.checkLeft('w',1,7));
		assertTrue(game.checkUpLeft('w',2,7));
		assertTrue(game.checkUp('w',2,6));
		assertTrue(game.checkUpRight('w',2,5));

		assertTrue(game.checkDownLeft('w',5,2));
		assertTrue(game.checkRight('w',6,0));
		assertTrue(game.checkDownRight('w',5,0));
		assertTrue(game.checkDown('w',5,1));
		

		
		assertFalse(game.legalMove('w',1,2));
		assertFalse(game.legalMove('w',2,2));
		assertFalse(game.legalMove('w',2,1));
		assertFalse(game.legalMove('w',2,0));

		assertFalse(game.legalMove('w',5,6));
		assertFalse(game.legalMove('w',6,5));
		assertFalse(game.legalMove('w',5,5));
		assertFalse(game.legalMove('w',5,7));

		assertFalse(game.legalMove('b',1,7));
		assertFalse(game.legalMove('b',2,7));
		assertFalse(game.legalMove('b',2,6));
		assertFalse(game.legalMove('b',2,5));

		assertFalse(game.legalMove('b',5,2));
		assertFalse(game.legalMove('b',6,0));
		assertFalse(game.legalMove('b',5,0));
		assertFalse(game.legalMove('b',5,1));
		

		
		assertFalse(game.checkLeft('w',1,2));
		assertFalse(game.checkUpLeft('w',2,2));
		assertFalse(game.checkUp('w',2,1));
		assertFalse(game.checkUpRight('w',2,0));

		assertFalse(game.checkDown('w',5,6));
		assertFalse(game.checkRight('w',6,5));
		assertFalse(game.checkDownRight('w',5,5));
		assertFalse(game.checkDownLeft('w',5,7));

		assertFalse(game.checkLeft('b',1,7));
		assertFalse(game.checkUpLeft('b',2,7));
		assertFalse(game.checkUp('b',2,6));
		assertFalse(game.checkUpRight('b',2,5));

		assertFalse(game.checkDownLeft('b',5,2));
		assertFalse(game.checkRight('b',6,0));
		assertFalse(game.checkDownRight('b',5,0));
		assertFalse(game.checkDown('b',5,1));
		
		assertFalse(game.legalMove('w', 4,4));
	}
	
	@Test
	public void testFlip(){
		OthelloGame game = new OthelloGame(null, null);
		char[][] board = new char[8][8];
		board[0][0] = 'b';
		board[0][3] = 'b';
		board[0][6] = 'b';
		board[3][0] = 'b';
		board[6][0] = 'b';
		board[3][6] = 'b';
		board[6][3] = 'b';
		board[6][6] = 'b';

		board[1][1] = 'w';
		board[1][3] = 'w';
		board[1][5] = 'w';
		board[2][2] = 'w';
		board[3][1] = 'w';
		board[5][1] = 'w';
		board[2][3] = 'w';
		board[2][4] = 'w';
		board[3][2] = 'w';
		board[3][4] = 'w';
		board[3][5] = 'w';
		board[4][2] = 'w';
		board[4][3] = 'w';
		board[4][4] = 'w';
		board[5][5] = 'w';
		board[5][3] = 'w';
		game.board=board;
		game.flip('b', 3, 3);
		int sum = 0;
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(game.board[i][j]=='b'){
					sum++;
				}
			}
		}
		assertTrue(sum==24);
		
		game = new OthelloGame(null, null);
		board = new char[8][8];
		board[0][0] = 'w';
		board[0][3] = 'w';
		board[0][6] = 'w';
		board[3][0] = 'w';
		board[6][0] = 'w';
		board[3][6] = 'w';
		board[6][3] = 'w';
		board[6][6] = 'w';

		board[1][1] = 'b';
		board[1][3] = 'b';
		board[1][5] = 'b';
		board[2][2] = 'b';
		board[3][1] = 'b';
		board[5][1] = 'b';
		board[2][3] = 'b';
		board[2][4] = 'b';
		board[3][2] = 'b';
		board[3][4] = 'b';
		board[3][5] = 'b';
		board[4][2] = 'b';
		board[4][3] = 'b';
		board[4][4] = 'b';
		board[5][5] = 'b';
		board[5][3] = 'b';
		game.board=board;
		game.flip('w', 3, 3);
		sum = 0;
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(game.board[i][j]=='w'){
					sum++;
				}
			}
		}
		assertTrue(sum==24);
	}
	
	@Test
	public void testStructure(){
		char[][] board = new char[8][8];
		board[1][1] = 'w';
		board[0][0] = 'b';
		board[1][0] = 'b';
		board[0][1] = 'b';
		board[0][2] = 'b';
		
		HumanMoves moves1 = new HumanMoves();
		moves1.board=board;
		moves1.current='w';
		
		HumanMoves moves2 = new HumanMoves();
		moves2.board = board;
		moves2.current='w';
		
		assertTrue(moves1.equals(moves2));
		
		moves2.current='b';
		
		assertFalse(moves1.equals(moves2));
		
		char[][] board2 = new char[8][8];
		board2[1][1] = 'b';
		board2[0][0] = 'b';
		board2[1][0] = 'b';
		board2[0][1] = 'b';
		board2[0][2] = 'b';
		moves2.board = board2;
		
		assertFalse(moves1.equals(moves2));

		moves2.current='w';
		
		assertFalse(moves1.equals(moves2));
	}

}
