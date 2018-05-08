package Battleship;

public class Ship {

	public boolean[] parts;
	public int[][] locations;

	public Ship(int[] start, int[] end){
		int length = end[0]-start[0]+1;
		boolean right = true;
		if(end[1]-start[1]+1>length){
			length = end[1]-start[1]+1;
			right= false;
		}
		locations = new int[length][2];
		parts = new boolean[length];
		if(right){
			for(int i =0;i<length;i++){
				locations[i][0] = start[0]+i;
				locations[i][1] = start[1];
			}
		}else{
			for(int i =0;i<length;i++){
				locations[i][1] = start[1]+i;
				locations[i][0] = start[0];
			}
		}
	}

	public boolean sunk() {
		for(int i = 0;i<parts.length;i++){
			if(!parts[i]){
				return false;
			}
		}
		return true;
	}

	public int damage(int[] guess){
		for(int i = 0;i<locations.length;i++){
			if(locations[i][0]==guess[0]&&locations[i][1]==guess[1]){
				parts[i] = true;
			}
		}
		if(sunk()){
			return locations.length;
		}
		return 0;
	}
	
	public int length(){
		return locations.length;
	}

	public boolean hit(int[] guess) {
		for(int i = 0;i<locations.length;i++){
			if(locations[i][0]==guess[0]&&locations[i][1]==guess[1]){
				return true;
			}
		}
		return false;
	}

}
