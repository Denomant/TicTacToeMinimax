package TicTacToe.boardprinter;

import TicTacToe.board.TicTacToeBoard;

/**
 * An interface for converting a TicTacToeBoard into a printable string. <br>
 * BoardPrinter.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public interface BoardPrinter {
    /**
     * Renders the given TicTacToeBoard into a string representation.
     * @param board The TicTacToeBoard to be rendered.
     * @return A string representation of the board.
     */
    String render(TicTacToeBoard<?> board);
}
