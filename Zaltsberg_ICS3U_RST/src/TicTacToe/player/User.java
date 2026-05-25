package TicTacToe.player;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;

import TicTacToe.input.*;

/**
 * A class implementing a user Tic Tac Toe player who makes a move based on console input. <br>
 * May be used with any implementation of IntInputReader. <br>
 * User.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public class User implements TicTacToePlayer {
    private final IntInputReader inputReader;

    /**
     * Constructor for User player. <br>
     * Abstracts the input method. <br>
     * @param inputReader An implementation of IntInputReader to read user input from.
     */
    public User(IntInputReader inputReader) {
        this.inputReader = inputReader;
    }

    /**
     * @return a move based on user input.
     */
    @Override
    public Cell makeMove(TicTacToeBoard<?> board) {
        if (board.isTerminal()){
            return null;
        }

        Cell[][] cellStructure = board.getCells();

        int maxRow = cellStructure.length, maxCol = cellStructure[0].length;

        Cell[] emptyCells = board.getEmptyCells();

        while (true){
            int row, col;

            do {
                row = inputReader.readInt("Enter the row of your move (1-" + maxRow + "):") - 1;
            } while (row < 0 || row >= maxRow);
            
            do {
                col = inputReader.readInt("Enter the col of your move (1-" + maxCol + "):") - 1;
            } while (col < 0 || col >= maxCol);

            Cell userMove = new Cell(row, col);

            // Check if move is valid
            for (Cell validMove : emptyCells){
                if (validMove.equals(userMove)){
                    return userMove;
                }
            }
        }
    }

}
