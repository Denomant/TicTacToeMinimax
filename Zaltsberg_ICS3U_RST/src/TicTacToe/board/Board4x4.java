package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;

/**
 * Represents a 4x4 tic-tac-toe board. <br>
 * Board4x4.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public final class Board4x4 extends TicTacToeBoard<Board4x4> {
    /**
     * Initializes a new 4x4 tic-tac-toe board with empty cells.
     */
    public Board4x4() {
        this(initializeEmptyCells());
    }

    /**
     * Initializes a new 4x4 tic-tac-toe board with the specified cells. <br>
     * @param cells A 2D array of Cell objects representing the initial state of the board.
     * @throws IllegalArgumentException if the provided cells array does not have a size of 4x4.
     */
    public Board4x4(Cell[][] cells) {
        super(cells);

        if (cells.length != 4 || cells[0].length != 4) {
            throw new IllegalArgumentException("Invalid board size");
        }
    }

    /**
     * Initializes a 4x4 array of empty cells.
     * @return A 2D array of Cell objects initialized to EMPTY with proper row and column indices.
     */
    private static Cell[][] initializeEmptyCells() {
        Cell[][] cells = new Cell[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cells[i][j] = new Cell(i, j, CellValue.EMPTY);
            }
        }
        return cells;
    }

    /**
     * @return The CellValue of the winner (X or O) if there is one, CellValue.EMPTY if it's a tie, or null if the game is not over.
     */
    @Override
    public CellValue getWinner() {
        // Check rows and columns for a winner
        for (int i = 0; i < 4; i++) {
            // Check row
            if (cells[i][0].getValue() != CellValue.EMPTY &&
                cells[i][0].getValue() == cells[i][1].getValue() &&
                cells[i][1].getValue() == cells[i][2].getValue() &&
                cells[i][2].getValue() == cells[i][3].getValue()){
                return cells[i][0].getValue();
            }

            // Check column
            if (cells[0][i].getValue() != CellValue.EMPTY &&
                cells[0][i].getValue() == cells[1][i].getValue() &&
                cells[1][i].getValue() == cells[2][i].getValue() &&
                cells[2][i].getValue() == cells[3][i].getValue()) {
                return cells[0][i].getValue();
            }
        }
        // Check diagonals for a winner
        if (cells[0][0].getValue() != CellValue.EMPTY &&
            cells[0][0].getValue() == cells[1][1].getValue() &&
            cells[1][1].getValue() == cells[2][2].getValue() &&
            cells[2][2].getValue() == cells[3][3].getValue()) {
            return cells[0][0].getValue();
        }
        if (cells[0][3].getValue() != CellValue.EMPTY &&
            cells[0][3].getValue() == cells[1][2].getValue() &&
            cells[1][2].getValue() == cells[2][1].getValue() &&
            cells[2][1].getValue() == cells[3][0].getValue()) {
            return cells[0][3].getValue();
        }

        // If no winner found, return a tie if the board is full, else return null if the game is not over
        return getEmptyCells().length == 0 ? CellValue.EMPTY : null;
    }

    /**
     * @return A new instance of Board4x4 initialized with the specified cells.
     */
    @Override
    protected Board4x4 create(Cell[][] cells) {
        return new Board4x4(cells);
    }
}
