package com.example.actividad_3.ui;
import com.example.actividad_3.models.SudokuBoard;

public class App {

    /** Lanza la GUI de Sudoku. (Main es el único punto de entrada) */
    public void runSudokuGUI() {
        int[][] PUZZLE_EASY = {
            {0,0,0, 2,6,0, 7,0,1},
            {6,8,0, 0,7,0, 0,9,0},
            {1,9,0, 0,0,4, 5,0,0},

            {8,2,0, 1,0,0, 0,4,0},
            {0,0,4, 6,0,2, 9,0,0},
            {0,5,0, 0,0,3, 0,2,8},
            
            {0,0,9, 3,0,0, 0,7,4},
            {0,4,0, 0,5,0, 0,3,6},
            {7,0,3, 0,1,8, 0,0,0}
        };
        SudokuBoard board = new SudokuBoard(PUZZLE_EASY);
        new SudokuGUI(board).showWindow(); // SudokuGUI debe estar en el paquete ui
    }
}
