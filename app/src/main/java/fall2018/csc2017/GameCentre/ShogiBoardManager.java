package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.List;

public class ShogiBoardManager implements BoardManager {
    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Represents the tile selected by the player
     */
    private int tileSelected = -1;

    /**
     * Represents the tile owner
     */
    private int tileOwner = -1;

    /**
     * Boolean showing whether the board manager is changed or not
     */
    private boolean changed = false;

    /**
     * This constructor takes a board object and sets this class' board attribute object
     * equal to it
     *
     * @param board, a Board object representing the board
     */
    ShogiBoardManager(Board board) {
        this.board = board;
        Board.NUM_COLS = board.getTiles().length;
        Board.NUM_ROWS = board.getTiles().length;
    }

    /**
     * This constructor takes an int size and creates the board of size size
     *
     * @param size, an integer representing the size of the board
     */

    ShogiBoardManager(int size) {
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_COLS = size;
        Board.NUM_ROWS = size;
        final int numTiles = size * size;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            Tile tile = new Tile(tileNum);
            if (tileNum < size) {
                tile.setBackground(R.drawable.black);
            } else if (tileNum > numTiles - size - 1) {
                tile.setBackground(R.drawable.red);
            } else {
                tile.setBackground(R.drawable.tile_25);
            }
            tiles.add(tile);
        }
        this.board = new Board(tiles);
    }

    /**
     * This method returns the board attribute of this board manager
     *
     * @return Board the board object attribute of this class
     */

    public Board getBoard() {
        return this.board;
    }

    /**
     * This method returns whether or not the board has been solved
     *
     * @return boolean, true if the puzzle has been solved, false otherwise
     */

    public boolean puzzleSolved() {
        return getBoard().numBlacks() <= 1 || getBoard().numReds() <= 1;
    }

    /**
     * This method returns whether or not the tap made by the user is a valid tap
     *
     * @return boolean, true if the puzzle has been solved, false otherwise
     */

    public boolean isValidTap(int position) {
        Tile currTile = getBoard().getTile(position / Board.NUM_ROWS, position % Board.NUM_ROWS);
        tileOwner = getTileOwner(currTile);
        if (isTurn()) {
            return setTileToMove(position, currTile);
        } else if (tileOwner == 0 && tileSelected != -1) {
            int fromTile = tileSelected;
            if ((inSameRow(fromTile, position) && !tileBlockingRow(fromTile, position) && isWhite(position / Board.NUM_COLS, position % Board.NUM_COLS))
                    || inSameCol(fromTile, position) && !tileBlockingCol(fromTile, position) && isWhite(position / Board.NUM_COLS, position % Board.NUM_COLS)) {
                return true;
            } else {
                resetTileSelected();
                return false;
            }
        }
        return false;
    }


    /**
     * Resets the selected tile
     */
    private void resetTileSelected() {
        tileSelected = -1;
    }

    /**
     * Switches the player after every valid move
     */
    private void switchPlayer() {
        getBoard().setCurrPlayer(3 - getBoard().getCurrPlayer());
    }

    /**
     * Gets the tile's owner at a specified tile
     *
     * @param currTile the tile for which the owner is to be found
     * @return the owner of the tile
     */
    private int getTileOwner(Tile currTile) {
        if (currTile.getBackground() == R.drawable.black) {
            return 1;
        } else if (currTile.getBackground() == R.drawable.red) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Returns boolean representing the player with the current turn
     *
     * @return boolean representing player with the current turn
     */
    private boolean isTurn() {
        return tileOwner == getBoard().getCurrPlayer();
    }

    boolean setTileToMove(int position, Tile currTile) {
        if (tileSelected == -1
                || getBoard().getTile(
                tileSelected / Board.NUM_ROWS, tileSelected % Board.NUM_ROWS).getBackground()
                == currTile.getBackground()) {
            tileSelected = position;
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is a stub
     *
     * @param position: an nt representing the position touched
     */

    public void touchMove(int position) {
        int fromTile = tileSelected;
        if (isValidTap(position) && fromTile != -1 && tileOwner == 0) {
            board.swapTiles(fromTile / Board.NUM_COLS, fromTile % Board.NUM_COLS, position / Board.NUM_COLS, position % Board.NUM_COLS);
            int left = checkCapturedLeft(position);
            for (int i = 1; i <= left; i++) {
                board.setTileBackground(position / Board.NUM_COLS, position % Board.NUM_COLS - i, R.drawable.tile_25);
            }
            int right = checkCapturedRight(position);
            for (int i = 1; i <= right; i++) {
                board.setTileBackground(position / Board.NUM_COLS, position % Board.NUM_COLS + i, R.drawable.tile_25);
            }
            int up = checkCapturedUp(position);
            for (int i = 1; i <= up; i++) {
                board.setTileBackground(position / Board.NUM_COLS - i, position % Board.NUM_COLS, R.drawable.tile_25);
            }
            int down = checkCapturedDown(position);
            for (int i = 1; i <= down; i++) {
                board.setTileBackground(position / Board.NUM_COLS + i, position % Board.NUM_COLS, R.drawable.tile_25);
            }
            setChanged(true);
            resetTileSelected();
            switchPlayer();
        } else {
            setChanged(false);
        }
    }

    /**
     * Returns whether a given tile is black, at the given row and column
     *
     * @param row the row of the supplied tile
     * @param col the column of the supplied tile
     * @return whether the supplied parameter tile is black
     */
    boolean isBlack(int row, int col) {
        if (row >= Board.NUM_COLS || row < 0 || col >= Board.NUM_COLS || col < 0) {
            return false;
        }
        return board.getTile(row, col).getBackground() == R.drawable.black;
    }

    /**
     * Returns whether a given tile is red, at the given row and column
     *
     * @param row the row of the supplied tile
     * @param col the column of the supplied tile
     * @return whether the supplied parameter tile is red
     */
    boolean isRed(int row, int col) {
        if (row >= Board.NUM_COLS || row < 0 || col >= Board.NUM_COLS || col < 0) {
            return false;
        }
        return board.getTile(row, col).getBackground() == R.drawable.red;
    }

    /**
     * Returns whether a given tile is white, at the given row and column
     *
     * @param row the row of the supplied tile
     * @param col the column of the supplied tile
     * @return whether the supplied parameter tile is white
     */
    private boolean isWhite(int row, int col) {
        return !isBlack(row, col) && !isRed(row, col);
    }

    /**
     * Check capturing downwards for sandwiched tiles
     *
     * @param toTile tile to check the capturing till
     * @return number of captured enemy pieces
     */
    private int checkCapturedDown(int toTile) {
        int numCap = 0;
        int nextRow = toTile / Board.NUM_COLS + 1;
        if (isBlack(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isRed(nextRow, toTile % Board.NUM_COLS)) {
                numCap++;
                nextRow++;
            }
            if (isBlack(nextRow, toTile % Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        } else if (isRed(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isBlack(nextRow, toTile % Board.NUM_COLS)) {
                numCap++;
                nextRow++;
            }
            if (isRed(nextRow, toTile % Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    /**
     * Checks capture upwards, till the specified tile
     *
     * @param toTile tile to check the capturing till
     * @return number of captured enemy pieces
     */
    private int checkCapturedUp(int toTile) {
        int numCap = 0;
        int nextRow = toTile / Board.NUM_COLS - 1;
        if (isBlack(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isRed(nextRow, toTile % Board.NUM_COLS)) {
                numCap++;
                nextRow--;
            }
            if (isBlack(nextRow, toTile % Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        } else if (isRed(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isBlack(nextRow, toTile % Board.NUM_COLS)) {
                numCap++;
                nextRow--;
            }
            if (isRed(nextRow, toTile % Board.NUM_COLS) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    /**
     * Checks capture to the right, till the specified tile
     *
     * @param toTile tile to check the capturing till
     * @return number of captured enemy pieces
     */
    private int checkCapturedRight(int toTile) {
        int numCap = 0;
        int nextCol = toTile % Board.NUM_COLS + 1;
        if (isBlack(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isRed(toTile / Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol++;
            }
            if (isBlack(toTile / Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        } else if (isRed(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isBlack(toTile / Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol++;
            }
            if (isRed(toTile / Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    /**
     * Checks capture towards the left, till the specified tile
     *
     * @param toTile tile to check the capturing till
     * @return number of captured enemy pieces
     */
    private int checkCapturedLeft(int toTile) {
        int numCap = 0;
        int nextCol = toTile % Board.NUM_COLS - 1;
        if (isBlack(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isRed(toTile / Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol--;
            }
            if (isBlack(toTile / Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        } else if (isRed(toTile / Board.NUM_COLS, toTile % Board.NUM_COLS)) {
            while (isBlack(toTile / Board.NUM_COLS, nextCol)) {
                numCap++;
                nextCol--;
            }
            if (isRed(toTile / Board.NUM_COLS, nextCol) && numCap > 0) {
                return numCap;
            }
        }
        return 0;
    }

    /**
     * returns whether two tiles are in the same row
     *
     * @param fromTile the first tile to consider
     * @param toTile   the second tile to consider
     * @return whether the two tiles are in the same row
     */
    boolean inSameRow(int fromTile, int toTile) {
        return fromTile / Board.NUM_COLS == toTile / Board.NUM_COLS;
    }

    /**
     * Returns whether two tiles are in the same column
     *
     * @param fromTile the first tile to consider
     * @param toTile   the second tile to consider
     * @return whether the two tiles are in the same column
     */
    boolean inSameCol(int fromTile, int toTile) {
        return fromTile % Board.NUM_COLS == toTile % Board.NUM_COLS;
    }

    /**
     * Returns whether there are tiles present between the two
     * considered tiles
     *
     * @param fromTile the first tile to consider
     * @param toTile   the second tile to consider
     * @return whether there are tiles between the fromTile and the toTile
     */
    boolean tileBlockingRow(int fromTile, int toTile) {
        int row = fromTile / Board.NUM_COLS;
        int start = fromTile % Board.NUM_COLS < toTile % Board.NUM_COLS ? fromTile % Board.NUM_COLS : toTile % Board.NUM_COLS;
        int end = fromTile % Board.NUM_COLS > toTile % Board.NUM_COLS ? fromTile % Board.NUM_COLS : toTile % Board.NUM_COLS;
        int col = start + 1;
        while (col < end) {
            if (getBoard().getTile(row, col).getBackground() != R.drawable.tile_25) {
                return true;
            }
            col++;
        }
        return false;
    }

    /**
     * Returns whether there are tiles present between the two considered tiles
     *
     * @param fromTile first tile to consider
     * @param toTile   second tile to consider
     * @return whether there are tiles between the fromTile and the toTile
     */
    boolean tileBlockingCol(int fromTile, int toTile) {
        int col = fromTile % Board.NUM_COLS;
        int start = fromTile / Board.NUM_COLS < toTile / Board.NUM_COLS ? fromTile / Board.NUM_COLS : toTile / Board.NUM_COLS;
        int end = fromTile / Board.NUM_COLS > toTile / Board.NUM_COLS ? fromTile / Board.NUM_COLS : toTile / Board.NUM_COLS;
        int row = start + 1;
        while (row < end) {
            if (getBoard().getTile(row, col).getBackground() != R.drawable.tile_25) {
                return true;
            }
            row++;
        }
        return false;
    }

    /**
     * Sets the selected tile to the parameter tile
     *
     * @param tile the tile to be set, the parameter tile
     */
    void setTileSelected(int tile) {
        this.tileSelected = tile;
    }

    /**
     * Sets the changed status of the board manager
     *
     * @param changed sets the boolean status of the shogi bm
     */
    private void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Returns the changed status of the board manager
     *
     * @return the changed status of the board manager
     */
    public boolean getChanged() {
        return changed;
    }
}
