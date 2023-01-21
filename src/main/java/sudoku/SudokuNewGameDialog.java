package sudoku;

import javax.swing.*;

public class SudokuNewGameDialog extends JDialog {
    private SudokuPuzzleType puzzleType;

    private float difficulty;

    public SudokuNewGameDialog(SudokuFrame parent) {
        super(parent, "New Game", true);

        Object[] options = {"6 By 6", "9 By 9", "12 By 12"};
        Object[] difficulties = {"Easy", "Medium", "Hard"};

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

        int difficultySelection = JOptionPane.showOptionDialog(
                this,
                "Select a difficulty",
                "New Game",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficulties,
                difficulties[1]);

        switch (difficultySelection) {
            case 0:
                difficulty = 0.5555f;
                break;
            case 2:
                difficulty = 0.2222f;
                break;
            default:
            case 1:
                difficulty =  0.4f;
                break;
        }
    }
    public SudokuPuzzleType getPuzzleType() {
    	return puzzleType;
    }

    public float getDifficulty() {
        return difficulty;
    }
}
