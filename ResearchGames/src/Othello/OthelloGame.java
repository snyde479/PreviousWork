package Othello;

//16 step look ahead at most
//empowerment is based on # of moves available
//Find polani's empowerment paper to cite

public class OthelloGame {

	private Player p1;
	private Player p2;
	public char[][] board = new char[8][8];

	public OthelloGame(Player p1, Player p2){
		this.p1 = p1;
		this.p2 = p2;
		board[4][4] = 'w';
		board[5][5] = 'w';
		board[4][5] = 'b';
		board[5][4] = 'b';
	}


	public Player playGame(){

		while(hasMoves()){
			if(hasMovesB()){
				int[] move = p1.getMove(board);
				if(legalMove('b',move[0],move[1])){
					flip('b', move[0], move[1]);
				}
				board[move[0]][move[1]] = 'b';
			}
			if(hasMovesW()){
				int[] move = p2.getMove(board);
				if(legalMove('w',move[0],move[1])){
					flip('w', move[0], move[1]);
				}
				board[move[0]][move[1]] = 'w';
			}
		}

		if(p1Win()){
			return p1;
		}else if(p2Win()){
			return p2;
		}else{
			return null;
		}
	}


	public void flip(char c, int i, int j) {
		if(checkRight(c,i,j)){
			flipRight(c,i,j);
		}
		if(checkLeft(c,i,j)){
			flipLeft(c,i,j);
		}
		if(checkUp(c,i,j)){
			flipUp(c,i,j);
		}
		if(checkDown(c,i,j)){
			flipDown(c,i,j);
		}
		if(checkUpLeft(c,i,j)){
			flipUpLeft(c,i,j);
		}
		if(checkDownLeft(c,i,j)){
			flipDownLeft(c,i,j);
		}
		if(checkUpRight(c,i,j)){
			flipUpRight(c,i,j);
		}
		if(checkDownRight(c,i,j)){
			flipDownRight(c,i,j);
		}
	}
	
	public void flipUpLeft(char c, int i, int j){
		int index = 1;
		while(board[i-index][j-index]!=0&&board[i-index][j-index]!=c){
			board[i-index][j-index]=c;
			index++;
		}
		board[i-index][j-index]=c;
	}
	
	public void flipDownLeft(char c, int i, int j){
		int index = 1;
		while(board[i+index][j-index]!=0&&board[i+index][j-index]!=c){
			board[i+index][j-index]=c;
			index++;
		}
		board[i+index][j-index]=c;
	}
	
	public void flipUpRight(char c, int i, int j){
		int index = 1;
		while(board[i-index][j+index]!=0&&board[i-index][j+index]!=c){
			board[i-index][j+index]=c;
			index++;
		}
		board[i-index][j+index]=c;
	}
	
	public void flipDownRight(char c, int i, int j){
		int index = 1;
		while(board[i+index][j+index]!=0&&board[i+index][j+index]!=c){
			board[i+index][j+index]=c;
			index++;
		}
		board[i+index][j+index]=c;
	}
	
	public void flipLeft(char c, int i, int j){
		int index = 1;
		while(board[i][j-index]!=0&&board[i][j-index]!=c){
			board[i][j-index]=c;
			index++;
		}
		board[i][j-index]=c;
	}
	
	public void flipDown(char c, int i, int j){
		int index = 1;
		while(board[i+index][j]!=0&&board[i+index][j]!=c){
			board[index+i][j]=c;
			index++;
		}
		board[i+index][j]=c;
	}


	public void flipUp(char c, int i, int j) {
		int index = 1;
		while(board[i-index][j]!=0&&board[i-index][j]!=c){
			board[i-index][j]=c;
			index++;
		}
		board[i-index][j]=c;
	}


	public void flipRight(char c, int i, int j) {
		int index = 1;
		while(board[i][j+index]!=0&&board[i][j+index]!=c){
			board[i][j+index]=c;
			index++;
		}
		board[i][j+index]=c;
	}


	public boolean hasMovesB() {
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j]== 0){
					if(legalMove('b',i,j)){
						return true;
					}
				}
			}
		}
		return false;
	}


	public boolean hasMovesW() {
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j]== 0){
					if(legalMove('w',i,j)){
						return true;
					}
				}
			}
		}
		return false;
	}


	public boolean hasMoves() {
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j]== 0){
					if(legalMove('w',i,j)||legalMove('b',i,j)){
						return true;
					}
				}
			}
		}
		return false;
	}


	public boolean legalMove(char c, int row, int column) {
		if(board[row][column]!=0)
			return false;
		if(checkUpLeft(c, row, column))
			return true;
		if(checkDownLeft(c, row, column))
			return true;
		if(checkUpRight(c, row, column))
			return true;
		if(checkDownRight(c, row, column))
			return true;
		if(checkLeft(c, row, column))
			return true;
		if(checkUp(c, row, column))
			return true;
		if(checkDown(c, row, column))
			return true;
		if(checkRight(c, row, column))
			return true;
		return false;
	}


	public boolean checkRight(char c, int row, int column) {
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


	public boolean  checkDown(char c, int row, int column) {
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


	public boolean  checkUp(char c, int row, int column) {
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


	public boolean  checkLeft(char c, int row, int column) {
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


	public boolean  checkDownRight(char c, int row, int column) {
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


	public boolean checkUpRight(char c, int row, int column) {
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


	public boolean checkDownLeft(char c, int row, int column) {
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


	public boolean checkUpLeft(char c, int row, int column) {
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


	public boolean p2Win() {
		int w = 0;

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j]=='w'){
					w++;
				}
			}
		}
		return w>32;
	}


	public boolean p1Win() {
		int b = 0;

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j]=='b'){
					b++;
				}
			}
		}

		return b>32;
	}
}
