package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;

/**
 * @Author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * Board3x3.java
 * A class representing a 3x3 Tic Tac Toe board.
 */

public final class Board3x3 extends TicTacToeBoard<Board3x3> {
    public Board3x3() {
        this(initializeEmptyCells());
    }

    public Board3x3(Cell[][] cells) {
        super(cells);

        if (cells.length != 3 || cells[0].length != 3) {
            throw new IllegalArgumentException("Invalid board size");
        }
    }
    
    private static Cell[][] initializeEmptyCells() {
        Cell[][] cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

    @Override
    protected Board3x3 create(Cell[][] cells) {
        return new Board3x3(cells);
    }
}
