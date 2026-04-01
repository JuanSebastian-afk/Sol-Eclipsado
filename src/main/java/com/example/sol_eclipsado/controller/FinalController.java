package com.example.sol_eclipsado.controller;

import com.example.sol_eclipsado.StageMain;
import com.example.sol_eclipsado.model.GameLogic;
import com.example.sol_eclipsado.model.SecretWord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the final screen of the game.
 * It displays the final outcome (Win or Loss), shows the corresponding
 * endgame image (full sun or total eclipse), and allows the player
 * to restart the game or exit the application.
 * @author Juan Sebastian Valencia
 * @version 1.1
 */
public class FinalController {

    @FXML
    private Label lbGameResult;

    @FXML
    private Label lbFinalText;

    @FXML
    private Button btReplay;

    @FXML
    private Button btExitGame;

    @FXML
    private ImageView imWin;

    /**
     * Initializes the final view by checking the game state from GameLogic.
     * Sets the appropriate text and images based on whether the player won or lost.
     */
    @FXML
    public void initialize(){

        // Load images for the final state
        Image lostImg = new Image(getClass().getResource(
                "/com/example/sol_eclipsado/Images/total_eclipse.png").toExternalForm());
        Image winImg = new Image(getClass().getResource(
                "/com/example/sol_eclipsado/Images/sun.png").toExternalForm());

        // Check the win condition stored in the Singleton model
        if(GameLogic.getInstance().getWin()){
            lbGameResult.setText("!HALLASTE LA PALABRA SECRETA!");
            lbFinalText.setText("Felicidades! quieres jugar de nuevo?");
            imWin.setImage(winImg);
        } else {
            lbGameResult.setText("EL SOL HA SIDO ECLIPSADO POR COMPLETO");
            lbFinalText.setText("Mejor suerte la proxima vez!");
            imWin.setImage(lostImg);
        }
    }

    /**
     * Resets the game state and returns the user to the welcome screen.
     * It clears previous game data to ensure a fresh start.
     * @throws IOException If the FXML file for the welcome view is missing.
     */
    @FXML
    public void onActionReplay() throws IOException {
        // CRITICAL: Reset game state before replaying
        resetGameState();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/welcome-view.fxml"));
        Parent root = loader.load();

        Stage stage = StageMain.getStage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Closes the application window.
     */
    @FXML
    public void onActionExitGame(){
        Stage stage = (Stage) btExitGame.getScene().getWindow();
        stage.close();
    }

    /**
     * Helper method to clear the data from previous sessions.
     */
    private void resetGameState() {
        GameLogic logic = GameLogic.getInstance();
        logic.setQuantityFailure(0);
        logic.setQuantityAid(3);
        logic.setWin(false);
        // SecretWord will be overwritten in the WelcomeController
    }
}