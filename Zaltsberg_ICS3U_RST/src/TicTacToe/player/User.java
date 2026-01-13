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
