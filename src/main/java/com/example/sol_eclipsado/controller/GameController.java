package com.example.sol_eclipsado.controller;

import com.example.sol_eclipsado.StageMain;
import com.example.sol_eclipsado.model.GameLogic;
import com.example.sol_eclipsado.model.SecretWord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.Normalizer;

/**
 * Controller for the main game screen.
 * Handles user interactions, dynamic generation of input fields,
 * the hint system (aids), and updates the visual state of the eclipse.
 * @author Juan Sebastian Valencia
 * @version 1.1
 */
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
    private ImageView imEclipsePhase;

    /**
     * Initializes the controller and generates the letter input fields.
     */
    @FXML
    public void initialize() {
        generateCasillas();
    }

    /**
     * Displays the game rules in a new window.
     * @throws IOException If the FXML file for rules is not found.
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
     * Executes the help logic by revealing a random missing letter.
     * Consumes one of the three available aids.
     */
    @FXML
    public void onActionHelp() {
        GameLogic logic = GameLogic.getInstance();
        int remainingAids = logic.getQuantityAid();

        if (remainingAids > 0) {
            int posicionEmpty = logic.getRandomEmptyPosition();

            // Safety check if no positions are left
            if (posicionEmpty == -1) return;

            TextField casilla = (TextField) hbLetras.getChildren().get(posicionEmpty);
            char letraRevelada = SecretWord.getInstance().getSecretWordArray()[posicionEmpty];

            // Reveal letter and disable input for that cell
            casilla.setText(String.valueOf(letraRevelada));
            casilla.setEditable(false);
            casilla.setStyle("-fx-border-color: gray; -fx-background-color: #B2E8AE; -fx-border-width: 2;");

            // Update internal state and model
            SecretWord.getInstance().setLettersFound(posicionEmpty, letraRevelada);
            logic.setQuantityAid(remainingAids - 1);

            // Update UI labels
            updateHelpUI(logic.getQuantityAid());

            // Check if this help completed the word
            if (logic.checkWinCondition()) {
                handleEndGame(true);
            }
        } else {
            btHelp.setDisable(true);
            btHelp.setStyle("-fx-background-color: grey;");
        }
    }

    /**
     * Dynamically generates TextFields for each letter of the secret word.
     * Each field includes a listener to validate user input in real-time.
     */
    public void generateCasillas() {
        int quantyCasillas = SecretWord.getInstance().getSecretWord().length();
        hbLetras.setSpacing(10);

        for (int i = 0; i < quantyCasillas; i++) {
            TextField casilla = new TextField();
            casilla.setPrefWidth(40);
            casilla.setPrefHeight(40);
            casilla.setAlignment(Pos.CENTER);

            final int index = i;
            casilla.textProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.isEmpty()) return;

                // Handle only the first character entered
                String input = newValue.substring(newValue.length() - 1).toLowerCase();
                char typedChar = input.charAt(0);

                if (checkLetter(index, typedChar)) {
                    processCorrectGuess(casilla, index, typedChar);
                } else {
                    processIncorrectGuess(casilla, typedChar);
                }
            });
            hbLetras.getChildren().add(casilla);
        }
    }

    private void processCorrectGuess(TextField casilla, int index, char letter) {
        casilla.setText(String.valueOf(letter));
        casilla.setEditable(false);
        casilla.setStyle("-fx-border-color: green; -fx-background-color: #B2E8AE; -fx-border-width: 2;");

        SecretWord.getInstance().setLettersFound(index, letter);
        lbBooleanResponse.setText("¡Correcto!");
        lbBooleanResponse.setStyle("-fx-text-fill: green;");

        if (GameLogic.getInstance().checkWinCondition()) {
            handleEndGame(true);
        } else {
            focusNextField(index);
        }
    }

    private void processIncorrectGuess(TextField casilla, char letter) {
        GameLogic logic = GameLogic.getInstance();
        logic.setQuantityFailure(logic.getQuantityFailure() + 1);

        casilla.clear();
        lbQuantityFailure.setText("Fallos: " + logic.getQuantityFailure());
        lbBooleanResponse.setText("Equivocado! la letra '" + letter + "' no es correcta.");
        lbBooleanResponse.setStyle("-fx-text-fill: red;");

        updateEclipseImage(logic.getQuantityFailure());

        if (logic.hasLost()) {
            handleEndGame(false);
        }
    }

    private void updateHelpUI(int count) {
        btHelp.setText("Ayuda (" + count + ")");
        lbQuantityHelp.setText("Ayudas restantes: " + count);
    }

    private void updateEclipseImage(int failureCount) {
        if (failureCount <= 5) {
            String url = "/com/example/sol_eclipsado/Images/eclipse" + failureCount + ".png";
            imEclipsePhase.setImage(new Image(getClass().getResource(url).toExternalForm()));
        }
    }

    private void focusNextField(int currentIndex) {
        if (currentIndex + 1 < hbLetras.getChildren().size()) {
            hbLetras.getChildren().get(currentIndex + 1).requestFocus();
        }
    }

    private void handleEndGame(boolean didWin) {
        GameLogic.getInstance().setWin(didWin);
        try {
            Parent root = new FXMLLoader(getClass().getResource("/com/example/sol_eclipsado/view/final-view.fxml")).load();
            StageMain.getStage().setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates a letter by comparing normalized versions (ignoring accents).
     * @param index The position in the secret word.
     * @param input The character entered by the user.
     * @return true if the letters match after normalization.
     */
    public static boolean checkLetter(int index, char input) {
        char secret = SecretWord.getInstance().getSecretWordArray()[index];
        return normalize(secret) == normalize(input);
    }

    private static char normalize(char c) {
        String str = Normalizer.normalize(String.valueOf(c), Normalizer.Form.NFD);
        return str.replaceAll("[^\\p{ASCII}]", "").toLowerCase().charAt(0);
    }
}