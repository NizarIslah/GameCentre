/*
=============================================================================
This class represents a single user who has registered for the Game Center
File Name: User.java
Date: November 2, 2018
Author: CSC207 Group 0506
===========================================================================*/

package fall2018.csc2017.GameCentre;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class User implements Serializable {
    /**
     * This is the username of this user
     */
    private String username;
    /**
     * This is the password of this user
     */
    private String password;
    /**
     * This is a stack holding all the game states of the saved game for this user
     */
    private Map<Integer, Stack<Board>> savedStates;
    /**
     * This is answer to this user's security question
     */
    private String answer;

    /**
     * This is the current highest score of this user per game
     */
    private Map<Integer, ArrayList<Integer>> highestScore;
    /**
     * This is the security question of this user
     */
    private String securityQuestion;
    /**
     * This is a counter keeping track of the available undos for a user
     */
    private HashMap<Integer, Integer> availableUndos;

    /**
     * An attribute to track a user's logins.
     */
    private boolean isLoggedIn = false;

    private Map<Integer, String> opponents;

    /**
     * The user of the application. Object is used to sign in and access various functions.
     *
     * @param username         the username of the user
     * @param password         the password of the user
     * @param securityQuestion the security question answered by the user
     * @param answer           the answer recorded by the user during sign up
     */

    @SuppressLint("UseSparseArrays")
    User(String username, String password, String securityQuestion, String answer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.opponents = new HashMap<>();
        this.highestScore = new HashMap<>();
        this.savedStates = new HashMap<>();
        this.availableUndos = new HashMap<>();
        for (int i = 0; i <= 2; i++) {//Initializes the stack
            this.savedStates.put(i, new Stack<Board>());
            this.highestScore.put(i, new ArrayList<Integer>());
            this.availableUndos.put(i, 3);
        }
    }

    /**
     * A getter to obtain this user's username
     *
     * @return the user's username
     */
    String getUsername() {
        return this.username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    String getPassword() {
        return password;
    }

    /**
     * The stack of boards used to keep track of the moves made by the user.
     *
     * @return the stack of the boards
     */
    Stack<Board> getGameStack(int gameNum) {
        return savedStates.get(gameNum);
    }

    /**
     * Sets available undos
     *
     * @param gameIndex the index of the game to set the undos for
     * @param newUndos  the new number of undos we want to allow.
     */
    void setAvailableUndos(int gameIndex, int newUndos) {
        this.availableUndos.put(gameIndex, newUndos);
    }

    /**
     * Gets available undos
     *
     * @param gameIndex The game for which we want to obtain undos
     * @return an integer representing the number of available undos for the game specified
     */
    @SuppressWarnings("ConstantConditions")
    int getAvailableUndos(int gameIndex) {

        return this.availableUndos.get(gameIndex);
    }

    /**
     * Gets the number of moves made by the player.
     *
     * @return the number of moves the player has made so far in the game.
     */
    int getNumMoves(int game) {
        return getGameStack(game).size();
    }

    /**
     * Adds the current state of the board into the stack of boards.
     *
     * @param board the board to be added to the stack
     */
    @SuppressWarnings("ConstantConditions")
    void addState(Board board, int gameNum) {
        try {
            savedStates.get(gameNum).push(board);
        } catch (NullPointerException e) {
            this.savedStates.put(gameNum, new Stack<Board>());
            savedStates.get(gameNum).push(board);
        }
    }


    /**
     * Resets the user's game stack for a specified game.
     *
     * @param gameIndex the index of the game to reset the stack
     */
    void resetGameStack(int gameIndex) {
        this.savedStates.put(gameIndex, new Stack<Board>());
    }

    /**
     * Returns the answer to the security question that the user answered during
     * account creation.
     *
     * @return the answer of the user's security question
     */
    String getAnswer() {
        return answer;
    }

    /**
     * Returns the highest score of the user.
     *
     * @return the highest score of the user
     */
    ArrayList<Integer> getHighestScoresList(int gameIndex) {
        return highestScore.get(gameIndex);
    }

    /**
     * Returm the HashMap mapping a game index integer to the String representing the opponent
     * of the game
     *
     * @return HashMap<Integer               ,                               String>
     */
    Map<Integer, String> getOpponents() {
        return this.opponents;
    }

    /**
     * @return the security question for this user.
     */

    String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Set whether this user is logged im
     *
     * @param login whether or not this user is logged in
     */
    void setLoggedIn(boolean login) {
        isLoggedIn = login;
    }

    /**
     * @return whether or not this user is logged in
     */
    boolean getLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Returns a string representation of the user object.
     *
     * @return the string representation of the user
     */
    @Override
    @NonNull
    public String toString() {
        String username = this.username;
        return "User: " + username + " || Highest score for SlidingTiles: " + highestScore.get(0) + " Highest score for Shogi: " + highestScore.get(1) + " Highest score for Connect 4: " + highestScore.get(2);
    }

    /**
     * Adds the session score to the hashmap of scores for the user.
     *
     * @param score     the session score from the game that just finished
     * @param gameIndex the ID of the game played by the player
     */
    @SuppressWarnings("ConstantConditions")
    void addSessionScore(int score, int gameIndex) {
        highestScore.get(gameIndex).add(score);
    }

    /**
     * Resets the scores for a user for a specific game
     *
     * @param gameIndex the index of the game to be reset
     */
    private void resetScoreHashmapForGame(int gameIndex) {
        highestScore.put(gameIndex, new ArrayList<Integer>());
    }

    /**
     * Resets the score for a user, for all the games in the hashmap
     */
    void resetScoreHashmapForAllGames() {
        for (int i = 0; i <= highestScore.size() - 1; i++) {
            resetScoreHashmapForGame(i);
        }
    }

    /**
     * @param gameIndex this index of the game of interest
     * @return the maximum score attained by the user for the given game, or 0 if no scores exist.
     */
    int getMaxScore(int gameIndex) {
        Integer maxScore;
        List scoreList = getHighestScoresList(gameIndex);
        if (scoreList.size() == 0) {
            maxScore = 0;
        } else {
            maxScore = (int) Collections.max(scoreList);
        }
        return maxScore;
    }
}
