import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Wins {

	public static void main(String[] args) throws IOException{
		Scanner scan = new Scanner(new File("/home/snyde479/Desktop/wins.txt"));
		while(scan.hasNext()){
			String[] s = scan.nextLine().split(" ");
			File player = new File("/home/snyde479/Desktop/Database/Player/"+s[0]+".txt");
			Scanner scan2 = new Scanner(player);
			FileWriter writer = new FileWriter(player);
			int last = -1;
			while(scan2.hasNext()){
				String[] str = scan2.nextLine().split(" ");
				if(str.length==3){
					writer.write(str[0]+" "+str[1]+" "+str[2]+"\r\n");
				}else{
					if(last==-1){
						if(s[2].equals("true")){
							writer.write(str[0]+" "+str[1]+"1");
							last = Integer.valueOf(str[0]);
						}else{
							writer.write(str[0]+" "+str[1]+"0");
							last = Integer.valueOf(str[0]);
						}
					}else if(last>Integer.valueOf(str[0])){
						last = Integer.MAX_VALUE;
					}
				}
				if(last==Integer.MAX_VALUE){
					writer.write(str[0]+" "+str[1]+"\r\n");
				}
			}
			writer.flush();
			writer.close();
		}
	}
	
}
