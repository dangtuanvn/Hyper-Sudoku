/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

/**
 *
 * @author Tuan Nguyen
 */
public class Cell {
    private int row, column, set, hyperset, value, index;
    public Cell(int row, int column){
        this.row = row;
        this.column = column;
        index = column + (row - 1)* 9 - 1;
        this.value = 0;
        
        this.hyperset = 0;
        if(row >= 2 && row <= 4 ){
            if(column >= 2 && column <= 4){
                hyperset = 1;
            }
            else if(column >= 6 && column <= 8){
                hyperset = 2;
            }
        }
        else if(row >= 6 && row <= 8 ){
            if(column >= 2 && column <= 4){
                hyperset = 3;
            }
            else if(column >= 6 && column <= 8){
                hyperset = 4;
            }
        }              
        
        if(row >= 1 && row <= 3){
            if(column >= 1 && column <= 3){
                set = 1;
             
            }
            else if(column >= 4 && column <= 6){
                set = 2;
            }
            else{
                set = 3;
            }
        }
        else if(row >= 4 && row <= 6){
            if(column >= 1 && column <= 3){
                set = 4;
            }
            else if(column >= 4 && column <= 6){
                set = 5;
            }
            else{
                set = 6;
            }
        }
        else {
          
            if(column >= 1 && column <= 3){
                set = 7;
            }
            else if(column >= 4 && column <= 6){
                set = 8;
            }
            else{
                set = 9;
            }
        }
    }
    
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
        return this.set;
    }
    
    public int getHyperset(){
        return this.hyperset;
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
}
