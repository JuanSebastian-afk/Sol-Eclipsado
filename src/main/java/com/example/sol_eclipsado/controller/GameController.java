package com.example.sol_eclipsado.controller;

import com.example.sol_eclipsado.StageMain;
import com.example.sol_eclipsado.model.GameLogic;
import com.example.sol_eclipsado.model.SecretWord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.Normalizer;

public class GameController {

    @FXML
    private HBox hbLetras;

    @FXML
    private Label lbBooleanResponse;

    @FXML
    private Label lbQuantityFailure;

    @FXML
    private Button btHelp;

    @FXML
    private Label lbQuantityHelp;

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


    @FXML
    public void onActionHelp(){
        if(GameLogic.getIntance().getQuantityAid() > 0){

            int posicionEmpty = GameLogic.getIntance().posicionVaciaLetterFound();

            TextField casilla = (TextField) hbLetras.getChildren().get(posicionEmpty);  //EN ESTA LINEA ESTA EL ERROR

            char letraRevelada = SecretWord.getInstance().getSecretWordArray()[posicionEmpty];

            casilla.setText(String.valueOf(letraRevelada).toLowerCase());  //Transformamos el char en un string.
            casilla.setEditable(false);
            casilla.setStyle("-fx-border-color: gray; " + "-fx-border-width: 2; " +
                    "-fx-border-style: solid; " + "-fx-background-color: #B2E8AE");

            int quantityAid = GameLogic.getIntance().getQuantityAid();
            btHelp.setText("Ayudas (" + quantityAid + ")");
            lbQuantityHelp.setText("Te quedan " + quantityAid + " ayudas");

            GameLogic.getIntance().setQuantityAid(quantityAid - 1);

        }else {
            btHelp.setDisable(false);
            int quantityAid = GameLogic.getIntance().getQuantityAid();
            btHelp.setText("Ayuda (" + quantityAid + ")");
            lbQuantityHelp.setText("Te quedan " + quantityAid + " ayudas");
            btHelp.setStyle("-fx-background-color: grey; " + "-fx-border-color: #1E1E33");
        }
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

                    // Tomamos unicamente la primera letra de la casilla
                    if(casilla.getText().length() > 1){
                        casilla.setText(newValue.substring(0, 1));  //Solo tomamos el primer caracter
                    }

                    if(checkLetter(finalI - 1, newValue.toLowerCase().charAt(0))){
                        casilla.setText(newValue);
                        SecretWord.getInstance().setLettersFound(finalI - 1, newValue.charAt(0));
                        casilla.setEditable(false);
                        //Damos estilo al borde para indicar que se hallo una palabrar correcta
                        casilla.setStyle("-fx-border-color: gray; " + "-fx-border-width: 2; " +
                                "-fx-border-style: solid; " + "-fx-background-color: #B2E8AE");  // Agregar bordes redondeandos a las casillas.
                        lbBooleanResponse.setText("¡Hallaste una letra!");
                        lbBooleanResponse.setStyle("-fx-text-fill: green;");

                        //Comprueba si con esa letra el jugador completo la palabra secreta y muestra la ventan final
                        if(GameLogic.getIntance().win()){
                            GameLogic.getIntance().setWin(true);
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/final-view.fxml"));
                            try {
                                Parent root = loader.load();
                                Stage stage = StageMain.getStage();
                                stage.setScene(new Scene(root));
                                stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        //Pasamos la siguiente casilla si esta ya esta llena
                        if(!newValue.isEmpty()){
                            if(finalI < hbLetras.getChildren().size()){
                                TextField casillaNex = (TextField) hbLetras.getChildren().get(finalI);
                                casillaNex.requestFocus();
                            }
                        }

                    }else {
                        int failure = GameLogic.getIntance().getQuantityFailure();
                        GameLogic.getIntance().setQuantityFailure(failure + 1);

                        lbQuantityFailure.setText("cantidad de fallos: " + (failure+1));
                        lbBooleanResponse.setText("¡Fallaste! " + newValue + " no es correcto ¡sigue intentandolo!");
                        lbBooleanResponse.setStyle("-fx-text-fill: red;");
                        casilla.setStyle("-fx-border-color: red; " + "-fx-border-width: 2; " +
                                "-fx-border-style: solid; " + "-fx-background-color: #FA9B9B");


                        //Comprueba si con ese fallo el jugaor ya perdio o aun le quedan mpas intentos.
                        if(GameLogic.getIntance().losed()){  // carga la ventana final en caso de perder.
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/final-view.fxml"));
                            try {
                                Parent root = loader.load();
                            Stage stage = StageMain.getStage();
                            stage.setScene(new Scene(root));
                            stage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        casilla.clear();
                    }

                }
            });
            hbLetras.getChildren().add(casilla);
        }

    }

    //Este método hace la validación de la letra ingresada antes quitandole los acentos.
    public static boolean checkLetter(int indice, char letraIngresada){
        return normalizer(SecretWord.getInstance().getSecretWordArray()[indice]) == normalizer(letraIngresada);
    }

    public static char normalizer(char c){

        String normalizeLetter = String.valueOf(c);  //Convierte el char en String para poder usar replaceAll
        normalizeLetter = Normalizer.normalize(normalizeLetter, Normalizer.Form.NFD);   // Esta linea utiliza la función "normalize" de la calse Normalizer para separar las letras de sus acentos
        normalizeLetter = normalizeLetter.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "");  //Utiliza la expreción regular para borrar los acentos.

        return normalizeLetter.charAt(0);
    }


}
