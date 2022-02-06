package cynofsin.minesweeper;
import java.util.Random;

/**
 * 2-dimensional field of mines
 * 
 * Coordinate convention (I promise that):
 * 		[x][y] 			- 2d array indexes will always use this format
 * 		i = x; j = y	- nested for loops will always use this format
 * with respect to the cells displayed to the user. I do not promise to
 * iterate in any particular order.
 */
public class Minefield {
	Random rng;
    int height, width;  // Height and width of the field in cells
    boolean[][] mines;
    Integer[][] hints;

    public Minefield(int x, int y, Random rng)
    {
		this.rng = rng == null ? new Random() : rng;
		width = x;
		height = y;

		mines = generateMines(width, height, rng);
		hints = generateHints(mines);
    }

	public static Integer[][] generateHints(boolean[][] mines) {
		Integer[][] hints = new Integer[mines.length][mines[0].length];

		for(int i = 0; i < hints.length; i++) {
			for(int j = 0; j < hints[0].length; j++) {
				if(!mines[i][j]) {
					int adjacentMines = 0;

					for(int dx = -1; dx <= 1; dx++) {
						for(int dy = -1; dy <= 1; dy++) {
							if(!(dx == 0 && dy == 0)) {
								int i2 = i + dx;
								int j2 = j + dy;
		
								if(!(i2 < 0 || i2 > hints.length - 1
								|| j2 < 0 || j2 > hints[0].length - 1)) {
									if(mines[i2][j2]) {
										adjacentMines++;
									}
								}
							}
						}
					}
	
					hints[i][j] = adjacentMines;
				}
				else {
					hints[i][j] = null;
				}
			}
		}
		return hints;
	}

	public static boolean[][] generateMines(int x, int y, Random rng)
	{
		boolean[][] mines = new boolean[x][y];
		for(int i = 0; i < mines.length; i++) {
			for(int j = 0; j < mines[0].length; j++) {
				mines[i][j] = rng.nextFloat() < 0.5;
			}
		}
		return mines;
	}
}