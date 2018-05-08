

public class BoardMove {

	public int board;
	public int layer;
	public String move;
	public boolean won;
	
	public BoardMove(int layer, int board, String move, boolean won){
		this.layer = layer;
		this.board=board;
		this.move=move;
		this.won=won;
	}
}
