package Battleship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EmpowermentPlayer extends Player {

	ArrayList<Board> boards;

	private static FileWriter writer = null;
	private static FileWriter writer2 = null;
	boolean bool;

	public EmpowermentPlayer(String file,boolean b){
		bool=b;
		if(writer==null&&b){
			try {
				writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if(writer2==null&&!b){
			try {
				writer2 = new FileWriter(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Board> temp = AllBoardsGenerator.getBoards();
		boards = new ArrayList<Board>();
		for(Board bo:temp){
			boards.add(bo);
		}
	}

	@Override
	public int[] getGuess(char[][] p1Guesses,int turn) {
		if(boards.size()==1){
			try {
				if(bool){
					writer.write(turn+" ");
					writer.flush();
				}else{
					writer2.write(turn+" ");
					writer2.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int m = 0;
		int n = 0;
		double p = Double.NEGATIVE_INFINITY;
		for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
			for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
				if(p1Guesses[i][j]==0){
					double[] hit = new double[6];
					double total = 0.0;
					for(int a = boards.size()-1;a>=0;a--){
						if(possible(p1Guesses,boards.get(a))&&boards.get(a).board[i][j]!=0){
							if(boards.get(a).board[i][j]==0)
								hit[0]++;
							else
								hit[boards.get(a).board[i][j]-'0']++;
							total++;
						}else if(possible(p1Guesses,boards.get(a))){
							total++;
						}else{
							boards.remove(a);
						}
					}
					double gain = 0.0;
					for(int a = 0;a<6;a++){
						if(hit[a]!=0.0){
							gain+= -Math.log(hit[a]/total)*hit[a]/total;
						}
					}
					if(gain==0.0&&hit[0]==total){
						gain=-10.0;
					}
					if(p<gain){
						m=i;
						n=j;
						p=gain;
					}
				}
			}
		}
		if(boards.size()==1){
			for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
				for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
					if(p1Guesses[i][j]==0&&boards.get(0).board[i][j]!=0){
						m=i;
						n=j;
					}
				}
			}
		}
		return new int[]{m,n};
	}

	private boolean possible(char[][] p1Guesses, Board board) {
		for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
			for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
				if(p1Guesses[i][j]!=board.board[i][j]&&p1Guesses[i][j]!=0){
					if(p1Guesses[i][j]=='h'&&board.board[i][j]==0){
						return false;
					}if(p1Guesses[i][j]=='m'&&board.board[i][j]!=0){
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void printEnd() {
		try {
			if(bool){
				writer.write("\r\n");
				writer.flush();
			}else{
				writer2.write("\r\n");
				writer2.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
