package TicTacToe.player;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;

/**
 * A class implementing a random Tic Tac Toe player who makes a move randomly. <br>
 * Random.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public class Random implements TicTacToePlayer {
    /* No constructor needed since there are no instance variables. */
    
    /**
     * @return a random valid move from the board.
     */
    @Override
    public PlayerAction makeMove(TicTacToeBoard<?> board) {
        if (board.isTerminal()){
            return new PlayerAction(null);
        }

        Cell[] emptyCells = board.getEmptyCells();
        int randomIndex = (int) (Math.random() * emptyCells.length);
        return new PlayerAction(emptyCells[randomIndex]);
    }

}
