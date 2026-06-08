package TicTacToe.player;

/**
 * A class extending Minimax with persistent memory across game launches. <br>
 * Relies on regular Minimax implementation for the actual algorithm. <br>
 * Implements custom serialization to save and load the memory to a file. <br>
 * PersistentMinimax.java
 * @author Denis Zaltsberg
 * @date 8/06/2026
*/

public class PersistentMinimax extends Minimax {
    private String filename;

    /**
     * Default constructor for PersistentMinimax. <br>
     * Initializes the Minimax player and loads memory from the default file name. <br>
     */
    public PersistentMinimax() {
        super();
        this.filename = "minimax_memory.dat"; // Default filename
        loadMemory();
    }

    /**
     * Constructor for PersistentMinimax with custom filename. <br>
     * Initializes the Minimax player and loads memory from the specified file name. <br>
     * @param filename The name of the file to load memory from and save memory to.
     */
    public PersistentMinimax(String filename) {
        super();
        this.filename = filename;
        loadMemory();
    }

    /**
     * Loads the minimax memory from the specified file. <br>
     */
    public void loadMemory() {
    }

    /**
     * Saves the minimax memory to the specified file. <br>
     */
    public void saveMemory() {
    }
}
