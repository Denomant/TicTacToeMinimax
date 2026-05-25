package TicTacToe.input;

import simpleIO.*;

/**
 * A class implementing a console-based input reader for integers. <br>
 * Uses the simpleIO library (Provided by ICS3U & ICS4U) to read input from the console. <br>
 * ConsoleIntInputReader.java
 * @author Denis Zaltsberg
 * @date 24/05/2026
*/

public class ConsoleIntInputReader implements IntInputReader {
    /**
     * @return An integer read from the console after prompting the user with the provided message.
     */
    @Override
    public int readInt(String prompt) {
        return Console.readInt(prompt);
    }
}
