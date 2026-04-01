package com.example.sol_eclipsado.model;

/**
 * Represents the secret word for the "Sol Eclipsado" game.
 * This class follows a variant of the Singleton pattern to ensure only one
 * secret word exists during the game session. It manages the storage,
 * character mapping, and validation logic required by the game rules.
 * @author Juan Sebastian Valencia
 * @version 1.1
 */
public class SecretWord {

    private static String secretWord;
    private static char[] secretWordArray;
    private static char[] lettersFound;
    private static SecretWord instance;

    /**
     * Private constructor to prevent instantiation from other classes,
     * ensuring the Singleton property.
     */
    private SecretWord(){}

    /**
     * Configures the secret word, initializes the discovery array,
     * and stores the word in lowercase for case-insensitive comparisons.
     * @param secretWord The string to be set as the game's secret word.
     */
    public void setSecretWord(String secretWord) {
        SecretWord.secretWord = secretWord;
        SecretWord.lettersFound = new char[secretWord.length()];
        // Converts to lowercase to ignore case sensitivity during the game
        SecretWord.secretWordArray = secretWord.toLowerCase().toCharArray();
    }

    /**
     * @return The raw string of the secret word.
     */
    public String getSecretWord() { return secretWord; }

    /**
     * @return A character array containing the secret word in lowercase.
     */
    public char[] getSecretWordArray() { return secretWordArray; }

    /**
     * @return A character array representing the letters correctly guessed by the player.
     */
    public static char[] getLettersFound() { return lettersFound; }

    /**
     * Updates the array of found letters at a specific position.
     * @param index The position in the word where the letter was found.
     * @param letter The character to store in the found letters array.
     */
    public void setLettersFound(int index, char letter){
        lettersFound[index] = letter;
    }

    /**
     * Retrieves the unique instance of the SecretWord class.
     * @return The Singleton instance of SecretWord.
     */
    public static SecretWord getInstance(){
        if(instance == null){
            instance = new SecretWord();
        }
        return instance;
    }

    /**
     * Validates if the secret word contains only Latin alphabet characters,
     * including accents and the letter 'ñ'.
     * @return {@code true} if the word matches the Latin character criteria, {@code false} otherwise.
     */
    public static boolean characterValidation(){
        // Note: Using \\p{IsLatin} verifies if the string matches Latin properties
        return secretWord.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    }

    /**
     * Validates if the length of the secret word is within the allowed range
     * (between 6 and 12 characters).
     * @return {@code true} if the size is valid, {@code false} if it is too short or too long.
     */
    public static boolean sizeValidation(){
        return secretWord.length() >= 6 && secretWord.length() <= 12;
    }

    /**
     * Checks if the secret word contains any blank spaces (initial, middle, or final).
     * @return {@code true} if spaces are found, {@code false} if the word is contiguous.
     */
    public static boolean spacingWordValidation(){
        return secretWord.contains(" ");
    }
}
