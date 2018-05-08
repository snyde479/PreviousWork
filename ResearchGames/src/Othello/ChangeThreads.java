package Othello;

import java.util.Scanner;

public class ChangeThreads extends Thread{
	
	public void run(){
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			DatabaseHeuristics.threadNum = scan.nextInt();
		}
		scan.close();
	}

}
