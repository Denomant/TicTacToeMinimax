package TicTacToe.test;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import TicTacToe.boardprinter.*;
import TicTacToe.board.*;
import TicTacToe.model.*;

/**
 * @author Denis Zaltsberg
 * Date: 20/12/25
 * Course: ICS3U
 * _JUnit_BoardPrinters.java
 * A JUnit test class for testing board printers in Tic Tac Toe.
 */


public class _JUnit_BoardPrinters {
    TicTacToeBoard<?>[] boards;

    @Before
    public void setUp() {
        // Generate all boards
        boards = new TicTacToeBoard<?>[] {
            // Empty 3x3 board
            new Board3x3(),

            // Partially filled 3x3 board
            new Board3x3(_JUnit_Boards.generateBoard(3, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(2, 2, CellValue.X)
                })),

            // Full 3x3 board
            new Board3x3(_JUnit_Boards.generateBoard(3, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 1, CellValue.O),
                new Cell(0, 2, CellValue.X),
                new Cell(1, 0, CellValue.O),
                new Cell(1, 1, CellValue.X),
                new Cell(1, 2, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 1, CellValue.O),
                new Cell(2, 2, CellValue.X)
            })),

            // Empty 4x4 board
            new Board4x4(),

            // Partially filled 4x4 board
            new Board4x4(_JUnit_Boards.generateBoard(4, new Cell[] {
                new Cell(0, 0, CellValue.O),
                new Cell(1, 1, CellValue.X),
                new Cell(2, 2, CellValue.O),
                new Cell(3, 3, CellValue.X)
            })),

            // Full 4x4 board
            new Board4x4(_JUnit_Boards.generateBoard(4, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 1, CellValue.O),
                new Cell(0, 2, CellValue.X),
                new Cell(0, 3, CellValue.O),
                new Cell(1, 3, CellValue.X),
                new Cell(1, 2, CellValue.O),
                new Cell(1, 1, CellValue.X),
                new Cell(1, 0, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 1, CellValue.O),
                new Cell(2, 2, CellValue.X),
                new Cell(2, 3, CellValue.O),
                new Cell(3, 3, CellValue.X),
                new Cell(3, 2, CellValue.O),
                new Cell(3, 1, CellValue.X),
                new Cell(3, 0, CellValue.O)
            }))
        };
    }
    
    @Test
    public void testSimplePrinter() {
        String[] expectedOutputs = {
            // Empty 3x3 board
            " 1 2 3 \n" +
            " 4 5 6 \n" +
            " 7 8 9 \n",

            // Partially filled 3x3 board
            " X 2 3 \n" +
            " 4 O 6 \n" +
            " 7 8 X \n",

            // Full 3x3 board
            " X O X \n" +
            " O X O \n" +
            " X O X \n",

            // Empty 4x4 board
            "  1  2  3  4 \n" +
            "  5  6  7  8 \n" +
            "  9 10 11 12 \n" +
            " 13 14 15 16 \n",

            // Partially filled 4x4 board
            "  O  2  3  4 \n" +
            "  5  X  7  8 \n" +
            "  9 10  O 12 \n" +
            " 13 14 15  X \n",

            // Full 4x4 board
            "  X  O  X  O \n" +
            "  O  X  O  X \n" +
            "  X  O  X  O \n" +
            "  O  X  O  X \n"
        };

        BoardPrinter simplePrinter = new SimplePrinter();

        for (int i = 0; i < boards.length; i++) {
            String rendered = simplePrinter.render(boards[i]);
            Assert.assertEquals("SimplePrinter failed for board index " + i, expectedOutputs[i], rendered);
        }
    }

    @Test
    public void testBoxCharacterPrinter() {
        String[] expectedOutputs = {
            // Empty 3x3 board
            "┌───┬───┬───┐\n" +
            "│ 1 │ 2 │ 3 │\n" +
            "├───┼───┼───┤\n" +
            "│ 4 │ 5 │ 6 │\n" +
            "├───┼───┼───┤\n" + 
            "│ 7 │ 8 │ 9 │\n" +
            "└───┴───┴───┘\n",

            // Partially filled 3x3 board
            "┌───┬───┬───┐\n" +
            "│ X │ 2 │ 3 │\n" +
            "├───┼───┼───┤\n" +
            "│ 4 │ O │ 6 │\n" +
            "├───┼───┼───┤\n" +
            "│ 7 │ 8 │ X │\n" +
            "└───┴───┴───┘\n",

            // Full 3x3 board
            "┌───┬───┬───┐\n" +
            "│ X │ O │ X │\n" +
            "├───┼───┼───┤\n" +
            "│ O │ X │ O │\n" +
            "├───┼───┼───┤\n" +
            "│ X │ O │ X │\n" +
            "└───┴───┴───┘\n",

            // Empty 4x4 board
            "┌───┬───┬───┬───┐\n" +
            "│ 1 │ 2 │ 3 │ 4 │\n" +
            "├───┼───┼───┼───┤\n" +
            "│ 5 │ 6 │ 7 │ 8 │\n" +
            "├───┼───┼───┼───┤\n" +
            "│ 9 │10 │11 │12 │\n" +
            "├───┼───┼───┼───┤\n" +
            "│13 │14 │15 │16 │\n" +
            "└───┴───┴───┴───┘\n",

            // Partially filled 4x4 board
            "┌───┬───┬───┬───┐\n" +
            "│ O │ 2 │ 3 │ 4 │\n" +
            "├───┼───┼───┼───┤\n" +
            "│ 5 │ X │ 7 │ 8 │\n" +
            "├───┼───┼───┼───┤\n" +
            "│ 9 │10 │ O │12 │\n" +
            "├───┼───┼───┼───┤\n" +
            "│13 │14 │15 │ X │\n" +
            "└───┴───┴───┴───┘\n",

            // Full 4x4 board
            "┌───┬───┬───┬───┐\n" +
            "│ X │ O │ X │ O │\n" +
            "├───┼───┼───┼───┤\n" +
            "│ O │ X │ O │ X │\n" +
            "├───┼───┼───┼───┤\n" +
            "│ X │ O │ X │ O │\n" +
            "├───┼───┼───┼───┤\n" +
            "│ O │ X │ O │ X │\n" +
            "└───┴───┴───┴───┘\n"
        };

        BoardPrinter boxPrinter = new BoxCharacterPrinter();

        for (int i = 0; i < boards.length; i++) {
            String rendered = boxPrinter.render(boards[i]);
            Assert.assertEquals("BoxCharacterPrinter failed for board index " + i, expectedOutputs[i], rendered);
        }
    }
}
