package TicTacToe;

/**
 * @Author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * Board4x4.java
 * A class representing a 4x4 Tic Tac Toe board.
 */

public final class Board4x4 extends TicTacToeBoard<Board4x4> {
    public Board4x4() {
        super(new Cell[4][4]);
    }

    @Override
    boolean isTerminal() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    CellValue getWinner() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
