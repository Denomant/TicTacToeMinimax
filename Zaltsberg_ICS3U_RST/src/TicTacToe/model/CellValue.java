package TicTacToe.model;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * CellValue.java
 * An enum representing the possible values of a cell in Tic Tac Toe.
 */

public enum CellValue {
    X(1),
    O(-1),
    EMPTY(0);

    private final int value;

    CellValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

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
