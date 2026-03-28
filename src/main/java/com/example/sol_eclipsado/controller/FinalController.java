package com.example.sol_eclipsado.controller;

import com.example.sol_eclipsado.StageMain;
import com.example.sol_eclipsado.model.GameLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FinalController {

    @FXML
    Label lbGameResult;

    @FXML
    Label lbFinalText;

    @FXML
    Button btReplay;

    @FXML
    Button btExitGame;

    @FXML
    public void initialize(){
        if(GameLogic.getIntance().getWin()){
            lbGameResult.setText("¡HALLASTE LA PALABRA SECRETA!");
            lbFinalText.setText("¡Felisidades! ¿te animas a intentarlo de nuevo?");
        }
    }

    @FXML
    public void onActionReplay() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/welcome-view.fxml"));
        Parent root = loader.load();

        Stage stage = StageMain.getStage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void onActionExitGame(){
        Stage stage = (Stage) btExitGame.getScene().getWindow();
        stage.close();
    }




}
