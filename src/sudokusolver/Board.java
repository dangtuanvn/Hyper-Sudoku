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
    int NUM_ROW = 9;
    int NUM_COLUMN = 9;
    int NUM_BOX = 9;
    int NUM_HYPERBOX = 4;
    private Cell[] board;
           
    List<List<Integer>> rows = new ArrayList<>();
    List<List<Integer>> columns = new ArrayList<>();
    List<List<Integer>> boxes = new ArrayList<>();
    List<List<Integer>> hyperboxes = new ArrayList<>();       
    
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
        for(int j = 1; j <= NUM_ROW; j++){
            for(int i = 1; i <= NUM_COLUMN; i++){
                System.out.print("" + board[index].getValue() + " | ");
                index++;
            }
            System.out.println();
        }
    }
    
    public Cell[] getBoard(){
        return board;
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

    public boolean checkSolution(){
        return false;
    } 
    
    // http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range
    private int getRandomFromSet(ArrayList<Integer> set){  
        return set.get(ThreadLocalRandom.current().nextInt(0, set.size()));        
    }    
        
    ArrayList<Integer> setNumber;
    private void resetSetNumber(){
        setNumber = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }
    
    public void generateSudoku(){    
        for(int j = 0; j < board.length; j++){
            resetSetNumber();
            int r = getRandomFromSet(setNumber);
            setNumber.remove(r);
            board[j].setValue(r);
        }
    }
}
