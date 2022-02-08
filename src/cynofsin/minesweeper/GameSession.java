package cynofsin.minesweeper;

public class GameSession {
	private int boardWidth, boardHeight;
	boolean[][] revealed;

	public GameSession(Minefield minefield) {
		boardHeight = minefield.getHeight();
		boardWidth = minefield.getWidth();
		revealed = new boolean[boardWidth][boardHeight]; // Sticking to my coordinate convention
	}

	public boolean isRevealed(int x, int y) {
		return revealed[x][y];
	}

	public void reveal(int x, int y) {
		revealed[x][y] = true;
	}

	
}
