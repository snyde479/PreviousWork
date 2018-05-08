package Battleship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EmpowermentKnown extends Player {

	ArrayList<Board> boards;
	
	private static FileWriter writer = null;

	public EmpowermentKnown(String file){
		if(writer==null){
			try {
				writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Desktop\\8551\\Project\\"+file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<Board> temp = AllBoardsGenerator.getBoards();
		boards = new ArrayList<Board>();
		for(Board b:temp){
			boards.add(b);
		}
	}

	@Override
	public int[] getGuess(char[][] p1Guesses,int turn) {
		if(boards.size()==1){
			try {
				writer.write(turn+" ");
				writer.flush();
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

	public boolean possible(char[][] p1Guesses, Board board) {
		boolean s1 = false,s2=false,s3=false,s4=false,s5=false;

		for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
			for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
				if(p1Guesses[i][j]=='1')
					s1=true;
				if(p1Guesses[i][j]=='2')
					s2=true;
				if(p1Guesses[i][j]=='3')
					s3=true;
				if(p1Guesses[i][j]=='4')
					s4=true;
				if(p1Guesses[i][j]=='5')
					s5=true;
			}
		}
		for(int i = 0;i<AllBoardsGenerator.BOARD_SIZE;i++){
			for(int j = 0;j<AllBoardsGenerator.BOARD_SIZE;j++){
				if(p1Guesses[i][j]=='m'&&board.board[i][j]==0){
					continue;
				}
				if(p1Guesses[i][j]!=board.board[i][j]&&p1Guesses[i][j]!=0){
					if(p1Guesses[i][j]=='h'&&board.board[i][j]==0){
						return false;
					}if(p1Guesses[i][j]=='m'&&board.board[i][j]!=0){
						return false;
					}if(p1Guesses[i][j]!='h'){
						return false;
					}
				}
				if(s1&&p1Guesses[i][j]==0&&board.board[i][j]=='1'){
					return false;
				}
				if(s2&&p1Guesses[i][j]==0&&board.board[i][j]=='2'){
					return false;
				}
				if(s3&&p1Guesses[i][j]==0&&board.board[i][j]=='3'){
					return false;
				}
				if(s4&&p1Guesses[i][j]==0&&board.board[i][j]=='4'){
					return false;
				}
				if(s5&&p1Guesses[i][j]==0&&board.board[i][j]=='5'){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void printEnd() {
		try {
			writer.write("\r\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
