package TicTacToe.javafx;

import java.util.concurrent.CompletableFuture;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;
import TicTacToe.player.PlayerAction;
import TicTacToe.player.TicTacToePlayer;

/**
 * JavaFX implementation of a TicTacToePlayer. <br>
 * Uses CompletableFuture to wait for user input from the JavaFX interface in the main sequential workflow. <br>
 * JavaFXPlayer.java
 * @author Denis Zaltsberg
 * @date 12/06/2026
*/

public class JavaFXPlayer implements TicTacToePlayer {
    private CompletableFuture<PlayerAction> action;

    public JavaFXPlayer(){
        action = new CompletableFuture<PlayerAction>();
    }

    @Override
    public PlayerAction makeMove(TicTacToeBoard<?> board) {
        Cell[][] cells = board.getCells();
        PlayerAction actualAction;
        int cellR;
        int cellC;
        do {
            actualAction = action.join(); // Wait for the next move to be triggered
            action = new CompletableFuture<PlayerAction>(); // Reset for the next move
            if (actualAction == PlayerAction.undo){
                return PlayerAction.undo;
            } else {
                cellR = actualAction.move().getRow();
                cellC = actualAction.move().getCol();
            }
        } while (cells[cellR][cellC].getValue() != CellValue.EMPTY); // Ensure the move is valid on the current board
        
        return actualAction;
    }

    public void trigger(Cell cell){
        if (action != null && !action.isDone()){
            action.complete(new PlayerAction(cell));
        }
    } 

    public void triggerUndo(){
        if (action != null && !action.isDone()){
            action.complete(PlayerAction.undo);
        }
    }
}
