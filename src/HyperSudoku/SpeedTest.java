/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HyperSudoku;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuan Nguyen
 */
public class SpeedTest {
    public static void main(String[] args) {
        // Create a sudoku board
        long sum = 0;
        for (int j = 0; j < 50; j++) {
            System.out.println("PUZZLE # " + j);
            Board sudoku = new Board();

            try {
                String sudokuString = sudoku.importSudoku("hard.txt", j);
                sudoku.generateSudokuFromString(sudokuString);

            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
            // sudoku.printBoard();

            // AI play
            long startTime = System.nanoTime();
            sudoku.AI_backtrack_var1();
            long endTime = System.nanoTime();

            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.   

            // Check the result
            // sudoku.printSets();
            // System.out.println();
            if (sudoku.checkSolution()) {
                System.out.println("The solution is CORRECT");
            } else {
                System.out.println("The solution is INCORRECT");
            }
            System.out.println();
            sudoku.printBoard();
            System.out.println("Time: " + duration / 1000 + " microseconds");
            System.out.println();
            sum += duration;    
        }
        
        long average = sum / 100;
        System.out.println("Sum: " + sum + " microseconds");
        System.out.println("Average: " + average + " microseconds");
    }
}
