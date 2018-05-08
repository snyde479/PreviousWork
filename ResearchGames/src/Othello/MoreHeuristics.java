package Othello;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MoreHeuristics {
	public static int threadNum = 5;
	public static ArrayList<HumanMoves> moves = new ArrayList<HumanMoves>();

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("C:\\Users\\Tim Snyder\\Downloads\\Games.txt"));
		int game = 1;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		(new ChangeThreads()).start();

		/*while(game<=1){
			scan.nextLine();
			game++;
		}*/
		ParserThread2 firstThread = new ParserThread2(scan.nextLine(), game);
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
				ParserThread2 newThread = new ParserThread2(scan.nextLine(), game);
				threads.add(newThread);
				newThread.start();
				System.out.println(game);
				game++;
			}
		}
		scan.close();
	}

}
