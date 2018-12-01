package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * The Sliding Tiles game activity.
 */
public class ConnectFourActivity extends GameActivity implements Observer {

    /**
     * The board manager.
     */
    private ConnectFourBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The gesture detector grid view for the Connect 4 board, calculated
     * based on the device size
     */
    private Connect4GestureDetectGridView gridView;

    /**
     * The column width and the column height, calculated based on the
     * device size
     */
    private static int columnWidth, columnHeight;
    /**
     * The username of the person logged in
     */
    private String username = new LoginManager().getPersonLoggedIn();

    /**
     * The filemanager for reading and writing to the HashMap
     */
    private FileManager fm = new FileManager();

    /**
     * The board manager factor
     */
    private BoardManagerFactory bmFactory = new BoardManagerFactory();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * On the creation of the screen, the function is executed
     *
     * @param savedInstanceState the Bundle that is executed for creating the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int gameIndex = 2; // identity of the connect4 game
        Stack<Board> userStack = fm.getStack(username, gameIndex);
        boardManager = (ConnectFourBoardManager) bmFactory.getBoardManager(gameIndex, userStack.peek());
        createTileButtons(this);
        setContentView(R.layout.activity_main_connect4);
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        addBoardObserver();
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;
                        display();
                    }
                });
    }

    /**
     * Adds the observer to the board, alerting the system of any changes
     * that take place in the Connect 4 board
     */
    private void addBoardObserver() {
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(ConnectFourActivity.this);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Updates the display
     *
     * @param o   the observable
     * @param arg the object
     */
    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}