package fall2018.csc2017.GameCentre;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourBoardManager implements BoardManager {


    /**
     * The board being managed.
     */
    private Board board;
    /**
     * The current player
     */
    private int currentPlayer = 1;
    /**
     * Signifies whether the game is over
     */
    private boolean gameOver = false;
    /**
     * The position of the most recent tap of the user
     * on the board
     */
    private int currentPos = -1;


    /**
     * Overloaded constructor for the board manager,
     * requiring a board to instantiate the board manager
     *
     * @param board the board to instantiate the board
     *              manager with
     */
    ConnectFourBoardManager(Board board) {
        this.board = board;
        Board.NUM_COLS = board.getTiles().length;
        Board.NUM_ROWS = board.getTiles().length;
    }

    /**
     * Overloaded constructor for the Connect 4 board manager,
     * using a size parameter to create boards and boardmanagers
     * of various sizes
     *
     * @param size the size of the board, for which the
     *             board manager is to be created
     */
    ConnectFourBoardManager(int size) {
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_COLS = size;
        Board.NUM_ROWS = size;

        final int numTiles = Board.NUM_COLS * Board.NUM_ROWS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            Tile tile = new Tile(tileNum);

            tile.setBackground(R.drawable.tile_25);

            tiles.add(tile);
        }

        this.board = new Board(tiles);
    }

    /**
     * Returns the board from the boardManager
     *
     * @return the board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Makes toast on the user screen
     *
     * @param textToDisplay the text to display in the toast
     */
    private void makeToast(String textToDisplay) {
        Toast.makeText(GlobalApplication.getAppContext(), textToDisplay, Toast.LENGTH_LONG).show();
    }

    /**
     * Switches the player after every turn
     */
    private void switchPlayer() {
        if (this.currentPlayer == 1) {
            this.currentPlayer = 2;
            this.board.setCurrPlayer(2);
        } else {
            this.currentPlayer = 1;
            this.board.setCurrPlayer(1);
        }
    }

    /**
     * Gets the suitable background depending on the current player
     *
     * @return the id of the background for the current player
     */
    private int getBackgroundForPlayer() {
        if (this.currentPlayer == 1) {
            return R.drawable.red;
        } else {
            return R.drawable.black;
        }
    }

    /**
     * Returns if a puzzle has been solved after a series of
     * 4 chips of same color have been connected.
     *
     * @return whether a puzzle has been solved.
     */
    public boolean puzzleSolved() {
        if ((checkDiagonals(currentPos) ||
                checkSides(currentPos) ||
                checkUnder(currentPos))) {
            setGameOver();
            return true;
        }
        return false;
    }

    /**
     * Returns the Background ID of the tile which is positioned at the place of
     * the most recent move
     *
     * @param position the position of the most recent move
     * @return BackgroundID of the most recently added tile
     */
    int getCurrentPlayer(int position) {
        return getTileColor(position);
    }

    /**
     * Returns the color of the tile at given position
     *
     * @param position the position of the tile we need to obtain the tileColor of
     * @return an int representing the colorID of the found tile
     */
    private int getTileColor(int position) {
        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile nextTile = null;
        while (position >= 0) {
            nextTile = iter.next();
            position--;
        }
        assert nextTile != null;
        return nextTile.getBackground();
    }

    /**
     * Checks diagonally for sequences of 4 or more same colored
     * chips.
     *
     * @return whether there is a sequence of same-color-chips of
     * length 4 or more on either diagonal of the newly added chip
     */
    boolean checkDiagonals(int position) {

        int currentPlayerID = getCurrentPlayer(position);
        int leftDiagUp = checkLeftDiagUp(currentPlayerID, position);
        int leftDiagDown = checkLeftDiagDown(currentPlayerID, position);
        int rightDiagUp = checkRightDiagUp(currentPlayerID, position);
        int rightDiagDown = checkRightDiagDown(currentPlayerID, position);
        return ((leftDiagDown + leftDiagUp - 1) > 3) || ((rightDiagDown + rightDiagUp - 1) > 3); // changed to 2 from 1
    }

    /**
     * Returns the number of same colored chips to the right diagonal, upwards
     *
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the upper right diagonal
     */
    private int checkRightDiagDown(int currentPlayerID, int position) {
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        while (colNewlyAdded >= 0 && rowNewlyAdded < Board.NUM_ROWS && rowNewlyAdded >= 0) {
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID) && (getTileColor(position) != R.drawable.tile_25)) {
                colorCounter++;
            } else {
                break;
            }

            if (position == Board.NUM_COLS * (rowNewlyAdded)) {
                break;
            }
            position = position + (Board.NUM_COLS - 1);
            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Returns the number of same colored chips to the right diagonal, upwards
     *
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the upper right diagonal
     */
    private int checkRightDiagUp(int currentPlayerID, int position) {
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        while (colNewlyAdded < Board.NUM_COLS && rowNewlyAdded >= 0 && colNewlyAdded >= 0) {
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID) && (getTileColor(position) != R.drawable.tile_25)) {
                colorCounter++;
            } else {
                break;
            }
            if (position == Board.NUM_COLS * (rowNewlyAdded)) {
                break;
            }

            position = position - (Board.NUM_COLS - 1);
            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Sets the status of the current game as over
     */
    private void setGameOver() {
        this.gameOver = true;
    }

    /**
     * Returns the number of same colored chips to the left diagonal, downwards
     *
     * @param position the position of the newly added tile
     * @return the number of same colored chips in the lower left diagonal
     */
    private int checkLeftDiagDown(int currentPlayerID, int position) {
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        while (colNewlyAdded < Board.NUM_COLS && rowNewlyAdded < Board.NUM_ROWS && colNewlyAdded >= 0 && rowNewlyAdded >= 0) {
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID) && (getTileColor(position) != R.drawable.tile_25)) {
                colorCounter++;
            } else {
                break;
            }
            if (position == Board.NUM_COLS * (rowNewlyAdded + 1) - 1) {
                break;
            }
            position = position + (Board.NUM_COLS + 1);
            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Returns the number of same colored chips to the left diagonal,
     * in the upward direction
     *
     * @param position the position of the newly added tile
     * @return number of same colored chips in the upper left diagonal
     */
    private int checkLeftDiagUp(int currentPlayerID, int position) {
        int colorCounter = 0;
        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_COLS;
        while (colNewlyAdded >= 0 && rowNewlyAdded >= 0) {
            // here check for same color and increment colorCounter
            if ((getTileColor(position) == currentPlayerID) && (getTileColor(position) != R.drawable.tile_25)) {
                colorCounter++;
            } else {
                break;
            }

            if (position == Board.NUM_COLS * (rowNewlyAdded)) {
                break;
            }

            position = position - (Board.NUM_COLS + 1);

            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Checks on either side for sequences of 4 or more same colored chips
     *
     * @return whether there are sequences of same-colored-chips or
     * length 4 or more on either sides of the newly added chip
     */
    boolean checkSides(int position) {
        int currentPlayerColor = getCurrentPlayer(position);
        return (numLeft(currentPlayerColor, position) + numRight(currentPlayerColor, position) - 1) > 3;
    }

    /**
     * Returns the number of tiles with the same color to the left
     * of the newly added tile.
     *
     * @param position the position of the newly added tile
     * @return the number of the same colored tiles to the left
     */
    private int numLeft(int currentPlayerID, int position) {
        int colNewlyAdded = position % Board.NUM_COLS;
        int colorCounter = 0;
        while (colNewlyAdded >= 0) {
            // check if there is a same colored tile to the left, and increment colorCounter
            if ((getTileColor(position) == currentPlayerID) && (getTileColor(position) != R.drawable.tile_25)) {
                colorCounter++;
            } else {
                break;
            }
            position = position - 1;
            if (position % Board.NUM_COLS == Board.NUM_COLS - 1) {
                break;
            }
            colNewlyAdded = position % Board.NUM_COLS;
        }
        return colorCounter;
    }

    /**
     * Returns the number of tiles with the same color to the right
     * of the newly added tile.
     *
     * @param position the position of the newly added tile
     * @return the number of the same colored tiles to the right
     */
    private int numRight(int currentPlayerID, int position) {

        int colNewlyAdded = position % Board.NUM_COLS;
        int rowNewlyAdded = position / Board.NUM_ROWS;
        int colorCounter = 0;
        while (colNewlyAdded < Board.NUM_COLS && rowNewlyAdded < Board.NUM_ROWS) {
            // check if there is a same colored tile to the right, and increment colorCounter
            if ((getTileColor(position) == currentPlayerID) && (getTileColor(position) != R.drawable.tile_25)) {
                colorCounter++;
            } else {
                break;
            }
            position = position + 1;
            if (position % Board.NUM_COLS == 0) {
                break;
            }
            colNewlyAdded = position % Board.NUM_COLS;
            rowNewlyAdded = position / Board.NUM_ROWS;
        }
        return colorCounter;
    }

    /**
     * Checks underneath for sequences of 4 or more same colored chips.
     *
     * @return whether there are sequence of 4 or more same-colored-chips
     * under the newly added chip.
     */
    boolean checkUnder(int position) {

        int currentPlayerColor = getCurrentPlayer(position);
        int colorCounter = 0; // to be incremented if same color found.
        int rowNewTile = position / Board.NUM_ROWS;
        while (rowNewTile < Board.NUM_ROWS) {
            if (getTileColor(position) == currentPlayerColor) {
                colorCounter++;
            } else {
                break;
            }
            position += Board.NUM_ROWS;
            rowNewTile = position / Board.NUM_ROWS;
        }
        return (colorCounter > 3);
    }

    /**
     * Returns if the position tapped by the use is a valid tap.
     * A tap is valid if the position tapped has no chips and there
     * is a chip present right underneath it or it is the bottom row.
     *
     * @param position the position of tapped by the user.
     * @return if the tap is a valid tap or not.
     */
    public boolean isValidTap(int position) {
        boolean result = checkEmptyTile(position);
        if (result) { // if empty tile
            return checkUnderneath(position);
        } else {
            return false;
        }
    }

    /**
     * Checks if the position has an empty tile.
     *
     * @param position the position tapped by the user.
     * @return whether there is an empty tile present on the tapped tile.
     */
    boolean checkEmptyTile(int position) {
        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile next = null;
        while (position >= 0) {
            next = iter.next();
            position = position - 1;
        }
        assert next != null;
        return next.getBackground() == R.drawable.tile_25;
    }

    /**
     * Returns if there is a tile present underneath the given tile.
     *
     * @param position the position tapped by the user.
     * @return whether there is a tile present underneath the
     * given tile, signified by the position
     */
    boolean checkUnderneath(int position) {
        //int blankID = 25;
        int positionBelow = position + Board.NUM_ROWS;
        int numRows = positionBelow / Board.NUM_ROWS;
        if (numRows < Board.NUM_ROWS) {
            Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
            Tile next = null;
            while (positionBelow >= 0) {
                next = iter.next();
                positionBelow--;
            }
            assert next != null;
            return !(next.getBackground() == R.drawable.tile_25);
        } else {
            return true;
        }
    }

    /**
     * Returns whether the current game is drawn (ended in stalemate)
     *
     * @return whether the current game has ended in stalemate
     */
    boolean gameDrawn() {
        Board.BoardIterator iter = (Board.BoardIterator) board.iterator();
        Tile tile;
        while (iter.hasNext()) {
            tile = iter.next();
            if (tile.getBackground() == R.drawable.tile_25) {
                return false;
            }
        }
        makeToast("The game is drawn!");
        setGameOver();
        return true;
    }

    /**
     * Method to process the movement of the touch, if the touch is a valid move
     *
     * @param position the position at which the move is to be made
     */
    public void touchMove(int position) {
        if (!gameOver) {
            int row = position / Board.NUM_ROWS;
            int col = position % Board.NUM_COLS;
            currentPos = position;
            board.setTileBackground(row, col, getBackgroundForPlayer());
            if (gameDrawn()) {
                makeToast("Game drawn! Start a new game");
            } else {
                switchPlayer();
            }
        } else {
            makeToast("Game over! Start a new game!");
        }
    }

    /**
     * Returns if the boardManager changed
     *
     * @return true if boardManager changed
     */
    public boolean getChanged() {
        return true;
    }

}
