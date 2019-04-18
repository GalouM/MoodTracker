package com.galou.moodtracker.controllers.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.galou.moodtracker.R;
import com.galou.moodtracker.controllers.activity.HistoryActivity;
import com.galou.moodtracker.controllers.adapter.MoodAdapter;
import com.galou.moodtracker.models.Mood;
import com.galou.moodtracker.models.MoodBank;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <b>
 *     Represent a Fragment History
 * </b>
 * <p>
 *     Contains a RecyclerView that displays a history of the user's moods
 *
 *     A {@link Fragment} subclass
 *     Implements the interface Listener from MoodAdapter used as a callback to know when a user
 *     click on an element of the RecyclerView
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 * @see HistoryActivity
 * @see MoodBank
 * @see MoodAdapter
 * @see Fragment
 *
 */
public class HistoryFragment extends Fragment implements MoodAdapter.Listener{

    // FOR DESIGN
    /**
     * RecyclerView that displays the mood history from the user
     *
     * <p>
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     *
     * @see HistoryFragment#configureRecyclerView()
     */
    @BindView(R.id.fragment_history_recyclerview) RecyclerView recyclerView;

    // FOR DATA
    /**
     * MoodBank that contains the moods to display on the RecyclerView
     * <p>
     *     It contains the user's moods history
     * </p>
     *
     * @see HistoryFragment#setMoodBank()
     */
    private MoodBank moodBank;
    /**
     * MoodAdapter that bind the RecyclerView with the MoodBank
     *
     * @see MoodAdapter
     * @see com.galou.moodtracker.views.MoodViewHolder
     */
    private MoodAdapter moodAdapter;
    /**
     * Width of the screen
     * <p>
     *     Used to adapt the width of the displayed Moods
     * </p>
     *
     * @see HistoryFragment#setScreenSize()
     * @see com.galou.moodtracker.views.MoodViewHolder#setSizeCardViewForMood(int)
     */
    private float widthScreen;
    /**
     * Activity that contains the fragment, History Activity
     *
     * @see HistoryActivity
     * @see HistoryFragment#setHistoryActivity()
     */
    private HistoryActivity activity;


    /**
     * Constructor
     */
    public HistoryFragment() {}


    /**
     * Actions executed when the fragment is created
     * <p>
     *     <li>inflate with the layout</li>
     *     <li>Bind views with ButterKnife</li>
     *     <li>set the Activity name that contains the fragment</li>
     *     <li>set the moodBank list to display it on the RecyclerView</li>
     *     <li>set the size of the screen</li>
     *     <li>configure the RecyclerView with an adapter and the data to display</li>
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
     * @see HistoryFragment#setHistoryActivity()
     * @see HistoryFragment#setMoodBank()
     * @see HistoryFragment#setScreenSize()
     * @see HistoryFragment#configureRecyclerView()
     * @see MoodAdapter
     * @see HistoryActivity
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        this.setHistoryActivity();
        this.setMoodBank();
        this.setScreenSize();
        this.configureRecyclerView();
        this.checkIfMoodBankEmpty();
        return view;
    }

    /**
     * Check if the bank is empty and display a Toats message if noo mood have been saved
     *
     * @see HistoryFragment#moodBank
     */
    private void checkIfMoodBankEmpty(){
        if (moodBank.getMoodList().size() <= 0){
            Toast.makeText(getContext(), R.string.no_mood_saved_toast, Toast.LENGTH_LONG).show();
        }

    }

    //----------------------//
    //SET DATA
    //----------------------//

    /**
     * Set the name of the activity that contains the fragment
     *
     * @see HistoryFragment#activity
     */
    private void setHistoryActivity(){
        activity = (HistoryActivity) getActivity();
    }

    /**
     * Set the MoodBank to be displayed by the RecyclerView
     *
     * @see HistoryFragment#moodBank
     * @see HistoryFragment#configureRecyclerView()
     */
    private void setMoodBank(){
        moodBank = this.activity.getBank();

    }

    /**
     * Set the size of the screen to adapt size of the displayed mood
     *
     * @see HistoryFragment#widthScreen
     * @see HistoryFragment#configureRecyclerView()
     */
    private void setScreenSize(){
        this.widthScreen = this.activity.getWidthScreen();
    }

    //----------------------//
    //SET VIEW
    //----------------------//

    /**
     * Configure the RecyclerView that display the moods, with an adapter and the correct data
     *
     * @see MoodAdapter
     * @see HistoryFragment#moodBank
     * @see HistoryFragment#widthScreen
     * @see HistoryFragment#recyclerView
     */
    private void configureRecyclerView(){
        this.moodAdapter = new MoodAdapter(this.moodBank.getMoodList(), widthScreen, this, this.getActivity());
        this.recyclerView.setAdapter(moodAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    //----------------------//
    //ACTIONS
    //----------------------//

    /**
     * Actions when the user click on the button Comment from one of the element of the RecyclerView
     * <p>
     *     Override method from the interface MoodAdapter.Listener
     *
     *     Create a Toast that displays the mood comment on which the user clicked
     * </p>
     *
     * @param position
     *      position in the moodBank list of the mood
     */
    @Override
    public void onClickCommentButton(int position) {
        Mood mood = moodBank.getMood(position);
        Toast.makeText(getContext(), mood.getComment(), Toast.LENGTH_LONG).show();
    }

    /**
     * Actions when the user click on the button Share from one of the element of the RecyclerView
     * <p>
     *     Override method from the interface MoodAdapter.Listener
     *
     *     Open a sharing Intent to share the Mood with another application
     * </p>
     *
     * @param position
     *      position in the moodBank list of the mood
     *
     * @see Mood#toString(Resources)
     */
    @Override
    public void onClickShareButton(int position) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, moodBank.getMood(position).toString(getResources()));
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_intent_share_your_mood)));

    }
}
