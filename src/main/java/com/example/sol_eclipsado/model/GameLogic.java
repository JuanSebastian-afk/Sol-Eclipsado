package com.example.sol_eclipsado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the core game state and logic for "El Sol Eclipsado."
 * This class tracks the number of failed attempts, available aids,
 * and determines the win/loss conditions based on the player's progress.
 * Follows the Singleton pattern to maintain a single game state.
 * @author Juan Sebastian Valencia
 * @version 1.1
 */
public class GameLogic {

    private int quantityFailure = 0;
    private int quantityAid = 3;
    private boolean win = false;
    private static GameLogic instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private GameLogic(){}

    /**
     * Provides access to the single instance of the GameLogic.
     * @return The current GameLogic instance.
     */
    public static GameLogic getInstance(){
        if(instance == null){
            instance = new GameLogic();
        }
        return instance;
    }

    /** @return The number of remaining aids (hints). */
    public int getQuantityAid() { return quantityAid; }

    /** @return The number of failed attempts committed. */
    public int getQuantityFailure(){ return quantityFailure; }

    /** @param quantityAid Updates the number of available aids. */
    public void setQuantityAid(int quantityAid) { this.quantityAid = quantityAid; }

    /** @param quantityFailure Updates the failure count. */
    public void setQuantityFailure(int quantityFailure) { this.quantityFailure = quantityFailure; }

    /** @param win Sets the current win status. */
    public void setWin(boolean win){ this.win = win; }

    /** @return {@code true} if the player has won the game. */
    public boolean getWin(){ return win; }

    /**
     * Checks if the player has lost based on the failure limit.
     * @return {@code true} if failures are 5 or more, {@code false} otherwise.
     */
    public boolean hasLost(){
        return quantityFailure >= 5;
    }

    /**
     * Verifies if all letters of the secret word have been found.
     * In Java, char arrays are initialized with the null character '\u0000'.
     * @return {@code true} if no empty spaces remain in the found letters array.
     */
    public boolean checkWinCondition(){
        for(char c : SecretWord.getLettersFound()){
            if(c == '\u0000'){
                return false;
            }
        }
        return true;
    }

    /**
     * Identifies all positions in the secret word that have not been revealed yet
     * and selects one at random to assist the player.
     * @return A random index of a hidden letter.
     * @throws IllegalArgumentException if no empty positions are left.
     */
    public int getRandomEmptyPosition(){
        List<Integer> emptyPositions = new ArrayList<>();
        Random random = new Random();
        char[] found = SecretWord.getLettersFound();

        for(int i = 0; i < found.length; i++){
            if(found[i] == '\u0000'){
                emptyPositions.add(i);
            }
        }

        if (emptyPositions.isEmpty()) {
            return -1; // Or handle as an exception
        }

        // Returns a random index from the list of empty slots
        return emptyPositions.get(random.nextInt(emptyPositions.size()));
    }
}