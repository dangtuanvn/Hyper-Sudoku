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
        int n = 200;
        long sum = 0;
        for (int j = 0; j < n; j++) {
            System.out.println("PUZZLE # " + j);
            // Create a sudoku board
            Board sudoku = new Board();

            try {
                String sudokuString = sudoku.importSudoku("problems.txt", j);
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
            duration = duration / 1000000;
            System.out.println("Time: " + duration + " miliseconds");
            System.out.println();
            
            sum += duration;    
        }
        
        long average = sum / n;
        System.out.println("Sum: " + sum + " miliseconds");
        System.out.println("Average: " + average + " miliseconds");
    }
}
