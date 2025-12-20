package TicTacToe;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * TicTacToePlayer.java
 * An interface representing a Tic Tac Toe player.
 */

public interface TicTacToePlayer {
    Cell makeMove(TicTacToeBoard<?> board);
}
