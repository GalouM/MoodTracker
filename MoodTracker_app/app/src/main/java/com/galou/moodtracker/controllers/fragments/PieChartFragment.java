package com.galou.moodtracker.controllers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.galou.moodtracker.R;
import com.galou.moodtracker.controllers.activity.PieChartActivity;
import com.galou.moodtracker.models.MoodBank;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * <b>
 *     Represent a PieChart Fragment
 * </b>
 * <p>
 *     Contains a Pie Chart that displays a history of the user's moods
 *
 *     A {@link Fragment} subclass
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see PieChartActivity
 * @see MoodBank
 * @see Fragment
 *
 */
public class PieChartFragment extends Fragment {

    /**
     * Pie Chart that displays the mood history from the user
     *
     * <p>
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     *
     * @see PieChartFragment#setupPieChart()
     */
    @BindView(R.id.fragment_pie_chart_chart) PieChartView pieChartView;
    /**
     * List that contains all the mood to display in the PieChart
     *
     * @see PieChartFragment#setupPieChart()
     */
    private List<SliceValue> pieData = new ArrayList<>();
    /**
     * Contains the Data for the Chart
     *
     * @see PieChartFragment#setupDefaultSlice()
     */
    private PieChartData pieChartData;
    /**
     * MoodBank that contains the moods to display on the PieChart
     * <p>
     *     It contains the user's moods history
     * </p>
     *
     * @see PieChartFragment#setMoodBank()
     */
    private MoodBank moodBank;
    /**
     * Activity that contains the fragment, PieChart Activity
     *
     * @see PieChartActivity
     * @see PieChartFragment#setPieChartActivity()
     */
    private PieChartActivity activity;
    /**
     * Percent of mood Sad
     *
     * @see PieChartFragment#setupDataInPercent()
     */private int percentSad;
    /**
     * Percent of mood Disappointed
     *
     * @see PieChartFragment#setupDataInPercent()
     */
    private int percentDis;
    /**
     * Percent of mood Normal
     *
     * @see PieChartFragment#setupDataInPercent()
     */
    private int percentNormal;
    /**
     * Percent of mood Happy
     *
     * @see PieChartFragment#setupDataInPercent()
     */
    private int percentHappy;
    /**
     * Percent of mood Super Happy
     *
     * @see PieChartFragment#setupDataInPercent()
     */
    private int percentSupHappy;
    /**
     * Total number of mood
     *
     * @see PieChartFragment#setupDataInPercent()
     */
    private float nbTotalMood;
    /**
     * Slice that contains the super happy moods
     *
     * @see PieChartFragment#setupDefaultSlice()
     * @see PieChartFragment#setupSliceToTargetValue()
     */
    private SliceValue superHappySlice;
    /**
     * Slice that contains the happy moods
     *
     * @see PieChartFragment#setupDefaultSlice()
     * @see PieChartFragment#setupSliceToTargetValue()
     */
    private SliceValue happySlice;
    /**
     * Slice that contains the normal moods
     *
     * @see PieChartFragment#setupDefaultSlice()
     * @see PieChartFragment#setupSliceToTargetValue()
     */
    private SliceValue normalSlice;
    /**
     * Slice that contains the disappointed moods
     *
     * @see PieChartFragment#setupDefaultSlice()
     * @see PieChartFragment#setupSliceToTargetValue()
     */
    private SliceValue disSlice;
    /**
     * Slice that contains the sad moods
     *
     * @see PieChartFragment#setupDefaultSlice()
     * @see PieChartFragment#setupSliceToTargetValue()
     */
    private SliceValue sadSlice;

    /**
     * Constructor
     */
    public PieChartFragment() {}


    /**
     * Actions executed when the fragment is created
     * <p>
     *     <li>inflate with the layout</li>
     *     <li>Bind views with ButterKnife</li>
     *     <li>set the Activity name that contains the fragment</li>
     *     <li>set the data to be in percent</li>
     *     <li>set and display the Pie Chart</li>
     * </p>
     * @param inflater
     *      layout inflater
     * @param container
     *      fragment container
     * @param savedInstanceState
     *      saved state of the fragment
     * @return
     *      view inflated
     *
     * @see PieChartFragment#setPieChartActivity()
     * @see PieChartFragment#setMoodBank()
     * @see PieChartFragment#setupDataInPercent()
     * @see PieChartFragment#setupPieChart()
     * @see PieChartActivity
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        ButterKnife.bind(this, view);
        this.setPieChartActivity();
        this.setMoodBank();
        this.setupDefaultSlice();
        this.setupDataInPercent();
        this.setupPieChart();
        return view;
    }

    //----------------------//
    //SET DATA
    //----------------------//
    /**
     * Set the name of the activity that contains the fragment
     *
     * @see PieChartFragment#activity
     */
    private void setPieChartActivity(){
        activity = (PieChartActivity) getActivity();
    }

