package TicTacToe.test;

import org.junit.Before;

import org.junit.Assert;
import org.junit.Test;

import TicTacToe.input.*;
import TicTacToe.model.*;
import TicTacToe.board.*;
import TicTacToe.player.*;

/**
 * @author Denis Zaltsberg
 * Date: 20/12/25
 * Course: ICS3U
 * _JUnit_Players.java
 * A JUnit test class for testing player interactions in Tic Tac Toe.
 */

public class _JUnit_Players {
    /* ==============
     * Helper Methods
     * ============== */

    private void testPlayerMovesValid(TicTacToePlayer player) {
        for (TicTacToeBoard<?> board : boards) {
            Cell move = player.makeMove(board);
            if (board.isTerminal()) {
                Assert.assertNull("Player should return null on full board", move);
            } else {
                Assert.assertNotNull("Player should return a move", move);
                // Check that the move is valid
                int row = move.getRow(), col = move.getCol();
                Assert.assertTrue("Player's move row should be within bounds", row >= 0 && row < board.getCells().length);
                Assert.assertTrue("Player's move column should be within bounds", col >= 0 && col < board.getCells().length);
                Assert.assertTrue("Player's move should be EMPTY", board.getCells()[move.getRow()][move.getCol()].isEmpty());
            }
        }
    }

    private TicTacToeBoard<?>[] boards;
    private TicTacToePlayer user, random, minimax;

    @Before
    public void setUp() {
        // User is initialized in individual tests because it requires specific input list
        random = new Random();
        minimax = new Minimax();

        // Generate all boards
        Board3x3[] boards3x3 = _JUnit_Boards.generateBoards3x3();
        Board4x4[] boards4x4 = _JUnit_Boards.generateBoards4x4();

        boards = new TicTacToeBoard[boards3x3.length + boards4x4.length];
        System.arraycopy(boards3x3, 0, boards, 0, boards3x3.length);
        System.arraycopy(boards4x4, 0, boards, boards3x3.length, boards4x4.length);
    }

    /* ==================
     * Test Random Player
     * ================== */

    @Test
    public void testRandomGetMove() {
        Assert.assertNotNull("Player should be initialized", random);

        testPlayerMovesValid(random);
    }

    /* ===================
     * Test Minimax Player
     * =================== */

    @Test
    public void testMinimaxGetMove() {
        Assert.assertNotNull("Player should be initialized", minimax);

        testPlayerMovesValid(minimax);

        // Test win-in-ones for Minimax
        TicTacToeBoard<?>[] winInOneBoards = new TicTacToeBoard<?>[] {
            // 3x3 Horizontal win for X (O moves for convenience)
            new Board3x3(_JUnit_Boards.generateBoard(3, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(0, 1, CellValue.X),
                new Cell(2, 2, CellValue.O)
            })),

            // 3x3 Diagonal win for O (X moves for convenience)
            new Board3x3(_JUnit_Boards.generateBoard(3, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 2, CellValue.O),
                new Cell(1, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(2, 1, CellValue.X)
            })),

            // 4x4 Vertical win for X (O moves for convenience)
            new Board4x4(_JUnit_Boards.generateBoard(4, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 1, CellValue.O),
                new Cell(1, 0, CellValue.X),
                new Cell(1, 1, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 1, CellValue.O),
                new Cell(3, 0, CellValue.X)
            })),

            // 4x4 Anti-diagonal win for O (X moves for convenience)
            new Board4x4(_JUnit_Boards.generateBoard(4, new Cell[] {
                new Cell(0, 0, CellValue.X),
                new Cell(0, 3, CellValue.O),
                new Cell(1, 0, CellValue.X),
                new Cell(1, 2, CellValue.O),
                new Cell(2, 0, CellValue.X),
                new Cell(2, 1, CellValue.O),
                new Cell(3, 1, CellValue.X),
                new Cell(3, 0, CellValue.O)
            })),
        };

        Cell[] expectedMoves = new Cell[] {
            new Cell(0, 2), // 3x3 Horizontal win
            new Cell(2, 0), // 3x3 Diagonal win
            new Cell(3, 0), // 4x4 Vertical win
            new Cell(3, 0)  // 4x4 Anti-diagonal win
        };

        for (int i = 0; i < winInOneBoards.length; i++) {
            TicTacToeBoard<?> board = winInOneBoards[i];
            Cell expectedMove = expectedMoves[i];
            Cell move = minimax.makeMove(board);
            Assert.assertNotNull("Minimax should return a move to win", move);
            Assert.assertEquals("Expected winning move at (" + expectedMove.getRow() + "," + expectedMove.getCol() + ")", expectedMove, move);
        }
    }

    /* ================
     * Test User Player
     * ================ */ 

    @Test
    public void testUserGetMove() {
        // Only 4 predefined moves for testing because 6/10 boards expect null before prompting the user
        // Console expects actual rows and cols instead of indexes
        int[] userInputs = new int[] {
            1, 1,
            3, 3,
            1, 1,
            4, 4
        };

        user = new User(new MockIntInputReader(userInputs));
        Assert.assertNotNull("Player should be initialized", user);
        testPlayerMovesValid(user);

        // Check reprompting on negative, 0, or out of bounds input
        int[] invalidInputs = new int[] {
            // 3x3 board
            -1, 0, 8, 4, 5, // all should be reprompted
            2, // valid
            -1, 0, 8, 4, 5, // all should be reprompted again
            2, // valid

            // 4x4 board
            -1, 0, 8, 5, // all should be reprompted
            4, // valid
            -1, 0, 8, 5, // all should be reprompted again
            2  // valid
        };

        user = new User(new MockIntInputReader(invalidInputs));
        Assert.assertNotNull("Player should be initialized", user);
        Cell move = user.makeMove(new Board3x3());
        Assert.assertEquals("User should eventually provide a valid move after invalid inputs", new Cell(1, 1), move);

        move = user.makeMove(new Board4x4());
        Assert.assertEquals("User should eventually provide a valid move after invalid inputs", new Cell(3, 1), move);

        // Check reprompting on occupied cell
        int[] occupiedInputs = new int[] {
            // 3x3, partially filled
            1, 1, // occupied by X
            2, 2, // occupied by O
            3, 3, // valid
            // 4x4, partially filled
            1, 1, // occupied by X
            2, 2, // occupied by O
            4, 4  // valid
        };

        user = new User(new MockIntInputReader(occupiedInputs));
        Assert.assertEquals("User should eventually provide a valid move after selecting occupied cells", new Cell(2, 2), user.makeMove(boards[1]));
        Assert.assertEquals("User should eventually provide a valid move after selecting occupied cells", new Cell(3, 3), user.makeMove(boards[5]));
    }
}
