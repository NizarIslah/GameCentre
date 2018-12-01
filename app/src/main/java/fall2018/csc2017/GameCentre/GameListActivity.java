package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * The gamelist activity with all the games.
 */
public class GameListActivity extends AppCompatActivity {
    /**
     * Invokes when the user logs in or signs up. Displays list of games for user to play.
     *
     * @param savedInstanceState of activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list);
        addSlidingTilesButtonListener();
        addHasamiShogiButtonListener();
        addConnectFourButtonListener();
    }

    /**
     * This will launch the sliding tiles GameActivity on click.
     */
    public void addSlidingTilesButtonListener() {
        Button launchSlidingTiles = findViewById(R.id.btnLaunchSlide);
        launchSlidingTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGame(0);
            }
        });
    }

    /**
     * Launches the hasami shogi game upon pressing
     */
    public void addHasamiShogiButtonListener() {
        Button launchHasamiShogi = findViewById(R.id.btnLaunchShogi);
        launchHasamiShogi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGame(1);
            }
        });

    }

    /**
     * Launches the connect 4 game upon pressing
     */
    public void addConnectFourButtonListener() {
        Button launchHasamiShogi = findViewById(R.id.btnLaunchConnectFour);
        launchHasamiShogi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGame(2);
            }
        });
    }


    /**
     * Launches the sliding tiles game based on index.
     *
     * @param gameIndex index of the game selected.
     */
    public void launchGame(int gameIndex) {
        Intent intent = null;
        switch (gameIndex) {
            case 0:
                intent = new Intent(this, StartingSlidingActivity.class);
                break;
            case 1:
                intent = new Intent(this, StartingShogiActivity.class);
                break;
            case 2:
                intent = new Intent(this, StartingConnectFourActivity.class);
        }
        assert intent != null;
        intent.putExtra("gameIndex", gameIndex);
        startActivity(intent);
    }

}
