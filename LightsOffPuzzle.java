import java.io.*;
import java.util.*;

public class LightsOffPuzzle {
	
	private int[][] puzzle;
	
	public LightsOffPuzzle() {
		int[][][] possiblePuzzles = possiblePuzzles();
		int random = (int)(Math.random()*32);
		puzzle = possiblePuzzles[random];
		for (int i = 0; i < 100; i++) {
			int randomRow = (int)(Math.random()*5);
			int randomCol = (int)(Math.random()*5);
			toggle(randomRow, randomCol);
		}
	}
	
	public int[][][] possiblePuzzles() {
		try {
			int[][][] result = new int[32][5][5];
			for (int i = 0; i < 32; i++) {
				Scanner input = new Scanner(new File("basepuzzles.txt"));
				while (input.hasNextLine()) {
					String line = input.nextLine();
					int index = 0;
					for (int r = 0; r < 5; r++) {
						for (int c = 0; c < 5; c++) {
							result[i][r][c] = Integer.parseInt("" + line.charAt(index));
							index++;
						}
					}
				}
				input.close();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	
	public void toggle(int r, int c) {
		puzzle[r][c] = (puzzle[r][c] + 1) % 2;
		// left
		if (c > 0) 
			puzzle[r][c-1] = (puzzle[r][c-1] + 1) % 2;
		// right 
		if (c < 4) 
			puzzle[r][c+1] = (puzzle[r][c+1] + 1) % 2;
		// top
		if (r > 0)
			puzzle[r-1][c] = (puzzle[r-1][c] + 1) % 2;
		// bottom
		if (r < 4) 
			puzzle[r+1][c] = (puzzle[r+1][c] + 1) % 2;
	}
	
	public int[][] getPuzzle() {return puzzle;}

	// https://en.wikipedia.org/wiki/Lights_Out_(game)
	// The above reference gives a mathematical rule for determining if a puzzle 
	// is solvable. Essentially, the puzzle expressed as a 1D vector must be 
	// orthogonal to two constant vectors shown below.  This provides 32 configurations
	// which can be used to generate other puzzles through toggling.

	public static void generatePossibleCandidates() throws IOException {
		FileWriter writer = new FileWriter("basepuzzles.txt");
		for (int i = 0; i < (int)(Math.pow(2,25)); i++) {
			String binary = Integer.toBinaryString(i);
			int length = binary.length();
			for (int j = 0; j < 25 - length; j++) {
				binary = "0" + binary;
			}
			int[] a = new int[25];
			for (int k = 0; k < 25; k++) {
				a[k] = binary.charAt(k) - 48;
			}
			int[] n1 = {0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0};
			int[] n2 = {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1,	1, 0, 1, 0, 1};
			if (orthogonal(a, n1) && orthogonal(a, n2)) {
				writer.write(binary + "\n");
			}
		}
		writer.close();
	}
	
	public static boolean orthogonal(int[] a, int[] b) {
		for (int i = 0; i < 25; i++) {
			if (a[i] * b[i] != 0) 
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		generatePossibleCandidates();
	}

}


