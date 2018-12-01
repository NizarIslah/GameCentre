package fall2018.csc2017.GameCentre;

class MovementController {
    /**
     * The board manager.
     */
    private BoardManager boardManager;
    /**
     * The username of the person logged in
     */
    private String username = new LoginManager().getPersonLoggedIn();
    /**
     * The file manager, to assist in reading and
     * saving the .ser files
     */
    private FileManager fm = new FileManager();

    /**
     * Default constructor for the Movement Controller
     */
    MovementController() {
    }

    /**
     * Sets the boardManager of MovementController.
     *
     * @param boardManager is type BoardManager.
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Processes a tap for the user.
     *
     * @param position that the user tapped.
     */
    boolean processTapMovement(int position, BoardManager boardManager) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the username of the winning player of the specified game
     *
     * @param gameIndex the identity index of the game
     * @return the userToDisplay player's username
     */
    String getWinnerUsername(int gameIndex) {
        // get the winning player (current player in the bm)
        int playerNumber = 3 - boardManager.getBoard().getCurrPlayer();
        if (gameIndex != 0 && playerNumber == 2) {
            return fm.getUser(username).getOpponents().get(gameIndex);
        }
        return username;
    }

}
