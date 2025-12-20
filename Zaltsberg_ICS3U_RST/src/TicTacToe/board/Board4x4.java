package TicTacToe;

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
    public boolean isTerminal() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CellValue getWinner() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
