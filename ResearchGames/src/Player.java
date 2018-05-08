import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class Player {

	public HashMap<Integer,HashMap<String,Integer>> moves = new HashMap<Integer, HashMap<String,Integer>>();
	public HashMap<Integer,Integer> boardSeen = new HashMap<Integer,Integer>();
	public int num;
	
	public Player(File f) throws FileNotFoundException{
		Scanner r = new Scanner(f);
		num = Integer.valueOf(f.getAbsolutePath().split(File.separator)[f.getAbsolutePath().split(File.separator).length-1].replaceAll(".txt", ""));
		while(r.hasNextLine()){
			String[] s = r.nextLine().split(" ");
			if(s.length!=3){
				break;
			}
			Integer[] ints = new Integer[3];
			ints[0] = Integer.valueOf(s[0]);
			ints[1] = Integer.valueOf(s[1]);
			ints[2] = Integer.valueOf(s[2]);
			String string = ints[1]+" "+ints[2];
			if(moves.containsKey(ints[0])){
				if(moves.get(ints[0]).containsKey(string)){
					moves.get(ints[0]).put(string, moves.get(ints[0]).get(string)+1);
					boardSeen.put(ints[0],boardSeen.get(ints[0])+1);
				}else{
					moves.get(ints[0]).put(string,+1);
					boardSeen.put(ints[0],boardSeen.get(ints[0])+1);
				}
			}else{
				moves.put(ints[0], new HashMap<String,Integer>());
				moves.get(ints[0]).put(string,1);
				boardSeen.put(ints[0],1);
			}
		}
		r.close();
	}
	
	public void addMove(int board, String move){
		if(moves.containsKey(board)){
			if(moves.get(board).containsKey(move)){
				moves.get(board).put(move, moves.get(board).get(move)+1);
				boardSeen.put(board,boardSeen.get(board)+1);
			}else{
				moves.get(board).put(move, 1);
				boardSeen.put(board,boardSeen.get(board)+1);
			}
		}else{
			ArrayList<String> s = new ArrayList<String>();
			s.add(move);
			ArrayList<Integer> i = new ArrayList<Integer>();
			i.add(1);
			moves.put(board, new HashMap<String,Integer>());
			moves.get(board).put(move, 1);
			boardSeen.put(board,boardSeen.get(board)+1);
		}
	}
	
	public double phat(int board, String move){
		if(moves.containsKey(board)&&moves.get(board).containsKey(move)){
			double m = moves.get(board).get(move);
			double total = 0.0;
			for(Entry<String,Integer> e:moves.get(board).entrySet()){
				total+= e.getValue();
			}
			return m/total;
		}
		return 0.0;
	}
	
	public boolean hasBoard(int board){
		return moves.containsKey(board);
	}
	
	public boolean hasMove(int board, String move){
		return hasBoard(board)&&moves.get(board).containsKey(move);
	}
	
	public int getSeenBoard(int board){
		if(hasBoard(board)){
			return boardSeen.get(board);
		}
		return 0;
	}
}
