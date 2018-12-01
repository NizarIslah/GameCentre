package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    /**
     * The scoreboard manager, to manage the logics of the activity
     */
    private ScoreboardManager scon = new ScoreboardManager();
    /**
     * The string representing the winner of the gamete
     */
    private String winner;

    /**
     * Called whenever the screen is presented to the user
     *
     * @param savedInstanceState the saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        winner = getIntent().getStringExtra("winner");
        setContentView(R.layout.scoreboard_global);
        List c4Scores = scon.getGlobalScores(2);
        List shScores = scon.getGlobalScores(1);
        List stScores = scon.getGlobalScores(0);
        StringBuilder c4Format = scon.formatScores(c4Scores);
        StringBuilder shFormat = scon.formatScores(shScores);
        StringBuilder stFormat = scon.formatScores(stScores);
        TextView connect4box = findViewById(R.id.c4HighScores);
        TextView shBox = findViewById(R.id.hsHighScores);
        TextView stBox = findViewById(R.id.stHighScores);
        connect4box.setText(c4Format);
        shBox.setText(shFormat);
        stBox.setText(stFormat);

        addGameListButtonListener();
        addUserStatsButtonListener();
    }

    /**
     * Adds the listener for the button that shows the user stats when pressed
     */
    private void addUserStatsButtonListener() {
        Button LocalScoreboard = findViewById(R.id.goToLocalScoreboard);
        LocalScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (winner == null || !winner.equals("Guest")) {
                    goToLocalScoreboard();
                } else {
                    Toast.makeText(getApplicationContext(), "Sign up to save and see your scores!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Switches to the screen displaying the player's top scores
     */
    private void goToLocalScoreboard() {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        String username = new LoginManager().getPersonLoggedIn();
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * Adds the listener for a button that switches to the screen showing
     * the list of games when pressed
     */
    private void addGameListButtonListener() {
        Button gameListBtn = findViewById(R.id.goToGameListbtn);
        gameListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGamesList();
            }
        });
    }

    /**
     * Switches to the list of games
     */
    private void goToGamesList() {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }
}
