package com.galou.moodtracker.models;

import com.galou.moodtracker.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>
 *   Represents a list of mood.
 *   </b>
 * <p>
 * A Bank Mood is characterize by the following information:
 * <ul>
 * <li>a array of mood</li>
 * </ul>
 * </p>
 *  <p>
 *  Implement the interface {@link Serializable}
 * </p>
 *
 * @see Mood
 *
 * @author galou
 * @version 1.0
 */
public class MoodBank implements Serializable {

    /**
     * List of mood
     * <p>
     *     We can add mood from this list
     * </p>
     * @see MoodBank#MoodBank()
     * @see MoodBank#addMood(Mood)
     * @see MoodBank#getMoodList()
     */
    private List<Mood> moodList;

    /**
     * Count number of mood Super Happy the user enter
     *
     * @see MoodBank#addMoodToCategory(Mood)
     */
    private int nbSuperHappy;
    /**
     * Count number of mood Happy the user enter
     *
     * @see MoodBank#addMoodToCategory(Mood)
     */
    private int nbHappy;
    /**
     * Count number of mood Normal the user enter
     *
     * @see MoodBank#addMoodToCategory(Mood)
     */
    private int nbNormal;
    /**
     * Count number of mood Disappointed the user enter
     *
     * @see MoodBank#addMoodToCategory(Mood)
     */
    private int nbDisappointed;
    /**
     * Count number of mood Sad the user enter
     *
     * @see MoodBank#addMoodToCategory(Mood)
     */
    private int nbSad;

    /**
     * Max number of mood in the list
     *
     * @see MoodBank#isListTooLong()
     */
    private static final int MAX_FEELING_IN_LIST = 8;

    /**
     * Constructor MoodBank. Create an empty array list
     */
    // CONSTRUCTOR
    public MoodBank() {
        this.moodList = new ArrayList<>();
        this.nbSad = 0;
        this.nbDisappointed = 0;
        this.nbNormal = 0;
        this.nbHappy = 0;
        this.nbSuperHappy = 0;
    }

    //----------------------//
    //GETTER AND SETTER
    //----------------------//

    /**
     * Add Mood to the bank
     * <p>
     *     if the list have too many items it remove the first item of the list
     *
     * </p>
     *
     *
     * @param mood
     *      Mood to add
     *
     * @see MoodBank#isListTooLong()
     * @see MoodBank#moodList
     * @see MoodBank#MAX_FEELING_IN_LIST
     * @see Mood
     * @see MoodBank#addMoodToCategory(Mood)
     */
    public void addMood(Mood mood){
        this.moodList.add(mood);
        if (this.isListTooLong()) {
            this.moodList.remove(0);
        }
        this.addMoodToCategory(mood);

    }

    /**
     * Return mood at specify position in the list
     *
     * @param position
     *      position of the desired mood
     * @return
     *      mood at specify position
     *
     * @see MoodBank#moodList
     * @see Mood
     */
    public Mood getMood(int position){
        return this.moodList.get(position);
    }

    /**
     * Return the list of Mood from the bank
     *
     * @return
     *      a list of mood
     *
     * @see MoodBank#moodList
     */
    public List<Mood> getMoodList() {
        return this.moodList;
    }

    /**
     * Return number of mood SuperHappy
     * @return
     *      number mood super happy
     *
     * @see MoodBank#nbSuperHappy
     * @see MoodBank#addMoodToCategory(Mood)
     */
    public int getNbSuperHappy() {
        return nbSuperHappy;
    }

    /**
     * Return number of mood Happy
     * @return
     *      number mood happy
     *
     * @see MoodBank#nbHappy
     * @see MoodBank#addMoodToCategory(Mood)
     */
    public int getNbHappy() {
        return nbHappy;
    }

    /**
     * Return number of mood Normal
     * @return
     *      number mood super normal
     *
     * @see MoodBank#nbNormal
     * @see MoodBank#addMoodToCategory(Mood)
     */
    public int getNbNormal() {
        return nbNormal;
    }

    /**
     * Return number of mood Disappointed
     * @return
     *      number mood super disappointed
     *
     * @see MoodBank#nbDisappointed
     * @see MoodBank#addMoodToCategory(Mood)
     */
    public int getNbDisappointed() {
        return nbDisappointed;
    }

    /**
     * Return number of mood Sad
     * @return
     *      number mood sad
     *
     * @see MoodBank#nbSad
     * @see MoodBank#addMoodToCategory(Mood)
     */
    public int getNbSad() {
        return nbSad;
    }

    //----------------------//
    //SET DATA
    //----------------------//

    /**
     * Return true is the length of the list is longer than the specify size
     * <p>
     *     Used to know if we should remove or not the first item of the list when we add a new Mood
     * </p>
     *
     * @return
     *      true or false depending if the list is too long or not
     *
     * @see MoodBank#moodList
     * @see MoodBank#MAX_FEELING_IN_LIST
     * @see MoodBank#addMood(Mood)
     */
    private boolean isListTooLong(){
        return (this.moodList.size() >= MAX_FEELING_IN_LIST);
    }

    /**
     * Count number of mood of each category (sad, disappointed, normal, happy, super happy)
     *
     * @param mood
     *      mood to add to the category
     *
     * @see MoodBank#addMood(Mood)
     * @see MoodBank#nbSad
     * @see MoodBank#nbDisappointed
     * @see MoodBank#nbNormal
     * @see MoodBank#nbHappy
     * @see MoodBank#nbSuperHappy
     */
    private void addMoodToCategory(Mood mood){
        switch (mood.getMood()){
            case R.drawable.smiley_super_happy:
                nbSuperHappy += 1;
                break;
            case R.drawable.smiley_happy:
                nbHappy += 1;
                break;
            case R.drawable.smiley_normal:
                nbNormal += 1;
                break;
            case R.drawable.smiley_disappointed:
                nbDisappointed += 1;
                break;
            case R.drawable.smiley_sad:
                nbSad += 1;
                break;
        }
    }


    /**
     * Return a String of the MoodBank
     * @return
     *      MoodBank in string
     */
    @Override
    public String toString() {
        return "MoodBank{" +
                "moodList=" + moodList +
                '}';
    }
}
