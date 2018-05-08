


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DatabaseGenerator {

	private final static char ZERO = 0;
	private static ArrayList<ArrayList<Board>> boards = new ArrayList<ArrayList<Board>>();
	private static HashMap<Integer, ArrayList<BoardMove>> map = new HashMap<Integer, ArrayList<BoardMove>>();

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int game = 0;
		for(int year = 1998;year<=2015;year++){
			boards = new ArrayList<ArrayList<Board>>();
			for(int i = 0;i<=60;i++){
				boards.add(new ArrayList<Board>());
			}
			map = new HashMap<Integer, ArrayList<BoardMove>>();
			InputStream scan = new FileInputStream(new File("/home/snyde479/Desktop/WTH_7708/WTH_"+year+".wtb"));
			ArrayList<Byte> chars = new ArrayList<Byte>();
			int b = scan.read();
			while(b!=-1){
				chars.add((byte)b);
				b = scan.read();
			}
			scan.close();

			chars.remove(0);
			chars.remove(0);
			chars.remove(0);
			chars.remove(0);

			chars.remove(0);
			chars.remove(0);
			chars.remove(0);
			chars.remove(0);

			chars.remove(0);
			chars.remove(0);
			chars.remove(0);
			chars.remove(0);

			chars.remove(0);
			chars.remove(0);
			chars.remove(0);
			chars.remove(0);

			char[] cs = {'0','a','b','c','d','e','f','g','h','0'};
			char[] ns = {'0','1','2','3','4','5','6','7','8','0'};
			for(int i = 0;i<chars.size()/68;i++){
				String s = "";
				int playerW = chars.get(i*68+4)*8+chars.get(i*68+5);
				int playerB = chars.get(i*68+2)*8+chars.get(i*68+3);
				int bScore = chars.get(i*64+6);
				for(int j = 0;j<68;j++){
					if(chars.get(i*68+j)<0){
						s+="00";
					}else{
						s+=cs[chars.get(i*68+j)%10];
						s+=ns[(chars.get(i*68+j)/10)%10];
					}
				}

				s = s.substring(16);
				int[][] moves = new int[60][2];
				for(int j =0;j<60;j++){
					if(s.charAt(2*j)=='a'){
						moves[j][0] = 0;
					}else if(s.charAt(2*j)=='b'){
						moves[j][0] = 1;
					}else if(s.charAt(2*j)=='c'){
						moves[j][0] = 2;
					}else if(s.charAt(2*j)=='d'){
						moves[j][0] = 3;
					}else if(s.charAt(2*j)=='e'){
						moves[j][0] = 4;
					}else if(s.charAt(2*j)=='f'){
						moves[j][0] = 5;
					}else if(s.charAt(2*j)=='g'){
						moves[j][0] = 6;
					}else if(s.charAt(2*j)=='h'){
						moves[j][0] = 7;
					}else if(s.charAt(2*j)=='0'){
						moves[j][0] = -1;
					}

					moves[j][1] = Integer.parseInt(""+s.charAt(2*j+1))-1;
				}

				makeBoards(moves, playerB, playerW,bScore>32);
				game++;
				if(game%1000==0){
					System.out.println(game);
				}
			}
			int numB = 0;
			new File("/home/snyde479/Desktop/Board/"+year+"/").mkdirs();
			for(int i = 0;i<boards.size()-1;i++){
				for(int j = 0;j<boards.get(i).size();j++){
					FileWriter writer = new FileWriter(new File("/home/snyde479/Desktop/Board/"+year+"/"+numB+".txt"));
					writer.write(boards.get(i).get(j).toString()+"\r\n");
					writer.flush();
					writer.close();
					numB++;
				}
			}
			int[] sizes = new int[60];
			for(int i = 0;i<59;i++){
				sizes[i+1] = sizes[i]+ boards.get(i).size();
			}
			new File("/home/snyde479/Desktop/Player/"+year+"/").mkdirs();
			for(Entry<Integer,ArrayList<BoardMove>> e:map.entrySet()){
				FileWriter writer = new FileWriter(new File("/home/snyde479/Desktop/Player/"+year+"/"+e.getKey()+".txt"));
				for(BoardMove bm:e.getValue()){
					writer.write((sizes[bm.layer]+bm.board)+" "+bm.move+" "+bm.won+"\r\n");
				}
				writer.flush();
				writer.close();
			}
			System.out.println(year);
		}
	}

	private static void makeBoards(int[][] moves, int bPlayer, int wPlayer, boolean won) {
		if(!map.containsKey(bPlayer)){
			map.put(bPlayer, new ArrayList<BoardMove>());
		}
		if(!map.containsKey(wPlayer)){
			map.put(wPlayer, new ArrayList<BoardMove>());
		}
		char[][] board = new char[8][8];
		board[3][3] = 'w';
		board[4][3] = 'b';
		board[4][4] = 'w';
		board[3][4] = 'b';
		char current = 'b';
		char other = 'w';
		for(int i = 0;i<60;i++){
			int index = findBoard(board, current,other, i);
			if(moves[i][0]!=-1){
				if(current=='b'){
					if(legalMove(board, current, moves[i][0],moves[i][1])){
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						int postMove = findBoard(board, current, other, i+1);
						addMove(board, i, index, postMove, wPlayer, moves[i][0],moves[i][1], won);
						current = 'w';
						other = 'b';
					}else{
						current = 'w';
						other = 'b';
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						int postMove = findBoard(board, current, other, i+1);
						addMove(board, i, index, postMove, bPlayer, moves[i][0],moves[i][1], won);
						current = 'b';
						other = 'w';
					}
				}else{
					if(legalMove(board, current, moves[i][0],moves[i][1])){
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						int postMove = findBoard(board, current, other, i+1);
						addMove(board, i, index, postMove, bPlayer, moves[i][0],moves[i][1], !won);
						current = 'b';
						other = 'w';
					}else{
						current = 'b';
						other = 'w';
						flip(board, current, moves[i][0],moves[i][1]);
						board[moves[i][0]][moves[i][1]] = current;
						int postMove = findBoard(board, current, other, i+1);
						addMove(board, i, index, postMove, wPlayer, moves[i][0],moves[i][1], !won);
						current = 'w';
						other = 'b';
					}
				}
			}else{
				break;
			}
		}
	}

	private static void addMove(char[][] board, int i, int index, int postMove, int player, int m, int n, boolean won) {
		Board b = new Board(board);
		int rot = b.rotation(boards.get(i+1).get(postMove));
		if(rot==1){
			map.get(player).add(new BoardMove(i, index, m+" "+n, won));
		}
		if(rot==2){
			map.get(player).add(new BoardMove(i, index, n+" "+m, won));
		}
		if(rot==3){
			map.get(player).add(new BoardMove(i, index, (7-m)+" "+(7-n), won));
		}
		if(rot==4){
			map.get(player).add(new BoardMove(i, index, (7-n)+" "+(7-m), won));
		}
	}

	private static int findBoard(char[][] board, char current, char other, int i) {
		Board b = new Board(board);
		int index = -1;
		for(int j = 0;index==-1&&j<boards.get(i).size();j++){
			if(boards.get(i).get(j).equals(b)){
				index = j;
			}
		}
		if(index==-1){
			index = boards.get(i).size();
			if(i==0){
				boards.get(i).add(b);
			}else{
				boolean found = false;
				ArrayList<Board> arrayList = boards.get(i-1);
				for(int j = 0;!found&&j<arrayList.size();j++){
					char[][] preBoard = arrayList.get(j).board;
					for(int m = 0;!found&&m<8;m++){
						for(int n = 0;!found&&n<8;n++){
							char looking = current;
							if(legalMove(preBoard,looking,m,n)){
								char[][] testBoard = copy(preBoard);
								flip(testBoard, looking,m,n);
								testBoard[m][n] = looking;
								Board b2 = new Board(testBoard);
								if(b.equals(b2)){
									boards.get(i).add(b2);
									found=true;
								}
							}
						}
					}
				}
				if(!found){
					for(int j = 0;!found&&j<arrayList.size();j++){
						char[][] preBoard = arrayList.get(j).board;
						for(int m = 0;!found&&m<8;m++){
							for(int n = 0;!found&&n<8;n++){
								char looking = other;
								if(legalMove(preBoard,looking,m,n)){
									char[][] testBoard = copy(preBoard);
									flip(testBoard, looking,m,n);
									testBoard[m][n] = looking;
									Board b2 = new Board(testBoard);
									if(b.equals(b2)){
										boards.get(i).add(b2);
										found=true;
									}
								}
							}
						}
					}
				}
			}
		}
		return index;
	}

	private static char[][] copy(char[][] preBoard) {
		char[][] copyVal = new char[8][8];
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				copyVal[i][j] = preBoard[i][j];
			}
		}
		return copyVal;
	}

	public static boolean legalMove(char[][] board, char looking, int row, int column) {
		if(board[row][column]!=ZERO)
			return false;
		if(checkUpLeft(board, looking, row, column))
			return true;
		if(checkDownLeft(board, looking, row, column))
			return true;
		if(checkUpRight(board, looking, row, column))
			return true;
		if(checkDownRight(board, looking, row, column))
			return true;
		if(checkLeft(board, looking, row, column))
			return true;
		if(checkUp(board, looking, row, column))
			return true;
		if(checkDown(board, looking, row, column))
			return true;
		if(checkRight(board, looking, row, column))
			return true;
		return false;
	}


	public static boolean checkRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column+i<8&&board[row][column+i]!=looking&&board[row][column+i]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(column+i<8&&board[row][column+i]==looking){
					return true;
				}else if(column+i<8&&board[row][column+i]==ZERO){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkDown(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&board[row+i][column]!=looking&&board[row+i][column]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&board[row+i][column]==looking){
					return true;
				}if(row+i<8&&board[row+i][column]==ZERO){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkUp(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=ZERO&&board[row-i][column]!=looking&&board[row-i][column]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=ZERO&&board[row-i][column]==looking){
					return true;
				}if(row-i>=ZERO&&board[row-i][column]==ZERO){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column-i>=ZERO&&board[row][column-i]!=looking&&board[row][column-i]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(column-i>=ZERO&&board[row][column-i]==looking){
					return true;
				}if(column-i>=ZERO&&board[row][column-i]==ZERO){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean  checkDownRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column+i<8&&board[row+i][column+i]!=looking&&board[row+i][column+i]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&column+i<8&&board[row+i][column+i]==looking){
					return true;
				}if(row+i<8&&column+i<8&&board[row+i][column+i]==ZERO){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean checkUpRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=ZERO&&column+i<8&&board[row-i][column+i]!=looking&&board[row-i][column+i]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=ZERO&&column+i<8&&board[row-i][column+i]==looking){
					return true;
				}if(row-i>=ZERO&&column+i<8&&board[row-i][column+i]==ZERO){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean checkDownLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column-i>=ZERO&&board[row+i][column-i]!=looking&&board[row+i][column-i]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row+i<8&&column-i>=ZERO&&board[row+i][column-i]==looking){
					return true;
				}if(row+i<8&&column-i>=ZERO&&board[row+i][column-i]==ZERO){
					return false;
				}
			}
		}
		return false;
	}


	public static boolean checkUpLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=ZERO&&column-i>=ZERO&&board[row-i][column-i]!=looking&&board[row-i][column-i]!=ZERO){
					opposite=true;
				}else{
					break;
				}
			}else{
				if(row-i>=ZERO&&column-i>=ZERO&&board[row-i][column-i]==looking){
					return true;
				}if(row-i>=ZERO&&column-i>=ZERO&&board[row-i][column-i]==ZERO){
					return false;
				}
			}
		}
		return false;
	}

	public static void flip(char[][] board, char c, int i, int j) {
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

	public static void flipUpLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i-index][j-index]!=ZERO&&board[i-index][j-index]!=c){
			board[i-index][j-index]=c;
			index++;
		}
		board[i-index][j-index]=c;
	}

	public static void flipDownLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j-index]!=ZERO&&board[i+index][j-index]!=c){
			board[i+index][j-index]=c;
			index++;
		}
		board[i+index][j-index]=c;
	}

	public static void flipUpRight(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i-index][j+index]!=ZERO&&board[i-index][j+index]!=c){
			board[i-index][j+index]=c;
			index++;
		}
		board[i-index][j+index]=c;
	}

	public static void flipDownRight(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j+index]!=ZERO&&board[i+index][j+index]!=c){
			board[i+index][j+index]=c;
			index++;
		}
		board[i+index][j+index]=c;
	}

	public static void flipLeft(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i][j-index]!=ZERO&&board[i][j-index]!=c){
			board[i][j-index]=c;
			index++;
		}
		board[i][j-index]=c;
	}

	public static void flipDown(char[][] board, char c, int i, int j){
		int index = 1;
		while(board[i+index][j]!=ZERO&&board[i+index][j]!=c){
			board[index+i][j]=c;
			index++;
		}
		board[i+index][j]=c;
	}


	public static void flipUp(char[][] board, char c, int i, int j) {
		int index = 1;
		while(board[i-index][j]!=ZERO&&board[i-index][j]!=c){
			board[i-index][j]=c;
			index++;
		}
		board[i-index][j]=c;
	}


	public static void flipRight(char[][] board, char c, int i, int j) {
		int index = 1;
		while(board[i][j+index]!=ZERO&&board[i][j+index]!=c){
			board[i][j+index]=c;
			index++;
		}
		board[i][j+index]=c;
	}

}
