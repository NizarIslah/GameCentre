package fall2018.csc2017.GameCentre;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;


public class GestureDetectGridView extends GridView {
    /**
     * The GestureDetector object that will be used here
     */
    private GestureDetector gDetector;


    /**
     * Overloaded Constructor that takes a Context
     * @param context the given context
     */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Overloaded Constructor that takes a Context and AttributeSet
     * @param context the given context
     * @param attrs the given attribute set
     */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Overloaded Constructor that takes a Context, an AttributeSet, and a defaultStyleAttribute integer
     * @param context the given context
     * @param attrs the set of attributes
     * @param defStyleAttr the style attribute integer
     */
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * An initializer method for the gesture detector
     * @param context the given context
     */
    private void init(final Context context) {
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });
    }

    /**
     * Switches to the leader-board screen after the game is won by
     * the guest
     *
     * @param context the current context
     */
    public void switchToLeaderBoardScreen(Context context, String winner) {
        Intent intent = new Intent(context, LeaderBoardActivity.class);
        intent.putExtra("winner", winner);
        context.startActivity(intent);
    }

    /**
     * Switches to the scoreboard screen if a game is won
     * @param context the given context
     * @param result the result integer
     * @param username the username of the winner
     */
    public void switchToScoreboardScreen(Context context, int result, String username) {
        Intent intent = new Intent(context, ScoreboardActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("username", username);
        context.startActivity(intent);
    }

    /**
     * Captures the user touch
     *
     * @param ev the MotionEvent for user touch
     * @return whether the touch happened or not
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }
}
