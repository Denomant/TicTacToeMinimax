package TicTacToe.player;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;
import java.util.HashMap;

/**
 * A class implementing the Minimax algorithm for Tic Tac Toe that always chooses the most optimal move. <br>
 * Alpha-beta pruning source: https://en.wikipedias.org/wiki/Alpha%E2%80%93beta_pruning <br>
 * Minimax.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public class Minimax implements TicTacToePlayer {
    // Helper data class
    private static record MoveValue(Cell move, int score) {}
    // Placeholder
    private static final Cell NO_MOVE = null;
    // Memory
    private final HashMap<Integer, MoveValue> memory = new HashMap<>();

    /*=====================
      Helper memory methods
      =====================*/
    /**
     * Encodes the board into an int for use as a key in the memory HashMap. <br>
     * Bitboard encoding of the board. <br>
     * @param board The board to encode. Must be 4x4 or smaller.
     * @return An int representing the board state.
     */
    private static int encodeBoard(TicTacToeBoard<?> board) {
        /*
        Every cell can be represented by 2 bits (3 possible states (X, O, EMPTY) + 1 unused state).
        Therefore, a board with n cells can be represented by 2n bits.
        int has 32 bits, therefore 4x4 (16 cells) board can perfectly fit in an int.
        */
        /*
        Establish:
        EMPTY -> 00
        X -> 01
        O -> 10
        11 not used
        */
        Cell[][] cells = board.getCells();
        if (cells.length * cells[0].length > 16){
            throw new IllegalArgumentException("Board too large to encode in an int: " + cells.length + "x" + cells[0].length);
        }

        int result = 0;
        
        for (Cell[] cellLine : cells){
            for (Cell cell : cellLine){
                result = result << 2; // Empty the right-most bits to free space for this cell
                switch (cell.getValue()) {
                    case EMPTY:
                        break;
                    case X:
                        result |= 0b01;
                        break;
                    case O:
                        result |= 0b10;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected cell value: " + cell.getValue());
                }
            }
        }
        return result;
    }

    /**
     * Puts the given board and move value in memory, along with all of its symmetries. <br>
     * Skips terminal boards since they take up too much space and are not worth storing for efficiency. <br>
     * @param board The board to put in memory.
     * @param moveValue The best move at the current board state and its score.
     */
    private void putInMemory(TicTacToeBoard<?> board, MoveValue moveValue){
        // Terminal boards take too much space, but are not worth storing for efficiency
        if (moveValue.move == NO_MOVE){
            return;
        }

        if (isBoardInMemory(board)){
            return;
        }
        
        HashMap<? extends TicTacToeBoard<?>, Cell> boardsCells = board.getAllSymmetryCellMappings(moveValue.move);

        for(HashMap.Entry<? extends TicTacToeBoard<?>, Cell> entry: boardsCells.entrySet()){
            Cell move = entry.getValue();

            if (entry.getKey().getCells()[move.getRow()][move.getCol()].getValue() != CellValue.EMPTY) {
                throw new RuntimeException("Symmetry mapping points to occupied cell! Board: " + entry.getKey() + ", move: " + move + ". Original board: " + board + ", original move: " + moveValue.move);
            }

            int encoded = encodeBoard(entry.getKey());
            
            memory.put(encoded, new MoveValue(move, moveValue.score));
        }
        
    }

    /**
     * Checks if the given board state is already in memory. <br>
     * @param board The board to check.
     * @return True if the board is in memory, false otherwise.
     */
    private boolean isBoardInMemory(TicTacToeBoard<?> board){
        return memory.containsKey(encodeBoard(board));
    }

    /**
     * Gets the move value for the given board from memory. <br>
     * @param board The board to get the move value for. 
     * @return The best move value for the given board from memory. 
     */
    private MoveValue getFromMemory(TicTacToeBoard<?> board){
        return memory.get(encodeBoard(board));
    }

    /**
     * Minimizer function for the Minimax algorithm. <br>
     * Represents the O player trying to minimize the score. <br>
     * @param board The current board state. 
     * @param alpha The alpha value for alpha-beta pruning. 
     * @param beta The beta value for alpha-beta pruning. 
     * @return The best move value for the given board from memory. 
     */
    private MoveValue minimizer(TicTacToeBoard<?> board, int alpha, int beta) {
        if (isBoardInMemory(board)){
            return getFromMemory(board);
        }

        if (board.isTerminal()){
            // If board is terminal getWinner will never return null
            MoveValue result = new MoveValue(NO_MOVE, board.getWinner().getValue());
            putInMemory(board, result);
            return result;
        }

        Cell[] emptyCells = board.getEmptyCells();
        MoveValue bestMove = new MoveValue(NO_MOVE, Integer.MAX_VALUE);
        boolean isPruned = false;

        for (Cell move : emptyCells){
            MoveValue result = maximizer(board.moveResult(move), alpha, beta);

            if (result.score < bestMove.score){
                bestMove = new MoveValue(move, result.score);
            }

            // Update beta
            beta = Math.min(beta, bestMove.score);

            // Prune if current beta makes this move impossible
            if (beta <= alpha){
                isPruned = true;
                break;
            }
        }

        if (!isPruned){
            putInMemory(board, bestMove);
        }

        return bestMove;
    }

    /**
     * Maximizer function for the Minimax algorithm. <br>
     * Represents the X player trying to maximize the score. <br>
     * @param board The current board state.
     * @param alpha The alpha value for alpha-beta pruning.
     * @param beta The beta value for alpha-beta pruning.
     * @return The best move value for the given board from memory.
     */
    private MoveValue maximizer(TicTacToeBoard<?> board, int alpha, int beta) {
        if (isBoardInMemory(board)){
            return getFromMemory(board);
        }

        if (board.isTerminal()){
            MoveValue result = new MoveValue(NO_MOVE, board.getWinner().getValue());
            putInMemory(board, result);
            return result;
        }

        Cell[] emptyCells = board.getEmptyCells();
        MoveValue bestMove = new MoveValue(NO_MOVE, Integer.MIN_VALUE);
        boolean isPruned = false;

        for (Cell move : emptyCells){
            MoveValue result = minimizer(board.moveResult(move), alpha, beta);

            if (result.score > bestMove.score){
                bestMove = new MoveValue(move, result.score);
            }

            // Update alpha
            alpha = Math.max(alpha, bestMove.score);

            // Prune if current alpha makes this move impossible
            if (alpha >= beta) {
                isPruned = true;
                break;
            }
        }

        if (!isPruned){
            putInMemory(board, bestMove);
        }

        return bestMove;
    }

    /**
     * @return the best move for the current board state using the Minimax algorithm with alpha-beta pruning.
     */
    @Override
    public Cell makeMove(TicTacToeBoard<?> board) {
        if (isBoardInMemory(board)){
            return getFromMemory(board).move;
        }

        if (board.isTerminal()){
            return null;
        }

        int currentPlayerValue = board.getCurrentPlayer().getValue();

        // Default alpha-beta
        int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;

        if (currentPlayerValue == CellValue.X.getValue()){
            return maximizer(board, alpha, beta).move;
        } else {
            return minimizer(board, alpha, beta).move;
        }
    }
}