    /**
     * Set the MoodBank to be displayed by the RecyclerView
     *
     * @see PieChartFragment#moodBank
     */
    private void setMoodBank(){
        moodBank = activity.getBank();
    }

    /**
     * Create default Slice with name and add them to the pieData
     * <p>
     *     Create default value allow to create an animation between default and targeted values
     * </p>
     *
     * @see PieChartFragment#pieData
     * @see PieChartFragment#superHappySlice
     * @see PieChartFragment#happySlice
     * @see PieChartFragment#normalSlice
     * @see PieChartFragment#disSlice
     * @see PieChartFragment#sadSlice
     */
    private void setupDefaultSlice(){
        superHappySlice = new SliceValue(20,
                getResources().getColor(R.color.banana_yellow))
                .setLabel(getString(R.string.mood_to_string_super_happy));
        happySlice = new SliceValue(20,
                getResources().getColor(R.color.light_sage))
                .setLabel(getString(R.string.mood_to_string_happy));
        normalSlice = new SliceValue(20,
                getResources().getColor(R.color.cornflower_blue_65))
                .setLabel(getString(R.string.mood_to_string_normal));
        disSlice = new SliceValue(20,
                getResources().getColor(R.color.warm_grey))
                .setLabel(getString(R.string.mood_to_string_disappointed));
        sadSlice = new SliceValue(20,
                getResources().getColor(R.color.faded_red))
                .setLabel(getString(R.string.mood_to_string_sad));
        pieData.add(superHappySlice);
        pieData.add(happySlice);
        pieData.add(normalSlice);
        pieData.add(disSlice);
        pieData.add(sadSlice);

        pieChartData = new PieChartData(pieData);

    }

    /**
     * Setup number of moods in percent
     *
     * @see PieChartFragment#getPercent(float)
     * @see PieChartFragment#percentSad
     * @see PieChartFragment#percentDis
     * @see PieChartFragment#percentNormal
     * @see PieChartFragment#percentHappy
     * @see PieChartFragment#percentSupHappy
     */
    private void setupDataInPercent(){
        float sad = (float) moodBank.getNbSad();
        float disappointed = (float) moodBank.getNbDisappointed();
        float normal = (float) moodBank.getNbNormal();
        float happy = (float) moodBank.getNbHappy();
        float superHappy = (float) moodBank.getNbSuperHappy();

        nbTotalMood = sad + disappointed + normal + happy + superHappy;

        percentSad = getPercent(sad);
        percentDis = getPercent(disappointed);
        percentNormal = getPercent(normal);
        percentHappy = getPercent(happy);
        percentSupHappy = getPercent(superHappy);

    }

    //----------------------//
    //SET VIEW
    //----------------------//
    /**
     * Setup the PieChart View look
     *
     * @see PieChartFragment#pieData
     * @see PieChartFragment#pieChartView
     */
    private void setupPieChart(){
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabelsOnlyForSelected(true);

        pieChartView.setOnValueTouchListener(new PieChartFragment.ValueTouchListener());
        this.setupSliceToTargetValue();
        pieChartView.startDataAnimation();
        pieChartView.setPieChartData(pieChartData);


        if(nbTotalMood <= 0){
            pieChartView.setVisibility(View.GONE);
            Toast.makeText(getContext(), R.string.no_mood_saved_toast, Toast.LENGTH_LONG).show();
        }
    }


    //----------------------//
    //UTILS
    //----------------------//

    /**
     * Return a number of mood in percent
     * @param number
     *      number to be converted
     * @return
     *      mood in percent int
     */
    private int getPercent(float number){
        float dividedNum = number/nbTotalMood;
        float percent = dividedNum * 100;
        return (int) percent;
    }

    /**
     * Setup Slices of the chart to thr right value
     * <p>
     *     Create animation from default value to target Values
     * </p>
     *
     * @see PieChartFragment#superHappySlice
     * @see PieChartFragment#happySlice
     * @see PieChartFragment#normalSlice
     * @see PieChartFragment#disSlice
     * @see PieChartFragment#sadSlice
     * @see PieChartFragment#setupDefaultSlice()
     */
    private void setupSliceToTargetValue() {
        superHappySlice.setTarget(percentSupHappy);
        happySlice.setTarget(percentHappy);
        normalSlice.setTarget(percentNormal);
        disSlice.setTarget(percentDis);
        sadSlice.setTarget(percentSad);
    }


    /**
     * Listener to know when a part of the PieChart is clicked
     */
    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getContext(), value.getValue() + "%", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {}

    }

}
