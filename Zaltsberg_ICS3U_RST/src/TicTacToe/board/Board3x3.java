package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;

/**
 * Represents a 3x3 tic-tac-toe board. <br>
 * Board3x3.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public final class Board3x3 extends TicTacToeBoard<Board3x3> {
    /**
     * Initializes a new 3x3 tic-tac-toe board with empty cells.
     */
    public Board3x3() {
        this(initializeEmptyCells());
    }

    /**
     * Initializes a new 3x3 tic-tac-toe board with the specified cells. <br>
     * @param cells A 2D array of Cell objects representing the initial state of the board.
     * @throws IllegalArgumentException if the provided cells array does not have a size of 3x3.
     */
    public Board3x3(Cell[][] cells) {
        super(cells);

        if (cells.length != 3 || cells[0].length != 3) {
            throw new IllegalArgumentException("Invalid board size");
        }
    }
    
    /**
     * Initializes a 3x3 array of empty cells.
     * @return A 2D array of Cell objects initialized to EMPTY with proper row and column indices.
     */
    private static Cell[][] initializeEmptyCells() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
        for (int i = 0; i < 3; i++) {
            // Check row
            if (cells[i][0].getValue() != CellValue.EMPTY &&
                cells[i][0].getValue() == cells[i][1].getValue() &&
                cells[i][1].getValue() == cells[i][2].getValue()) {
                return cells[i][0].getValue();
            }

            // Check column
            if (cells[0][i].getValue() != CellValue.EMPTY &&
                cells[0][i].getValue() == cells[1][i].getValue() &&
                cells[1][i].getValue() == cells[2][i].getValue()) {
                return cells[0][i].getValue();
            }
        }
        // Check diagonals for a winner
        if (cells[0][0].getValue() != CellValue.EMPTY &&
            cells[0][0].getValue() == cells[1][1].getValue() &&
            cells[1][1].getValue() == cells[2][2].getValue()) {
            return cells[0][0].getValue();
        }
        if (cells[0][2].getValue() != CellValue.EMPTY &&
            cells[0][2].getValue() == cells[1][1].getValue() &&
            cells[1][1].getValue() == cells[2][0].getValue()) {
            return cells[0][2].getValue();
        }

        // If no winner found, return a tie if the board is full, else return null if the game is not over
        return getEmptyCells().length == 0 ? CellValue.EMPTY : null;
    }

    /**
     * @return A new instance of Board3x3 initialized with the specified cells.
     */
    @Override
    protected Board3x3 create(Cell[][] cells) {
        return new Board3x3(cells);
    }
}
