//package sudoku;
//
//import java.util.Stack;
//
//public final class ActionHistory {
//    private static final ActionHistory INSTANCE = new ActionHistory();
//    private final Stack undoStack;  //Record of actions
//    private final Stack redoStack;
//
//    private ActionHistory() {   //Constructor
//        undoStack = new Stack();
//        redoStack = new Stack();
//    }
//
//    public static ActionHistory getInstance() { //return instance
//        return INSTANCE;
//    }
//
//    public boolean canUndo() {
//        return undoStack.size() > 0;    //Check if there is action history
//    }
//    public boolean canRedo() {
//        return redoStack.size() > 0;
//    }
//
//    public void clearUndoStack() {  //Clear stack record
//        undoStack.clear();
//    }
//    public void clearRedoStack() {
//        redoStack.clear();
//    }
//
//    public AbstractStep popUndoStack() {
//        return (AbstractStep) undoStack.pop();
//    }
//
//    public void pushUndoStack(final AbstractStep step) {    //Push action into stack
//        undoStack.push(step);
//    }
//    public void pushRedoStack(final AbstractStep step) {
//        redoStack.push(step);
//    }
//
//    public AbstractStep popRedoStack() {
//        return (AbstractStep) redoStack.pop();
//    }
//
//}
//
//public class ChangeManager {
//    private Node currentIndex = null;   //current index node
//    private Node parentNode = new Node();   //first node
//
//    public ChangeManager() {     //Constructor
//        currentIndex = parentNode;
//    }
//
//    public ChangeManager(ChangeManager manager){    //create duplicate
//        this();
//        currentIndex = manager.currentIndex;
//    }
//
//    public void addChangeFunction(ChangeFunction changeFunction){   //Add change function
//        Node node = new Node(changeFunction);
//        currentIndex.right = node;
//        node.left = currentIndex;
//        currentIndex = node;
//    }
//
//    public void clear(){        //To clear all redo if action is performed after undo
//        currentIndex = parentNode;
//    }
//
//    public boolean canUndo(){
//        return currentIndex != parentNode;
//    }
//    public boolean canRedo(){
//        return currentIndex.right != null;
//    }
//
//    public void undo(){     //Undo at current index
//        if ( !canUndo() ){
//            throw new IllegalStateException("Cannot undo. Index is out of range.");
//        }
//
//        currentIndex.changeFunction.undo(); //undo
//        prevIndex(); //update index
//    }
//
//    public void redo(){
//        if ( !canRedo() ){
//            throw new IllegalStateException("Cannot redo. Index is out of range.");
//        }
//
//        nextIndex(); //update index
//        currentIndex.changeFunction.redo(); //redo
//    }
//
//    private void prevIndex(){
//        if ( currentIndex.left == null ){
//            throw new IllegalStateException("Internal index set to null.");
//        }
//        currentIndex = currentIndex.left;   //move left
//    }
//
//    private void nextIndex(){
//        if ( currentIndex.right == null ){
//            throw new IllegalStateException("Internal index set to null.");
//        }
//        currentIndex = currentIndex.right;  //move right
//    }
//
//    private class Node {
//        private Node left = null;
//        private Node right = null;
//
//        private final ChangeFunction changeFunction;
//
//        public Node(ChangeFunction cf){
//            changeFunction = cf;
//        }
//        public Node(){
//            changeFunction = null;
//        }
//    }
//
//}
