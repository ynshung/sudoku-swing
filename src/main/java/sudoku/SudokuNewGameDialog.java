package sudoku;

import javax.swing.*;

public class SudokuNewGameDialog extends JDialog {
    private SudokuPuzzleType puzzleType;

    public SudokuNewGameDialog(SudokuFrame parent) {
        super(parent, "New Game", true);

        Object[] options = {"6 By 6", "9 By 9", "12 By 12"};
        Object[] difficulties = {"Easy", "Medium", "Hard", "Custom"};

        int puzzleTypeSelection = JOptionPane.showOptionDialog(
                this,
                "Select a puzzle type",
                "New Game",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        switch (puzzleTypeSelection) {
            case 0:
                puzzleType = SudokuPuzzleType.SIXBYSIX;
                break;
            case 2:
                puzzleType = SudokuPuzzleType.TWELVEBYTWELVE;
                break;
            default:
            case 1:
                puzzleType = SudokuPuzzleType.NINEBYNINE;
                break;
        }
    }
    public SudokuPuzzleType getPuzzleType() {
    	return puzzleType;
    }
}
