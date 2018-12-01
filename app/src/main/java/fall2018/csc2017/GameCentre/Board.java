package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class Board extends Observable implements Serializable, Iterable<Tile> {
    /**
     * The number of rows.
     */

    static int NUM_ROWS;

    /**
     * The number of rows.
     */
    static int NUM_COLS;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * The current player of the board.
     */
    private int currPlayer = 1;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }


    /**
     * A getter method to return the array of tiles contained within
     * the board.
     *
     * @return an array of tiles used in the construction of the board.
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return Board.NUM_ROWS * Board.NUM_COLS;
    }

    /**
     * Returns the number of black tiles in the current board.
     *
     * @return number of black tiles in the current board.
     */
    int numBlacks() {
        Iterator<Tile> iter = iterator();
        int numBlacks = 0;
        while (iter.hasNext()) {
            if (iter.next().getBackground() == R.drawable.black) {
                numBlacks++;
            }
        }
        return numBlacks;
    }

    /**
     * Returns the number of red tiles in the current board.
     *
     * @return number of red tiles in the current board.
     */
    int numReds() {
        Iterator<Tile> iter = iterator();
        int numReds = 0;
        while (iter.hasNext()) {
            if (iter.next().getBackground() == R.drawable.red) {
                numReds++;
            }
        }
        return numReds;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile tile1 = getTile(row1, col1);
        Tile tile2 = getTile(row2, col2);
        Tile[][] newTiles = tiles.clone();
        newTiles[row1][col1] = tile2;
        newTiles[row2][col2] = tile1;
        tiles = newTiles;
        setChanged();
        notifyObservers();
    }

    /**
     * Sets the tile background of a tile at the specified row and column
     *
     * @param row        the row of the specified tile
     * @param col        the column of the specified tile
     * @param background the background the tile needs to be set to
     */
    void setTileBackground(int row, int col, int background) {
        getTile(row, col).setBackground(background);
        setChanged();
        notifyObservers();
    }

    /**
     * Return the string representation of the board.
     *
     * @return the string representation of the board.
     */
    @NonNull
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Override iterator to return an instance of BoardIterator.
     *
     * @return a new instance of BoardIterator.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * A class for iterating over each tile in the board.
     */
    protected class BoardIterator implements Iterator<Tile> {
        /**
         * The row number of next tile.
         */
        int row = 0;
        /**
         * The column number of next tile.
         */
        int col = 0;

        /**
         * Returns whether you have reached the last tile in the board.
         *
         * @return true if there is a next tile.
         */
        @Override
        public boolean hasNext() {
            return row != Board.NUM_ROWS && col != Board.NUM_COLS;
        }


        /**
         * Return the next tile if there is one, updates row and col.
         *
         * @return nextTile, which is a Tile object.
         */
        @Override
        public Tile next() {
            Tile nextTile = getTile(row, col);
            if (col == Board.NUM_COLS - 1) {
                col = 0;
                row++;
            } else {
                col++;
            }
            return nextTile;
        }

    }

    /**
     * Return the number of inversions within the board for a odd side length.
     * An inversion is when a larger tile precedes a smaller valued tile.
     * Therefore, a solved board has no inversions.
     *
     * @return the number of inversions present within the board.
     */
    private int checkInversions() {
        int inversions = 0;
        ArrayList<Integer> arrayList = new ArrayList<>();
        BoardIterator iterator = new BoardIterator();
        while (iterator.hasNext()) {
            Tile nextTile = iterator.next();
            if (nextTile.getId() != numTiles()) {
                arrayList.add(nextTile.getId());
            }


        }
        for (int i = 0; i < arrayList.size(); i++) {
            Integer item = arrayList.get(i);
            for (int j = i; j < arrayList.size(); j++) {
                Integer parsedItem = arrayList.get(j);
                if (parsedItem < item) {
                    inversions++;
                }
            }
        }

        return inversions;

    }

    /**
     * Returns whether the board is solveable.
     *
     * @return whether the board can be solved.
     */

    boolean isSolveable() {
        int numTiles = this.numTiles();
        if (numTiles % 2 == 1) {
            int numInversions = this.checkInversions();
            return numInversions % 2 == 0;
        } else {
            int numInversions = this.checkInversions();
            int[] position = this.getEmptyTilePosition();
            int row = position[0];
            if (row % 2 == 1) { // covers row being even from below case
                return numInversions % 2 != 1;

            } else { // covers row being odd from below case
                return numInversions % 2 != 0;
            }
        }
    }

    /**
     * Returns the position of the empty tile within the board in the format [row, column]
     *
     * @return the position of the empty tile within the board in the format [row, column]
     */
    private int[] getEmptyTilePosition() {
        int position = getPos();
        int row = position / NUM_ROWS;
        int column = position % NUM_COLS;
        int[] list = new int[2];
        list[0] = row;
        list[1] = column;
        return list;

    }

    /**
     * Returns the index of the empty tile within the board
     *
     * @return the index of the empty tile within the board
     */
    private int getPos() {
        int counter = 0;
        BoardIterator iterator = new BoardIterator();
        while (iterator.hasNext()) {
            Tile tile = iterator.next();
            if (tile.getId() == numTiles()) {
                return counter;
            } else {
                counter++;
            }

        }
        return 0;
    }

    /**
     * Sets the current player of the game
     *
     * @param currPlayer the current player
     */
    void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * Returns the current player of the game
     *
     * @return the current player of the game
     */
    int getCurrPlayer() {
        return currPlayer;
    }
}
