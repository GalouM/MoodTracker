package com.galou.moodtracker.controllers.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.galou.moodtracker.models.MoodBank;
import com.google.gson.Gson;

/**
 * <b>
 *     Represents a Base Activity
 * </b>
 * <p>
 *     Base class used to create the app activities
 *
 *     A {@link AppCompatActivity} subclass
 *
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see AppCompatActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    // FOR DATA
    /**
     * Shared Preferences in which are stored the user's data
     *
     * @see BaseActivity#getSharedPreferences(String, int)
     * @see MainActivity#saveMoodInPrefs()
     */
    protected SharedPreferences preferences;

    /**
     * MoodBank in which are stored the user's moods
     *
     * @see BaseActivity#getSharedPreferences(String, int)
     */
    protected MoodBank moodBank;

    /**
     * Key used to store and retrieve the application sharedPreference
     *
     * @see BaseActivity#preferences
     * @see BaseActivity#getMoodBank()
     * @see MainActivity#saveMoodInPrefs()
     */
    public static final String SHARED_PREF_KEY = "moodTrackerPrefs";

    /**
     * Key used to store and retrieve the user's MoodBank in the sharedPreference
     *
     * @see BaseActivity#preferences
     * @see BaseActivity#moodBank
     * @see BaseActivity#getMoodBank()
     * @see MainActivity#saveMoodInPrefs()
     */
    public static final String PREF_KEY_FEELING_BANK = "moodBank";

    /**
     * Get the user's MoodBank from the SharedPreferences
     *
     * @see BaseActivity#preferences
     * @see BaseActivity#moodBank
     */
    protected void getMoodBank(){
        preferences = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(PREF_KEY_FEELING_BANK, null);

        if(json != null){
            moodBank = gson.fromJson(json, MoodBank.class);
        } else {
            moodBank = new MoodBank();
        }
    }

    //----------------------//
    //GETTERS AND SETTERS
    //----------------------//

    /**
     * Public getter to share the moodBank.
     * <p>
     *     return the moodBank saved in the SharedPreferences
     * </p>
     * @return
     *      moodBank, list of the user past moods
     *
     * @see MoodBank
     * @see PieChartActivity#getSharedPreferences(String, int)
     * @see PieChartActivity#moodBank
     *
     */
    public MoodBank getBank(){
        return moodBank;
    }
}
