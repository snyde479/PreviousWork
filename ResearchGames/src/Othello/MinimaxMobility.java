package Othello;

public class MinimaxMobility extends Player{

	private final int depth;
	private final char c;

	public MinimaxMobility(int depth, char c){
		this.depth = depth;
		this.c = c;
	}

	@Override
	public int[] getMove(char[][] board) {
		int[] best = {0,0};
		int max = Integer.MIN_VALUE;
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
				int current = minimax(newBoard, depth, true);
				if(current>max){
					best = new int[] {i,j};
					max = current;
				}
			}
		}
		return best;
	}

	private int minimax(char[][] board, int depth2, boolean player) {
		if(depth2==0){
			return heuristic(board);
		}
		if(player){
			int min = Integer.MAX_VALUE;
			for(int i = 0;i<8;i++){
				for(int j = 0;j<8;j++){
					if(c=='b'&&!legalMove(board, 'w', i,j)){
						continue;
					}
					if(c=='w'&&!legalMove(board, 'b', i,j)){
						continue;
					}
					char[][] newBoard = new char[8][8];
					for(int a = 0;a<8;a++){
						for(int b = 0;b<8;b++){
							newBoard[a][b] = board[a][b];
							if(a==i&&b==j){
								if(c=='w'){
									newBoard[a][b] = 'b';
								}
								if(c=='b'){
									newBoard[a][b] = 'w';
								}
							}
						}
					}
					if(c=='w'){
						flip(newBoard,'b',i,j);
					}else{
						flip(newBoard, 'w',i,j);
					}
					int current = minimax(newBoard, depth2-1, false);
					if(current<min){
						min = current;
					}
				}
			}
			return min;
		}else{
			int max = Integer.MIN_VALUE;
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
					int current = minimax(newBoard, depth2-1, true);
					if(current>max){
						max = current;
					}
				}
			}
			return max;
		}
	}

	private int heuristic(char[][] board) {
		return mobility(board);
	}
	
	private int coinParity(char[][] board){
		int total = 0;
		int max = 0;
		
		for(int i = 0;i<8;i++){
			for(int j = 0;j<9;j++){
				if(board[i][j]==c){
					max++;
				}
				if(board[i][j]!=0){
					total++;
				}
			}
		}
		
		return (100*(max-(total-max)))/total;
	}
	
	private int mobility(char[][] board){
		int total = 0;
		int max = 0;
		
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(legalMove(board, c,i,j)){
					total++;
					max++;
				}else if(legalMove(board, 'w',i,j)||legalMove(board,'b',i,j)){
					total++;
				}
			}
		}

		if(total==0){
			return coinParity(board);
		}
		
		return (100*(max-(total-max)))/total;
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
