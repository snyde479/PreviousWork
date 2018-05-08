package Othello;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseHeuristics {
	public static int threadNum = 5;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Downloads\\Games.txt"));
		int game = 1;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		(new ChangeThreads()).start();

		/*while(game<=1){
			scan.nextLine();
			game++;
		}*/
		ParserThread firstThread = new ParserThread(scan.nextLine(), game);
		threads.add(firstThread);
		firstThread.start();
		System.out.println(game);
		game++;
		
		while(threads.size()>0){
			for(int i = 0;i<threads.size();i++){
				if(!threads.get(i).isAlive()){
					threads.remove(i);
					continue;
				}
			}
			if(game<1000&&threads.size()<threadNum){
				ParserThread newThread = new ParserThread(scan.nextLine(), game);
				threads.add(newThread);
				newThread.start();
				System.out.println(game);
				game++;
			}
		}
		scan.close();
	}
}
