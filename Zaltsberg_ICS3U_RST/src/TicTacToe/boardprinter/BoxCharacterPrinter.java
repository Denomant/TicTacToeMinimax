package TicTacToe;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * BoxCharacterPrinter.java
 * A class for printing the Tic Tac Toe board using box characters.
 * Output format:
 * ┌───┬───┬───┐
 * │ 1 │ 2 │ O │
 * ├───┼───┼───┤
 * │ X │ 5 │ O │
 * ├───┼───┼───┤
 * │ X │ 7 │ X │
 * └───┴───┴───┘

 */
public final class BoxCharacterPrinter implements BoardPrinter {
    BoxCharacterPrinter() {
    }

    @Override
    public String render(TicTacToeBoard<?> board) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
