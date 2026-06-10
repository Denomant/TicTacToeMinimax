package TicTacToe.javafx;

import TicTacToe.board.*;
import TicTacToe.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;;

public class JavaFXApp extends Application {
    // Dependencies
    private final JavaFXPlayer player;
    private final JavaFXPrinter printer;
    private final TicTacToeBoard<?> initialBoard;
    
    private GridPane grid;
    private Label turnLabel;
    private Button undoBtn;

    // Colors
    private static final String RGB_GRID_GAPS = "#555555";
    private static final String RGB_BACKGROUND = "#1a1a2e"; 
    private static final String RGB_CELL_IDLE = "#16213e"; 
    private static final String RGB_CELL_HOVER = "#0f3460";
    private static final String RGB_EXIT_RED = "#8c3a3a";
    private static final String RGB_BTN_BACK = "#1f4068";
    private static final String RGB_TEXT = "#eeeeee";
    private static final String FONT = "'Courier New', Courier, monospace";

    // Temp for testing.
    // TODO: Remove
    public JavaFXApp(){
        this.player = new JavaFXPlayer();
        this.printer = new JavaFXPrinter();
        this.initialBoard = new Board3x3();
    }
    /**
     * TODO:
     * @param player
     * @param printer
     * @param initialBoard
     */
    public JavaFXApp(JavaFXPlayer player, JavaFXPrinter printer, TicTacToeBoard<?> initialBoard){
        this.player = player;
        this.printer = printer;
        this.initialBoard = initialBoard;
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();
        root.setFillHeight(true);
        root.setStyle("-fx-background-color: " + RGB_BACKGROUND + ";");

        grid = buildBoardGrid();
        HBox.setHgrow(grid, Priority.ALWAYS);   // consume all remaining width
        printer.connect(grid);

        BorderPane controlPanel = buildControlPanel();

        root.getChildren().addAll(grid, controlPanel);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreenExitHint(""); // Remove hint
        stage.setFullScreen(true);
        stage.setTitle("Tic-Tac-Toe");
        stage.show();
    }

    /**
     * Builds the GridPane representing the board, with buttons for each cell.
     * @return The constructed GridPane for the board, with buttons for each cell.
     */
    private GridPane buildBoardGrid(){
        GridPane grid = new GridPane();
        grid.setHgap(6);
        grid.setVgap(6);
        grid.setPadding(new Insets(6));
        grid.setMinWidth(300);
        grid.setMinHeight(300);
        grid.setStyle("-fx-background-color: " + RGB_GRID_GAPS + ";");

        Cell[][] cells = initialBoard.getCells();
        int rows = cells.length;
        int cols = cells[0].length;

        // Equally-wide columns — always stretch to fill available width
        for (int col = 0; col < cols; col++) {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setHgrow(Priority.ALWAYS);
            columnConstraint.setPercentWidth(100.0 / cols);
            grid.getColumnConstraints().add(columnConstraint);
        }

        // Equally-tall rows — always stretch to fill available height
        for (int row = 0; row < rows; row++) {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setVgrow(Priority.ALWAYS);
            rowConstraint.setPercentHeight(100.0 / rows);
            grid.getRowConstraints().add(rowConstraint);
        }

        // One Button per Cell
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = cells[row][col];

                Button btn = new Button();

                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setStyle(cellStyle(false));
                btn.setOnMouseEntered(e -> btn.setStyle(cellStyle(true)));
                btn.setOnMouseExited(e -> btn.setStyle(cellStyle(false)));
                btn.setOnAction(e -> {
                    player.trigger(cell);
                    btn.setStyle(cellStyle(false)); // Reset hover style after click
                    btn.setOnMouseEntered(null);
                    btn.setOnMouseExited(null);
                });

                grid.add(btn, col, row);
            }
        }

        return grid;
    }

    /**
     * Inline style for a board cell button.
     * @param hovered Whether the mouse is currently hovering over the button. Defines the color.
     * @return The inline CSS style string for a board cell button, based on the hovered state.
     */
    private static String cellStyle(boolean hovered) {
        return "-fx-background-color: " + (hovered ? RGB_CELL_HOVER : RGB_CELL_IDLE) + ";"
             + "-fx-background-radius: 0;"
             + "-fx-cursor: " + (hovered ? "hand" : "auto") + ";";
    }

    private BorderPane buildControlPanel(){
        BorderPane controlPanel = new BorderPane();
        controlPanel.setMinWidth(600);
        controlPanel.setPadding(new Insets(16));

        Button exitBtn = new Button("X");
        exitBtn.setStyle(
                "-fx-font-size: 24px;"
              + "-fx-font-weight: bold;"
              + "-fx-font-family: " + FONT   + ";"
              + "-fx-background-color: " + RGB_EXIT_RED + ";"
              + "-fx-text-fill: " + RGB_TEXT +";"
              + "-fx-padding: 4 10 4 10;"
              + "-fx-cursor: hand;");
        exitBtn.setOnAction(e -> Platform.exit()); // TODO: Tie to saving Minimax
        BorderPane.setAlignment(exitBtn, Pos.TOP_RIGHT);
        controlPanel.setTop(exitBtn);

        // Body
        VBox body = new VBox(24);
        body.setAlignment(Pos.CENTER);

        turnLabel = new Label("It is X's Turn");
        turnLabel.setStyle(
                "-fx-font-size: 20px;"
              + "-fx-font-weight: bold;"
              + "-fx-font-family: " + FONT + ";"
              + "-fx-text-fill: " + RGB_TEXT + ";");
        turnLabel.setWrapText(true);
        turnLabel.setAlignment(Pos.CENTER);

        undoBtn = new Button("Undo Move");
        undoBtn.setMaxWidth(Double.MAX_VALUE);
        undoBtn.setStyle(
                "-fx-font-size: 24px;"
              + "-fx-font-weight: bold;"
              + "-fx-font-family: " + FONT  + ";"
              + "-fx-text-fill: " + RGB_TEXT + ";"
              + "-fx-background-color: " + RGB_BTN_BACK + ";"
              + "-fx-padding: 10 20 10 20;"
              + "-fx-cursor: hand;");
        // TODO: Undo logic

        body.getChildren().addAll(turnLabel, undoBtn);
        controlPanel.setCenter(body);

        return controlPanel;
    }
    public static void main(String[] args){
        launch(args);
    }
}
