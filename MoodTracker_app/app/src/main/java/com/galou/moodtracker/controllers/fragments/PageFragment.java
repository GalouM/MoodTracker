package com.galou.moodtracker.controllers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.galou.moodtracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <b>
 *     Represent a Page Fragment
 * </b>
 *
 * <p>
 *     Used to instantiate the different Pages of the {@link com.galou.moodtracker.views.VerticalViewPager}
 *     Each pages has it's own background color and drawable but all have the same structure
 * </p>
 * A {@link Fragment} subclass
 */
public class PageFragment extends Fragment {

    // keys for bundle
    /**
     * Key used in the bundle for the color parameter
     */
    public static final String KEY_COLOR = "color";
    /**
     * Key used in the bundle for the smiley parameter
     */
    public static final String KEY_SMILEY = "smiley";

    // views
    /**
     * Fragment layout
     * <p>
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     */
    @BindView(R.id.pageFragment_rootview) LinearLayout rootLayout;
    /**
     * ImageView used to displayed the smiley
     * <p>
     *     Use the Butter Knife library to bind the view in the activity
     * </p>
     */
    @BindView(R.id.pageFragment_smiley) ImageView smileyImage;


    /**
     * Constructor
     */
    public PageFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of {@link PageFragment} and add it to its bundle
     *
     * @param color
     *      background color of the fragment
     * @param smiley
     *      smiley of the fragment
     * @return
     *      the new fragment with its bundle
     *
     * @see com.galou.moodtracker.controllers.adapter.PageAdapter
     */
    public static PageFragment newInstance(int color, int smiley){
        PageFragment frag = new PageFragment();
        // create bundle and add data
        Bundle args = new Bundle();
        args.putInt(KEY_COLOR, color);
        args.putInt(KEY_SMILEY, smiley);
        frag.setArguments(args);

        return(frag);
    }


    /**
     * Actions executed when the fragment is created
     * <p>
     *     <li>inflate fragment with its layout</li>
     *     <li>Bind view with ButterKnife</li>
     *     <li>get data from the bundle</li>
     *     <li>set background color from the color stored in the bundle</li>
     *     <li>set the smiley image with the drawable stored in the bundle</li>
     * </p>
     * @param inflater
     *      layout inflater
     * @param container
     *      fragment container
     * @param savedInstanceState
     *      saved state of the fragment
     * @return
     *      view inflated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result =  inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, result);
        // Get data from bundle
        int color = getArguments().getInt(KEY_COLOR, -1);
        int smiley = getArguments().getInt(KEY_SMILEY, -1);
        // update widget with data
        rootLayout.setBackgroundColor(color);
        smileyImage.setImageResource(smiley);

        return result;


    }


}
