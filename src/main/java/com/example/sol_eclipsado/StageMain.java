package com.example.sol_eclipsado;

import javafx.stage.Stage;

/**
 * Manages the primary stage (window) of the application.
 * This class uses a Singleton-like approach to ensure that a single window
 * is reused throughout the game's lifecycle, preventing the creation of
 * multiple windows when switching between views.
 * * @author Juan Sebastian Valencia
 * @version 1.1
 */
public class StageMain {

    /** The main stage instance of the application. */
    private static Stage stage;

    /**
     * Private constructor to prevent instantiation from other classes.
     */
    private StageMain(){}

    /**
     * Sets the main stage of the program.
     * @param stage The stage to be used as the primary window.
     */
    public static void setStage(Stage stage){
        StageMain.stage = stage;
    }

    /**
     * Retrieves the main stage of the program.
     * If the stage has not been initialized, a new Stage instance is created.
     * @return The primary stage of the application.
     */
    public static Stage getStage(){
        if(stage == null){
            stage = new Stage();
        }
        return stage;
    }
}