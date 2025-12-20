package TicTacToe.input;

import simpleIO.*;

/**
 * @author Denis Zaltsberg
 * Date: 20/12/25
 * Course: ICS3U
 * ConsoleIntInputReader.java
 * An implementation of IntInputReader that reads input from the console.
 */

public class ConsoleIntInputReader implements IntInputReader {
    @Override
    public int readInt(String prompt) {
        return Console.readInt(prompt);
    }
}
