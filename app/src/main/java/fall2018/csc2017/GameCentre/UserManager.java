package fall2018.csc2017.GameCentre;


import java.util.Stack;

class UserManager {

    /**
     * The FileManager attribute of this class
     */
    private FileManager fm;

    /**
     * Constructor for the userManager, initalizing the FileManager.
     */
    UserManager() {
        fm = new FileManager();
    }

    /**
     * @param username  the username of the player who wishes to undo
     * @param gameIndex the game for which the player wishes to undo
     * @return whether or not the attempted undo was successful.
     */
    boolean processUndo(String username, int gameIndex) {
        User user = fm.getUser(username);
        Stack<Board> userStack = fm.getStack(username, gameIndex);
        if (user.getAvailableUndos(gameIndex) == 0 || userStack.size() == 1) {
            return false;
        } else {
            user.getGameStack(gameIndex).pop();
            user.setAvailableUndos(gameIndex, user.getAvailableUndos(gameIndex) - 1);
            fm.saveUser(user, username);
            return true;
        }
    }

    /**
     * @param gameIndex The gameindex for which we want to add an opponent for
     * @param opponent  The string representing the opponent to face.
     */
    void addOpponent(int gameIndex, String opponent) {
        LoginManager lm = new LoginManager();
        String username = lm.getPersonLoggedIn();
        User user = fm.getUser(username);
        user.getOpponents().put(gameIndex, opponent);
        fm.saveUser(user, username);
    }

    /**
     * @param username     The username for whom we want to set the undos
     * @param undoLimit    the proposed undoLimit
     * @param gameIndex    the game for which we want to set the undos
     * @param boardManager the boardmanager to which we will add the initial state.
     */
    void setUserUndos(String username, int undoLimit, int gameIndex, BoardManager boardManager) {
        User user = fm.getUser(username);
        user.setAvailableUndos(gameIndex, undoLimit);
        user.resetGameStack(gameIndex);
        user.addState(boardManager.getBoard(), gameIndex);
        fm.saveUser(user, username);
    }

    void saveState(String username, BoardManager boardManager, int gameIndex) {
        User user = fm.getUser(username);
        user.addState(boardManager.getBoard(), gameIndex);
        fm.saveUser(user, username);
    }

}
