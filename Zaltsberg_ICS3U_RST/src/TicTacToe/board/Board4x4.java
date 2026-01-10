package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;

/**
 * @Author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * Board4x4.java
 * A class representing a 4x4 Tic Tac Toe board.
 */

public final class Board4x4 extends TicTacToeBoard<Board4x4> {
    public Board4x4() {
        this(initializeEmptyCells());
    }

    public Board4x4(Cell[][] cells) {
        super(cells);

        if (cells.length != 4 || cells[0].length != 4) {
            throw new IllegalArgumentException("Invalid board size");
        }
    }

    private static Cell[][] initializeEmptyCells() {
        Cell[][] cells = new Cell[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cells[i][j] = new Cell(i, j, CellValue.EMPTY);
            }
        }
        return cells;
    }

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

    @Override
    protected Board4x4 create(Cell[][] cells) {
        return new Board4x4(cells);
    }
}
