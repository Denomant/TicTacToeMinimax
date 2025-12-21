package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * TicTacToeBoard.java
 * An abstract class representing the general Tic Tac Toe board.
 */

public abstract class TicTacToeBoard <T extends TicTacToeBoard<T>> {
    protected final Cell[][] cells;

    TicTacToeBoard(Cell[][] cells) {
        this.cells = cells;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Cell[][] getCells() {
        return cells;
    }

    public T moveResult(Cell cell) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T getMirroredVertically() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T getMirroredHorizontally() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Cell[] getEmptyCells() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public CellValue getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    abstract public boolean isTerminal();

    abstract public CellValue getWinner();
}
