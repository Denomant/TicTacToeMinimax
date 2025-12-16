package TicTacToe;

/**
 * @Author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * Board3x3.java
 * A class representing a 3x3 Tic Tac Toe board.
 */

public final class Board3x3 extends TicTacToeBoard<Board3x3> {
    public Board3x3() {
        super(new Cell[3][3]);
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
