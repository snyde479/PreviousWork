package Othello;

import java.util.ArrayList;
import java.util.Random;

public class EmpowermentMovesPlayer extends Player{

	private final char c;
	private final float beta;
	private final Random rand = new Random();

	public EmpowermentMovesPlayer(char c, float beta){
		this.c = c;
		this.beta=beta;
	}

	@Override
	public int[] getMove(char[][] board) {
		int[] best = {0,0};
		float max = Float.MIN_VALUE;
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(!legalMove(board, c, i,j)){
					continue;
				}
				char[][] newBoard = new char[8][8];
				for(int a = 0;a<8;a++){
					for(int b = 0;b<8;b++){
						newBoard[a][b] = board[a][b];
						if(a==i&&b==j){
							newBoard[a][b] = c;
						}
					}
				}
				flip(newBoard,c,i,j);
				
				float current = Float.MIN_VALUE;
				if(c=='b'){
					current = empHelper(newBoard, 'w');
				}else{
					current = empHelper(newBoard, 'b');
				}
				if(current>max){
					best = new int[] {i,j};
					max = current;
				}
			}
		}
		return best;
	}
	
	private float empHelper(char[][] board, char looking){
		int depth = 1;
		while(rand.nextBoolean()){
			depth++;
		}
		return empowerment(board, looking, depth);
	}
	
	private float empowerment(char[][] board, char looking, int depth){
		char oppPiece = 'b';
		if(looking=='b'){
			oppPiece = 'w';
		}
		
		ArrayList<Float> lookingEmp = new ArrayList<Float>();
		ArrayList<Float> oppEmp = new ArrayList<Float>();
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(legalMove(board, looking, i, j)){
					char[][] newBoard = new char[8][8];
					for(int a=0;a<8;a++){
						for(int b=0;b<8;b++){
							newBoard[a][b] = board[a][b];
						}
					}
					flip(newBoard,looking,i,j);
					if(depth>0){
						lookingEmp.add(empowerment(newBoard, oppPiece, depth-1));
					}else{
						float moves = 0;
						for(int a = 0;a<8;a++){
							for(int b = 0;b<8;b++){
								if(legalMove(newBoard, looking, a,b)){
									moves++;
								}
							}
						}
						lookingEmp.add(moves);
					}
					float oppMoves = 0;
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							if(legalMove(newBoard, oppPiece, a,b)){
								oppMoves++;
							}
						}
					}
					oppEmp.add(oppMoves);
				}
			}
		}

		float total = (float) 0.0;
		for(int i = 0;i<lookingEmp.size();i++){
			total+=lookingEmp.get(i);
		}
		
		float value = (float) 0.0;
		for(int i = 0;i<lookingEmp.size();i++){
			value+= (oppEmp.get(i)-beta*lookingEmp.get(i))*(lookingEmp.get(i)/total);
		}
		
		return value;
	}

	public void flip(char[][] board, char c, int i, int j) {
		if(checkRight(board, c,i,j)){
			flipRight(board, c,i,j);
		}
		if(checkLeft(board, c,i,j)){
			flipLeft(board, c,i,j);
		}
		if(checkUp(board, c,i,j)){
			flipUp(board, c,i,j);
		}
		if(checkDown(board, c,i,j)){
			flipDown(board, c,i,j);
		}
		if(checkUpLeft(board, c,i,j)){
			flipUpLeft(board, c,i,j);
		}
		if(checkDownLeft(board, c,i,j)){
			flipDownLeft(board, c,i,j);
		}
		if(checkUpRight(board, c,i,j)){
			flipUpRight(board, c,i,j);
		}
		if(checkDownRight(board, c,i,j)){
			flipDownRight(board, c,i,j);
		}
	}

	public void flipUpLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i-index][j-index]!=0&&board[i-index][j-index]!=c){
			board[i-index][j-index]=c;
			index++;
		}
		board[i-index][j-index]=c;
	}

	public void flipDownLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j-index]!=0&&board[i+index][j-index]!=c){
			board[i+index][j-index]=c;
			index++;
		}
		board[i+index][j-index]=c;
	}

	public void flipUpRight(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i-index][j+index]!=0&&board[i-index][j+index]!=c){
			board[i-index][j+index]=c;
			index++;
		}
		board[i-index][j+index]=c;
	}

	public void flipDownRight(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j+index]!=0&&board[i+index][j+index]!=c){
			board[i+index][j+index]=c;
			index++;
		}
		board[i+index][j+index]=c;
	}

	public void flipLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i][j-index]!=0&&board[i][j-index]!=c){
			board[i][j-index]=c;
			index++;
		}
		board[i][j-index]=c;
	}

	public void flipDown(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j]!=0&&board[i+index][j]!=c){
			board[index+i][j]=c;
			index++;
		}
		board[i+index][j]=c;
	}


	public void flipUp(char[][] board, char c, int i, int j) {
		int index = 1;
		while(board[i-index][j]!=0&&board[i-index][j]!=c){
			board[i-index][j]=c;
			index++;
		}
		board[i-index][j]=c;
	}


	public void flipRight(char[][] board, char c, int i, int j) {
		int index = 1;
		while(board[i][j+index]!=0&&board[i][j+index]!=c){
			board[i][j+index]=c;
			index++;
		}
		board[i][j+index]=c;
	}


	public boolean legalMove(char[][] board, char c, int row, int column) {
		if(board[row][column]!=0)
			return false;
		if(checkUpLeft(board, c, row, column))
			return true;
		if(checkDownLeft(board, c, row, column))
			return true;
		if(checkUpRight(board, c, row, column))
			return true;
		if(checkDownRight(board, c, row, column))
			return true;
		if(checkLeft(board, c, row, column))
			return true;
		if(checkUp(board, c, row, column))
			return true;
		if(checkDown(board, c, row, column))
			return true;
		if(checkRight(board, c, row, column))
			return true;
		return false;
	}


	public boolean checkRight(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column+i<8&&board[row][column+i]!=c&&board[row][column+i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(column+i<8&&board[row][column+i]==c){
					return true;
				}
			}
		}
		return false;
	}


	public boolean  checkDown(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&board[row+i][column]!=c&&board[row+i][column]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&board[row+i][column]==c){
					return true;
				}
			}
		}
		return false;
	}


	public boolean  checkUp(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=0&&board[row-i][column]!=c&&board[row-i][column]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=0&&board[row-i][column]==c){
					return true;
				}
			}
		}
		return false;
	}


	public boolean  checkLeft(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column-i>=0&&board[row][column-i]!=c&&board[row][column-i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(column-i>=0&&board[row][column-i]==c){
					return true;
				}
			}
		}
		return false;
	}


	public boolean  checkDownRight(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column+i<8&&board[row+i][column+i]!=c&&board[row+i][column+i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&column+i<8&&board[row+i][column+i]==c){
					return true;
				}
			}
		}
		return false;
	}


	public boolean checkUpRight(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=0&&column+i<8&&board[row-i][column+i]!=c&&board[row-i][column+i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=0&&column+i<8&&board[row-i][column+i]==c){
					return true;
				}
			}
		}
		return false;
	}


	public boolean checkDownLeft(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column-i>=0&&board[row+i][column-i]!=c&&board[row+i][column-i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&column-i>=0&&board[row+i][column-i]==c){
					return true;
				}
			}
		}
		return false;
	}


	public boolean checkUpLeft(char[][] board, char c, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=0&&column-i>=0&&board[row-i][column-i]!=c&&board[row-i][column-i]!=0){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=0&&column-i>=0&&board[row-i][column-i]==c){
					return true;
				}
			}
		}
		return false;
	}

}
