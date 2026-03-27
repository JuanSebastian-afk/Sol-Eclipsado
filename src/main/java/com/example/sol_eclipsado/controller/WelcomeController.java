package com.example.sol_eclipsado.controller;

import com.example.sol_eclipsado.StageMain;
import com.example.sol_eclipsado.model.SecretWord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class WelcomeController {

    @FXML
    TextField txSecretWord;  //Secret word entered by user.

    @FXML
    Button btPlay;  //Button to start the game, activate word validation and load the game-view.

    @FXML
    Button btRulesGame;  //Button to view the rules of the game.

    @FXML
    public void onActionButtonPlay() throws IOException {
        SecretWord.getInstance().setSecretWord(txSecretWord.getText());

        //Dentro de este condicional se muestran las diferentes alertase en caso de que la cumpla.
        if(validateWord()){
            //mostrar la ventana de juego

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/game-view.fxml"));
            Parent root = loader.load();

            StageMain.getStage().setScene(new Scene(root));
            StageMain.getStage().show();
        }
    }


    @FXML
    public void onActionButtonRulesGame(){
        //Mostrar la ventana de reglas del juego (sobre la ventana de bienvenida)


    }


    public boolean validateWord(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Palabra no valida");

        //Se comprueba que la palabra cumple con las restricciones establecidas
        if(SecretWord.spacingWordValidation()){
            alert.setHeaderText("Espacios en blanco");
            alert.setContentText("La palabra secreta no debe contener espacios en blanco");
            alert.showAndWait();
            return false;
        }else if(SecretWord.sizeValidation()){
            alert.setHeaderText("Tamaño no valido");
            alert.setContentText("La palabra secreta debe tener entre seis y doce letras");
            alert.showAndWait();
            return false;
        }else if (SecretWord.characterValidation()) {
            alert.setHeaderText("Caracteres no validos");
            alert.setContentText("La palabra secreta unicamente puede contener caracteres del alfabeto latino incluyendo acentos");
            alert.showAndWait();
            return false;
        }

        return true;
    }


}
