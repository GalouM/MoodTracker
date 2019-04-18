package com.galou.moodtracker.controllers.activity;

import android.os.Bundle;

import com.galou.moodtracker.R;
import com.galou.moodtracker.models.MoodBank;

/**
 * <b>
 *     Represents the PieChart Activity of the application
 * </b>
 * <p>
 *     A {@link BaseActivity} subclass
 *
 *     Launch when the user click on the PieChart button from the MainActivity.
 *     From this Activity the user can see his moods in a pie chart
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see BaseActivity
 * @see com.galou.moodtracker.models.MoodBank
 */
public class PieChartActivity extends BaseActivity {


    /**
     * Actions executed when the Activity is created
     * <p>
     *     <li>get the user MoodBank saved in the SharedPreferences</li>
     *     <li>set the layout of the activity</li>
     * </p>
     *
     * @param savedInstanceState
     *      saved state of the activity
     *
     * @see PieChartActivity#getMoodBank()
     * @see PieChartActivity#moodBank
     * @see MoodBank
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMoodBank();
        setContentView(R.layout.activity_pie_chart);

    }



}
