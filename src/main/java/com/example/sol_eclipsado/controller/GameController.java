package com.example.sol_eclipsado.controller;

import com.example.sol_eclipsado.model.SecretWord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameController {

    @FXML
    private HBox hbLetras;

    @FXML
    Label lbBooleanResponse;


    @FXML
    public  void initialize(){  //Generamos las casillas cuando inicializamos el fxml
        generateCasillas();
    }


    @FXML
    public void onActionRulesGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/rules-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }


    /*
    * Este método se encargara de crear las casillas donde el jugador introducira
    * las letras de la palabra secreta
    */
    public void generateCasillas(){

        int quantyCasillas = SecretWord.getInstance().getSecretWord().length();
        hbLetras.setSpacing(10);

        for(int i = 1; i <= quantyCasillas; i++){

            /*
            * Este código genera las casillas correspondientes a cada letra de la palabra secreta
            * */
            TextField casilla = new TextField();  // Creamos una casilla por cada letra que tenga la palabra.
            casilla.setPrefWidth(40);
            casilla.setPrefHeight(40);
            casilla.setAlignment(Pos.CENTER);

            /*
            * Este código agrega un Listener a cada casilla para que reciba los cambios en el
            * texto ingresados en la casilla correspondiente, la idea es que a travez de este
            * Listener se verifique si la letra ingresada coresponde a la letra en palabra
            * secreta o no
            * */
            int finalI = i; //Para poder acceder al indice desde el método del Listener
            casilla.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

                    //newValue = newValue.toLowerCase();  //Para que no importe si el usuario ingreso una mayuscula o miniscula

                    /*
                    * Limitamos la cantidad de letras que se pueden ingresar en una casilla a 1.
                    * ESTE MÉTODO ESGTA PENDIENTE SU IMPLEMENTACIÓN.
                    * */
                    /*if(casilla.getText().length() > 1){
                        casilla.setEditable(false);  //Para que no modifique el texto si la cantidad de caracteres ingresados es mayor a 1.
                    }*/
                    if(casilla.getText().length() == 1 && checkLetter(finalI - 1, newValue.toLowerCase().charAt(0))){
                        casilla.setText(newValue);
                        SecretWord.getInstance().setLettersFound(finalI, newValue.charAt(0));
                        casilla.setEditable(false);
                        //Damos estilo al borde para indicar que se hallo una palabrar correcta
                        casilla.setStyle("-fx-border-color: gray; " + "-fx-border-width: 2; " +
                                "-fx-border-style: solid; " + "-fx-background-color: #B2E8AE");  // Agregar bordes redondeandos a las casillas.
                        lbBooleanResponse.setText("¡Hallaste una letra!");
                    }else {
                        //casilla.clear();
                        lbBooleanResponse.setText("¡Fallaste !sigue intentandolo!");
                    }

                }
            });
            hbLetras.getChildren().add(casilla);
        }

    }


    public static boolean checkLetter(int indice, char letraIngresada){
        return SecretWord.getInstance().getSecretWordArray()[indice] == letraIngresada;
    }


}
