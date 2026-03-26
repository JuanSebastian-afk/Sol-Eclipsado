package com.example.sol_eclipsado;

import javafx.stage.Stage;

/**
 * Esta clase sera la encargada de contener la ventana principal de l juego
 * su objetivo es evitar que se creen ventana núevas cada que se cambie de
 * vista, utiliza el patron singleton para este fin.
 * */

public class StageMain {

    private static Stage stage;

    //private static StageMain instancia;

    private StageMain(){};

    public static void setStage(Stage stage){
        StageMain.stage = stage;
    }

    public static Stage getStage(){
        if(stage == null){
            stage = new Stage();
        }
        return stage;
    }
}
