/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import java.util.List;

/**
 *
 * @author Tuan Nguyen
 */
public class Board {
    int NUM_ROW = 9;
    int NUM_COLUMN = 9;
    int NUM_SET = 9;
    int NUM_HYPERSET = 4;
    private Cell[] board;
    private int[][] sets, hypersets, rows, columns;
    
    
    public Board(){  
        sets = new int[NUM_SET][9];
        hypersets = new int[NUM_HYPERSET][9];
        rows = new int[NUM_ROW][9];
        columns = new int[NUM_COLUMN][9];
        board = createBoard();
        loadSets(board);
        printSets();
    }
    
    public Cell[] createBoard(){
        Cell[] board = new Cell[81];    
        int index = 0;
        for(int i = 1; i <= NUM_COLUMN; i++){
            for(int j = 1; j <= NUM_ROW; j++){
                board[index] = new Cell(j, i);
//                rows[i - 1][j - 1] = index;               
//                columns[j - 1][i - 1] = index;                
//                
//                sets[board[index].getSet() - 1][j - 1] = index;
//                
//                if(board[index].getHyperset() != 0){
//                    hypersets[board[index].getHyperset() - 1][ 0 ] = index;
//                }
                rows[i - 1][j - 1] = index;               
                columns[j - 1][i - 1] = index;                
                
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
    
    public void generateSudoku(int n){
        
    }
    
    public Cell[] getBoard(){
        return board;
    }
    
    public boolean checkCells(Cell cell){
        return false;
    }
    
    public boolean checkSolution(){
        return false;
    }
    
    public void printSets(){
        System.out.println("Rows:");
        for(int j = 0; j < NUM_ROW; j++){
            for(int i = 0; i < NUM_ROW; i++){
                System.out.print(rows[j][i] + " ");
            }
        }
        System.out.println();
        
        System.out.println("Columns:");
        for(int j = 0; j < NUM_ROW; j++){
            for(int i = 0; i < NUM_ROW; i++){
                System.out.print(columns[j][i] + " ");
            }
        }
        System.out.println();
        
        System.out.println("Sets:");
        for(int j = 0; j < NUM_SET; j++){
            for(int i = 0; i < 9; i++){
                System.out.print(sets[j][i] + " ");
            }
            System.out.println();
        }
        
        System.out.println("Hypersets:");
        for(int j = 0; j < NUM_HYPERSET; j++){
            for(int i = 0; i < 9; i++){
                System.out.print(hypersets[j][i] + " ");
            }
            System.out.println();
        }
    }
    
    public void loadSets(Cell[] cells){
        int index0 = 0, index1 = 0, index2 = 0, index3 = 0, index4 = 0, index5 = 0, index6 = 0, index7 = 0, index8 = 0;
        int hyper0 = 0, hyper1 = 0, hyper2 = 0, hyper3 = 0;
        for(int i = 0; i < cells.length; i++){            
            switch (cells[i].getSet()) {
                case 1:
                    sets[0][index0] = cells[i].getIndex();
                    index0++;
                    break;
                case 2:
                    sets[1][index1] = cells[i].getIndex();
                    index1++;
                    break;
                case 3:
                    sets[2][index2] = cells[i].getIndex();
                    index2++;
                    break;
                case 4:
                    sets[3][index3] = cells[i].getIndex();
                    index3++;
                    break;
                case 5:
                    sets[4][index4] = cells[i].getIndex();
                    index4++;
                    break;
                case 6:
                    sets[5][index5] = cells[i].getIndex();
                    index5++;
                    break;
                case 7:
                    sets[6][index6] = cells[i].getIndex();
                    index6++;
                    break;
                case 8:
                    sets[7][index7] = cells[i].getIndex();
                    index7++;
                    break;
                case 9:
                    sets[8][index8] = cells[i].getIndex();
                    index8++;
                    break;
                default:
                    break;
            }
            
            switch (cells[i].getHyperset()) {
                case 1:
                    hypersets[0][hyper0] = cells[i].getIndex();
                    hyper0++;
                    break;
                case 2:
                    hypersets[1][hyper1] = cells[i].getIndex();
                    hyper1++;
                    break;
                case 3:
                    hypersets[2][hyper2] = cells[i].getIndex();
                    hyper2++;
                    break;
                case 4:
                    hypersets[3][hyper3] = cells[i].getIndex();
                    hyper3++;
                    break;
            }
        }
    }
}
