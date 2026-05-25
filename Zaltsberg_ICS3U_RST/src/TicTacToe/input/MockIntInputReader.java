package TicTacToe.input;

/**
 * A mock implementation of IntInputReader for testing purposes. <br>
 * MockIntInputReader.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public class MockIntInputReader implements IntInputReader {
    private final int[] inputs;
    private int currentIndex;

    /**
     * Constructor for MockIntInputReader. <br>
     * @param inputs An array of integers that will be returned sequentially when readInt is called.
     */
    public MockIntInputReader(int[] inputs) {
        this.inputs = inputs;
        this.currentIndex = 0;
    }

    /**
     * Returns the next integer in the inputs array each time it is called. <br>
     * @param prompt The prompt to display to the user (Used for debugging).
     * @return The next integer in the inputs array.
     * @throws IndexOutOfBoundsException if readInt is called more times than the length of the inputs array.
     */
    @Override
    public int readInt(String prompt) {
        if (currentIndex < inputs.length) {
            return inputs[currentIndex++];
        } else {
            throw new IndexOutOfBoundsException("No more mock inputs available. Prompt was: " + prompt  );
        }
    }
}
