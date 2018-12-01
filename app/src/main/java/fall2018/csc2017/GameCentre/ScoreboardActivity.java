package fall2018.csc2017.GameCentre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreboardActivity extends AppCompatActivity {

    /**
     * TextView for the sliding score
     * List of all the users' highscores.
     */
    TextView slidingScore;
    /**
     * TextView for the hasami shogi score
     */
    TextView hasamiScore;
    /**
     * TextView for the Connect 4 scores
     */
    TextView connect4Score;
    /**
     * TextView for the session score
     */
    TextView sessionScore;

    /**
     * The string representing the winning player
     */
    String winner;

    /**
     * The file manager, assisting with reading and
     * writing the .ser files
     * Called when the scoreboard button is pressed, or when the user completes a game.
     * Displays high scores in a list format.
     */
    FileManager fm = new FileManager();

    /**
     * Called when the screen is created
     *
     * @param savedInstanceState the saved instance state bundle used to call the screen
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String winnerUsername = getIntent().getStringExtra("username");
        winner = winnerUsername;
        User user = fm.getUser(winnerUsername);
        int newScore = getIntent().getIntExtra("result", -1);
        int[] results = new int[4];
        for (int i = 0; i < 3; i++) {
            results[i] = user.getMaxScore(i);
        }
        results[3] = newScore;

        setContentView(R.layout.scoreboard);
        slidingScore = findViewById(R.id.slidingTilesScoreViewer);
        hasamiScore = findViewById(R.id.hasamiShogiScoreViewer);
        connect4Score = findViewById(R.id.connect4ScoreViewer);
        sessionScore = findViewById(R.id.currentScoreViewer);
        slidingScore.setText("Sliding Tiles: " + String.valueOf(results[0]));
        hasamiScore.setText("Hasami Shogi: " + String.valueOf(results[1]));
        connect4Score.setText("Connect 4: " + String.valueOf(results[2]));
        try {
            if (results[3] > -1) {
                sessionScore.setText("Your Score: " + String.valueOf(results[3]));
            } else {
                sessionScore.setText("Your Score: <N/A>");
            }
        } catch (IndexOutOfBoundsException e) {
            sessionScore.setText("Your Score: " + "<N/A>");
        }

        addResetScoresButtonListener();
        addGoToGameListListener();
        addGoToGlobalScoresListener();

    }

    /**
     * Adds the listener for the global score button,
     * goes to the global leader-board if pressed
     */
    private void addGoToGlobalScoresListener() {
        Button globalScores = findViewById(R.id.goToGlobalScores);
        globalScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGlobalScores();
            }
        });
    }

    /**
     * Goes to the global leader-boards upon pressing the button
     */
    private void goToGlobalScores() {
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }

    /**
     * A listener for the reset button. Resets scores when pressed,
     * and this fucction tracks button pressing
     * /*
     * Adds a reset scores button listener which calls a method to set all user high scores to 0.
     */
    private void addResetScoresButtonListener() {
        Button resetScores = findViewById(R.id.resetScores);
        resetScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = fm.getUser(winner);
                user.resetScoreHashmapForAllGames();
                fm.saveUser(user, winner);
                updateResetScores();
                Toast.makeText(GlobalApplication.getAppContext(), "Scores for " + user.getUsername() + " are reset!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Resets the score screen to 0 for all scores
     */
    @SuppressLint("SetTextI18n")
    public void updateResetScores() {
        slidingScore.setText("Sliding Tiles: 0");
        hasamiScore.setText("Hasami Shogi: 0");
        connect4Score.setText("Connect 4: 0");
    }

    /**
     * A listener for the going to the game list button
     * Goes to the game list screen to start new games when pressed
     */
    private void addGoToGameListListener() {
        Button gameListButton = findViewById(R.id.goToGameList);
        gameListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameList();
            }
        });

    }

    /**
     * A function to transition to the game list screen to start new games or
     * load old games
     */
    public void goToGameList() {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }
}

