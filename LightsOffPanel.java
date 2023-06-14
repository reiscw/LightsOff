import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class LightsOffPanel extends JPanel {

	private JToggleButton[][] buttons;
	private JButton newGame;
	private JButton quit;
	private JButton resetGame;
	
	private final int BUTTON_SIZE = 100;
	private int[][] state;

	public LightsOffPanel() {
		//adjust size and set layout
        setPreferredSize (new Dimension (6*BUTTON_SIZE, 6*BUTTON_SIZE+25));
        setLayout (null);
        
        // set up toggle buttons
        buttons = new JToggleButton[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				final int r = i; // necessary for lambda expression
				final int c = j; // necessary for lambda expression
				buttons[i][j] = new JToggleButton();
				buttons[i][j].setBounds(50 + BUTTON_SIZE * j, 50 + BUTTON_SIZE * i, BUTTON_SIZE, BUTTON_SIZE);
				buttons[i][j].setIcon(new ImageIcon(squareIcon(Color.LIGHT_GRAY)));
				buttons[i][j].setSelectedIcon(new ImageIcon(squareIcon(Color.BLUE)));
				add(buttons[i][j]);
				buttons[i][j].addActionListener(e -> {
					try {
						click(r, c);
					} catch (Exception exc) {
						exc.printStackTrace();
					}
				});
			}
		}
		
		// set up other buttons
		newGame = new JButton("New Game");
		newGame.setBounds(50, 575, 100, 20);
		add(newGame);
		
		resetGame = new JButton("Reset Game");
		resetGame.setBounds(250, 575, 100, 20);
		add(resetGame);
		
		quit = new JButton("Quit");
		quit.setBounds(450, 575, 100, 20);
		add(quit);
		
		newGame();
		
		newGame.addActionListener(e -> {
			try {
				newGame();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
		resetGame.addActionListener(e -> {
			try {
				resetGame();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
		quit.addActionListener(e -> {
			try {
				System.exit(0);
			} catch (Exception exc) {
				exc.printStackTrace();
			} 
		});
	}
	
	public void resetGame() {
		if (state == null) {
			return;
		} else {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (state[i][j] == 0) {
						buttons[i][j].setSelected(false);
					} else {
						buttons[i][j].setSelected(true);
					}
				}
			}

		}
	}
	
	public void newGame() {
		state = new LightsOffPuzzle().getPuzzle();
		// randomize buttons
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (state[i][j] == 1) {
					buttons[i][j].setSelected(true);
				} else {
					buttons[i][j].setSelected(false);
				}
			}
		}
	}
	
	public void click(int r, int c) {
		toggle(r, c);
		check();
	}
	
	public void check() {
		boolean success = true;
		for (int i = 0; i < 5 && success; i++) {
			for (int j = 0; j < 5 && success; j++) {
				if (buttons[i][j].isSelected()) {
					success = false;
				}
			}
		}
		if (success) {
			String successMessage = "Would you like to play again?";
			int result = JOptionPane.showConfirmDialog(null,  successMessage, "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				state = null;
				newGame();
			} else {
				System.exit(0);
			}
		}
	}
	
	public void toggle(int r, int c) {
		// left
		if (c > 0) 
			buttons[r][c-1].setSelected(!buttons[r][c-1].isSelected());
		// right 
		if (c < 4) 
			buttons[r][c+1].setSelected(!buttons[r][c+1].isSelected());
		// top
		if (r > 0)
			buttons[r-1][c].setSelected(!buttons[r-1][c].isSelected());
		// bottom
		if (r < 4) 
			buttons[r+1][c].setSelected(!buttons[r+1][c].isSelected());
	}
	
	
	public BufferedImage squareIcon(Color c) {	
		// set up result value and get graphics reference
		BufferedImage tile = new BufferedImage(BUTTON_SIZE, BUTTON_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = (Graphics2D)tile.getGraphics();
		// make the image a blue square and return
		graphic.setColor(c);
        graphic.fillRect(2, 2, BUTTON_SIZE-4, BUTTON_SIZE-4);            
		return tile;
	}
	
	public static void main(String[] args) {		
		JFrame frame = new JFrame("Lights Off! Version 1.2 by Christopher Reis");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new LightsOffPanel());
        frame.pack();
        frame.setVisible(true);
	}
}

