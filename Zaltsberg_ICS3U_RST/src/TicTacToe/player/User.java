package TicTacToe.player;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;

import TicTacToe.input.*;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * User.java
 * A class representing a user player in Tic Tac Toe who makes a move using the console.
 */

public class User implements TicTacToePlayer {
    private final IntInputReader inputReader;

    public User(IntInputReader inputReader) {
        this.inputReader = inputReader;
    }

    @Override
    public Cell makeMove(TicTacToeBoard<?> board) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
