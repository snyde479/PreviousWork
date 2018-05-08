package Battleship;

import java.util.ArrayList;

public class AllBoardsGenerator {
	
	public static final int BOARD_SIZE = 8;
	private static ArrayList<Board> boards = null;
	
	public static ArrayList<Board> getBoards(){
		if(boards==null){
			Board board = new Board(new char[BOARD_SIZE][BOARD_SIZE]);
			int[] ships = new int[]{3,4,5};
			boards=new ArrayList<Board>();
			boards = getBoardHelper(board,ships,'1');
			/*boards.add(board);
			for(int i = 0;i<ships.length;i++){
				ArrayList<Board> newBoards = new ArrayList<Board>();
				for(int a = boards.size()-1;a>=0;a--){
					for(int m = 0;m<BOARD_SIZE;m++){
						for(int n = 0;n<BOARD_SIZE;n++){
							board = boards.get(a).clone();
							boolean added = true;
							for(int p = 0;p<ships[i];p++){
								if(m+p>=BOARD_SIZE){
									added=false;
								}else if(board.board[m+p][n]!=0){
									added = false;
								}else{
									board.board[m+p][n] = (char) ('0'+i+1);
								}
							}
							if(added){
								newBoards.add(board);
							}
						}
					}
					for(int m = 0;m<BOARD_SIZE;m++){
						for(int n = 0;n<BOARD_SIZE;n++){
							board = boards.get(a).clone();
							boolean added = true;
							for(int p = 0;p<ships[i];p++){
								if(n+p>=BOARD_SIZE){
									added=false;
								}else if(board.board[p][n+p]!=0){
									added = false;
								}else{
									board.board[m][n+p] = (char) ('0'+i+1);
								}
							}
							if(added){
								newBoards.add(board);
							}
						}
					}
					boards.remove(a);
				}
				
				boards = newBoards;
				System.out.println(boards.size());
			}*/
		}
		return boards;
	}
	
	private static ArrayList<Board> getBoardHelper(Board board, int[] ships, char c){
		if(ships.length==1){
			ArrayList<Board> boards = new ArrayList<Board>();
			for(int i = 0;i<BOARD_SIZE;i++){
				for(int j = 0;j<BOARD_SIZE;j++){
					boolean works = true;
					for(int a = 0;works&&a<ships[0];a++){
						if(i+a>=BOARD_SIZE||board.board[i+a][j]!=0){
							works=false;
						}
					}
					if(works){
						Board b = board.clone();
						for(int a = 0;works&&a<ships[0];a++){
							b.board[i+a][j] = c;
						}
						boards.add(b);
					}
					
					works = true;
					for(int a = 0;works&&a<ships[0];a++){
						if(j+a>=BOARD_SIZE||board.board[i][j+a]!=0){
							works=false;
						}
					}
					if(works){
						Board b = board.clone();
						for(int a = 0;works&&a<ships[0];a++){
							b.board[i][j+a] = c;
						}
						boards.add(b);
					}
				}
			}
			return boards;
		}
		ArrayList<Board> boards = new ArrayList<Board>();
		for(int i = 0;i<BOARD_SIZE;i++){
			for(int j = 0;j<BOARD_SIZE;j++){
				boolean works = true;
				for(int a = 0;works&&a<ships[0];a++){
					if(i+a>=BOARD_SIZE||board.board[i+a][j]!=0){
						works=false;
					}
				}
				if(works){
					Board b = board.clone();
					for(int a = 0;works&&a<ships[0];a++){
						b.board[i+a][j] = c;
					}
					int[] newS = new int[ships.length-1];
					for(int a = 1;a<ships.length;a++){
						newS[a-1] = ships[a];
					}
					ArrayList<Board> bs = getBoardHelper(b,newS,(char) (c+1));
					for(Board bo:bs){
						boards.add(bo);
					}
				}
				
				works = true;
				for(int a = 0;works&&a<ships[0];a++){
					if(j+a>=BOARD_SIZE||board.board[i][j+a]!=0){
						works=false;
					}
				}
				if(works){
					Board b = board.clone();
					for(int a = 0;works&&a<ships[0];a++){
						b.board[i][j+a] = c;
					}
					int[] newS = new int[ships.length-1];
					for(int a = 1;a<ships.length;a++){
						newS[a-1] = ships[a];
					}
					ArrayList<Board> bs = getBoardHelper(b,newS,(char) (c+1));
					for(Board bo:bs){
						boards.add(bo);
					}
				}
			}
		}
		return boards;
	}

}
