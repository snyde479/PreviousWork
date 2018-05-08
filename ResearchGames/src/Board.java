

public class Board {

	public char[][] board = new char[8][8];
	private int sum = 0;
	
	public Board(char[][][] boards, int a){
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				board[i][j] = boards[i][j][a];
				if(board[i][j]=='w'){
					sum -= (i*j+(7-i)*(7-j));
				}else if(board[i][j]=='b'){
					sum += (i*j+(7-i)*(7-j));
				}
			}
		}
	}
	
	public Board(char[][] b){
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				board[i][j] = b[i][j];
				if(board[i][j]=='w'){
					sum -= (i*j+(7-i)*(7-j));
				}else if(board[i][j]=='b'){
					sum += (i*j+(7-i)*(7-j));
				}
			}
		}
	}
	
	public String toString(){
		String s = "";
		for(int i = 0;i<8;i++){
			for(int j = 0;j<7;j++){
				s+=board[i][j]+",";
			}
			s+=board[i][7]+"\r\n";
		}
		return s;
	}
	
	public int rotation(Board o){
		boolean temp = true;
		for(int i = 0;temp&&i<8;i++){
			for(int j = 0;temp&&j<8;j++){
				if(this.board[i][j]!=o.board[i][j]){
					temp=false;
				}
			}
		}
		if(temp) return 1;
		temp = true;
		for(int i = 0;temp&&i<8;i++){
			for(int j = 0;temp&&j<8;j++){
				if(this.board[i][j]!=o.board[j][i]){
					temp=false;
				}
			}
		}
		if(temp) return 2;
		temp = true;
		for(int i = 0;temp&&i<8;i++){
			for(int j = 0;temp&&j<8;j++){
				if(this.board[i][j]!=o.board[7-i][7-j]){
					temp=false;
				}
			}
		}
		if(temp) return 3;
		return 4;
	}
	
	public boolean equals(Board o){
		if(this.sum!=o.sum)
			return false;
		boolean same = false;
		boolean temp = true;
		for(int i = 0;temp&&i<8;i++){
			for(int j = 0;temp&&j<8;j++){
				if(this.board[i][j]!=o.board[i][j]){
					temp=false;
				}
			}
		}
		same = same||temp;
		temp = true;
		for(int i = 0;temp&&i<8;i++){
			for(int j = 0;temp&&j<8;j++){
				if(this.board[i][j]!=o.board[j][i]){
					temp=false;
				}
			}
		}
		same = same||temp;
		temp = true;
		for(int i = 0;temp&&i<8;i++){
			for(int j = 0;temp&&j<8;j++){
				if(this.board[i][j]!=o.board[7-i][7-j]){
					temp=false;
				}
			}
		}
		same = same||temp;
		temp = true;
		for(int i = 0;temp&&i<8;i++){
			for(int j = 0;temp&&j<8;j++){
				if(this.board[i][j]!=o.board[7-j][7-i]){
					temp=false;
				}
			}
		}
		same = same||temp;
		return same;
	}
}
