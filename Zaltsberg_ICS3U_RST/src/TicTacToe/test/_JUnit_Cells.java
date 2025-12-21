package TicTacToe.test;

import org.junit.Assert;
import org.junit.Test;

import TicTacToe.model.Cell;
import TicTacToe.model.CellValue;

/**
 * @author Denis Zaltsberg
 * Date: 17/12/25
 * Course: ICS3U
 * _JUnit_Cells.java
 * A JUnit test class for the Cell and CellValue classes.
*/

public class _JUnit_Cells {
    /* ===============
     * CellValue Tests
     * ===============*/

    @Test
    public void testCellValueGetValue() {
        Assert.assertEquals("CellValue.X.getValue() should return 1", 1, CellValue.X.getValue());
        Assert.assertEquals("CellValue.O.getValue() should return -1", -1, CellValue.O.getValue());
        Assert.assertEquals("CellValue.EMPTY.getValue() should return 0", 0, CellValue.EMPTY.getValue());
    }

    @Test
    public void testCellValueEnumSize() {
        Assert.assertEquals(
                "CellValue should contain exactly 3 constants",
                3,
                CellValue.values().length
        );
    }

    @Test
    public void testCellValueNotNull() {
        Assert.assertNotNull("CellValue.X should not be null", CellValue.X);
        Assert.assertNotNull("CellValue.O should not be null", CellValue.O);
        Assert.assertNotNull("CellValue.EMPTY should not be null", CellValue.EMPTY);
    }

    /* ===========================
     * Unimplemented Methods Tests
     * ===========================*/

    @Test(expected = UnsupportedOperationException.class)
    public void testCellValueGetCharacterUnsupported() {
        CellValue.X.getCharacter();
    }
    
    /* ===========================
     * Logical Behavior Tests
     * Uncomment when implemented
     * ===========================*/

    /* 
    @Test
    public void testCellValueGetCharacter() {
        Assert.assertEquals("CellValue.X.getCharacter() should return 'X'", 'X', CellValue.X.getCharacter());
        Assert.assertEquals("CellValue.O.getCharacter() should return 'O'", 'O', CellValue.O.getCharacter());
        Assert.assertEquals("CellValue.EMPTY.getCharacter() should return ' '", ' ', CellValue.EMPTY.getCharacter());
    }
    */


    /* ============
     * Cell Tests
     * ============*/

    @Test
    public void testCellConstructorsAndGetters() {
        Cell cell1 = new Cell(0, 1, CellValue.X);
        Assert.assertEquals("Cell row should be 0", 0, cell1.getRow());
        Assert.assertEquals("Cell column should be 1", 1, cell1.getCol());
        Assert.assertEquals("Cell value should be CellValue.X", CellValue.X, cell1.getValue());

        Cell cell2 = new Cell(2, 2);
        Assert.assertEquals("Cell row should be 2", 2, cell2.getRow());
        Assert.assertEquals("Cell column should be 2", 2, cell2.getCol());
        Assert.assertEquals("Cell value should be CellValue.EMPTY", CellValue.EMPTY, cell2.getValue());
    }

    @Test
    public void testCellExceptionNegativeIndices() {
        int row_col[][] = { {-1, 0}, {0, -1}, {-1, -1} };
        for (int[] rc : row_col) {
            try{
                Cell cell = new Cell(rc[0], rc[1], CellValue.X);
                cell.getValue(); // to avoid unused variable warning
                Assert.fail("Cell constructor should throw an exception for negative indices: (" + rc[0] + ", " + rc[1] + ")");
            } catch (IllegalArgumentException e) {
                // Expected exception
            } catch (Exception e) {
                Assert.fail("Cell constructor threw wrong exception type for negative indices: (" + rc[0] + ", " + rc[1] + ")");
            }
        }
    }

    /* ===========================
     * Unimplemented Methods Tests
     * ===========================*/

