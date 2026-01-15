package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * TicTacToeBoard.java
 * An abstract class representing the general Tic Tac Toe board.
 */

public abstract class TicTacToeBoard <T extends TicTacToeBoard<T>> {
    protected final Cell[][] cells;

    // Helper
    protected static Cell[][] deepCopyCells(Cell[][] cells) {
        Cell[][] newCells = new Cell[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            // Safe because Cell is immutable
            newCells[i] = cells[i].clone();
        }
        return newCells;
    }

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

    protected static void normalizeCellPositions(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, cells[i][j].getValue());
            }
        }
    }

    public Cell[][] getCells() {
        // deep copy of the array
        Cell[][] copy = deepCopyCells(cells);
        return copy;
    }

    public T moveResult(Cell cell) {
        Cell[][] newCells = getCells();

        if (newCells[cell.getRow()][cell.getCol()].getValue() != CellValue.EMPTY) {
            throw new IllegalArgumentException("Cell is already occupied. Tried to put " + cell + " in board:\n" + this);
        }

        newCells[cell.getRow()][cell.getCol()] = new Cell(cell.getRow(), cell.getCol(), getCurrentPlayer());
        return create(newCells);
    }

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

    public T getRotated90(int times){
        times = ((times % 4) + 4) % 4; // account for negatives

        T result = create(getCells());

        for (int i = 0; i < times; i++){
            result = result.getRotated90();
        }

        return result;
    }

    public Cell getCellReflectedHorizontally(Cell cell) {
        if (cell == null) {
            return null;
        }

        int newCol = cells[0].length - 1 - cell.getCol();
        return new Cell(cell.getRow(), newCol, cell.getValue());
    }

    public Cell getCellReflectedVertically(Cell cell) {
        if (cell == null) {
            return null;
        }
        
        int newRow = cells.length - 1 - cell.getRow();
        return new Cell(newRow, cell.getCol(), cell.getValue());
    }

    public Cell getCellRotated90(Cell cell){
        if (cell == null){
            return null;
        }

        int newRow = cell.getCol();
        int newCol = cells.length - 1 - cell.getRow();

        return new Cell(newRow, newCol, cell.getValue());
    }

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

        // Rotations
        for (int i = 1; i <= 3; i++) {
            T rotated = getRotated90(i);
            Cell rotatedCell = getCellRotated90(cell, i);
            map.put(rotated, rotatedCell);

            // Mirrored rotations
            T rotatedH = rotated.getMirroredHorizontally();
            map.put(rotatedH, rotatedH.getCellReflectedHorizontally(rotatedCell));

            T rotatedV = rotated.getMirroredVertically();
            map.put(rotatedV, rotatedV.getCellReflectedVertically(rotatedCell));
        }

        return map;
    }

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

    public boolean isTerminal() {
        // if there is a winner or a tie the game is over.
        return (getWinner() != null);
    }

    abstract public CellValue getWinner();

    abstract protected T create(Cell[][] cells);
}

