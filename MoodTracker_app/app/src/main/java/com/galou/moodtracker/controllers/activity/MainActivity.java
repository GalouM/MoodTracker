package com.galou.moodtracker.controllers.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;

import com.galou.moodtracker.R;
import com.galou.moodtracker.controllers.adapter.PageAdapter;
import com.galou.moodtracker.models.Mood;
import com.galou.moodtracker.views.VerticalViewPager;
import com.google.gson.Gson;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <b>
 *     Represents the Main Activity of the application
 * </b>
 * <p>
 *     A {@link BaseActivity} subclass
 *
 *     The user can select a Mood by scrolling the pages of a {@link VerticalViewPager}.
 *     A temporary mood will be created with the user selection and added to the user {@link com.galou.moodtracker.models.MoodBank} the next day.
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see BaseActivity
 * @see Mood
 * @see VerticalViewPager
 * @see com.galou.moodtracker.models.MoodBank
 */
public class MainActivity extends BaseActivity{

    // views
    /**
     * {@link VerticalViewPager} used to slide between different fragments
     * <p>
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     *
     * @see VerticalViewPager
     * @see MainActivity#configureViewPager()
     */
    @BindView(R.id.activity_main_viewpager) VerticalViewPager viewPager;

    // For data
    /**
     * Color from the fragment displayed used to create the temporary mood
     *
     * @see MainActivity#moodTemp
     * @see Mood
     *
     */
    private int colorFromFragment = 1;

    /**
     * Id of the drawable from the fragment displayed used to create the temporary mood
     *
     * @see MainActivity#moodTemp
     * @see Mood
     */
    private int smileyFromFragment;

    /**
     * List of color used by the {@link PageAdapter} to give a different color to each fragment
     *
     * @see PageAdapter
     * @see VerticalViewPager
     * @see MainActivity#configureViewPager()
     */
    private int[] colorList;

    /**
     * Comment enter by the user in the AlertDialog used to create the temporary mood
     *
     * @see MainActivity#onClickCommentButton(View)
     * @see MainActivity#moodTemp
     * @see Mood
     */
    private String userComment;

    /**
     * Temporary Mood choose by the user
     * <p>
     *     The mood is stored until the user choose another mood for the day or open the application on another day
     *     It inherits its property from the fragment displayed (color and drawable), today's date and the user comment
     * </p>
     *
     * @see MainActivity#addTmpMoodInBank()
     * @see MainActivity#getMoodBank()
     * @see Mood
     * @see MainActivity#smileyFromFragment
     * @see MainActivity#colorFromFragment
     * @see MainActivity#todayDate
     * @see MainActivity#userComment
     */
    private Mood moodTemp;

    /**
     * today's date
     *
     * @see MainActivity#getTodayDate()
     */
    private Calendar todayDate;

    /**
     * Id of the mood set from the viewpager ID
     *
     */
    private int idPageMood;

    /**
     * Key used to store and retrieve the user's temporary Mood in the sharedPreference
     *
     * @see BaseActivity#preferences
     * @see MainActivity#getMoodBank()
     * @see Mood
     */
    public static final String PREF_FEY_FEELING_TMP = "feelingTempKey";

    /**
     * Set the {@link VerticalViewPager} at this position when the activity is created
     *
     * @see MainActivity#configureViewPager()
     * @see VerticalViewPager
     */
    private int start_view = 3;

    /**
     * List of drawable used by the {@link PageAdapter} to give a different drawable to each fragment
     *
     * @see PageAdapter
     * @see VerticalViewPager
     * @see MainActivity#configureViewPager()
     */
    private static final int[] MOOD_DRAWABLE = new int[]{
            R.drawable.smiley_sad,
            R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,
            R.drawable.smiley_happy,
            R.drawable.smiley_super_happy};

    /**
     * Actions executed when the Activity is created
     * <p>
     *     <li>set the layout of the activity</li>
     *     <li>get today's date</li>
     *     <li>get the user {@link com.galou.moodtracker.models.MoodBank} and temporary mood saved in the SharedPreferences</li>
     *     <li>configure the {@link ViewPager}</li>
     * </p>
     *
     * @param savedInstanceState
     *      saved state of the activity
     *
     * @see MainActivity#getTodayDate()
     * @see MainActivity#getMoodBank()
     * @see MainActivity#configureViewPager()
     * @see MainActivity#moodBank
     * @see MainActivity#moodTemp
     * @see MainActivity#todayDate
     * @see com.galou.moodtracker.models.MoodBank
     * @see Mood
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.getTodayDate();
        this.getMoodBank();
        this.configureViewPager();
    }

    /**
     * Actions executed when the Activity is stopped
     * <p>
     *     <li>create a new moodTemp from the user selection</li>
     *     <li>save {@link com.galou.moodtracker.models.MoodBank} and moodTemp in the SharedPreferences</li>
     * </p>
     *
     * @see MainActivity#moodTemp
     * @see BaseActivity#preferences
     * @see Mood
     * @see MainActivity#saveMoodInPrefs()
     */
    @Override
    protected void onStop() {
        super.onStop();
        moodTemp = new Mood(this.smileyFromFragment, this.colorFromFragment, todayDate, this.userComment, this.idPageMood);
        this.saveMoodInPrefs();
    }

    //----------------------//
    //SET VIEWS
    //----------------------//

