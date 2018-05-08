import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class PlayerCoallator {
	
	static HashMap<Integer, ArrayList<String>> playerMap = new HashMap<Integer, ArrayList<String>>();
	
	public static void main(String[] args) throws IOException{
		String outputFolder = "/home/snyde479/Desktop/Database/Players/";
		for(int year = 1977;year<=1997;year++){
			File[] players = (new File("/home/snyde479/Desktop/Player/"+year+"/")).listFiles();
			HashMap<Integer, Integer> boardsByYear = new HashMap<Integer, Integer>();
			Scanner boards = new Scanner(new File("/home/snyde479/Desktop/changes.txt"));
			while(boards.hasNextLine()){
				String s = boards.nextLine();
				if(s.startsWith(year+"")){
					String[] line = s.split(" ");
					boardsByYear.put(Integer.valueOf(line[1]), Integer.valueOf(line[2]));
				}
			}
			boards.close();
			for(int i = 0;i<players.length;i++){
				Integer playerNum = Integer.valueOf(players[i].getName().replaceAll(".txt",""));
				if(!playerMap.containsKey(playerNum)||playerMap.get(playerNum)==null){
					playerMap.put(playerNum, new ArrayList<String>());
				}
				Scanner player = new Scanner(players[i]);
				while(player.hasNextInt()){
					playerMap.get(playerNum).add(boardsByYear.get(Integer.valueOf(player.nextInt()))+" "+ player.nextInt()+" "+player.nextInt()+" "+player.next());
				}
				player.close();
			}
		}
		Set<Integer> set = playerMap.keySet();
		for(Integer i:set){
			FileWriter writer = new FileWriter(new File(outputFolder+i+".txt"));
			for(int j = 0;j<playerMap.get(i).size();j++){
				writer.write(playerMap.get(i).get(j)+"\r\n");
			}
			writer.flush();
			writer.close();
		}
	}

}
