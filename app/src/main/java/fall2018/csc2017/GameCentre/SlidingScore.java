package fall2018.csc2017.GameCentre;


class SlidingScore implements Score {

    /**
     * A board object
     */
    private Board board;

    /**
     * The constructor for this class, which intiailizes the fm object
     */
    SlidingScore() {
    }

    /**
     * Calculate and return the score of User user
     *
     * @param numMoves, an integer representing the number of moves made by this user
     * @param size,     an integer representing the size of the board
     * @return score, an integer representing the score of this user
     */

    public int calculateUserScore(int numMoves, int size) {
        double score = 2000 / numMoves;
        return (int) score + (50 * size);
    }

    /**
     * A setter for the board of this class.
     *
     * @param board the board to be set
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
