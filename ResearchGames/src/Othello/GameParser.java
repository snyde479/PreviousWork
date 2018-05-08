package Othello;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameParser {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileWriter writer = new FileWriter(new File("C:\\Users\\Tim Snyder\\Downloads\\Games.txt"));
		for(int year = 1977;year<=2008;year++){
			InputStream scan = new FileInputStream(new File("C:\\Users\\Tim Snyder\\Downloads\\WTH_7708\\WTH_"+year+".wtb"));
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

			String[] games = new String[chars.size()/68];
			char[] cs = {'0','a','b','c','d','e','f','g','h','0'};
			char[] ns = {'0','1','2','3','4','5','6','7','8','0'};
			for(int i = 0;i<chars.size()/68;i++){
				String s = "";
				int playerW = chars.get(i*68+4)*8+chars.get(i*68+5);
				int playerB = chars.get(i*68+2)*8+chars.get(i*68+3);
				for(int j = 0;j<68;j++){
					if(chars.get(i*68+j)<0){
						s+="00";
					}else{
						s+=cs[chars.get(i*68+j)%10];
						s+=ns[(chars.get(i*68+j)/10)%10];
					}
				}
				games[i] = s.substring(16);
				writer.write(s.substring(16)+" "+playerB+" "+playerW+"\r\n");
			}
		}
		writer.flush();
		writer.close();
	}

}
