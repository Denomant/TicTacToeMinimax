package TicTacToe.player;

import TicTacToe.model.Cell;

public record PlayerAction(Cell move, boolean isUndoMove) {
    public static final PlayerAction undo = new PlayerAction(null, true);

    public PlayerAction(Cell move) {
        this(move, false);
    }
}
