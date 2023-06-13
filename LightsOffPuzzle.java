public class LightsOffPuzzle {
	
	private int[][] puzzle;
	
	public LightsOffPuzzle() {
		puzzle = new int[5][5];
		boolean success = false;
		while (!success) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					puzzle[i][j] = (int)(Math.random()*2);
				}
			}
			if (validPuzzle()) {
				success = true;
			}
		}
	}
	
	public int[][] getPuzzle() {return puzzle;}

	// https://en.wikipedia.org/wiki/Lights_Out_(game)
	// The above reference gives a mathematical rule for determining if a puzzle 
	// is solvable. Essentially, the puzzle expressed as a 1D vector must be 
	// orthogonal to two constant vectors shown below.

	public boolean validPuzzle() {
		int[] n1 = {0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0};
		int[] n2 = {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1,	1, 0, 1, 0, 1};
		int[] s = convertMatrixToVector();
		boolean check1 = orthogonal(n1, s);
		boolean check2 = orthogonal(n2, s);
		return check1 && check2;
	}
	
	public int[] convertMatrixToVector() {
		int[] result = new int[25];
		int i = 0;
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {
				result[i] = puzzle[r][c];
				i++;
			}
		}
		return result;
	}

	public boolean orthogonal(int[] a, int[] b) {
		for (int i = 0; i < 25; i++) {
			if (a[i] * b[i] != 0) 
				return false;
		}
		return true;
	}

}


