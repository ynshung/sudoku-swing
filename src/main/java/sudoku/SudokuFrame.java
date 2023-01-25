package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	private JPanel rightPanel;
	private SudokuTimer timerPanel;
	private SudokuNewGameDialog dialog;
	
	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800,600));

		Image icon = Toolkit.getDefaultToolkit().getImage("sudoku.png");
		this.setIconImage(icon);

		JMenuBar menuBar = new JMenuBar();
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new NewGameListener());
		newGame.setPreferredSize(new Dimension(100,50));
		newGame.setFont(new Font("Arial", Font.PLAIN, 24));
		newGame.setForeground(Color.decode("#000000"));
		newGame.setBackground(Color.decode("#e07a5f"));

		menuBar.add(newGame);
		this.setJMenuBar(menuBar);
		
		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800,500));
		windowPanel.setBackground(new Color(244, 241,222));

		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(150,500));
		rightPanel.setBackground(new Color(244, 241,222));

		timerPanel = new SudokuTimer();
		timerPanel.setBackground(new Color(244, 241,222));

		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(100,500));
		buttonSelectionPanel.setBackground(new Color(244, 241,222));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(244, 241,222));

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new UndoListener());

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearListener());

		JButton clearAllButton = new JButton("Clear All");
		clearAllButton.addActionListener(new ClearAllListener());

		buttonPanel.add(clearButton);
		buttonPanel.add(undoButton);

		rightPanel.add(timerPanel);
		rightPanel.add(buttonPanel);
		rightPanel.add(clearAllButton);
		rightPanel.add(buttonSelectionPanel);

		sPanel = new SudokuPanel(this, timerPanel);
		sPanel.setBackground(new Color(244, 241,222));
		
		windowPanel.add(sPanel);
		windowPanel.add(rightPanel);
		this.add(windowPanel);

		if (!newGameDialog()) {
			System.exit(0);
		}
	}

	public boolean newGameDialog() {
		dialog = new SudokuNewGameDialog(this);
		if (dialog.isCancelled())
			return false;
		rebuildInterface(dialog.getPuzzleType(), dialog.getDifficulty());
		timerPanel.resetTimer();
		return true;
	}
	

	public void rebuildInterface(SudokuPuzzleType puzzleType, float difficulty) {
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType, difficulty);
		sPanel.newSudokuPuzzle(generatedPuzzle);
		sPanel.setFontSize(puzzleType.getFontSize());
		buttonSelectionPanel.removeAll();
		for(String value : generatedPuzzle.getValidValues()) {
			JButton b = new JButton(value);
			b.setPreferredSize(new Dimension(45,45));
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b);
		}
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}
	
	private class NewGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (newGameDialog()) {
				timerPanel.resetTimer();
			}
		}
	}

	private class UndoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sPanel.undoAction();
		}
	}

	private class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sPanel.clearAction();
		}
	}

	private class ClearAllListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sPanel.clearAllAction();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}
}
