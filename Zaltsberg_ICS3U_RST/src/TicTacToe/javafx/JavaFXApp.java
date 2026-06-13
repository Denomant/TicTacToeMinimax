package TicTacToe.javafx;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.Cell;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.App;

/**
 * JavaFX application handler for the Tic Tac Toe game. <br>
 * Responsible for rendering the game board, handling user interactions, and displaying Yes/No dialogs. <br>
 * JavaFXApp.java
 * @author Denis Zaltsberg
 * @date 12/06/2026
*/

public class JavaFXApp extends Application {
    // Dependencies
    private static JavaFXPlayer player;
    private static JavaFXPrinter printer;
    private static TicTacToeBoard<?> initialBoard;
    
    private static GridPane grid;
    private static Label turnLabel;
    private static Button undoBtn;

    // Synchronization latch to ensure JavaFX is fully initialized before the game starts
    public static final CountDownLatch startupLatch = new CountDownLatch(1);

    // Colors
    private static final String RGB_GRID_GAPS = "#555555";
    private static final String RGB_BACKGROUND = "#1a1a2e"; 
    private static final String RGB_CELL_IDLE = "#16213e"; 
    private static final String RGB_CELL_HOVER = "#0f3460";
    private static final String RGB_EXIT_RED = "#8c3a3a";
    private static final String RGB_BTN_BACK = "#1f4068";
    private static final String RGB_TEXT = "#eeeeee";
    private static final String FONT = "'Courier New', Courier, monospace";

    /**
     * Default constructor for JavaFXApp. Required for JavaFX applications, but does not perform any initialization logic itself. <br>
     * MUST call setDependencies() before launch() to ensure the JavaFX application has access to the necessary dependencies for rendering and game logic.
     */
    public JavaFXApp(){
    }

    /**
     * Sets the dependencies for the JavaFXApp, including the player, printer, and initial board state. <br>
     * @param player The JavaFXPlayer instance responsible for handling user input and game logic interactions.
     * @param printer The JavaFXPrinter instance responsible for rendering the game board.
     * @param initialBoard The initial state of the Tic-Tac-Toe board.
     */
    public static void setDependencies(JavaFXPlayer p, JavaFXPrinter pr, TicTacToeBoard<?> iB){
        player = p;
        printer = pr;
        initialBoard = iB;
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

        // Signal that JavaFX is ready and the game can start
        startupLatch.countDown(); 
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
                buttonHoverLogic(btn, true);
                btn.setOnAction(e -> {
                    player.trigger(cell);
                    btn.setStyle(cellStyle(false)); // Reset hover style after click
                    buttonHoverLogic(btn, false);
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

    /**
     * Enables or disables hover effects for a given button. <br>
     *  When enabled, the button will change color on mouse hover to indicate interactivity. <br>
     *  When disabled, the button will not respond to mouse hover events and will maintain a static appearance.
     * @param btn The Button for which to enable or disable hover effects.
     * @param isOn Whether to enable (true) or disable (false) hover effects for the button.
     */
    public static void buttonHoverLogic(Button btn, boolean isOn){
        if (isOn){
            btn.setOnMouseEntered(e -> btn.setStyle(cellStyle(true)));
            btn.setOnMouseExited(e -> btn.setStyle(cellStyle(false)));
        } else {
            btn.setOnMouseEntered(null);
            btn.setOnMouseExited(null);
        }
    }

    /**
     * Builds the control panel on the right side of the screen, containing the turn label and undo button.
     * @return The constructed BorderPane for the control panel, with the turn label and undo button.
     */
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
        exitBtn.setOnAction(e -> App.close()); // TODO: Tie to saving Minimax
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
        
        undoBtn.setOnAction(e -> player.triggerUndo());

        body.getChildren().addAll(turnLabel, undoBtn);
        controlPanel.setCenter(body);

        return controlPanel;
    }

    /**
     * Displays a styled Yes/No dialog matching the game's theme.
     * Can be called from any thread.
     */
    public static boolean askYesNoQuestion(String question) {
        AtomicBoolean answer = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);

            VBox vbxDialog = new VBox(30);
            vbxDialog.setAlignment(Pos.CENTER);
            vbxDialog.setPadding(new Insets(40));
            vbxDialog.setStyle(
                "-fx-background-color: " + RGB_BACKGROUND + ";" +
                "-fx-border-color: " + RGB_GRID_GAPS + ";" +
                "-fx-border-width: 2px;");

            Label lblQuestion = new Label(question);
            lblQuestion.setWrapText(true);
            lblQuestion.setAlignment(Pos.CENTER);
            lblQuestion.setStyle(
                "-fx-font-family: " + FONT + ";" +
                "-fx-font-size: 22px;" +
                "-fx-text-fill: " + RGB_TEXT + ";" +
                "-fx-font-weight: bold;");


            HBox hbxButtons = new HBox(20);
            hbxButtons.setAlignment(Pos.CENTER);

            Button btnYes = new Button("Yes");
            setupDialogButton(btnYes, RGB_BTN_BACK);
            btnYes.setOnAction(e -> {
                answer.set(true);
                dialog.close();
                latch.countDown();
            });

            Button btnNo = new Button("No");
            setupDialogButton(btnNo, RGB_EXIT_RED);
            btnNo.setOnAction(e -> {
                answer.set(false);
                dialog.close();
                latch.countDown();
            });

            hbxButtons.getChildren().addAll(btnYes, btnNo);
            vbxDialog.getChildren().addAll(lblQuestion, hbxButtons);
            Scene scene = new Scene(vbxDialog);
            dialog.setScene(scene);
            dialog.showAndWait();
            latch.countDown();
        });

        try {
            latch.await(); // Wait for the user to make a choice
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return answer.get();
    }

    /**
     * Helper to apply consistent styling to dialog buttons.
     * @param btn The Button to style.
     * @param color The background color to apply to the button, matching the game's theme.
     */
    private static void setupDialogButton(Button btn, String color) {
        btn.setMinWidth(120);
        String baseStyle = 
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: " + RGB_TEXT + ";" +
            "-fx-font-family: " + FONT + ";" +
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 0;" +
            "-fx-cursor: hand;";
        
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(baseStyle + "-fx-opacity: 0.8;"));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
    }

    public static void updateTurnLabel(String text){
        Platform.runLater(() -> turnLabel.setText(text));
    }
}
