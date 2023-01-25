package sudoku;

import java.util.Stack;

public class ActionHistory {
    public static class Action {
        private final int row;
        private final int col;
        private final String value;

        public Action(int row,int col,String value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        public int getRow() {
            return row;
        }
        public int getColumn() {
            return col;
        }
        public String getValue() { return value; }
    }

    private static Stack<Action> undoStack;

    public ActionHistory() {   //Constructor
        undoStack = new Stack<>();
    }

    public static boolean canUndo() {
        return undoStack.size() > 0;    //Check if there is action history
    }

    public void clearUndoStack() {  //Clear stack action record
        undoStack.clear();
    }

    public static void pushUndoStack(Action action) {    //Push action into stack
        undoStack.push(action);
    }

    public static Action popUndoStack() {  //Pop last action from stack
        return undoStack.pop();
    }



}
