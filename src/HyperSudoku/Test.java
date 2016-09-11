package HyperSudoku;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {   
    public static void main(String[] args) {
        // Create a sudoku board
        Board sudoku = new Board();           
        
        try {
            String sudokuString = sudoku.importSudoku("input.txt", 5);
            sudoku.generateSudokuFromString(sudokuString);
            
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        sudoku.printBoard();
        
        // AI play
        long startTime = System.nanoTime();
        sudoku.AI_backtrack_var1();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.   
        
        
        // Check the result
        // sudoku.printSets();
        // System.out.println();
        
        if(sudoku.checkSolution()){
            System.out.println("The solution is CORRECT");
        }
        else{
            System.out.println("The solution is INCORRECT");
        }
        System.out.println();
        sudoku.printBoard();
        System.out.println("Time: " + duration / 1000000 + " microseconds");
    }    
}
