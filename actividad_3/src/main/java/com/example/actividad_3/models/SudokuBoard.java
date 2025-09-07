package com.example.actividad_3.models;

import com.example.actividad_3.process.SudokuValidator;

public class SudokuBoard {

    private final int[][] board;
    private final boolean[][] fixed; // true = celda dada/no editable

    public SudokuBoard() {
        this.board = new int[9][9];
        this.fixed = new boolean[9][9];
    }

    public SudokuBoard(int[][] initial) {
        if (initial == null || initial.length != 9) {
            throw new IllegalArgumentException("Tablero 9x9 requerido");
        }
        this.board = new int[9][9];
        this.fixed = new boolean[9][9];
        for (int r = 0; r < 9; r++) {
            if (initial[r].length != 9) {
                throw new IllegalArgumentException("Tablero 9x9 requerido");
            }
            for (int c = 0; c < 9; c++) {
                this.board[r][c] = initial[r][c];
                this.fixed[r][c] = initial[r][c] != 0; // todo no-cero es “dado”
            }
        }
    }

    public int get(int r, int c) {
        checkRC(r, c);
        return board[r][c];
    }

    public boolean isFixed(int r, int c) {
        checkRC(r, c);
        return fixed[r][c];
    }

    /**
     * Coloca val (1..9) si no es fija y es válido; 0 limpia si no es fija.
     */
    public boolean trySet(int r, int c, int val) {
        checkRC(r, c);
        if (fixed[r][c]) {
            return false;
        }
        if (val == 0) {
            board[r][c] = 0;
            return true;
        }
        if (val < 1 || val > 9) {
            return false;
        }
        if (SudokuValidator.isPlacementValid(board, r, c, val)) {
            board[r][c] = val;
            return true;
        }
        return false;
    }

    /**
     * Limpia una celda si no es fija.
     */
    public boolean clear(int r, int c) {
        checkRC(r, c);
        if (fixed[r][c]) {
            return false;
        }
        board[r][c] = 0;
        return true;
    }

    /**
     * Borra TODAS las celdas no fijas.
     */
    public void resetNonFixed() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!fixed[r][c]) {
                    board[r][c] = 0;
                }
            }
        }
    }

    /**
     * Desbloquea todo y limpia (para empezar desde cero).
     */
    public void wipeAll() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = 0;
                fixed[r][c] = false;
            }
        }
    }

    /**
     * Marca como fijas todas las celdas no vacías actuales.
     */
    public void lockCurrentNonZeros() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                fixed[r][c] = board[r][c] != 0;
            }
        }
    }

    /**
     * Carga un puzzle (no modifica si tamaño incorrecto).
     */
    public void loadPuzzle(int[][] puzzle) {
        if (puzzle == null || puzzle.length != 9) {
            throw new IllegalArgumentException("Tablero 9x9 requerido");
        }
        for (int r = 0; r < 9; r++) {
            if (puzzle[r].length != 9) {
                throw new IllegalArgumentException("Tablero 9x9 requerido");
            }
        }
        wipeAll();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = puzzle[r][c];
                fixed[r][c] = (puzzle[r][c] != 0);
            }
        }
    }

    public boolean isComplete() {
        return SudokuValidator.isComplete(board);
    }

    public boolean isBoardValid() {
        return SudokuValidator.isBoardValid(board);
    }

    public int[][] toMatrix() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(board[r], 0, copy[r], 0, 9);
        }
        return copy;
    }

    public void print() {
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) {
                System.out.println("------+-------+------");
            }
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) {
                    System.out.print("| ");
                }
                int v = board[r][c];
                if (v == 0) {
                    System.out.print(". "); 
                }else {
                    // marca visual mínima: * si es fija
                    System.out.print(v + (fixed[r][c] ? "* " : " "));
                }
            }
            System.out.println();
        }
    }

    private void checkRC(int r, int c) {
        if (r < 0 || r >= 9 || c < 0 || c >= 9) {
            throw new IndexOutOfBoundsException("(r,c) debe estar en 0..8");
        }
    }
}
