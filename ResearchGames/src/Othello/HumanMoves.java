package Othello;

import java.util.ArrayList;

public class HumanMoves {

	public char[][] board;
	public char current;
	public ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
	int bWin = 0;
	int wWin = 0;
	int seen = 0;

	public boolean equals(HumanMoves o){
		if(this.current!=o.current){
			return false;
		}
		boolean arr = true;
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(arr&&board[i][j]!=o.board[i][j]){
					arr=false;
				}
			}
		}
		return arr;
	}
	
	public void addMove(int[] move, char[][] otherBoard, char winner){
		boolean arr = true;
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(arr&&board[i][j]!=otherBoard[i][j]){
					return;
				}
			}
		}
		boolean found = false;
		for(int i = 0;!found&&i<moves.size();i++){
			if(arr&&move[0]==moves.get(i).get(0)&&move[1]==moves.get(i).get(1)){
				found=true;
			}
		}
		
		if(!found){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(move[0]);
			temp.add(move[1]);
			moves.add(temp);
		}
		seen++;
		if(winner=='w'){
			wWin++;
		}else if(winner=='b'){
			bWin++;
		}
	}
	
	public String print(){
		String s = "";
		
		for(int i = 0 ;i<8;i++){
			for(int j = 0;j<7;j++){
				if(board[i][j]==0){
					s+="0, ";
				}else{
					s+=board[i][j]+", ";
				}
			}
			if(board[i][7]==0){
				s+="0\r\n";
			}else{
				s+=board[i][7]+"\r\n";
			}
		}
		
		s+= current+"\r\n"+wWin+"\r\n"+bWin+"\r\n"+seen+"\r\n";
		
		for(int i = 0;i<moves.size();i++){
			s+=moves.get(i).get(0)+", "+moves.get(i).get(1)+"\r\n";
		}
		
		return s;
	}
}
