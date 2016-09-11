package HyperSudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    // Declare variables
    public int NUM_ROW = 9;
    public int NUM_COLUMN = 9;
    public int NUM_BOX = 9;
    public int NUM_HYPERBOX = 4;
    private Cell[] board;
           
    // Declare sets in the form of list of lists
    private List<List<Integer>> rows = new ArrayList<>();
    private List<List<Integer>> columns = new ArrayList<>();
    private List<List<Integer>> boxes = new ArrayList<>();
    private List<List<Integer>> hyperboxes = new ArrayList<>();               
    
    // Constructor
    public Board(){    
        // Initialize sets
        for(int j = 0; j < NUM_ROW; j++){
            rows.add(new ArrayList<>());
        }
        for(int j = 0; j < NUM_COLUMN; j++){
            columns.add(new ArrayList<>());
        }
        for(int j = 0; j < NUM_BOX; j++){
            boxes.add(new ArrayList<>());
        }
        for(int j = 0; j < NUM_HYPERBOX; j++){
            hyperboxes.add(new ArrayList<>());
        }                
        
        // Create the board
        board = createBoard();                
    }
        
    // Accessors 
    public Cell[] getBoard(){
        return this.board;
    }
    
    /*
    Create a sudoku board
    */
    public Cell[] createBoard(){
        Cell[] board = new Cell[81];            
        int index = 0;
        
        // Create cells
        for(int i = 1; i <= NUM_ROW; i++){
            for(int j = 1; j <= NUM_COLUMN; j++){
                board[index] = new Cell(i, j);
                
                // Load the cell's value to sets
                rows.get(i - 1).add(board[index].getValue());               
                columns.get(j - 1).add(board[index].getValue());
                boxes.get(board[index].getSet() - 1).add(board[index].getValue());
                if(board[index].getHyperbox() != 0){
                    hyperboxes.get(board[index].getHyperbox() - 1).add(board[index].getValue());
                }                
                index++;
            }
        }                
        return board;
    }

    /*
    Print the sudoku board
    http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    */
    public void printBoard(){
        int index = 0;
        String pval;
        
        for(int j = 1; j <= NUM_ROW; j++){
            for(int i = 1; i <= NUM_COLUMN; i++){
                if(board[index].getValue() == 0){
                    pval = ".";
                }
                else{
                    pval = Integer.toString(board[index].getValue());
                }
                // Print in color blue if the cell is in a hyperbox
                // Print a space break every 3 columns
                if(!(j == 1 || j == 5 || j == 9 || i == 1 || i == 5 || i == 9)){
                    System.out.print("" + "\u001B[36m" + pval + "\u001B[0m  " + (i % 3 == 0 ? "  " : ""));
                }
                else{
                    System.out.print("" + pval + "  " + (i % 3 == 0 ? "  " : ""));
                }
                index++;
            }
            System.out.println();
            
            // Print a line break every 3 rows
            if(j % 3 == 0){
                System.out.println();
            }
        }
        System.out.println("------------------------------");
        System.out.println();
    }        
 
    /*
    Print the sets for debugging purpose
    */
    public void printSets(){
        // Print set of rows
        System.out.println("Rows:");
        for(int j = 0; j < NUM_ROW; j++){
            for(int i = 0; i < NUM_ROW; i++){
                System.out.print(rows.get(j).get(i) + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        // Print set of columns
        System.out.println("Columns:");
        for(int j = 0; j < NUM_ROW; j++){
            for(int i = 0; i < NUM_ROW; i++){
                System.out.print(columns.get(j).get(i) + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        // Print set of boxes
        System.out.println("Boxes:");
        for(int j = 0; j < NUM_BOX; j++){
            for(int i = 0; i < 9; i++){
                System.out.print(boxes.get(j).get(i) + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        // Print set of hyperboxes
        System.out.println("Hyperboxes:");
        for(int j = 0; j < NUM_HYPERBOX; j++){
            for(int i = 0; i < 9; i++){
                System.out.print(hyperboxes.get(j).get(i) + " ");
            }
            System.out.println();
        }
    }
    
    /* 
    Check a cell's value if it is not conflicted with other cells' values
    WARNING: not used
    */
    public boolean checkCells(Cell cell){         
        if(rows.get(cell.getRow() - 1).contains(cell.getValue())){
            return false;
        }
        
        if(columns.get(cell.getColumn() - 1).contains(cell.getValue())){
            return false;
        }
        
        if(boxes.get(cell.getBox() - 1).contains(cell.getValue())){
            return false;
        }
        
        if(hyperboxes.get(cell.getHyperbox() - 1).contains(cell.getValue())){
            return false;
        }
        return true;
    }
    
    /* 
    Check a cell's value if it is not conflicted with other cells' values
    WARNING: not used
    */
    public boolean checkCells(Cell cell, int newValue){         
        if(rows.get(cell.getRow() - 1).contains(newValue)){
            return false;
        }
        
        if(columns.get(cell.getColumn() - 1).contains(newValue)){
            return false;
        }
        
        if(boxes.get(cell.getBox() - 1).contains(newValue)){
            return false;
        }
        
        if(hyperboxes.get(cell.getHyperbox() - 1).contains(newValue)){
            return false;
        }
        return true;
    }
    
    /*
    Set a cell's value
    */
    public void setCellValue(int index, int newValue){     
        // Initialize variables
        int row = board[index].getRow()- 1;
        int column = board[index].getColumn() - 1;
        int box = board[index].getBox() - 1;
        int hyperbox = board[index].getHyperbox() - 1;
        int oldValue = board[index].getValue();
            
        // Remove the old value from the sets
        rows.get(row).remove(Integer.valueOf(oldValue));
        columns.get(column).remove(Integer.valueOf(oldValue));
        boxes.get(box).remove(Integer.valueOf(oldValue));
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).remove(Integer.valueOf(oldValue));
        }
        
        // Set new value on the cell
        board[index].setValue(newValue);
        board[index].addUsedList(newValue);
        
        // Add new value to the sets
        rows.get(row).add(newValue);
        columns.get(column).add(newValue);
        boxes.get(box).add(newValue);
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).add(newValue);                                 
        }
    }
    
    /*
    Set a cell's value and set it uneditable
    WARNING: only use this function when creating a board, not solving it
    */
    public void setStartValue(int index, int newValue){        
        // Initialize varables
        Cell cell = board[index];
        int row = cell.getRow()- 1;
        int column = cell.getColumn() - 1;
        int box = cell.getBox() - 1;
        int hyperbox = cell.getHyperbox() - 1;
        int oldValue = cell.getValue();
        
        // Remove the old value from the sets
        rows.get(row).remove(oldValue);
        columns.get(column).remove(oldValue);
        boxes.get(box).remove(oldValue);
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).remove(oldValue);
        }
     
        // Set new value on the cell and set it uneditable
        board[index].setValue(newValue);
        board[index].setEditableFalse();
        
        // Add new value to the sets
        rows.get(row).add(newValue);
        columns.get(column).add(newValue);
        boxes.get(box).add(newValue);
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).add(newValue);                                 
        }
    }
    
    /* 
    Get a random element of a list
    http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
    */
    private int getRandomFromSet(List<Integer> set){  
        return set.get(ThreadLocalRandom.current().nextInt(0, set.size()));        
    }                
    
    /* 
    Check a cell for possible values that can be filled in that cell.
    Return those values in an ArrayList.
    */
    public ArrayList checkPossibleValues(Cell cell) {                
        // Initialize variables
        ArrayList<Integer> possibleValues = new ArrayList<>();
        possibleValues.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));                
        
        int row = cell.getRow() - 1;
        int column = cell.getColumn() - 1;
        int box = cell.getBox() - 1;
        int hyperbox = cell.getHyperbox() - 1;               
        
        // Check against other cells in the same row
        for (int j = 0; j < rows.get(row).size(); j++) {
            int value = rows.get(row).get(j);
            if (value != 0) {
                //System.out.println(value);
                if (possibleValues.contains(value)){
                    possibleValues.remove(Integer.valueOf(value));
                }
            }
        }           
        
        // Check against other cells in the same column
        for (int j = 0; j < columns.get(column).size(); j++) {
            int value = columns.get(column).get(j);
            if (value != 0) {
                //System.out.println(value);
                if (possibleValues.contains(value)){
                    possibleValues.remove(Integer.valueOf(value));
                }
            }
        }        
        
        // Check against other cells in the same box
        for (int j = 0; j < boxes.get(box).size(); j++) {
            int value = boxes.get(box).get(j);
            if (value != 0) {
                //System.out.println(value);
                if (possibleValues.contains(value)) {
                    possibleValues.remove(Integer.valueOf(value));
                }
            }
        }        
        
        // Check against other cells in the same hyperbox
        if (hyperbox != -1) {            
            for (int j = 0; j < hyperboxes.get(hyperbox).size(); j++) {               
                int value = hyperboxes.get(hyperbox).get(j);
                if (value != 0) {
                    //System.out.println(value);
                    if (possibleValues.contains(value)) {
                        possibleValues.remove(Integer.valueOf(value));
                    }
                }
            }
        }        
        
        // Check against other cells in the used list
        for (int j = 0; j < cell.getUsedList().size(); j++) {
            int value = cell.getUsedList().get(j);
            if (possibleValues.contains(value)) {
                possibleValues.remove(Integer.valueOf(value));
            }
        }

        return possibleValues;
    }    
   
    /*
    AI to solve the sudoku board.
    Using exhaustive search and depth first search algorithm.
    */
    public void AI_backtrack(){        
        // Declare variables
        int index = 0;
        Stack<Integer> backtrack = new Stack();
        ArrayList<Integer> possibleValues; 
        
        // Go through all the cells and set their values
        while(index < 81){                        
            if(board[index].isEditable()){
                possibleValues = checkPossibleValues(board[index]);
                if(!possibleValues.isEmpty()){ // if there is possible values to fill in
                    // int r = getRandomFromSet(possibleValues);            
                    int r = possibleValues.get(0);
                    setCellValue(index, r);
                    backtrack.add(index);
                    index++;
                }
                else{ // the index returns to the last element from stack to backtrack                                                  
                    board[index].resetUsedList();                    
                    index = backtrack.pop();                         
                    setCellValue(index, 0);                    
                }
            }
            else{
                index++;
            }
            
            // Print debug
            // System.out.println("Edit cell: " + index);            
            // printBoard();
        }        
    }    
    
    public void AI_backtrack_var1(){
        // Declare variables
        int index = 0;
        Stack<Integer> backtrack = new Stack();
        ArrayList<Integer> possibleValues; 
        boolean check = false;
        
        // Go through all the cells and set their values if there is only one possible value
        for (int j = 0; j < board.length; j++) {
            if (board[j].isEditable()) {
                possibleValues = checkPossibleValues(board[j]);
                if (possibleValues.size() == 1) {
                    setCellValue(j, possibleValues.get(0));
                    board[j].setEditableFalse();
                    // System.out.println("FILL " + j + " with " + possibleValues.get(0));
                    check = true;
                }
            }
            if (j == 80 && check) {
                j = 0;
                check = false;
                // System.out.println("LOOP AGAIN");
            }
        }

        // System.out.println("BACKTRACK");

        // Go through all the cells and set their values using backtracking method
        while(index < 81){                        
            if(board[index].isEditable()){
                possibleValues = checkPossibleValues(board[index]);
                if(!possibleValues.isEmpty()){ // if there is possible values to fill in
                    // int r = getRandomFromSet(possibleValues);            
                    int r = possibleValues.get(0);
                    setCellValue(index, r);
                    backtrack.add(index);
                    index++;
                }
                else{ // the index returns to the last element from stack to backtrack                                                  
                    board[index].resetUsedList();                    
                    index = backtrack.pop();                         
                    setCellValue(index, 0);                    
                }
            }
            else{
                index++;
            }
        }
    }
    
    public void AI_backtrack_var2(){
        // Declare variables
        int index = 0;
        Stack<Integer> backtrack = new Stack();
        ArrayList<Integer> possibleValues; 
        List<List<Integer>> tempPossibleValues = new ArrayList<>();   
        boolean check = false;
        
        for(int j = 0; j < NUM_ROW; j++){
            tempPossibleValues.add(new ArrayList<>());
        }
        
        // Go through all the cells and set their values if there is only one possible value
        for (int j = 0; j < board.length; j++) {
            if (board[j].isEditable()) {
                possibleValues = checkPossibleValues(board[j]);
                if (possibleValues.size() == 1) {
                    setCellValue(j, possibleValues.get(0));
                    board[j].setEditableFalse();
                    System.out.println("FILL " + j + " with " + possibleValues.get(0));
                    check = true;
                }
            }
            if (j == 80 && check) {
                j = 0;
                check = false;
                System.out.println("LOOP AGAIN");
            }
        }

        System.out.println("BACKTRACK");

        // Go through all the cells and set their values using backtracking method
        while(index < 81){                        
            if(board[index].isEditable()){
                possibleValues = checkPossibleValues(board[index]);
                if(!possibleValues.isEmpty()){ // if there is possible values to fill in
                    // int r = getRandomFromSet(possibleValues);            
                    int r = possibleValues.get(0);
                    setCellValue(index, r);
                    backtrack.add(index);
                    index++;
                }
                else{ // the index returns to the last element from stack to backtrack                                                  
                    board[index].resetUsedList();                    
                    index = backtrack.pop();                         
                    setCellValue(index, 0);                    
                }
            }
            else{
                index++;
            }
        }
    }
    
    /*
    Check the sets to for duplicate values
    http://stackoverflow.com/questions/562894/java-detect-duplicates-in-arraylist
    */
    public boolean checkSolution(){
        for(int j = 0; j < rows.size(); j++){
            List<Integer> list = rows.get(j);
            Set<Integer> set = new HashSet<>(list);
            if (set.size() < list.size()) {
                return false;
            }
        }
        
        for(int j = 0; j < columns.size(); j++){
            List<Integer> list = columns.get(j);
            Set<Integer> set = new HashSet<>(list);
            if (set.size() < list.size()) {
                return false;
            }
        }
        
        for(int j = 0; j < boxes.size(); j++){
            List<Integer> list = boxes.get(j);
            Set<Integer> set = new HashSet<>(list);
            if (set.size() < list.size()) {
                return false;
            }
        }
        
        for(int j = 0; j < hyperboxes.size(); j++){
            List<Integer> list = hyperboxes.get(j);
            Set<Integer> set = new HashSet<Integer>(list);
            if (set.size() < list.size()) {
                return false;
            }
        }        
        return true;
    }
    
    /*
    Import a string of txt.
    Each line in the txt file is a sudoku.
    Parameter index to pick which sudoku to import.
    */
    public String importSudoku(String filename, int index) throws IOException{    
        String sudokuString;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int lineRead = 0;    
            // Read lines until it reaches the index line then append that line and break
            while (line != null) {                                   
                if(lineRead == index){
                    sb.append(line);
                    sb.append(System.lineSeparator());                                        
                    break;
                }         
                line = br.readLine();                                    
                lineRead++;
            }
            sudokuString = sb.toString();
        }
        // System.out.println(sudokuString);
        return sudokuString;
    }    
    
    /*
    Generate the sudoku board from the imported string
    */
    public void generateSudokuFromString(String sudokuString){
        char[] sudokuArray = sudokuString.replaceAll(" ", "").trim().toCharArray();        
        for(int j = 0; j < sudokuArray.length; j++){            
            int value = Character.getNumericValue(sudokuArray[j]);
            // System.out.println("position: " + j + " " + value);
            if(value != 0){
                setStartValue(j, value);
            }
        }
    }
    
    public void exportSudoku(String filename){
        
    }
    
    public Board cloneBoard(){
        Board newBoard = new Board();
        newBoard.createBoard();
        
        
        return newBoard;
    }
}



