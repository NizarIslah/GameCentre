package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ScoreboardManager {
    private FileManager fm;
    private ScoreFactory scoreFactory = new ScoreFactory();

    ScoreboardManager() {
        this.fm = new FileManager();
    }

    /**
     * Generates the user score for a user, given the username and the
     * game index of the game
     *
     * @param userLoggedIn the username of the user
     * @param gameIndex    the game index identity of the game
     * @return the score of the user
     */
    int generateUserScore(String winner, String userLoggedIn, int gameIndex) {
        int newScore;
        User user = fm.getUser(winner);
        Score s1 = scoreFactory.getScore(gameIndex);
        if (!winner.equals(userLoggedIn)) {
            User user2 = fm.getUser(userLoggedIn);
            s1.setBoard(user2.getGameStack(gameIndex).peek());
            newScore = s1.calculateUserScore(user2.getNumMoves(gameIndex), Board.NUM_COLS);
        } else {
            s1.setBoard(user.getGameStack(gameIndex).peek());
            newScore = s1.calculateUserScore(user.getNumMoves(gameIndex), Board.NUM_COLS);
        }
        user.addSessionScore(newScore, gameIndex);
        fm.saveUser(user, winner);
        return newScore;
    }

    /**
     * Formats the scores to display them onto the screen in a better fashion
     *
     * @param c4Scores the scores of a user from a game of Connect 4
     * @return the StringBuilder representing the
     */
    StringBuilder formatScores(List c4Scores) {
        StringBuilder scoreFormat = new StringBuilder();
        for (int i = 0; i < 4 && i < c4Scores.size(); i++) {
            Object[] scoreTup = (Object[]) c4Scores.get(i);
            scoreFormat.append(scoreTup[0]).append(": ").append(scoreTup[1]).append("\n");
            //Appends the username and score to the tup.
        }
        if (scoreFormat.length() == 0) {
            scoreFormat.append("No scores to show! Be the first one!");
        }
        return scoreFormat;
    }

    /**
     * Gets the global scores for all users, with the game gameIndex
     *
     * @param gameIndex the identity of the game
     * @return the ArrayList of global scores
     */
    List getGlobalScores(int gameIndex) {
        Map<String, User> users = fm.readObject();
        List<Object> highScoresTup = new ArrayList<>();
        for (Object o : users.entrySet()) {
            HashMap.Entry pair = (HashMap.Entry) o;
            User u = (User) pair.getValue();
            ArrayList<Integer> highScoresList = u.getHighestScoresList(gameIndex);
            for (int i = 0; i < highScoresList.size(); i++) {
                Object[] tempTup = new Object[2];
                tempTup[0] = u.getUsername();
                tempTup[1] = highScoresList.get(i);
                highScoresTup.add(tempTup);
            }
        }
        return sortListofUserandScores(highScoresTup);
    }

    /**
     * Sorts the arrays of users and their scores for various games.
     *
     * @param highScoresTup the list of highscores of a user
     * @return the sorted list of highscores of a user
     */
    private List sortListofUserandScores(List highScoresTup) {
        Collections.sort(highScoresTup, new Comparator() {
            @Override
            public int compare(Object o, Object t) {
                Object[] tempO = (Object[]) o;
                Object[] tempT = (Object[]) t;
                int o1 = (int) tempO[1];
                int t1 = (int) tempT[1];
                return t1 - o1;
            }
        });
        return highScoresTup;
    }
}
