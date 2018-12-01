package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class StartingConnectFourActivity extends StartingActivity {
    /**
     * The ConnectFourBoardManager to be used in the game
     */
    private ConnectFourBoardManager boardManager;
    /**
     * The username of the player to play
     */
    private String username;
    /**
     * The size of the board
     */
    private int size;
    /**
     * The gameIndex of this game
     */
    private int gameIndex = 2;
    /**
     * The BoardManager factory
     */
    private BoardManagerFactory bmFactory = new BoardManagerFactory();
    /**
     * The UserManager for this game
     */
    private UserManager userManager;

    /**
     * Initializes the activity, by initializing listeners and setting the view
     *
     * @param savedInstanceState the savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = new FileManager();
        username = new LoginManager().getPersonLoggedIn();
        userManager = new UserManager();
        setContentView(R.layout.starting_connect4);
        addLoadButtonListener();
        addStartButtonListener();
        addbtnScoreboardListener();
        setSizeDropdown();
        assert username != null; //There should be someone logged in once we get to this screen.
    }

    /**
     * Initializes the spinner which allows the user to select from several board sizes
     */
    private void setSizeDropdown() {
        Spinner dropdown = findViewById(R.id.dropdownC4);
        String[] itemsForDropdown = new String[]{"6x6", "7x7", "8x8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemsForDropdown);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }

    /**
     * Adds a listener for the scoreboard button
     */
    private void addbtnScoreboardListener() {
        Button btnScoreboard = findViewById(R.id.btnScoreboardC4);

        btnScoreboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchToScoreboardScreen();
            }
        });
    }

    /**
     * Adds a listener for the start button
     */
    private void addStartButtonListener() {
        Button startGameButton = findViewById(R.id.btnStartGameC4);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner dropdown = findViewById(R.id.dropdownC4);
                String selectedSize = dropdown.getSelectedItem().toString();
                size = Integer.parseInt(selectedSize.substring(0, 1));
                boardManager = (ConnectFourBoardManager) bmFactory.getBoardManager(gameIndex, size);
                userManager.setUserUndos(username, 0, gameIndex, boardManager);
                TextView p2username = findViewById(R.id.txtP2UsernameC4);
                String p2usernameString = p2username.getText().toString().trim();
                TextView p2password = findViewById(R.id.txtP2PasswordC4);
                String p2passwordString = p2password.getText().toString().trim();
                startButtonHelper(p2usernameString, p2passwordString, gameIndex);
            }
        });
    }

    /**
     * Adds a listener for the loadButton.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.btnLoadGameConnect4);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadGame(gameIndex, username);
            }
        });
    }
}

