package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstract class representing an immutable Tic-Tac-Toe board. <br>
 * This class provides common functionality for different board sizes. <br>
 * The parameter T is the type of the concrete board class that extends this class (CRTP). <br>
 * TicTacToeBoard.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public abstract class TicTacToeBoard <T extends TicTacToeBoard<T>> {
    protected final Cell[][] cells;

    /**
     * Creates a deep copy of the given 2D array of cells. <br>
     * @param cells The 2D array of cells to copy.
     * @return A deep copy of the given 2D array of cells.
     */
    protected static Cell[][] deepCopyCells(Cell[][] cells) {
        Cell[][] newCells = new Cell[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            // Safe because Cell is immutable
            newCells[i] = cells[i].clone();
        }
        return newCells;
    }

    /**
     * Constructs a TicTacToeBoard with the specified 2D array of cells. <br>
     * @param cells The 2D array of cells to initialize the board with.
     */
    public TicTacToeBoard(Cell[][] cells) {
        // Make a deep copy of cells to ensure immutability
        Cell[][] newCells = deepCopyCells(cells);

        this.cells = newCells;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                hash = 31 * hash + (cell != null ? cell.hashCode() : 0);
            }
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // Early-exit optimization point
        if (this == obj) {
            return true;
        }

        // Check the other object is not null
        if (obj == null) {
            return false;
        }

        // Check the other class is a board
        if (!(obj instanceof TicTacToeBoard<?>)){
            return false;
        }

        // Cast obj to TicTacToeBoards
        TicTacToeBoard<?> other = (TicTacToeBoard<?>) obj;

        return Arrays.deepEquals(cells, other.cells);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                sb.append(cell.getValue().toString().charAt(0)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Normalizes the row and column fields of the given 2D array of cells to match their positions in the array. <br>
     * Normalizes the cells in-place. <br>
     * @param cells The 2D array of cells to normalize.
     */
    protected static void normalizeCellPositions(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, cells[i][j].getValue());
            }
        }
    }

    /**
     * Gets a deep copy of the 2D array of cells representing the board. <br>
     * @return A deep copy of the 2D array of cells representing the board.
     */
    public Cell[][] getCells() {
        // deep copy of the array
        Cell[][] copy = deepCopyCells(cells);
        return copy;
    }


    /**
     * Returns a new board resulting from making the specified move on the current board. <br>
     * @param cell The cell to make the move on.
     * @return A new board resulting from making the specified move on the current board.
     * @throws IllegalArgumentException if the specified cell is not empty on the current board.
     */
    public T moveResult(Cell cell) {
        Cell[][] newCells = getCells();

        if (newCells[cell.getRow()][cell.getCol()].getValue() != CellValue.EMPTY) {
            throw new IllegalArgumentException("Cell is already occupied. Tried to put " + cell + " in board:\n" + this);
        }

        newCells[cell.getRow()][cell.getCol()] = new Cell(cell.getRow(), cell.getCol(), getCurrentPlayer());
        return create(newCells);
    }

    /**
     * Returns a new board resulting from mirroring the current board vertically. <br>
     * Cell[n][m] -> Cell[boardSize-1-n][m] <br>
     * @return A new board resulting from mirroring the current board vertically.
     */
    public T getMirroredVertically() {
        Cell[][] newCells = getCells();
        Cell[] temp;
        
        for (int i = 0; i < newCells.length / 2; i++) {
            temp = newCells[i];
            newCells[i] = newCells[newCells.length - 1 - i];
            newCells[newCells.length - 1 - i] = temp;
        }

        normalizeCellPositions(newCells);

        return create(newCells);
    }

    /**
     * Returns a new board resulting from mirroring the current board horizontally. <br>
     * Cell[n][m] -> Cell[n][boardSize-1-m] <br>
     * @return A new board resulting from mirroring the current board horizontally.
     */
    public T getMirroredHorizontally() {
        Cell[][] newCells = getCells();
        Cell temp;

        for (int i = 0; i < newCells.length; i++) {
            for (int j = 0; j < newCells[i].length / 2; j++) {
                temp = newCells[i][j];
                newCells[i][j] = newCells[i][newCells[i].length - 1 - j];
                newCells[i][newCells[i].length - 1 - j] = temp;
            }
        }

        normalizeCellPositions(newCells);

        return create(newCells);
    }

    /**
     * Returns a new board resulting from rotating the current board 90 degrees clockwise. <br>
     * @return A new board resulting from rotating the current board 90 degrees clockwise.
     */
    public T getRotated90(){
        Cell[][] newCells = getCells();
        int rows = newCells.length;
        int cols = newCells[0].length;
        Cell[][] rotatedCells = new Cell[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedCells[j][rows - 1 - i] = newCells[i][j];
            }
        }

        normalizeCellPositions(rotatedCells);

        return create(rotatedCells);
    }

    /**
     * Returns a new board resulting from rotating the current board 90 degrees clockwise the specified number of times. <br>
     * @param times The number of times to rotate the board 90 degrees clockwise.
     * @return A new board resulting from rotating the current board 90 degrees clockwise the specified number of times.
     */
    public T getRotated90(int times){
        times = ((times % 4) + 4) % 4; // account for negatives

        T result = create(getCells());

        for (int i = 0; i < times; i++){
            result = result.getRotated90();
        }

        return result;
    }

    /**
     * Reflects the given cell horizontally across the vertical center line of the board. <br>
     * Cell[n][m] -> Cell[n][boardSize-1-m] <br>
     * @param cell The cell to reflect.
     * @return A new cell resulting from reflecting the given cell horizontally across the vertical center line of the board. <br>
     *        Returns null if the given cell is null.
     */
    public Cell getCellReflectedHorizontally(Cell cell) {
        if (cell == null) {
            return null;
        }

        int newCol = cells[0].length - 1 - cell.getCol();
        return new Cell(cell.getRow(), newCol, cell.getValue());
    }

    /**
     * Reflects the given cell vertically across the horizontal center line of the board. <br>
     * Cell[n][m] -> Cell[boardSize-1-n][m] <br>
     * @param cell The cell to reflect.
     * @return A new cell resulting from reflecting the given cell vertically across the horizontal center line of the board. <br>
     *        Returns null if the given cell is null.
     */
    public Cell getCellReflectedVertically(Cell cell) {
        if (cell == null) {
            return null;
        }
        
        int newRow = cells.length - 1 - cell.getRow();
        return new Cell(newRow, cell.getCol(), cell.getValue());
    }

    /**
     * Rotates the given cell 90 degrees clockwise around the center of the board. <br>
     * @param cell The cell to rotate.
     * @return A new cell resulting from rotating the given cell 90 degrees clockwise around the center of the board. <br>
     *        Returns null if the given cell is null.
     */
    public Cell getCellRotated90(Cell cell){
        if (cell == null){
            return null;
        }

        int newRow = cell.getCol();
        int newCol = cells.length - 1 - cell.getRow();

        return new Cell(newRow, newCol, cell.getValue());
    }

    /**
     * Rotates the given cell 90 degrees clockwise around the center of the board the specified number of times. <br>
     * @param cell The cell to rotate.
     * @param times The number of times to rotate the cell 90 degrees clockwise.
     * @return A new cell resulting from rotating the given cell 90 degrees clockwise around the center of the board the specified number of times. <br>
     *        Returns null if the given cell is null.
     */
    public Cell getCellRotated90(Cell cell, int times){
        if (cell == null){
            return null;
        }
        times = ((times % 4) + 4) % 4; // account for negatives

        Cell result = cell;

        for (int i = 0; i < times; i++){
            result = getCellRotated90(result);
        }

        return result;
    }

    /**
     * Returns a mapping of all symmetry transformations of the board to the corresponding transformation of the given cell. <br>
     * The keys of the map are the transformed boards. <br>
     * The values are the corresponding transformed cells. <br>
     * @param cell The cell to transform along with the board.
     * @return HashMap<T, Cell> where T are transformed boards, and Cell are the corresponding transformed cells. <br>
     *         If the given cell is null, returns an empty map.
     */
    public HashMap<T, Cell> getAllSymmetryCellMappings(Cell cell){
        HashMap<T, Cell> map = new HashMap<>();
        if (cell == null) return map;

        // Original
        map.put(create(getCells()), cell);

        // Mirrors
        T mirroredH = getMirroredHorizontally();
        map.put(mirroredH, getCellReflectedHorizontally(cell));

        T mirroredV = getMirroredVertically();
        map.put(mirroredV, getCellReflectedVertically(cell));

        T mirroredHV = mirroredH.getMirroredVertically();
        map.put(mirroredHV, getCellReflectedVertically(getCellReflectedHorizontally(cell)));
        
        return map;
    }

    /**
     * Gets an array of all empty cells on the board. <br>
     * @return An array of all empty cells on the board.
     */
    public Cell[] getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<>();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getValue() == CellValue.EMPTY) {
                    emptyCells.add(cell);
                }
            }
        }

        Cell[] emptyCellsArray = new Cell[emptyCells.size()];
        emptyCells.toArray(emptyCellsArray);

        return emptyCellsArray;
    }

    /**
     * Gets the current player based on the counts of X and O on the board. <br>
     * If the game is terminal, returns null. <br>
     * @return The current player (As CellValue), or null if the game is terminal.
     */
    public CellValue getCurrentPlayer() {        
        if (isTerminal()) {
            return null;
        }

        int xCount = 0;
        int oCount = 0;

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getValue() == CellValue.X) {
                    xCount++;
                } else if (cell.getValue() == CellValue.O) {
                    oCount++;
                }
            }
        }

        return (xCount <= oCount) ? CellValue.X : CellValue.O;
    }

    /**
     * Checks if the game is terminal <br>
     * Relies on concrete board implementation of getWinner() to determine if there is a winner or tie. <br>
     * @return true if the game is terminal, false otherwise.
     */
    public boolean isTerminal() {
        // if there is a winner or a tie the game is over.
        return (getWinner() != null);
    }

    /**
     * Gets the winner of the game. <br>
     * @return CellValue.X if X has won, CellValue.O if O has won, CellValue.EMPTY if there is a tie, and null if the game is not terminal. <br>
     */
    abstract public CellValue getWinner();

    /**
     * Factory method to create a new board of the concrete type T with the given cells. <br>
     * @param cells The cells to create the new board with.
     * @return A new board of the concrete type T with the given cells.
     */
    abstract protected T create(Cell[][] cells);
}

