package fall2018.csc2017.GameCentre;

public interface Score {

    /**
     * Calculates the user score based on the number of moves
     * executed by the user, and the size of the board
     *
     * @param numMoves number of moves executed
     * @param size     the size of the board
     * @return score of the user
     */
    int calculateUserScore(int numMoves, int size);

    /**
     * Setting the board of the score to the parameter board
     *
     * @param board the board to be set
     */
    void setBoard(Board board);

}
