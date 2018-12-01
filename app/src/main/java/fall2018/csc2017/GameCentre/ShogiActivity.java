package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * The Sliding Tiles game activity.
 */
public class ShogiActivity extends GameActivity implements Observer {

    /**
     * The board manager.
     */
    private ShogiBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private ShogiGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;
    private int gameIndex = 1;
    private String username = new LoginManager().getPersonLoggedIn();
    private FileManager fm = new FileManager();
    private BoardManagerFactory bmFactory = new BoardManagerFactory();
    private UserManager userManager = new UserManager();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Called everytime the screen for this activity class is created
     *
     * @param savedInstanceState the saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stack<Board> userStack = fm.getStack(username, gameIndex);
        boardManager = (ShogiBoardManager) bmFactory.getBoardManager(gameIndex, userStack.peek());
        createTileButtons(this);
        setContentView(R.layout.activity_main_shogi);
        // Add View to activity
        addUndoButtonListener();
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
     * Adds the board observer for shogi
     */
    private void addBoardObserver() {
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(ShogiActivity.this);
    }

    /**
     * Adds the listener for the undo button on the UI
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.btnUndoHS);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userManager.processUndo(username, gameIndex)) {
                    if (boardManager.puzzleSolved()) {
                        showToast("Game over! Start a new game!");
                    } else {
                        User user = fm.getUser(username);
                        makeToastUndo(user, gameIndex);
                        boardManager = (ShogiBoardManager) bmFactory.getBoardManager(gameIndex, user.getGameStack(gameIndex).peek());
                        addBoardObserver();
                        display();
                    }

                } else {
                    showToast("You have used all your undos!");
                }
            }
        });
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