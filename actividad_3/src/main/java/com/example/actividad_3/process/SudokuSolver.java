package com.example.actividad_3.process;

public class SudokuSolver {

    private final int[][] board; // 9x9; 0 = vacío

    public SudokuSolver(int[][] board) {
        if (board == null || board.length != 9) throw new IllegalArgumentException("Tablero 9x9 requerido");
        for (int[] row : board) if (row.length != 9) throw new IllegalArgumentException("Tablero 9x9 requerido");
        this.board = board;
    }

    // Backtracking recursivo: llena celdas vacías intentando 1..9
    public boolean solve() {
        int[] pos = findEmpty();
        if (pos == null) return true; // no hay vacías: solucionado

        int r = pos[0], c = pos[1];
        for (int num = 1; num <= 9; num++) {
            if (isValid(r, c, num)) {
                board[r][c] = num;        // probar
                if (solve()) return true; // recursión
                board[r][c] = 0;          // retroceso (backtrack)
            }
        }
        return false; // ninguna opción funciona aquí
    }

    // Busca primera casilla vacía (0); devuelve null si no hay
    private int[] findEmpty() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] == 0) return new int[]{r, c};
        return null;
    }

    // Reglas de Sudoku: sin repetición en fila, columna, y subcuadro 3x3
    private boolean isValid(int row, int col, int num) {
        // Fila
        for (int c = 0; c < 9; c++)
            if (board[row][c] == num) return false;

        // Columna
        for (int r = 0; r < 9; r++)
            if (board[r][col] == num) return false;

        // Caja 3x3
        int br = (row / 3) * 3, bc = (col / 3) * 3;
        for (int r = br; r < br + 3; r++)
            for (int c = bc; c < bc + 3; c++)
                if (board[r][c] == num) return false;

        return true;
    }

    // Impresión sencilla
    public void print() {
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) System.out.println("------+-------+------");
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) System.out.print("| ");
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }

    public int[][] getBoard() { return board; }
}

    

