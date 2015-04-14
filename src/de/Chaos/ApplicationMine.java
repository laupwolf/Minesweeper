package de.Chaos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Paul Baylan on 4/5/15.
 */
public class ApplicationMine extends Application {

    public static void main(String args[]) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        root.getChildren().add(b);
        Scene scene = new Scene(root, 200, 200);
        primaryStage.setTitle("Titel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
