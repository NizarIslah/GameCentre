package fall2018.csc2017.GameCentre;

interface BoardManager {
    /**
     * Return the current board.
     *
     * @return a board
     */
    Board getBoard();

    /**
     * Returns whether the puzzle has been solved according
     * to the current board
     *
     * @return whether the puzzle is solved
     */
    boolean puzzleSolved();

    /**
     * Returns whether the most recent tap of the user is a
     * valid tap
     *
     * @param position the position of the user tap
     * @return whether the most recent tap of the user was valid
     */
    boolean isValidTap(int position);

    /**
     * Executes the appropriate move after validating the touch
     * of the user at the specified position
     *
     * @param position the position of the user's touch
     */
    void touchMove(int position);

    /**
     * Returns whether the board changes after touchmove has been
     * executed after touch validation
     *
     * @return whether the board changed
     */
    boolean getChanged();


}
