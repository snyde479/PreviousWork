import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveGenerator {

	private static final char ZERO = 0;


	public static void main(String[] args) throws IOException {
		ArrayList<Board> boards = new ArrayList<Board>();
		File boardFolder = new File("/home/snyde479/Desktop/Database/Boards/");
		int numBoard = boardFolder.listFiles().length;
		int i = 0;
		File f = new File("/home/snyde479/Desktop/Database/Boards/"+i+".txt");
		while(f.exists()) {
			System.out.println(f.getAbsolutePath());
			Scanner scan = new Scanner(f);
			int j = 0;
			char[][] b = new char[8][8];
			while(scan.hasNext()){
				String s = scan.nextLine();
				String[] moves = s.split(",");
				System.out.println(s);
				for(int m = 0;m<8;m++){
					if(moves[m].trim().length()==0) {
						b[m][j] = 0;
					}else {
						b[m][j] = moves[m].trim().charAt(0);
					}
				}
				j++;
			}
			boards.add(new Board(b));
			scan.close();
			i++;
			f = new File("/home/snyde479/Desktop/Database/Boards/"+i+".txt");
		}

		File playerLocation = new File("/home/snyde479/Desktop/Database/Players/");
		File output = new File("/home/snyde479/Desktop/BoardMove.txt");
		output.createNewFile();
		FileWriter writer = new FileWriter(output);
		for(i=0;i<playerLocation.list().length;i++){
			File player = playerLocation.listFiles()[i];
			int first = -1;
			int second = -1;
			Scanner reader = new Scanner(player);
			while(reader.hasNextLine()){
				int start = -1;
				char c = 0;
				String line = null;
				while(start<=first&&reader.hasNext()){
					String firstLine = line;
					line = reader.nextLine();
					if(start==-1){
						if(first==-1){
							start = Integer.valueOf(line.split(" ")[0]);
						}else{
							start = first;
						}
						if(start==0){
							c='b';
						}else{
							c='w';
						}
						first = Integer.valueOf(line.split(" ")[0]);
					}else{
						second = Integer.valueOf(line.split(" ")[0]);
						Board b = boards.get(first);
						b = flip(b, Integer.valueOf(firstLine.split(" ")[1]), Integer.valueOf(firstLine.split(" ")[2]), c);
						Board b2 = boards.get(second);
						first = second;
						
						writer.write(b.toString()+"\r\n");
						writer.write(b2.toString()+"\r\n");
						writer.write(line.split(" ")[1]+" "+line.split(" ")[2]+"\r\n");
					}
				}
			}
			reader.close();
		}
		
		writer.flush();
		writer.close();
	}

	private static Board flip(Board b, int x, int y, char c){
		char[][] b2 = new char[8][8];
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				b2[i][j] = b.board[i][j];
			}
		}
		if(checkRight(b2,c,x,y)){
			flipRight(b2,c,x,y);
		}
		if(checkLeft(b2,c,x,y)){
			flipLeft(b2,c,x,y);
		}
		if(checkUp(b2,c,x,y)){
			flipUp(b2,c,x,y);
		}
		if(checkDown(b2,c,x,y)){
			flipDown(b2,c,x,y);
		}
		if(checkUpRight(b2,c,x,y)){
			flipUpRight(b2,c,x,y);
		}
		if(checkDownRight(b2,c,x,y)){
			flipDownRight(b2,c,x,y);
		}
		if(checkUpLeft(b2,c,x,y)){
			flipUpLeft(b2,c,x,y);
		}
		if(checkDownLeft(b2,c,x,y)){
			flipDownLeft(b2,c,x,y);
		}
		return new Board(b2);
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
