package TicTacToe;

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

    T moveResult(Cell cell) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    T getMirroredVertically() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    T getMirroredHorizontally() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Cell[] getEmptyCells() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    CellValue getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    abstract boolean isTerminal();

    abstract CellValue getWinner();
}
