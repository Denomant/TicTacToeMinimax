package TicTacToe.board;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author Denis Zaltsberg
 * Date: 16/12/25
 * Course: ICS3U
 * TicTacToeBoard.java
 * An abstract class representing the general Tic Tac Toe board.
 */

public abstract class TicTacToeBoard <T extends TicTacToeBoard<T>> {
    protected final Cell[][] cells;

    TicTacToeBoard(Cell[][] cells) {
        // Make a deep copy of cells to ensure immutability
        Cell[][] newCells = new Cell[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            newCells[i] = cells[i].clone();
        }

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

        // Cast obj to Cell
        TicTacToeBoard<?> other = (TicTacToeBoard<?>) obj;

        return Arrays.deepEquals(cells, other.cells);
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
        Cell[][] copy = new Cell[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            copy[i] = cells[i].clone();
        }
        return copy;
    }

    public T moveResult(Cell cell) {
        Cell[][] newCells = getCells();
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

    // TODO: Add static methods for inversing a cell's position vertically and horizontally

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

