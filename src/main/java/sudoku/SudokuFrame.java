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
//		JMenu file = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new NewGameListener());
		newGame.setPreferredSize(new Dimension(100,50));
		newGame.setFont(new Font("Arial", Font.PLAIN, 24));

		JMenuItem undoItem = new JMenuItem("Undo");
		undoItem.addActionListener(new UndoListener());
		undoItem.setFont(new Font("Arial", Font.PLAIN, 20));

//		file.add(newGame);
//		menuBar.add(file);
		menuBar.add(newGame);
		menuBar.add(undoItem);
		this.setJMenuBar(menuBar);
		
		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800,500));

		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100,340));
		
		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(100,500));

		timerPanel = new SudokuTimer();

		rightPanel.add(timerPanel);
		rightPanel.add(buttonSelectionPanel);

		sPanel = new SudokuPanel(this, timerPanel);
		
		windowPanel.add(sPanel);
		windowPanel.add(rightPanel);
		this.add(windowPanel);

		newGameDialog();
	}

	public void newGameDialog() {
		dialog = new SudokuNewGameDialog(this);
		rebuildInterface(dialog.getPuzzleType(), dialog.getDifficulty());
		timerPanel.resetTimer();
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
			newGameDialog();
		}
	}

	private class UndoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sPanel.undoAction();
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
