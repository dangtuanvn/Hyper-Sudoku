package sudokusolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuSolver {
    
    public static void main(String[] args) {
        Board sudoku = new Board();           

//        try {
//            String sudokuString = sudoku.importSudoku("input.txt", 1);
//            sudoku.generateSudoku(sudokuString);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(SudokuSolver.class.getName()).log(Level.SEVERE, null, ex);
//        }
        sudoku.printBoard();
        sudoku.AI_play();
    }    
}
