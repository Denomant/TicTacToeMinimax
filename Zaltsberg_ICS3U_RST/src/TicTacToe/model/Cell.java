package TicTacToe.model;

/**
 * A class representing a single cell on the Tic-Tac-Toe board. <br>
 * A cell is immutable and contains its row, column, and value (X, O, or EMPTY). <br>
 * Cell.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public class Cell {
    private final int row, col;
    private final CellValue value;

    /**
     * Constructs a Cell with the specified row, column, and value. <br>
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param value The value of the cell.
     * @throws IllegalArgumentException if row or col is negative, or if value is null
     */
    public Cell(int row, int col, CellValue value) {
        if (row < 0 || col < 0 || value == null) {
            throw new IllegalArgumentException("Invalid cell parameters");
        }
        this.row = row;
        this.col = col;
        this.value = value;
    }

    /**
     * Constructs a Cell with the specified row and column, and an EMPTY value. <br>
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @throws IllegalArgumentException if row or col is negative.
     */
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

    @Override
    public String toString() {
        return "Cell(" + row + ", " + col + ", " + value + ")";
    }

    /**
     * Gets the row of the cell. <br>
     * @return The row of the cell.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column of the cell. <br>
     * @return The column of the cell.
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets the value of the cell. <br>
     * @return The value of the cell.
     */
    public CellValue getValue() {
        return value;
    }

    /**
     * Gets the character representation of the cell's value. <br>
     * @return The character representing the cell's value.
     */
    public char getValueCharacter() {
        return value.getCharacter();
    }

    /**
     * Gets the string representation of the cell's value. <br>
     * @return The string representing the cell's value.
     */
    public String getValueString() {
        return Character.toString(value.getCharacter()); 
    }

    /**
     * Checks if the cell is empty. <br>
     * @return true if the cell is EMPTY, false otherwise.
     */
    public boolean isEmpty() {
        return value == CellValue.EMPTY;
    }
}
