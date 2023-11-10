import java.util.*;

public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	boolean isRevealed = false;

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);
		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
		
	}

	public void clicked()

	{
		if (thisSquareHasBomb) 
		{
			setImage("Resources\\images\\bomb.png");
		}
		else 
		{
			int BombCount = SurroundingBombs();
			if (BombCount > 0) {
                setImage("Resources\\images\\" + BombCount + ".png");
			}
			else
			{
				setImage("Resources\\images\\blank.png");
				CheckSurroundingSquares();
			}
			
	}}


private int SurroundingBombs() {
	int BombCount = 0;
	GameBoard gameBoard = this.board;
	int x = xLocation;
	int y = yLocation;

	//The possible directions the gamesquare will check against

	int[] xplane = {-1, -1, -1, 0, 0, 1, 1, 1};
	int[] yplane = {-1, 0, 1, -1, 1, -1, 0, 1};

	for (int i = 0; i < 8 ;i++) {
		int CheckX = x + xplane[i];
		int CheckY = y + yplane[i];
		//Check if the coordinates are out of bounds
		 {	GameSquare adjacentSquare = gameBoard.getSquareAt(CheckX, CheckY);
			
			//Will not increment if getSquareAt returns null
			// Casting object to Bombsquare class to call thisSquareHasBomb Method
			if (adjacentSquare != null && ((BombSquare) adjacentSquare).thisSquareHasBomb == true) {
				BombCount++;	
			}
		}
	}
	return BombCount;



}

private void CheckSurroundingSquares() {
	if (!isRevealed  && !thisSquareHasBomb) {
		setImage("Resources\\images\\blank.png");
        isRevealed = true;

		int[][] surroundingsquares = {
			{-1, -1}, {-1, 0}, {-1, 1},{0, -1}, {0, 1},{1, -1},  {1, 0},  {1, 1}};
		
		for (int i = 0; i < 8; i++) {
			int checkX = xLocation + surroundingsquares[i][0];
			int checkY = yLocation + surroundingsquares[i][1];

        GameSquare adjacentSquare = board.getSquareAt(checkX, checkY);
		if (adjacentSquare != null && !((BombSquare) adjacentSquare).thisSquareHasBomb) {
			((BombSquare) adjacentSquare).xLocation = xLocation;
            ((BombSquare) adjacentSquare).yLocation = yLocation;
			((BombSquare) adjacentSquare).CheckSurroundingSquares(); // Recursively click adjacent squares		
		}
}

}}}
