package TicTacToe.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new Label("SomeText"));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Tic-Tac-Toe");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
