package TicTacToe.input;

/**
 * @author Denis Zaltsberg
 * Date: 20/12/25
 * Course: ICS3U
 * MockIntInputReader.java
 * A mock implementation of IntInputReader for testing purposes.
 */

public class MockIntInputReader implements IntInputReader {
    private final int[] inputs;
    private int currentIndex;

    public MockIntInputReader(int[] inputs) {
        this.inputs = inputs;
        this.currentIndex = 0;
    }

    @Override
    public int readInt(String prompt) {
        if (currentIndex < inputs.length) {
            return inputs[currentIndex++];
        } else {
            throw new IndexOutOfBoundsException("No more mock inputs available.");
        }
    }

}
