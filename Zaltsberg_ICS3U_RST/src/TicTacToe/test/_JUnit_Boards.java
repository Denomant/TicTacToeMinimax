package TicTacToe.test;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import TicTacToe.Board3x3;
import TicTacToe.Board4x4;
import TicTacToe.TicTacToeBoard;
import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;

/**
 * @author Denis Zaltsberg
 * Date: 18/12/25
 * Course: ICS3U
 * _JUnit_Boards.java
 * A JUnit test for the TicTacToeBoard abstract class behavior, and all of its child classes (3x3, and 4x4). 
*/

public class _JUnit_Boards {
    /* =======================
     * Helper Methods
     * =======================*/
    
    private static Cell[][] generateBoard(int size, Cell[] moves) {
        Cell[][] board = new Cell[size][size];

        // generate empty board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        // apply moves
        if (moves == null) {
            return board;
        }
        for (Cell move : moves) {
            if (move != null) {
                board[move.getRow()][move.getCol()] = new Cell(move.getRow(), move.getCol(), move.getValue());
            }
        }

        return board;
    }

    private void testBoardConstructorsAndGetCells(TicTacToeBoard<?>[] allBoards, int expectedSize) {
        int i = 0;
        for (TicTacToeBoard<?> board : allBoards) {
            Assert.assertNotNull("Board " + i + " should not be null", board);
            Assert.assertEquals("Board " + i + " should have " + expectedSize + " rows", expectedSize, board.getCells().length);
            for (Cell[] row : board.getCells()) {
                Assert.assertNotNull("Row in Board " + i + " should not be null", row);
                Assert.assertEquals("Each row in Board " + i + " should have " + expectedSize + " columns", expectedSize, row.length);
                for (Cell cell : row) {
                    Assert.assertNotNull("Cell in Board " + i + " should not be null", cell);
                    Assert.assertTrue("Cell value should be X, O, or EMPTY (board " + i + ")",
                            cell.getValue() == CellValue.X ||
                            cell.getValue() == CellValue.O ||
                            cell.getValue() == CellValue.EMPTY
                    );
                }
            }
            i++;
        }
    }

    /* =======================
     * Test Boards Constructor
     * =======================*/

    private Board3x3[] boards3x3;
    private Board4x4[] boards4x4;

    @Before
    public void setUp() {
        // Initialize the 3x3 boards
        boards3x3 = new Board3x3[] {
            // Empty board
            new Board3x3(generateBoard(3, null)),

            // Partially filled board
            new Board3x3(generateBoard(3, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(0, 1, CellValue.X)
            })),

            // Horizontal win for X (O moves for convenience)
            new Board3x3(generateBoard(3, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(0, 1, CellValue.X),
                new Cell(2, 2, CellValue.O),
                new Cell(0, 2, CellValue.X)
            })),

