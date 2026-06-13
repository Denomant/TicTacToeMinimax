package TicTacToe.javafx;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.boardprinter.BoardPrinter;
import TicTacToe.model.Cell;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * JavaFX implementation of a BoardPrinter. <br>
 * Updates a connected GridPane of Buttons with X and O images based on the board state. <br>
 * JavaFXPrinter.java
 * @author Denis Zaltsberg
 * @date 12/06/2026
*/

public class JavaFXPrinter implements BoardPrinter{
    private Image X_IMG;
    private Image O_IMG;
    private GridPane grid;

    private ImageView[][] cache; // Cache for ImageViews to avoid creating new ones every render call.
    private Button[][] btnCache; // Cache for Buttons to manage hover effects and actions.
    
    /**
     * Initializes the JavaFXPrinter.
     */
    public JavaFXPrinter(){
    }

    /**
     * Connects the JavaFXPrinter to the GridPane used for displaying the board. <br>
     * Loads the X and O images from the resources <br>
     * @param grid GridPane filled with buttons, used for displaying the board state.
     */
    public void connect(GridPane grid){
        this.grid = grid;
        X_IMG = new Image(getClass().getResource("/images/x.png").toString());
        O_IMG = new Image(getClass().getResource("/images/o.png").toString());
    }

    @Override
    public String render(TicTacToeBoard<?> board) {
        Cell[][] cells = board.getCells();
        int rows = cells.length;
        int cols = cells[0].length;

        if (cache == null || cache.length != rows || cache[0].length != cols) {
            // Initialize cache if it's null or if the board size has changed
            cache = new ImageView[rows][cols];
            btnCache = new Button[rows][cols];
            
            for (Node node : grid.getChildren()){
                if (node instanceof Button btn){
                    // Catch possible null Integer
                    Integer r = GridPane.getRowIndex(btn);
                    Integer c = GridPane.getColumnIndex(btn);
                    int row = (r == null) ? 0 : r;
                    int col = (c == null) ? 0 : c;

                    // Create the ImageView graphic for this button
                    ImageView imgView = new ImageView();
                    imgView.setPreserveRatio(true);
                    imgView.setSmooth(true);

                    // Bind the ImageView's size to the button's size, with padding
                    imgView.fitWidthProperty().bind(btn.widthProperty().multiply(0.9));
                    imgView.fitHeightProperty().bind(btn.heightProperty().multiply(0.9));
                    

                    // Attach it to the button and store it in cache
                    Platform.runLater(() -> btn.setGraphic(imgView)); // Ensure this runs on the JavaFX Application Thread
                    cache[row][col] = imgView;
                    btnCache[row][col] = btn;
                }
            }
        }

        for (int row = 0; row < board.getCells().length; row++) {
            for (int col = 0; col < board.getCells()[row].length; col++) {
                Cell cell = board.getCells()[row][col];
                switch (cell.getValue()) {
                    case X:
                        cache[row][col].setImage(X_IMG);
                        JavaFXApp.buttonHoverLogic(btnCache[row][col], false);
                        break;
                    case O:
                        cache[row][col].setImage(O_IMG);
                        JavaFXApp.buttonHoverLogic(btnCache[row][col], false);
                        break;
                    default:
                        cache[row][col].setImage(null);
                        JavaFXApp.buttonHoverLogic(btnCache[row][col], true);
                        break;
                }
            }
        }
        return ""; // Return an empty string since the actual rendering is done through the GridPane.
    }

}
