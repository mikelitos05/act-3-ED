package com.example.actividad_3.process;
import java.util.ArrayList;
import java.util.List;

public class SudokuValidator {

    /** ¿Es válido poner 'num' en (row,col) considerando el estado actual? */
    public static boolean isPlacementValid(int[][] b, int row, int col, int num) {
        if (num < 1 || num > 9) return false;
        // ignorar la casilla actual (permite reintentos)
        for (int c = 0; c < 9; c++) if (c != col && b[row][c] == num) return false;    // fila
        for (int r = 0; r < 9; r++) if (r != row && b[r][col] == num) return false;    // columna

        int br = (row / 3) * 3, bc = (col / 3) * 3;
        for (int r = br; r < br + 3; r++)
            for (int c = bc; c < bc + 3; c++)
                if (!(r == row && c == col) && b[r][c] == num) return false;           // caja 3x3
        return true;
    }

    /** ¿Todas las celdas no vacías cumplen reglas? */
    public static boolean isBoardValid(int[][] b) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                int v = b[r][c];
                if (v != 0) {
                    b[r][c] = 0; // evitar chocar consigo mismo
                    boolean ok = isPlacementValid(b, r, c, v);
                    b[r][c] = v;
                    if (!ok) return false;
                }
            }
        return true;
    }

    public static boolean isComplete(int[][] b) {
        for (int[] row : b) for (int v : row) if (v == 0) return false;
        return true;
    }

    /** Lista posiciones en conflicto, formato "rXcY". */
    public static List<String> findConflicts(int[][] b) {
        List<String> bad = new ArrayList<>();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                int v = b[r][c];
                if (v == 0) continue;
                b[r][c] = 0;
                boolean ok = isPlacementValid(b, r, c, v);
                b[r][c] = v;
                if (!ok) bad.add("r" + (r+1) + "c" + (c+1));
            }
        return bad;
    }

    /** Impresión con separadores. */
    public static void print(int[][] b) {
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) System.out.println("------+-------+------");
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) System.out.print("| ");
                System.out.print(b[r][c] + " ");
            }
            System.out.println();
        }
    }
}