            // Diagonal win for O (X moves for convenience)
            new Board3x3(generateBoard(3, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 2, CellValue.O),
                new Cell(1, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(2, 1, CellValue.X),
                new Cell(2, 0, CellValue.O)
            })),

            // Full board, no winner
            new Board3x3(generateBoard(3, new Cell[] {
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
        };

        // Initialize the 4x4 boards
        boards4x4 = new Board4x4[] {
            // Empty board
            new Board4x4(generateBoard(4, null)),

            // Partially filled board
            new Board4x4(generateBoard(4, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(0, 1, CellValue.X),
                new Cell(2, 2, CellValue.O)
            })),

            // Vertical win for X (O moves for convenience)
            new Board4x4(generateBoard(4, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 1, CellValue.O),
                new Cell(1, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 1, CellValue.O),
                new Cell(3, 0, CellValue.X)
            })),

            // Anti-diagonal win for O (X moves for convenience)
            new Board4x4(generateBoard(4, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 3, CellValue.O),
                new Cell(1, 0, CellValue.X),
                new Cell(1, 2, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 1, CellValue.O),
                new Cell(3, 1, CellValue.X),
                new Cell(3, 0, CellValue.O)
            })),

            // Full board, no winner
            new Board4x4(generateBoard(4, new Cell[] {
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
            })),};
    }

    // Test the constructors work as expected before proceeding to other tests

    @Test
    public void testBoard3x3ConstructorsAndGetCells() {
        // Include the default empty constructor once for coverage
        Board3x3 defaultBoard = new Board3x3();
        Board3x3 [] allBoards3x3 = new Board3x3[boards3x3.length + 1];

        System.arraycopy(boards3x3, 0, allBoards3x3, 0, boards3x3.length);
        allBoards3x3[boards3x3.length] = defaultBoard;

        testBoardConstructorsAndGetCells(allBoards3x3, 3);
    }

    @Test
    public void testBoard4x4ConstructorsAndGetCells() {
        // Include the default empty constructor once for coverage
        Board4x4 defaultBoard = new Board4x4();
        Board4x4 [] allBoards4x4 = new Board4x4[boards4x4.length + 1];

        System.arraycopy(boards4x4, 0, allBoards4x4, 0, boards4x4.length);
        allBoards4x4[boards4x4.length] = defaultBoard;

        testBoardConstructorsAndGetCells(allBoards4x4, 4);
    }

    /* ===========================
     * Unimplemented Methods Tests
     * ===========================*/

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsHashCodeUnsupported() {
        boards3x3[0].hashCode();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsEqualsUnsupported() {
        boards3x3[0].equals(boards3x3[1]);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsMoveResultUnsupported() {
        boards3x3[0].moveResult(new Cell(0, 0, CellValue.X));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsGetMirroredVerticallyUnsupported() {
        boards3x3[0].getMirroredVertically();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsGetMirroredHorizontallyUnsupported() {
        boards3x3[0].getMirroredHorizontally();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsGetEmptyCellsUnsupported() {
        boards3x3[0].getEmptyCells();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsGetCurrentPlayerUnsupported() {
        boards3x3[0].getCurrentPlayer();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsIsTerminalUnsupported() {
        boards3x3[0].isTerminal();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testBoardsGetWinnerUnsupported() {
        boards3x3[0].getWinner();
    }
    
    /* ===========================
     * Logical Behavior Tests
     * Uncomment when implemented
     * ===========================*/

    /*

    @Test
    public void testBoardsHashCode() {
        // Create a new empty board and test its hash code with the pregenerated empty boards
        Board3x3 newEmptyBoard3x3 = new Board3x3();
        Assert.assertEquals("New empty 3x3 board should have the same hash code as pregenerated empty board",
                boards3x3[0].hashCode(), newEmptyBoard3x3.hashCode());

        Board4x4 newEmptyBoard4x4 = new Board4x4();
        Assert.assertEquals("New empty 4x4 board should have the same hash code as pregenerated empty board",
                boards4x4[0].hashCode(), newEmptyBoard4x4.hashCode());
    }

    @Test
    public void testBoardsEquals() {
        // Create a new empty board and test its equality with the pregenerated empty boards
        Board3x3 newEmptyBoard3x3 = new Board3x3();
        Assert.assertTrue("New empty 3x3 board should be equal to pregenerated empty board",
                boards3x3[0].equals(newEmptyBoard3x3));

        Board4x4 newEmptyBoard4x4 = new Board4x4();
        Assert.assertTrue("New empty 4x4 board should be equal to pregenerated empty board",
                boards4x4[0].equals(newEmptyBoard4x4));
    }

    @Test
    public void testBoardsMoveResult() {
        /*  Test making a move on an empty board, then:
            1) verify the new state is correct
            2) verify the original board is unchanged
            3) verify the new board is a different object
        *\/
        Board3x3 newBoard3x3 = boards3x3[0].moveResult(new Cell(0, 0));
        Assert.assertEquals("New board after move should have X at (0,0)",
                CellValue.X, newBoard3x3.getCells()[0][0].getValue());
        Assert.assertEquals("Original board should still have EMPTY at (0,0)",
                CellValue.EMPTY, boards3x3[0].getCells()[0][0].getValue());
        Assert.assertNotSame("New board should be a different object than the original board",
                newBoard3x3, boards3x3[0]);

        Board4x4 newBoard4x4 = boards4x4[0].moveResult(new Cell(3, 3));
        Assert.assertEquals("New board after move should have X at (3,3)",
                CellValue.X, newBoard4x4.getCells()[3][3].getValue());
        Assert.assertEquals("Original board should still have EMPTY at (3,3)",
                CellValue.EMPTY, boards4x4[0].getCells()[3][3].getValue());
        Assert.assertNotSame("New board should be a different object than the original board",
                newBoard4x4, boards4x4[0]);
    }

    @Test
    public void testBoardsGetEmptyCells() {
        int [] emptyCellsCount3x3 = new int[] {9, 6, 4, 3, 0};
        for (int i = 0; i < boards3x3.length; i++) {
            Cell[] emptyCells = boards3x3[i].getEmptyCells();
            Assert.assertEquals("Board 3x3 " + i + " should have correct number of empty cells",
                    emptyCellsCount3x3[i], emptyCells.length);
        }

        int [] emptyCellsCount4x4 = new int[] {16, 12, 9, 7, 0};
        for (int i = 0; i < boards4x4.length; i++) {
            Cell[] emptyCells = boards4x4[i].getEmptyCells();
            Assert.assertEquals("Board 4x4 " + i + " should have correct number of empty cells",
                    emptyCellsCount4x4[i], emptyCells.length);
        }

    }

    @Test
    public void testBoardsGetCurrentPlayer() {
        CellValue [] currentPlayers3x3 = new CellValue[] {
            CellValue.X, CellValue.O, null, null, null
        };
        for (int i = 0; i < boards3x3.length; i++) {
            CellValue currentPlayer = boards3x3[i].getCurrentPlayer();
            Assert.assertEquals("Board 3x3 " + i + " should have correct current player",
                    currentPlayers3x3[i], currentPlayer);
        }

        CellValue [] currentPlayers4x4 = new CellValue[] {
            CellValue.X, CellValue.X, null, null, null
        };
        for (int i = 0; i < boards4x4.length; i++) {
            CellValue currentPlayer = boards4x4[i].getCurrentPlayer();
            Assert.assertEquals("Board 4x4 " + i + " should have correct current player",
                    currentPlayers4x4[i], currentPlayer);
        }
    }

    @Test
    public void testBoardsIsTerminal() {
        boolean [] terminalStates3x3 = new boolean[] {
            false, false, true, true, true
        };
        for (int i = 0; i < boards3x3.length; i++) {
            Assert.assertEquals("Board 3x3 " + i + " should have correct terminal state",
                    terminalStates3x3[i], boards3x3[i].isTerminal());
        }

        boolean [] terminalStates4x4 = new boolean[] {
            false, false, true, true, true
        };
        for (int i = 0; i < boards4x4.length; i++) {
            Assert.assertEquals("Board 4x4 " + i + " should have correct terminal state",
                    terminalStates4x4[i], boards4x4[i].isTerminal());
        }
    }

    @Test
    public void testBoardsGetWinner() {
        CellValue [] winners3x3 = new CellValue[] {
            null, null, CellValue.X, CellValue.O, null
        };
        for (int i = 0; i < boards3x3.length; i++) {
            Assert.assertEquals("Board 3x3 " + i + " should have correct winner",
                    winners3x3[i], boards3x3[i].getWinner());
        }

        CellValue [] winners4x4 = new CellValue[] {
            null, null, CellValue.X, CellValue.O, null
        };
        for (int i = 0; i < boards4x4.length; i++) {
            Assert.assertEquals("Board 4x4 " + i + " should have correct winner",
                    winners4x4[i], boards4x4[i].getWinner());
        }
    }
    */
}
