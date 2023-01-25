package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {

	private SudokuPuzzle puzzle;
	private SudokuTimer timerPanel;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;

	private SudokuFrame parentFrame;
	
	public SudokuPanel(SudokuFrame parentFrame, SudokuTimer timerPanel) {
		this.setPreferredSize(new Dimension(580,520));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.addKeyListener(new SudokuPanelKeyListener());
		this.puzzle = new SudokuGenerator().generateRandomSudoku(SudokuPuzzleType.NINEBYNINE, 0.4f);
		this.timerPanel = timerPanel;
		this.parentFrame = parentFrame;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}

	public void newSudokuPuzzle(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		g2d.setColor(new Color(240,248,255));   //panel color
		
		int slotWidth = this.getWidth()/puzzle.getNumColumns();
		int slotHeight = this.getHeight()/puzzle.getNumRows();
		
		usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
		usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();
		
		g2d.fillRect(0, 0,usedWidth,usedHeight);
		
		g2d.setColor(new Color(0.0f,0.0f,0.0f));		//panel outline color
		for(int x = 0;x <= usedWidth;x+=slotWidth) {
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
		//this will draw the right most line
		//g2d.drawLine(usedWidth - 1, 0, usedWidth - 1,usedHeight);
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		//this will draw the bottom line
		//g2d.drawLine(0, usedHeight - 1, usedWidth, usedHeight - 1);
		
		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		for(int row=0;row < puzzle.getNumRows();row++) {
			for(int col=0;col < puzzle.getNumColumns();col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					if (puzzle.isSlotMutable(row, col)) {
						g2d.setColor(new Color(0.239f,0.353f,0.502f));		//input font color
					}
					else {
						g2d.setColor(new Color(0.0f,0.0f,0.0f));		//original font on panel color
					}
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));

					//highlight the selected number
					if(puzzle.getValue(row,col) == puzzle.getValue(currentlySelectedRow,currentlySelectedCol))
					{
						g2d.setColor(new Color(0.0f,0.0f,1.0f,0.1f));
						g2d.fillRect(col * slotWidth,row * slotHeight ,slotWidth,slotHeight);
					}

				}
			}
		}
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(61,90,158,80));		//selected grid color
			g2d.fillRect(currentlySelectedCol * slotWidth,currentlySelectedRow * slotHeight,slotWidth,slotHeight);

			//highlight the selected row
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.1f));
			g2d.fillRect(0, currentlySelectedRow * slotHeight, usedWidth, slotHeight);

			//highlight the selected column
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.1f));
			g2d.fillRect(currentlySelectedCol * slotWidth,0, slotWidth, usedHeight);


		}

	}
	
	public void messageFromNumActionListener(String buttonValue) {
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true);
			repaint();

			if(puzzle.boardFull())
			{
				String[] options = {"OK", "New Game"};
				timerPanel.stopTimer();
				int selection = JOptionPane.showOptionDialog(
						null,
						"Congratulations, you have completed the Sudoku game in " + timerPanel.getTimer()+ "!",
						"Game Complete",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[1]);

				if(selection == 1)
				{
					parentFrame.newGameDialog();
				}
			}
		}
	}

	public void clearSelectedSlot() {
		if (puzzle.isSlotMutable(currentlySelectedRow, currentlySelectedCol)) {
			puzzle.makeSlotEmpty(currentlySelectedRow, currentlySelectedCol);
			repaint();
		}
	}

	public void undoAction() {
		if (ActionHistory.canUndo()) {
			clearSelectedSlot();
			ActionHistory.Action lastAction;
			lastAction = ActionHistory.popUndoStack();
			currentlySelectedRow = lastAction.getRow();
			currentlySelectedCol = lastAction.getColumn();
			repaint();
		}
	}

	public class UndoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			undoAction();
		}
	}
	
	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromNumActionListener(((JButton) e.getSource()).getText());	
		}
	}
	
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = usedWidth/puzzle.getNumColumns();
				int slotHeight = usedHeight/puzzle.getNumRows();
				currentlySelectedRow = e.getY() / slotHeight;
				currentlySelectedCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}

	private class SudokuPanelKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				messageFromNumActionListener("1");
			}
			else if(e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				messageFromNumActionListener("2");
			}
			else if(e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				messageFromNumActionListener("3");
			}
			else if(e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
				messageFromNumActionListener("4");
			}
			else if(e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
				messageFromNumActionListener("5");
			}
			else if(e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
				messageFromNumActionListener("6");
			}
			else if(e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
				messageFromNumActionListener("7");
			}
			else if(e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				messageFromNumActionListener("8");
			}
			else if(e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
				messageFromNumActionListener("9");
			}
			else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
				clearSelectedSlot();
			}
			else if(e.getKeyCode() == KeyEvent.VK_A) {
				messageFromNumActionListener("A");
			}
			else if(e.getKeyCode() == KeyEvent.VK_B) {
				messageFromNumActionListener("B");
			}
			else if(e.getKeyCode() == KeyEvent.VK_C) {
				messageFromNumActionListener("C");
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (currentlySelectedCol > 0) {
					currentlySelectedCol--;
					repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (currentlySelectedCol < puzzle.getNumColumns() - 1) {
					currentlySelectedCol++;
					repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (currentlySelectedRow > 0) {
					currentlySelectedRow--;
					repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (currentlySelectedRow < puzzle.getNumRows() - 1) {
					currentlySelectedRow++;
					repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_Z) {
				if (e.isControlDown()){
					undoAction();
				}
			}
		}
	}
}
