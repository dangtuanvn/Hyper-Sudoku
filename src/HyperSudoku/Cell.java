package HyperSudoku;

import java.util.ArrayList;

public class Cell {
    // Declare variables
    private int row, column, box, hyperbox, value, index;
    private boolean editable;    
    ArrayList<Integer> used; // Use to record what values the AI had tried on the cell
    
    public Cell(int row, int column){
        // Initialize variables
        this.row = row;
        this.column = column;
        index = column + (row - 1)* 9;
        this.value = 0;        
        used = new ArrayList<>();
        editable = true;                                
        
        // Determine which box the cell is in
        if(row >= 1 && row <= 3){
            if(column >= 1 && column <= 3){
                box = 1;
             
            }
            else if(column >= 4 && column <= 6){
                box = 2;
            }
            else{
                box = 3;
            }
        }
        else if(row >= 4 && row <= 6){
            if(column >= 1 && column <= 3){
                box = 4;
            }
            else if(column >= 4 && column <= 6){
                box = 5;
            }
            else{
                box = 6;
            }
        }
        else {
          
            if(column >= 1 && column <= 3){
                box = 7;
            }
            else if(column >= 4 && column <= 6){
                box = 8;
            }
            else{
                box = 9;
            }
        }
        
        // Determine which hyperbox the cell is in
        if(row >= 2 && row <= 4 ){
            if(column >= 2 && column <= 4){
                hyperbox = 1;
            }
            else if(column >= 6 && column <= 8){
                hyperbox = 2;
            }
        }
        else if(row >= 6 && row <= 8 ){
            if(column >= 2 && column <= 4){
                hyperbox = 3;
            }
            else if(column >= 6 && column <= 8){
                hyperbox = 4;
            }
        }       
        else{
            this.hyperbox = 0;
        }
    }

    // Mutators and accessors
    public void setValue(int newValue){
        this.value = newValue;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public void resetValue(){
        this.value = -1;
    }
    
    public int getSet(){
        return this.box;
    }
    
    public int getBox(){
        return this.box;
    }
    
    public int getHyperbox(){
        return this.hyperbox;
    }
    
    public int getColumn(){
        return this.column;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public boolean isEditable(){
        return this.editable;
    }
   
    public void setEditableFalse(){
        editable = false;
    }
    
    public ArrayList<Integer> getUsedList(){
        return used;
    }       
        
    public void addUsedList(int value){
        used.add(value);
    }
    
    public void resetUsedList(){
        used.clear();
    }
}
