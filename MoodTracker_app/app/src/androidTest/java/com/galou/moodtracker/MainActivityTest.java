package com.galou.moodtracker;

import android.content.Context;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.galou.moodtracker.controllers.activity.HistoryActivity;
import com.galou.moodtracker.controllers.activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by galou on 2019-02-18
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // For data
    private Context context;

    @Rule
    public final ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        this.context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void checkIfCanSwipeOnViewPager(){
        onView(ViewMatchers.withId(R.id.activity_main_viewpager)).perform(swipeDown());
    }

    // TODO test add feeling to bank, how to do test on onStop()

    @Test
    public void checkIfHistoryButtonStartHistoryActivity(){
        onView(ViewMatchers.withId(R.id.main_activity_history_button)).perform(click());
        Intents.init();
        intended(hasComponent(HistoryActivity.class.getName()));
    }

}
