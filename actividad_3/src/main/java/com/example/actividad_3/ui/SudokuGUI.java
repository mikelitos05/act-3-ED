package com.example.actividad_3.ui;
import com.example.actividad_3.models.SudokuBoard;
import com.example.actividad_3.process.SudokuSolver;
import com.example.actividad_3.process.SudokuValidator;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SudokuGUI extends JFrame {
    private final SudokuBoard board;
    private final Cell[][] cells = new Cell[9][9];

    public SudokuGUI(SudokuBoard board) {
        super("Sudoku — Flechas y dígitos");
        this.board = board;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(buildGrid(), BorderLayout.CENTER);
        add(buildToolbar(), BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);
    }

    public void showWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() { setVisible(true); }
        });
    }

    private JPanel buildGrid() {
        JPanel panel = new JPanel(new GridLayout(9, 9));
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 22);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                final Cell tf = new Cell(r, c);
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(f);
                tf.setPreferredSize(new Dimension(48, 48));

                int v = board.get(r, c);
                if (v != 0) {
                    tf.setText(String.valueOf(v));
                    tf.setEditable(false);
                    tf.setForeground(new Color(0, 70, 160)); // fijas en azul
                } else {
                    tf.setText("");
                    tf.setEditable(true);
                }

                // bordes gruesos por cajas 3x3
                int top = (r % 3 == 0) ? 3 : 1;
                int left = (c % 3 == 0) ? 3 : 1;
                int bottom = (r == 8) ? 3 : 1;
                int right = (c == 8) ? 3 : 1;
                tf.setBorder(BorderFactory.createMatteBorder(top,left,bottom,right, Color.DARK_GRAY));

                // Mover con flechas entre celdas
                tf.setFocusTraversalKeysEnabled(false);
                InputMap im = tf.getInputMap(JComponent.WHEN_FOCUSED);
                ActionMap am = tf.getActionMap();
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
                am.put("moveUp", new MoveAction(-1,0));
                am.put("moveDown", new MoveAction(1,0));
                am.put("moveLeft", new MoveAction(0,-1));
                am.put("moveRight", new MoveAction(0,1));

                // Escribir dígitos 1..9, limpiar con 0 o Backspace
                tf.addKeyListener(new KeyAdapter() {
                    @Override public void keyTyped(KeyEvent e) {
                        if (!tf.isEditable()) return;
                        char ch = e.getKeyChar();
                        if (ch >= '1' && ch <= '9') {
                            e.consume();
                            int val = ch - '0';
                            if (board.trySet(tf.r, tf.c, val)) {
                                tf.setText(String.valueOf(val));
                                flash(tf, new Color(0,150,0));
                            } else {
                                flash(tf, Color.RED);
                            }
                        } else if (ch == '0' || ch == '\b') {
                            e.consume();
                            if (board.clear(tf.r, tf.c)) {
                                tf.setText("");
                                flash(tf, Color.GRAY);
                            } else {
                                flash(tf, Color.RED);
                            }
                        } else if (!Character.isISOControl(ch)) {
                            e.consume(); // bloquea letras/símbolos
                        }
                    }
                });

                cells[r][c] = tf;
                panel.add(tf);
            }
        }
        return panel;
    }

    private JPanel buildToolbar() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        JButton bCand = new JButton("Candidatos");
        bCand.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Cell sel = getFocusedCell();
                if (sel == null) { info("Selecciona una celda editable."); return; }
                if (!sel.isEditable()) { info("La celda es fija."); return; }
                List<Integer> cs = new ArrayList<Integer>();
                int[][] m = board.toMatrix();
                for (int v = 1; v <= 9; v++)
                    if (SudokuValidator.isPlacementValid(m, sel.r, sel.c, v)) cs.add(v);
                info("Candidatos en ("+(sel.r+1)+","+(sel.c+1)+"): " + cs);
            }
        });

        JButton bValid = new JButton("Validar tablero");
        bValid.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if (board.isBoardValid()) info("✔ Tablero válido (sin conflictos).");
                else info("✖ Conflictos en: " + SudokuValidator.findConflicts(board.toMatrix()));
            }
        });

        JButton bSolved = new JButton("¿Resuelto?");
        bSolved.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                boolean valid = board.isBoardValid();
                boolean full  = board.isComplete();
                if (valid && full) info("✔ ¡Correcto! Tablero completo y válido.");
                else if (!valid)   info("✖ Hay conflictos.");
                else               info("• Aún incompleto, pero consistente.");
            }
        });

        JButton bReset = new JButton("Limpiar no fijas");
        bReset.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                board.resetNonFixed();
                refreshGrid();
            }
        });

        JButton bLoadEasy = new JButton("Cargar EASY");
        bLoadEasy.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                board.loadPuzzle(PUZZLE_EASY());
                refreshGrid();
            }
        });

        // opcionales: más puzzles
        JButton bLoadMedium = new JButton("Cargar MEDIUM");
        bLoadMedium.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                board.loadPuzzle(PUZZLE_MEDIUM());
                refreshGrid();
            }
        });

        JButton bLoadHard = new JButton("Cargar HARD");
        bLoadHard.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                board.loadPuzzle(PUZZLE_HARD());
                refreshGrid();
            }
        });

        JButton bPeek = new JButton("Ver solución");
        bPeek.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                int[][] work = board.toMatrix();
                SudokuSolver solver = new SudokuSolver(work);
                if (solver.solve()) showMatrix("Solución (no aplicada):", solver.getBoard());
                else info("✖ No tiene solución.");
            }
        });

        for (JButton b : new JButton[]{bCand,bValid,bSolved,bReset,bLoadEasy,bLoadMedium,bLoadHard,bPeek}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(200, 36));
            p.add(b);
            p.add(Box.createVerticalStrut(8));
        }
        return p;
    }

    private void refreshGrid() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                int v = board.get(r,c);
                Cell cell = cells[r][c];
                cell.setEditable(!board.isFixed(r,c));
                cell.setForeground(board.isFixed(r,c) ? new Color(0,70,160) : Color.BLACK);
                cell.setText(v == 0 ? "" : String.valueOf(v));
            }
    }

    private void flash(JComponent comp, Color color) {
        comp.setBorder(new LineBorder(color, 2));
        // Opcional: sonido -> Toolkit.getDefaultToolkit().beep();
    }

    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Sudoku", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMatrix(String title, int[][] m) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) sb.append("------+-------+------\n");
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) sb.append("| ");
                sb.append(m[r][c]).append(' ');
            }
            sb.append('\n');
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, new JScrollPane(area), title, JOptionPane.PLAIN_MESSAGE);
    }

    private Cell getFocusedCell() {
        Component f = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (f instanceof Cell) return (Cell) f;
        return null;
    }

    private class MoveAction extends AbstractAction {
        private final int dr, dc;
        MoveAction(int dr, int dc) { this.dr = dr; this.dc = dc; }
        @Override public void actionPerformed(ActionEvent e) {
            Cell cur = getFocusedCell();
            if (cur == null) return;
            int nr = Math.max(0, Math.min(8, cur.r + dr));
            int nc = Math.max(0, Math.min(8, cur.c + dc));
            cells[nr][nc].requestFocusInWindow();
        }
    }

    private static class Cell extends JTextField {
        final int r, c;
        Cell(int r, int c) { this.r = r; this.c = c; }
    }

    // Puzzles
    private static int[][] PUZZLE_EASY() {
        return new int[][]{
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
    }
    private static int[][] PUZZLE_MEDIUM() {
        return new int[][]{
            {0,0,0, 0,0,0, 2,0,0},
            {0,8,0, 0,0,7, 0,9,0},
            {6,0,2, 0,0,0, 0,0,0},
            {0,0,0, 0,6,0, 0,0,3},
            {0,0,0, 5,0,9, 0,0,0},
            {8,0,0, 0,2,0, 0,0,0},
            {0,0,0,  0,0,0, 8,0,6},
            {0,2,0, 7,0,0, 0,4,0},
            {0,0,5, 0,0,0, 0,0,0}
        };
    }
    private static int[][] PUZZLE_HARD() {
        return new int[][]{
            {0,0,0, 0,0,0, 0,1,2},
            {0,0,0, 0,0,7, 0,0,0},
            {0,0,1, 0,0,0, 0,0,0},
            {0,0,0, 0,6,0, 0,0,0},
            {0,0,0, 8,0,3, 0,0,0},
            {0,0,0, 0,2,0, 0,0,0},
            {0,0,0, 0,0,0, 5,0,0},
            {0,0,0, 7,0,0, 0,0,0},
            {4,6,0, 0,0,0, 0,0,0}
        };
    }
}
