package com.galou.moodtracker.models;

import android.content.res.Resources;

import com.galou.moodtracker.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <b>
 *     Represents a Mood.
 * </b>
 * <p>
 * A Mood is characterize by the following information:
 * <ul>
 * <li>a mood id corresponding to a drawable</li>
 * <li>a color</li>
 * <li>an object Date</li>
 * <li>a comment</li>
 * </ul>
 * </p>
 *  <p>
 *  Implement the interface {@link Serializable}
 * </p>
 *
 * @see MoodBank
 *
 * @author galou
 * @version 1.0
 *
 */
public class Mood implements Serializable{

    /**
     * Mood ID of the Mood corresponding to a drawable
     * @see Mood#Mood(int, int, Calendar, String, int)
     * @see Mood#getMood()
     */
    private int mood;

    /**
     * Color of the Mood
     * @see Mood#Mood(int, int, Calendar, String, int id)
     * @see Mood#getColor()
     */
    private int color;

    /**
     * Date of the Mood
     *
     * For more information on "Date", see Doc from Java.util
     * @see Mood#Mood(int, int, Calendar, String, int)
     * @see Mood#getDate()
     * @see Date
     */
    private Calendar date;

    /**
     * Comment of the Mood
     * @see Mood#Mood(int, int, Calendar, String, int)
     * @see Mood#getComment()
     */
    private String comment;

    /**
     * ID of the mood
     * @see Mood#Mood(int, int, Calendar, String, int)
     * @see Mood#getId()
     */
    private int id;

    /**
     *  Mood's constructor
     *  <p>
     *      When the mood is being build, a mood, a color, a date and a comment are assigned.
     *  </p>
     *
     * @param mood
     *      Mood's mood
     * @param color
     *      Mood's color
     * @param date
     *      Mood's date
     * @param comment
     *      Mood's comment
     * @param id
     *      Mood's ID
     *
     * @see Mood#mood
     * @see Mood#color
     * @see Mood#date
     * @see Mood#comment
     */
    public Mood(int mood, int color, Calendar date, String comment, int id) {
        this.mood = mood;
        this.color = color;
        this.date = date;
        this.comment = comment;
        this.id = id;
    }

    //----------------------//
    //GETTER
    //----------------------//

    /**
     *  Return mood of the mood
     * @return Mood's mood
     */
    public int getMood() {
        return mood;
    }

    /**
     *  Return color of the mood
     * @return Mood's color
     */
    public int getColor() {
        return color;
    }

    /**
     *Return date of the mood
     * @return Mood's date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     *Return comment of the mood
     * @return Mood's comment
     */
    public String getComment(){
        return comment;
    }

    /**
     * Return ID of the Mood
     * @return
     *      int
     */
    public int getId() {
        return id;
    }

    /**
     * Return Mood in string.
     * <p>
     *     Convert mood into words
     * </p>
     * @param res
     *      project resources
     * @return
     *      Mood in string
     */
    public String toString(Resources res) {

        String moodInString;

        switch (mood){
            case R.drawable.smiley_super_happy:
                moodInString = res.getString(R.string.mood_to_string_super_happy);
                break;
            case R.drawable.smiley_happy:
                moodInString = res.getString(R.string.mood_to_string_happy);
                break;
            case R.drawable.smiley_normal:
                moodInString = res.getString(R.string.mood_to_string_normal);
                break;
            case R.drawable.smiley_disappointed:
                moodInString = res.getString(R.string.mood_to_string_disappointed);
                break;
            case R.drawable.smiley_sad:
                moodInString = res.getString(R.string.mood_to_string_sad);
                break;
            default:
                moodInString = res.getString(R.string.mood_to_string_not_recognize);
                break;
        }

        SimpleDateFormat simpleFormat = new SimpleDateFormat(
                res.getString(R.string.format_diplsay_date), Locale.CANADA);
        String formattedDate = simpleFormat.format(date.getTime());

        return String.format(res.getString(R.string.i_was_on_mood_to_string),
                moodInString, formattedDate, comment);
    }
}
