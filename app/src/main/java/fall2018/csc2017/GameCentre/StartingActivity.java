package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class StartingActivity extends AppCompatActivity {
    /**
     * The FileManager for this activity
     */
    public FileManager fm;
    /**
     * The userManager for this activity
     */
    private UserManager userManager;
    /**
     * The loginManager for this activity
     */
    private LoginManager lm = new LoginManager();

    /**
     * Initializes the attributes of this activity
     *
     * @param savedInstanceState the savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = new FileManager();
        userManager = new UserManager();
    }

    /**
     * Loads a saved game, if available
     *
     * @param gameIndex the game index representing the game to load
     * @param username  the user to load from
     */
    public void loadGame(int gameIndex, String username) {
        if (fm.getStack(username, gameIndex).size() < 1) {
            Toast.makeText(this, "No game to load! Start a new game!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
            switchToGame(gameIndex);
        }
    }

    /**
     * Switch to the Activity view of the game specified by the gameIndex.
     */
    public void switchToGame(int gameIndex) {
        Intent intent = null;
        switch (gameIndex) {
            case 0:
                intent = new Intent(this, SlidingActivity.class);
                break;
            case 1:
                intent = new Intent(this, ShogiActivity.class);
                break;
            case 2:
                intent = new Intent(this, ConnectFourActivity.class);
                break;
        }

        startActivity(intent);
    }

    /**
     * Switches to the scoreboard screen when the scoreboard button is clicked
     */
    public void switchToScoreboardScreen() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        String username = lm.getPersonLoggedIn();
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * Checks if player two's credentials are valid, and takes the appropriate action.
     *
     * @param p2usernameString the username for player two
     * @param p2passwordString the password for player two
     * @param gameIndex        the game which the players are trying to play
     */

    public void startButtonHelper(String p2usernameString, String p2passwordString, int gameIndex) {
        if (p2usernameString.equals("")) {
            Toast.makeText(this, "Signing P2 as Guest", Toast.LENGTH_SHORT).show();
            userManager.addOpponent(gameIndex, "Guest");
            switchToGame(gameIndex);
        } else if (p2passwordString.equals("")) {
            Toast.makeText(this, "Password Field is Empty!", Toast.LENGTH_SHORT).show();
        } else if (p2usernameString.equals(new LoginManager().getPersonLoggedIn())) {
            Toast.makeText(this, "The opponent cannot be the same as Player 1! Use a different player!", Toast.LENGTH_SHORT).show();
        } else {
            LoginManager lm = new LoginManager();
            if (lm.authenticateP2(p2usernameString, p2passwordString)) {
                Toast.makeText(this, "Starting Game...", Toast.LENGTH_SHORT).show();
                userManager.addOpponent(gameIndex, p2usernameString);
                switchToGame(gameIndex);
            }
        }
    }
}