package TicTacToe.boardprinter;

import TicTacToe.board.TicTacToeBoard;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * BoardPrinter.java
 * An interface for printing the Tic Tac Toe board.
 */

public interface BoardPrinter {
    String render(TicTacToeBoard<?> board);
}
