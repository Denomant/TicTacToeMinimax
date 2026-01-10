package TicTacToe.test;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import TicTacToe.board.Board3x3;
import TicTacToe.board.Board4x4;
import TicTacToe.board.TicTacToeBoard;
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
    /* ==============
     * Helper Methods
     * ==============*/
    
    private Board3x3[] boards3x3;
    private Board4x4[] boards4x4;

    static Cell[][] generateBoard(int size, Cell[] moves) {
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

    static Board3x3[] generateBoards3x3() {
        return new Board3x3[] {
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
                new Cell(1, 2, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 2, CellValue.O),
                new Cell(2, 1, CellValue.X)
            })),
        };
    }

    static Board4x4[] generateBoards4x4() {
        return new Board4x4[] {
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
                new Cell(1, 2, CellValue.X),
                new Cell(1, 3, CellValue.O),
                new Cell(1, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 1, CellValue.O),
                new Cell(2, 2, CellValue.X),
                new Cell(2, 3, CellValue.O),
                new Cell(3, 3, CellValue.X),
                new Cell(3, 2, CellValue.O),
                new Cell(3, 1, CellValue.X),
                new Cell(3, 0, CellValue.O)
            })),
        };
    }

    @Before
    public void setUp() {
        boards3x3 = generateBoards3x3();
        boards4x4 = generateBoards4x4();
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

    @FunctionalInterface
    private interface BoardMirror{
        TicTacToeBoard<?> mirror(TicTacToeBoard<?> board);
    }

    private void testMirroringSharedBehavior(String boardMirrorName, BoardMirror mirrorFunction) {
        // Mirroring an empty board should return a board that would be equal to the original board, but different object
        TicTacToeBoard<?> mirroredBoard = mirrorFunction.mirror(boards3x3[0]);
        Assert.assertNotSame(boardMirrorName + ": Mirrored empty 3x3 board should be a different object than the original board",
                mirroredBoard, boards3x3[0]);
        Assert.assertEquals(boardMirrorName + ": Mirrored empty 3x3 board should be equal to the original board",
                mirroredBoard, boards3x3[0]);

        mirroredBoard = mirrorFunction.mirror(boards4x4[0]);
        Assert.assertNotSame(boardMirrorName + ": Mirrored empty 4x4 board should be a different object than the original board",
                mirroredBoard, boards4x4[0]);
        Assert.assertEquals(boardMirrorName + ": Mirrored empty 4x4 board should be equal to the original board",
                mirroredBoard, boards4x4[0]);

        // Double mirroring contract check
        for (int i = 0; i < boards3x3.length; i++) {
            mirroredBoard = mirrorFunction.mirror(mirrorFunction.mirror(boards3x3[i]));
            Assert.assertEquals(boardMirrorName + ": Double mirrored Board3x3 " + i + " should equal the original board",
                    boards3x3[i], mirroredBoard);

            mirroredBoard = mirrorFunction.mirror(mirrorFunction.mirror(boards4x4[i]));
            Assert.assertEquals(boardMirrorName + ": Double mirrored Board4x4 " + i + " should equal the original board",
                    boards4x4[i], mirroredBoard);
        }
    }

    /* =======================
     * Test Boards Constructor
     * =======================*/


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

    @Test
    public void testBoardsHashCode() {
        // Re-generate all boards, and test that their hashes are euqal, despite them being diffrent objects
        Board3x3[] regeneratedBoards3x3 = generateBoards3x3();
        Board4x4[] regeneratedBoards4x4 = generateBoards4x4();

        for (int i = 0; i < boards3x3.length; i++) {
            // Ensure they are different objects
            Assert.assertNotSame("Board3x3 " + i + " should be a different object than its regenerated counterpart",
                    boards3x3[i], regeneratedBoards3x3[i]);
            Assert.assertNotSame("Board4x4 " + i + " should be a different object than its regenerated counterpart",
                    boards4x4[i], regeneratedBoards4x4[i]);

            // Test that their hash codes are equal
            Assert.assertEquals("Board3x3 " + i + " should have the same hash code as its regenerated counterpart",
                    boards3x3[i].hashCode(), regeneratedBoards3x3[i].hashCode());
            Assert.assertEquals("Board4x4 " + i + " should have the same hash code as its regenerated counterpart",
                    boards4x4[i].hashCode(), regeneratedBoards4x4[i].hashCode());
        }
    }

    @Test
    public void testBoardsEquals() {
        // Regenerate all boards, and test that they are equal, depite them being diffrent objects
        Board3x3[] regeneratedBoards3x3 = generateBoards3x3();
        Board4x4[] regeneratedBoards4x4 = generateBoards4x4();

        for (int i = 0; i < boards3x3.length; i++) {
            // Ensure they are different objects
            Assert.assertNotSame("Board3x3 " + i + " should be a different object than its regenerated counterpart",
                    boards3x3[i], regeneratedBoards3x3[i]);
            Assert.assertNotSame("Board4x4 " + i + " should be a different object than its regenerated counterpart",
                    boards4x4[i], regeneratedBoards4x4[i]);

            // Test that they are equal
            Assert.assertEquals("Board3x3 " + i + " should be equal to its regenerated counterpart",
                    boards3x3[i], regeneratedBoards3x3[i]);
            Assert.assertEquals("Board4x4 " + i + " should be equal to its regenerated counterpart",
                    boards4x4[i], regeneratedBoards4x4[i]);

            // Test that they are not equal to null or different types
            Assert.assertNotEquals("Board3x3 " + i + " should not be equal to null",
                    boards3x3[i], null);
            Assert.assertNotEquals("Board3x3 " + i + " should not be equal to an object of a different type",
                    boards3x3[i], "NOT BOARD");
            Assert.assertNotEquals("Board4x4 " + i + " should not be equal to null",
                    boards4x4[i], null);
            Assert.assertNotEquals("Board4x4 " + i + " should not be equal to an object of a different type",
                    boards4x4[i], "NOT BOARD");

            // Test that different boards are not equal
            if (i < boards3x3.length - 1) {
                Assert.assertNotEquals("Board3x3 " + i + " should not be equal to Board3x3 " + (i + 1),
                        boards3x3[i], boards3x3[i + 1]);
                Assert.assertNotEquals("Board4x4 " + i + " should not be equal to Board4x4 " + (i + 1),
                        boards4x4[i], boards4x4[i + 1]);
            }
        }
    }

    @Test
    public void testBoardsMoveResult() {
        /*  Test making a move on an empty board, then:
            1) verify the new state is correct
            2) verify the original board is unchanged
            3) verify the new board is a different object
        */
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
    public void testBoardsGetMirroredVertically() {
        testMirroringSharedBehavior("Vertical Mirroring", TicTacToeBoard::getMirroredVertically);

        /* Expected Vertical Mirroring Behavior:
        board at index 1
        X X 3    1 X X
        4 O 5 -> 4 O 6
        6 7 8    7 8 9

        X X 3 4       1 2 X X
        5 O 7 8    -> 5 6 O 8
        9 10 O 12     9 O 11 12
        13 14 15 16   13 14 15 16

        Test:
        1) Mirrored board matches expected layout
        2) Original board remains unchanged
        3) Mirrored board is a different object than the original board
        */
        Board3x3 expectedBoard3x3 = new Board3x3(generateBoard(3, new Cell[] {
            new Cell(0, 1, CellValue.X),
            new Cell(1, 1, CellValue.O),
            new Cell(0, 2, CellValue.X)
        }));
        Board4x4 expectedBoard4x4 = new Board4x4(generateBoard(4, new Cell[] {
            new Cell(0, 2, CellValue.X),
            new Cell(1, 2, CellValue.O),
            new Cell(0, 3, CellValue.X),
            new Cell(2, 1, CellValue.O)
        }));

        Board3x3 mirroredBoard3x3 = boards3x3[1].getMirroredHorizontally();
        Assert.assertEquals("Mirrored Board3x3 at index 1 should match expected layout",
                expectedBoard3x3, mirroredBoard3x3);
        Assert.assertNotEquals("Original Board3x3 at index 1 should remain unchanged",
                expectedBoard3x3, boards3x3[1]);
        Assert.assertNotSame("Mirrored Board3x3 at index 1 should be a different object than the original board",
                mirroredBoard3x3, boards3x3[1]);

        Board4x4 mirroredBoard4x4 = boards4x4[1].getMirroredHorizontally();
        Assert.assertEquals("Mirrored Board4x4 at index 1 should match expected layout",
                expectedBoard4x4, mirroredBoard4x4);
        Assert.assertNotEquals("Original Board4x4 at index 1 should remain unchanged",
                expectedBoard4x4, boards4x4[1]);
        Assert.assertNotSame("Mirrored Board4x4 at index 1 should be a different object than the original board",
                mirroredBoard4x4, boards4x4[1]);
    }
    
    @Test
    public void testBoardsGetMirroredHorizontally() {
        testMirroringSharedBehavior("Horizontal Mirroring", TicTacToeBoard::getMirroredHorizontally);

        /* Expected Mirroring Behavior:
        board at index 1
        X X 3    1 2 3
        4 O 5 -> 4 O 6
        6 7 8    X X 9

        X X 3 4       1 2 3 4
        5 O 7 8    -> 5 6 O 8
        9 10 O 12     9 O 11 12
        13 14 15 16   X X 15 16
        Test:
        1) Mirrored board matches expected layout
        2) Original board remains unchanged
        3) Mirrored board is a different object than the original board
        */

        Board3x3 expectedBoard3x3 = new Board3x3(generateBoard(3, new Cell[] {
            new Cell(2, 0, CellValue.X),
            new Cell(1, 1, CellValue.O),
            new Cell(2, 1, CellValue.X)
        }));

        Board4x4 expectedBoard4x4 = new Board4x4(generateBoard(4, new Cell[] {
            new Cell(3, 0, CellValue.X),
            new Cell(1, 2, CellValue.O),
            new Cell(3, 1, CellValue.X),
            new Cell(2, 1, CellValue.O)
        }));

        Board3x3 mirroredBoard3x3 = boards3x3[1].getMirroredVertically();
        Assert.assertEquals("Mirrored Board3x3 at index 1 should match expected layout",
                expectedBoard3x3, mirroredBoard3x3);
        Assert.assertNotEquals("Original Board3x3 at index 1 should remain unchanged",
                expectedBoard3x3, boards3x3[1]);
        Assert.assertNotSame("Mirrored Board3x3 at index 1 should be a different object than the original board",
                mirroredBoard3x3, boards3x3[1]);

        Board4x4 mirroredBoard4x4 = boards4x4[1].getMirroredVertically();
        Assert.assertEquals("Mirrored Board4x4 at index 1 should match expected layout",
                expectedBoard4x4, mirroredBoard4x4);
        Assert.assertNotEquals("Original Board4x4 at index 1 should remain unchanged",
                expectedBoard4x4, boards4x4[1]);
        Assert.assertNotSame("Mirrored Board4x4 at index 1 should be a different object than the original board",
                mirroredBoard4x4, boards4x4[1]);
    }

    @Test
    public void testBoardsGetEmptyCells() {
        int [] emptyCellsCount3x3 = new int[] {9, 6, 4, 3, 0};
        for (int i = 0; i < boards3x3.length; i++) {
            Cell[] emptyCells = boards3x3[i].getEmptyCells();
            Assert.assertEquals("Board 3x3 " + i + " should have correct number of empty cells",
                    emptyCellsCount3x3[i], emptyCells.length);
        }

        int [] emptyCellsCount4x4 = new int[] {16, 12, 9, 8, 0};
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
            null, null, CellValue.X, CellValue.O, CellValue.EMPTY
        };
        for (int i = 0; i < boards3x3.length; i++) {
            Assert.assertEquals("Board 3x3 " + i + " should have correct winner",
                    winners3x3[i], boards3x3[i].getWinner());
        }

        CellValue [] winners4x4 = new CellValue[] {
            null, null, CellValue.X, CellValue.O, CellValue.EMPTY
        };
        for (int i = 0; i < boards4x4.length; i++) {
            Assert.assertEquals("Board 4x4 " + i + " should have correct winner",
                    winners4x4[i], boards4x4[i].getWinner());
        }
    }
}
