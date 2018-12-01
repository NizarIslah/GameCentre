/* ======================================================================
  This class is a Factory that instantiates different BoardManagers
  based on the input
  File Name: BoardManagerFactory.java
  Authors: Group 0647
  Date: November 25, 2018
 ======================================================================== */
package fall2018.csc2017.GameCentre;

class ScoreFactory {

    /***
     * Returns a new BoardManager for different games, based on size and gameIndex
     * @param gameIndex, an integer representing which game to initialize a BoardManager for
     *                   (0 --> SlidingTiles    1 --> HasamiShogi   2 --> Connect4)
     * @return an object of a class implementing Calculate, depending on gameIndex
     */
    Score getScore(int gameIndex) {
        switch (gameIndex) {
            case 0:
                return new SlidingScore();
            case 1:
                return new ShogiScore();
            case 2:
                return new ConnectFourScore();
        }
        return null;
    }
}
