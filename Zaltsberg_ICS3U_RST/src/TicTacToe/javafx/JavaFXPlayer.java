package TicTacToe.javafx;

import java.util.concurrent.CompletableFuture;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;
import TicTacToe.player.TicTacToePlayer;

/**
 * JavaFX implementation of a TicTacToePlayer. <br>
 * Uses CompletableFuture to wait for user input from the JavaFX interface in the main sequential workflow. <br>
 * JavaFXPlayer.java
 * @author Denis Zaltsberg
 * @date 12/06/2026
*/

public class JavaFXPlayer implements TicTacToePlayer {
    private CompletableFuture<Cell> nextMove;

    public JavaFXPlayer(){
        nextMove = new CompletableFuture<Cell>();
    }

    @Override
    public Cell makeMove(TicTacToeBoard<?> board) {
        Cell[][] cells = board.getCells();
        Cell move;
        int cellR;
        int cellC;
        do {
            move = nextMove.join(); // Wait for the next move to be triggered
            nextMove = new CompletableFuture<Cell>(); // Reset for the next move
            cellR = move.getRow();
            cellC = move.getCol();
        } while (cells[cellR][cellC].getValue() != CellValue.EMPTY); // Ensure the move is valid on the current board
        
        return move;
    }

    public void trigger(Cell cell){
        if (nextMove != null && !nextMove.isDone()){
            nextMove.complete(cell);
        }
    } 
}