    /**
     * Configure the View Pager
     * <p>
     *     <li>set the adapter with the list of colors and list of drawable</li>
     *     <li>set listener to the {@link VerticalViewPager} to know when a page is scrolled to get the actual fragment displayed</li>
     *     <li>get the color and drawable from the fragment displayed to create the temporary mood with those data</li>
     * </p>
     *
     * @see VerticalViewPager
     * @see PageAdapter
     * @see MainActivity#colorList
     * @see MainActivity#MOOD_DRAWABLE
     * @see MainActivity#smileyFromFragment
     * @see MainActivity#colorFromFragment
     */
    private void configureViewPager(){
        this.colorList = getResources().getIntArray(R.array.colorPagesViewPager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),
                this.colorList, MOOD_DRAWABLE) {
        });
        viewPager.setCurrentItem(start_view); //display to mood happy when open

        // get data from viewpager when open
        smileyFromFragment = MOOD_DRAWABLE[viewPager.getCurrentItem()];
        colorFromFragment = colorList[viewPager.getCurrentItem()];
        idPageMood = viewPager.getCurrentItem();

        // set listener to what view is active
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                smileyFromFragment = MOOD_DRAWABLE[position];
                colorFromFragment = colorList[position];
                idPageMood = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Get today's date from Calendar Library
     *
     * @see Calendar
     * @see MainActivity#todayDate
     */
    private void getTodayDate(){
        todayDate = Calendar.getInstance();//get today's date
    }

    //----------------------//
    //SET DATA
    //----------------------//

    /**
     * Save MoodBank and temporary Mood in the SharedPreferences
     *
     * @see BaseActivity#preferences
     * @see BaseActivity#moodBank
     * @see MainActivity#moodTemp
     * @see Mood
     * @see com.galou.moodtracker.models.MoodBank
     */
    private void saveMoodInPrefs(){
        SharedPreferences.Editor prefEditor = preferences.edit();
        Gson gson = new Gson();
        String jsonFeelingBank = gson.toJson(moodBank);
        prefEditor.putString(PREF_KEY_FEELING_BANK, jsonFeelingBank);
        String jsonFeelingTmp = gson.toJson(moodTemp);
        prefEditor.putString(PREF_FEY_FEELING_TMP, jsonFeelingTmp);
        prefEditor.apply();

    }

    /**
     * Get MoodBank from SharedPreferences
     * <p>
     *     Override method from BaseActivity to get the temporary Mood from the preferences as well.
     *     Add the temporary Mood to the bank
     * </p>
     *
     * @see BaseActivity#getMoodBank()
     * @see BaseActivity#preferences
     * @see MainActivity#moodTemp
     * @see Mood
     * @see MainActivity#addTmpMoodInBank()
     */
    @Override
    protected void getMoodBank() {
        super.getMoodBank();
        Gson gson = new Gson();
        String jsonFeelingTmp = preferences.getString(PREF_FEY_FEELING_TMP, null);


        if(jsonFeelingTmp != null){
            moodTemp = gson.fromJson(jsonFeelingTmp, Mood.class);
            this.addTmpMoodInBank();
        }

        if(jsonFeelingTmp != null && isMoodToday()){
            start_view = moodTemp.getId();
            userComment = moodTemp.getComment();
        } else {
            start_view = 3;
            userComment = "";
        }
    }

    /**
     * Add temporary mood to the user's MoodBank
     * <p>
     *     If the temporary mood date is different than today's date, the mood is added to the bank
     *     and the sharedPreferences are updated
     * </p>
     *
     * @see MainActivity#moodTemp
     * @see BaseActivity#moodBank
     * @see MainActivity#todayDate
     * @see Mood
     * @see com.galou.moodtracker.models.MoodBank
     * @see MainActivity#saveMoodInPrefs()
     */
    private void addTmpMoodInBank(){
        if (!isMoodToday()){
            moodBank.addMood(moodTemp);
            this.saveMoodInPrefs();

        }

    }

    /**
     * Return boolean is saved Mood is from today
     * @return
     *      boolean
     */
    private boolean isMoodToday(){
        return (DateUtils.isToday(moodTemp.getDate().getTime().getTime()));

    }

    //----------------------//
    //ACTION USER
    //----------------------//

    /**
     * Actions executed when the user click on the History Button
     * <p>
     *     A new HistoryActivity is started.
     *
     *     The OnClick Listener is created with the ButterKnife Library.
     * </p>
     * @param view
     *      View on which the user has clicked
     *
     * @see HistoryActivity
     */
    @OnClick(R.id.main_activity_history_button)
    public void onClickHistoryButton (View view){
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    /**
     * Actions executed when the user click on the Comment Button
     * <p>
     *     A new Alert Dialog is started and allow the user to enter a comment
     *     The comment is stored as a String into the userComment variable and used to create the temporary mood
     * </p>
     * @param view
     *      View on which the user has clicked
     *
     * @see MainActivity#userComment
     * @see MainActivity#moodTemp
     * @see Mood
     */
    @OnClick(R.id.main_activity_comment_button)
    public void onClickCommentButton(View view){
        final AlertDialog.Builder commentDialog = new AlertDialog.Builder(this);
        commentDialog.setTitle(R.string.title_dialog_comment);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(userComment);
        commentDialog.setView(input);
        commentDialog.setPositiveButton(R.string.validate_button_dialog_comment, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userComment = input.getText().toString();
            }
        });
        commentDialog.setNegativeButton(R.string.cancel_button_dialog_comment, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        commentDialog.show();
    }

    /**
     * Actions executed when the user click on the Pie Chart button
     * <p>
     *     A new activity is started that allow the user to visualize his MoodBank in a pie chart
     *
     * </p>
     * @param view
     *      View on which the user has clicked
     *
     * @see com.galou.moodtracker.models.MoodBank
     * @see PieChartActivity
     */
    @OnClick(R.id.main_activity_pie_chart_button)
    public void onClickPieChartButton (View view){
        Intent intent = new Intent(MainActivity.this, PieChartActivity.class);
        startActivity(intent);
    }

}
