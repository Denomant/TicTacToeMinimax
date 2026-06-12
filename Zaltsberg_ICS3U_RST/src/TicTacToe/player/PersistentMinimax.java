package TicTacToe.player;

import TicTacToe.model.*;
import java.io.*;
import java.util.ArrayList;

/**
 * A class extending Minimax with persistent memory across game launches. <br>
 * Relies on regular Minimax implementation for algorithm. <br>
 * Implements custom serialization to save and load the memory to a file. <br>
 * PersistentMinimax.java
 * @author Denis Zaltsberg
 * @date 8/06/2026
*/

// TODO: Before deploying, make memory read-only to fit in a binary executable

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
        try (BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(this.filename))){
            loadMemoryFromStream(fileIn);
        } catch (FileNotFoundException e) {
            System.out.println("No existing minimax memory file found. Starting with an empty memory.");
        } catch (IOException e) {
            System.err.println("Failed to load minimax memory from file: " + this.filename);
            e.printStackTrace();
        }
    }

    /**
     * Saves the minimax memory to the specified file. <br>
     */
    public void saveMemory()  {
        try (BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(this.filename))){
            writeMemoryStream(fileOut);
        } catch (IOException e) {
            System.err.println("Failed to save minimax memory to file: " + this.filename);
            e.printStackTrace();
        }
    }

    private void loadMemoryFromStream(InputStream fileIn) throws IOException {
        memory.clear();
        byte[] buffer = new byte[5]; // 4 bytes for key, 1 byte for MoveValue
        while (fileIn.read(buffer) == 5) {
            // Deserialize the key
            // 0xFF -> 00000000 00000000 00000000 11111111 used as a mask.
            int key = ((buffer[0] & 0xFF) << 24) | ((buffer[1] & 0xFF) << 16) | ((buffer[2] & 0xFF) << 8) | (buffer[3] & 0xFF);

            // Deserialize the MoveValue
            byte moveValue = buffer[4];
            // 0x03 -> 00000000 00000000 00000000 00000011 used as a mask to extract the last 2 bits.
            int row = (moveValue >> 6) & 0x03;
            int col = (moveValue >> 4) & 0x03;
            CellValue cellValue = CellValue.values()[(moveValue >> 2) & 0x03];
            int score = (moveValue & 0x03) - 1;

            Cell move = new Cell(row, col, cellValue);
            memory.put(key, new MoveValue(move, score));
        }
    } 

    /**
     * Serializes the internal HashMap<Integer, MoveValue> memory into a byte array. <br>
     * @return The byte array representing the serialized memory.
     */
    private void writeMemoryStream(OutputStream fileOut) throws IOException{
        for (Integer key : memory.keySet()) {
            // Serialize the key (int: 4 bytes, 1 byte at the time)
            fileOut.write((byte) (key >> 24));
            fileOut.write((byte) (key >> 16));
            fileOut.write((byte) (key >> 8));
            fileOut.write((byte) (key >> 0));

            // Bit-pack MoveValue into 1 byte
            /*
            2 bits for move row (1-4)
            2 bits for move col (1-4)
            2 bits for cell value (X, O, EMPTY)
            2 bits for score (-1, 0, 1)
            */
            MoveValue value = memory.get(key);
            Cell cell = value.move();
            byte moveValue = (byte) ((cell.getRow() << 6) | (cell.getCol() << 4) | (cell.getValue().ordinal() << 2) | (value.score() + 1));
            fileOut.write(moveValue);
        }
    }
}
