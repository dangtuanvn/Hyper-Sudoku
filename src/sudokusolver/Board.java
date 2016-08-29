/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Tuan Nguyen
 */
public class Board {
    public int NUM_ROW = 9;
    public int NUM_COLUMN = 9;
    public int NUM_BOX = 9;
    public int NUM_HYPERBOX = 4;
    private Cell[] board;
           
    private List<List<Integer>> rows = new ArrayList<>();
    private List<List<Integer>> columns = new ArrayList<>();
    private List<List<Integer>> boxes = new ArrayList<>();
    private List<List<Integer>> hyperboxes = new ArrayList<>();       
    
    private List<List<Integer>> checkSets = new ArrayList<>();       
    
    public Board(){    
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
        checkSets.add(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
        
        board = createBoard();        
        printSets();
    }
    
    public Cell[] createBoard(){
        Cell[] board = new Cell[81];            
        int index = 0;
        for(int i = 1; i <= NUM_ROW; i++){
            for(int j = 1; j <= NUM_COLUMN; j++){
                board[index] = new Cell(i, j);
                
                // Load the number to sets
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
                if(!(j == 1 || j == 5 || j == 9 || i == 1 || i == 5 || i == 9)){
                    System.out.print("" + "\u001B[36m" + pval + "\u001B[0m  " + (i % 3 == 0 ? "  " : ""));
                }
                else{
                    System.out.print("" + pval + "  " + (i % 3 == 0 ? "  " : ""));
                }
                index++;
            }
            System.out.println();
            if(j%3==0){
                System.out.println();
            }
        }
        System.out.println("------------------------------");
    }
    
    public Cell[] getBoard(){
        return this.board;
    }
    
    public void printSets(){
        System.out.println("Rows:");
        for(int j = 0; j < NUM_ROW; j++){
            for(int i = 0; i < NUM_ROW; i++){
                System.out.print(rows.get(j).get(i) + " ");
            }
        }
        System.out.println();
        
        System.out.println("Columns:");
        for(int j = 0; j < NUM_ROW; j++){
            for(int i = 0; i < NUM_ROW; i++){
                System.out.print(columns.get(j).get(i) + " ");
            }
        }
        System.out.println();
        
        System.out.println("Boxes:");
        for(int j = 0; j < NUM_BOX; j++){
            for(int i = 0; i < 9; i++){
                System.out.print(boxes.get(j).get(i) + " ");
            }
            System.out.println();
        }
        
        System.out.println("Hyperboxes:");
        for(int j = 0; j < NUM_HYPERBOX; j++){
            for(int i = 0; i < 9; i++){
                System.out.print(hyperboxes.get(j).get(i) + " ");
            }
            System.out.println();
        }
    }
    
    public boolean checkCells(Cell cell){         
        if(rows.get(cell.getRow() - 1).     contains(cell.getValue())){
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
    
    public void setCellValue(Cell cell, int newValue){     
        int row = cell.getRow()- 1;
        int column = cell.getColumn() - 1;
        int box = cell.getBox() - 1;
        int hyperbox = cell.getHyperbox() - 1;
        
        rows.get(row).remove(cell.getValue());
        columns.get(column).remove(cell.getValue());
        boxes.get(box).remove(cell.getValue());
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).remove(cell.getValue());
        }
        
        cell.setValue(newValue);
        
        rows.get(row).add(newValue);
        columns.get(column).add(newValue);
        boxes.get(box).add(newValue);
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).add(newValue);                                 
        }
    }
    
    // WARNING: only use this function for creating a board, not solving it
    public void setCellValue(int input_row, int input_column, int newValue){
        int index = (input_column + (input_row - 1)* 9) - 1;
        Cell cell = board[index];
        int row = cell.getRow()- 1;
        int column = cell.getColumn() - 1;
        int box = cell.getBox() - 1;
        int hyperbox = cell.getHyperbox() - 1;
        
        rows.get(row).remove(cell.getValue());
        columns.get(column).remove(cell.getValue());
        boxes.get(box).remove(cell.getValue());
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).remove(cell.getValue());
        }
     
        board[index].setValue(newValue);
        
        rows.get(row).add(newValue);
        columns.get(column).add(newValue);
        boxes.get(box).add(newValue);
        if(hyperbox != -1){
            hyperboxes.get(hyperbox).add(newValue);                                 
        }
    }

    public boolean checkSolution(){
        return false;
    } 
    
    // http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
    private int getRandomFromSet(List<Integer> set){  
        return set.get(ThreadLocalRandom.current().nextInt(0, set.size()));        
    }                
    
    public void generateSudoku(){    
        
    }
    
    public ArrayList checkPossibleValues(Cell cell) {
        ArrayList possibleValues = new ArrayList<>(checkSets);
        int row = cell.getRow() - 1;
        int column = cell.getColumn() - 1;
        int box = cell.getBox() - 1;
        int hyperbox = cell.getHyperbox() - 1;

        for (int j = 0; j < rows.get(row).size(); j++) {
            int value = rows.get(row).get(j);
            if (value != 0) {
                System.out.println(value);
                if (possibleValues.contains(value)) {
                    possibleValues.remove(value);
                }
            }
        }   

        for (int j = 0; j < columns.get(column).size(); j++) {
            int value = columns.get(column).get(j);
            if (value != 0) {
                System.out.println(value);
                if (possibleValues.contains(value)) {
                    possibleValues.remove(value);
                }
            }
        }

        for (int j = 0; j < boxes.get(box).size(); j++) {
            int value = boxes.get(box).get(j);
            if (value != 0) {
                System.out.println(value);
                if (possibleValues.contains(value)) {
                    possibleValues.remove(value);
                }
            }
        }

        if (hyperbox != -1) {            
            for (int j = 0; j < hyperboxes.get(hyperbox).size(); j++) {
                
                int value = hyperboxes.get(hyperbox).get(j);
                if (value != 0) {
                    System.out.println(value);
                    if (possibleValues.contains(value)) {
                        possibleValues.remove(value);
                    }
                }
            }
        }

        for (int j = 0; j < cell.getUsedList().size(); j++) {
            int value = cell.getUsedList().get(j);
            if (value != 0) {
                if (possibleValues.contains(value)) {
                    possibleValues.remove(value);
                }
            }
        }
        return possibleValues;
    }
    
    public void AI_exhaustiveSearch(){
        
    }      
}