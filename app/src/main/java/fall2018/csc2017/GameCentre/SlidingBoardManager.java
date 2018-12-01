package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingBoardManager implements BoardManager, Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * This constructor takes a Board object and sets it equal to the class' attribute board
     *
     * @param board, a board object representing the board
     */

    SlidingBoardManager(Board board) {
        this.board = board;
        Board.NUM_COLS = board.getTiles().length;
        Board.NUM_ROWS = board.getTiles().length;
    }

    /**
     * This constructor takes an integer size and creates a board of size size
     *
     * @param size, an integer representing the size of this board
     */

    SlidingBoardManager(int size) {
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_COLS = size;
        Board.NUM_ROWS = size;
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles - 1; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        makeSolvable(tiles, size);

    }

    /**
     * This method returns the board attribute of this class
     *
     * @return Board, a board object
     */

    public Board getBoard() {
        return this.board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        int counter = 1;
        for (Tile tile : board) {
            if (tile.getId() != counter) return false;
            counter++;
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();

        if (isValidTap(position)) {
            Tile above = row == 0 ? null : board.getTile(row - 1, col);
            Tile left = col == 0 ? null : board.getTile(row, col - 1);
            Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

            if (above != null && above.getId() == blankId) board.swapTiles(row, col, row - 1, col);
            else if (left != null && left.getId() == blankId)
                board.swapTiles(row, col, row, col - 1);
            else if (right != null && right.getId() == blankId)
                board.swapTiles(row, col, row, col + 1);
            else board.swapTiles(row, col, row + 1, col);
        }

    }

    /**
     * This method creates a sovlable board
     *
     * @param tiles, an Arraylist holding tile objects. size, an integer representing the size
     *               of this board
     */

    private void makeSolvable(List<Tile> tiles, int size) {
        Tile testTile = new Tile(24);
        testTile.setId(size * size);
        tiles.add(testTile);
        Collections.shuffle(tiles);
        Board dummyBoard = new Board(tiles);
        while (!dummyBoard.isSolveable()) {
            Collections.shuffle(tiles);
            dummyBoard = new Board(tiles);
        }
        this.board = dummyBoard;
    }

    /**
     * Gets whether the state has changed.
     *
     * @return true
     */
    public boolean getChanged() {
        return true;
    }

}
