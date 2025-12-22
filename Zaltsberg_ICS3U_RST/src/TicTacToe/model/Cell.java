package TicTacToe.model;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * Cell.java
 * An immutable class cell representing a single cell on the Tic Tac Toe board.
 */

public final class Cell {
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
        int result = 31;
        result = 31 * result + row;
        result = 31 * result + col;
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Early-exit optimization point
        if (this == obj) {
            return true;
        }

        // Check the other object is not null
        if (obj == null) {
            return false;
        }

        // Check the other class is a cell
        if (obj.getClass() != Cell.class){
            return false;
        }

        // Cast obj to Cell
        Cell other = (Cell) obj;

        return row == other.row && col == other.col && value == other.value;
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
        return value.getCharacter();
    }

    public String getValueString() {
        return Character.toString(value.getCharacter()); 
    }

    public boolean isEmpty() {
        return value == CellValue.EMPTY;
    }
}
