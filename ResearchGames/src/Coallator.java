import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Coallator {
	private static ArrayList<Board> boards = new ArrayList<Board>();

	public static void main(String[] args) throws IOException {
		String outputFolder = "/home/snyde479/Desktop/Database/Boards/";
		FileWriter writer = new FileWriter(new File("/home/snyde479/Desktop/changes.txt"));
		File[] bs = (new File(outputFolder)).listFiles();
		for(int i = 0;bs!=null&&i<bs.length;i++){
			File board = new File(outputFolder+i+".txt");
			char[][] bo = new char[8][8];
			FileReader reader = new FileReader(board);
			for(int a = 0;a<8;a++){
				for(int b = 0;b<8;b++){
					char c = (char) reader.read();
					while(c!='b'&&c!='w'&&c!=0){
						c = (char) reader.read();
					}
					bo[a][b] = c;
				}
			}boards.add(new Board(bo));
			reader.close();
		}
		for(int year = 1977;year<=1997;year++){
			int i = 0;
			File board = new File("/home/snyde479/Desktop/Board/"+year+"/"+i+".txt");
			while(board.exists()){
				char[][] bo = new char[8][8];
				FileReader reader = new FileReader(board);
				for(int a = 0;a<8;a++){
					for(int b = 0;b<8;b++){
						char c = (char) reader.read();
						while(c!='b'&&c!='w'&&c!=0){
							c = (char) reader.read();
						}
						bo[a][b] = c;
					}
				}
				Board b = new Board(bo);
				boolean found = false;
				for(int a = 0;!found&&a<boards.size();a++){
					if(b.equals(boards.get(a))){
						found = true;
						writer.write(year+" "+i+" "+a+"\r\n");
					}
				}
				if(!found){
					writer.write(year+" "+i+" "+boards.size()+"\r\n");
					FileWriter quickWrite = new FileWriter(new File(outputFolder+boards.size()+".txt"));
					for(int a = 0;a<8;a++){
						for(int w = 0;w<7;w++){
							quickWrite.write(bo[a][w]+",");
						}
						quickWrite.write(bo[a][7]+"\r\n");
					}
					quickWrite.flush();
					quickWrite.close();
					boards.add(b);
				}
				i++;
				board = new File("/home/snyde479/Desktop/Board/"+year+"/"+i+".txt");
				reader.close();
			}
		}
		writer.flush();
		writer.close();
	}

}
