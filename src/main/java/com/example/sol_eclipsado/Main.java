package com.example.sol_eclipsado;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    @Override
    public void start(Stage primarySatge) throws IOException {

        StageMain.setStage(primarySatge);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/welcome-view.fxml")); //Carga la ventana de bienvenida
        Parent root = loader.load();

        StageMain.getStage().setScene(new Scene(root));  //Carga la escena en la ventana usando una clase anonima
        StageMain.getStage().show();  //Muestra la ventana con la escena cargada
    }

}
