import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class AddWinRate {
	private static ArrayList<ArrayList<Board>> boards = new ArrayList<ArrayList<Board>>();
	private static HashMap<Integer, ArrayList<Boolean>> map = new HashMap<Integer, ArrayList<Boolean>>();

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int game = 0;
		for(int year = 1977;year<=2015;year++){
			boards = new ArrayList<ArrayList<Board>>();
			for(int i = 0;i<=60;i++){
				boards.add(new ArrayList<Board>());
			}
			map = new HashMap<Integer, ArrayList<Boolean>>();
			InputStream scan = new FileInputStream(new File("/home/snyde479/Desktop/WTH_7708/WTH_"+year+".wtb"));
			ArrayList<Byte> chars = new ArrayList<Byte>();
			int byt = scan.read();
			while(byt!=-1){
				chars.add((byte)byt);
				byt = scan.read();
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

			for(int i = 0;i<chars.size()/68;i++){
				int playerW = chars.get(i*68+4)*8+chars.get(i*68+5);
				int playerB = chars.get(i*68+2)*8+chars.get(i*68+3);
				int bScore = chars.get(i*64+7);
				if(bScore>32){
					if(!map.containsKey(playerW)){
						map.put(playerW, new ArrayList<Boolean>());
					}
					if(!map.containsKey(playerB)){
						map.put(playerB, new ArrayList<Boolean>());
					}
					map.get(playerW).add(false);
					map.get(playerB).add(true);
				}else if(bScore==32){
					if(!map.containsKey(playerW)){
						map.put(playerW, new ArrayList<Boolean>());
					}
					if(!map.containsKey(playerB)){
						map.put(playerB, new ArrayList<Boolean>());
					}
					map.get(playerW).add(false);
					map.get(playerB).add(false);
				}else{
					if(!map.containsKey(playerW)){
						map.put(playerW, new ArrayList<Boolean>());
					}
					if(!map.containsKey(playerB)){
						map.put(playerB, new ArrayList<Boolean>());
					}
					map.get(playerW).add(true);
					map.get(playerB).add(false);
				}
				game++;
				if(game%1000==0){
					System.out.println(game);
				}
			}
		}
		FileWriter writer = new FileWriter(new File("/home/snyde479/Desktop/wins.txt"));
		for(Entry<Integer,ArrayList<Boolean>> e: map.entrySet()){
			for(Boolean b:e.getValue()){
				writer.write(e.getKey()+" "+b+"\r\n");
			}
		}
		writer.flush();
		writer.close();
	}
}
