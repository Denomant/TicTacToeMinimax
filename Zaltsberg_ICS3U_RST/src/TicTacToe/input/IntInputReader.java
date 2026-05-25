package TicTacToe.input;

/**
 * An interface for reading integer input. <br>
 * IntInputReader.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public interface IntInputReader {
    /**
     * Reads an integer. <br>
     * @param prompt The prompt to display to the user.
     * @return The integer read from the input.
     */
    public int readInt(String prompt);
}
