package com.galou.moodtracker.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.galou.moodtracker.R;
import com.galou.moodtracker.controllers.adapter.MoodAdapter;
import com.galou.moodtracker.models.Mood;
import com.galou.moodtracker.models.NumberToWords;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <b>
 *     Represent a Mood View Holder
 * </b>
 *
 * <p>
 *     Display a mood in the Recycler View
 *     Setup how a mood is displayed in the RecyclerView.
 *     Each mood is displayed with a CardView and the following parameters are setup
 *     depending on the mood attributes:
 *     <li>the CardView background color</li>
 *     <li>the CardView width</li>
 *     <li>the comment button if it has a comment</li>
 *     <li>display the date in a TextView</li>
 *
 *     A {@link android.support.v7.widget.RecyclerView.ViewHolder} subclass
 *     Implements a OnClickListener interface for the views.
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see Mood
 */
public class MoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * Text View used to display when the Mood was created
     * <p>
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     *
     * @see Mood
     * @see MoodViewHolder#setDateText(Calendar, Context)
     */
    @BindView(R.id.fragment_history_item_text) TextView textView;
    /**
     * CardView used to display the mood
     * <p>
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     *
     * @see MoodViewHolder#updateWithMood(Mood, MoodAdapter.Listener, Context)
     */
    @BindView(R.id.fragment_history_item_card_view) CardView cardView;
    /**
     * Comment Image Button displayed if the user enter a comment for this mood
     * <p>
     *     Display a Toast with the comment when the user click on it
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     *
     * @see MoodViewHolder#setCommentButton(String, MoodAdapter.Listener)
     */
    @BindView(R.id.fragment_history_item_comment_button) ImageButton commentButton;
    /**
     * Share Image Button
     * <p>
     *     Allow the user to share the selected mood
     * </p>
     *
     * @see MoodViewHolder#setCommentButton(String, MoodAdapter.Listener)
     */
    @BindView(R.id.fragment_history_item_share_button) ImageButton shareButton;

    // FOR DATA
    /**
     * Width of the screen used to adapt the width of the CardView
     *
     * @see MoodViewHolder#setSizeCardViewForMood(int)
     * @see MoodViewHolder#MoodViewHolder(View, float)
     */
    private float widthScreen;
    /**
     * Callback to the Fragment that will handle the action when the user click on a button
     * <p>
     *     WeakReference avoid memory leak and allow the callback to bw collected by he garbage collector
     * </p>
     *
     * @see com.galou.moodtracker.controllers.fragments.HistoryFragment#onClickCommentButton(int)
     */
    private WeakReference<MoodAdapter.Listener> callbackWeakReference;

    /**
     * Constructor
     * <p>
     *     Get width and height of the screen
     *     Bind the views with Butter Knife
     * </p>
     *
     * @param itemView
     *      Items of the view
     * @param widthScreen
     *      width of the screen
     *
     * @see MoodViewHolder#widthScreen
     */
    public MoodViewHolder(View itemView, float widthScreen) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.widthScreen = widthScreen;

    }

    //----------------------//
    //SET VIEW
    //----------------------//

    /**
     * Update ViewHolder with the Mood Data
     * <p>
     *     <li>set the background color of the CardView</li>
     *     <li>set the size of the CardView</li>
     *     <li>set the comment button behavior</li>
     *     <li>set the mood date text</li>
     * </p>
     * @param mood
     *      Mood display by this item
     * @param callback
     *      callback to the fragment
     * @param context
     *      context of the RecyclerView
     *
     * @see Mood
     * @see MoodViewHolder#callbackWeakReference
     * @see MoodViewHolder#widthScreen
     * @see MoodViewHolder#setSizeCardViewForMood(int)
     * @see MoodViewHolder#setCommentButton(String, MoodAdapter.Listener)
     * @see MoodViewHolder#setDateText(Calendar, Context)
     */
    public void updateWithMood(Mood mood, MoodAdapter.Listener callback, Context context) {
        this.cardView.setCardBackgroundColor(mood.getColor());

        // set size cardView
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) cardView.getLayoutParams();
        layoutParams.width = (int) this.setSizeCardViewForMood(mood.getMood());

        //set buttons
        this.setCommentButton(mood.getComment(), callback);
        this.shareButton.setOnClickListener(this);
        this.callbackWeakReference = new WeakReference<MoodAdapter.Listener>(callback);

        //set text date
        textView.setText(this.setDateText(mood.getDate(), context));

    }

    /**
     * Set the Size of the CardView depending on the size of the screen and the type of Mood
     *
     * @param moodType
     *      type of mood
     *
     * @return
     *      width of the cardView
     *
     * @see MoodViewHolder#updateWithMood(Mood, MoodAdapter.Listener, Context)
     * @see MoodViewHolder#widthScreen
     * @see Mood
     */
    private double setSizeCardViewForMood(int moodType){
        double ratio = 1;
        switch (moodType){
            case R.drawable.smiley_super_happy:
                ratio = widthScreen;
                break;
            case R.drawable.smiley_happy:
                ratio = widthScreen * 0.8;
                break;
            case R.drawable.smiley_normal:
                ratio = widthScreen * 0.6;
                break;
            case R.drawable.smiley_disappointed:
                ratio = widthScreen * 0.4;
                break;
            case R.drawable.smiley_sad:
                ratio = widthScreen * 0.2;
                break;
        }
        return ratio;
    }

    /**
     * Display comment button is the mood contains a comment and create callback to fragment
     *
     * @param comment
     *      comment of the mood
     * @param callback
     *      on click listener on the comment button
     *
     * @see MoodViewHolder#commentButton
     * @see MoodViewHolder#callbackWeakReference
     * @see MoodViewHolder#onClick(View)
     * @see MoodViewHolder#updateWithMood(Mood, MoodAdapter.Listener, Context)
     * @see Mood
     */
    private void setCommentButton(String comment, MoodAdapter.Listener callback){
        this.commentButton.setOnClickListener(this);
        if (comment.isEmpty()) {
            commentButton.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Set the Date text
     * <p>
     *     Calculate the number of days elapsed since the date of the mood and set the text accordingly
     *     Display number in words
     * </p>
     * @param date
     *      date of the mood
     * @param context
     *      context of the Recycler view, used to get the Resources
     * @return
     *      String of the text to display
     *
     * @see Mood
     * @see MoodViewHolder#updateWithMood(Mood, MoodAdapter.Listener, Context)
     * @see NumberToWords
     * @see MoodViewHolder#getDayInWeek(long)
     * @see MoodViewHolder#getCalInMilli(Calendar)
     *
     *
     */
    private String setDateText(Calendar date, Context context){
        Resources resources = context.getResources();
        Calendar todayDate = Calendar.getInstance();
        long todayDateMilli = getCalInMilli(todayDate);
        long dateInMilli = getCalInMilli(date);
        long daySinceMood = TimeUnit.MILLISECONDS.toDays(Math.abs(todayDateMilli - dateInMilli));

        String textToDisplay;

        if(daySinceMood >= 31){
            textToDisplay = resources.getString(R.string.text_view_feeling_month);
        } else {
            if(daySinceMood >= 7){
                int weekSinceMood = getDayInWeek(daySinceMood);
                if(weekSinceMood == 1){
                    textToDisplay = String.format(resources.getString(R.string.text_view_feeling_week), NumberToWords.convert(weekSinceMood));
                } else {
                    textToDisplay = String.format(resources.getString(R.string.text_view_feeling_weeks), NumberToWords.convert(weekSinceMood));
                }
            } else {
                if(daySinceMood <= 1){
                    textToDisplay = resources.getString(R.string.text_view_feeling_yesterday);
                }
                else if(daySinceMood == 2){
                    textToDisplay = resources.getString(R.string.text_view_feeling_two_days_ago);
                } else {
                    textToDisplay = String.format(resources.getString(R.string.text_view_feeling_days), NumberToWords.convert(daySinceMood));
                }
            }
        }

        return textToDisplay;

    }

    //----------------------//
    //UTILS
    //----------------------//

    /**
     * Convert a Calendar Object in Milliseconds
     * <p>
     *     Set all time to 0 to only compare dates
     *     Return a long, the time in milliseconds
     * </p>
     * @param date
     *      date to be converted
     *
     * @return
     *      date in milliseconds
     */
    private long getCalInMilli(Calendar date){
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.setTimeZone(TimeZone.getTimeZone("PST"));

        return date.getTimeInMillis();
    }

    /**
     * Convert days in week
     * @param days
     *      number of days
     * @return
     *      number of weeks
     */
    private int getDayInWeek(long days){
        int week = 0;
        while (days >= 7) {
            days -= 7;
            week += 1;
        }
        return week;
    }


    /**
     * Action executed when user click on a button
     * @param v
     *      view clicked
     */
    @Override
    public void onClick(View v) {
        MoodAdapter.Listener callback = callbackWeakReference.get();
        if(callback != null) {
            if(v == shareButton){
                callback.onClickShareButton(getAdapterPosition());
            }
            if(v == commentButton){
                callback.onClickCommentButton(getAdapterPosition());
            }

        }
    }
}
