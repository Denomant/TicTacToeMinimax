package TicTacToe.model;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * Cell.java
 * An immutable class cell representing a single cell on the Tic Tac Toe board.
 */

public class Cell {
    private final int row, col;
    private final CellValue value;

    public Cell(int row, int col, CellValue value) {
        if (row < 0 || col < 0 || value == null) {
            throw new IllegalArgumentException("Invalid cell parameters");
        }
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public Cell(int row, int col) {
        this(row, col, CellValue.EMPTY);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellValue getValue() {
        return value;
    }

    public char getValueCharacter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getValueString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
