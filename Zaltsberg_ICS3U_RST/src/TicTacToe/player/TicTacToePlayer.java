package TicTacToe.player;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;

/**
 * An interface representing an input method for making moves in a Tic Tac Toe game. <br>
 * TicTacToePlayer.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
 */

public interface TicTacToePlayer {
    /**
     * Returns the cell that the player wants to mark on the board. <br>
     * Does not necessarily have to be a human player, as this could be implemented by an AI. <br>
     * @param board The current state of the board, which the player can use to make an informed decision.
     * @return The cell that the player wants to mark on the board.
     */
    Cell makeMove(TicTacToeBoard<?> board);
}
