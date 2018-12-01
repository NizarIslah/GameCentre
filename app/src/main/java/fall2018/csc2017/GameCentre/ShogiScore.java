package fall2018.csc2017.GameCentre;

class ShogiScore implements Score {

    /**
     * A Board object
     */
    private Board board;

    /**
     * The constructor of this class.
     */
    ShogiScore() {
    }

    /**
     * Calculates and returns and integer representing the score of User user
     *
     * @param numMoves, an int representing the number of moves made by this user
     * @param size,     an integer representing the size of the played board
     * @return score, an integer representing the score of this user
     */

    public int calculateUserScore(int numMoves, int size) {
        return generateScore(numMoves, getPieceDiff(board)) + (50 * size);
    }

    /**
     * Sets the board of the score view to the boad in the parameter
     *
     * @param board the board to be set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Returns the difference in numbers of pieces
     *
     * @param board the board used to playing the game
     * @return the integer representing the difference
     */
    int getPieceDiff(Board board) {
        return Math.abs(board.numBlacks() - board.numReds());
    }

    /**
     * Generates the score based on the score difference and the number
     * of moves executed by the person
     *
     * @param numMoves  number of moves executed
     * @param pieceDiff difference in number of pieces
     * @return score of hte user
     */
    int generateScore(int numMoves, int pieceDiff) {
        if (numMoves < 14)
            return 860 + (20 * pieceDiff);

        int score = Math.round(-1 * numMoves + 774) + (20 * pieceDiff);

        if (score < 0)
            score = 0;

        return score;
    }
}
