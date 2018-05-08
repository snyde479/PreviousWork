package Battleship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static ArrayList<ArrayList<Integer>> turns = new ArrayList<ArrayList<Integer>>();
	public static int done = 0;
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<Board> boards = AllBoardsGenerator.getBoards();
		System.out.println(boards.size()+" boards generated");
		
		Random rand = new Random();
		while(done<250||done<boards.size()/100){
			Board board = boards.get(rand.nextInt(boards.size())).clone();
			ArrayList<Integer> boardTurns = new ArrayList<Integer>();
			Player random = new RandomPlayer();
			Player ordered = new OrderedPlayer();
			Player emp = new EmpowermentPlayer(AllBoardsGenerator.BOARD_SIZE+"Emp.txt",true);
			Player maxLikeli = new MaxLikelihoodPlayer(AllBoardsGenerator.BOARD_SIZE+"Max.txt",true);
			Player empK = new EmpowermentKnown(AllBoardsGenerator.BOARD_SIZE+"EmpK.txt");
			Player makLikeliK = new MaxLikelihoodKnown(AllBoardsGenerator.BOARD_SIZE+"MaxK.txt");

			SinglePlayer game = new SinglePlayer(random, board.clone());
			boardTurns.add(game.playGame());
			
			game = new SinglePlayer(ordered, board.clone());
			boardTurns.add(game.playGame());
			
			game = new SinglePlayer(empK, board.clone());
			boardTurns.add(game.playGame());
			
			game = new SinglePlayer(maxLikeli, board.clone());
			boardTurns.add(game.playGame());

			SinglePlayerReveal game2 = new SinglePlayerReveal(emp, board.clone());
			boardTurns.add(game2.playGame());
			
			game2 = new SinglePlayerReveal(makLikeliK, board.clone());
			boardTurns.add(game2.playGame());
			
			
			emp = new EmpowermentPlayer(AllBoardsGenerator.BOARD_SIZE+"EmpR.txt",false);
			maxLikeli = new MaxLikelihoodPlayer(AllBoardsGenerator.BOARD_SIZE+"MaxR.txt",false);
			
			SinglePlayerKnown game3 = new SinglePlayerKnown(emp, board.clone());
			boardTurns.add(game3.playGame());
			
			game3 = new SinglePlayerKnown(maxLikeli, board.clone());
			boardTurns.add(game3.playGame());
			
			turns.add(boardTurns);
			done++;
			System.out.println(done);
		}
		
		File file = new File("C:\\Users\\Tim Snyder\\Downloads\\Turns"+AllBoardsGenerator.BOARD_SIZE+".txt");
		FileWriter writer = new FileWriter(file);
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(0)+" ");
		}
		writer.write("\r\n");
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(1)+" ");
		}
		writer.write("\r\n");
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(2)+" ");
		}
		writer.write("\r\n");
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(3)+" ");
		}
		writer.write("\r\n");
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(4)+" ");
		}
		writer.write("\r\n");
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(5)+" ");
		}
		writer.write("\r\n");
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(6)+" ");
		}
		writer.write("\r\n");
		for(int i = 0;i<turns.size();i++){
			writer.write(""+turns.get(i).get(7)+" ");
		}
		writer.flush();
		writer.close();
	}

}
