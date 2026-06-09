package TicTacToe.javafx;

import TicTacToe.board.TicTacToeBoard;
import TicTacToe.model.*;
import TicTacToe.board.Board4x4;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

    // Colors
    private final String RGB_GRID_GAPS = "#555555";
    private final String RGB_BACKGROUND = "#1a1a2e"; 
    
    // Temp for testing.
    // TODO: Remove
    public JavaFXApp(){
        this.player = new JavaFXPlayer();
        this.printer = new JavaFXPrinter();
        this.initialBoard = new Board4x4();
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

        BorderPane controlPanel = new BorderPane(); // TODO: Fix stub
        controlPanel.setMinWidth(600);

        root.getChildren().addAll(grid, controlPanel);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreenExitHint(""); // Remove hint
        stage.setFullScreen(true);
        stage.setTitle("Tic-Tac-Toe");
        stage.show();
    }

    private GridPane buildBoardGrid(){
        GridPane grid = new GridPane();
        grid.setHgap(6);
        grid.setVgap(6);
        grid.setPadding(new Insets(6));
        grid.setMinWidth(100);
        grid.setMinHeight(100);
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
                //btn.setStyle(cellStyle(false));
                //btn.setOnMouseEntered(e -> btn.setStyle(cellStyle(true)));
                //btn.setOnMouseExited(e  -> btn.setStyle(cellStyle(false)));
                btn.setOnAction(e -> player.trigger(cell));

                grid.add(btn, col, row);
            }
        }

        return grid;
    }

    public static void main(String[] args){
        launch(args);
    }
}
