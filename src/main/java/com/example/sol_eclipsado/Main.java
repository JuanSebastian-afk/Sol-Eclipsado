package com.example.sol_eclipsado;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main application class that extends JavaFX Application.
 * It initializes the primary stage and loads the initial welcome view.
 * @author Juan Sebastian Valencia
 * @version 1.1
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by setting the primary stage and loading
     * the FXML layout for the welcome screen.
     * @param primaryStage The initial stage provided by the JavaFX runtime.
     * @throws IOException If the welcome-view.fxml file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Register the primary stage in the StageMain manager
        StageMain.setStage(primaryStage);

        // Load the initial welcome view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/welcome-view.fxml"));
        Parent root = loader.load();

        // Configure the scene and display the window
        StageMain.getStage().setTitle("El Sol Eclipsado");
        StageMain.getStage().setScene(new Scene(root));
        StageMain.getStage().show();
    }
}