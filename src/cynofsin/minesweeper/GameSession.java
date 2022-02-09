package cynofsin.minesweeper;

public class GameSession {
	private int boardWidth, boardHeight;
	private boolean[][] revealed;
	private Minefield minefield;

	public GameSession(Minefield minefield) {
		this.minefield = minefield;
		boardHeight = this.minefield.getHeight();
		boardWidth = this.minefield.getWidth();
		revealed = new boolean[boardWidth][boardHeight]; // Sticking to my coordinate convention
	}

	public boolean isRevealed(int x, int y) {
		return revealed[x][y];
	}

	public void reveal(int x, int y) {
		revealed[x][y] = true;
	}

	public boolean isMine(int x, int y) {
		return minefield.getHints()[x][y] == null;
	}

	public Integer getHint(int x, int y) {
		return minefield.getHints()[x][y];
	}
}
