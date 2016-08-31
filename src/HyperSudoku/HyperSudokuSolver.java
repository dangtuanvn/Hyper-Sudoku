package HyperSudoku;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HyperSudokuSolver {
    
    public static void main(String[] args) {
        // Create a sudoku board
        Board sudoku = new Board();           
        
        try {
            String sudokuString = sudoku.importSudoku("input.txt", 8);
            sudoku.generateSudokuFromString(sudokuString);
            
        } catch (IOException ex) {
            Logger.getLogger(HyperSudokuSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        sudoku.printBoard();
        
        // AI play
        sudoku.AI_play();
        
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
    }    
}
