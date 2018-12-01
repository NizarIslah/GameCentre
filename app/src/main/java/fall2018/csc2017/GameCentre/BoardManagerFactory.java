/*
 * ======================================================================
 * This class is a Factory that instantiates different BoardManagers
 * based on the input
 * File Name: BoardManagerFactory.java
 * Authors: Group 0647
 * Date: November 25, 2018
 * ========================================================================
 */
package fall2018.csc2017.GameCentre;

class BoardManagerFactory {

    /***
     * Returns a new BoardManager for different games, based on size and gameIndex
     * @param gameIndex, an integer representing which game to initialize a BoardManager for
     *                   (0 --> SlidingTiles    1 --> HasamiShogi   2 --> Connect4)
     * @param size, an integer representing the ize of the Board
     * @return an object of a class implementing BoardManager, depending on gameIndex
     */
    BoardManager getBoardManager(int gameIndex, int size) {
        switch (gameIndex) {
            case 0:
                return new SlidingBoardManager(size);
            case 1:
                return new ShogiBoardManager(size);
            case 2:
                return new ConnectFourBoardManager(size);
        }
        return null;

    }

    /**
     * Returns a new BoardManager for different games, based on gameIndex and the board given
     *
     * @param gameIndex, an integer representing which game to initialize a BoardManager for
     *                   (0 --> SlidingTiles    1 --> HasamiShogi   2 --> Connect4)
     * @param board,     a Board object representing the board which BoardManager uses
     * @return an object of a class implementing BoardManager, depending on gameIndex
     */

    BoardManager getBoardManager(int gameIndex, Board board) {
        switch (gameIndex) {
            case 0:
                return new SlidingBoardManager(board);
            case 1:
                return new ShogiBoardManager(board);
            case 2:
                return new ConnectFourBoardManager(board);
        }
        return null;
    }
}
