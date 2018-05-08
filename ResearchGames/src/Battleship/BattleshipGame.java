/*package Battleship;

//empowerment is increase the likelihood to hit opponent's ships/decrease unknowns on statespace

public class BattleshipGame {

	private Player p1;
	private Player p2;
	private char[][] p1Guesses = new char[10][10];
	private char[][] p2Guesses = new char[10][10];
	private Ship[] p1Ships;
	private Ship[] p2Ships;
	
	public BattleshipGame(Player p1, Player p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Player playGame(){
		p1Ships = p1.getShips();
		p2Ships = p2.getShips();
		while(!sunk(p1Ships)&&!sunk(p2Ships)){
			int[] guess = p1.getGuess(p1Guesses);
			if(hit(p2Ships, guess)){
				p1.sendHit();
				p1.sendSink(sinks(p2Ships, guess));
				p1Guesses[guess[0]][guess[1]] = 'h';
			}else{
				p1.sendMiss();
				p1Guesses[guess[0]][guess[1]] = 'm';
			}
			if(!sunk(p2Ships)){
				guess = p2.getGuess(p2Guesses);
				if(hit(p1Ships, guess)){
					p2.sendHit();
				}else{
					p2.sendMiss();
				}
			}
		}
		
		Player winner;
		if(sunk(p1Ships)){
			winner = p2;
		}else{
			winner = p1;
		}
		return winner;
	}

	public int sinks(Ship[] ships, int[] guess) {
		for(int i = 0;i<ships.length;i++){
			if(ships[i].hit(guess)){
				return ships[i].damage(guess);
			}
		}
		return 0;
	}

	public boolean hit(Ship[] ships, int[] guess) {
		for(int i = 0;i<ships.length;i++){
			if(ships[i].hit(guess)){
				return true;
			}
		}
		return false;
	}

	public boolean sunk(Ship[] ships) {
		for(int i =0;i<ships.length;i++){
			if(!ships[i].sunk()){
				return false;
			}
		}
		return true;
	}
}
*/