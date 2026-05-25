package TicTacToe.model;

/**
 * An enum representing the possible values of a cell in a Tic-Tac-Toe game. <br>
 * Each state has an associated integer value and a character representation. <br>
 * CellValue.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public enum CellValue {
    X(1),
    O(-1),
    EMPTY(0);

    private final int value;

    /**
     * Constructor for CellValue enum constants.
     * @param value The integer value associated with the cell state (1 for X, -1 for O, 0 for EMPTY).
     */
    CellValue(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value associated with this CellValue.
     * @return The integer value of this CellValue (1 for X, -1 for O, 0 for EMPTY).
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the character representation associated with this CellValue.
     * @return The character representing this CellValue ('X' for X, 'O' for O, ' ' for EMPTY).
     */
    public char getCharacter() {
        switch (this) {
            case X:
                return 'X';
            case O:
                return 'O';
            case EMPTY:
                return ' ';
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
    }
}
