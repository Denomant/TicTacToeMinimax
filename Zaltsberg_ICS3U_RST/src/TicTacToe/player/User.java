package TicTacToe.player;

import simpleIO.Console;

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
     * @return a move based on user input. <br>
     * If -1 -1 is entered, the player will return an undo move. <br>
     */
    @Override
    public PlayerAction makeMove(TicTacToeBoard<?> board) {
        if (board.isTerminal()){
            return new PlayerAction(null);
        }

        Cell[][] cellStructure = board.getCells();

        int maxRow = cellStructure.length, maxCol = cellStructure[0].length;

        Cell[] emptyCells = board.getEmptyCells();

        while (true){
            int row, col;

            do {
                row = inputReader.readInt("Enter the row of your move (1-" + maxRow + "):") - 1;
                if (row == -2){ // if user entered -1 it becomes -2 after subtracting 1
                    break;
                }
            } while (row < 0 || row >= maxRow);
            
            do {
                col = inputReader.readInt("Enter the col of your move (1-" + maxCol + "):") - 1;
                if (col == -2){ // if user entered -1 it becomes -2 after subtracting 1
                    break;
                }
            } while (col < 0 || col >= maxCol);

            if (row == -2 && col == -2){
                return PlayerAction.undo;
            }

            // If only one of row or col is -2, it's an invalid move
            if (row == -2 ^ col == -2){
                Console.print("Invalid move. Please try again.\n");
                continue;
            }

            Cell userMove = new Cell(row, col);

            // Check if move is valid
            for (Cell validMove : emptyCells){
                if (validMove.equals(userMove)){
                    return new PlayerAction(userMove);
                }
            }
            Console.print("Invalid move. Please try again.\n");
        }
    }

}