    @Test(expected = UnsupportedOperationException.class)
    public void testCellHashCodeUnsupported() {
        Cell cell = new Cell(0, 0, CellValue.X);
        cell.hashCode();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCellEqualsUnsupported() {
        Cell cell1 = new Cell(0, 0, CellValue.X);
        Cell cell2 = new Cell(0, 0, CellValue.X);
        cell1.equals(cell2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCellGetValueCharacterUnsupported() {
        Cell cell = new Cell(0, 0, CellValue.X);
        cell.getValueCharacter();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCellGetValueStringUnsupported() {
        Cell cell = new Cell(0, 0, CellValue.X);
        cell.getValueString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCellIsEmptyUnsupported() {
        Cell cell = new Cell(0, 0, CellValue.X);
        cell.isEmpty();
    }

    /* ===========================
     * Logical Behavior Tests
     * Uncomment when implemented
     * ===========================*/

    /* 
    @Test
    public void testCellHashCode() {
        Cell cell1 = new Cell(2, 2, CellValue.O);
        Cell cell2 = new Cell(2, 2, CellValue.O);
        Cell cell3 = new Cell(0, 0, CellValue.X);
        Cell cell4 = new Cell(0, 0, CellValue.X);

        Assert.assertEquals("Equal cells should have the same hash code", cell1.hashCode(), cell2.hashCode());
        Assert.assertEquals("Equal cells should have the same hash code", cell3.hashCode(), cell4.hashCode());
    }

    @Test
    public void testCellEquals() {
        Cell cellO1 = new Cell(1, 1, CellValue.O);
        Cell cellO2 = new Cell(1, 1, CellValue.O);
        Cell cellX1 = new Cell(0, 0, CellValue.X);
        Cell cellX2 = new Cell(1, 1, CellValue.X);

        Assert.assertTrue("Cells with same row, col, and value should be equal", cellO1.equals(cellO2));
        Assert.assertFalse("Cells with different row, col, or value should not be equal", cellO1.equals(cellX1));
        Assert.assertFalse("Cells with different row, col, or value should not be equal", cellO1.equals(cellX2));
        Assert.assertFalse("Cells with different row, col, or value should not be equal", cellX1.equals(cellX2));

        Assert.assertFalse("Cell should not be equal to null", cellO1.equals(null));
        Assert.assertFalse("Cell should not be equal to an object of different type", cellO1.equals("Not Cell"));

        // Symmetric test
        Assert.assertTrue("Cells with same row, col, and value should be equal (symmetric)", cellO2.equals(cellO1));
        Assert.assertFalse("Cells with different row, col, or value should not be equal (symmetric)", cellX1.equals(cellO1));
        Assert.assertFalse("Cells with different row, col, or value should not be equal (symmetric)", cellX2.equals(cellO1));
        Assert.assertFalse("Cells with different row, col, or value should not be equal (symmetric)", cellX2.equals(cellX1));

        // Cell should be equal to itself
        Assert.assertTrue("Cell should be equal to itself", cellO1.equals(cellO1));
        Assert.assertTrue("Cell should be equal to itself", cellX2.equals(cellX2));
    }

    @Test
    public void testCellGetValueCharacter() {
        Cell cellX = new Cell(0, 0, CellValue.X);
        Cell cellO = new Cell(1, 1, CellValue.O);
        Cell cellEmpty = new Cell(2, 2, CellValue.EMPTY);

        Assert.assertEquals("CellValue.X should return 'X'", 'X', cellX.getValueCharacter());
        Assert.assertEquals("CellValue.O should return 'O'", 'O', cellO.getValueCharacter());
        Assert.assertEquals("CellValue.EMPTY should return ' '", ' ', cellEmpty.getValueCharacter());
    }

    @Test
    public void testCellGetValueString() {
        Cell cellX = new Cell(0, 0, CellValue.X);
        Cell cellO = new Cell(1, 1, CellValue.O);
        Cell cellEmpty = new Cell(2, 2, CellValue.EMPTY);

        Assert.assertEquals("CellValue.X should return \"X\"", "X", cellX.getValueString());
        Assert.assertEquals("CellValue.O should return \"O\"", "O", cellO.getValueString());
        Assert.assertEquals("CellValue.EMPTY should return \" \"", " ", cellEmpty.getValueString());
    }

    @Test
    public void testCellIsEmpty() {
        Cell cellX = new Cell(0, 0, CellValue.X);
        Cell cellO = new Cell(1, 1, CellValue.O);
        Cell cellEmpty = new Cell(2, 2, CellValue.EMPTY);   

        Assert.assertFalse("Cell with value X should not be empty", cellX.isEmpty());
        Assert.assertFalse("Cell with value O should not be empty", cellO.isEmpty());
        Assert.assertTrue("Cell with value EMPTY should be empty", cellEmpty.isEmpty());
    }
    */
}
