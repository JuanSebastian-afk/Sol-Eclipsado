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
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the welcome screen (initial view).
 * It manages the input of the secret word, validates it according to the game rules,
 * and handles the transition to the main game view or the rules window.
 * @author Juan Sebastian Valencia
 * @version 1.1
 */
public class WelcomeController {

    @FXML
    private TextField txSecretWord;

    @FXML
    private Button btPlay;

    @FXML
    private Button btRulesGame;

    /**
     * Handles the event when the "Play" button is pressed.
     * It captures the input, validates it, and if successful, transitions to the game board.
     * @throws IOException If the FXML file for the game view cannot be loaded.
     */
    @FXML
    public void onActionButtonPlay() throws IOException {
        String inputWord = txSecretWord.getText();

        // Temporarily set to validate, but we only proceed if validateWord returns true
        SecretWord.getInstance().setSecretWord(inputWord);

        if (validateWord()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/game-view.fxml"));
            Parent root = loader.load();

            StageMain.getStage().setScene(new Scene(root));
            StageMain.getStage().show();
        }
    }

    /**
     * Opens a new window displaying the game rules.
     * @throws IOException If the FXML file for the rules view cannot be loaded.
     */
    @FXML
    public void onActionRulesGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/rules-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

    /**
     * Validates the secret word based on three main criteria:
     * 1. No blank spaces allowed.
     * 2. Length must be between 6 and 12 characters.
     * 3. Only Latin alphabet characters (including accents and 'ñ') are allowed.
     * @return {@code true} if the word meets all criteria, {@code false} otherwise.
     */
    public boolean validateWord() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Palabra invalida");

        // Rule 1: No spaces
        if (SecretWord.spacingWordValidation()) {
            alert.setHeaderText("Espacio en blanco detectado");
            alert.setContentText("La palabra secreta no puede contener espacios.");
            alert.showAndWait();
            return false;
        }

        // Rule 2: Size constraint (6-12 letters)
        // Note: sizeValidation() in the model was updated to return true if VALID.
        // So we check if it is NOT valid.
        if (!SecretWord.sizeValidation()) {
            alert.setHeaderText("Tamaño invalido");
            alert.setContentText("La palabra secreta debe contener entre 6 y 12 caractares.");
            alert.showAndWait();
            return false;
        }

        // Rule 3: Character set (Latin only)
        if (!SecretWord.characterValidation()) {
            alert.setHeaderText("Caracteres invalidos");
            alert.setContentText("La palabra secreta solo puede contener letras del alfabeto latino, incluyendo acentos y la 'ñ'");
            alert.showAndWait();
            return false;
        }

        return true;
    }
}