package TicTacToe.javafx;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;
import TicTacToe.player.TicTacToePlayer;
import java.util.concurrent.CompletableFuture;

public class JavaFXPlayer implements TicTacToePlayer {
    private CompletableFuture<Cell> nextMove;

    public JavaFXPlayer(){
        nextMove = new CompletableFuture<Cell>();
    }

    @Override
    public Cell makeMove(TicTacToeBoard<?> board) {
        Cell move = nextMove.join(); // Wait for the next move to be set by the JavaFX event handler
        nextMove = new CompletableFuture<Cell>(); // Reset for the next move
        return move;
    }

    public void trigger(Cell cell){
        if (nextMove != null && !nextMove.isDone()){
            nextMove.complete(cell);
        }
    } 
}
