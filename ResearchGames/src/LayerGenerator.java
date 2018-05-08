import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LayerGenerator {

	private static final int ZERO = 0;


	public static void main(String[] args) throws IOException {
		int num = Integer.parseInt(args[0]);
		Scanner scan = new Scanner(new File("/home/snyde479/Desktop/BoardMove.txt"));
		FileWriter writer = new FileWriter(new File("/home/snyde479/Desktop/FinalData"+num+".txt"));
		FileWriter answers = new FileWriter(new File("/home/snyde479/Desktop/FinalAnswer"+num+".txt"));
		ArrayList<String> boards = new ArrayList<String>();
		while(scan.hasNextLine()){
			String b = "";
			for(int i = 0;i<19;i++){
				if(i==8||i==17) {
					scan.nextLine();
				}else {
					b+=scan.nextLine()+"\r\n";
				}
			}
			boards.add(b);
		}
		Collections.sort(boards);

		boolean skip = true;
		for(int i = (num-1)*boards.size()/10;i<num*boards.size()/10;i++){
			int start = i;
			if(i<boards.size()&&!boards.get(start).substring(0, 256).equals(boards.get(start-1).substring(0, 256))) {
				skip = false;
			}
			for(;i<boards.size();i++){
				if(!boards.get(start).substring(0, 256).equals(boards.get(i).substring(0, 256))){
					i--;
					break;
				}
			}
			if(!skip) {
				int end = i;
				if(! (i<boards.size())) {
					i=boards.size()-1;
				}
				writer.write(createLayers(boards, start, end));
				double[][] ans = new double[8][8];
				for(int j = start;j<=end;j++){
					int length = boards.get(j).length()-5;
					ans[Integer.parseInt(boards.get(j).substring(length).split(" ")[0])][Integer.parseInt(boards.get(j).substring(length).split(" ")[1].trim())]++;
				}
				System.out.println("Finished creating ans");
				NumberFormat formatter = new DecimalFormat("#0.0000");
				for(int j = 0;j<8;j++){
					for(int h = 0;h<8;h++){
						answers.write(formatter.format(ans[j][h]/((double) 1+end-start)));
						if(h==7){
							answers.write("\r\n");
						}else{
							answers.write(",");
						}
					}
				}
				writer.flush();
				answers.flush();
				System.out.println("board "+i+" finished");
			}
			skip = false;
		}
		scan.close();
		writer.close();
		answers.close();

	}

	private static String createLayers(ArrayList<String> boards, int start, int end) {
		String result = "";
		String player = null;
		String oPlayer = null;
		int w1 = 0;
		int w2 = 0;
		for(int i = 0;i<64;i++){
			if(boards.get(start).charAt(i)=='w'){
				w1++;
			}
		}
		for(int i = 64;i<128;i++){
			if(boards.get(start).charAt(i)=='w'){
				w2++;
			}
		}
		if(w1>w2){
			player = "b";
			oPlayer = "w";
		}else{
			player = "w";
			oPlayer = "b";
		}
		char[][] board = new char[8][8];

		String s = boards.get(start);
		String[] lines = s.split("\r\n");
		for(int i = 0;i<8;i++){
			String[] line = lines[i].split(",");
			for(int j = 0;j<8;j++){
				if(line[j].equals(player)){
					result+="1";
					board[i][j] = player.charAt(0);
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			String[] line = lines[i].split(",");
			for(int j = 0;j<8;j++){
				if(line[j].equals(oPlayer)){
					result+="1";
					board[i][j] = oPlayer.charAt(0);
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, oPlayer.charAt(0),i,j)){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, oPlayer.charAt(0),i,j)&&pointsWorth(board,oPlayer.charAt(0),i,j)/16==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, oPlayer.charAt(0),i,j)&&(pointsWorth(board,oPlayer.charAt(0),i,j)%16)/8==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, oPlayer.charAt(0),i,j)&&(pointsWorth(board,oPlayer.charAt(0),i,j)%8)/4==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, oPlayer.charAt(0),i,j)&&(pointsWorth(board,oPlayer.charAt(0),i,j)%4)/2==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, oPlayer.charAt(0),i,j)&&(pointsWorth(board,oPlayer.charAt(0),i,j)%2)==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			String[] line = lines[i+8].split(",");
			for(int j = 0;j<8;j++){
				if(line[j].equals(player)){
					result+="1";
					board[i][j] = player.charAt(0);
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			String[] line = lines[i+8].split(",");
			for(int j = 0;j<8;j++){
				if(line[j].equals(oPlayer)){
					result+="1";
					board[i][j] = oPlayer.charAt(0);
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, player.charAt(0),i,j)){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, player.charAt(0),i,j)&&pointsWorth(board,player.charAt(0),i,j)/16==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, player.charAt(0),i,j)&&(pointsWorth(board,player.charAt(0),i,j)%16)/8==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, player.charAt(0),i,j)&&(pointsWorth(board,player.charAt(0),i,j)%8)/4==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, player.charAt(0),i,j)&&(pointsWorth(board,player.charAt(0),i,j)%4)/2==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(check(board, player.charAt(0),i,j)&&(pointsWorth(board,player.charAt(0),i,j)%2)==1){
					result+="1";
				}else{
					result+="0";
				}
				if(j<7){
					result+=",";
				}else{
					result+="\r\n";
				}
			}
		}

		return result;
	}

	private static int pointsWorth(char[][] board, char charAt, int i, int j) {
		int value = 0;
		if(checkRight(board, charAt, i, j)){
			value+=countRight(board, charAt,i ,j);
		}
		if(checkLeft(board, charAt, i, j)){
			value+=countLeft(board, charAt,i ,j);
		}
		if(checkUp(board, charAt, i, j)){
			value+=countUp(board, charAt,i ,j);
		}
		if(checkDown(board, charAt, i, j)){
			value+=countDown(board, charAt,i ,j);
		}
		if(checkUpRight(board, charAt, i, j)){
			value+=countUpRight(board, charAt,i ,j);
		}
		if(checkDownRight(board, charAt, i, j)){
			value+=countDownRight(board, charAt,i ,j);
		}
		if(checkUpLeft(board, charAt, i, j)){
			value+=countUpLeft(board, charAt,i ,j);
		}
		if(checkDownLeft(board, charAt, i, j)){
			value+=countDownLeft(board, charAt,i ,j);
		}
		return value;
	}

	private static int countDownLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		int count = 0;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column-i>=ZERO&&board[row+i][column-i]!=looking&&board[row+i][column-i]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	private static int countUpLeft(char[][] board, char looking, int row, int column) {
		int count = 0;
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=ZERO&&column-i>=ZERO&&board[row-i][column-i]!=looking&&board[row-i][column-i]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	private static int countDownRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		int count = 0;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&column+i<8&&board[row+i][column+i]!=looking&&board[row+i][column+i]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	private static int countUpRight(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		int count = 0;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=ZERO&&column+i<8&&board[row-i][column+i]!=looking&&board[row-i][column+i]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	private static int countDown(char[][] board, char looking, int row, int column) {
		int count=0;
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row+i<8&&board[row+i][column]!=looking&&board[row+i][column]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	private static int countUp(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		int count= 0;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(row-i>=ZERO&&board[row-i][column]!=looking&&board[row-i][column]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	private static int countLeft(char[][] board, char looking, int row, int column) {
		boolean opposite = false;
		int count = 0;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column-i>=ZERO&&board[row][column-i]!=looking&&board[row][column-i]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	private static int countRight(char[][] board, char looking, int row, int column) {
		int count=0;
		boolean opposite = false;
		for(int i = 1; i<8;i++){
			if(!opposite){
				if(column+i<8&&board[row][column+i]!=looking&&board[row][column+i]!=ZERO){
					opposite=true;
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}

	public static boolean check(char[][] board, char looking, int row, int column){
		return checkRight(board, looking, row, column)||checkLeft(board, looking, row, column)||checkUp(board, looking, row, column)||checkDown(board, looking, row, column)||checkUpRight(board, looking, row, column)||checkDownRight(board, looking, row, column)||checkUpLeft(board, looking, row, column)||checkDownLeft(board, looking, row, column);
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

}
