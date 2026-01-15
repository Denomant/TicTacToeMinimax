package TicTacToe.boardprinter;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.*;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * BoxCharacterPrinter.java
 * A class for printing the Tic Tac Toe board using box characters.
 * Output format:
 * ┌───┬───┬───┐
 * │   │   │ O │
 * ├───┼───┼───┤
 * │ X │   │ O │
 * ├───┼───┼───┤
 * │ X │   │ X │
 * └───┴───┴───┘
 */
public final class BoxCharacterPrinter implements BoardPrinter {
    @Override
    public String render(TicTacToeBoard<?> board) {
        StringBuilder result = new StringBuilder();

        Cell[][] cells = board.getCells();
        int row = cells.length, col = cells[0].length;

        // Top border
        result.append('┌');
        for (int c = 0; c < col; c++) {
            result.append("───");
            if (c < col - 1) {
                result.append('┬');
            }
        }
        result.append("┐\n");

        for (int r = 0; r < row; r++) {
            // Actual row
            result.append('│');
            for (int c = 0; c < col; c++) {
                String val = cells[r][c].getValueString();
                result.append(" ").append(val).append(" ");
                result.append('│');
            }
            result.append('\n');

            // Row separator
            if (r < row - 1) {
                result.append('├');
                for (int c = 0; c < col; c++) {
                    result.append("───");
                    if (c < col - 1) result.append('┼');
                }
                result.append("┤\n");
            }
        }

        // Bottom border
        result.append('└');
        for (int c = 0; c < col; c++) {
            result.append("───");
            if (c < col - 1){
                result.append('┴');
            }
        }

        result.append("┘");

        return result.toString();
    }
}
