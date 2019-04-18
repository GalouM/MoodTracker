package com.galou.moodtracker.controllers.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.galou.moodtracker.R;
import com.galou.moodtracker.models.MoodBank;

/**
 * <b>
 *     Represents the History Activity of the application
 * </b>
 * <p>
 *     A {@link BaseActivity} subclass
 *
 *     Launch when the user click on the HistoryButton from the MainActivity.
 *     From this Activity the user can see his last 7 Moods
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see BaseActivity
 * @see com.galou.moodtracker.models.MoodBank
 */

public class HistoryActivity extends BaseActivity {

    // FOR DATA
    /**
     * Width of the screen
     * <p>
     *     Used to adapt the width of the displayed Moods
     * </p>
     *
     * @see HistoryActivity#computeScreenSize()
     * @see com.galou.moodtracker.views.MoodViewHolder#setSizeCardViewForMood(int)
     */
    private int screenWidth;
    /**
     * Actions executed when the Activity is created
     * <p>
     *     <li>get the user MoodBank saved in the SharedPreferences</li>
     *     <li>get the size of the screen</li>
     *     <li>set the layout of the activity</li>
     * </p>
     *
     * @param savedInstanceState
     *      saved state of the activity
     *
     * @see HistoryActivity#getMoodBank()
     * @see HistoryActivity#moodBank
     * @see HistoryActivity#screenWidth
     * @see HistoryActivity#computeScreenSize()
     * @see MoodBank
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMoodBank();
        this.computeScreenSize();
        setContentView(R.layout.activity_history);
    }

    //----------------------//
    //SET DATA
    //----------------------//

    /**
     * Get the height and the width of the screen
     *
     * @see HistoryActivity#screenWidth
     */
    private void computeScreenSize(){
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        screenWidth = display.widthPixels;
    }

    /**
     * Public getter to share the width of the screen.
     * @return
     *      width of the screen
     *
     * @see HistoryActivity#computeScreenSize()
     * @see HistoryActivity#screenWidth
     */
    public int getWidthScreen(){
        return screenWidth;
    }


}
