package com.galou.moodtracker.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * <b>
 *     Represent a Vertical View Pager
 * </b>
 * <p>
 *     Create a vertical ViewPager instead of horizontal
 *
 *
 *     A {@link ViewPager} subclass
 * </p>
 */
public class VerticalViewPager extends ViewPager {

    /**
     * Constructor
     * @param context
     *      context of the ViewPager
     */
    public VerticalViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * Constructor
     *
     * @param context
     *      context of the ViewPager
     * @param attrs
     *      attribute set
     */
    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initialize the vertical ViewPager
     * <p>
     *     set a new transformer type that is called every time a page is scrolled
     * </p>
     *
     * @see VerticalPageTransformer
     */
    private void init(){
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * <b>Vertical Page Transformer</b>
     * <p>
     *     Called every time a page is scrolled
     * </p>
     *
     */
    private class VerticalPageTransformer implements ViewPager.PageTransformer{
        /**
         * Apply new property transformation to the given page
         * @param page
         *      apply transformation to this page
         * @param position
         *      position on the page
         */
        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position <-1){
                page.setAlpha(0);

            } else if(position <=1){
                page.setAlpha(1);

                // counteract the default side transition
                page.setTranslationX(page.getWidth() * -position);

                //set Y position to swipe from top to bottom
                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);

            } else {
                page.setAlpha(0);
            }
        }
    }

    /**
     * Swap XY to transform the horizontal ViewPager in a Vertical one
     *
     * @param ev
     *      motion event
     * @return
     *      motion event swapped
     *
     * @see VerticalViewPager#onInterceptTouchEvent(MotionEvent)
     */
    private MotionEvent swapXY(MotionEvent ev){
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    /**
     *  Intercept a Touch Event
     * @param ev
     *      motion event
     * @return
     *      boolean if a motion event happened
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    /**
     * Touch event
     * @param ev
     *      motion event
     * @return
     *      boolean motion event swapped
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }


}
