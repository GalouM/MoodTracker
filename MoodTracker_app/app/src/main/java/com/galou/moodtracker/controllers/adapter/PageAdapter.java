package com.galou.moodtracker.controllers.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.galou.moodtracker.controllers.fragments.PageFragment;

/**
 * <b>
 *     Represent a Page Adapter
 * </b>
 *
 * <p>
 *     Bind the instance of {@link PageFragment} with the {@link com.galou.moodtracker.views.VerticalViewPager}
 *
 *     A {@link FragmentStatePagerAdapter} subclass
 * </p>
 *
 * @see com.galou.moodtracker.views.VerticalViewPager
 * @see PageFragment
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    /**
     * List of color used to give a different color to each fragment
     *
     * @see PageAdapter#PageAdapter(FragmentManager, int[], int[])
     */
    private int[] colors;
    /**
     * List of smiley to give a different drawable to each fragment
     *
     * @see PageAdapter#PageAdapter(FragmentManager, int[], int[])
     */
    private int[] smiley;

    /**
     * Constructor, create a new adapter with a list of color and a list of smiley
     *
     * @param mgr
     *      fragment manager
     * @param colors
     *      list of color used to create the fragments
     * @param smiley
     *      list of smiley used to create the fragments
     *
     * @see PageAdapter#smiley
     * @see PageAdapter#colors
     */
    public PageAdapter(FragmentManager mgr, int[] colors, int[] smiley){
        super(mgr);
        this.colors = colors;
        this.smiley = smiley;
    }

    /**
     * Create new Instance of {@link PageFragment} every time the method is called
     *
     * <p>
     *     Override method getItem()
     * </p>
     *
     * @param position
     *      position of th ViewPager
     * @return
     *      new instance of the fragment created
     *
     * @see PageFragment#newInstance(int, int)
     */
    @Override
    public Fragment getItem(int position) {
        return(PageFragment.newInstance(this.colors[position], this.smiley[position]));
    }

    /**
     * Set the total number of pages of the ViewPager
     * <p>
     *     Override method getCount()
     * </p>
     *
     * @return
     *      total number of pages
     *
     */
    @Override
    public int getCount() {
        return(this.colors.length);
    }


}
