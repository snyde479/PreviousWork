import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class KLHistogram {

	private static final int NUM_BOARD = (new File("/home/snyde479/Desktop/Database/Board/")).listFiles().length;;
	private static Player[] players;

	public static void main(String[] args) throws IOException {
		players = createPlayers();
		double[][] kl = new double[players.length][players.length];
		System.out.println("Finished making the players");

		FileWriter writer = new FileWriter(new File("/home/snyde479/Desktop/KL.txt"));
		for(int i = 0;i<kl.length;i++){
			for(int j = i+1;j<kl.length;j++){
				writer.write(kl(players[i],players[j])+",");
			}
			writer.write("\r\n");
			writer.flush();
			System.out.println("KL for player "+i);
		}
		writer.close();
	}

	private static double kl(Player p1, Player p2) {
		double val = 0.0;
		for(int board = 1;board<NUM_BOARD;board++){
			/*double maxCount = 0.0;
			for(int i = 0;i<players.length;i++){
				maxCount+=players[i].getSeenBoard(board);
			}*/
			for(int a = 0;a<8;a++){
				for(int b = 0;b<8;b++){
					String move = a+", "+b;
					if(p1.hasMove(board, move)){
						if(p2.hasMove(board, move)){
							double phat1 = p1.phat(board, move);
							double phat2 = p2.phat(board, move);
							val+= 0.5*phat1*Math.log(phat1/phat2);
							val+= 0.5*phat2*Math.log(phat2/phat1);
						}/*else{
							val+= 0.5*p1.phat(board, move)*Math.log(p1.phat(board, move)/(1.0/maxCount));
							val+= 0.5*(1.0/maxCount)*Math.log((1.0/maxCount)/p1.phat(board, move));
						}*/
					}/*else if(p2.hasMove(board, move)){
						val+= 0.5*(1.0/maxCount)*Math.log((1.0/maxCount)/p2.phat(board, move));
						val+= 0.5*p2.phat(board, move)*Math.log(p2.phat(board, move)/(1.0/maxCount));
					}*/
				}
			}
		}
		return val;
	}

	private static Player[] createPlayers() throws FileNotFoundException {
		File folder = new File("/home/snyde479/Desktop/Database/Player/");
		Player[] ps = new Player[folder.listFiles().length];
		for(int i = 0;i<ps.length;i++){
			ps[i] = new Player(folder.listFiles()[i]);
		}
		return ps;
	}

}
