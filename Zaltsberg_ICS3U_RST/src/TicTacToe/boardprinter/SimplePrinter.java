package TicTacToe.boardprinter;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.*;

/**
 * A class for printing the Tic Tac Toe board using spaces to separate cells. <br>
 * SimplePrinter.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
 */

/*
 * Output format:
 * 1 2 3
 * X 5 6
 * 7 O 9
*/

public final class SimplePrinter implements BoardPrinter{
    /**
     * @return A string representation of the board, with spaces between cells and newlines between rows.
     */
    @Override
    public String render(TicTacToeBoard<?> board) {
        StringBuilder result = new StringBuilder("");
        Cell[][] cells = board.getCells();

        int counter = 0;
        for (Cell[] cellRow : cells){
            result.append(" ");
            for (Cell cell : cellRow){
                counter += 1;

                if (cell.getValue() == CellValue.EMPTY) {
                    // Align double digit numbers
                    if (counter < 10) {
                        result.append(" ");
                    }
                    result.append(counter);
                } else {
                    result.append(" ").append(cell.getValueString());
                }

                result.append(" "); // space between cells
            }
            result.append("\n");
        }
        
        // Remove last newline
        result.setLength(result.length() - 1);

        return result.toString();
    }
}

